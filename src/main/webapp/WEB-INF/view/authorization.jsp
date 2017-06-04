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

    <title>Authorzation page</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="stylesheet">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
</head>

<body>
<jsp:include page="header.jsp"/>
<div class = "modal-content">
    <div class="container" style="width: 300px;">
                <c:url value="/j_spring_security_check" var="loginUrl"/>
                <form action="${loginUrl}" method="post">
                    <h2 class="form-signin-heading">Please sign in</h2>
                    <div class="form-group">
                       <input type="text" class="form-control" name="j_username" placeholder="Email" required autofocus>
                    </div>
                    <div class="form-group">
                       <input type="password" class="form-control" name="j_password" placeholder="Password" required>
                    </div>
                    <div class="form-group">
                       <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
                    </div>

                    <c:if test = "${param.error != null}">
                  <div class="alert alert-danger center-block" style="padding: 5px;" role="alert">
                      Login failed: bad credentials
                    </div>
                    </c:if>

                </form>
    </div>
</div>

<jsp:include page="footer.jsp"/>


</body>
</html>

