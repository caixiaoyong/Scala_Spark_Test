package com.atguigu.chapter03

/**
 * apply方法
 * @author CZY
 * @date 2021/11/20 10:50
 * @description Test11_Apply
 */
object Test11_Apply {
  def main(args: Array[String]): Unit = {
    //val  Person11 = new Person11
    val person1: Person11 = Person11.getPerson11

    //如果调用的方法是apply的话  方法名apply可以不写
    //val person11: Person11 = Person11.apply()
    val person11: Person11 = Person11()
    val zhangsan: Person11 = Person11("zhangsan")

    // 类的apply方法调用 apply方法依然能够自定义  同时调用时不写方法名
    person11()

  }
}

class Person11 private(){
  var name:String = _
  def this(name:String){
    this()
    this.name = name
  }

  //def apply(): Person11 = new Person11()
  def apply(): Unit = {
    println("类的apply方法调用")
  }
}

object Person11{
  // 使用伴生对象的静态方法来获取对象实例
  def getPerson11: Person11 = new Person11
  // 伴生对象的apply方法
  def apply(): Person11 = {
    println("apply方法的调用")
    new Person11()
  }
  // apply方法的重载
  def apply(name1:String): Person11 = {
    println("apply方法的重载")
    new Person11()
  }
}
