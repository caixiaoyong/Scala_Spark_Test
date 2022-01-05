package com.atguigu.keyvalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/28 22:56
 * @description KeyValue05_FoldByKey
 */
object KeyValue05_FoldByKey {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建第一个RDD
    val rdd: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("a", 3), ("a", 5), ("b", 7), ("b", 2), ("b", 4), ("b", 6), ("a", 7)), 2)

    //3.2.1 求初始值为10的key的和
    rdd.foldByKey(10)((res,elem)=>res+elem).mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)
    // 化简形式
    rdd.foldByKey(10)(_+_)

    println("============")
    //3.2.2 求相同key的value和
//    rdd.aggregateByKey(0)(_+_,_+_).collect().foreach(println)
    rdd.foldByKey(0)(_+_).mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)

    println("============")
    //3.2.2 求相同key的最大值
    rdd.foldByKey(0)(math.max).collect().foreach(println)



    //TODO 3.关闭连接
    sc.stop()
  }
}
