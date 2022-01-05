package com.atguigu.doublevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/28 21:20
 * @description DoubleValue01_intersection
 */
object Test01_Intersection {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf配置文件并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")
    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc = new SparkContext(conf)

    //创建RDD
    val rdd1: RDD[Int] = sc.makeRDD(1 to 4)
    val rdd2: RDD[Int] = sc.makeRDD(4 to 8)

    // 4.intersection()交集
    rdd1.intersection(rdd2).collect().foreach(println)//4
    println("=============")
    rdd2.intersection(rdd1).collect().foreach(println)//4

    println("=============")
    // 5. union()并集不去重
    rdd1.union(rdd2).collect().foreach(print)//123445678

    println("=============")
    // 6.subtract()差集
    rdd1.subtract(rdd2).collect().foreach(print)//123
    println("=============")
    rdd2.subtract(rdd1).collect().foreach(print)//8567
    //TODO 3.关闭资源
    sc.stop()
  }
}
