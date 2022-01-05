package com.atguigu.chapter02

/**
  *
  * @author CZY
  *         2021/11/19 16:42
  *         MyWhile
  */
object MyWhile {
  def main(args: Array[String]): Unit = {

    var i = 0
    while (i < 5) {
      println(i)
      i += 1
    }
    i=0

    //柯里化
    def mywhile(b: =>Boolean)(op: => Unit): Unit = {
      if (b) {
        op
        mywhile(b)(op)
      }
    }
    println("====================")
    mywhile({
      println("布尔代码块")
      i < 5//值调用，在mywhile方法中返回true，只走一次，所以需要将方法内的b改成名调用
    }) {
      println(i)
      i += 1
    }

  }
}
