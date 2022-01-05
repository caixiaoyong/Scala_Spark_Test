package com.atguigu.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/29 19:32
 * @description action07_aggregate
 */
object action07_aggregate {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建第一个RDD
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 8)

    println(rdd.aggregate(0)(_ + _, _ + _))//10
    println(rdd.aggregate(10)(_ + _, _ + _))//100


    println(rdd.fold(0)(_ + _))//10
    println(rdd.fold(10)(_ + _))//100

    //TODO 3.关闭连接
    sc.stop()
  }
}
