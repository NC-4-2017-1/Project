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
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
</head>
<body>
<div class="sortTab" hidden>${sortTab} </div>
<jsp:include page="header.jsp"/>

<h3 class="pageName">Project list</h3>
<%--<c:if test = "${not empty error}">
    <div class="alert alert-danger hide text-center">${error}</div>
</c:if>--%>

<div class="pull-right">
    <button class="btn btn-sm btn-success add-project" onclick="myFunction()">
        <i class="fa fa-plus-square" aria-hidden="true"></i>&nbsp;<b>Add Project</b>
    </button>
</div>

<ul class="nav nav-tabs" id="projectTab">
    <li><a data-target="#1" data-toggle="tab" class = "first">My projects</a></li>
    <li><a data-target="#2" data-toggle="tab" class = "second">Shared projects</a></li>
</ul>
<div class="tab-content">
    <%--<div>${sortF} </div>--%>

        <div class="tab-pane" id="1">
            <c:if test = "${not empty error1}">
                <div class="alert alert-danger prj-list-info"> <strong>Warning!</strong> ${error1}</div>
            </c:if>
        <c:if test = "${empty error1}">
            <c:if test = "${not empty userProjects}">
            <form>
                    <div class="form-group ">
                        <label class="control-label pull-left search-input" for="SearchMyProject">Search project:</label>
                        <div class="col-sm-3">
                            <input class="form-control input-sm" id="SearchMyProject" type="text" placeholder="Project name..." class="search-in-list" autofocus>
                        </div>
                    </div>
            </form>
                <table id="table-sort" class="table table-striped table-condensed project-list">
                    <thead>
                    <tr>
                        <th>
                           <%-- <form method="POST"  action="/user/dashboard-get">
                                <input type="text" value=" 2 " name="field" hidden>
                                <button type="submit" class="btn btn-link desc" name = "sort-type" value=" desc"><i class="fa fa-sort-desc" aria-hidden="true"></i></button>
                                <button type="submit" class="btn btn-link asc"  name = "sort-type" value=" asc"><i class="fa fa-sort-asc" aria-hidden="true"></i></button>
                            </form>--%>
                            <c:if test = "${fn:length(userProjects) > 1}">
                                <c:if test = "${sortF == 'name'}">
                                    <u>Name</u>
                                    <c:if test = "${sortT == 'desc'}">
                                        <a href="/user/dashboard-get/name/asc/1" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                        <%-- <button class="btn btn-sm btn-link" type="button" onclick="sort(2, 'asc')"><i class="fa fa-long-arrow-up" aria-hidden="true"></i></button>--%>
                                     </c:if>
                                     <c:if test = "${sortT == 'asc'}">
                                         <a href="/user/dashboard-get/name/desc/1" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                       <%-- <button class="btn btn-sm btn-link" type="button" onclick="sort(2, 'desc')"><i class="fa fa-long-arrow-down" aria-hidden="true"></i></button>--%>
                                    </c:if>
                                </c:if>
                                <c:if test = "${sortF != 'name'}">
                                    Name
                                        <a href="/user/dashboard-get/name/asc/1" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                        <a href="/user/dashboard-get/name/desc/1" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${fn:length(userProjects) == 1}">
                                Name
                            </c:if>
                        </th>
                        <th>
                            <c:if test = "${fn:length(userProjects) > 1}">
                                <c:if test = "${sortF == 'creation_date'}">
                                    <u>Created at</u>
                                    <c:if test = "${sortT == 'desc'}">
                                        <a href="/user/dashboard-get/creation_date/asc/1" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                    </c:if>
                                    <c:if test = "${sortT == 'asc'}">
                                        <a href="/user/dashboard-get/creation_date/desc/1" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                    </c:if>
                                </c:if>
                                <c:if test = "${sortF != 'creation_date'}">
                                    Created at
                                        <a href="/user/dashboard-get/creation_date/desc/1" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                        <a href="/user/dashboard-get/creation_date/asc/1" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${fn:length(userProjects) == 1}">
                                Created at
                            </c:if>
                        </th>
                        <th>Type</th>
                        <th></th>
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
                                                        data-toggle="tooltip" title="${fn:substring(project.description, 0, 150)}
                                                        <c:if test ="${fn:length(project.description) > 149}" >
                                                            ...
                                                         </c:if>
                                                         " data-placement="right"
                                                    </c:if>
                                                    name="projDvId" value="${project.id}">${project.name}</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${project.type.name().equals('HEALTH_MONITORING')}">
                                        <form role="form" method="GET" action="/project/project-hm">
                                            <button type="submit" class="btn btn-link btn-xs pr-name"
                                                    <c:if test = "${not empty fn:trim(project.description)}">
                                                        data-toggle="tooltip" title="${fn:substring(project.description, 0, 150)}
                                                        <c:if test ="${fn:length(project.description) > 149}" >
                                                            ...
                                                        </c:if>
                                                        " data-placement="right"
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
                            <%--<td class="pr-autor">
                                    ${project.authorFullName}
                            </td>--%>
                            <td>
                                <div class="btn-group pull-right">
                                     <button type="button" class="btn btn-xs btn-primary dropdown-toggle" data-toggle="dropdown">
                                                    Actions <span class="caret"></span></button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="<c:url value="/project/delete/${project.id}/${project.type}"  />">
                                                        <i class="fa fa-trash-o fa-lg"></i> Delete</a></li>
                                        <li><a href="<c:url value="/project/share/${project.id}/3/desc" />">
                                                        <i class="fa fa-share-alt"></i> Share</a></li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test = "${empty userProjects}">
                <div class="alert alert-info prj-list-info"><strong>Info!</strong> You haven't projects yet.</div>
            </c:if>
    </c:if>
    </div>



        <div class="tab-pane" id="2">
        <c:if test = "${not empty error2}">
            <div class="alert alert-danger prj-list-info"> <strong>Warning!</strong> ${error1}</div>
        </c:if>
        <c:if test = "${empty error2}">
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
                        <th>
                            <c:if test = "${fn:length(sharedToUserProjects) > 1}">
                                <c:if test = "${sortF == 'name'}">
                                    <u>Name</u>
                                    <c:if test = "${sortT == 'desc'}">
                                        <a href="/user/dashboard-get/name/asc/2" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                    </c:if>
                                    <c:if test = "${sortT == 'asc'}">
                                        <a href="/user/dashboard-get/name/desc/2" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                    </c:if>
                                </c:if>
                                <c:if test = "${sortF != 'name'}">
                                    Name
                                        <a href="/user/dashboard-get/name/desc/2" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                        <a href="/user/dashboard-get/name/asc/2" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${fn:length(sharedToUserProjects) == 1}">
                                Name
                            </c:if>
                        </th>
                        <th>
                            <c:if test = "${fn:length(sharedToUserProjects) > 1}">
                                <c:if test = "${sortF == 'creation_date'}">
                                    <u>Created at</u>
                                    <c:if test = "${sortT == 'desc'}">
                                        <a href="/user/dashboard-get/creation_date/asc/2" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                    </c:if>
                                    <c:if test = "${sortT == 'asc'}">
                                        <a href="/user/dashboard-get/creation_date/desc/2" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                    </c:if>
                                </c:if>
                                <c:if test = "${sortF != 'creation_date'}">
                                    Created at
                                        <a href="/user/dashboard-get/creation_date/desc/2" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                        <a href="/user/dashboard-get/creation_date/asc/2" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${fn:length(sharedToUserProjects) == 1}">
                                Created at
                            </c:if>
                        </th>
                        <th>Type</th>
                        <th>
                            <c:if test = "${fn:length(sharedToUserProjects) > 1}">
                                <c:if test = "${sortF == 'author_name'}">
                                    <u>Created by</u>
                                    <c:if test = "${sortT == 'desc'}">
                                        <a href="/user/dashboard-get/author_name/asc/2" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                    </c:if>
                                    <c:if test = "${sortT == 'asc'}">
                                        <a href="/user/dashboard-get/author_name/desc/2" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                    </c:if>
                                 </c:if>
                                <c:if test = "${sortF != 'author_name'}">
                                    Created by
                                        <a href="/user/dashboard-get/author_name/desc/2" class="desc">     <i class="fa fa-long-arrow-down" aria-hidden="true"></i></a>
                                        <a href="/user/dashboard-get/author_name/asc/2" class="asc">     <i class="fa fa-long-arrow-up" aria-hidden="true"></i></a>
                                </c:if>
                            </c:if>
                            <c:if test = "${fn:length(sharedToUserProjects) == 1}">
                                Created by
                            </c:if>
                        </th>
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
                                                        data-toggle="tooltip" title="${fn:substring(project.description, 0, 150)}
                                                        <c:if test ="${fn:length(project.description) > 149}" >
                                                            ...
                                                        </c:if>
                                                        " data-placement="right"
                                                    </c:if>
                                                    name="projDvId" value="${project.id}">${project.name}</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${project.type.name().equals('HEALTH_MONITORING')}">
                                        <form role="form" method="GET" action="/project/project-hm">
                                            <button type="submit" class="btn btn-link btn-xs pr-name"
                                                    <c:if test = "${not empty fn:trim(project.description)}">
                                                        data-toggle="tooltip" title="${fn:substring(project.description, 0, 150)}
                                                        <c:if test ="${fn:length(project.description) > 149}" >
                                                            ...
                                                        </c:if>
                                                        " data-placement="right"
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
        </c:if>
        </div>

</div>


<jsp:include page="footer.jsp"/>

</body>
<script>


    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        //$('#projectTab a:first').tab('show');
       // $('.nav-tabs a[class = "active"]').tab('show');

        var sortTab = $.trim($( ".sortTab" ).text());
        if (sortTab == "1"){
            $('.nav-tabs a[class = "first"]').tab('show');
            //$('#projectTab a[href="#2"]').tab('show');
        } else if (sortTab == "2"){
            //$('#projectTab a[href="#2"]').tab('show');
            $('.nav-tabs a[class = "second"]').tab('show');
        } else {
            //$('#projectTab a[href="#1"]').tab('show');
            $('.nav-tabs a[class = "first"]').tab('show');
        }
    });



    function myFunction() {
        window.location.assign("/project/new-layout");
    }


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
