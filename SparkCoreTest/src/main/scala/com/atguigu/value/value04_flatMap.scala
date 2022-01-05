package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/27 0:20
 * @description value04_flatMap
 */
object value04_flatMap {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[List[Int]] = sc.makeRDD(List(List(1,2,3,4),List(3, 4, 5) ), 2)

    val result: RDD[Int] = rdd.flatMap(list => list)

    result.collect().foreach(println)
    // flatmap不改变原有分区
//    result.mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)
    //TODO 3.关闭连接
    sc.stop()
  }
}
