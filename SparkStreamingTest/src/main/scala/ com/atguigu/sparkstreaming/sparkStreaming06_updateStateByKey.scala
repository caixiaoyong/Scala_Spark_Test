package  com.atguigu.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 使用updateStateByKey 实现累加WordCount。
 * @author CZY
 * @date 2021/12/6 18:12
 * @description sparkStreaming06_updateStateByKey
 */
object sparkStreaming06_updateStateByKey {


  def main(args: Array[String]): Unit = {
    //TODO 1. 初始化Spark配置信息
    val conf: SparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")
    //TODO 2. 初始化SparkStreamingContext 这里批次时间设置3s
    val ssc = new StreamingContext(conf, Seconds(3))

    //使用updateStateByKey要设置检查点目录 保存状态
    ssc.checkpoint("D:\\BigData\\Scala_SparkTest\\SparkStreamingTest\\checkpoint")

    //4 创建DStream 获取一行数据
    val lineDStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop103", 9999)

    //5 切割=》变换
    val word2oneDStream: DStream[(String, Int)] = lineDStream.flatMap(_.split(" ")).map((_, 1))

    //6 使用updateStateByKey来更新状态，统计从运行开始以来单词总的次数
    val result: DStream[(String, Int)] = word2oneDStream.updateStateByKey(updateFunc)

    result.print()

    //TODO 3. 启动StreamingContext，并且阻塞主线程，一直执行
    ssc.start()
    ssc.awaitTermination()
  }

  //定义更新状态方法，参数seq为当前批次单词次数，state为以往批次单词次数
  // 方法返回类型要求为option 所以要使用Some返回value
  def updateFunc = (seq:Seq[Int], state:Option[Int])=>{
    //获取当前批次单词的和
    val currentCount: Int = seq.sum
    //获取历史状态的数据
    val stateCount: Int = state.getOrElse(0)
    //将当前批次的和加上历史状态的数据和,返回
    Some(currentCount+stateCount)
  }
}
