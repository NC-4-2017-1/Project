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
    <link href="<c:url value="/resources/css/styles-dashboard.css" />" rel="stylesheet">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">

    <script src="/resources/js/jquery-3.2.1.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
</head>
<script>
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });

    function myFunction() {
        window.location.assign("/project/new-layout");
    }
</script>
<body>

<jsp:include page="header.jsp"/>

    <h3 class="pageName">PROJECT LIST</h3>
<div class="row col-sm-12">
    <div class="col-sm-1 col-sm-offset-11">
        <button class="btn btn-sm btn-success btn-lg" onclick="myFunction()">
            <i class="fa fa-plus-square" aria-hidden="true"><b>&nbsp;Add Project</b></i>
        </button>
    </div>
</div>
    <div>
    <c:forEach items="${userProjects}" var="project">
        <div class="project-item">
           <!-- <div class="row pr-info">
                    <div class="pr-type col-sm-3">
                        Type: <strong>${project.type.toString()}</strong>
                    </div>
                    <div class="pr-date col-sm-3">
                        Created: <strong><u>${project.creationDate}</u></strong>
                    </div>
                    <div class="pr-autor col-sm-3">
                        Autor: <strong><u>_________________</u></strong>
                    </div>
            </div>-->
            <table class="table project-list">
                <thead>
                    <tr>
                        <th colspan="3">
                            <c:choose>
                                <c:when test="${project.type.name().equals('DATA_VISUALIZATION')}">
                                    <form role="form" method="GET" action="/project/project-dv">
                                        <b><u>Project:</u> </b><button type="submit" class="btn btn-secondary btn-sm pr-name" data-toggle="tooltip" title="${project.description}" data-placement="right"
                                                                name="projDvId" value="${project.id}">${project.name}</button>
                                    </form>
                                </c:when>
                                <c:when test="${project.type.name().equals('HEALTH_MONITORING')}">
                                    <!--<b>Project: </b><a href="/project/project-hm" data-toggle="tooltip" title="${project.description}" data-placement="right" name="projHmId" value="${project.id}">${project.name}</a>                    -->
                                    <form role="form" method="GET" action="/project/project-hm">
                                        <b><u>Project:</u> </b><button type="submit" class="btn btn-secondary btn-sm pr-name" data-toggle="tooltip" title="${project.description}" data-placement="right"
                                                                name="projHmId" value="${project.id}">${project.name}</button>
                                            <%--  <button type="submit" class="btn btn-default btn-md glyphicon glyphicon-eye-open" name="projHmId" value="${project.id}"></button>--%>
                                    </form>
                                </c:when>
                            </c:choose>
                        </th>
                    </tr>
                </thead>
                <tbody>
                <tr class="active">
                <td class="pr-date">
                    Created: <strong>${project.creationDate}</strong>
                </td>
                <td class="pr-type">
                        Type: <strong>${project.type.toString()}</strong>
                </td>
                <td class="pr-autor">
                    Autor: <strong>${project.authorFullName}</strong>
                </td>
                </tr>
                </tbody>
            </table>
        </div>
    </c:forEach>
    </div>

<jsp:include page="footer.jsp"/>

</body>
</html>
