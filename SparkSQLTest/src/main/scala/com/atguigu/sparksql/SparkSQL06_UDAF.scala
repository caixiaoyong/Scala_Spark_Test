package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{DataFrame, Encoder, Encoders, SparkSession, functions}

/**
 *
 * @author CZY
 * @date 2021/12/3 20:40
 * @description SparkSQL06_UDAF
 */
object SparkSQL06_UDAF {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf配置文件,并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")
    //TODO 2 利用SparkConf创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    // 1 读取数据
    val df: DataFrame = spark.read.json("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.json")

    // 2 创建DF临时视图
    df.createOrReplaceTempView("user")

    // 3 注册自定义UDAF
    spark.udf.register("myAvg",functions.udaf(new MyAvgUDAF))

    //sql风格
    spark.sql("select myAvg(age) from user").show()

    //TODO 3 关闭资源
    spark.stop()
  }
}

case class Buff(var sum: Long, var count: Long)

/**
 * 自定义UDAF
 * 输入: Long
 * 缓存区: Buff
 * 输出: Double
 */
class MyAvgUDAF extends Aggregator[Long, Buff, Double] {

  //buff初始化方法
  override def zero: Buff = Buff(0L, 0L)

  //单个分区,分区内的聚合方法 将输入的年龄和缓冲区的数据进行聚合
  override def reduce(buff: Buff, age: Long): Buff = {
    buff.sum += age
    buff.count += 1
    buff
  }

  //多个分区,分区间的聚合方法
  override def merge(b1: Buff, b2: Buff): Buff = {
    b1.sum += b2.sum
    b1.count += b2.count
    b1
  }

  //逻辑处理方法
  override def finish(reduction: Buff): Double = {
    reduction.sum.toDouble/reduction.count
  }

  //buff的序列化方法  自定义类型就是product   自带类型根据类型选择
  override def bufferEncoder: Encoder[Buff] = Encoders.product

  //返回值的序列化方法
  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}