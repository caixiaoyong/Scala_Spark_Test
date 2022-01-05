package com.atguigu.chapter01

/**
  *
  * @author CZY
  * @date 2021/11/16 19:41
  * @description Test06_TypeCast
  */
object Test06_TypeCast {
  def main(args: Array[String]): Unit = {
//    （1）自动提升原则：有多种类型的数据混合运算时，
//     系统首先自动将所有数据转换成精度大的那种数据类型，然后再进行计算。
        val b1 : Byte = 1
        val f1 : Float = b1 + 2 + 3L + 3.1f
        val d1 : Double = b1 + 2 + 3L + 3.1f + 3.1
        println("b1= "+b1+" f1= "+f1+" d1= "+d1)

//    （2）把精度大的数值类型赋值给精度小的数值类型时，就会报错，反之就会进行自动类型转换。
        val i :Int= b1
        //val i1 :Byte= i

//    （3）（byte，short）和char之间不会相互自动转换。
//        val c : Char = b1

//    （4）byte，short，char他们三者可以计算，在计算时首先转换为int类型。
        val b2 : Byte = 2
        val i2 : Int = b1 + b2
        val s: Short = 1
        val s1: Short = 1
        val i3: Int = s + s1

        val i4: Int = 1
        val i5: Int = 1
        val i6: Int = i4 + i5

        println(s"i2:$i2 i3:$i3 i6:$i6")

        println(
          s"""
            |i2=$i2
            |i3=$i3
            |i6=$i6
          """.stripMargin)

        println("i2= "+i2+" i3= "+i3+" i6= "+i6)

      // 强制类型转换
      //    （1）将数据由高精度转换为低精度，就需要使用到强制转换
      val d2 = 3.94
      // 表示四舍五入
      val int: Int = (d2 + 0.5).toInt
      println(int)

      //    （2）强转符号只针对于最近的操作数有效，往往会使用小括号提升优先级
      println(10 * 3.5 + 6 * 1.5.toInt)
      println((10 * 3.5 + 6 * 1.5).toInt)

      //    （1）基本类型转String类型（语法：将基本类型的值+"" 即可）
      val string : String = 10.0.toString
      println(string)

      val str : String = 1 + ""
    println(str)

//    （2）String类型转基本数值类型（语法：s1.toInt、s1.toFloat、s1.toDouble、    s1.toByte、s1.toLong、s1.toShort）
      val double : Double= "3.012".toDouble
    println(double +1)
    println(double.toInt)

    // 不能直接将小数类型的字符串转换为整数  需要先转换为double再转换int
    // println("3.14".toInt)

    // 标记为f的float数能够识别
//    println("12.6f".toFloat)

    // 将int值130强转为byte  值为多少

    // 0000 0000 ..16.. 1000 0010   => 表示int的130
    val test = 130

    // 1000 0010
    println(test.toByte)

  }
}
