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

    <title>HM Project</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/styles-projview.css" />" rel="stylesheet">


    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script src="/resources/js/jquery-3.2.1.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/highcharts-more.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>

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
    <div class="projtype"><h1><c:out value="${project.type}"/></h1></div>
    <div class="projname"><h2><c:out value="${project.name}"/></h2></div>

    <div class="buttons">
        <a class="btn btn-danger btn-lg" href="#">
            <i class="fa fa-trash-o fa-lg"></i> Delete</a>
        <a class="btn btn-primary btn-lg" href="#">
            <i class="fa fa-share-alt"></i> Share</a>
    </div>

    <div class="projdescription jumbotron">
        <h4>Author:</h4>
        <pre class="text-success"><c:out value="${author.fullName}"/></pre>
        <h4>Connection details:</h4>
        <pre class="text-primary">Server - <c:out value="${project.serverName}"/>;   Port - <c:out value="${project.port}"/>;  SID - <c:out value="${project.sid}"/>;  User name - <c:out value="${project.userName}"/>;  Password  - <c:out value="${project.password}"/>;</pre>
        <h4>Description:</h4>
        <pre class="text-muted"><c:out value="${project.description}"/></pre>
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