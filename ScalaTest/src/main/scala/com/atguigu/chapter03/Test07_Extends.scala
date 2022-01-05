package com.atguigu.chapter03

/**
 *  继承
 * @author CZY
 * @date 2021/11/21 15:12
 * @description Test07_Extends
 */
object Test07_Extends {
  def main(args: Array[String]): Unit = {
    val student0 = new Student07
    println("===================")
    val zhangsan = new Student07("zhangsan")

  }
}

class Person07(){
  println("父类的主构造器")
  var name :String = _
  def this(name:String)={
    this()
    println("父类的辅助构造器")
    this.name =name
  }
  def eat(food : String) ={
    println("eat meat")
  }
}
//继承父类的辅助构造器，父类的参数在子类中必须得有
class Student07(name:String) extends  Person07(name:String){
  println("子类的主构造器")
  def this() ={
    this("zhangsan")
    println("子类的辅助构造器调用")
  }
}
