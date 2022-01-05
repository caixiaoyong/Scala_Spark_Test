package com.atguigu.keyvalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/29 9:25
 * @description KeyValue08_mapValues
 */
object KeyValue08_mapValues {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建第一个RDD
    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((1, "a"), (1, "d"), (2, "b"), (3, "c")))

    rdd.mapValues(str=>str*2).collect().foreach(println)
    println("==================")
    //2.1 使用map偏函数
    rdd.map{
      case (k,v)=>(k,v*2)
    }.collect().foreach(println)
    println("==================")
    //2.2 使用map匿名函数
    rdd.map(i=>(i._1,i._2*2)).collect().foreach(println)

    //TODO 3.关闭连接
    sc.stop()
  }
}
