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

    <title>DV Settings</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="script">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="script">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.js"
            integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE=" crossorigin="anonymous"></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<div>Visualization Settings</div>
<div class="well"><c:out value="${table}" escapeXml="false"></c:out></div>
<div class="form-group row">
    <div class="offset-sm-2 col-sm-10">
        <button onclick="addGraphRadioButtons()" class="btn btn-lg btn-primary col-sm-2 col-sm-offset-5" type="button"
                id="addgraph">Add graph
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
            <hr>
        </div>
    </div>


    <div class="form-group row">
        <div class="offset-sm-2 col-sm-10">
            <button class="btn btn-lg btn-primary col-sm-2 col-sm-offset-5" type="button" id="submit">Next
            </button>
        </div>
    </div>

    <script>
        var i = 1;

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


            cloneOfKeyRadio.appendTo(".keyRadios");
            i++;
        }

        function isData() {

            var xAxisArray = [];
            var yAxisArray = [];

            var xAxis = $(".xAxis");
            var yAxis = $(".yAxis");

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

            var data = {
                xAxis: xAxisArray,
                yAxis: yAxisArray
            };
            return data;

        }
        ;


        $("#submit").click(function () {
            var data = isData();
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

