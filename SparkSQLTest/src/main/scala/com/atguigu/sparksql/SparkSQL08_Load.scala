package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 *
 * @author CZY
 * @date 2021/12/3 21:04
 * @description SparkSQL08_Load
 */
object SparkSQL08_Load {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf配置文件,并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")

    //2.2 在conf调用之前可以修改
    //    conf.set("spark.sql.sources.default","json")

    //TODO 2 利用SparkConf创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //1.读取特定格式的数据
    val df: DataFrame = spark.read.json("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.json")
    df.show()
    val df1: DataFrame = spark.read.csv("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.json")
    df1.show()


    //2.1 通用格式加载 默认只能加载parquet格式 spark.sql.sources.default	= parquet
    //val df3: DataFrame = spark.read.load("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.json")

    //3.format指定加载数据类型
    val df3: DataFrame = spark.read.format("json").load("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.json")
    df3.show()

    //TODO 3 关闭资源
    spark.stop()
  }
}
