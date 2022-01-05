package com.atguigu.chapter04

/**
 *
 * @author CZY
 * @date 2021/11/23 11:33
 * @description Test16_WordCount1
 */
object Test16_WordCount1 {
  def main(args: Array[String]): Unit = {
    val tupleList = List(("Hello Scala Spark World ", 4), ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))
    // 方法1: 转换数据结构为长字符串
    val lineList = tupleList.map((tuple: (String, Int)) => (tuple._1 + " ") * tuple._2)
    println(lineList)

    val tuples = lineList.flatMap(_.split(" "))
      .groupBy(s => s)
      .mapValues(_.length)
      .toList
      .sortWith(_._2 > _._2)
      .take(3)
    println(tuples)

    println("方法2 :")
    // 方法2 : 转换结构=> ("单词",次数)
    val tupleList1 = tupleList.map((tuple: (String, Int)) => {
      tuple._1.split(" ").map((s: String) => (s, tuple._2)).toList
    })
    println(tupleList1)
    println(tupleList1.flatten)

    //直接使用flatMap
    val tuples1 = tupleList.flatMap(tuple => tuple._1.split(" ")
      .map((s: String) => (s, tuple._2)))
    println(tuples1)
    //化简方式
    tupleList.flatMap(tuple => tuple._1.split(" ").map((_, tuple._2)))

    // 将相同的单词聚合在一个组内
    val stringToTuples = tuples1.groupBy((tuple: (String, Int)) => tuple._1)
    println(stringToTuples)

    // 获取map中value里的list值，再通过foldLeft来将list后的集合规约统计
    val stringToInt = stringToTuples.mapValues((list: List[(String, Int)]) => list.foldLeft(0)((res: Int, elem: (String, Int)) => res + elem._2))
      //println(stringToInt)

    println(stringToTuples.mapValues((list: List[(String, Int)]) => list.map(_._2).sum))
    // 排序取top3
    println(stringToInt.toList.sortWith(_._2 > _._2).take(3))

  }
}
