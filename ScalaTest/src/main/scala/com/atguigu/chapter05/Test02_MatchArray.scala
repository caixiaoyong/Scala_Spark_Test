package com.atguigu.chapter05

/**
 *
 * @author CZY
 * @date 2021/11/23 20:08
 * @description Test02_MatchArray
 */
object Test02_MatchArray {
  def main(args: Array[String]): Unit = {
    def func1(n:Any):Unit={
      n match {
        case Array(1,0)=> println("两个元素的数组,一个是1一个是0")
        case Array(x, _) => println(s"两个元素的数组  第一个是$x")
        case Array(_*) => println("所有的数组")
      }
    }
    val array: Array[Double] = Array(1.0, 1.0, 0.1)
    func1(array)
    func1(array)
    println("===========================")
    def func2(n:Any):Unit = {
      n match {
        case i:Array[Int] => println("整数类型的数组")
        case c:Array[Char] => println("char类型的数组")
        case s:Array[String] => println("字符串类型的数组")
        case _ => println("其他数据")
      }
    }

    func2(Array(1,2,3))
    func2(Array("1","2","3"))
    func2(Array(1.0,2.0,3.0))
    func2(Array('1','2','3'))


    // list在匹配的时候 忽略掉了泛型的影响   泛型擦除
    def func3(n:Any):Unit = {
      n match {
        case c:List[Char] => println("char类型的集合")
        case i:List[Int] => println("整数类型的集合")
        case s:List[String] => println("字符串类型的集合")
        case _ => println("其他数据")
      }
    }
    println("===========================")
    func3(List(1,2,3))
    func3(List("1","2","3"))
    func3(List(1.0,2.0,3.0))
    func3(List('1','2','3'))

  }
}
