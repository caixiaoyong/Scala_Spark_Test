package com.atguigu.WordCount

/**
 *
 * @author CZY
 * @date 2021/11/24 19:18
 * @description TestWordCount2
 */
object TestWordCount2 {
  def main(args: Array[String]): Unit = {
    val list = List(("Hello Scala Spark World ", 4), ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))

    //1. 转换成("单词","次数")
    val list1 = list.map((tuple: (String, Int)) => {
      tuple._1.split(" ").map(s => (s, tuple._2)).toList
    })
    println(list1)
    println(list1.flatten)
      //直接使用flatMap 简写
      val tuples = list.flatMap((tuple => tuple._1.split(" ").map((_, tuple._2))))

    //2. 将相同单词聚合在一个组内
    val stringToTuples = tuples.groupBy((s: (String, Int)) => s._1)
    println(stringToTuples)
    // 化简方式
    tuples.groupBy(_._1)
    println(stringToTuples)

    //3. 获取map中value里的list值，再通过foldLeft来将list后的集合规约统计
    val stringToInt = stringToTuples.mapValues((list: List[(String, Int)]) => list.foldLeft(0)((res: Int, elem: (String, Int)) => res + elem._2))
    println(stringToInt)
    //方式二 直接将list值内的值求和
    stringToTuples.mapValues((list: List[(String, Int)]) => list.map(_._2).sum)

    //4. 排序取top3
    println(stringToInt.toList.sortWith(_._2 > _._2).take(3))

  }
}
