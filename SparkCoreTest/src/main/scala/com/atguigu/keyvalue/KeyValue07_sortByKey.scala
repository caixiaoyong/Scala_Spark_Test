package com.atguigu.keyvalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/29 16:38
 * @description KeyValue07_sortByKey
 */
object KeyValue07_sortByKey {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建第一个RDD
    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((3, "aa"), (6, "cc"), (2, "bb"), (1, "dd")))

    //3.2 按照key的正序（默认顺序）
    rdd.sortByKey(true).collect().foreach(println)

    //3.3 按照key的倒序
    rdd.sortByKey(false).collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
