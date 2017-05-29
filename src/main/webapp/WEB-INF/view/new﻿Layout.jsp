<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
﻿<script
        src="https://code.jquery.com/jquery-3.2.1.js"
        integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
        crossorigin="anonymous">
</script>
﻿<form method="post" action="/project/create">
    Project Type:<br>
    <select id="type" name="type">
        <c:forEach items="${projectTypes}" var="entry">
            <option value="${entry.key}">${entry.value}</option>
        </c:forEach>
    </select>
    <br>
    Project name:<br>
    <input type="text" name="name" value="">
    <br>
    Description:<br>
    <input type="text" name="description" value="">
    <br><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>
