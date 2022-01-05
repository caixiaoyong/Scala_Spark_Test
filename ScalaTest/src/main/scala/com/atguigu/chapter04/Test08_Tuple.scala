package com.atguigu.chapter04

/**
 * 元组 可以存放各种相同或不同类型数据的容器
 *
 * @author CZY
 * @date 2021/11/22 19:01
 * @description Test08_Tuple
 */
object Test08_Tuple {
  def main(args: Array[String]): Unit = {
    //    （1）声明元组的方式：(元素1，元素2，元素3)
    val tuple = new Tuple2[String, Int]("hi", 5)

    val tuple1: (String, Int) = ("hello", 2)
    //    （2）访问元组,通过元素的顺序进行访问，调用方式：_顺序号
    println(tuple._2)
    //    （3）Map中的键值对其实就是元组,只不过元组的元素个数为2，称之为对偶
    val map1 = Map(("hi", 1), ("hello", 2))
    map1.foreach(println)
    map1.foreach((tuple:(String,Int))=>println(tuple._1))
    //map1.foreach((tuple)=>println(tuple._1))
    println(map1.toList)
  }
}
