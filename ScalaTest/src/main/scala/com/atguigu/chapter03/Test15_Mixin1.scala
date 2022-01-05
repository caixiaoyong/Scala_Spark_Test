package com.atguigu.chapter03

/**
 * 特质叠加类型
 * @author CZY
 * @date 2021/11/21 21:15
 * @description Test15_Mixin1
 */
object Test15_Mixin1 {
  def main(args: Array[String]): Unit = {
    val person1 = new Person15
    println(person1.info())
  }
}
trait Age15{
  def info():String = {
    "age"
  }
}

trait Young15 extends Age15 {
  override def info(): String = "young " + super.info()
}

trait Old15 extends Age15{
  override def info(): String =  "old " + super.info()
}

class Person15 extends Young15 with Old15 {
  override def info(): String = "person "+ super.info()
}