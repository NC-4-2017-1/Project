<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>DV ${project.name}</title>
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
<div class="projtype"><h3 class="pageName"><c:out value="${project.type}"/></h3></div>
<div class="projname"><p><c:out value="${project.name}"/></p></div>
<div class="row col-sm-12">
    <div class="btn-group col-sm-1 col-sm-offset-11">
        <c:if test="${author.id == sessionScope.userObject.getId()}">
        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
            Actions <span class="caret"></span></button>
        <ul class="dropdown-menu" role="menu">
            <li><a href="/project/delete?id=${project.id}&project_type=${project.type}">
                <i class="fa fa-trash-o fa-lg"></i> Delete</a></li>
                <%--<c:url value="/project/delete/${project.id}/${project.type}"  />--%>
            <%--<li><a href="<c:url value="/project/share/${project.id}/3/desc" />">
                <i class="fa fa-share-alt"></i> Share</a></li>--%>
            <li><a href="/project/share?idProject=${project.id}">
                <i class="fa fa-share-alt"></i> Share</a></li>
        </ul>
        </c:if>
    </div>
</div>
<!--<div class="buttons">
    <a class="btn btn-danger btn-sm" href="<c:url value="/project/delete/${project.id}" />">
        <i class="fa fa-trash-o fa-lg"></i> Delete</a>
    <a class="btn btn-primary btn-sm" href="<c:url value="/project/share/${project.id}" />">
        <i class="fa fa-share-alt"></i> Share</a>
</div>-->
<div class="row">
    <h5><b>Author: </b><u><c:out value="${author.fullName}"/></u></h5>
    <h5><b>Description:</b></h5>
    <div class="descriptionP">
        <p class="text-muted">
            <c:if test = "${not empty fn:trim(project.description)}">
                ${project.description}
            </c:if>
            <c:if test = "${empty fn:trim(project.description)}">
                not defined
            </c:if>
        </p>
    </div>
</div>

<!--<div class="author"><h4>Author: <c:out value="${author.fullName}"/></h4></div>
<div class="projdescription">
    <h4>Description:
        <p>
            <c:out value="${project.description}"/>
        </p>
    </h4>
</div>-->


<div class="graphs">

    <c:forEach items="${graphics}" var="graph">
        <div class="graphitem">
            <div class="graphname"><h4><c:out value="${graph.name}"/></h4></div>
            <div id="${graph.id}"
                 style="min-width: 310px; height: 400px; margin: 0 auto; background-color: #5BE870"></div>
            <script>
                var container_name = "${graph.id}";
                var t = ${graph.graphicJSON};
                eval(t.jsCodeForGraph);
            </script>
            <div class="graphmath">
                <span id="mathName${graph.id}"><script>$("#mathName${graph.id}").text("Calculate by "+t.math+" column");
                </script></span>
                <div class="graphmathitem">
                    <b> Average:</b> <c:out value="${graph.average}"/>
                </div>
                <div class="graphmathitem">
                    <b> Olympic average:</b> <c:out value="${graph.olympicAverage}"/>
                </div>
                <div class="graphmathitem">
                    <b> Dispersion:</b> <c:out value="${graph.dispersion}"/>
                </div>
                <div class="graphmathitem">
                    <b> Mathematical Expectation:</b> <c:out value="${graph.mathExpectation}"/>
                </div>
            </div>
        </div>
    </c:forEach>

</div>


<jsp:include page="footer.jsp"/>

</body>
</html>