package com.atguigu.chapter02

/**
  *高阶函数
  * @author CZY
  *         2021/11/17 19:44
  *         Test06_HighFunc
  */
object Test06_HighFunc {
  def main(args: Array[String]): Unit = {
    def sayHi(name: String): Unit = {
      println(s"hi $name")
    }

    sayHi("beijing")

    //    1）函数可以作为值进行传递
    // 给sayHi函数起个变量名func
    val func = sayHi _
    val func1: String => Unit = sayHi //alter+enter 可以省略'_'

    func("sichuan")

    val func2: (String) => String = "Play " + _
    println(func2("CsOnline"))

    //    2）函数可以作为参数进行传递
    def sumAB(a: Int, b: Int, func: (Int, Int) => Int): Int = {
      func(a, b)
    }

    sumAB(10, 20, (a: Int, b: Int) => 2 * a - 3 * b)
    sumAB(10, 20, (a, b) => 2 * a - 3 * b)

    val i = sumAB(10, 20, 2 * _ - 3 * _)
    println(i)

    def mapReduce(data: String, map: (String) => String, reduce: (String) => String): String = {
      val mapResult = map(data)
      val result = reduce(data)
      result
    }

    val str = mapReduce("log", (data: String) => data + "经过map处理,", (mapResult: String) => mapResult + "经过reduce处理")

    mapReduce("log", (data) => data + "经过map处理,", (mapResult) => mapResult + "经过reduce处理")
    mapReduce("log",  _ + "经过map处理,", _ + "经过reduce处理")
    println(str)

    //    3）函数可以作为函数返回值返回
    def sumByx(x:Int)={
      def sumXY(y:Int)={
        x+y
      }
      sumXY _
    }

    println(sumByx(10))
    //可以分开写，也可连着写
    println(sumByx(10)(20))

    //使用匿名函数化简,不推荐使用
    def sumByx1(x:Int):Int =>Int = x+_




  }
}
