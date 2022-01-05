package  com.atguigu.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 *  无状态转化操作
 *  通过Transform可以将DStream每一批次的数据直接转换为RDD的算子操作
 * @author CZY
 * @date 2021/12/6 16:31
 * @description SparkStreaming05_Transform
 */
object SparkStreaming05_Transform {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(3))

    //3 创建DStream
    val lineDStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop103", 10001)

    // 在Driver端执行，全局一次
    println("111111111111111":+Thread.currentThread().getName)

    //4 转换为RDD操作
    val word2SumDFStream: DStream[(String, Int)] = lineDStream.transform(
      rdd => {
        // 在Driver端执行(ctrl+n JobGenerator)，一个批次一次 3s执行一次
        println("2222222222222" :+ Thread.currentThread().getName)

        val result: RDD[(String, Int)] = rdd.flatMap(_.split(" "))
          .map(x => {
            // 在Executor端执行，每个单词执行一次
            println("333333333333333" :+ Thread.currentThread().getName)
            (x, 1)
          })
          .reduceByKey(_ + _)

        result
      }
    )
    //5 打印
    word2SumDFStream.print()

    //启动StreamContext 并阻塞主线程，一直执行
    ssc.start()
    ssc.awaitTermination()
  }
}
