package com.atguigu.chapter02

/**
  * 惰性函数
  * @author CZY
  * 2021/11/19 11:49
  * Test10_Lazy
  */
object Test10_Lazy {
  def main(args: Array[String]): Unit = {
    def sumAB(a:Int,b:Int):Int={
      println("函数调用")
      a+b
    }
    val i = sumAB(1,2)
    println("其他代码")
    println(i)
    println(i)

println("===================")

    def sumAB1(a:Int,b:Int):Int={
      println("函数调用")
      a+b
    }
    lazy val j = sumAB1(1,2)
    println("其他代码")
    println(j)
    println(j)
  }
}
