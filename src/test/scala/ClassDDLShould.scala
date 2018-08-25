import com.beust.jcommander.JCommander
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.junit.{Assert, Test}
import ru.seidzi.graffe.{BaseArgs, DDL}

import scala.io.Source

class ClassDDLShould {

  @Test
  def createCorrectDDL(): Unit = {
    val arg:Seq[String] = Seq(
      "-jdbc", "example",
      "-driver", "example",
      "-username", "example",
      "-password", "example",
      "-query", "example",
      "-sqlOutPath", "\\git\\Giraffe\\test",
      "-ddlHivetbl", "raw_mysql_sakila.actor",
      "-ddlTblFormat", "ORC",
      "-ddlPath", "/datalake/data/raw/mysql/sakila/actor",
      "-ddlPart", "dlk_cob_date",
      "-ddlBucketing", "num",
      "-ddlBucketingFigure", "5",
      "-ddlCompress", "SNAPPY",
      "-ddlExternal"
    )
    var requared_schems = "create external table raw_mysql_sakila.actor { \n" +
      "\tnum INT,\n" +
      "\tletter STRING\n" +
      "} \n" +
      "PARTITIONED BY (dlk_cob_date) \n" +
      "CLUSTERED BY (num) INTO 5 BUCKETS \n" +
      "STORED AS ORC\n" +
      "LOCATION \"/datalake/data/raw/mysql/sakila/actor\"\n" +
      "TBLPROPERTIES  (\"ORC.compress\"=\"SNAPPY\")\n" +
      ";" +
      "\n"
    val baseArgs = new BaseArgs()
    val jc = new JCommander()
    jc.setProgramName("Giraffe")
    jc.addObject(baseArgs)
    jc.parse(arg: _*)
    val schema = StructType(
      List(
        StructField("num", IntegerType, true),
        StructField("letter", StringType, true)
      )
    )

    new DDL().create(schema,baseArgs)
    var out_schema = ""
    for (line <- Source.fromFile("\\git\\Giraffe\\test\\raw_mysql_sakila.actor.sql").getLines) {
      out_schema = out_schema + line + "\n"
    }

    Assert.assertEquals(requared_schems, out_schema)
  }
}
