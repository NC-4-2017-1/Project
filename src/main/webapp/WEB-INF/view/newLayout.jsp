<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>HM Settings</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="stylesheet">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
</head>

<body>

<jsp:include page="header.jsp"/>

﻿
<form method="post" action="/project/create">
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

<jsp:include page="footer.jsp"/>

</body>
</html>