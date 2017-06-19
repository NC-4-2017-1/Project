<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>DV Setup</title>
    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.file-input.js"></script>
    <script>
        $(document).ready(function () {
            $('input[type=file]').bootstrapFileInput();
        });
    </script>
</head>

<body>

<jsp:include page="header.jsp"/>

    <div class="setup-error">
        <div class="alert alert-danger
            <c:if test="${messageFile == null}">
                hide
            </c:if>
            text-center" >${messageFile}
        </div>
        <div class="alert alert-danger
            <c:if test="${messageFormat == null}">
                hide
            </c:if>
            text-center" id = "right_conn">${messageFormat}
        </div>
    </div>
       <h3 class="pageName">Setup: please select a file to upload and date format</h3>
        <br>
        <form data-toggle="validator" class="form-horizontal" role="form" method="POST" action="/project/upload"
              enctype="multipart/form-data">
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3">Date format:</label>
                <div class="col-sm-3">
                    <select class="form-control input-sm" id="dateFormat" name="dateFormat">
                        <c:forEach items="${dateFormat}" var="entry">
                            <option value="${entry.key}">${entry.value}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3">File:</label>
                <div class="col-sm-7">
                    <input class="btn-sm input-sm" type="file" accept=".xml, .csv" name="file"/><br/>
                </div>
            </div>

            <div class="form-group">
            <div class="pull-right">
                <button class="btn btn-sm btn-primary" type="submit" value="Next">
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
    $("#back").click(function () {
        window.location.assign("/project/new-layout");
    });
</script>


<%--<jsp:include page="footer.jsp"/>--%>
</body>
</html>
