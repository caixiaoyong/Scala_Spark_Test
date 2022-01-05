package com.atguigu.chapter04

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * 集合可变数组
 * @author CZY
 * @date 2021/11/21 22:47
 * @description Test02_ArrayBuffer
 */
object Test02_ArrayBuffer {
  def main(args: Array[String]): Unit = {
    // 创建一个可变数组
    // 初始化大小默认是16  表示底层存储的大小  不是集合的长度
    val buffer: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    // 使用伴生对象的apply方法创建
    val buffer1: ArrayBuffer[Int] = ArrayBuffer(1, 2, 3, 4)

    // 遍历打印可变数组
    buffer.foreach(println)
    buffer1.foreach(println)
    println(buffer1)

    // 增加元素
    //    buffer :+ 1
    //    buffer += 1
    buffer.append(1)
    buffer.prepend(0)
    buffer.appendAll(Array(2,3,4))
    println(buffer)

    // 删除元素
    buffer.remove(0)
    buffer.remove(2)
    println(buffer)

    // 修改元素
    buffer.update(0,100)
    println(buffer)

    println(buffer(0))
    buffer(1) = 100
    println(buffer)

    println("=======================")
    // 可变数组和不可变数组的转换
    val array: Array[Int] = Array(1, 2, 3)
    val arrayBuffer: ArrayBuffer[Int] = ArrayBuffer(4, 5, 6)

    // 可变数组转换为不可变数组
    val array1: Array[Int] = arrayBuffer.toArray

    // 不可变数组转换为可变数组
    val buffer2: mutable.Buffer[Int] = array.toBuffer
    buffer2.append(10)
    println(buffer2)
  }
}
