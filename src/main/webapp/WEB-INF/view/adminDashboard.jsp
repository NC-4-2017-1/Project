<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin dashboard</title>
    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery-validate.bootstrap-tooltip.js"></script>
    <script type="text/javascript" src="/resources/js/searchForm.js"></script>
</head>

<body>
<jsp:include page="header.jsp"/>
<h3 class="pageName">User list</h3>
<form method="POST"  action="/user/admin-panel" class="searchUserForm">
    <div class="form-group">
        <label class="control-label pull-left search-input" for="SearchUser">Search user:</label>
        <div class="col-sm-3">
            <input class="form-control input-sm SearchUser" id="SearchUser" name="SearchUser"
                   type="text" placeholder="User email..."
            <c:if test = "${not empty SearchUserEmail}"> value = "${SearchUserEmail}" </c:if>
                   autofocus>
            <input type="text" name="field" value="${sortF}" hidden>
            <input type="text" name="sortType" value="${sortT}" hidden>
        </div>
        <div class="form-group col-sm-3">
            <button type="submit" class="btn btn-sm btn-primary" value="search" name="search">Search</button>
            <button type="submit" class="btn btn-sm btn-danger" value="cancel" name="cancel">Cancel</button>
        </div>
    </div>
</form>

<div class="pull-right">
    <button class="btn btn-sm btn-success" onclick="createUser()" >
        <i class="fa fa-plus-square" aria-hidden="true"></i>&nbsp;<b>Add user</b>
    </button>
</div>

<c:if test = "${not empty error1}">
    <div class="alert alert-danger admin-list-users"> <strong>Warning!</strong> ${error1}</div>
</c:if>
<c:if test = "${empty error1 && not empty users}">
                <table class="table table-striped table-condensed table-sm">
                    <thead>
                    <tr>
                        <th>
                            <c:if test = "${fn:length(users) > 1}">
                                <c:if test = "${sortF == 'first_name'}">
                                    <u>First name</u>
                                    <c:if test = "${sortT == 'desc'}">
                                        <a href="/user/admin-panel?field=first_name&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">
                                            <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                    </c:if>
                                    <c:if test = "${sortT == 'asc'}">
                                        <a href="/user/admin-panel?field=first_name&sortType=desc&whereEmail=${SearchUserEmail}"  class="desc">
                                            <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                    </c:if>
                                </c:if>
                                <c:if test = "${sortF != 'first_name'}">
                                    First name
                                    <a href="/user/admin-panel?field=first_name&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">
                                        <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                    <a href="/user/admin-panel?field=first_name&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">
                                        <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${fn:length(users) == 1}">
                                First name
                            </c:if>
                        </th>
                        <th>
                            <c:if test = "${fn:length(users) > 1}">
                                <c:if test = "${sortF == 'last_name'}">
                                    <u>Last name</u>
                                    <c:if test = "${sortT == 'desc'}">
                                        <a href="/user/admin-panel?field=last_name&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                    </c:if>
                                    <c:if test = "${sortT == 'asc'}">
                                        <a href="/user/admin-panel?field=last_name&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                    </c:if>
                                </c:if>
                                <c:if test = "${sortF != 'last_name'}">
                                    Last name
                                    <a href="/user/admin-panel?field=last_name&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                    <a href="/user/admin-panel?field=last_name&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${fn:length(users) == 1}">
                                Last name
                            </c:if>
                        </th>
                        <th>
                            <c:if test = "${fn:length(users) > 1}">
                                <c:if test = "${sortF == 'email'}">
                                    <u>Email</u>
                                    <c:if test = "${sortT == 'desc'}">
                                        <a href="/user/admin-panel?field=email&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                    </c:if>
                                    <c:if test = "${sortT == 'asc'}">
                                        <a href="/user/admin-panel?field=email&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                    </c:if>
                                </c:if>
                                <c:if test = "${sortF != 'email'}">
                                    Email
                                    <a href="/user/admin-panel?field=email&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                    <a href="/user/admin-panel?field=email&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${fn:length(users) == 1}">
                                Email
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
                                <input class="btn btn-danger btn-xs  pull-right" type="button" value="delete"
                                       onclick="deleteObj(${user.id});"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
</c:if>
<c:if test = "${empty users && empty error1 }">
    <div class="alert alert-info user-list-info"><strong>Info!</strong> Users not found.</div>
</c:if>

<jsp:include page="footer.jsp"/>


</body>
<script>
    function deleteObj(id) {
        var x = new XMLHttpRequest();
        x.open("DELETE", "/user/delete/" + id);
        x.onreadystatechange = function () {
            window.location.replace("/user/admin-panel?field=${sortF}&sortType=${sortT}&whereEmail=${SearchUserEmail}");
        }
        x.send();
    }
    function createUser() {
        window.location.href = "/user/create-user";
    }
</script>
</html>

