<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create user</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="script">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="script">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
</head>

<body>

<jsp:include page="header.jsp"/>
<c:url value="/user/create" var="create"/>
<form:form class="form-horizontal" modelAttribute="user" action="${create}" method="post"  style="margin: 0 auto;
    width: 500px;">
    <fieldset>
        <div id="legend">
            <legend class="">Register</legend>
        </div>
        <div class="control-group">
            <!-- First name -->
            <label class="control-label" for="firstname">First name</label>
            <div class="controls">
                <form:input type="text" id="firstname" path="firstName" placeholder="" class="input-xlarge"
                       required="required"/>
            </div>
        </div>
        <div class="control-group">
            <!-- Last name -->
            <label class="control-label" for="lastname">Last name</label>
            <div class="controls">
                <form:input type="text" id="lastname" path="lastName" placeholder="" class="input-xlarge"
                       required="required"/>
            </div>
        </div>
        <div class="control-group">
            <!-- Email -->
            <label class="control-label" for="email">Email</label>
            <div class="controls">
                <form:input type="email" id="email" path="email" placeholder="" class="input-xlarge" required="required"/>
            </div>
        </div>

        <div class="control-group">
            <!-- Password-->
            <label class="control-label" for="password">Password</label>
            <div class="controls">
                <form:input type="password" id="password" path="password" placeholder="" class="input-xlarge"
                       required="required"/>
            </div>
        </div>
        <br>
        <c:if test="${errorMessage!=null}">
            <div class="alert alert-danger center-block" style="padding: 5px;" role="alert">
                <p>${errorMessage}</p>
            </div>
        </c:if>
        <br>

        <div class="control-group">
            <!-- Button -->
            <div class="controls">
                <button class="btn btn-success">Register</button>
            </div>
        </div>
    </fieldset>
</form:form>
<jsp:include page="footer.jsp"/>

</body>
</html>
