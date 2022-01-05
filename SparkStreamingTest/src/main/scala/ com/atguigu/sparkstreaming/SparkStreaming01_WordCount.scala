package  com.atguigu.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 *
 * @author CZY
 * @date 2021/12/4 23:09
 * @description SparkStreaming01_WordCount
 */
object SparkStreaming01_WordCount {
  def main(args: Array[String]): Unit = {
    //TODO 1. 初始化Spark配置信息
    val conf: SparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")
    //TODO 2. 初始化SparkStreamingContext 这里批次时间设置3s
    val ssc = new StreamingContext(conf, Seconds(3))

    //3.通过监控端口创建DStream，读进来的数据为一行行
    val lineDStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop103", 9999)

    //3.1 将每一行数据做切分，形成一个个单词
    val wordDStream: DStream[String] = lineDStream.flatMap(_.split(" "))

    //3.2 将单词映射成元组（word,1）
    val word2SumStream: DStream[(String, Int)] = wordDStream.map((_, 1))

    //3.3 将相同的单词次数做统计
    val word2SumDStream: DStream[(String, Int)] = word2SumStream.reduceByKey(_ + _)

    //3.4 打印
    word2SumDStream.print()


    //TODO 3. 启动StreamingContext，并且阻塞主线程，一直执行
    ssc.start()
    ssc.awaitTermination()
  }
}
