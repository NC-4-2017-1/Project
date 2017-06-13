<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin dashboard</title>

    <jsp:include page="headFragment.jsp"/>
    <link href="<c:url value="/resources/js/bootstrap.min.js"/>">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js"/>">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
</head>

<body>
<jsp:include page="header.jsp"/>
<h3 class="pageName">USER LIST</h3>
<div class="pull-right">
    <!--<input class="btn btn-sm" id="add-button" type="button" onclick="createUser()" value="Add user"/>-->
    <button class="btn btn-sm btn-success" onclick="createUser()" >
        <i class="fa fa-plus-square" aria-hidden="true"></i>&nbsp;<b>Add user</b>
    </button>
</div>
                <table class="table table-striped table-condensed table-sm">
                    <thead>
                    <tr>
                        <th>FIRST NAME</th>
                        <th>LAST NAME</th>
                        <th>EMAIL</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td><p>${user.firstName}</p></td>
                            <td><p>${user.lastName}</p></td>
                            <td><p>${user.email}</p></td>
                            <td>
                                <input class="btn btn-danger btn-xs" type="button" value="delete"
                                       onclick="deleteObj(${user.id});"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>


<jsp:include page="footer.jsp"/>


</body>
<script>
    function deleteObj(id) {
        var x = new XMLHttpRequest();
        x.open("DELETE", "/user/delete/" + id);
        x.onreadystatechange = function () {
            window.location.replace("/user/admin-panel");
        }
        x.send();
    }
    function createUser() {
        window.location.href = "/user/create-user";
    }
</script>
</html>

