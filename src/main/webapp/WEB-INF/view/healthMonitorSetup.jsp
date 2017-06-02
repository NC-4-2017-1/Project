<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    ﻿<script src="https://code.jquery.com/jquery-3.2.1.js"   integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="    crossorigin="anonymous"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/HMsetup.js"></script>
</head>
<body>
<div class="container bg-white">
    <div class="content">
        <h2 class="col-sm-offset-3"> HM project Connection</h2>
        <form data-toggle="validator" class="form-horizontal" role="form">
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
            <div class="row">
                <button class="btn btn-lg btn-primary col-sm-2 col-sm-offset-3" type="button" id="submit">Test</button>
                <button class="btn btn-lg btn-primary col-sm-2 col-md-offset-1" type="button" id="next">Next</button>
            </div>
            <br>
            <div class="form-group">
                <div class="alert alert-danger hide"></div>
                <div class="alert alert-success hide"></div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
