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

    <title>Index</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="stylesheet">
</head>

<body>

<div class="container">

    <div class="modal-content" style="margin-top: 20px;">
        <h1>datavisualization.com</h1>
        <p class="lead">
            MODULE1&2 LLC
        </p>
        <sec:authorize access="!isAuthenticated()">
            <p><a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Войти</a></p>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <p>Ваш логин: <sec:authentication property="principal.username" /></p>
            <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>

            <p><a class="btn btn-default" href="<c:url value="/user/dashboard" />" role="button">User: dashboard</a></p>
            <p><a class="btn btn-default" href="<c:url value="/user/admin-panel" />" role="button">Admin: admin panel</a></p>
            <p><a class="btn btn-default" href="<c:url value="/user/create-user" />" role="button">Admin: create user</a></p>

            <p><a class="btn btn-default" href="<c:url value="/project/layout" />" role="button">Proj: project view</a></p>
            <p><a class="btn btn-default" href="<c:url value="/project/share" />" role="button">Proj: share project</a></p>
            <p><a class="btn btn-default" href="<c:url value="/project/new-layout" />" role="button">Proj: create project</a></p>
            <p><a class="btn btn-default" href="<c:url value="/project/visualization-setup" />" role="button">Proj: dv setup</a></p>
            <p><a class="btn btn-default" href="<c:url value="/project/visualization-settings" />" role="button">Proj: dv settings</a></p>
            <p><a class="btn btn-default" href="<c:url value="/project/health-monitor-setup" />" role="button">Proj: hm setup</a></p>
            <p><a class="btn btn-default" href="<c:url value="/project/health-monitor-settings" />" role="button">Proj: hm settings</a></p>


        </sec:authorize>
    </div>

    <div class="footer">
        <p>© dreamteam 2017</p>
    </div>

</div>
</body>
</html>