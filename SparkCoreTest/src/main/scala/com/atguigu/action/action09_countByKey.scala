package com.atguigu.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/29 19:48
 * @description action09_countByKey
 */
object action09_countByKey {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建第一个RDD
    val rdd: RDD[(Int, String)] = sc.makeRDD(List((1, "a"), (1, "a"), (1, "a"), (2, "b"), (3, "c"), (3, "c")))

    //3.2 统计每种key的个数
    val result: collection.Map[(Int, String), Long] = rdd.countByValue()
    println(result)//Map((3,c) -> 2, (1,a) -> 3, (2,b) -> 1)

    //TODO 3.关闭连接
    sc.stop()
  }
}
