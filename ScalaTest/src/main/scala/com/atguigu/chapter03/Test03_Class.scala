package com.atguigu.chapter03

import scala.beans.BeanProperty

/**
  * 定义和封装
  *
  * @author CZY
  * @date 2021/11/19 20:19
  * @description Test03_Class
  */
object Test03_Class {
  def main(args: Array[String]): Unit = {
    val person0 = new Person03
    person0.age = 11
    //val只有读权限，可以通过@BeanProperty注解实现get、set方法
    //person0.name="lisi"
    person0.getName1

    person0.getAge1
    person0.setAge1(11)
  }
}

// 同一个包下面 不能出现相同名称的类
class Person03 {
  //类中的属性定义
  // scala使用val和var的标记来区分读写权限  所以不推荐使用get和set方法
  val name: String = "zhangsan"
  var age: Int = 10

  @BeanProperty
  val name1: String = "zhangsan"

  @BeanProperty
  var age1: Int = 10
}

// 同一个文件中  可以出现多个public的类
class Student03 {

}