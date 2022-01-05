package com.atguigu.project01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 优化版2.0：
 * 转换数据结构，提前补0，减少reduceByKey的次数
 * @author CZY
 * @date 2021/11/30 20:44
 * @description require01_top10Category_method1
 */
object require01_top10Category_method1_3 {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    // 需求最后结果(品类ID，(点击总数，下单总数，支付总数))
    //    （品类，点击总数）（品类，下单总数）（品类，支付总数）
    //    （品类，（点击总数，下单总数，支付总数））
    //     倒序


    //1. 从文件读取创建RDD
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\user_visit_action.txt")
//    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\userTest")

    //问题1:rdd在下面用到了多次,最好缓存一下
    rdd.cache()


    //TODO 3.关闭连接
    sc.stop()
  }
}
