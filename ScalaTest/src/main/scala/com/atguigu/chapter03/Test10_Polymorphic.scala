package com.atguigu.chapter03

/**
 * 多态：scala中多态不论属性还是方法，统一使用子类的
 * @author CZY
 * @date 2021/11/21 14:37
 * @description Test10_Polymorphic
 */
object Test10_Polymorphic {
  def main(args: Array[String]): Unit = {
    //如果使用类型推断，默认不会使用多态
    val student1 = new Student10
    student1.name1

    //多态需要手动改写类型
    val student11: Person10 = new Student10
    println(student11.name)
    student11.sayHi()
  }
}

class Person10{
  val name = "person"
  var age = 10
  def sayHi(): Unit ={
    println("hi person")
  }
}

class Student10 extends  Person10{
  override val name: String = "student"
  val name1 = "student1"
  override def sayHi() : Unit ={
    println("hi student")
  }
}
