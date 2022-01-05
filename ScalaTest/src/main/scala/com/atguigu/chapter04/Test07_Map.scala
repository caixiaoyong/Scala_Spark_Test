package com.atguigu.chapter04

import scala.collection.mutable

/**
 * Map集合
 *
 * @author CZY
 * @date 2021/11/22 19:27
 * @description Test07_Map
 */
object Test07_Map {
  def main(args: Array[String]): Unit = {
    //不可变Map
    //（1）创建不可变集合Map
    val map = Map("hello" -> 1, "world" -> 2)
    val map1 = Map(("hi", 1), ("hello", 2))

    //（2）循环打印
    for (elem <- map1) {
      println(elem)
    }

    val keys: Iterable[String] = map.keys
    keys.foreach(println)
    println("=============")
    val values: Iterable[Int] = map.values

    //（3）读取数据
    val maybeInt: Option[Int] = map1.get("hi") //Some(1)
    val maybeInt1: Option[Int] = map1.get("hi1") //None

    println(maybeInt)
    println(maybeInt1)

    println(maybeInt.get) //1
    if (!maybeInt1.isEmpty) {
      println(maybeInt1.get)
    }
    // option有区分是否有数据的方法 推荐使用getOrElse  如果为None，返回默认值
    println(maybeInt1.getOrElse("不存在就返回此句话"))
    println(map1.getOrElse("hi", 10))//1
    // (4) 修改数据
    println(map1.updated("hid", 5))
    println("============")

    //可变Map
    //    （1）创建可变集合
    val map2 = mutable.Map(("hello", 1), ("world", 1))
    //    （2）向集合增加数据
    map2.put("hi",2)

    println(map2)
    //    （3）修改数据
    map2.put("hello",100)
    println(map2)
    map2.update("hello",200)
    println(map2)

    //    （4）删除数据
    map2.remove("hello")
    println(map2)
  }
}


