package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/28 19:56
 * @description value10_distinct
 */
object value10_distinct {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    // 3.1 创建一个RDD
    val distinctRdd: RDD[Int] = sc.makeRDD(List(1, 2, 1, 5, 2, 9, 6, 1))

    // 3.2 打印去重后生成的新RDD
    distinctRdd.distinct().collect().foreach(println)

    println("===============")
    // 3.3 对RDD采用多个Task去重，提高并发度
    distinctRdd.distinct(2).collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
