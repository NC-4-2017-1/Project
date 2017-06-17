<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin dashboard</title>
    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
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

<c:if test = "${not empty error1}">
    <div class="alert alert-danger admin-list-users"> <strong>Warning!</strong> ${error1}</div>
</c:if>
<c:if test = "${empty error1}">
                <table class="table table-striped table-condensed table-sm">
                    <thead>
                    <tr>
                        <th>
                            <c:if test = "${sortF == 'first_name'}">
                                <u>First name</u>
                                <c:if test = "${sortT == 'desc'}">
                                    <a href="/user/admin-panel/first_name/asc" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                </c:if>
                                <c:if test = "${sortT == 'asc'}">
                                    <a href="/user/admin-panel/first_name/desc" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${sortF != 'first_name'}">
                                First name
                                <a href="/user/admin-panel/first_name/asc" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                <a href="/user/admin-panel/first_name/desc" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                            </c:if>
                        </th>
                        <th>
                            <c:if test = "${sortF == 'last_name'}">
                                <u>Last name</u>
                                <c:if test = "${sortT == 'desc'}">
                                    <a href="/user/admin-panel/last_name/asc" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                </c:if>
                                <c:if test = "${sortT == 'asc'}">
                                    <a href="/user/admin-panel/last_name/desc" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${sortF != 'last_name'}">
                                Last name
                                <a href="/user/admin-panel/last_name/asc" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                <a href="/user/admin-panel/last_name/desc" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                            </c:if>
                        </th>
                        <th>
                            <c:if test = "${sortF == 'email'}">
                                <u>Email</u>
                                <c:if test = "${sortT == 'desc'}">
                                    <a href="/user/admin-panel/email/asc" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                </c:if>
                                <c:if test = "${sortT == 'asc'}">
                                    <a href="/user/admin-panel/email/desc" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${sortF != 'email'}">
                                Email
                                <a href="/user/admin-panel/email/asc" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                <a href="/user/admin-panel/email/desc" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                            </c:if>
                        </th>
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
</c:if>

<jsp:include page="footer.jsp"/>


</body>
<script>
    function deleteObj(id) {
        var x = new XMLHttpRequest();
        x.open("DELETE", "/user/delete/" + id);
        x.onreadystatechange = function () {
            window.location.replace("/user/admin-panel/0/s");
        }
        x.send();
    }
    function createUser() {
        window.location.href = "/user/create-user";
    }
</script>
</html>

