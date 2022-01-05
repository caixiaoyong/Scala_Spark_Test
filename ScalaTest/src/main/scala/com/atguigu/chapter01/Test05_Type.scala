package com.atguigu.chapter01

/**
  *
  * @author CZY
  * 2021/11/16 18:23
  * Test05_Type
  */
object Test05_Type {
  def main(args: Array[String]): Unit = {

    val i : Int = {
      9
      println("hi")
      10
    }
    // 使用代码块最后一行的代码作为返回值
    println(i)


    // 如果代码块只有一行  大括号省略
    val i1: Int = 22

    // （0）Unit类型用来标识过程，也就是没有明确返回值的函数。
    val unit = println("hello")
    println(unit)//返回()

    //特例
    val unit2 :Unit= {
      println("hi")
      1 + 1
      println("1")
    }
    println("unit2= "+unit2)

    val unit3 : Unit = "aa"
    println(unit3)

    // （1）Scala各整数类型有固定的表示范围和字段长度，
    // 不受具体操作的影响，以保证Scala程序的可移植性
    val b1 : Byte = 2
    //val b2 : Byte = 128

    val b3 : Byte= 1 + 1 //只是idea识别不出scala，所以报红提示，实际可以运行
    println(b3)

    val b4 :Byte = 127
    val b5 :Byte = 126 + 1
    println(b5)

    // 浮点数
    // 默认使用double,如果使用float 在末尾添加f
    val f1: Float = 3.14f
    val d: Double = 3.14
    println("f1: "+f1+" d: "+d)

    //不能将大类型赋值给小类型
    val i2 = 1
    val i3 : Double= i2 + 1
//  val i4 : Byte = i2 + 3
    println(i3)


    // char
    //    （1）字符常量是用单引号 ' ' 括起来的单个字符。
    //    （2）\t ：一个制表位，实现对齐的功能
    //    （3）\n ：换行符
    //    （4）\\ ：表示\
    //    （5）\" ：表示"
    val c : Char = 'a'
    val c1 : Char = 97
    val c2 : Char = '\t'
    val c3 : Char = '\n'
    val c5 = '\"'
    println(c2 + 0)
    println("c5= "+c5)


    val s1 : String = null
    println(s1)
    if (s1 != null){
      val strings : Array[String] = s1.split(" ")
      println(strings)
    }

    //不能将null赋值给任何数值类型
//    val s2 : Int = null
//    println(s2)

    //
    val exception = new RuntimeException
    println(exception)
//    val value:Nothing = throw exception




  }
}
