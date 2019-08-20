<#compress></#compress>
<#list row.tableCols as col>
        ${row.tablename}${col.name} = keys(${col_index})
</#list>

