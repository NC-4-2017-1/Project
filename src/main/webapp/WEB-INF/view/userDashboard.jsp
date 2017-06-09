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

    <title>User dasboard</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="script">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="script">
    <link href="<c:url value="/resources/css/styles-dashboard.css" />" rel="stylesheet">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">
</head>

<body>

<jsp:include page="header.jsp"/>
<div class="modal-content container-userdash">
    <div class="center-block" style="width: 140px; height: 45px; margin-top: 20px; margin-bottom: 30px;">
        <button class="btn btn-primary btn-lg new-proj" onclick="myFunction()">New Project</button>
    </div>


    <c:forEach items="${userProjects}" var="project">
        <div class="project-item">


            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h1 class="panel-title">Project: <strong>${project.name}</strong></h1>
                </div>
                <div class="panel-body">

                    <div class="pr-type">
                        Type: <strong>
                        ${project.type.toString()}
                    </strong>
                    </div>
                    <div class="pr-date">
                        Created: <strong>
                        <u>${project.creationDate}</u>
                    </strong>
                    </div>
                    <pre>${project.description}</pre>

                </div>
                <div class="panel-footer">
                <c:choose>
                    <c:when test="${project.type.name().equals('DATA_VISUALIZATION')}">
                        <form role="form" method="GET" action="/project/project-dv">
                                <button type="submit" class="btn btn-default btn-md glyphicon glyphicon-eye-open" name="projDvId" value="${project.id}"></button>

                        </form>
                    </c:when>
                    <c:when test="${project.type.name().equals('HEALTH_MONITORING')}">
                        <form role="form" method="GET" action="/project/project-hm">
                                <%--         <input type="submit" name="projHmId" value="${project.id}">--%>
                            <button type="submit" class="btn btn-default btn-md glyphicon glyphicon-eye-open" name="projHmId" value="${project.id}"></button>
                        </form>
                    </c:when>
                </c:choose>
                </div>
            </div>
        </div>
    </c:forEach>



    <script>
        function myFunction() {
            window.location.assign("/project/new-layout");
        }
    </script>

</div>
<jsp:include page="footer.jsp"/>

</body>
</html>
