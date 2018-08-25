package ru.seidzi.graffe

import com.beust.jcommander.Parameter

class BaseArgs {
  @Parameter(names = Array("-h", "--help"), required = false, description = "Application usage", help = true, order = 0)
  private var help: Boolean = false
  @Parameter(names = Array("-jdbc"), required = true, description = "JDBC connection string", order = 1)
  private var jdbc: String = ""
  @Parameter(names = Array("-driver"), required = true, description = "driver classpath", order = 2)
  private var driver: String = ""
  @Parameter(names = Array("-username"), required = true, description = "username for database connection", order = 3)
  private var username: String = ""
  @Parameter(names = Array("-password"), required = true, description = "password for database connection", order = 4)
  private var password: String = ""
  @Parameter(names = Array("-query"), required = true, description = "SQL query(destination database dialect)", order = 5)
  private var query: String = ""
  @Parameter(names = Array("-sqlOutPath"), required = true, description = "output path for sql", order = 7)
  private var sqlOutPath: String = ""
  @Parameter(names = Array("-ddlHivetbl"), required = false, description = "ddl table name with schema", order = 8)
  private var ddlHivetbl: String = ""
  @Parameter(names = Array("-ddlTblFormat"), required = false, description = "file format", order = 9)
  private var ddlTblFormat: String = ""
  @Parameter(names = Array("-ddlPath"), required = false, description = "path for new table data", order = 10)
  private var ddlPath: String = ""
  @Parameter(names = Array("-ddlPart"), required = false, description = "partition column(s) for new table, with type", order = 11)
  private var ddlPart: String = ""
  @Parameter(names = Array("-ddlBucketing"), required = false, description = "bucketing column(s) for new table", order = 12)
  private var ddlBucketing: String = ""
  @Parameter(names = Array("-ddlBucketingFigure"), required = false, description = "number buckets for new table", order = 13)
  private var ddlBucketingFigure: String = ""
  @Parameter(names = Array("-ddlCompress"), required = false, description = "compression codec for new table(NONE, ZLIB, SNAPPY)", order = 14)
  private var ddlCompress: String = ""
  @Parameter(names = Array("-ddlExternal"), required = false, description = "compression codec for new table(NONE, ZLIB, SNAPPY)", order = 15)
  private var ddlExternal: Boolean = false

  def getJdbc: String = jdbc

  def getDriver: String = driver

  def getUsername: String = username

  def getPassword: String = password

  def getQuery: String = "(" + query + ")" + "as t"

  def getSqlOutPath: String = sqlOutPath

  def getHelp: Boolean = help

  def getDDLHivetbl: String = ddlHivetbl

  def getDDLTblFormat: String = ddlTblFormat

  def getDDLPath: String = ddlPath

  def getDDLPart: String = ddlPart

  def getDDLBucketing: String = ddlBucketing

  def getDDLBucketingFigure: String = ddlBucketingFigure

  def getDDLCompress: String = ddlCompress

  def getDDLExternal: Boolean = ddlExternal

}
