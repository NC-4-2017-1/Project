<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create user</title>
    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery-validate.bootstrap-tooltip.js"></script>
    <script type="text/javascript" src="/resources/js/createUser.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<c:url value="/user/create" var="create"/>
<c:if test="${errorMessage!=null}">
    <div class="alert alert-danger text-center" style="padding: 5px;" role="alert">
        <p>${errorMessage}</p>
    </div>
</c:if>
<form:form class="form-horizontal col-sm-offset-4 createUser" modelAttribute="user" action="${create}" method="post">
    <fieldset>
        <%--<div id="legend">
            <legend class="">Registration</legend>
        </div>--%>

        <div class="row col-sm-12">
            <h3 class="pageName col-sm-6"> Registration</h3>
        </div>

        <table class="userCreateTable">
            <tr><td class="text-right"><label class="control-label" for="firstName">First name:</label></td><td>
                <form:input type="text" id="firstName" name="firstName" path="firstName" placeholder="First name" class="form-control input-sm userCreateInput"   required="required"/>
            </td></tr>
            <tr><td class="text-right"><label class="control-label" for="lastName">Last name:</label></td><td>
                <form:input type="text" id="lastName"  name="lastName" path="lastName" placeholder="Last name" class="form-control input-sm userCreateInput"  required="required"/>
            </td></tr>
            <tr><td class="text-right"><label class="control-label" for="email">Email:</label></td><td>
                <form:input type="email" id="email" name="email" path="email" placeholder="someaddress@email.com" class="form-control input-sm userCreateInput" required="required"/>
            </td></tr>
            <tr><td class="text-right"><label class="control-label" for="password">Password:</label></td><td>
                <form:input type="password" id="password" name="password" path="password" placeholder="password" class="form-control input-sm userCreateInput"
                            required="required"/>
            </td></tr>
            <tr><td colspan="2" class="text-center"><button class="btn btn-success">Register</button></td>
                <td></td></tr>

        </table>
       <%-- <div class="form-group col-sm-12">
            <label class="control-label col-sm-3" for="firstName">First name:</label>
            <div class="col-sm-9">
                <form:input type="text" id="firstName" name="firstName" path="firstName" placeholder="First name" class="form-control input-sm userCreateInput"   required="required"/>
            </div>
        </div>
        <div class="form-group col-sm-12">
            <label class="control-label col-sm-3" for="lastName">Last name:</label>
            <div class="col-sm-9">
                <form:input type="text" id="lastName"  name="lastName" path="lastName" placeholder="Last name" class="form-control input-sm userCreateInput"  required="required"/>
            </div>
        </div>

        <div class="form-group col-sm-12">
            <label class="control-label col-sm-3" for="email">Email:</label>
            <div class="col-sm-9">
                <form:input type="email" id="email" name="email" path="email" placeholder="someaddress@email.com" class="form-control input-sm userCreateInput" required="required"/>
            </div>
        </div>
        <div class="form-group col-sm-12">
            <label class="control-label col-sm-3" for="password">Password:</label>
            <div class="col-sm-9">
                <form:input type="password" id="password" name="password" path="password" placeholder="password" class="form-control input-sm userCreateInput"
                            required="required"/>
            </div>
        </div>
        <div class="form-group col-sm-12">
            <label class="control-label col-sm-3"></label>
            <div class="col-sm-9">
                <button class="btn btn-success">Register</button>
            </div>
        </div>--%>

    </fieldset>
</form:form>
<jsp:include page="footer.jsp"/>

</body>
</html>