package com.atguigu.dependency

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * rdd依赖关系
 * @author CZY
 * @date 2021/11/29 22:25
 * @description Lineage03
 */
object Lineage03 {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //sc对应一个Application
    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //3个窄依赖算子
    //从文件读取创建RDD
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\1.txt")

    //分割空行
    val flatMapRdd: RDD[String] = rdd.flatMap(_.split(" "))

    //使用偏函数：(String)=>(String,1)
    val mapRdd: RDD[(String, Int)] = flatMapRdd.map {
      case (string: String) => (string, 1)
    }

    //2个job ，两个reduceByKey 但只走一个 每个job对应 1个shuffle 所以有 4个stage

    //通过coalesce来缩减分区，减少shuffle占用资源
    val value1: RDD[(String, Int)] = mapRdd.coalesce(1)

    //按相同key，求value和 reduceByKey算子为宽依赖，1个shuffle=2个stage
    val reduceRdd: RDD[(String, Int)] = value1.reduceByKey(_ + _)

    // reduceByKey不是一定走shuffle，会判断和上一个rdd的分区器是否一样，如果一样就不走shuffle
    val value: RDD[(String, Int)] = mapRdd.reduceByKey(_ + _)

    //2个action算子=2个job
    //job0打印到控制台
    reduceRdd.collect().foreach(println)

    //job1输出到磁盘
    reduceRdd.saveAsTextFile("D:\\BigData\\SparkCoreTest\\output")

    //阻塞线程,方便进入localhost:4040查看
    Thread.sleep(Long.MaxValue)


    //TODO 3.关闭连接
    sc.stop()
  }
}
