package com.atguigu.createrdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 从集合中创建
 * @author CZY
 * @date 2021/11/26 16:29
 * @description createrdd01_array
 */
object createrdd01_array {
  def main(args: Array[String]): Unit = {
    // 1.配置spark配置文件
    val conf = new SparkConf().setAppName("SparkCoreTest01").setMaster("local[*]")
    // 2.创建sc对象，该对象是提交Spark App的入口
    val sc = new SparkContext(conf)

    // 4.使用parallelize创建rdd
    val rdd: RDD[Int] = sc.parallelize(Array(1, 2, 3, 4, 5, 6, 7, 8))
    rdd.collect().foreach(println)

    println("===============")
    // 5.使用makeRDD创建rdd
    val rdd1: RDD[Int] = sc.makeRDD(Array(1, 2, 3, 4))
    rdd1.collect().foreach(println)

    // 3.关闭资源
    sc.stop()
  }
}
