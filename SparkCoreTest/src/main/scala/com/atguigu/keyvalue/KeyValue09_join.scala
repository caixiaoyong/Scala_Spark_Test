package com.atguigu.keyvalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/29 16:50
 * @description KeyValue09_join
 */
object KeyValue09_join {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建RDD
    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((1, "a"), (2, "b"), (3, "c"),(4,"a")))
    val rdd1: RDD[(Int, Int)] = sc.makeRDD(Array((1, 4), (2, 5), (4, 6)))

    //3.2 join操作并打印结果
    rdd.join(rdd1).collect().foreach(println)//(4,(a,6)) (1,(a,4)) (2,(b,5))

    //TODO 3.关闭连接
    sc.stop()
  }
}
