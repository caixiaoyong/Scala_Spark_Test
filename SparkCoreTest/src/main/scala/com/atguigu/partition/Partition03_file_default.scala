package com.atguigu.partition

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 默认分区源码（RDD数据从文件中读取后创建）
 * @author CZY
 * @date 2021/11/26 20:10
 * @description Partition03_file_default
 */
object Partition03_file_default {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //1. 默认分区的数量：默认取值为当前核数和2的最小值,一般为2
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\1.txt")

    rdd.saveAsTextFile("D:\\BigData\\SparkCoreTest\\output1")

    //TODO 3.关闭连接
    sc.stop()
  }
}
