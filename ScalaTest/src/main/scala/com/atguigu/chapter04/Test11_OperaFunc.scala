package com.atguigu.chapter04

import scala.collection.mutable.ListBuffer

/**
 * 集合计算初级函数
 *
 * @author CZY
 * @date 2021/11/22 14:50
 * @description Test11_LowFunc
 */
object Test11_OperaFunc {
  def main(args: Array[String]): Unit = {
    val list: List[Int] = List(1, 5, -3, 4, 2, -1, -7, 6)
    val list1: ListBuffer[Int] = ListBuffer(1, 5, -3, 4, 2, -7, 6)
    //    （1）求和
    val sum: Int = list.sum
    println(sum)

    //    （2）求乘积
    val product: Int = list.product
    println(product)

    //    （3）最大值
    val max: Int = list.max
    println(max)

    //    （4）最小值
    val min: Int = list.min
    println(min)

    //    （5）排序
    // 1.sorted 对一个集合进行自然排序
    val sorted: List[Int] = list.sorted
    println(list)
    println("自然排序：" + sorted)

    //  修改排序规则 从大到小
    val ints: List[Int] = list.sorted(Ordering[Int].reverse)
    println("从大到小：" + ints)

    // 对元组进行排序
    val tuples = List(("hello", 10), ("hello", 9), ("hello", 11), ("world", 2), ("scala", 3), ("spark", 9))

    // 按照元组的默认字典序排列 结果按照第一个字母的字典序排序的
    val sorted1: List[(String, Int)] = tuples.sorted
    println("元祖自然排序：" + sorted1)

    // 2.sortBy 按照后面数字从小到大排序
    val tuples1 = tuples.sortBy((tuple: (String, Int)) => tuple._2) //一个二元组
    println("元祖按数字自然排序：" + tuples1)

    // 按照后面数字从大到小排序
    val tuples2 = tuples.sortBy((tuple: (String, Int)) => tuple._2)(Ordering[Int].reverse)
    println("元祖按数字从大到小：" + tuples2)
    // 化简形式
    tuples.sortBy(_._2)(Ordering[Int].reverse)

    // 3.sortWith 自定义排序规则
    val tuples3 = tuples.sortWith((left: (String, Int), Right: (String, Int)) => left._2 > Right._2)//两个二元组
    println("元祖按数字从大到小2："+tuples3)
    // 化简形式
    val tuples4 = tuples.sortWith(_._2 > _._2)
    println(tuples4)


  }
}
