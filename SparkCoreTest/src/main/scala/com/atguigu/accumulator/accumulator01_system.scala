package com.atguigu.accumulator

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 累加器
 * @author CZY
 * @date 2021/11/30 14:59
 * @description accumulator01_system
 */
object accumulator01_system {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val dataRDD: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("a", 2), ("a", 3), ("a", 4)))
    //需求:统计a出现的所有次数 ("a",10)

    //累加器实现
    //1 声明累加器
    val accSum: LongAccumulator = sc.longAccumulator("sum")

    dataRDD.foreach{
      case (a,count)=>{
        //2 使用累加器累加  累加器.add()
        accSum.add(count)
      }
    }
    //3 获取累加器的值 累加器.value
    println("sum= "+accSum.value)

    //TODO 3.关闭连接
    sc.stop()
  }
}
