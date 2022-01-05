package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/27 13:56
 * @description Test08_Filter
 */
object value08_Filter {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5), 2)

    // filter过滤所有的偶数
    // 数据的原有分区同时保持不变
    val value: RDD[Int] = rdd.filter(i => i % 2 == 0)
    rdd.filter(_%2==0)
    value.mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
