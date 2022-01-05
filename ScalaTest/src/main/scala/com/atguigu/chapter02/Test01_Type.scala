package com.atguigu.chapter02

import scala.io.StdIn
import scala.util.control.Breaks

/**
  *
  * @author CZY
  * 2021/11/17 21:26
  * Test01_Type
  */
object Test01_Type {
  def main(args: Array[String]): Unit = {
    val age = StdIn.readInt()

    //    （3）需求3：Scala中返回值类型不一致，取它们共同的祖先类型。
    val value: Any = if (age < 18)
      "童年"
    else if (age <= 60) {
      "中年"
    } else if (age < 120) {
      "老年"
    } else {
      666
    }
    println(value)

    //    （4）需求4：Java中的三元运算符可以用if else实现
    val str = if (age<18)"童年" else "成年"
    println(str)

    for(i <- 1 to 3 if i != 2) {
      print(i + " ")
    }
    println()

    //需求1：采用异常的方式退出循环
    try{
      for (i<-1 to 10){
        println(i)
        if (i==5){
          throw new RuntimeException
        }
      }
    }catch {
      case e=>
    }

    //需求2：采用Scala自带的函数，退出循环
    Breaks.breakable{
      for (i<-1 to 10){
        println(i)
        if (i==5) Breaks.break()
      }
    }


  }

}
