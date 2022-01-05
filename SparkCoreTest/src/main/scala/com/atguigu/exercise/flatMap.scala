package com.atguigu.exercise

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/30 21:30
 * @description flatMap
 */
object flatMap {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //    val array: Array[String] = Array("15, 13, 5, 11, 8")
    //    val rdd: RDD[String] = sc.makeRDD(array)
    //    val fm: RDD[String] = rdd.flatMap(line => line.split(","))
    //    val fm1: RDD[(String, Int)] = fm.map((s => (s, 1)))
    //    fm1.reduceByKey(_+_).collect().foreach(println)
    //    //fm.collect().foreach(println)
    //    println("-----------------")
    //    rdd.map(_.split(",")).map(s=>(s.toList,1)).collect().foreach(println)

    //    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5), 2)
    //    rdd.foreach(println)
    val ints = List(1, 2, 3, 4, 5)
    val ints2 = List(1, 2, 3, 5, 6, 7, 8, 9, 5)
    println(ints2.filter(x => ints.contains(x)))//在ints2中过滤ints中含有的数据

    println(ints.intersect(ints2))

    //TODO 3.关闭连接
    sc.stop()
  }
}
