package com.atguigu.keyvalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/29 17:54
 * @description KeyValue10_cogroup
 */
object KeyValue10_cogroup {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3.1 创建RDD
    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((1, "a"), (2, "b"), (3, "c")))
    val rdd1: RDD[(Int, Int)] = sc.makeRDD(Array((1, 4), (2, 5), (4, 6)))

    //3.2 cogroup两个RDD并打印结果
//    (4,(CompactBuffer(),CompactBuffer(6))) (1,(CompactBuffer(a),CompactBuffer(4)))
//    (2,(CompactBuffer(b),CompactBuffer(5))) (3,(CompactBuffer(c),CompactBuffer()))
    rdd.cogroup(rdd1).collect().foreach(println)


    //TODO 3.关闭连接
    sc.stop()
  }
}
