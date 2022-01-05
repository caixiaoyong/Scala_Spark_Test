package com.atguigu.sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 *
 * @author CZY
 * @date 2021/12/4 13:55
 * @description SparkSQL12_Hive
 */
object SparkSQL12_Hive {
  def main(args: Array[String]): Unit = {
    // 修改程序系统的用户名称  对应访问权限
    System.setProperty("HADOOP_USER_NAME", "cy")

    //TODO 1 创建SparkConf配置文件,并设置App名称
    val conf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")
    //TODO 2 利用SparkConf创建sparksession对象  #获取外部hive的支持
    val spark: SparkSession = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()

    // 3 连接外部Hive，并进行操作
    spark.sql("show tables").show(1000,false)
   // spark.sql("create table user3(id int, name string)").show() 上传到hdfs的/user/hive/warehouse下
   // spark.sql("insert into user3 values(1,'zs')")
    spark.sql("select * from user3").show

    /**
     * idea操作外部hive
     * 1.导入pom依赖  (1) spark-sql (2) spark-hive  (3) mysql连接驱动
     * 2. 将hive-site.xml 拷贝一份到项目的类路径下
     * 3. .enableHiveSupport() 创建sparksession对象的时候 要获取外部hive支持
     * 4. 将hdfs-site.xml,core-site.xml 拷贝一份到项目的类路径下(不是必须)
     */
    //TODO 3 关闭资源
    spark.stop()
  }
}
