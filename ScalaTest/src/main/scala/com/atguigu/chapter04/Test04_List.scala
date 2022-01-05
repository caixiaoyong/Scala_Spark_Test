package com.atguigu.chapter04

/**
 *
 * @author CZY
 * @date 2021/11/22 10:52
 * @description Test04_List
 */
object Test04_List {
  def main(args: Array[String]): Unit = {
    //    （1）List默认为不可变集合
    val list = List(1, 2, 4, 1, 0, "hello", 'c')
    //    （2）创建一个List（数据有顺序，可重复）
    val list1 = List(1, 2, 3, 5, 5, 6)

    list1.sliding(2,3).foreach(println)
    println("=============")
    //    （3）遍历List
    list.foreach(println)
    //    （4）List增加数据
    val list2: List[Any] = list :+ 1
    println("list2= "+list2)

    //  :: 是将元素当成一个整体插入进去
    val ints: List[Int] = 2 :: list1
    println("ints= "+ints)

    val list3: List[Any] = ints :: list1
    println("list3= "+list3)
    //    （5）集合间合并：将一个整体拆成一个一个的个体，称为扁平化,对应炸裂函数
    val list4: List[Any] = ints ::: list1
    println("list4= "+list4)
    //    （6）取指定数据。不可变集合不能使用apply方法修改数据。
    val i: Any = list4(0)
    println(i)
    //    （7）空集合Nil:用于添加数据，会创建一个新的集合
    val ints1: List[Int] = 1 :: 2 :: 3 :: 4 :: Nil
    println(ints1)

  }
}
