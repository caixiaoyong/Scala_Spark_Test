package com.atguigu.keyvalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/28 22:47
 * @description Test04_AggregateByKey
 */
object KeyValue04_AggregateByKey {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建第一个RDD
    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a",1),("a",3),("a",5),("b",7),("b",2),("b",4),("b",6),("a",7)), 2)

    //3.2 取出每个分区相同key对应值的最大值，然后相加
    rdd.aggregateByKey(0)(math.max(_,_),_+_).mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)
    // 化简形式
    rdd.aggregateByKey(0)(math.max,_+_)

    println("========================")
    val rdd1: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("d", 4)), 8)
    rdd1.aggregateByKey(10)(_+_,_+_).collect().foreach(println)


    //TODO 3.关闭连接
    sc.stop()
  }

}
