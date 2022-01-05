package com.atguigu.chapter02

import scala.annotation.tailrec

/**
  * 递归
  * @author CZY
  * 2021/11/19 11:54
  * Test08_Recursion
  */
object Test08_Recursion {
  def main(args: Array[String]): Unit = {

    // 递归
    // 1. 调用自身
    // 2. 跳出条件
    // 3. 填入的参数必须有规律
    // 4. 递归必须声明函数返回值类型
    def rec(n:Int):Int={
      if (n==1)1 else rec(n-1)*n
    }
    println(rec(5))

    // 尾递归优化
    def rec1(n:Int):Int ={
      def func1(n1:Int,res:Int):Int={
        if (n1 == 1){
          res
        }else{
          println(res)
          func1(n1-1,res * n1)
        }
      }
      func1(n,1)
    }
    println(rec1(5))

    @tailrec //判断下面函数是否是尾递归
    def rec2(n:Int,res:Int=1):Int={
      if (n == 1){
        res
      }else{
        println(res)
        rec2(n-1,res*n)
      }
    }

    println(rec2(5))


  }
}
