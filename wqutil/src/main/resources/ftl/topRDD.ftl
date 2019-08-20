package com.yss.bigdata.ta.achievementpay

import com.yss.bigdata.ta.analyticutil.{AnalyticFormula, ArithHelper, FormulaComputing}
import java.util.{Stack, HashMap => jhashmap, Map => jmap}
import org.apache.spark.sql.SparkSession
import scala.collection.mutable.ListBuffer

object TopRDD {
  def main(args: Array[String]): Unit = {
    val sparkSql = SparkSession.builder().master("local").appName("TestAchievementpay").getOrCreate()
    val sc = sparkSql.sparkContext
    var topRDD
    <#include "iteratorFlowRow.ftl">
}


