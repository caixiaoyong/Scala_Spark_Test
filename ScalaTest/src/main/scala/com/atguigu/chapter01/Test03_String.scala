package com.atguigu.chapter01

/**
  *
  * @author CZY
  * @date 2021/11/16 16:37
  * @description Test03_String
  */
object Test03_String {
  def main(args: Array[String]): Unit = {
//    1）以字母或者下划线开头，后接字母、数字、下划线
      var hello : String = ""
      var hello2 : String = ""
      var hello_ : String = ""
      var _hello : String = ""

//    （2）以操作符开头，且只包含操作符（+ - * / # !等）
      var - : String =""
      var & : String = ""
      var int :String =""

//    （3）用反引号`....`包括的任意字符串，即使是Scala关键字（39个）也可以
      var `throw` : String =""


//    （1）字符串，通过+号连接
      println("hello"+"world"+123)
//    （2）重复字符串拼接
      println(("hello"+123)*10)
//    （3）printf用法：字符串，通过%传值。
      printf("%d岁 %s 的祖国 \n",100,"伟大")
//    （4）字符串模板（插值字符串）：通过$获取变量值
      val name = "zuguo"
      val age = 100
      val s1 = s"name: $name,age:${age}"
      println(s1)

      val s2 = s"name:${name+1},age:${age + 2}"
      println(s2)

//     （5）长字符串  原始字符串
      println("I" +
       "am"+
        "a"+
        "boy")

    println(
      """
        |I
        |am
        |a
        |boy
      """.stripMargin)

    s"""
      |$name
      |${age+1}
    """.stripMargin
  }
}
