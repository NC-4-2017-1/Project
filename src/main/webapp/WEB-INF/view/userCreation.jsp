<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create user</title>

    <jsp:include page="headFragment.jsp"/>
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="script">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="script">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
</head>

<body>

<jsp:include page="header.jsp"/>
<c:url value="/user/create" var="create"/>
<form:form class="form-horizontal col-sm-6 col-sm-offset-3" modelAttribute="user" action="${create}" method="post">
    <fieldset>
        <div id="legend">
            <legend class="">Register</legend>
        </div>
        <c:if test="${errorMessage!=null}">
            <div class="alert alert-danger center-block" style="padding: 5px;" role="alert">
                <p>${errorMessage}</p>
            </div>
        </c:if>
        <div class="form-group col-sm-12">
            <label class="control-label col-sm-3" for="firstName">First name:</label>
            <div class="col-sm-9">
                <form:input type="text" id="firstname" path="firstName" placeholder="First name" class="form-control input-sm"   required="required"/>
            </div>
        </div>

        <%--<div class="control-group">
            <label class="control-label" for="firstname">First name</label>
            <div class="controls">
                <form:input type="text" id="firstname" path="firstName" placeholder="First name" class="form-control input-sm"
                       required="required"/>
            </div>
        </div>--%>
        <div class="form-group col-sm-12">
            <label class="control-label col-sm-3" for="lastName">Last name:</label>
            <div class="col-sm-9">
                <form:input type="text" id="lastname" path="lastName" placeholder="Last name" class="form-control input-sm"  required="required"/>
            </div>
        </div>
            <%--<div class="control-group">
                <label class="control-label" for="lastname">Last name</label>
                <div class="controls">
                    <form:input type="text" id="lastname" path="lastName" placeholder="Last name" class="form-control input-sm"
                           required="required"/>
                </div>
            </div>--%>

        <div class="form-group col-sm-12">
            <label class="control-label col-sm-3" for="email">Email:</label>
            <div class="col-sm-9">
                <form:input type="email" id="email" path="email" placeholder="someaddress@email.com" class="form-control input-sm" required="required"/>
            </div>
        </div>

        <%--<div class="control-group">
            <label class="control-label" for="email">Email</label>
            <div class="controls">
                <form:input type="email" id="email" path="email" placeholder="someaddress@email.com" class="form-control input-sm" required="required"/>
            </div>
        </div>--%>

        <div class="form-group col-sm-12">
            <label class="control-label col-sm-3" for="password">Password:</label>
            <div class="col-sm-9">
                <form:input type="password" id="password" path="password" placeholder="password" class="form-control input-sm"
                            required="required"/>
            </div>
        </div>

       <%-- <div class="control-group">
            <label class="control-label" for="password">Password</label>
            <div class="controls">
                <form:input type="password" id="password" path="password" placeholder="password" class="form-control input-sm"
                       required="required"/>
            </div>
        </div>--%>
        <%--<div class="control-group">
            <!-- Button -->
            <div class="controls">
                <button class="btn btn-success">Register</button>
            </div>
        </div>--%>

        <div class="form-group col-sm-12">
            <label class="control-label col-sm-3"></label>
            <div class="col-sm-9">
                <button class="btn btn-success">Register</button>
            </div>
        </div>

    </fieldset>
</form:form>
<jsp:include page="footer.jsp"/>

</body>
</html>