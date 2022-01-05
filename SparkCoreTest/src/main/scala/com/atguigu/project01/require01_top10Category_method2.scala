package com.atguigu.project01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 方案二：
 * 使用样例类，在公司使用此方法 可读性更高
 * @author CZY
 * @date 2021/12/1 0:23
 * @description require01_top10Category_method2
 */
object require01_top10Category_method2 {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf()
      .setAppName("SparkCoreTest")
      .setMaster("local[*]")
      // 替换默认的序列化机制
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      // 注册需要使用kryo序列化的自定义类
      .registerKryoClasses(Array(classOf[UserVisitAction]))

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3. 从文件读取创建RDD
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\user_visit_action.txt")

    //3.1 将读取的数据转换为样例类
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

    //3.2 转换数据结构 将actionRdd的数据变为 CategoryCountInfo(品类ID,1,0,0)
    val infoRdd: RDD[CategoryCountInfo] = actionRdd.flatMap(action => {
      if (action.click_category_id != "-1") {
        //点击的数据 这里使用flatMap 因为下单和支付的数据是集合 所以将点击数据设置成集合
        List(CategoryCountInfo(action.click_category_id, 1, 0, 0))
      } else if (action.order_category_ids != "null") {
        //下单行为
        val arr: Array[String] = action.order_category_ids.split(",")
        arr.map(id => CategoryCountInfo(id, 0, 1, 0))
      } else if (action.pay_category_ids != "null") {
        //支付行为
        val arr: Array[String] = action.pay_category_ids.split(",")
        arr.map(id => CategoryCountInfo(id, 0, 0, 1))
      } else {
        Nil
      }
    })
    //infoRdd.collect().foreach(println)

    //4.按照品类id对数据分组
    val groupRdd: RDD[(String, Iterable[CategoryCountInfo])] = infoRdd.groupBy(_.categoryId)

    //5.对每一组的数据,进行聚合
    val reduceRdd: RDD[CategoryCountInfo] = groupRdd.mapValues(iter => {
      iter.reduce(
        (info1, info2) => {
          info1.orderCount += info2.orderCount
          info1.clickCount += info2.clickCount
          info1.payCount += info2.payCount
          info1
        }
      )
    }).map(_._2)

    //6 将聚合后的数据 倒序排序 取前10
    val resArr: Array[CategoryCountInfo] = reduceRdd.sortBy(info => (info.clickCount, info.orderCount, info.payCount), false).take(10)
    resArr.foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}

