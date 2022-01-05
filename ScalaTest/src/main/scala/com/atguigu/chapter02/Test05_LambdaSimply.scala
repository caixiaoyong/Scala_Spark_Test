package com.atguigu.chapter02

/**
  * 匿名函数
  * @author CZY
  *         2021/11/17 19:02
  *         Test05_LambdaSimply
  */
object Test05_LambdaSimply {
  def main(args: Array[String]): Unit = {
    val function: String => String = (name: String) => {
      "hi" +name
    }
    println(function(" hefei"))

    //    （1）参数的类型可以省略，会根据形参进行自动的推导
    val function1: String => String = (name) => {
      "hi" +name
    }

    //    （2）类型省略之后，发现只有一个参数，则圆括号可以省略；其他情况：没有参数和参数超过1的永远不能省略圆括号。
    val function2: String => String = name => {
      "hi" +name
    }
    val function3: () => String = () => {
      "hi"
    }
    val function4: (String,Int) => String = (name,age) => {
      "hi"
    }
    //    （3）匿名函数如果只有一行，则大括号也可以省略
    val function5: String => String = name => "hi" +name

    //    （4）如果参数只出现一次，则参数省略且后面参数可以用_代替
    val function6: String => String =  "hi" +_
    val function7 : (String,String) => String = "hi"+ _ + _
    println(function6(" chengdu"))

    val f1:Int=>Int = 5+_
    println(f1(123))

    val f2:String=>String = _+" "
    println(f2("qwe"))


  }
}
