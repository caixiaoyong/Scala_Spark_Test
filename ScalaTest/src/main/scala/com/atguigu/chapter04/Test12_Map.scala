package com.atguigu.chapter04

/**
 * 集合计算高级函数
 * @author CZY
 * @date 2021/11/22 20:13
 * @description Test12_Map
 */
object Test12_Map {
  def main(args: Array[String]): Unit = {
    val list = List(4, 2, 6, 7, 8, 1, 9)

    //    （1）过滤
    //    遍历一个集合并从中获取满足指定条件的元素组成一个新的集合
    list.filter((i: Int) => i % 2 == 1)
    // 化简形式
    println(list.filter(_ % 2 == 1))

    //    （2）转化/映射（map）
    //    将集合中的每一个元素映射到某一个函数
    // 将集合中的元素*2
    val ints = list.map((i: Int) => i * 2)
    println(ints)


    // 改变数据类型  ("我是",int)
    val tuples = list.map((i: Int) => ("我是", i))
    println(tuples)

    //    （3）扁平化 :将一个整体拆成一个一个的个体
    val list1 = List(List(1, 2, 3), List(4, 5, 6))
    println(list1.flatten)

    //    （4）扁平化+映射 注：flatMap相当于先进行map操作，在进行flatten操作
    //    集合中的每个元素的子元素映射到某个函数并返回新集合
    val strings: List[String] = List("hello world", "hello scala spark", "hello hive")
    // 使其返回List("hello","world","hello","scala","spark"...)
    val list2 = strings.map((line: String) => {
      val strings1 = line.split(" ")
      strings1.toList//转换成list数组
    })
    println(list2)
    println(list2.flatten)

    //直接使用flatMap
    println(strings.flatMap(x => x.split(" ")))
    // 化简形式
    strings.flatMap(_.split(" "))

    //    （5）分组(group)
    //    按照指定的规则对集合的元素进行分组
    println(list.groupBy((i: Int) => i % 2))
    println(list.groupBy((i: Int) => i % 2==0))//为0则true，否则false
    // 化简形式
    list.groupBy(_%2==0)



  }
}
