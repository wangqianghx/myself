    <#compress></#compress>
    val ${row.tablename}_path = "${row.prefixStr}${row.tablename}${row.suffixStr}"
    val ${row.tablename}_rdd = sc.textFile(${row.tablename}_path, 1)
