package com.atguigu.chapter04

/**
 * 基本属性和常用操作
 * @author CZY
 * @date 2021/11/22 18:40
 * @description Test09_LowFunc
 */
object Test09_LowFunc {
  def main(args: Array[String]): Unit = {
    val list: List[Int] = List(1, 2, 3, 4, 5, 6, 7)
    val set = Set(1, 2, 3, 4, 6, 8)
//    （1）获取集合长度
    println(list.length)
//    （2）获取集合大小
    println(list.size)
    //set没有length方法
    println(set.size)
//    （3）循环遍历
    list.foreach(print)
    list.foreach(i => print(i*2+"\t"))
//    （4）迭代器 list.iterator.for
    for (elem <- list.iterator) {
      println(elem)
    }
//    （5）生成字符串
    println(list.mkString(","))

    println(list.mkString("List(", ",", ")"))
//    （6）是否包含
    println(list.contains(3))
  }
}
