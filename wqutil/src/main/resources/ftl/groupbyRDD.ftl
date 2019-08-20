
    <#include "table2rdd.ftl">
    topRDD = ${row.tablename}_rdd.map(line => {
        val keys = line.split("\t")
        <#include "iteratorTableCol.ftl">
        (${row.toRowkey}, line)
    }).groupByKey()
    <#if env == "dev">
    topRDD.foreach(println(_))
    </#if>
