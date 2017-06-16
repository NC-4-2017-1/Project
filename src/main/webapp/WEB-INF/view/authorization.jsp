<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Authorzation page</title>
    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link href="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
</head>

<body>
<jsp:include page="header.jsp"/>
    <div class="container" style="width: 300px;">
                <c:url value="/j_spring_security_check" var="loginUrl"/>
                <form action="${loginUrl}" method="post">
                    <h3 class="form-signin-heading pageName">Please sign in</h3>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input type="text" class="form-control input-sm" name="j_username" placeholder="Email" required autofocus>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input type="password" class="form-control input-sm" name="j_password" placeholder="Password" required>
                        </div>
                    </div>
                    <div class="form-group">
                       <button class="btn btn-sm btn-primary btn-block" type="submit">Log in</button>
                    </div>

                    <c:if test = "${param.error != null}">
                  <div class="alert alert-danger center-block" style="padding: 5px;" role="alert">
                      Login failed: bad credentials
                    </div>
                    </c:if>

                </form>
</div>

<jsp:include page="footer.jsp"/>


</body>
</html>

