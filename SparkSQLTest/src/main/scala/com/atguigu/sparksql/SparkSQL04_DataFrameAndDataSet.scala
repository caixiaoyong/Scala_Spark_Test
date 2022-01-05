package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
 *
 * @author CZY
 * @date 2021/12/3 20:11
 * @description SparkSQL04_DataFrameAndDataSet
 */
object SparkSQL04_DataFrameAndDataSet {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf配置文件,并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")
    //TODO 2 利用SparkConf创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    // 1 读取数据
    val df: DataFrame = spark.read.json("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.json")
    df.show()
    //导入隐式转换
    import spark.implicits._

    // df中列名顺序为最终输出的列名顺序
    //假如df中列名和样例类列名不一致 样例类的又不好改动 这时可以使用df.toDF()的方法将df中列名改为和样例类一致即可
    val df1: DataFrame = df.toDF("age1","name1")

    //TODO DF=>DS
    //文件中列名和样例类的列名必须一致 样例类中的列名顺序无所谓 是按df中列名顺序来的
    val ds: Dataset[User] = df1.as[User]
    ds.show()

    //TODO DS=>DF
    val df2: DataFrame = ds.toDF()
    df2.show()

    //TODO 3 关闭资源
    spark.stop()
  }
}
