package com.atguigu.partition

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 默认分区源码（RDD数据从集合中创建）
 * @author CZY
 * @date 2021/11/26 19:17
 * @description Partition01_Array_default
 */
object Partition01_Array_default {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //1. 从集合中创建
    val rdd: RDD[Int] = sc.makeRDD(Array(1,2,3,4,5,6,7,8))

    //2. 输出数据，产生了4个分区--默认分区数跟本地模式的cpu核数有关
    rdd.saveAsTextFile("D:\\BigData\\SparkCoreTest\\output2")

    //TODO 3.关闭连接
    sc.stop()

  }
}
