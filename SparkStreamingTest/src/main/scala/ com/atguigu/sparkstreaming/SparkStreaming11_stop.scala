package  com.atguigu.sparkstreaming

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext, StreamingContextState}

/**
 *
 * @author CZY
 * @date 2021/12/6 20:33
 * @description SparkStreaming11_stop
 */
object SparkStreaming11_stop {
  def main(args: Array[String]): Unit = {
    //TODO 1. 创建SparkConf配置文件，并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")

    //4.1 先设置优雅关闭参数为true
    conf.set("spark.streaming.stopGracefullyOnShutdown","true");

    //TODO 2. 利用SparkConf创建StreamingContext对象
    val ssc = new StreamingContext(conf, Seconds(3))

    //1. 对接数据源 Hadoop103 9999端口
    val lineDstream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop103", 9999)

    //2. 切分数据 转换数据结构 按key聚合value 得到相同单词的个数
    val resultDStream: DStream[(String, Int)] = lineDstream.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    //3. 打印输出结构流
    resultDStream.print()

    //4.2 开启线程监控程序
    new Thread(new MonitorStop(ssc)).start()

    //TODO 3.启动StreamingContext，并阻塞主线程，一直执行
    ssc.start()
    ssc.awaitTermination()
  }
}

//4.3 创建一个继承Runnable的线程类
class MonitorStop(ssc: StreamingContext) extends Runnable{
  override def run(): Unit = {
    //获取HDFS文件系统
    //FileSystem.get(new URI(""))
    val fs: FileSystem = FileSystem.get(new URI("hdfs://hadoop102:8020"),new Configuration(),"atguigu")

    while (true){
      // 每间隔5s,去hdfs监控一次
      Thread.sleep(5000)

      //监控hdfs上是否出现关闭信号
      val result: Boolean = fs.exists(new Path("hdfs://hadoop102:8020/stop1206"))

      if(result){
        //获取当前ssc的任务状态
        val state: StreamingContextState = ssc.getState()
        //如果hdfs上出现关闭信号,并且当前ssc的运行状态是激活状态
        if(state==StreamingContextState.ACTIVE){

          //优雅的关闭当前ssc程序
          ssc.stop(true,true)
          //退出当前进程
          System.exit(0)
        }
      }
    }
  }
}
