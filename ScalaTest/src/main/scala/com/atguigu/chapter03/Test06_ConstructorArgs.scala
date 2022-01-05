package com.atguigu.chapter03

/**
 * 主构造器参数介绍
 * @author CZY
 * @date 2021/11/21 15:07
 * @description Test06_ConstructorArgs
 */
object Test06_ConstructorArgs {
  def main(args: Array[String]): Unit = {
    val person0 = new Person06("zhangsan",18,5000)
    //person0.age=11
  }
}
//主构造器参数
//没有修饰词  => 将参数作为基础的形参使用
//val 修饰  => 会将形参自动转换为不可变属性
//var 修饰  => 会将形参自动转换为可变属性
class Person06(name:String,val age:Int=10,var salary:Int){
  val name1 = name
}
