package com.atguigu.accumulator

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * 自定义累加器
 * @author CZY
 * @date 2021/11/30 15:12
 * @description accumulator03_define
 */
object accumulator03_define {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //需求：自定义累加器，统计RDD中首字母为“H”的单词以及出现的次数。
    //3. 创建RDD
    val rdd: RDD[String] = sc.makeRDD(List("Hello", "Hello", "Hello", "Hello", "Spark", "Spark"), 2)

    // 使用累加器
    //3.1 创建累加器
    val acc = new MyAcc

    //3.2 注册累加器
    sc.register(acc,"myacc")

    //3.3 使用累加器
    rdd.foreach(word=>{
      acc.add(word)
    })

    //3.4 获取累加器的累加结果
    val value: mutable.Map[String, Long] = acc.value
    println(value)


    //TODO 3.关闭连接
    sc.stop()
  }

  class MyAcc extends AccumulatorV2[String, mutable.Map[String, Long]] {
    // 首先创建一个本体 返回值是什么 本体就是什么
    private val map = new mutable.HashMap[String, Long]()

    // 判断累加器是否为空
    override def isZero: Boolean = map.isEmpty

    // 复制多个累加器的副本给到executor
    override def copy(): AccumulatorV2[String, mutable.Map[String, Long]] = new MyAcc

    // 重置累加器
    override def reset(): Unit = map.clear()

    // 累加rdd中的一个元素 => 分区内累加
    override def add(v: String): Unit = {
      // 如果传入一个H开头的单词 根据map中是否存在 添加 或者修改value+1
      if (v.startsWith("H")) {
        map(v) = map.getOrElse(v, 0L) + 1L
      }
    }

    // 合并从executor返回的累加器
    override def merge(other: AccumulatorV2[String, mutable.Map[String, Long]]): Unit = {
      //1. 调用返回的累加器
      val map1: mutable.Map[String, Long] = other.value
      //2. 遍历map1，将其中key的value加到map上
      //      map1.foreach{
      //        case (word,count)=>{
      //          map(word)=map.getOrElse(word,0L)+count
      //        }
      //      }

      // 循环map1，将其中的元素放入到map本体中
      for (elem <- map1) {
        val count: Long = elem._2
        val word: String = elem._1
        map.put(word, map.getOrElse(word, 0L) + count)
      }
    }

    // 返回累加器的本地
    override def value: mutable.Map[String, Long] = map
  }

}
