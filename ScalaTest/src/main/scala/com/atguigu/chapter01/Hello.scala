package com.atguigu.chapter01

/**
  *
  * @author CZY
  * @date 2021/11/16 11:47
  * @description Hello
  */

object Hello {
  def main(args: Array[String]): Unit = {
    println("hello scala")

    // 如果使用类型推导  只会推导为int值
    var i = 10
    val unit = i = 11
    val n = 10

    val d : Int = 0
    var d0 : Int = 1
    println(i)

    val person0 = new person()
    var person1 = new person()

    person1 = new person()

    //person0.name = "lisi"
    person0.age = 11

    println(person0.age)
  }
}

class person{
  val name : String = "zhangsan"
  var age : Int = 10
}
