package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * glom()分区转换数组--数组是存在内存当中的，容易出现内存溢出，一般在海量数据使用到的很少
 * @author CZY
 * @date 2021/11/27 9:31
 * @description value05_glom
 */
object value05_glom {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5), 2)
//    val rdd: RDD[Int] = sc.makeRDD(1 to 4, 2)

    rdd.glom().map(_.toList).mapPartitionsWithIndex((index,iter)=>iter.map((index,_))).collect().foreach(println)

    // 使用glom将文件中一行一行的sql整合在一起
    val rdd1: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\2.txt", 1)
    // TODO 打印数组方法一
    rdd1.glom().map(_.toList).collect().foreach(println)
    // TODO 打印数组方法二
    rdd1.glom().map(_.toList.mkString("\n")).collect().foreach(println)
    //TODO 3.关闭连接
    sc.stop()
  }

}
