package com.atguigu.chapter04

/**
 * 集合多维数组
 * @author CZY
 * @date 2021/11/21 23:00
 * @description Test03_ArrayDim
 */
object Test03_ArrayDim {
  def main(args: Array[String]): Unit = {
    val array = new Array[Array[Int]](3)
    array(0)=Array(1,2,3)
    array(1) = Array(4,5,6)
    array(2) = Array(7,8,9)

    for (elems <- array) {
      for (elem <- elems) {
        print(elem + "\t")
      }
      println()
    }

    //scala中封装了多维数组方法Array.ofDim[Int](n1, n2)
    val array1: Array[Array[Int]] = Array.ofDim[Int](3, 4)
    for (elems <- array1) {
      for (elem <- elems) {
        print(elem + "\t")
      }
      println()
    }
  }
}
