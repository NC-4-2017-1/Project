<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>DV Settings</title>
    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<h3 class="pageName">Settings: preview data table</h3>
<%--<h4 class="text-center">Preview data table</h4>--%>
<c:out value="${table}" escapeXml="false"></c:out>
<h4 class="text-center">Please select the coordinate axis for the graph and column for calculating mathematical data:</h4>
<form data-toggle="validator" class="form-horizontal" role="form" method="POST" action="/project/save-visualization"
      enctype="multipart/form-data">
    <div class="form-group">
        <label class="control-label col-sm-2 col-sm-offset-3">X-axis:</label>
        <div class="col-sm-3">
            <select class="form-control input-sm xAxisSelect"  name="X">
                <c:forEach items="${tableKeys}" var="entry">
                    <option value="${entry}">${entry}</option>
                </c:forEach>
            </select>
        </div>

        <label class="control-label col-sm-2 col-sm-offset-3">Y-axis:</label>
        <div class="col-sm-3">
            <select class="form-control input-sm yAxisSelect"   name="Y">
                <c:forEach items="${tableKeys}" var="entry">
                    <option value="${entry}">${entry}</option>
                </c:forEach>
            </select>
        </div>

        <label class="control-label col-sm-2 col-sm-offset-3">MathDataColumn:</label>
        <div class="col-sm-3">
            <select class="form-control input-sm mathSelect" name="Math">
              <%--  <option value=""></option>--%>
                <c:forEach items="${tableKeys}" var="entry">
                    <option value="${entry}">${entry}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="form-group">
        <div class="pull-left">
            <div class="col-sm-5">
                <button class="btn btn-sm btn-success"  type="button"
                id="addGraph"><i class="fa fa-plus-square"><b>&nbsp;Add graph</b></i>
                </button>
            </div>
            <div class="col-sm-5">
                <button class="btn btn-sm btn-danger"  type="button"
                id="removeGraph"><i class="fa fa-minus-square"><b>&nbsp;Remove graph</b></i>
                </button>
            </div>
        </div>

        <div class="pull-right">
            <button class="btn btn-sm btn-primary" type="button" id="submit">
                <b>Finish</b>&nbsp;<i class="fa fa-arrow-right" aria-hidden="true"></i>
            </button>
        </div>
        <div class="pull-right">
            <button class="btn btn-link" type="button" id="back">
                <b>Back</b>
            </button>
        </div>
    </div>


    <table class="table js-table hidden tab-pane"  style="width:100%" border="1">
        <tr>
            <th  style="width:5%; text-align:center;">Graph </th>
            <th  style="width:30%">X-axis</th>
            <th  style="width:30%">Y-axis</th>
            <th  style="width:30%">MathDataColumn</th>
        </tr>
    </table>

    <script>

        $('#submit').prop('disabled', 'disabled');

        $("#addGraph").click(function () {
            $('#submit').prop('disabled', false);
            var x = $(".xAxisSelect :selected").val();
            var y = $(".yAxisSelect :selected").val();
            var math = $(".mathSelect :selected").val();
            var table = document.querySelector(".js-table");
            $(table).removeClass("hidden");
            tr = table.insertRow(1);
            td = tr.insertCell(0);
            td.setAttribute("style", "text-align:center;");
            var check = document.createElement("INPUT");
            check.setAttribute("type", "checkbox");
            check.setAttribute("class", "js-remove");
            td.appendChild(check);
            td = tr.insertCell(1);
            td.appendChild(document.createTextNode(x));
            $(td).addClass("xAxis");
            td = tr.insertCell(2);
            td.appendChild(document.createTextNode(y));
            $(td).addClass("yAxis");
            td = tr.insertCell(3);
            td.appendChild(document.createTextNode(math));
            $(td).addClass("selectForMath");
        });

        $("#removeGraph").click(function () {
            var removElements = $(".js-remove");
            $.each(removElements, function (index, value) {
                if($(value).prop("checked")){
                    $(value).closest('tr').remove();
                }
            });
            var removAllElements = $(".js-remove");
            if(removAllElements.length ==0 ){
                $(".js-table").addClass("hidden");
                $('#submit').prop('disabled', 'disabled');
            }
        });

        function isData() {

            var xAxisArray = [];
            var yAxisArray = [];
            var mathArray = [];

            var xAxis = $(".xAxis");
            var yAxis = $(".yAxis");
            var math = $(".selectForMath");

            $.each(xAxis, function (index, value) {
                var val = value.textContent;
                xAxisArray.push(val);
            });

            $.each(yAxis, function (index, value) {
                var val = value.textContent;
                yAxisArray.push(val);
            });

            $.each(math, function(index, value){
                var val = value.textContent;
                mathArray.push(val);

            });

            var data = {
                xAxis: xAxisArray,
                yAxis: yAxisArray,
                mathCol: mathArray
            };
            return data;
        };

        $("#submit").click(function () {
            var data = isData();
            if (data.xAxis.length == 0 || data.yAxis.length == 0) {
                return false;
            }
            $.ajax({
                url: "/project/save-visualization",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(data),
                error: function (response) {
                    if (response.responseText == "successful") {
                        window.location.assign("/project/project-dv");
                    }
                }
            });
        });

        $("#back").click(function () {
            window.location.assign("/project/visualization-setup");
            });

    </script>
</form>

<jsp:include page="footer.jsp"/>

</body>
</html>

