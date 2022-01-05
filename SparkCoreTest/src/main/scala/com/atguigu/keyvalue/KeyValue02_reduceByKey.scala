package com.atguigu.keyvalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/27 15:50
 * @description KeyValue02_reduceByKey
 */
object KeyValue02_reduceByKey {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建第一个RDD
    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 5), ("a", 5), ("b", 2)))

    //3.2 计算相同key对应值的相加结果
    val reduce: RDD[(String, Int)] = rdd.reduceByKey((v1, v2) => v1 + v2)

    //化简形式
    rdd.reduceByKey(_+_)
    reduce.mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
