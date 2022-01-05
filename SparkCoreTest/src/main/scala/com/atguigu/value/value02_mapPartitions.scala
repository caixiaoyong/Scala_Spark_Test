package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * mapPartitions()以分区为单位执行Map
 * @author CZY
 * @date 2021/11/26 21:17
 * @description value02_mapPartitions
 */
object value02_mapPartitions {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(1 to 4)

    //mapPartitions是算子--点进去是RDD类，里面map的是集合函数--点进去是scala类
    val rdd1: RDD[Int] = rdd.mapPartitions(x => x.map(i=>i * 2))
    rdd.mapPartitions(_.map(_ * 2))

    rdd1.collect().foreach(println)
    //TODO 3.关闭连接
    sc.stop()
  }
}
