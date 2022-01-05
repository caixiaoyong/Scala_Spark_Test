package com.atguigu.chapter03

/**
 *
 * @author CZY
 * @date 2021/11/21 21:42
 * @description Test17_Extends
 */
object Test17_ExInstanceof {
  def main(args: Array[String]): Unit = {
    val son1 = new Son17
    println(son1.name)

    //使用多态,就无法调用父类中没有的属性了，这时要用到子类的属性需要强转
    val son2: Father17 = new Son17
    //son2.name
    if (son2.isInstanceOf[Son17]){
      val son11 = son2.asInstanceOf[Son17]
      println(son11.name)
    }

    // 获取模板
    val value: Class[Father17] = classOf[Father17]
  }
}
class Father17{

}
class Son17 extends Father17{
  val name : String = "son"
}
