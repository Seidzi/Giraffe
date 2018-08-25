package ru.seidzi.graffe

import java.io.{BufferedWriter, File, FileWriter}

import org.apache.spark.sql.types.StructType
import org.slf4j.{Logger, LoggerFactory}

class DDL {
  private val LOGGER: Logger = LoggerFactory.getLogger(this.getClass)
  val ANSI_RESET = "\u001B[0m"
  val ANSI_RED = "\u001B[31m"

  def create(extSchema: StructType, baseArgs: BaseArgs): Unit = {
    var schema = "create"
    if (baseArgs.getDDLExternal)
      schema = schema + " external "
    schema = schema + "table " + baseArgs.getDDLHivetbl + " { \n"
    for (i <- 0 until extSchema.length) {
      schema = schema + "\t" + extSchema.apply(i).name + " " + extSchema.apply(i).dataType.sql
      if (i < extSchema.length - 1)
        schema = schema + ","
      schema = schema + "\n"
    }
    schema = schema + "} \n"
    if (!baseArgs.getDDLPart.equals(""))
      schema = schema + "PARTITIONED BY (" + baseArgs.getDDLPart + ") \n"
    if (!baseArgs.getDDLBucketing.equals("")) {
      if(schema.contains(baseArgs.getDDLBucketing)){
        schema = schema + "CLUSTERED BY (" + baseArgs.getDDLBucketing + ") INTO " + baseArgs.getDDLBucketingFigure + " BUCKETS \n"
      }else{
        LOGGER.error(ANSI_RED + "schema is not conteins field " + baseArgs.getDDLBucketing + ANSI_RESET)
        LOGGER.error(ANSI_RED + "required field: " + ANSI_RESET)
        for (field <- extSchema) {
          LOGGER.error(ANSI_RED + field.name + " " + field.dataType.sql + ANSI_RESET)
        }
      }
    }
    if (!baseArgs.getDDLTblFormat.equals(""))
      schema = schema + "STORED AS " + baseArgs.getDDLTblFormat + "\n"
    if (!baseArgs.getDDLPath.equals(""))
      schema = schema + "LOCATION \"" + baseArgs.getDDLPath + "\"\n"
    if (!baseArgs.getDDLCompress.equals(""))
      schema = schema + "TBLPROPERTIES  (\"" + baseArgs.getDDLTblFormat + ".compress\"=\"" + baseArgs.getDDLCompress + "\")\n"
    schema = schema + ";\n"

    write(schema, baseArgs)
  }

  def write(schema: String, baseArgs: BaseArgs): Unit = {
    val file = new File(baseArgs.getSqlOutPath)
    file.mkdirs()
    val bw = new BufferedWriter(new FileWriter(file + File.separator + baseArgs.getDDLHivetbl + ".sql"))
    bw.write(schema)
    bw.close()
  }

  def diff(extSchema: StructType, hiveSchema: StructType): Unit = {
    extSchema.diff(hiveSchema)
  }
}
