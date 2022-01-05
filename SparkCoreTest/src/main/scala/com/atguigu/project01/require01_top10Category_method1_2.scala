package com.atguigu.project01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 优化版1.0：
 * rdd使用多次，添加cache缓存
 * cogroup全连接会走到shuffle效率低 换成union满外连接-补0
 * @author CZY
 * @date 2021/11/30 20:44
 * @description require01_top10Category_method1
 */
object require01_top10Category_method1_2 {
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

    //问题1:rdd在下面用到了多次,最好缓存一下
    rdd.cache()

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

    //问题2:cogroup()算子底层会走shuffle,最好换掉..
    //5.按照品类进行排序,取热门品类前10名
    //union方式实现满外连接，需要先补0
    // (品类ID,点击数量)  => (品类ID,(点击数量,0,0))
    // (品类ID,下单数量)  => (品类ID,(0,下单数量,0))
    // (品类ID,支付数量)  => (品类ID,(0,0,支付数量))
    // (品类ID,(点击数量,下单数量,支付数量))
    val clickRdd: RDD[(String, (Int, Int, Int))] = clickCountRdd.map {
      case (cid, cnt) => (cid, (cnt, 0, 0))
    }
    val orderRdd: RDD[(String, (Int, Int, Int))] = orderCountRdd.map {
      case (cid, cnt) => (cid, (0, cnt, 0))
    }
    val payRdd: RDD[(String, (Int, Int, Int))] = payCountRdd.map {
      case (cid, cnt) => (cid, (0, 0, cnt))
    }

    //union的方式实现满外连接
    val unionRdd: RDD[(String, (Int, Int, Int))] = clickRdd.union(orderRdd).union(payRdd)
    val unionRdd2: RDD[(String, (Int, Int, Int))] = unionRdd.reduceByKey((tuple1, tuple2) => (tuple1._1 + tuple2._1, tuple1._2 + tuple2._2, tuple1._3 + tuple2._3))


    //倒序排序取前10
    val result: Array[(String, (Int, Int, Int))] = unionRdd2.sortBy(_._2, false).take(10)

    //6.将结果采集到控制到打印输出
    result.foreach(println)
    //TODO 3.关闭连接
    sc.stop()
  }
}
