package com.atguigu.cache

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/30 11:09
 * @description cache01
 */
object cache02 {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)


    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\1.txt")

    val wordRdd: RDD[String] = rdd.flatMap(_.split(" "))

    val result: RDD[(String, Int)] = wordRdd.map {
      word => {
        println("***************")
        (word, 1)
      }
    }
    println(result.toDebugString)
    println("=====================")

    //将重复的计算结果缓存，避免每个job重复计算
    result.cache()

    // 缓存等级 使用persist可以手动修改
    //result.persist(StorageLevel.DISK_ONLY)

    result.collect().foreach(println)
    result.collect().foreach(println)

    //缓存不改变rdd原有的血缘关系 只会多记录一条缓存的信息
    println(result.toDebugString)
    println("=====================")

    //TODO 3.关闭连接
    sc.stop()
  }
}
