package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 *
 * @author CZY
 * @date 2021/12/3 21:33
 * @description SparkSQL10_MySQL_Read
 */
object SparkSQL10_MySQL_Read {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf配置文件,并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")
    //TODO 2 利用SparkConf创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val df: DataFrame = spark.read.format("jdbc")
      .option("url", "jdbc:mysql:///db1")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("user", "root")
      .option("password", "123456")
      .option("dbtable", "stu1")
      .load()
    df.show()

    //SQL风格语法
    df.createTempView("user")
    spark.sql("select name,age from user where id=1").show()
    //TODO 3 关闭资源
    spark.stop()
  }
}
