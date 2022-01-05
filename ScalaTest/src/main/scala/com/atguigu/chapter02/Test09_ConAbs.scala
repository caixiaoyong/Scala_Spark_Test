package com.atguigu.chapter02

/**
  * 控制抽象：控制代码块在什么时候运行，运行多少次
  * @author CZY
  * 2021/11/19 16:25
  * Test09_ConAbs
  */
object Test09_ConAbs {
  def main(args: Array[String]): Unit = {
    def sayHi(name:String): Unit ={
      println("函数调用")
      println(s"hi $name")
      println(s"hi $name")
    }

    // 值调用：先运行，然后将值传递过去
    sayHi({
      println("这是代码块1")
      "shanghai"})

    println("=======================")

    // 名调用:控制动态运行
    //  => String 表示返回值为字符串的代码块

    def sayHi1(name: => String): Unit ={
      println("函数调用")
      println(s"hi $name")
      println(s"hi $name")
    }

    sayHi1({
      println("这是代码块2")
      "shanghai"})

  }
}
