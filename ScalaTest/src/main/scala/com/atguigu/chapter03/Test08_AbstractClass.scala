package com.atguigu.chapter03

/**
 * 抽象类
 * @author CZY
 * @date 2021/11/20 16:33
 * @description Test09_AbstractClass
 */
object Test08_AbstractClass {
  def main(args: Array[String]): Unit = {
    val student0: Person08 = new Student08
    println(student0.age)
    println(student0.name)
    student0.sayHi()

    //匿名子类的使用 -- new 父类
    val person0: Person08 = new Person08 {
      override val name: String = "匿名子类"
      override var age: Int = 0

      //只能够解耦在里面，在内部嵌套使用
      val name1 : String ="匿名子类的属性"

      override def sayHi(): Unit = {
        println("hi 匿名子类")
        println(name1)
      }
    }//.var
    println(person0.name)
    println(person0.age)
    person0.sayHi()
    //多态子类中无法调用父类没有的属性
    //person0.name1
  }
}

//抽象属性、方法后面都没有等号
abstract class Person08 {
  val name: String
  var age: Int

  def sayHi(): Unit
}

//子类需要将父类的抽象属性和方法重写
class Student08 extends Person08{
  override val name: String = "student"
  override var age: Int = 18

  override def sayHi(): Unit = {
    println("hi student")
  }
}