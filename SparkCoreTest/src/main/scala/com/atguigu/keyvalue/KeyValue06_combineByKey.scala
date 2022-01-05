package com.atguigu.keyvalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/28 23:14
 * @description KeyValue06_combineByKey
 */
object KeyValue06_combineByKey {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建第一个RDD
    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)), 2)

    //3.2 将相同key对应的值相加，同时记录该key出现的次数，放入一个二元组
    val value: RDD[(String, (Int, Int))] = rdd.combineByKey(
      (_, 1),
      (acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1), //泛型擦除，需要写全类型
      (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2)
    )
      value.mapPartitionsWithIndex((index, iter) => iter.map((index, _))).collect().foreach(println) //(0,(b,(286,3))) (1,(a,(274,3)))

    //3.3 计算平均值--这里使用偏函数
    value.map{
      case (key,value)=>{
        (key,value._1.toDouble/value._2)
      }
    }.collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
