package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/12/9 10:20
 * @description value11_coalesce
 */
object value11_coalesce {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(Array(1, 2, 3, 4,5,6),3)

    //(0,1) (0,2) (1,3) (1,4) (1,5) (1,6) 默认不执行Shuffle
    val coalescRdd: RDD[Int] = rdd.coalesce(2)

    //(0,1) (0,4) (0,5) (1,2) (1,3) (1,6) 执行Shuffle
    val coalescRdd1: RDD[Int] = rdd.coalesce(2,true)

    coalescRdd. mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)
    println("--------------------")
    coalescRdd1. mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
