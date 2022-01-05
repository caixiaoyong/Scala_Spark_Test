package com.atguigu.project01

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.{immutable, mutable}

/**
 * 需求2：Top10热门品类中每个品类的Top10活跃Session统计
 *
 * @author CZY
 * @date 2021/12/1 21:11
 * @description require02_top10Category_sessionTop10
 */
object require02_top10Category_sessionTop10 {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //1. 从文件读取创建RDD
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\user_visit_action.txt")

    //2. 封装样例类 将切割出来的数据封装进样例类里
    val actionRdd: RDD[UserVisitAction] = rdd.map(line => {
      val datas: Array[String] = line.split("_")
      UserVisitAction(
        datas(0),
        datas(1),
        datas(2),
        datas(3),
        datas(4),
        datas(5),
        datas(6),
        datas(7),
        datas(8),
        datas(9),
        datas(10),
        datas(11),
        datas(12)
      )
    })

    //3.1 创建累加器
    val acc = new MyAcc1

    //3.2 注册累加器
    sc.register(acc, "acc")

    //3.3 使用累加器
    actionRdd.foreach(action => acc.add(action))

    //3.4 获取累加器的累加结果
    // ((4,click),5961) ((4,order),1760) ((4,pay),1271)
    val accValue: mutable.Map[(String, String), Long] = acc.value
    //    accValue.foreach(println)

    //4. 将accValue按照品类id进行分组
    // (4,Map((4,click) -> 5961, (4,order) -> 1760, (4,pay) -> 1271))
    val groupByRdd: Map[String, mutable.Map[(String, String), Long]] = accValue.groupBy(_._1._1)
    //    groupByRdd.foreach(println)

    //5. 将groupByRdd按照key转换成样例类集合
    // CategoryCountInfo(4,5961,1760,1271)
    val iterRdd: immutable.Iterable[CategoryCountInfo] = groupByRdd.map {
      case (key, list) => {
        val click: Long = list.getOrElse((key, "click"), 0L)
        val order = list.getOrElse((key, "order"), 0L)
        val pay = list.getOrElse((key, "pay"), 0L)
        CategoryCountInfo(key, click, order, pay)
      }
    }
    //    iterRdd.foreach(println)

    //6.将集合后的样例类 倒序排序取前10
    // CategoryCountInfo(2,6119,1767,1196)
    val resRdd = iterRdd.toList.sortBy(line => (line.clickCount, line.orderCount, line.payCount))(Ordering[(Long, Long, Long)].reverse).take(10)
    //    resRdd.foreach(println)

    //********************需求二**********************
    //需求2：Top10热门品类中每个品类的Top10活跃Session统计
    //1 获取Top10热门品类
    val ids: List[String] = resRdd.map(_.categoryId)

    //2 ids创建广播变量
    val bdIds: Broadcast[List[String]] = sc.broadcast(ids)

    //3 过滤原始数据,保留热门Top10的点击数据
    val filterActionRdd: RDD[UserVisitAction] = actionRdd.filter(action => {
      //一个会话一定会从点击开始,因此我们在这只要点击的数据
      if (action.click_category_id != "-1") {
        bdIds.value.contains((action.click_category_id))
      } else {
        false
      }
    })

    //4 转换数据结构 UserVisitAction => (品类id-会话id,1)
    val cliAndses2One: RDD[(String, Int)] = filterActionRdd.map(action => (action.click_category_id + "=" + action.session_id, 1))

    //5 按照 品类id-会话id分组聚合 => (品类id-会话id,sum)
    val cliAndses2Sum: RDD[(String, Int)] = cliAndses2One.reduceByKey(_ + _)

    //6 变化结构 (品类id-会话id,sum) => (品类id,(会话id,sum))
/*    val cli2sesAndSum0: RDD[(String, (String, Int))] = cliAndses2One.map(action => {
      val line: Array[String] = action._1.split("=")
      (line(0), (line(1), action._2))
    })*/
    val cli2sesAndSum: RDD[(String, (String, Int))] = cliAndses2Sum.map {
      case (key, sum) =>{
        val keys: Array[String] = key.split("=")
        (keys(0), (keys(1), sum))
      }
    }

    //7 按照品类id分组 (品类id,(会话id,sum)) => (品类id,[(会话id,sum) ,(会话id,sum),....])
    val groupRdd: RDD[(String, Iterable[(String, Int)])] = cli2sesAndSum.groupByKey()

    //8 对groupRDD的每个品类的集合倒序排序,求前10
    val resultRdd: RDD[(String, List[(String, Int)])] = groupRdd.mapValues(action => {
      action.toList.sortBy(_._2)(Ordering[Int].reverse)
    }.take(10))
    resultRdd.collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}

class MyAcc1 extends AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]] {
  //创建本体map
  private val map = new mutable.HashMap[(String, String), Long]()

  //判断本体为空
  override def isZero: Boolean = map.isEmpty

  //复制累加器的副本到executor
  override def copy(): AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]] = new MyAcc1

  //重置累加器
  override def reset(): Unit = map.clear()

  //分区内累加数据
  override def add(action: UserVisitAction): Unit = {
    if (action.click_category_id != "-1") {
      val key = (action.click_category_id, "click")
      map(key) = map.getOrElse(key, 0L) + 1
    } else if (action.order_category_ids != "null") {
      val ids: Array[String] = action.order_category_ids.split(",")
      for (elem <- ids) {
        val key = (elem, "order")
        map(key) = map.getOrElse(key, 0L) + 1
      }
    } else if (action.pay_category_ids != "null") {
      val ids: Array[String] = action.pay_category_ids.split(",")
      for (elem <- ids) {
        val key = (elem, "pay")
        map(key) = map.getOrElse(key, 0L) + 1
      }
    }
  }

  //分区间合并累加器
  override def merge(other: AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]]): Unit = {
    val map1: mutable.Map[(String, String), Long] = other.value


    map1.foreach {
      case (key, count) => {
        map(key) = map.getOrElse(key, 0L) + count
      }
    }
    //    for (elem <- map1) {
    //      case (elem._1, elem._2) => {
    //        map(elem._1) = map.getOrElse(elem._1, 0L) + elem._2
    //      }
    //    }
  }

  //返回本体
  override def value: mutable.Map[(String, String), Long] = map
}