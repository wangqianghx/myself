<#compress></#compress>
<#list flowRows as row>
    <#if row.flowType == "groupbykey"><#include "groupbyRDD.ftl"></#if>
    <#if row.flowType == "leftOuterJoin"><#include "joinRDD.ftl"></#if>
</#list>