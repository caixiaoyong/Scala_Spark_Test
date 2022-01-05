package com.atguigu.chapter04

/**
 * 集合不可变数组
 * @author CZY
 * @date 2021/11/21 22:16
 * @description Test01_Array
 */
object Test01_Array {
  def main(args: Array[String]): Unit = {
    //创建数组的两种方法
    //1. 创建一个不可变数组
    val array = new Array[Int](10)

    //2. 使用伴生对象的apply方法创建数组
    val array1: Array[Int] = Array(1, 2, 3)

    // 遍历打印数组array.for
    for (elem <- array) {
      println(elem)
    }

    // scala中封装了简写方式：i <- 0 until array.length
    for (i <- array.indices) {
      println(array(i))
    }

    // 使用迭代器打印
    val iterator: Iterator[Int] = array1.iterator
    while (iterator.hasNext) {
      val i: Int = iterator.next()
      println(i)
    }
    println("=======================")

    // scala独有的遍历打印方法
    def myPrint(i: Int): Unit = {
      println(i * 2)
    }

    //使用匿名函数化简
    def myPrint1(i: Int) = println(i * 2)

    array1.foreach(myPrint) //f:Int => U--泛型
    // 化简形式
    array1.foreach(i => println(i * 2))

    // 最常用的遍历打印
    array1.foreach(println)//底层参数是任意类型Object > Int所以可以使用println

    println("=======================")
    // 修改元素
    array1(0)
    array1(0) = 100
    array1.update(1, 200)
    array1.foreach(println)

    println("=======================")
    // 添加元素
    val array2: Array[Int] = array1 :+ 1
    array2.foreach(println)

    println("=======================")
    val array3: Array[Int] = 1 +: array1
    array3.foreach(println)
  }
}
