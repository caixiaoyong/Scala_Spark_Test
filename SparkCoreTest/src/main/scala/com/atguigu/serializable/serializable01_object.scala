package com.atguigu.serializable

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * rdd序列化
 * @author CZY
 * @date 2021/11/29 20:19
 * @description serializable01_object
 */
object serializable01_object {
  def main(args: Array[String]): Unit = {
    //TODO 1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest")
      .setMaster("local[*]")
      // 替换默认的序列化机制
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      // 注册需要使用kryo序列化的自定义类
      .registerKryoClasses(Array(classOf[User]))

    //TODO 2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //在driver端创建user的对象
    val user1 = new User()
    val user2 = new User()
    user1.name ="zhangsan"
    user2.name ="lisi"

    val userRDD1: RDD[User] = sc.makeRDD(List(user1,user2))

    //3.1 在executor端执行算子调用user 因为没有实现序列化 所以会报java.io.NotSerializableException
    val value: RDD[User] = userRDD1.map(user => {
      user.age += 1
      user
    })
    //在executor端执行算子调用user
    userRDD1.foreach(user=>println(user.name))

    //3.2 打印，RIGHT （因为没有传对象到Executor端）
    val userRDD2: RDD[User] = sc.makeRDD(List())
    //userRDD2.foreach(user => println(user.name))

    //3.3 打印，ERROR Task not serializable
    //注意：此段代码没执行就报错了,因为spark自带闭包检查
    userRDD2.foreach(user => println(user.name+" love "+user1.name))


    //TODO 3.关闭连接
    sc.stop()
  }
}
case class User(){
  var name:String = _
  var age:Int = _
}
