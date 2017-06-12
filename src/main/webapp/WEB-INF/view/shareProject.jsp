<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Share project</title>

    <jsp:include page="headFragment.jsp"/>
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="stylesheet">
    <script src="/resources/js/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container" id="container-middle">
    <div class="panel panel-default">
        <span>
            <input id="SearchUser" type="text" placeholder="Search User" class="search-in-list">
        </span>

        <table class="table">
            <thead>
            <tr>
                <th>first name</th>
                <th>last name</th>
                <th>email</th>
                <th></th>
            </tr>
            </thead>
            <tbody id="allElements">
            <c:forEach items="${users}" var="user">
                <tr data-select="user" data-firstname="${user.firstName}" data-lastname="${user.lastName}" data-email="${user.email}" class="user-in-list">
                    <td><p>${user.firstName}</p></td>
                    <td><p>${user.lastName}</p></td>
                    <td><p>${user.email}</p></td>
                    <td id="${user.id}">
                        <script>var i=0;</script>
                        <c:forEach items="${users_with_access}" var="user_with_access">
                            <c:if test="${user.email == user_with_access.email}">
                                <script>i++;</script>
                            </c:if>
                        </c:forEach>
                        <script>
                            if(i==0){
                                $("#${user.id}").html('<input class="btn btn-default btn-sm" type="button" value="share" onclick="shareObj(${user.id});"/>');
                            }
                            else {
                                $("#${user.id}").html('<input class="btn btn-default btn-sm" type="button" value="unshare" onclick="UnShareObj(${user.id});"/>');
                            }
                        </script>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
<script>
    function shareObj(id) {
        var x = new XMLHttpRequest();
        x.open("GET", "/project/share/${project_id}/" + id + "");
        x.onreadystatechange = function () {
            window.location.replace("/project/share/${project_id}/");
        }
        x.send();
    }

    function UnShareObj(id) {
        var x = new XMLHttpRequest();
        x.open("GET", "/project/unshare/${project_id}/" + id + "");
        x.onreadystatechange = function () {
            window.location.replace("/project/share/${project_id}/");
        }
        x.send();
    }

    var searchField;
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
    }

</script>
</html>
