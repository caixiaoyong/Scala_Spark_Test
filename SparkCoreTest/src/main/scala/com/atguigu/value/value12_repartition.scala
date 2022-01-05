package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/12/9 10:30
 * @description value12_repartition
 */
object value12_repartition {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(Array(1, 2, 3, 4),3)

    rdd.mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)
    println("------------------")

    val repResult: RDD[Int] = rdd.repartition(2)

    repResult.mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
