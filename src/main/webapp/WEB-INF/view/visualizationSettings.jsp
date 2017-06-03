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

    <title>DV Settings</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="stylesheet">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
</head>

<body>

<jsp:include page="header.jsp"/>

<div>Visualization Settings</div>
<p><c:out value="${message}"/></p>



<jsp:include page="footer.jsp"/>

</body>
</html>

