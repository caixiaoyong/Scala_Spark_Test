package com.atguigu.chapter04

/**
 *
 * @author CZY
 * @date 2021/11/22 20:51
 * @description Test13_Reduce
 */
object Test13_Reduce {
  def main(args: Array[String]): Unit = {
    //    （6）简化（归约）
    val list = List(4, 5, 9, 7, -2, 3)
    // 归约指将上次计算的结果值和下一个元素值进行合并
    //val i = list.reduce((res: Int, elem: Int) => res + elem)
    val i = list.sum

    println(i)

    // reduce的归约最开始的结果值 是第一个元素
    val list1 = List(1, 2, 3, 4, 5)
    val i1 = list1.reduce((res: Int, elem: Int) => res - elem)
    println(i1)
    // 化简形式
    list1.reduce(_ - _)

    //1-(2-(3-(4-5)))
    list1.reduceRight((res: Int, elem: Int) => res - elem)
    println("reduceRight= "+list1.reduceRight(_ - _))

    //    （7）折叠
    // fold 带有初始值的归约 初始值类型和元素类型必须保持一致
    val i2 = list1.fold(10)((res: Int, elem: Int) => res - elem)
    println(i2)
    // 化简形式
    list1.fold(10)(_-_)

    // 改变数据类型的规约
    //val list1 = List(1, 2, 3, 4, 5)  归约结果为 ("和为",sum)
    val tuple = list1.foldLeft(("我们", 0))((res: (String, Int), elem: Int) => (res._1, res._2 + elem))
    println(tuple)
    // 化简形式
    list1.foldLeft(("我们",0))((res,elem)=>(res._1,res._2+elem))

    val list2 = List(1, 2, 3, 4)
    println(list2.foldLeft(1)((x, y) => x - y))
    println(list2.foldRight(10)((x, y) => x + y))

  }
}
