package ru.seidzi.graffe.conf

import org.apache.spark.sql.{SQLContext, SparkSession}

class InitConfiguration {
  def init(): SQLContext = {
    val spark = SparkSession
      .builder()
      .appName("Giraffe")
//      .master("local")
      //      .enableHiveSupport() //TODO: future for diff between hive and ext schemas
      .getOrCreate()

    new SQLContext(spark.sparkContext)
  }
}
