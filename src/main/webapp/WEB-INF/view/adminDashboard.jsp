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

    <title>Admin dashboard</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js"/>">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js"/>">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
</head>

<body>
<jsp:include page="header.jsp"/>

<input class="btn btn-default" id="add-button" type="button" onclick="createUser()" value="Add user"/>
<br>
    <div class="panel panel-default">
        <table class="table">
            <thead>
            <tr>
                <th>first name</th>
                <th>last name</th>
                <th>email</th>
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
                        <input class="btn btn-default btn-sm" type="button" value="delete"
                               onclick="deleteObj(${user.id});"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>



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

