package com.atguigu.project01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 方案三：
 * 在样例类2的方法下 虽然可以实现排名功能 但是在代码中
 * 3.2 转换数据结构里没有<k,v>结构 使用groupby效率太低
 * 没有提前聚合功能 且在当中使用的reduce是scala当中的方法，不是算子 性能低 数量大容易卡
 * 所以这里提前实现<k,v>结构
 * 样例类+算子优化
 *
 * @author CZY
 * @date 2021/12/1 0:23
 * @description require01_top10Category_method2
 */
object require01_top10Category_method3 {
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
    val infoRdd = actionRdd.flatMap(line => {
      //点击数据
      if (line.click_category_id != "-1") {
        List((line.click_category_id, CategoryCountInfo(line.click_category_id, 1, 0, 0)))
      } else if (line.order_category_ids != "null") {
        // 下单行为
        val arr: Array[String] = line.order_category_ids.split(",")
        arr.map(id => (id, CategoryCountInfo(id, 0, 1, 0)))
      } else if (line.pay_category_ids != "null") {
        // 支付行为
        val arr: Array[String] = line.pay_category_ids.split(",")
        arr.map(id => (id, CategoryCountInfo(id, 0, 0, 1)))
      } else {
        Nil
      }
    })

    //3.3 对每一组数据 按照key进行聚合
    val redRdd: RDD[CategoryCountInfo] = infoRdd.reduceByKey((res, elem) => {
      res.clickCount += elem.clickCount
      res.orderCount += elem.orderCount
      res.payCount += elem.payCount
      res
    }).map(_._2)

    //4. 将聚合后的数据 倒序排序 取前10
    val arrRdd: Array[CategoryCountInfo] = redRdd.sortBy(id => (id.clickCount, id.orderCount, id.payCount), false).take(10)
    arrRdd.foreach(println)
    //TODO 3.关闭连接
    sc.stop()
  }
}

