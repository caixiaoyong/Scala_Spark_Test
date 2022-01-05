package com.atguigu.chapter04

import scala.collection.immutable.Queue
import scala.collection.mutable

/**
 *
 * @author CZY
 * @date 2021/11/23 14:12
 * @description Test17_Queue
 */
object Test17_Queue {
  def main(args: Array[String]): Unit = {
    // 集合默认使用不可变，但是没法new Queue，可以使用封装好的apply伴生对象方法
//    new Queue
    val queue = Queue(1, 2, 3, 4)
    // 进队
    println(queue.enqueue(5))
    // 出队
    val dequeue = queue.dequeue
    println(dequeue._1)
    println(dequeue._2)

    println("=========")
    // 可变的队列
    val que = new mutable.Queue[String]()
    que.enqueue("a","b","c")
    println(que.dequeue())
    println(que.dequeue())
    println(que.dequeue())
  }
}
