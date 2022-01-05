package com.atguigu.keyvalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/27 16:03
 * @description KeyValue03_groupByKey
 */
object KeyValue03_groupByKey {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)


    val rdd = sc.makeRDD(List(("a",1),("b",5),("a",5),("b",2)))
    val group: RDD[(String, Iterable[Int])] = rdd.groupByKey()
    group.mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)//(1,(a,CompactBuffer(1, 5))) (2,(b,CompactBuffer(5, 2)))
    println("========groupBy:=========")
    val group1: RDD[(String, Iterable[(String, Int)])] = rdd.groupBy(_._1)
    group1.mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)//(1,(a,CompactBuffer((a,1), (a,5)))) (2,(b,CompactBuffer((b,5), (b,2))))
    //TODO 3.关闭连接
    sc.stop()
  }
}
