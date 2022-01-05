package  com.atguigu.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Window设置窗口大小和滑动窗口的时间
 * @author CZY
 * @date 2021/12/6 18:34
 * @description SparkStreaming07_window
 */
object SparkStreaming07_window {
  def main(args: Array[String]): Unit = {
    //TODO 1. 初始化Spark配置信息
    val conf: SparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")
    //TODO 2. 初始化SparkStreamingContext 这里批次时间设置3s
    val ssc = new StreamingContext(conf, Seconds(3))

    //1. 对接数据源 Hadoop102 9999端口
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop103", 9999)

    //2. 先切割 再变换
    val word2oneDStream: DStream[(String, Int)] = lines.flatMap(_.split(" ")).map((_, 1))

    //3. 进行开窗操作，设置窗口范围为12s 滑动步长为6s
    val word2oneByWindow: DStream[(String, Int)] = word2oneDStream.window(Seconds(12), Seconds(6))

    //4. 对开过窗的流进行按照key聚合value，求出相同单词的个数
    val word2CountStream: DStream[(String, Int)] = word2oneByWindow.reduceByKey(_ + _)
    word2CountStream.print()


    //TODO 3. 启动StreamingContext，并且阻塞主线程，一直执行
    ssc.start()
    ssc.awaitTermination()
  }
}
