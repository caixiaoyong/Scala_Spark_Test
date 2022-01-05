package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
 *
 * @author CZY
 * @date 2021/12/3 21:14
 * @description SparkSQL09_Save
 */
object SparkSQL09_Save {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf配置文件,并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")
    //TODO 2 利用SparkConf创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //读取数据
    val df: DataFrame = spark.read.json("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.json")

    //保存数据
    //1. 特定保存
    //    df.write.json("D:\BigData\Scala_SparkTest\SparkSQLTest\output")
    //    df.write.csv("D:\BigData\Scala_SparkTest\SparkSQLTest\output")

    //2. 默认保存为parquet文件 可以修改conf.set("spark.sql.sources.default","json")
    //    df.write.save("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\output")
    //3. 指定方式保存
    //    df.write.format("json").save("D:\BigData\Scala_SparkTest\SparkSQLTest\output")

    /**
     * 四种模式总结
     * 1.默认模式(存在即报错模式) 如果路径不存在,正常写入;如果路径存在,报错
     * 2.追加模式               如果路径不存在,正常写入;如果路径存在,追加写入
     * 3.覆盖模式               如果路径不存在,正常写入;如果路径存在,删除该路径和路径下的所有数据,创建一个同名路径,写入
     * 4.忽略模式               如果路径不存在,正常写入;如果路径存在,忽略本次操作,不报错
     */
    //4. 指定模式
    df.write.mode(SaveMode.Ignore).save("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\output")
    //5. 指定模式、格式保存
    df.write.mode("append").json("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\output")


    //TODO 3 关闭资源
    spark.stop()
  }
}
