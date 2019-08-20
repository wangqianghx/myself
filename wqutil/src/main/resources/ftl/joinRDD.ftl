
<#include "table2rdd.ftl">
val ${row.tablename}_rdd1 = ${row.tablename}_rdd.map(line => {
        val keys = line.split("\t")
        <#include "iteratorTableCol.ftl">
        (${row.toRowkey}, line)
    }).groupByKey()
<#if env == "dev">
    ${row.tablename}_rdd1.foreach(println(_))
</#if>

topRDD = topRDD.${row.flowType}(${row.tablename}_rdd1)
<#if env == "dev">
    topRDD.foreach(println(_))
</#if>
    topRDD = topRDD.flatMap(line => {
      val value_all = new ListBuffer[(String, String)]()
      for (i <- line._2._1){
        for(j <- line._2._2.get.toList){

          value_all.append((j.split("\t")(15), i + "\t" + j))
        }
      }
      value_all.toList
    }).groupByKey()
