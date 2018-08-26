package ru.seidzi.graffe

import com.beust.jcommander.{JCommander, ParameterException}

object Main {
  val baseArgs = new BaseArgs()

  def main(args: Array[String]): Unit = {

    val jc = new JCommander()
    jc.setProgramName("Giraffe")
    jc.addObject(baseArgs)
    try {
      jc.parse(args: _*)
    } catch {
      case e: ParameterException => print(e.getStackTraceString)
        jc.usage()
        System.exit(0)
    }


    if (baseArgs.getHelp) {
      jc.usage()
      System.exit(0)
    } else {
      noName()
    }
    Schema.stopSpark()
  }

  //TODO: change method name
  def noName(): Unit = {
    val extSchema = Schema.getExtSchema(baseArgs)
    //TODO: create option to diff between hive and ext schemas
    //    if (!baseArgs.getHivetbl.equals("")){
    //      val hiveSchema = Schema.getHiveSchema(baseArgs)
    //      diff(extSchema, hiveSchema)
    //    }else{
    if (baseArgs.getFetchSchema){
      for (i <- 0 until extSchema.length) {
        println( extSchema.apply(i).name + " " + extSchema.apply(i).dataType.sql )
      }
    } else {
      new DDL().create(extSchema, baseArgs)
    }
    //    }
  }
}
