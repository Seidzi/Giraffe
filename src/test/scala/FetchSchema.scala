import com.beust.jcommander.JCommander
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.junit.{Assert, Ignore, Test}
import ru.seidzi.graffe.{BaseArgs, DDL, Main}

class FetchSchema {

  @Ignore
  @Test
  def fetchSchemaInToConsole(): Unit ={
    val arg = Array (
      "-jdbc", "jdbc:mysql://192.168.88.254:3306/sakila?useUnicode=true&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&useLegacyDatetimeCode=false&autoReconnect=true&useSSL=false",
      "-driver", "com.mysql.jdbc.Driver",
      "-username", "spark",
      "-password", "spark",
      "-query", "select * from sakila.actor",
      "-fetchSchema"
    )
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

    Main.main(arg)
  }
}
