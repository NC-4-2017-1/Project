<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>DV Settings</title>

    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

    <script src="/resources/js/jquery-3.2.1.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<h3 class="pageName"> Visualization Settings</h3>
<c:out value="${table}" escapeXml="false"></c:out>
<div class="form-group row">
    <div class="col-sm-12">
        <button onclick="addGraphRadioButtons()" class="btn btn-sm btn-success col-sm-2 col-sm-offset-5" type="button"
                id="addgraph"><i class="fa fa-plus-square" aria-hidden="true"><b>&nbsp;Add graph</b></i>
        </button>
    </div>
</div>
<form method="POST" role="form" action="/project/save-visualization">
    <div class="keyRadios">
        <div class="keyRadio">
            <div class="radiosDivX">
                X:
                <c:forEach items="${tableKeys}" var="entry">
                    <label class="radio-inline"><input class="xAxis" type="radio" name="x"
                                                       value="${entry}">${entry}</label>
                </c:forEach>
            </div>
            <div class="radiosDivY">
                Y:
                <c:forEach items="${tableKeys}" var="entry">
                    <label class="radio-inline"><input class="yAxis" type="radio" name="y"
                                                       value="${entry}">${entry}</label>
                </c:forEach>
            </div>

            <div class="selectMath">
                Choose column for calculate:
                <select class="selectForMath form-control" id="valSelectMath0" name="selectForMath" style="width: 20%;">
                    <option value=""></option>
                    <c:forEach items="${tableKeys}" var="entry">
                        <option value="${entry}">${entry}</option>
                    </c:forEach>
                </select>
            </div>

            <hr>
        </div>




    </div>




    <div class="form-group row">
        <div class="col-sm-12">
            <button class="btn btn-sm btn-primary col-sm-2 col-sm-offset-5" type="button" id="submit">
                <b>Next</b>&nbsp;<i class="fa fa-arrow-right" aria-hidden="true"></i>
            </button>
        </div>
    </div>

    <script>
        var i = 1;
        var j = 0;

        function addGraphRadioButtons() {
            var cloneOfKeyRadio = $(".keyRadio:first").clone();

            $("div.radiosDivX").each(function() {
                $(this).find('input').attr('name', 'x' + i);
                i++;
            });

            $("div.radiosDivY").each(function() {
                $(this).find('input').attr('name', 'y' + i);
                i++;
            });

            $("div.selectMath").find('#valSelectMath'+j).attr('id','valSelectMath' + (j+1));

            cloneOfKeyRadio.appendTo(".keyRadios");
            i++;
            j++;
        }

        function isData() {

            var xAxisArray = [];
            var yAxisArray = [];
            var mathArray = [];

            var xAxis = $(".xAxis");
            var yAxis = $(".yAxis");
            var math = $(".selectForMath");

            $.each(xAxis, function (index, value) {

                if ($(value).is(":checked")) { // check if the radio is checked
                    var val = $(this).val(); // retrieve the value
                    xAxisArray.push(val);
                }
            });

            $.each(yAxis, function (index, value) {
                if ($(value).is(":checked")) { // check if the radio is checked
                    var val = $(this).val(); // retrieve the value
                    yAxisArray.push(val);
                }
            });

            $.each(math, function(index, value){
                var idd = value.id;
                mathArray.push($("#"+idd).val());
            });

            var data = {
                xAxis: xAxisArray,
                yAxis: yAxisArray,
                mathCol: mathArray
            };
            return data;

        }
        ;


        $("#submit").click(function () {
            var data = isData();
            console.log(JSON.stringify(data));
            if (data == false) {
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

    </script>


</form>

<jsp:include page="footer.jsp"/>

</body>
</html>

