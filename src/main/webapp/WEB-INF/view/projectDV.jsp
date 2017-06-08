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

    <title>DV Project</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/styles-projview.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="stylesheet">

    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/highcharts-more.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>


<div class="projtype"><h1><c:out value="${project.type}"/></h1></div>
<div class="projname"><h2><c:out value="${project.name}"/></h2></div>

<div class="buttons">
    <a class="btn btn-danger btn-lg" href="#">
        <i class="fa fa-trash-o fa-lg"></i> Delete</a>
    <a class="btn btn-primary btn-lg" href="<c:url value="/project/share/${project.id}/${project.type}" />">
        <i class="fa fa-share-alt"></i> Share</a>
</div>

<div class="author"><h4>Author: <c:out value="${author.fullName}"/></h4></div>
<div class="projdescription">
    <h4>Description:
        <p>
            <c:out value="${project.description}"/>
        </p>
    </h4>
</div>


<div class="graphs">

    <c:forEach items="${graphics}" var="graph">
        <div class="graphitem">
            <div class="graphname"><h3><c:out value="${graph.name}"/></h3></div>
            <div id="${graph.id}"
                 style="min-width: 310px; height: 400px; margin: 0 auto; background-color: #5BE870"></div>
            <script>
                var container_name = "${graph.id}";
                var t = ${graph.graphicJSON};
                eval(t.jsCodeForGraph);
            </script>
            <div class="graphmath">
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