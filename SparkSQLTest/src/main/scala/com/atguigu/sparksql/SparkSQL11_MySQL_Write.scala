package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
 *
 * @author CZY
 * @date 2021/12/3 22:41
 * @description SparkSQL11_MySQL_Write
 */
object SparkSQL11_MySQL_Write {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf配置文件,并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")
    //TODO 2 利用SparkConf创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //读取数据
    val df: DataFrame = spark.read.json("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.json")

    df.show()

    //追加保存到db1数据库下的stu1表中
    df.write.format("jdbc")
        .option("url","jdbc:mysql:///db1")
        .option("driver","com.mysql.jdbc.Driver")
        .option("user","root")
        .option("password","123456")
        .option("dbtable","stu1")
        .mode(SaveMode.Append)
        .save()

    //TODO 3 关闭资源
    spark.stop()
  }
}
