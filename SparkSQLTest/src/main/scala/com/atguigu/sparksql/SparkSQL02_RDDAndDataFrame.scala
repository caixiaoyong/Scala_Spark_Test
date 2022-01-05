package com.atguigu.sparksql

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
 *
 * @author CZY
 * @date 2021/12/3 19:27
 * @description SparkSQL02_RDDAndDataFrame
 */
object SparkSQL02_RDDAndDataFrame {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf配置文件,并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")
    //TODO 2 利用SparkConf创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //1.使用spark创建sc对象
    val sc: SparkContext = spark.sparkContext

    //2.读取文件创建linerdd
    val lineRdd: RDD[String] = sc.textFile("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.txt")

    //3.1 结构的变换  qiaofeng,20 => (qiaofeng,20)
    val rdd: RDD[(String, String)] = lineRdd.map(line => {
      val datas: Array[String] = line.split(",")
      (datas(0), datas(1))
    })
    rdd.collect().foreach(println)
    println("=============")
    //3.2 结构的变换 转成user样例类RDD
    val userRdd: RDD[User] = rdd.map {
      case (name, age) => User(name, age.toLong)
    }
    userRdd.collect().foreach(println)
    println("=============")
    //rdd和DF和DS三者进行转换之前,一定要导入一个隐式转换,spark指的是上面的sparkSession的变量
    import spark.implicits._

    //TODO RDD=>DF
    //普通rdd转换DF,需要自己手动补充列名,如果不补充,列名默认为_1,_2
    val df: DataFrame = rdd.toDF("name", "age")
    df.show()

    //样例类rdd转换DF,不需要自己手动补充列名,列名默认为样例类的属性名
    val userdf: DataFrame = userRdd.toDF()
    userdf.show()

    //TODO DF=>RDD
    //DF转换成RDD,直接.rdd即可,但是要注意转换出来的rdd数据类型会变成Row,df会丢失原本数据类型
    val rdd1: RDD[Row] = df.rdd
    val rdd2: RDD[Row] = userdf.rdd

    rdd2.collect().foreach(println)

    val rdd3: RDD[User] = rdd2.map {
      row => {
        val name: String = row.getString(0)
        val age: Long = row.getLong(1)
        User(name, age)
      }
    }
    rdd3.collect().foreach(println)

    //TODO 3 关闭资源
    spark.stop()
  }
}
case class User(name1:String,age1:Long)