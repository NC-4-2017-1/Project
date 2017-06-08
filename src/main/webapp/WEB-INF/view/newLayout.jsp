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

    <title>Project Creation</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="stylesheet">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.js" integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE=" crossorigin="anonymous"></script>
</head>

<body>
<jsp:include page="header.jsp"/>
<div class = "modal-content">
    <div class="container">
        <h2 class="col-sm-offset-4"> Please select project Type:</h2>
        <form data-toggle="validator" class="form-horizontal" role="form">
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3">Type:</label>
                <div class="col-sm-3">
                    <select class="form-control" id="type" name="type">
                        <c:forEach items="${projectTypes}" var="entry">
                            <option value="${entry.key}">${entry.value}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="name">Name:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="name" name="name" placeholder="name" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="description">Description:</label>
                <div class="col-sm-3">
                    <textarea class="form-control" rows="5" id="description"></textarea>
                </div>
                <!--<label class="control-label col-sm-2 col-sm-offset-3" for="description"></label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="description" name="description" placeholder="Description" required>
                </div>-->
            </div>
            <div class="form-group">
                <button class="btn btn-lg btn-primary col-sm-2 col-sm-offset-5" type="button" id="submit">Next</button>
            </div>
        </form>
    </div>
</div>

<script>

    function isData() {
        var type = $("#type").val();
        var name = $("#name").val();
        var description = $("#description").val();

        if (!name || !description) {
            alert("Fill all the fields");
            return false;
        }
        var data = {
            type: type,
            name: name,
            description: description
        };
        return data;
    };

    $("#submit").click(function () {
        var data = isData();
        if (data == false) {
            return false;
        }
        $.ajax({
            url: "/project/create",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            error: function (responce) {
                if (responce.responseText == "visualization-setup") {
                    window.location.assign("/project/visualization-setup");
                }
                else {
                    window.location.assign("/project/health-monitor-setup");
                }
            }
        });
    });

</script>

<jsp:include page="footer.jsp"/>

</body>
</html>