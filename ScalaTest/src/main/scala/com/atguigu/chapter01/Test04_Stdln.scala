package com.atguigu.chapter01

import scala.io.StdIn

/**
  *
  * @author CZY
  * @date 2021/11/16 18:06
  * @description Test04_Stdln
  */
object Test04_Stdln {
  def main(args: Array[String]): Unit = {
    println("请输入您的姓名:")
    val name = StdIn.readLine()
    println("请输入您的年龄:")
    val age = StdIn.readInt()
    println(s"name:${name+1} age:${age+1}")
  }

}
