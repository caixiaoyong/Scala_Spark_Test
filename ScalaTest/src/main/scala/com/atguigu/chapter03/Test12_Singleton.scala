package com.atguigu.chapter03

/**
 * 单例模式编写
 * @author CZY
 * @date 2021/11/20 11:13
 * @description Test12_Singleton
 */
object Test12_Singleton {
  def main(args: Array[String]): Unit = {
    val person1: Person12 = Person12()
    val person12: Person12 = Person12()
    // 判断多次调用单词对象返回的结果地址值是否相同--true表示相同
    println(person1.eq(person12))

  }
}

//单例模式
//饿汉式 --没有线程安全问题，对象直接在静态属性的时候加载出来了，不像懒汉式使用一次new一次
class Person12 private(){

}

//object Person12{
//  private val person1: Person12 = new Person12
//
//  def apply(): Person12 = person1
//}

//懒汉式 --线程安全问题
object Person12{
  private var person1: Person12 = _

  def apply(): Person12 = {
    if(person1 == null){
      person1 = new Person12
    }
    person1
  }
}

