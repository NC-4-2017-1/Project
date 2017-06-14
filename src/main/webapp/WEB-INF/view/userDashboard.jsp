<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>User dasboard</title>

    <jsp:include page="headFragment.jsp"/>
    <link href="<c:url value="/resources/css/styles-dashboard.css" />" rel="stylesheet">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link href="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" rel="stylesheet">

    <script src="/resources/js/jquery-3.2.1.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<h3 class="pageName">PROJECT LIST</h3>
<div class="pull-right">
    <button class="btn btn-sm btn-success" onclick="myFunction()">
        <i class="fa fa-plus-square" aria-hidden="true"></i>&nbsp;<b>Add Project</b>
    </button>
</div>
<ul class="nav nav-tabs" id="projectTab">
    <li><a data-target="#1" data-toggle="tab">My projects</a></li>
    <li><a data-target="#2" data-toggle="tab">Shared projects</a></li>
</ul>
<div class="tab-content">
        <div class="tab-pane" id="1">
            <c:if test = "${not empty userProjects}">
            <form>
                    <div class="form-group ">
                        <label class="control-label pull-left search-input" for="SearchMyProject">Search project:</label>
                        <div class="col-sm-3 ">
                            <input class="form-control input-sm" id="SearchMyProject" type="text" placeholder="Project name..." class="search-in-list" autofocus>
                        </div>
                    </div>
            </form>
                <table class="table table-striped table-condensed project-list">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Created at</th>
                        <th>Type</th>
                        <th>Created by</th>
                    </tr>
                    </thead>
                    <tbody id="allElementsOfMyProjects">
                    <c:forEach items="${userProjects}" var="project">
                        <tr data-select="my_project" data-name="${project.name}">
                            <td>
                                <c:choose>
                                    <c:when test="${project.type.name().equals('DATA_VISUALIZATION')}">
                                        <form role="form" method="GET" action="/project/project-dv">
                                            <button type="submit" class="btn btn-link btn-xs pr-name"
                                                    <c:if test = "${not empty fn:trim(project.description)}">
                                                        data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                                    </c:if>
                                                    name="projDvId" value="${project.id}">${project.name}</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${project.type.name().equals('HEALTH_MONITORING')}">
                                        <form role="form" method="GET" action="/project/project-hm">
                                            <button type="submit" class="btn btn-link btn-xs pr-name"
                                                    <c:if test = "${not empty fn:trim(project.description)}">
                                                        data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                                    </c:if>
                                                    name="projHmId" value="${project.id}">${project.name}</button>
                                        </form>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td class="pr-date">
                                    ${project.creationDate}
                            </td>
                            <td class="pr-type">
                                    ${project.type.toString()}
                            </td>
                            <td class="pr-autor">
                                    ${project.authorFullName}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test = "${empty userProjects}">
                <div class="alert alert-info prj-list-info"><strong>Info!</strong> You haven't projects yet.</div>
            </c:if>
        </div>



        <div class="tab-pane" id="2">

            <c:if test = "${not empty sharedToUserProjects}">
                <%--<div class="col-sm-3 search-input">
                    <label class="control-label" for="SearchSharedProject">Search project:</label>
                    <input class="form-control conn-field input-sm" id="SearchSharedProject" type="text" placeholder="Project name..." class="search-in-list" autofocus>
                </div>--%>

                <form>
                    <div class="form-group ">
                        <label class="control-label pull-left search-input" for="SearchSharedProject">Search project:</label>
                        <div class="col-sm-3 ">
                            <input class="form-control input-sm" id="SearchSharedProject" type="text" placeholder="Project name..." class="search-in-list" autofocus>
                        </div>
                    </div>
                </form>

                <table class="table table-striped table-condensed project-list">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Created at</th>
                        <th>Type</th>
                        <th>Created by</th>
                    </tr>
                    </thead>
                    <tbody id="allElementsOfSharedProjects">
                    <c:forEach items="${sharedToUserProjects}" var="project">
                        <tr data-select="shared_project" data-name="${project.name}">
                            <td>
                                <c:choose>
                                    <c:when test="${project.type.name().equals('DATA_VISUALIZATION')}">
                                        <form role="form" method="GET" action="/project/project-dv">
                                            <button type="submit" class="btn btn-link btn-xs pr-name"
                                                    <c:if test = "${not empty fn:trim(project.description)}">
                                                        data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                                    </c:if>
                                                    name="projDvId" value="${project.id}">${project.name}</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${project.type.name().equals('HEALTH_MONITORING')}">
                                        <form role="form" method="GET" action="/project/project-hm">
                                            <button type="submit" class="btn btn-link btn-xs pr-name"
                                                    <c:if test = "${not empty fn:trim(project.description)}">
                                                        data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                                    </c:if>
                                                    name="projHmId" value="${project.id}">${project.name}</button>
                                        </form>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td class="pr-date">
                                    ${project.creationDate}
                            </td>
                            <td class="pr-type">
                                    ${project.type.toString()}
                            </td>
                            <td class="pr-autor">
                                    ${project.authorFullName}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test = "${empty sharedToUserProjects}">
                <div class="alert alert-info prj-list-info"><strong>Info!</strong> There are no shared projects for you.</div>
            </c:if>
        </div>

</div>


<%--<div class = "pull-left">
    <button class="btn btn-default btn-sm" id="span-myProjects" onclick="activatePr()">My projects</button>
    <button class="btn btn-default btn-sm" id="span-sharedToMeProjects" onclick="activateSh()">Shared to me projects</button>
</div>
    <div id="myProjects" class="active-divs">
        <table class="table table-striped table-condensed project-list">
            <thead>
            <tr>
                <th>Name</th>
                <th>Created at</th>
                <th>Type</th>
                <th>Created by</th>
            </tr>
            </thead>
            <tbody>
                 <c:forEach items="${userProjects}" var="project">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${project.type.name().equals('DATA_VISUALIZATION')}">
                                        <form role="form" method="GET" action="/project/project-dv">
                                            <button type="submit" class="btn btn-link btn-xs pr-name"
                                                    <c:if test = "${not empty fn:trim(project.description)}">
                                                        data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                                    </c:if>
                                                    name="projDvId" value="${project.id}">${project.name}</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${project.type.name().equals('HEALTH_MONITORING')}">
                                        <form role="form" method="GET" action="/project/project-hm">
                                            <button type="submit" class="btn btn-link btn-xs pr-name"
                                                    <c:if test = "${not empty fn:trim(project.description)}">
                                                        data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                                    </c:if>
                                                    name="projHmId" value="${project.id}">${project.name}</button>
                                        </form>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td class="pr-date">
                                    ${project.creationDate}
                            </td>
                            <td class="pr-type">
                                   ${project.type.toString()}
                            </td>
                            <td class="pr-autor">
                                    ${project.authorFullName}
                            </td>
                        </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="sharedToMeProjects" class="inactive-divs">
        <table class="table table-striped table-condensed project-list">
            <thead>
            <tr>
                <th>Name</th>
                <th>Created at</th>
                <th>Type</th>
                <th>Created by</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sharedToUserProjects}" var="project">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${project.type.name().equals('DATA_VISUALIZATION')}">
                                <form role="form" method="GET" action="/project/project-dv">
                                    <button type="submit" class="btn btn-link btn-xs pr-name"
                                            <c:if test = "${not empty fn:trim(project.description)}">
                                                data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                            </c:if>
                                            name="projDvId" value="${project.id}">${project.name}</button>
                                </form>
                            </c:when>
                            <c:when test="${project.type.name().equals('HEALTH_MONITORING')}">
                                <form role="form" method="GET" action="/project/project-hm">
                                    <button type="submit" class="btn btn-link btn-xs pr-name"
                                            <c:if test = "${not empty fn:trim(project.description)}">
                                                data-toggle="tooltip" title="${project.description}"  data-placement="right"
                                            </c:if>
                                            name="projHmId" value="${project.id}">${project.name}</button>
                                </form>
                            </c:when>
                        </c:choose>
                    </td>
                    <td class="pr-date">
                            ${project.creationDate}
                    </td>
                    <td class="pr-type">
                            ${project.type.toString()}
                    </td>
                    <td class="pr-autor">
                            ${project.authorFullName}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>--%>

<jsp:include page="footer.jsp"/>

</body>
<script>
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        $('.nav-tabs a:first').tab('show');
    });

    function myFunction() {
        window.location.assign("/project/new-layout");
    }

    /* function activatePr(){
     $("#myProjects").removeClass("inactive-divs");
     $("#myProjects").addClass("active-divs");

     $("#sharedToMeProjects").removeClass("active-divs");
     $("#sharedToMeProjects").addClass("inactive-divs");
     }

     function activateSh(){
     $("#sharedToMeProjects").removeClass("inactive-divs");
     $("#sharedToMeProjects").addClass("active-divs");

     $("#myProjects").removeClass("active-divs");
     $("#myProjects").addClass("inactive-divs");
     }*/

    var searchField;
    var secondSearchField;
    var searchFieldData  = "";

    searchField = $("#SearchMyProject");
    searchField.on("keydown", OnSearchKeyDown);

    secondSearchField = $("#SearchSharedProject");
    secondSearchField.on("keydown", OnSearchKeyDown);

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

        var allUsersParent;
        var allUserTr;

        if(this.id == "SearchMyProject"){
            allUsersParent = $("#allElementsOfMyProjects");
            allUserTr = allUsersParent.find("[data-select=my_project]");
            BuildSearchBody(searchFieldData, allUserTr);
        }else if(this.id == "SearchSharedProject"){
            allUsersParent = $("#allElementsOfSharedProjects");
            allUserTr = allUsersParent.find("[data-select=shared_project]");
            BuildSearchBody(searchFieldData, allUserTr);
        }

    }


    function BuildSearchBody(searchFieldData, allUserTr)
    {
        $.each(allUserTr, function(key, value)
        {
            var currentElement;
            var currentName;

            currentElement = $(value);
            currentName = currentElement.attr("data-name");

            currentName = currentName.toLowerCase();
            searchFieldData = searchFieldData.toLowerCase();

            if((currentName.indexOf(searchFieldData) +1)) {
                currentElement.show();
            }
            else {
                currentElement.hide();
            }
        });
    }

</script>
</html>
