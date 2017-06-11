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

    <title>DV ${project.name}</title>

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


<div class="projtype"><h3 class="pageName"><c:out value="${project.type}"/></h3></div>
<div class="projname"><h4><c:out value="${project.name}"/></h4></div>
<div class="row col-sm-12">
    <div class="btn-group col-sm-1 col-sm-offset-11">
        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
            Actions <span class="caret"></span></button>
        <ul class="dropdown-menu" role="menu">
            <li><a href="<c:url value="/project/delete/${project.id}/${project.type}"  />">
                <i class="fa fa-trash-o fa-lg"></i> Delete</a></li>
            <li><a href="<c:url value="/project/share/${project.id}" />">
                <i class="fa fa-share-alt"></i> Share</a></li>
        </ul>
    </div>
</div>
<!--<div class="buttons">
    <a class="btn btn-danger btn-sm" href="<c:url value="/project/delete/${project.id}" />">
        <i class="fa fa-trash-o fa-lg"></i> Delete</a>
    <a class="btn btn-primary btn-sm" href="<c:url value="/project/share/${project.id}" />">
        <i class="fa fa-share-alt"></i> Share</a>
</div>-->
<div class="row">
    <h5><b>Author:</b><u><c:out value="${author.fullName}"/></u></h5>
    <h5><b>Description:</b></h5>
    <p class="text-muted"><c:out value="${project.description}"/></p>
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