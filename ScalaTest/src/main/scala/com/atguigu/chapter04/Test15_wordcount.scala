package com.atguigu.chapter04

/**
 *
 * @author CZY
 * @date 2021/11/23 10:11
 * @description wordcount
 */
object Test15_wordcount {
  def main(args: Array[String]): Unit = {
    // 单词计数：将集合中出现的相同的单词，进行计数，取计数排名前三的结果
    val stringList = List("Hello Scala Hbase kafka", "Hello Scala Hbase", "Hello Scala", "Hello")
    //1.扁平化加映射
    val strings = stringList.flatMap(_.split(" "))
    println(strings)
    //2.groupby
    val stringToStrings = strings.groupBy(word => word)
    println(stringToStrings)

    //3.(word, list) => (word, count)
    //方式一：使用map映射
    val wordCountList = stringToStrings.map((tuple: (String, List[String])) => (tuple._1, tuple._2.size))
    println(wordCountList)
    // 化简方式
    stringToStrings.map(tuple => (tuple._1, tuple._2.size))

    //方式二：map中key不变，使用mapValue
    println(stringToStrings.mapValues((tuple: (List[String])) => (tuple.size)))
    // 化简方式
    stringToStrings.mapValues( _.size)

    //4.转换成list，进行倒序排序，然后取前3个
    println(wordCountList.toList.sortWith(_._2 > _._2).take(3))

    println("==================================================")
    //最简写：
    val stringList1 = List("Hello Scala Hbase kafka", "Hello Scala Hbase", "Hello Scala", "Hello")

    val tuples = stringList1.flatMap(_.split(" "))
      .groupBy(word => word)
      .mapValues(_.size)
      .toList
      .sortWith(_._2 > _._2)
      .take(3)
    println(tuples)
  }
}
