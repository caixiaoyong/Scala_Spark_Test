package com.atguigu.chapter04

/**
 * 衍生集合:一个集合调用一个方法，返回一个新的集合，原集合保持不变
 * @author CZY
 * @date 2021/11/22 18:13
 * @description Test10_ListFunc
 */
object Test10_ListFunc {
  def main(args: Array[String]): Unit = {
    val list1: List[Int] = List(1, 2, 3, 4, 5, 6, 7)
    val list2: List[Int] = List(4, 5, 6, 7, 8, 9, 10)

    //    （1）获取集合的头
    println(list1.head)
    //    （2）获取集合的尾（不是头的就是尾）
    println(list1.tail)
    //    （3）集合最后一个数据
    println(list1.last)
    //    （4）集合初始数据（不包含最后一个）
    println(list1.init)
    //    （5）反转
    println(list1.reverse)
    //    （6）取前（后）n个元素 --hql中常用，取top
    println("=====================")
    println(list1.take(3))
    println(list1.takeRight(3))
    //    （7）去掉前（后）n个元素
    println(list1.drop(3))
    println(list1.dropRight(3))
    //    （8）并集
    println(list1.union(list2))
    //    （9）交集
    println(list1.intersect(list2))
    //    （10）差集
    println("=====================")
    println(list1.diff(list2))
    //    （11）拉链 注:如果两个集合的元素个数不相等，那么会将同等数量的数据进行拉链，多余的数据省略不用
    println(list1.zip(list2))

    //    （12）滑窗
    println("=====================")
    list1.sliding(2,5).foreach(println) //2个元素，5个一滑

    val iterator: Iterator[List[Int]] = list2.sliding(3)
    //iterator.foreach(println)

    // 给定一个数组  求数组中相邻3个元素和最大的值
    // 迭代器只能使用一次  指针指向最后就不能再次使用了
    var max: Int = Int.MinValue
    for (elem <- iterator) {
      max = math.max(max,elem.sum)
    }
    println(max)

  }
}
