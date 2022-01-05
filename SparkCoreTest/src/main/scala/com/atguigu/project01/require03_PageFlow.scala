package com.atguigu.project01

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 页面单跳转化率统计
 * 1-2/ 1   2-3/2   3-4/3   4-5/4    5-6/5    6-7/6
 *
 * @author CZY
 * @date 2021/12/2 10:53
 * @description require03_PageFlow
 */
object require03_PageFlow {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //1. 从文件读取创建RDD
    val rdd: RDD[String] = sc.textFile("D:\\BigData\\SparkCoreTest\\input\\user_visit_action.txt")

    //2. 封装样例类 将切割出来的数组封装进样例类
    val actionRdd: RDD[UserVisitAction] = rdd.map(action => {
      val datas: Array[String] = action.split("_")
      UserVisitAction(
        datas(0),
        datas(1),
        datas(2),
        datas(3),
        datas(4),
        datas(5),
        datas(6),
        datas(7),
        datas(8),
        datas(9),
        datas(10),
        datas(11),
        datas(12)
      )
    })

    //3.定义要统计的页面 分母
    val ids = List("1", "2", "3", "4", "5", "6", "7")

    //3.1 定义对分子过滤集合 然后转换结构
    val zipIds: List[String] = ids.zip(ids.tail).map {
      case (t1, t2) => t1 + "-" + t2
    }

    //分母的广播变量
    val bdIds: Broadcast[List[String]] = sc.broadcast(ids)

    //4.计算分母 过滤出符合ids的page_id (最后一页只能为分子 所以用init排除)
    val idsMap: Map[String, Long] = actionRdd
      .filter(action => bdIds.value.init.contains(action.page_id))
      //结构变换 统计页面总次数 然后将array转成Map方便后续使用
      .map(action => (action.page_id, 1L))
      .reduceByKey(_ + _).collect().toMap

    val value: RDD[(String, Long)] = actionRdd
      .filter(action => bdIds.value.init.contains(action.page_id))
      //结构变换 统计页面总次数 然后将array转成Map方便后续使用
      .map(action => (action.page_id, 1L))
      .reduceByKey(_ + _)
    val tuples: Array[(String, Long)] = value.collect()
//     idsMap.foreach(println)

    //5.计算分子
    //5.1 按照session进行分组
    val sessGroupRdd: RDD[(String, Iterable[UserVisitAction])] = actionRdd.groupBy(_.session_id)

    //5.2 将分组后的数据根据时间进行排序（升序）
    // (d13fb042-bf65-4b12-bdcb-caee280ee866,List(1-2))
    val page2pageRdd: RDD[(String, List[String])] = sessGroupRdd.mapValues(datas => {
      //1 将迭代器转成list,然后按照行动时间升序排序
      val actions: List[UserVisitAction] = datas.toList.sortBy(_.action_time)
      //2 转变数据结构 UserVisitAction => UserVisitAction.pageid
      val pageidList: List[String] = actions.map(_.page_id)
      //3 页面list拉链获得单跳元组  (1,2,3,4).zip(2,3,4) =>(1,2),(2,3),(3,4)
      val page2PageList: List[(String, String)] = pageidList.zip(pageidList.tail)
      //4 再次转变结构 元组变字符串 (1,2) => 1-2
      val pageJumpCounts: List[String] = page2PageList.map {
        case (elem1, elem2) => elem1 + "-" + elem2
      }
      //5 对分子也进行过滤下,提升效率 只保留1-2,2-3,3-4,4-5,5-6,6-7的数据
      pageJumpCounts.filter(str => zipIds.contains(str))//过滤出分子集合zipIds里有的数据
    })

    val pageFlowRdd: RDD[List[String]] = page2pageRdd.map(_._2)
//    pageFlowRdd.collect().foreach(println)

    //5.3 聚合统计结果
    val reduceFlowRdd: RDD[(String, Int)] = pageFlowRdd.flatMap(word => word).map((_, 1)).reduceByKey(_ + _)
//    reduceFlowRdd.collect().foreach(println)

    //6. 求单跳转换率
    reduceFlowRdd.foreach{
      case (p2p,cnt)=>{
        val pages: Array[String] = p2p.split("-")
        val prePagecnt: Long = idsMap(pages(0))
        println(p2p+"的转化率为= "+cnt.toDouble / prePagecnt)
      }
    }
    //TODO 3.关闭连接
    sc.stop()
  }
}
