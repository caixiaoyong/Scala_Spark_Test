package com.atguigu.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/29 20:03
 * @description action11_foreach
 */
object action11_foreach {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建一个RDD
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),1)

    //3.2 收集后打印
    rdd.collect().foreach(println)//1 2 3 4
    println("===================")

    //3.3 分布式打印
    rdd.foreach(println)// 3 4 1 2
    println("===================")
    rdd1.foreach(println)//1 2 3 4


    //TODO 3.关闭连接
    sc.stop()
  }
}
