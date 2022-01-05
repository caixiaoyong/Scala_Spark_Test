package com.atguigu.chapter03

/**
  *
  * @author CZY
  * @date 2021/11/19 20:56
  * @description Test05_Construct
  */
object Test05_Constructor {
  def main(args: Array[String]): Unit = {
    val person0 = new Person05("zhangsan")
    val person01 = new Person05()
    println("================")
    val person02 = new Person05(10)

  }
}
//主构造器
class Person05(name1:String){
  println("主构造器调用")
  val name:String = name1

  //辅助构造器
  def this()={
    //辅助构造器第一行代码 必须直接或间接调用主构造器
    this("lisi")
    println("辅助构造器1的调用")
  }

  //间接构造器
  def this(age:Int){
    //先调用上一个辅助构造器内的内容
    this()
    println("辅助构造器2的调用")
  }
}
