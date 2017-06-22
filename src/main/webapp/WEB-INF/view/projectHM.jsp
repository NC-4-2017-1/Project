<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>HM ${project.name}</title>
    <jsp:include page="headFragment.jsp"/>
    <link href="<c:url value="/resources/css/styles-projview.css" />" rel="stylesheet">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://code.highcharts.com/highcharts.js"></script>
    <script type="text/javascript" src="https://code.highcharts.com/highcharts-more.js"></script>
    <script type="text/javascript" src="https://code.highcharts.com/modules/exporting.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="row col-sm-12">
    <c:if test = "${(errorSelector != null) || (errorGraphic != null) || (errorProject != null)}">
        <div class="alert alert-danger col-sm-12" role="alert">
            <div>${errorProject}</div>
            <div>${errorSelector}</div>
            <div>${errorGraphic}</div>
        </div>
    </c:if>
</div>
<c:if test = "${not empty project}">
    <div class="projtype"><h3 class="pageName"><c:out value="${project.type}"/></h3></div>
    <div class="projname"><h4><c:out value="${project.name}"/></h4></div>

    <div class="row col-sm-12">
        <div class="btn-group col-sm-1 col-sm-offset-11">
            <c:if test="${author.id == sessionScope.userObject.getId()}">
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                Actions <span class="caret"></span></button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="/project/delete?id=${project.id}&project_type=${project.type}">
                    <i class="fa fa-trash-o fa-lg"></i> Delete</a></li>
                <%--<li><a href="<c:url value="/project/share/${project.id}/3/desc" />">
                    <i class="fa fa-share-alt"></i> Share</a></li>--%>
                <li><a href="/project/share?idProject=${project.id}">
                    <i class="fa fa-share-alt"></i> Share</a></li>
            </ul>
            </c:if>
        </div>
    </div>
    <div class="row">
        <h5><b>Author: </b><u><c:out value="${author.fullName}"/></u></h5>
        <h5><b>Connection details:</b>
            <u>Server</u> - <c:out value="${project.serverName}"/>;
            <u>Port</u> - <c:out value="${project.port}"/>;  <u>SID</u> - <c:out value="${project.sid}"/>;
            <u>User name</u> - <c:out value="${project.userName}"/>;  <u>Password</u>  - <c:out value="${project.password}"/>;</h5>
        <h5><b>Description:</b></h5>
        <p class="text-muted">
            <c:if test = "${not empty fn:trim(project.description)}">
                ${project.description}
            </c:if>
            <c:if test = "${empty fn:trim(project.description)}">
                not defined
            </c:if>
        </p>
    </div>


    <ul class="nav nav-tabs" id="myTab">
        <c:forEach items="${project.selectors}" var="entry">
            <li><a data-target="#${entry.key}" data-toggle="tab">${entry.value.name}</a></li>
        </c:forEach>
        <c:if test = "${not empty project.graphic}">
            <li><a data-target="#graphic" data-toggle="tab">${project.graphic.name}</a></li>
        </c:if>
    </ul>

    <div class="tab-content">
        <c:forEach items="${project.selectors}" var="entry">
            <div class="tab-pane" id="${entry.key}">
                <c:if test = "${not empty attrSelectors[entry.key]}">
                    ${attrSelectors[entry.key]}
                </c:if>
            ${entry.value.value}
            </div>
        </c:forEach>
        <c:if test = "${not empty project.graphic}">
            <div class="tab-pane" id="graphic">
                <pre class="text-primary graphic-info-mess">Last hours for graph:  ${project.graphic.hourCount}.</pre>
                <div id="containerGraphic" style="min-width: 310px; height: 100%; margin: 0 auto"></div>
            </div>
        </c:if>
    </div>
</c:if>



<jsp:include page="footer.jsp"/>
<script>
    $(document).ready(function () {
        $('.nav-tabs a:first').tab('show');
        <c:if test = "${not empty project.graphic}">
            Highcharts.setOptions({
                global: {
                    useUTC: false
                }
            });
             var t = ${project.graphic.graphicJSON};
             if(t.jsCodeForGraph) {
                 eval(t.jsCodeForGraph);
             }else{
                 $('#containerGraphic').html(t.data);
             }
        </c:if>
    });
</script>
</body>
</html>