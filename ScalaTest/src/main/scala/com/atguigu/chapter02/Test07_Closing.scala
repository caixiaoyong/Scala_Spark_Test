package com.atguigu.chapter02

/**
  * 闭包
  * @author CZY
  * @date 2021/11/19 19:15
  * @description Test07_Closing
  */
object Test07_Closing {
  def main(args: Array[String]): Unit = {
    // 两个数相加
    // 泛用性强 适用性差  每次都要填写两个参数
    def sumXY(x:Int,y:Int):Int = x + y

    val i: Int = sumXY(4, 5)
    println(i)

    // 应用于大量使用4加一个数的场景
    // 适用性强  泛用性差
    def sumByFour(y:Int):Int = 4 + y


    // 同时兼容泛用性和适用性
    def sumByX(x:Int) = {
      def sumByY(y:Int):Int = {
        x + y
      }

      sumByY _
    }

    val sumByFour1: Int => Int = sumByX(4)
}
}
