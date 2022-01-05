package com.atguigu.chapter03

import scala.collection.mutable.{HashMap, ListBuffer}

/**
  * 导包说明
  *
  * @author CZY
  * @date 2021/11/19 20:06
  * @description Test02_Import
  */
object Test02_Import {
  def main(args: Array[String]): Unit = {
    // 可以不导包  直接使用全类名
    new scala.collection.mutable.HashSet
    new HashMap[String,String]()

    // 局部导入
    import scala.collection.mutable.HashSet
    new HashSet[String]()

    // 导入这个包里面所有的对象和类
    // 只能导入当前包里面所有的类和对象  不能导入嵌套包里面的东西
    import scala.collection._
    new mutable.HashMap[String,String]()

    // 导入静态伴生对象下的属性和方法
    // 只包含静态的伴生对象  不包含类
    import scala.collection.mutable.ListBuffer._
    newBuilder
  }
}

class Person02{

}