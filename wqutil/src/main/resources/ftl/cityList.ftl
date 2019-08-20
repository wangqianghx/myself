<!DOCTYPE html>

<html lang="en">
<#include "temp.ftl">
<body>
<#list cityList as city>

City: ${city.cityName}! <br>
Q:Why I like? <br>
A:${city.description}!

</#list>
</body>

</html>