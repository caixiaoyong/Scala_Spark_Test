package com.atguigu.exercise

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @author CZY
 * @date 2021/11/29 10:32
 * @description Demo_ad_click_top3
 */
object Demo_ad_click_top3 {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //1. 从文件读取后创建RDD
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\3.txt")

    //2. 将原始数据进行结构转换string =>(pro-adv,1)
    val proAndadv2One: RDD[(String, Int)] = rdd.map(line => {
        val datas: Array[String] = line.split(" ")
        (datas(1) + "-" + datas(4), 1)
      })
    //3. 将转换结构后的数据进行聚合统计（pro-adv,1）=>(pro-adv,sum)
    val proAndadv2Sum: RDD[(String, Int)] = proAndadv2One.reduceByKey(_ + _)

    //4. 将统计的结果进行结构的转换（pro-adv,sum）=>(pro,(adv,sum))
    val pro2advAndSum: RDD[(String, (String, Int))] = proAndadv2Sum.map {
      case (proAndadv, sum) => {
        val line: Array[String] = proAndadv.split("-")
        (line(0), (line(1), sum))
      }
    }
    println("=====上述方法使用偏函数，下述方法使用匿名函数======")
    proAndadv2Sum.map(line=>{
      val strings: Array[String] = line._1.split("-")
      (strings(0),(strings(1),line._2))
    })

    //5.根据省份对数据进行分组：(pro,(adv,sum)) => (pro, Iterator[(adv,sum)])
    val groupRDD: RDD[(String, Iterable[(String, Int)])] = pro2advAndSum.groupByKey()

    //6.对相同省份中的广告进行排序（降序），取前三名
    val res: RDD[(String, List[(String, Int)])] = groupRDD.mapValues(
      line => {
        line.toList.sortBy(_._2)(Ordering[Int] reverse).take(3)
      }
    )

//    val res: RDD[(String, List[(String, Int)])] = groupRDD.mapValues(
//      line => {
//        line.toList.sortWith((left, right) => {
//          left._2 > right._2
//        }).take(3)
//      }
//    )
    res.collect().foreach(println)
    //TODO 3.关闭连接
    sc.stop()
  }
}
