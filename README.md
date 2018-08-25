# Giraffe
A tool for automatically creating a table from an external source schema

Usage: Giraffe [options]
  Options:
    -h, --help
      Application usage
  * -jdbc 
      _JDBC connection string_
      Default: <empty string>
  * -driver 
      _driver classpath<_
      Default: <empty string>
  * -username 
      _username for database connection_
      Default: <empty string>
  * -password
      _password for database connection_
      Default: <empty string>
  * -query
      _SQL query(destination database dialect)_
      Default: <empty string>
  * -sqlOutPath
      _output path for sql_
      Default: <empty string>
  * -ddlHivetbl
      _ddl table name with schema_
      Default: <empty string>
  * -ddlTblFormat
      _file format_
      Default: <empty string>
  * -ddlPath
      _path for new table data_
      Default: <empty string>
  * -ddlBucketingFigure
      _number buckets for new table_
      Default: <empty string>
  * -ddlExternal
      _compression codec for new table(NONE, ZLIB, SNAPPY)_
      Default: false
  * -ddlPart
      _partition column(s) for new table, with type_
      Default: <empty string>
  * -ddlBucketing
      _bucketing column(s) for new table_
      Default: <empty string>
  * -ddlCompress
      _compression codec for new table(NONE, ZLIB, SNAPPY)_
      Default: <empty string>
	  
# Use:
   
Schema in MySQL:  
<pre>   actor_id  SMALLINT(5),  
   first_name  VARCHAR(45),
   last_name  VARCHAR(45),
   last_update TIMESTAMP</pre>
 
 Without dependencies:
<pre>spark-submit --master local \
  --jars /root/mysql-connector-java-8.0.11.jar,/root/jcommander-1.72.jar \
  --class ru.seidzi.graffe.Main Giraffe-1.0.jar \
  -jdbc "jdbc:mysql://192.168.88.254:3306/sakila?useUnicode=true&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&useLegacyDatetimeCode=false&autoReconnect=true&useSSL=false" \
  -driver "com.mysql.jdbc.Driver" \
  -username "spark" \
  -password "spark" \
  -query "select * from sakila.actor" \
  -sqlOutPath "/root/testGiraffe" \
  -ddlHivetbl "raw_mysql_sakila.actor" \
  -ddlTblFormat "ORC" \
  -ddlPath "/datalake/data/raw/mysql/sakila/actor" \
  -ddlPart "dlk_cob_date" \
  -ddlBucketing "actor_id" \
  -ddlBucketingFigure "5" \
  -ddlCompress "SNAPPY" \
  -ddlExternal</pre>

With dependencies:
<pre>spark-submit --master local   \
  /root/Giraffe-1.0-jar-with-dependencies.jar  \
  -jdbc "jdbc:mysql://192.168.88.254:3306/sakila?useUnicode=true&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&useLegacyDatetimeCode=false&autoReconnect=true&useSSL=false"   \
  -driver "com.mysql.jdbc.Driver"   \
  -username "spark"   \
  -password "spark"   \
  -query "select * from sakila.actor"   \
  -sqlOutPath "/root/testGiraffe"   \
  -ddlHivetbl "raw_mysql_sakila.actor"   \
  -ddlTblFormat "ORC"   \
  -ddlPath "/datalake/data/raw/mysql/sakila/actor"   \
  -ddlPart "dlk_cob_date"   \
  -ddlBucketing "actor_id"  \
  -ddlBucketingFigure "5"  \
  -ddlCompress "SNAPPY"   \
  -ddlExternal</pre>

Created hive ddl:
<pre>create external table raw_mysql_sakila.actor { 
	actor_id INT,
	first_name STRING,
	last_name STRING,
	last_update TIMESTAMP
} 
PARTITIONED BY (dlk_cob_date) 
CLUSTERED BY (actor_id) INTO 5 BUCKETS 
STORED AS ORC
LOCATION "/datalake/data/raw/mysql/sakila/actor"
TBLPROPERTIES  ("ORC.compress"="SNAPPY")
;
</pre>