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

    <title>Project view</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="stylesheet">
</head>

<body>


<!--Navigation-->
<div class="container">
    <div class="row">
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a href="<c:url value="/" />" class="navbar-brand hidden-sm">
                        <img class="brand-img" src="#" alt="Brand">
                    </a>
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#respinsive-menu">
                        <span class="sr-only">Open navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="respinsive-menu">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="<c:url value="/"/>">Main</a></li>
                        <li><a href="<c:url value="/user/dashboard"/>">Projects</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="#">${sessionScope.userObject.getFullName()}</a>
                        </li>
                        <li>
                            <a href="#">Exit</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="page-wrap">

</div>
<div class="container bg-white">
    <div class="row">
        <div class="content">
            <div class="col-md-12">


                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>
                <p>ada</p>

            </div>
        </div>

    </div>
</div>


<!--Footer-->


<div class="container">
    <div class="row">
        <div class="page-wrap">

        </div>
        <footer class="site-footer bg-white">
            <div class="col-md-12">
                <div class="padding-text-footer">Copyright © DREAMTEAM 2017. All rights reserved.</div>
            </div>
            <div class="padding-text-footer">
            </div>


        </footer>
    </div>
</div>
</body>
</html>
