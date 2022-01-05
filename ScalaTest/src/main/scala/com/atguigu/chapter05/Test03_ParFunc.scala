package com.atguigu.chapter05

/**
 * 偏函数
 * @author CZY
 * @date 2021/11/23 20:26
 * @description Test03_ParFunc
 */
object Test03_ParFunc {
  def main(args: Array[String]): Unit = {
    // 定义一个偏函数  传入一个集合  返回集合第二个元素
    val func1 = new PartialFunction[List[Int], Option[Int]] {
      //第一个泛型表示匹配什么，定义匹配条件
      override def isDefinedAt(x: List[Int]): Boolean = {
        x match {
          case first :: second :: list => true
          case _ => false
        }
      }

      //第二个泛型表示最终返回结果是什么，返回匹配结果
      override def apply(v1: List[Int]): Option[Int] = {
        v1 match {
          case first :: second :: list => Some(second)
        }
      }
    }
    //简化偏函数:删掉match
    val function: List[Int] => Option[Int] = {
      case first :: second :: list => Some(second)
      case _ => None
    }

    //    将该List(1,2,3,4,5,6,"test")中的Int类型的元素加一，并去掉字符串。
    val list = List(1, 2, 3, 4, 5, 6, "test")
    //    list.filter((n:Any)=>{
    //      n match {
    //        case s:String => false
    //        case i :Int => true
    //        case _ => true
    //      }
    //    })
    //删除match，使其为偏函数
    val list1: List[Any] = list.filter({
      case s: String => false
      case i: Int => true
      case _ => true
    })

    println(list1)
    //方法一：
    val list2 = list1.map((i: Any) => i match {
      case i: Int => i + 1
      case x: Any => x
    })
    println(list2)

    //方法二：
    // 偏函数会自动判断是否保留当前数据 如果匹配不上,会自动删除
    val list3: List[Int] = list.collect({
      case i: Int => i + 1
    })

    println(list3)
  }
}
