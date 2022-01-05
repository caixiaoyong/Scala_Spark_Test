package com.atguigu.WordCount

/**
 *
 * @author CZY
 * @date 2021/11/22 22:20
 * @description TestWordCount
 */
object TestWordCount {
  def main(args: Array[String]): Unit = {
    val stringList = List("Hello Scala Hbase kafka", "Hello Scala Hbase", "Hello Scala", "Hello")

    //1. 将每一个字符串转换成一个一个单词
    val wordList = stringList.flatMap(x => x.split(" "))
    println(wordList)

    //2. 将相同的单词放置在一起
    val wordToWordsMap: Map[String, List[String]] = wordList.groupBy(x => x)
    println(wordToWordsMap)

    //3. 对相同的单词进行计数：分组后，为一个map
    val wordToCountMap = wordToWordsMap.map(i => (i._1, i._2.size))
    println(wordToCountMap)

    //4. 对计数完成后的结果进行排序（降序）
    //println(wordToCountMap.toList)
    val sortList = wordToCountMap.toList.sortWith((left, right) => left._2 > right._2)
    println(sortList)

    //5. 对排序后的结果取前3名
    val resultList = sortList.take(3)
    println(resultList)
  }
}
