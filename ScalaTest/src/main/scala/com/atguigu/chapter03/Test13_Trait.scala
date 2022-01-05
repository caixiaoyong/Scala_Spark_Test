package com.atguigu.chapter03

/**
 * 特质介绍使用
 * @author CZY
 * @date 2021/11/21 20:36
 * @description Test13_Trait
 */
object Test13_Trait {
  def main(args: Array[String]): Unit = {
    val student1 = new Student13
    println(student1.name)
  }
}

trait Age13{
  // 抽象的属性
  val name: String
  var age: Int

  //非抽象的属性
  val name1: String = "zhangsan"
  var age1: Int = 10

  // 抽象的方法
  def sayHi():Unit
  // 非抽象的方法
  def sayHi1():Unit = println("hi")

}

trait Young13{}


class Person13{}

// 实现特质的语法
// 如果只实现单个特质  使用extends
// 如果实现多个特质  多次使用with即可
// java中的接口都能够直接当做scala中的特质使用
// 如果还需要继承父类  必须使用extends父类  之后with接口
class Student13 extends Person13 with Age13 with Young13 with java.io.Serializable {
  override val name: String = "student"
  override var age: Int = _

  override def sayHi(): Unit = {
    println("hi student")
  }
}