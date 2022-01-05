package com.atguigu.chapter05

/**
 *
 * @author CZY
 * @date 2021/11/23 20:00
 * @description Test01_Match
 */
object Test01_Match {
  def main(args: Array[String]): Unit = {

    // 匹配类型
    def func2(x:Any):Unit={
      x match {
        case i:Int => println("x是一个整数")
        case c:Char => println("x是一个字节")
        case s:String => println("x是一个字符串")
        case _ => println("其他数据")
      }
    }
    func2(1)
    func2(2)
    func2(200)
    func2('a')
    func2('b')
    func2("a")
  }
}
