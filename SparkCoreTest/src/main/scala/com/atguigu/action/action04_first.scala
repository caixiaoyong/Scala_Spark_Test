package com.atguigu.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/29 19:15
 * @description action04_first
 */
object action04_first {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)
    //3.1 创建第一个RDD
    val rdd: RDD[Int] = sc.makeRDD(List(1,3,2,4))

    //3.2 返回RDD中元素的个数
    val firstResult: Int = rdd.first()
    val takeResult: Array[Int] = rdd.take(2)

    println(firstResult)
    println(takeResult)//这是个集合，返回地址值
    println(takeResult.mkString(","))
    val str: String = takeResult.mkString(",")

    //takeOrdered()返回该RDD排序后前n个元素组成的数组
    val result: Array[Int] = rdd.takeOrdered(2)
    println(result.mkString(","))

    //TODO 3.关闭连接
    sc.stop()
  }
}
