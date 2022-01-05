package com.atguigu.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/26 9:22
 * @description WordCount
 */
object WordCount {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建Spark配置文件并设置App名称
    val conf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    //TODO 2 创建sc对象，该对象是提交Spark App的入口
    val sc = new SparkContext(conf)

    //1.读取指定位置文件:hello atguigu atguigu
    val lineRdd: RDD[String] = sc.textFile("D:\\BigData\\WordCount\\input\\1.txt")

    val wordRdd: RDD[String] = lineRdd.flatMap(_.split(" "))

    val word2OneRDD: RDD[(String, Int)] = wordRdd.map((_, 1))

    val word2Sum: RDD[(String, Int)] = word2OneRDD.reduceByKey(_ + _)

    val word2Result: Array[(String, Int)] = word2Sum.collect()

    word2Result.foreach(println)
    //TODO 3 关闭资源
    sc.stop()

  }
}
