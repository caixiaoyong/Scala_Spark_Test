package com.atguigu.chapter03

/**
  * 包对象
  * @author CZY
  * @date 2021/11/19 19:33
  * @description Test01_Package
  */
object Test01_Package {
  def main(args: Array[String]): Unit = {
    // 使用包对象的属性和方法
    println(packageName)
    sayHi()
  }
}



// scala特有的包定义方法  可以不和文件夹对应
package com{
  import com.shanghai.Inner
  object Outer{
    def main(args: Array[String]): Unit = {
      println("hi Outer")
      // 外部调用内部的对象
      // 需要手动导包
      Inner.main(args)

      // 打印出现冲突的包对象属性
      // 内部的包对象会覆盖外部的包对象属性
      println(packageName)
    }
  }

  package shanghai{

    object Inner{
      def main(args: Array[String]): Unit = {
        println("hi Inner")
        Outer.main(args)

      }
    }
  }
}

package object com{
  val packageName:String = "com"
}

