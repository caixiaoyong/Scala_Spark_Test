package com.atguigu.sparkstreaming

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

/**
 *
 * @author CZY
 * @date 2021/12/6 19:20
 * @description SparkStreaming09_reduceByKeyAndWindow
 */
object SparkStreaming09_reduceByKeyAndWindow {
  def main(args: Array[String]): Unit = {
    //TODO 1. 初始化Spark配置信息
    val conf: SparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")
    //TODO 2. 初始化SparkStreamingContext 这里批次时间设置3s
    val ssc = new StreamingContext(conf, Seconds(3))

    ssc.checkpoint("D:\\BigData\\Scala_SparkTest\\SparkStreamingTest\\checkpoint")
    //1. 对接数据源 hadoop103 9999
    val lineDStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop103", 9999)

    //2. 先切割 再变换结构
    val word2oneDStream: DStream[(String, Int)] = lineDStream.flatMap(_.split(" ")).map((_, 1))

    //3. 对word2oneDStream进行开窗操作,窗口大小12s,滑动步长6s,并且按照key聚合
    val wordCounts = word2oneDStream.reduceByKeyAndWindow(
      (x: Int, y: Int) => (x + y),
      (x: Int, y: Int) => (x - y), //反向reduce
      Seconds(12),
      Seconds(6),
      new HashPartitioner(2),// 使用过滤函数 不返回为0的数据
      (x:(String,Int))=>x._2 > 0
    )

    //4. 打印
    wordCounts.print()


    //TODO 3. 启动StreamingContext，并且阻塞主线程，一直执行
    ssc.start()
    ssc.awaitTermination()
  }
}
