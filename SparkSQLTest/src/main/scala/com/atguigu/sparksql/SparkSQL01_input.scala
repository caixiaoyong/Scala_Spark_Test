package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 *
 * @author CZY
 * @date 2021/12/3 19:05
 * @description SparkSQL01_input
 */
object SparkSQL01_input {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf配置文件，并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")
    //TODO 2 利用SparkConf创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val df: DataFrame = spark.read.json("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.json")
    df.show()

    //sql风格
    df.createTempView("user")
    spark.sql("select * from user where age =18").show()

    println("===================")

    //DSL风格
    df.select("name","age").where("age=18").show()

    //TODO 3 关闭资源
    spark.stop()
  }
}
