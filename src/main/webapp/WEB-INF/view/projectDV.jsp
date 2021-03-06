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
    <script type="text/javascript" src="/resources/js/bootbox.min.js"></script>
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
            <li><a href="/project/delete?id=${project.id}&project_type=${project.type.id}" class = "projectDelete">
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
                 style="min-width: 310px; height: 400px; margin: 0 auto;"></div>
            <script>
                var container_name = "${graph.id}";
                var t = ${graph.graphicJSON};
                eval(t.jsCodeForGraph);
            </script>
            <div class="graphmath">

                <table class="table table-condensed">
                    <caption>
                        <span id="mathName${graph.id}" class = "mathName">
                              <script>$("#mathName${graph.id}").html("Calculate by '<b><u>" + t.math + "</u></b>' column"); </script>
                        </span>
                    </caption>
                    <thead>
                    <tr>
                        <th>Average</th>
                        <th>Olympic average</th>
                        <th>Dispersion</th>
                        <th>Mathematical Expectation</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class = "active">
                        <td><c:out value="${graph.average}"/></td>
                        <td><c:out value="${graph.olympicAverage}"/></td>
                        <td><c:out value="${graph.dispersion}"/></td>
                        <td><c:out value="${graph.mathExpectation}"/></td>
                    </tr>
                    </tbody>
                </table>
                <div id="correlation${graph.id}">

                </div>
                <script>
                    var correlation = JSON.parse(t.correlation);
                    console.log(JSON.parse(t.correlation));
                    var corrDiv = $("#correlation${graph.id}");
                    corrDiv.append("<span><strong>Correlation</strong></span>");
                    $.each(correlation, function(key, value){
                        console.log(key) ;
                        console.log(value);
                        $.each(value, function(corrColumnName, corrValue){
                            var corrP = $("<p>");
//                            corrP.text("Between "+key+" and "+corrColumnName+" is "+ corrValue);
                            corrP.text("Between "+key+" and "+corrColumnName+" is "+ Math.round(corrValue*1000)/1000);
                            corrDiv.append(corrP);
                        });
                    });
                </script>

                <%--<div class="graphmathitem">
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
                </div>--%>
            </div>
        </div>
        <br>
    </c:forEach>
</div>
<jsp:include page="footer.jsp"/>
</body>
<script>
    $(document).ready(function () {
        $(".projectDelete").click(function (event) {
            event.preventDefault();
            var href = $(this).attr("href");
            var name = $('.projname p').text().trim();
            bootbox.confirm({
                title: "Delete project",
                message: "Do you want delete project <span class='wordWrap'>'" + name + "'? <\span>",
                buttons: {
                    cancel: {
                        label: '<i class="fa fa-times"></i> Cancel'
                    },
                    confirm: {
                        label: '<i class="fa fa-check"></i> Confirm'
                    }
                },
                callback: function (result) {
                    if (result){
                        window.location = href;
                    }
                }
            });
        });
    });
</script>
</html>