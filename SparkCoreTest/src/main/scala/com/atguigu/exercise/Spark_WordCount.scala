package com.atguigu.exercise

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/28 23:39
 * @description WordCount1
 */
object Spark_WordCount {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //wordcount1(sc)
    //wordcount2(sc)
    wordcount3(sc)
    //TODO 3.关闭连接
    sc.stop()
  }

  //    1.读取文件，利用groupByKey和groupBy算子实现WordCount功能。
  def wordcount1(sc: SparkContext) = {
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\1.txt")
    val word: RDD[String] = rdd.flatMap(_.split(" "))
    val word2One: RDD[(String, Int)] = word.map((_, 1))
    val group: RDD[(String, Iterable[Int])] = word2One.groupByKey()
    group.mapValues(iter => iter.size).collect().foreach(println)

    println("==============")
    val group2: RDD[(String, Iterable[String])] = word.groupBy(word => word)
    group2.mapValues(_.size).collect().foreach(println)
  }

  //    2.读取文件，利用foldByKey和aggregateByKey算子实现WordCount功能。
  def wordcount2(sc: SparkContext) = {
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\1.txt")
    val word: RDD[String] = rdd.flatMap(_.split(" "))
    val word2One: RDD[(String, Int)] = word.map((_, 1))
    word2One.foldByKey(0)(_ + _).collect().foreach(println)

    println("==============")
    word2One.aggregateByKey(0)(_ + _, _ + _).collect().foreach(println)
  }

  //    3.读取文件，利用combineByKey算子实现WordCount功能。
  def wordcount3(sc: SparkContext) = {
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\1.txt")
    val word: RDD[String] = rdd.flatMap(_.split(" "))
    val word2One: RDD[(String, Int)] = word.map((_, 1))
    word2One.collect().foreach(println)
    println("==========")
//    word2One.combineByKey(
//      v => v,
//      (acc: (String, Int), v) => (acc._2 + v),
//      (acc1: (String, Int), acc2: (String, Int)) => (acc1._2 + acc2._2)
//    )
    word2One.combineByKey(
      v => v,
      (x: Int, y) => x + y,
      (x: Int, y: Int) => x + y
    ).collect().foreach(println)

  }
}



