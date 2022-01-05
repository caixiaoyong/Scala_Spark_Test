package com.atguigu.createrdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 从外部存储系统的数据集创建
 * @author CZY
 * @date 2021/11/26 18:26
 * @description createrdd02_file
 */
object createrdd02_file {
  def main(args: Array[String]): Unit = {
    //TODO 1. 创建spark配置对象
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2. 创建sc对象，该对象是提交Spark App的入口
    val sc = new SparkContext(conf)

    //1. 从hdfs上读取文件
    val rdd: RDD[String] = sc.textFile("hdfs://hadoop102:8020/input")
    rdd.collect().foreach(println)
    println("=========")

    //2. 从指定路径上读取
    val rdd2: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\1.txt")
    rdd2.collect().foreach(println)
    println("=========")

    //3. 从其他RDD创建
    val rdd3: RDD[String] = rdd2.flatMap(_.split(" "))
    rdd3.saveAsTextFile("D:\\BigData\\SparkCoreTest\\output")

    //TODO 3. 关闭资源
    sc.stop()
  }
}
