package com.atguigu.chapter02

/**
  *
  * @author CZY
  *         2021/11/18 22:43
  *         LambdaFun_Test
  */
object LambdaFun_Test {
  def main(args: Array[String]): Unit = {
    //需求1：定义一个函数 f2(int,char,string),如果传入是(0,‘0’,“”),则返回false，否则返回true
    def f1(a: Int, b: Char, c: String): Boolean = {
      if (a == 0 && b == '0' && c == "") {
         false
      }
       true
    }

    def f2(a: Int, b: Char, c: String) = a != 0 || b != '0' || c != ""

    (a: Int, b: Char, c: String)=>a != 0 || b != '0' || c !=""


    println(f1(0, '0', ""))

    //需求2：定义一个函数 f2(int)(char)(string),如果传入是(0)(‘0’)(“”),则返回false，否则返回true
    def fa(x: Int) = {
      def fb(y: Char) = {
        def fc(z: String): Boolean = {
          if (x == 0 && y == '0' && z == "") {
            return false
          }
          return true
        }

        fc _
      }

      fb _
    }

    val bool: Boolean = fa(0)('0')("")
    println(bool)


      //化简：匿名函数
      def fa1(x: Int) = {
        def fb(y: Char) = {
          def fc(z: String): Boolean = {
            if (x == 0 && y == '0' && z == "") {
              return false
            }
            return true
          }

          fc _
        }

        fb _
      }

      //需求2：柯里化写法
      def func(i:Int)(c:Char)(s:String)={
        i != 0 || c != '0' || s !=""
      }
  }
}
