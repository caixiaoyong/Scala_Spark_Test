package com.atguigu.value

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/27 10:14
 * @description value07_groupby_wordcount
 */
object value07_groupby_wordcount {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //1、 创建RDD
    val strList: List[String] = List("Hello Scala", "Hello Spark", "Hello World")
    val rdd: RDD[String] = sc.makeRDD(strList)

    //2、将字符串拆分成一个一个的单词
    val wordRdd: RDD[String] = rdd.flatMap(_.split(" "))

    //方式一： 使用reduceByKey方法
    val word2oneRdd: RDD[(String, Int)] = wordRdd.map((_, 1))
    val value: RDD[(String, Int)] = word2oneRdd.reduceByKey(_ + _)
    value.collect().foreach(println)
    println("==============")


    //方式二：使用wordCount老方式--使用groupBy聚合
    //groupBy的性能对比reduceByKey会差非常多 因为没有预聚合
    val value1: RDD[(String, Iterable[String])] = wordRdd.groupBy(s => s)
    //1. 使用mapValues简化
    val result: RDD[(String, Int)] = value1.mapValues(_.size)

    //2. 使用map对比
    //2.1 常规的匿名函数写法
    value1.map(tuple=>(tuple._1,tuple._2.size))

    //2.2 匿名函数模式匹配的写法
    value1.map(tuple=>tuple match {
      case (k,v)=>(k,v.size)
    })

    //2.3 偏函数的写法  能够省略外部的小括号  大括号表示偏函数  不能省略
    value1.map{
      case (k,v)=>(k,v.size)
    }

    result.collect().foreach(println)




    //TODO 3.关闭连接
    sc.stop()
  }
}
