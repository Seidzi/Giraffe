create external table raw_mysql_sakila.actor { 
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
