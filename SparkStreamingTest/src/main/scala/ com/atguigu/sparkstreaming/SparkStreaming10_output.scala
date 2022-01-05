package  com.atguigu.sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 *
 * @author CZY
 * @date 2021/12/6 19:48
 * @description SparkStreaming10_output
 */
object SparkStreaming10_output {
  def main(args: Array[String]): Unit = {
    //TODO 1. 初始化Spark配置信息
    val conf: SparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")
    //TODO 2. 初始化SparkStreamingContext 这里批次时间设置3s
    val ssc = new StreamingContext(conf, Seconds(3))

    //1. 对接数据源 Hadoop103 9999端口
    val lineDStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop103", 9999)

    //2. 切分数据 再转换结构 聚合求出相同单词的个数
    val resultDStream: DStream[(String, Int)] = lineDStream.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    //driver端 全局执行一次
    println("111111111:" + Thread.currentThread().getName)


    //在做项目时，要把数据写到Mysql时，需要写jdbc连接，因为数据在executor端，
    // 所以在executor要使用连接将数据传送到mysql 。jdbc连接不支持Spark序列化的，所以不能在dirver端，最后只能在executor端，如果使用foreach那么对于每种单词都会建立一个连接，造成资源浪费，因此采用foreachPartition按照迭代器的整个分区的数据建立数据库连接 可以节省资源。
    resultDStream.foreachRDD(
      rdd=>{
        //driver端  每个批次执行一次
        println("222222222:" + Thread.currentThread().getName)

        println("--------------------------")
        rdd.foreachPartition(
          iter=>{
            //创建数据库连接
            //DriverManager.getConnection("jdbc:mysql:///db1", "root", "123456");
            //5.2 企业代码
            //5.2.1 获取连接
            //5.2.2 操作数据，使用连接写库
            //5.2.3 关闭连接


            //使用连接将这个分区的每条数据写到数据
            iter.foreach(println)
          }
        )
      }
    )
    /*resultDStream.foreachRDD(
      rdd=>{
        //driver端  每个批次执行一次
        println("222222222:" + Thread.currentThread().getName)

        println("--------------------------")
        rdd.foreach(x=>{
          //executor端  几种单词打印几个
          println("333333333:" + Thread.currentThread().getName)
          println(x)
        })
      }
    )*/



    //TODO 3. 启动StreamingContext，并且阻塞主线程，一直执行
    ssc.start()
    ssc.awaitTermination()
  }
}
