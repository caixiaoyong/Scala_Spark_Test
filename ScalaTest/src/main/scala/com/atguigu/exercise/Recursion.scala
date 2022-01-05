package com.atguigu.exercise

/**
 *
 * @author CZY
 * @date 2021/11/20 8:50
 * @description Recursion
 */
object Recursion {
  def main(args: Array[String]): Unit = {
    def rec(i:Int,res:Int=1): Int ={
      if (i==1){
        res
      } else {
        println(res)
       rec(i - 1, res * i)
      }
    }

    println(rec(5))
  }
}
