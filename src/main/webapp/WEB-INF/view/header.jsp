<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!--Navigation-->
<div class="container">
    <div class="row">
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a href="<c:url value="/"/>" class="navbar-brand">
                        Data visualizer
                    </a>
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive-menu">
                        <span class="sr-only">Open navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="responsive-menu">
                    <ul class="nav navbar-nav">
                        <sec:authorize access="hasRole('ADMIN')">
                            <li><a href="<c:url value="/user/admin-panel"/>" class="nav-link">All users</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ADMIN')">
                            <li><a href="<c:url value="/user/create-user"/>" class="nav-link">Create user</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('REGULAR_USER')">
                            <li>
                                <a href="<c:url value="/user/dashboard-get"/>" class="nav-link">All projects</a>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('REGULAR_USER')">
                            <li>
                                <a href="<c:url value="/project/new-layout"/>" class="nav-link">Create project</a>
                            </li>
                        </sec:authorize>
                    </ul>
                    <c:if test="${sessionScope.userObject!=null}">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="#" class="nav-link lobster-style">${sessionScope.userObject.getFullName()}</a>
                        </li>
                        <li>
                            <a href="<c:url value="/logout" />" class="nav-link">Log out</a>
                        </li>
                    </ul>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="page-wrap"></div>
<div class="page-wrap"></div>
<style>
.container-main{min-height: calc(100vh - 121px)}
</style>

<div class="container container-main">
    <div class="row">
        <div class="content">
            <div class="col-md-12">
                <div class="center-block" style="width:1170px;max-width:100%;">
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="content">
            <div class="col-md-12">
