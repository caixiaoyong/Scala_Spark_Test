package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/27 9:33
 * @description value06_groupby
 */
object value06_groupby {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //1. 按照奇偶数分区
    val rdd: RDD[Int] = sc.makeRDD(Array(1, 2, 3, 4))

    val rdd2: RDD[(Int, Iterable[Int])] = rdd.groupBy(_ % 2)

    rdd2.collect().foreach(println)

    //2. 按照首字母分区
    val rdd1: RDD[String] = sc.makeRDD(List("hello","hive","hadoop","spark","scala"))
    rdd1.groupBy(_.charAt(0),2).mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }

}
