<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Share project</title>
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
<h3 class="pageName">User list for share project </h3>
<h4 class="pageName"><c:if test = "${not empty projectName}">
                        "<i><c:out value="${projectName}"/></i>"
                    </c:if></h4>
<form method="POST"  action="/project/share" id="userSearchShare" class="searchUserForm">
    <div class="form-group">
        <label class="control-label pull-left search-input" for="SearchUser">Search user:</label>
        <div class="col-sm-3">
            <input class="form-control input-sm SearchUser" id="SearchUser" name="SearchUser"
                   type="text" placeholder="User email..."
            <c:if test = "${not empty SearchUserEmail}"> value = "${SearchUserEmail}" </c:if>
                   autofocus>
            <input type="text" name="field" value="${sortF}" hidden>
            <input type="text" name="sortType" value="${sortT}" hidden>
            <input type="text" name="projectId" value="${project_id}" hidden>
        </div>
        <div class="form-group col-sm-3">
            <button type="submit" class="btn btn-sm btn-success" value="search" name="search">Search</button>
            <button type="submit" class="btn btn-sm btn-danger" value="cancel" name="cancel">Cancel</button>
        </div>
    </div>
</form>

<c:if test = "${not empty error1}">
    <div class="alert alert-danger project-share-list-error"> <strong>Warning!</strong> ${error1}</div>
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
                                <a href="/project/share?idProject=${project_id}&field=first_name&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">
                                    <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                            </c:if>
                            <c:if test = "${sortT == 'asc'}">
                                <a href="/project/share?idProject=${project_id}&field=first_name&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">
                                    <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                            </c:if>
                        </c:if>
                        <c:if test = "${sortF != 'first_name'}">
                            First name
                            <a href="/project/share?idProject=${project_id}&field=first_name&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">
                                <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                            <a href="/project/share?idProject=${project_id}&field=first_name&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">
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
                                <a href="/project/share?idProject=${project_id}&field=last_name&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">
                                    <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                            </c:if>
                            <c:if test = "${sortT == 'asc'}">
                                <a href="/project/share?idProject=${project_id}&field=last_name&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">
                                    <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                            </c:if>
                        </c:if>
                        <c:if test = "${sortF != 'last_name'}">
                            Last name
                            <a href="/project/share?idProject=${project_id}&field=last_name&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">
                                <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                            <a href="/project/share?idProject=${project_id}&field=last_name&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">
                                <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
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
                                <a href="/project/share?idProject=${project_id}&field=email&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">
                                    <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>

                            </c:if>
                            <c:if test = "${sortT == 'asc'}">
                                <a href="/project/share?idProject=${project_id}&field=email&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">
                                    <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                            </c:if>
                        </c:if>
                        <c:if test = "${sortF != 'email'}">
                            Email
                            <a href="/project/share?idProject=${project_id}&field=email&sortType=asc&whereEmail=${SearchUserEmail}" class="asc">
                                <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                            <a href="/project/share?idProject=${project_id}&field=email&sortType=desc&whereEmail=${SearchUserEmail}" class="desc">
                                <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                        </c:if>
                    </c:if>
                    <c:if test = "${fn:length(users) == 1}">
                        Email
                    </c:if>
                </th>
                <th></th>
            </tr>
            </thead>
            <tbody id="allElements">
            <c:forEach items="${users}" var="user">
                <tr data-select="user" data-firstname="${user.firstName}" data-lastname="${user.lastName}" data-email="${user.email}" class="user-in-list">
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td id="${user.id}">
                        <script>var i=0;</script>
                        <c:forEach items="${users_with_access}" var="user_with_access">
                            <c:if test="${user.email == user_with_access.email}">
                                <script>i++;</script>
                            </c:if>
                        </c:forEach>
                        <script>
                           if(i==0){
                                $("#${user.id}").html('<input class="btn btn-xs btn-success bt-share pull-right" type="button" value="share" onclick="shareObj(${user.id});"/>');
                            }
                            else {
                                $("#${user.id}").html('<input class="btn btn-xs btn-danger bt-share pull-right" type="button" value="unshare" onclick="UnShareObj(${user.id});"/>');
                            }
                        </script>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
</c:if>
<c:if test = "${empty users && empty error1}">
    <div class="alert alert-info user-list-info"><strong>Info!</strong> Users not found.</div>
</c:if>

<jsp:include page="footer.jsp"/>

</body>
<script>
    function shareObj(id) {
        var x = new XMLHttpRequest();
        x.open("GET", "/project/share/${project_id}/" + id);
        x.onreadystatechange = function () {
            document.getElementById("userSearchShare").submit();
            //window.location.replace("/project/share/${project_id}/${sortF}/${sortT}");
        }
        x.send();
    }

    function UnShareObj(id) {
        var x = new XMLHttpRequest();
        x.open("GET", "/project/unshare/${project_id}/" + id);
        x.onreadystatechange = function () {
            document.getElementById("userSearchShare").submit();
            //window.location.replace("/project/share/${project_id}/${sortF}/${sortT}");
        }
        x.send();
    }

  /*  var searchField;
    var searchFieldData  = "";
    searchField = $("#SearchUser");
    searchField.on("keydown", OnSearchKeyDown);

    function OnSearchKeyDown(event)
    {
        var currentElement;
        var key;
        var keyCode;

        currentElement = $(event.currentTarget);
        key = event.key;
        keyCode = event.keyCode;

        console.debug(keyCode);
        searchFieldData = currentElement.val();
        if(keyCode >= 65 && keyCode <= 90) {
            searchFieldData += key;
        }
        else if(keyCode == 46) {
            searchFieldData = "";
        }
        else if(keyCode == 8) {
            var length;
            length = searchFieldData.length;
            if(length > 0) {
                searchFieldData = searchFieldData.substring(0, length - 1);
            }
            else {
                searchFieldData = "";
            }
        }
        else if(keyCode == 46) {
            var length;
            length = searchFieldData.length;
            if(length > 0) {
                searchFieldData = searchFieldData.substring(0, length - 1);
            }
            else {
                searchFieldData = "";
            }
        }
        BuildSearchBody(searchFieldData);
    }


    function BuildSearchBody(searchFieldData)
    {
        var allUsersParent;
        var allUserTr;

        allUsersParent = $("#allElements");
        allUserTr = allUsersParent.find("[data-select=user]");

        $.each(allUserTr, function(key, value)
        {
            var currentElement;
            var currentName;
            var currentLastName;
            var currentPhone;

            currentElement = $(value);
            currentName = currentElement.attr("data-firstname");
            currentLastName = currentElement.attr("data-lastname");
            currentPhone = currentElement.attr("data-email");

            currentName = currentName.toLowerCase();
            currentLastName = currentLastName.toLowerCase();
            currentPhone = currentPhone.toLowerCase();
            searchFieldData = searchFieldData.toLowerCase();

            if((currentName.indexOf(searchFieldData) +1) || (currentPhone.indexOf(searchFieldData)+1) || (currentLastName.indexOf(searchFieldData) +1)) {
                currentElement.show();
            }
            else {
                currentElement.hide();
            }
        });
    }*/

</script>
</html>
