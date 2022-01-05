package com.atguigu.chapter04

import scala.collection.mutable

/**
 *
 * @author CZY
 * @date 2021/11/23 10:13
 * @description Test14_mapreduce
 */
object Test14_ReduceMap {
  def main(args: Array[String]): Unit = {
    // 归约两个map   如果元素存在 就将value值合并,  如果不存在  放入到map中
    val map = mutable.Map(("hello", 1), ("world", 2))
    val map1 = mutable.Map( ("world", 2) , ("scala",3))

    // 方式一
    for (elem <- map) {
      val key: String = elem._1
      val value: Int = elem._2
      // 原map中如果包含当前元素就加上key对应的value，如果不存在就加0
      map1.put(key,value+ map1.getOrElse(key,0))
    }
    println(map1)

    // 方式二
    val map2 = Map(("hello", 1), ("world", 2))
    val map3 = Map( ("world", 2) , ("scala",3))

    val map4 = map3.foldLeft(map2)((res: Map[String, Int], elem: (String, Int)) => {
      //res.updated(elem._1,elem._2+res.getOrElse(elem._1.var,0))
      val key = elem._1
      res.updated(key, elem._2 + res.getOrElse(key, 0))
    })
    println(map4)
  }
}
