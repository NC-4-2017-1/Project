<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>HM Setup</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="    crossorigin="anonymous"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/resources/js/HMsetup.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class = "modal-content">
        <h2 class="col-sm-offset-4"> HM project Connection:</h2>
        <form id="connform" class="form-horizontal" role="form">
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="serverName">Server name:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="serverName" name="serverName" placeholder="Server name" required autofocus>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="port">Port:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="port" name="port" placeholder="Port" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="sid">SID:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="sid" name="sid" placeholder="SID" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="username">User name:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="username" name="username" placeholder="User name" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="password">Password:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="password" name="password" placeholder="Password" required>
                </div>
            </div>
            <div class="form-group row">
                <button class="btn btn-lg btn-primary col-sm-2 col-sm-offset-3" type="button" id="submit">Test</button>
                <button class="btn btn-lg btn-primary col-sm-2 col-md-offset-1" type="button" id="next">Next</button>
            </div>
            <div class="form-group row">
                <div class="col-sm-5 col-sm-offset-3 alert alert-danger hide" id = "error_conn"></div>
                <div class="col-sm-5 col-sm-offset-3 alert alert-success hide" id = "right_conn"></div>
            </div>
        </form>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
