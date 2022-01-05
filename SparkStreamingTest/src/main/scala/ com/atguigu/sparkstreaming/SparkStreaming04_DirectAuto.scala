package  com.atguigu.sparkstreaming

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 *
 * @author CZY
 * @date 2021/12/6 11:04
 * @description SparkStreaming04_DirectAuto
 */
object SparkStreaming04_DirectAuto {
  def main(args: Array[String]): Unit = {
    //TODO 1 创建SparkConf 并且设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")
    //TODO 2 创建StreamingContext
    val ssc = new StreamingContext(conf, Seconds(3))

    //4.定义kafka参数：kafka集群地址、消费者组名称、key序列化、value序列化
    var kafkaPara = Map[String,Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG->"hadoop102:9092,hadoop103:9092,hadoop104:9092",
      ConsumerConfig.GROUP_ID_CONFIG->"atguiguGroup",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG->"org.apache.kafka.common.serialization.StringDeserializer",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG->classOf[StringDeserializer]
    )

    //3.读取Kafka数据创建DStream
    // LocationStrategies：根据给定的主题和集群地址创建consumer
    // LocationStrategies.PreferConsistent：持续的在所有Executor之间分配分区
    // ConsumerStrategies：选择如何在Driver和Executor上创建和配置Kafka Consumer
    // ConsumerStrategies.Subscribe：订阅一系列主题
    val kafkaDStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String,String](Set("testTopic"), kafkaPara)
    )

    //5.
    val valueDStream: DStream[String] = kafkaDStream.map(record => record.value())

    //6. 计算WordCount
    valueDStream.flatMap(_.split(" "))
        .map((_,1))
        .reduceByKey(_+_)
        .print()

    //TODO 3 启动StreamingContext，并且阻塞主线程，一直执行
    ssc.start()
    ssc.awaitTermination()
  }
}
