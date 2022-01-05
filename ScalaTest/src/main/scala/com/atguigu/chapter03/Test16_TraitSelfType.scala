package com.atguigu.chapter03

/**
 * 特质自身类型
 * @author CZY
 * @date 2021/11/21 21:25
 * @description Test16_TraitSelfType
 */
object Test16_TraitSelfType {
  def main(args: Array[String]): Unit = {

  }
}
trait Age16{
  var age : Int
}

trait Young16{
  // 特质自身类型
  _:Age16 =>
}

//如果继承Young16，必须要实现特质Age16
class Person16 extends Young16 with Age16{
  override var age: Int = 10
}