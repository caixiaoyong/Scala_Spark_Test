package com.atguigu.chapter03

/**
 * 特质混入冲突问题
 * @author CZY
 * @date 2021/11/21 20:46
 * @description Test13_Mixin
 */
object Test14_Mixin {
  def main(args: Array[String]): Unit = {
    val student1 = new Student14
    println(student1.name)
    println(student1.age)
    student1.sayHi()
  }
}

trait Age14{
  val name: String = "age"
  var age: Int = 10

  def sayHi():Unit = {
    println("hi age")
  }
}

class Person14{
  val name: String = "person"
  //var age1: Int = 11
  var age1: Int = 11

  def sayHi():Unit = {
    println("hi person")
  }
}

class Student14 extends Person14 with Age14{
  // 如果子类继承的父类和特质中有名称相同的属性  会直接报错
  // 必须要重写解决
  override val name: String = "student"
  // 如果是父类和特质中 var的属性冲突
  // 没法解决 只能修改父类和特质的属性名
  age = 12

  // 如果方法冲突
  // 通过重写解决
  override def sayHi(): Unit = {
    println("hi student")
  }
}
