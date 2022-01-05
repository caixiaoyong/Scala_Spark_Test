package com.atguigu.doublevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/28 21:28
 * @description Test02_Zip
 */
object Test02_Zip {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建RDD
    val rdd1: RDD[Int] = sc.makeRDD(Array(1, 2, 3), 3)
    val rdd2: RDD[String] = sc.makeRDD(Array("a", "b", "c"), 3)
    val rdd3: RDD[String] = sc.makeRDD(Array("a", "b"), 3)
    val rdd4: RDD[String] = sc.makeRDD(Array("a","b","c"), 2)

    //3.2 元素个数相同，分区数相同的RDD组合RDD并打印
    rdd1.zip(rdd2).collect().foreach(println)
    rdd2.zip(rdd1).collect().foreach(println)

    //3.3 元素个数不同，分区数不同，不能拉链
//    rdd1.zip(rdd3).collect().foreach(println)
//    rdd1.zip(rdd4).collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
