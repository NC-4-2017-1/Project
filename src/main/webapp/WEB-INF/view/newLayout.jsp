<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Project Creation</title>
    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>

        <h3 class="pageName"> Please select project type</h3>

        <form data-toggle="validator" class="form-horizontal" role="form">
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3">Type:</label>
                <div class="col-sm-3">
                    <select class="form-control input-sm" id="type" name="type">
                        <c:forEach items="${projectTypes}" var="entry">
                            <option value="${entry.key}">${entry.value}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="name">Name:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control input-sm" id="name" name="name" placeholder="name" required>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="description">Description:</label>
                <div class="col-sm-3 ">
                    <textarea class="form-control input-sm" rows="5" id="description"></textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="pull-right">
                    <button class="btn btn-sm btn-primary" type="button" id="submit">
                        <b>Next</b>&nbsp;<i class="fa fa-arrow-right" aria-hidden="true"></i>
                    </button>
                </div>
            <div class="pull-right">
                <button class="btn btn-link" type="button" id="back">
                    <b>Back</b>
                </button>
            </div>
        </div>
        </form>

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

    $("#back").click(function () {
        window.location.assign("/user/dashboard-get/0/s/0");
    });

</script>

<%--<jsp:include page="footer.jsp"/>--%>

</body>
</html>