package com.atguigu.partition

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 分区源码（RDD数据从文件中读取后创建）
 * @author CZY
 * @date 2021/11/26 20:18
 * @description partition04_file
 */
object partition04_file {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //1. 当指定最小分区数时，最终决定分区数 是由mapred代码决定的
    // 计算规则：使用文件的字节数 / 填写的分区数 得到goalSize 之后按照是否超过1.1倍切分
    // 如果剩下的文件长度超过1.1倍 会多出一个分区
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\1.txt",3)//最小分区数

    rdd.saveAsTextFile("D:\\BigData\\SparkCoreTest\\output2")

    // 数据划分到哪个分区 按照goalSize来取索引下标 同一行的数据会自动划分到最先读到的一个分区

    //TODO 3.关闭连接
    sc.stop()
  }
}
