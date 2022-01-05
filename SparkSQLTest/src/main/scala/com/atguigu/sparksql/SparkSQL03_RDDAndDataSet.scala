package com.atguigu.sparksql

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Dataset, SparkSession}

/**
 *
 * @author CZY
 * @date 2021/12/3 19:55
 * @description SparkSQL03_RDDAndDataSet
 */
object SparkSQL03_RDDAndDataSet {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf配置文件,并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")
    //TODO 2 利用SparkConf创建sparksession对象
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //1. 创建sc对象
    val sc: SparkContext = spark.sparkContext

    //2.读取文件创建linerdd
    val lineRdd: RDD[String] = sc.textFile("D:\\BigData\\Scala_SparkTest\\SparkSQLTest\\input\\user.txt")

    //3.1 结构的变换  qiaofeng,20 => (qiaofeng,20)
    val rdd: RDD[(String, String)] = lineRdd.map(line => {
      val datas: Array[String] = line.split(",")
      (datas(0), datas(1))
    })

    //3.2 结构的变换 转成user样例类RDD
    val userRdd: RDD[User] = rdd.map {
      case (name, age) => User(name, age.toLong)
    }

    //rdd和DF和DS三者进行转换之前,一定要导入一个隐式转换
    import spark.implicits._

    //TODO RDD=>DS
    //普通RDD转DS(不建议) 不能补充元数据（列名）
    val ds: Dataset[(String, String)] = rdd.toDS()
    ds.show()

    //样例类rdd转换DS(建议) 不需要补充元数据 列名默认用的是样例类的属性名
    val userDs: Dataset[User] = userRdd.toDS()
    userDs.show()


    //TODO DS=>RDD
    //DS=>RDD  DS可以记录数据原本的类型,这一点是要比DF强的
    val rdd1: RDD[(String, String)] = ds.rdd
    val rdd2: RDD[User] = userDs.rdd

    rdd2.collect().foreach(println)

    //TODO 3 关闭资源
    spark.stop()
  }
}
