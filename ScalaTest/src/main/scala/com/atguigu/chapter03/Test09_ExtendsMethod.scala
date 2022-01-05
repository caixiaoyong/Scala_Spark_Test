package com.atguigu.chapter03

/**
 *
 * @author CZY
 * @date 2021/11/21 16:20
 * @description Test09_ExtendsMethod
 */
object Test09_ExtendsMethod {
  def main(args: Array[String]): Unit = {

  }
}
abstract class Person09{
  // 非抽象的属性
  val name: String = "zhangsan"
  var age: Int = 10

  // 抽象的属性
  val name1: String
  var age1: Int

  // 非抽象的方法
  def sayHi(): Unit = {
    println("hi person")
  }

  // 抽象的方法
  def sayHi1(): Unit
}
class Student09 extends Person09{
  override val name1: String = "student"
  override var age1: Int = _

  override def sayHi1(): Unit = {
    println("hi student")
  }
}