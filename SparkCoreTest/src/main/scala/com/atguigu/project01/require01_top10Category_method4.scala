package com.atguigu.project01

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.{immutable, mutable}

/**
 * 方案四：使用累加器
 * 在优化版本 方案三中 最终按照key聚合仍然需要使用reduceByKey 还是会走shuffle 效率会降低
 * 最后版 使用累加器来提高效率
 *
 * @author CZY
 * @date 2021/12/1 14:52
 * @description require01_top10Category_method4
 */
object require01_top10Category_method4 {
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

    //3. 使用累加器统计相同品类id的点击数量,下单数量,支付数量
    //3.1 创建累加器
    val myAcc = new MyAcc

    //3.2 注册累加器
    sc.register(myAcc, "myAcc")

    //3.3 使用累加器
    actionRdd.foreach(action=>{
      myAcc.add(action)
    })

    //3.4 获得累加器的值 ((品类id,品类操作标识),品类操作次数)
    // ((4,click),5961) ((4,order),1760) ((4,pay),1271)
    val accMap: mutable.Map[(String, String), Long] = myAcc.value
//    accMap.foreach(println)

    //4. 将accMap里按照品类id进行分组
    // (4,Map((4,click) -> 5961, (4,order) -> 1760, (4,pay) -> 1271))
    val groupMap: Map[String, mutable.Map[(String, String), Long]] = accMap.groupBy(_._1._1)

    //5 将groupMap转换成样例类集合
    val infoIter: immutable.Iterable[CategoryCountInfo] = groupMap.map {
      case (id, list) => {
        val click = list.getOrElse((id, "click"), 0L)
        val order = list.getOrElse((id, "order"), 0L)
        val pay = list.getOrElse((id, "pay"), 0L)
        CategoryCountInfo(id, click, order, pay)
      }
    }

    //infoIter.foreach(println)

    //6 倒序排序 取前10
    val resRdd: List[CategoryCountInfo] = infoIter.toList.sortBy(info => (info.clickCount, info.orderCount, info.payCount))(Ordering[(Long, Long, Long)].reverse).take(10)

    resRdd.foreach(println)


    //TODO 3.关闭连接
    sc.stop()
  }
}

/**
 * 输入  UserVisitAction
 * 输出 mutable.Map[(String,String),Long]
 */
class MyAcc extends AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]] {
  //首先需要创建一个本体 返回值是什么 本体就是什么
  private val map = new mutable.HashMap[(String, String), Long]()

  // 判断本体为空
  override def isZero: Boolean = map.isEmpty

  // 复制累加器的副本到executor
  override def copy(): AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]] = new MyAcc

  // 重置累加器
  override def reset(): Unit = map.clear()

  // 分区内累加数据
  override def add(action: UserVisitAction): Unit = {
    if (action.click_category_id != "-1") {
      //点击的action
      val key = (action.click_category_id, "click")
      map(key) = map.getOrElse(key, 0L) + 1
    } else if (action.order_category_ids != "null") {
      // 下单的action
      val ids: Array[String] = action.order_category_ids.split(",")
      //ids数组里有多个下单品类的id
      for (elem <- ids) {
        val key = (elem, "order")
        map(key) = map.getOrElse(key, 0L) + 1
      }
    } else if (action.pay_category_ids != "null") {
      // 支付的action
      val ids: Array[String] = action.pay_category_ids.split(",")
      //ids数组里有多个下单品类的id
      for (elem <- ids) {
        val key = (elem, "pay")
        map(key) = map.getOrElse(key, 0L) + 1
      }
    }
  }

  // 分区间合并累加器
  override def merge(other: AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]]): Unit = {
    val map1: mutable.Map[(String, String), Long] = other.value
    map1.foreach {
      case (key, count) => {
        map(key) = map.getOrElse(key, 0L) + count
      }
    }
  }

  // 返回本体
  override def value: mutable.Map[(String, String), Long] = map
}