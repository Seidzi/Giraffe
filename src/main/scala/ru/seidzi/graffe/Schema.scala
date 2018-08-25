package ru.seidzi.graffe

import org.apache.spark.sql.types.StructType
import org.slf4j.{Logger, LoggerFactory}
import ru.seidzi.graffe.conf.InitConfiguration

object Schema {
  private val LOGGER: Logger = LoggerFactory.getLogger(Schema.getClass)

  private val spark = new InitConfiguration().init()

  def getExtSchema(baseArgs: BaseArgs): StructType = {
    try {
      Class.forName(baseArgs.getDriver)
    } catch {
      case e: ClassNotFoundException => LOGGER.error(e.getStackTraceString)
        stopSpark()
        System.exit(-1)
    }

    spark.read.format("jdbc").
      option("url", baseArgs.getJdbc).
      option("driver", baseArgs.getDriver).
      option("useUnicode", "true").
      option("continueBatchOnError", "true").
      option("useSSL", "false").
      option("user", baseArgs.getUsername).
      option("password", baseArgs.getPassword).
      option("dbtable", baseArgs.getQuery).
      load().schema
  }

  def stopSpark(): Unit = {
    spark.sparkSession.stop()
    spark.sparkSession.close()
  }
  //TODO: consider the partition and bucket columns
  def getHiveSchema(baseArgs: BaseArgs): StructType = {
    spark.sql("select * from " + baseArgs.getDDLHivetbl + " where 1=0").schema
  }
}
