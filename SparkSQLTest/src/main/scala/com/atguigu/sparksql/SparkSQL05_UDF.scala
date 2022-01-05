package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 *
 * @author CZY
 * @date 2021/12/3 20:32
 * @description SparkSQL05_UDF
 */
object SparkSQL05_UDF {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf配置文件,并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")
    //TODO 2 利用SparkConf创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    // 1 读取数据
    val df: DataFrame = spark.read.json("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.json")

    // 2 创建DF临时视图
    df.createOrReplaceTempView("user")

    //3. 自定义UDF
    spark.udf.register("addName",(name:String)=>{"Name:"+name})
    spark.udf.register("doubleAge",(age:Long)=>{age*2})

    //sql风格
    spark.sql("select addName(name),doubleAge(age) from user").show()

    //TODO 3 关闭资源
    spark.stop()
  }
}
