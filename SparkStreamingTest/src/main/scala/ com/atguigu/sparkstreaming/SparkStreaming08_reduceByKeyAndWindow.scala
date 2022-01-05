package com.atguigu.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 使用reduceByKeyAndWindow设置窗口大小和滑动窗口的时间 ,并且按照key聚合
 *
 * @author CZY
 * @date 2021/12/6 18:56
 * @description SparkStreaming08_reduceByKeyAndWindow
 */
object SparkStreaming08_reduceByKeyAndWindow {
  def main(args: Array[String]): Unit = {
    //TODO 1. 初始化Spark配置信息
    val conf: SparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")
    //TODO 2. 初始化SparkStreamingContext 这里批次时间设置3s
    val ssc = new StreamingContext(conf, Seconds(3))

    //1. 对接数据源 hadoop103 9999
    val lineDStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop103", 9999)

    //2. 先切割 再变换结构
    val word2oneDStream: DStream[(String, Int)] = lineDStream.flatMap(_.split(" ")).map((_, 1))

    //3. 对word2oneDStream进行开窗操作,窗口大小12s,滑动步长6s,并且按照key聚合
    val wordCounts= word2oneDStream.reduceByKeyAndWindow( (x:Int, y:Int) => (x + y),Seconds(12),Seconds(6))

    //4. 打印
    wordCounts.print()


    //TODO 3. 启动StreamingContext，并且阻塞主线程，一直执行
    ssc.start()
    ssc.awaitTermination()
  }
}
