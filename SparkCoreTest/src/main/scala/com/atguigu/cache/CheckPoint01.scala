package com.atguigu.cache

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 持久化CheckPoint检查点
 * @author CZY
 * @date 2021/11/30 19:35
 * @description CheckPoint01
 */
object CheckPoint01 {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    // 使用检查点必须设置存储数据的文件夹 数据会以序列化的形式保存在文件中
    sc.setCheckpointDir("D:\\BigData\\SparkCoreTest\\ck")

    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\1.txt")

    val wordRdd: RDD[String] = rdd.flatMap(_.split(" "))

    val tupleRdd1: RDD[(String, Int)] = wordRdd.map((_, 1))

    val tupleRdd: RDD[String] = wordRdd.map(tuple => {
      println("************"+System.currentTimeMillis())
      tuple
    })

    tupleRdd.checkpoint()

    tupleRdd.collect().foreach(println)

    //复用的时候，打印的数据上述CheckPoint保存的数据
    tupleRdd.collect().foreach(println)

    Thread.sleep(Long.MaxValue)
    //TODO 3.关闭连接
    sc.stop()
  }
}
