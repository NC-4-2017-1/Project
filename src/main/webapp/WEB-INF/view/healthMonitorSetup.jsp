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
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="    crossorigin="anonymous"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/resources/js/HMsetup.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class = "container-hm-setup">
        <h3 class="pageName"> HM project Connection:</h3>
        <form id="connform" class="form-horizontal" role="form">
            <div class="form-group">
                <label class="control-label col-sm-5" for="serverName">Server name:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="serverName" name="serverName" placeholder="Server name" required autofocus>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-5" for="port">Port:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="port" name="port" placeholder="Port" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-5" for="sid">SID:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="sid" name="sid" placeholder="SID" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-5" for="username">User name:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="username" name="username" placeholder="User name" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-5" for="password">Password:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="password" name="password" placeholder="Password" required>
                </div>
            </div>
            <div>
                <div class="col-sm-5">
                <button class="btn btn-sm btn-primary col-sm-3 col-sm-offset-9" type="button" id="submit">
                    <i class="fa fa-cog" aria-hidden="true"></i><b>&nbsp;Test</b>
                </button>
                </div>

                <div class="col-sm-5">
                <button class="btn btn-sm btn-primary col-sm-3" type="button" id="next">
                    <i class="fa fa-arrow-right" aria-hidden="true"></i><b>&nbsp;Next</b>
                </button>
                </div>
            </div>
        </form>
    <div class="hm-setup-error">
        <div class="col-sm-5 col-sm-offset-3 alert alert-danger hide" id = "error_conn"></div>
        <div class="col-sm-5 col-sm-offset-3 alert alert-success hide" id = "right_conn"></div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
