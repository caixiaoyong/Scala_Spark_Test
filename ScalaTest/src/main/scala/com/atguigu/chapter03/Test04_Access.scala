package com.atguigu.chapter03

/**
  * 访问权限
  * @author CZY
  * @date 2021/11/19 20:32
  * @description Test04_Access
  */
object Test04_Access {
  def main(args: Array[String]): Unit = {
    val person = new Person4
    //1.1 私有的访问权限  只能在类的内部和伴生对象的内部调用
    //  person.name
    //  但是可以通过对象.方法、类.方法进行调用
    person.sayHi()
    println("==============")
    Person4.sayHi1()

    //2.1同一个包内,不同的包无法调用
    println(person.age)
  }
}

class Person4{
  //1. 私有的权限
  private val name : String ="私有的"
  //2. 包访问权限
  private[chapter03] val age: Int = 10
  //3. 受保护的访问权限
  protected val sex:String = "男"

  def sayHi():Unit={
    val person = new Person4
    println(person.name)
    println(Person4.name1)

    //3.1 自身类及伴生对象中能够调用保护的权限
    println(person.sex)
  }
}

object Person4{//静态伴生对象
  private val name1: String = "私有的"

  def sayHi1():Unit={
    val person = new Person4
    println(person.name)
    //println(person.name1)
    println(Person4.name1)
    //3.1
    println(person.sex)
  }
}