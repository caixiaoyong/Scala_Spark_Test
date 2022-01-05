package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * map()映射
 * @author CZY
 * @date 2021/11/26 21:12
 * @description value01_map
 */
object value01_map {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(1 to 4,2)

    //Map算子 rdd.map((i:Int)=>i*2)
    val maprdd: RDD[Int] = rdd.map(_ * 2)

    maprdd.collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
