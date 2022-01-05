package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/26 23:37
 * @description value03_mapPartitionsWithIndex
 */
object value03_mapPartitionsWithIndex {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(1 to 4, 2)

    // 转换为(分区号，2*int)
    val rdd2: RDD[(Int, Int)] = rdd.mapPartitionsWithIndex((index, iter) => iter.map(i => (index, i*2)))

    rdd2.collect().foreach(println)

    println("============")

    // 将rdd中的元素带分区号打印
    rdd.mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
