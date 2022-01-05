package com.atguigu.project01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/30 20:44
 * @description require01_top10Category_method1
 */
object require01_top10Category_method1 {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    // 需求最后结果(品类ID，(点击总数，下单总数，支付总数))
    //    （品类，点击总数）（品类，下单总数）（品类，支付总数）
    //    （品类，（点击总数，下单总数，支付总数））
    //     倒序


    //1. 从文件读取创建RDD
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\user_visit_action.txt")
//    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\userTest")

    //2.统计品类的点击数量 (品类ID,点击数量)
    val clickActionRDD: RDD[String] = rdd.filter(line => {
      val strings: Array[String] = line.split("_")
      strings(6) != "-1"
    })
    val clickCountRdd: RDD[(String, Int)] = clickActionRDD.map(line => {
      val strings: Array[String] = line.split("_")
      (strings(6), 1)
    }).reduceByKey(_ + _)

    //3.统计品类的下单数量 (品类ID,下单数量)
    val orderActionRdd: RDD[String] = rdd.filter(line => {
      val action: Array[String] = line.split("_")
      action(8) != "null"
    })
    val orderCountRdd: RDD[(String, Int)] = orderActionRdd.flatMap(line => {
      val action: Array[String] = line.split("_")
      val strings: Array[String] = action(8).split(",")
      strings.map((_, 1))
    }).reduceByKey(_ + _)

    //4.统计品类的支付数量 (品类ID,支付数量)
    val payActionRdd: RDD[String] = rdd.filter(line => {
      val action: Array[String] = line.split("_")
      action(10) != "null"
    })
    val payCountRdd: RDD[(String, Int)] = payActionRdd.flatMap(line => {
      val action: Array[String] = line.split("_")
      val strings: Array[String] = action(10).split(",")
      strings.map((_, 1))
    }).reduceByKey(_ + _)

    //5.按照品类进行排序,取热门品类前10名
    //热门排名:先按照点击排,然后按照下单数量排,最后按照支付数量排
    //元组排序:先比较第一个,再比较第二个,最后比较第三个
    //(品类ID,(点击数量,下单数量,支付数量))
    val cogroupRdd: RDD[(String, (Iterable[Int], Iterable[Int], Iterable[Int]))] = clickCountRdd.cogroup(orderCountRdd, payCountRdd)

    val cogroupRdd2: RDD[(String, (Int, Int, Int))] = cogroupRdd.mapValues {
      case (iter1, iter2, iter3) => {
/*        var clickCnt = 0
        val clickIter: Iterator[Int] = iter1.iterator
        if (clickIter.hasNext) {
          clickCnt = clickIter.next()
        }

        var orderCnt = 0
        val orderIter: Iterator[Int] = iter2.iterator
        if (orderIter.hasNext) {
          orderCnt = orderIter.next()
        }

        var payCnt = 0
        val payIter: Iterator[Int] = iter3.iterator
        if (payIter.hasNext) {
          payCnt = payIter.next()
        }
        (clickCnt, orderCnt, payCnt)*/
        (iter1.sum,iter2.sum,iter3.sum)
      }
    }

    //倒序排序取前10
    val result: Array[(String, (Int, Int, Int))] = cogroupRdd2.sortBy(_._2, false).take(10)

    //6.将结果采集到控制到打印输出
    result.foreach(println)
    //TODO 3.关闭连接
    sc.stop()
  }
}
