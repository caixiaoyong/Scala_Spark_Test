package com.atguigu.chapter02

/**
  *函数定义
  * @author CZY
  * 2021/11/17 14:44
  * Test02_FuncDef
  */
object Test02_FuncDef {
  def main(args: Array[String]): Unit = {

    //    （1）函数1：无参，无返回值
    def sayHi(): Unit = println("hello word")

    sayHi

    //    （2）函数2：无参，有返回值
    def sayHi1(): String = "hi"
    val str: String = sayHi1()
    println(str)
    println(sayHi1)

    //    （3）函数3：有参，无返回值
    def sayHi2(name : String):Unit = println(s"hi ${name}")
    val shanghai = sayHi2("shanghai")

    //    （4）函数4：有参，有返回值
    def sayHi3(name : String) : String ={
      //若有多行不可省略
      s"hi ${name}"
      s"hello $name"
    }
    val beijing = sayHi3("beijing")
    println(beijing)

    //    （5）函数5：多参，无返回值
    def sayHi4(name : String , age : Int) :Unit = {
      println(s"name: $name age: $age")
    }
    val pp = sayHi4("pp",18)

    //    （6）函数6：多参，有返回值
    def sayHi5(name : String , age : Int) :String = s"age: $age name: $name"
    val bb = sayHi5("bb",22)
    println(bb)
  }
}
