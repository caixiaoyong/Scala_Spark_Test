package com.atguigu.exercise

/**
  *
  * @author CZY
  */
object Problems {
  def main(args: Array[String]): Unit = {
    //打印一个9*9乘法口诀表
   /* for(i<-1 to 9 ){
      for (j<-1 to i){
        print(s"$i*$j="+i*j+"\t")
      }
      println()
    }*/

    for(i<-1 to 9;j<-1 to i){
      print(s"$i * $j = ${j*i}\t")
      if(j==i){
        println()
      }
    }

    //打印  *  九层
    //     ***
    //    *****

    for(i<-1 to 9){
      println(" "*(9-i)+"*"*((2*i)-1))
    }

    for{
      i <- 1 to 9
      j = 9-i
      k = 2*i - 1
    } {
       println(" "*j+"*"*k)
      }
  }
}
