<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Index</title>
    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class = "modal-content" style="margin-top: 20px;">
    <div class="container">
        <h1>datavisualization.com</h1>
        <p class="lead">
            MODULE1&2 LLC
        </p>
        <sec:authorize access="!isAuthenticated()">
            <p><a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Log in</a></p>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <p>Ваш логин: <sec:authentication property="principal.username"/></p>
            <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Log out</a></p>

            <p><a class="btn btn-default" href="<c:url value="/user/dashboard" />" role="button">User: dashboard</a></p>
            <p><a class="btn btn-default" href="<c:url value="/user/admin-panel" />" role="button">Admin: admin
                panel</a></p>
            <p><a class="btn btn-default" href="<c:url value="/user/create-user" />" role="button">Admin: create
                user</a></p>

            <p><a class="btn btn-default" href="<c:url value="/project/project-dv" />" role="button">Proj: project dv
                view</a>
            </p>
            <p><a class="btn btn-default" href="<c:url value="/project/project-hm" />" role="button">Proj: project hm
                view</a>
            </p>
            <p><a class="btn btn-default" href="<c:url value="/project/share" />" role="button">Proj: share project</a>
            </p>
            <p><a class="btn btn-default" href="<c:url value="/project/new-layout" />" role="button">Proj: create
                project</a></p>
            <p><a class="btn btn-default" href="<c:url value="/project/visualization-setup" />" role="button">Proj: dv
                setup</a></p>
            <p><a class="btn btn-default" href="<c:url value="/project/visualization-settings" />" role="button">Proj:
                dv settings</a></p>
            <p><a class="btn btn-default" href="<c:url value="/project/health-monitor-setup" />" role="button">Proj: hm
                setup</a></p>
            <p><a class="btn btn-default" href="<c:url value="/project/health-monitor-settings" />" role="button">Proj:
                hm settings</a></p>


        </sec:authorize>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
