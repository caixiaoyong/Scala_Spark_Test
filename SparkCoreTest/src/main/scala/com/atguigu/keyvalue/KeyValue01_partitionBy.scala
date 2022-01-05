package com.atguigu.keyvalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/27 14:47
 * @description KeyValue01_partitionBy
 */
object KeyValue01_partitionBy {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)


    val rdd: RDD[(Int, String)] = sc.makeRDD(Array((1, "aaa"), (2, "bbb"), (3, "ccc")), 3)

    //1. 使用HashPartitioner进行分区
    val rdd2: RDD[(Int, String)] = rdd.partitionBy(new HashPartitioner(2))

    rdd2.mapPartitionsWithIndex((index, iter) => iter.map((index, _))).collect().foreach(println)


    val rdd3: RDD[(Int, String)] = rdd.partitionBy(new MyPartitionBy(2))

    //TODO 3.关闭连接
    sc.stop()
  }
}

class MyPartitionBy(num: Int) extends Partitioner {
  override def numPartitions: Int = num

  override def getPartition(key: Any): Int = {
    key match {
      case keyInt: Int =>
        if (keyInt % 2 == 0) 0 else 1
      case _ => 0
    }
  }
}