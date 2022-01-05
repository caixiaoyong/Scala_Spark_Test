package com.atguigu.chapter02

/**
  *函数参数
  * @author CZY
  *         2021/11/17 18:41
  *         Test03_FunArgs
  */
object Test03_FunArgs {
  def main(args: Array[String]): Unit = {
    //    （1）可变参数
    def sayHi(names: String*): Unit = {
      println(s"hi $names")
    }

    sayHi()
    sayHi("shanghai")
    sayHi("shanghai", "hangzhou")

    //    （2）如果参数列表中存在多个参数，那么可变参数一般放置在最后
    def sayHi1(sex: String, names: String*): Unit = {
      println(s"hi $names")
    }

    //    （3）参数默认值，一般将有默认值的参数放置在参数列表的后面
    def sayHi2(names : String="guangzhou"): Unit = {
      println(s"hi $names")
    }
    sayHi2("shenzhen")
    sayHi2()

    //可变参数在使用的时候，可以不在最后
    def sayHi3(names : String="guangzhou",age : Int): Unit = {
      println(s"hi $names")
    }
    //    （4）带名参数
    sayHi3(age = 10)

    def sayHi4(age : Int , names : String="guangzhou"): Unit = {
      println(s"hi $names")
    }
    sayHi4(10)
  }
}
