<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>User dasboard</title>

    <jsp:include page="headFragment.jsp"/>
    <link href="<c:url value="/resources/css/styles-dashboard.css" />" rel="stylesheet">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link href="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">

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

    function activatePr(){
       $("#myProjects").removeClass("inactive-divs");
       $("#myProjects").addClass("active-divs");

       $("#sharedToMeProjects").removeClass("active-divs");
       $("#sharedToMeProjects").addClass("inactive-divs");
    }

    function activateSh(){
        $("#sharedToMeProjects").removeClass("inactive-divs");
        $("#sharedToMeProjects").addClass("active-divs");

        $("#myProjects").removeClass("active-divs");
        $("#myProjects").addClass("inactive-divs");
    }
</script>
<body>

<jsp:include page="header.jsp"/>

    <h3 class="pageName">PROJECT LIST</h3>
<div class="pull-right">
    <button class="btn btn-sm btn-success" onclick="myFunction()">
        <i class="fa fa-plus-square" aria-hidden="true"></i>&nbsp;<b>Add Project</b>
    </button>
</div>
<div class = "pull-left">
    <button class="btn btn-default btn-sm" id="span-myProjects" onclick="activatePr()">My projects</button>
    <button class="btn btn-default btn-sm" id="span-sharedToMeProjects" onclick="activateSh()">Shared to me projects</button>
</div>
    <div id="myProjects" class="active-divs">
        <table class="table table-striped table-condensed project-list">
            <thead>
            <tr>
                <th>Name</th>
                <th>Created at</th>
                <th>Type</th>
                <th>Created by</th>
            </tr>
            </thead>
            <tbody>
                 <c:forEach items="${userProjects}" var="project">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${project.type.name().equals('DATA_VISUALIZATION')}">
                                        <form role="form" method="GET" action="/project/project-dv">
                                            <button type="submit" class="btn btn-link btn-sm pr-name"
                                                    <c:if test = "${not empty fn:trim(project.description)}">
                                                        data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                                    </c:if>
                                                    name="projDvId" value="${project.id}">${project.name}</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${project.type.name().equals('HEALTH_MONITORING')}">
                                        <form role="form" method="GET" action="/project/project-hm">
                                            <button type="submit" class="btn btn-link btn-sm pr-name"
                                                    <c:if test = "${not empty fn:trim(project.description)}">
                                                        data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                                    </c:if>
                                                    name="projHmId" value="${project.id}">${project.name}</button>
                                        </form>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td class="pr-date">
                                    ${project.creationDate}
                            </td>
                            <td class="pr-type">
                                   ${project.type.toString()}
                            </td>
                            <td class="pr-autor">
                                    ${project.authorFullName}
                            </td>
                        </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="sharedToMeProjects" class="inactive-divs">
        <table class="table table-striped table-condensed project-list">
            <thead>
            <tr>
                <th>Name</th>
                <th>Created at</th>
                <th>Type</th>
                <th>Created by</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sharedToUserProjects}" var="project">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${project.type.name().equals('DATA_VISUALIZATION')}">
                                <form role="form" method="GET" action="/project/project-dv">
                                    <button type="submit" class="btn btn-link btn-sm pr-name"
                                            <c:if test = "${not empty fn:trim(project.description)}">
                                                data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                            </c:if>
                                            name="projDvId" value="${project.id}">${project.name}</button>
                                </form>
                            </c:when>
                            <c:when test="${project.type.name().equals('HEALTH_MONITORING')}">
                                <form role="form" method="GET" action="/project/project-hm">
                                    <button type="submit" class="btn btn-link btn-sm pr-name"
                                            <c:if test = "${not empty fn:trim(project.description)}">
                                                data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                            </c:if>
                                            name="projHmId" value="${project.id}">${project.name}</button>
                                </form>
                            </c:when>
                        </c:choose>

                    </td>
                    <td class="pr-date">
                            ${project.creationDate}
                    </td>
                    <td class="pr-type">
                            ${project.type.toString()}
                    </td>
                    <td class="pr-autor">
                            ${project.authorFullName}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

<jsp:include page="footer.jsp"/>

</body>
</html>
