CREATE SCHEMA IF NOT EXISTS raw_mysql_sakila;

DROP TABLE IF EXISTS raw_mysql_sakila.actor;

CREATE EXTERNAL TABLE raw_mysql_sakila.actor ( 
	num INT,
	letter STRING
) 
PARTITIONED BY (dlk_cob_date string) 
CLUSTERED BY (num) INTO 5 BUCKETS 
STORED AS ORC
LOCATION "/datalake/data/raw/mysql/sakila/actor"
TBLPROPERTIES  ("ORC.compress"="SNAPPY")
;

MSCK REPAIR TABLE raw_mysql_sakila.actor;
