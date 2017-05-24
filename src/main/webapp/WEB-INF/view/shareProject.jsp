<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
﻿<script
        src="https://code.jquery.com/jquery-3.2.1.js"
        integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
        crossorigin="anonymous">
</script>

<sec:authorize access="!isAuthenticated()">
    <% response.sendRedirect("/login"); %>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <sec:authorize access="hasAuthority('REGULAR_USER')">


        <%--html and other code goes here--%>
        <%--html and other code goes here--%>
        <%--html and other code goes here--%>
        <%--html and other code goes here--%>
        <%--html and other code goes here--%>
        <%--html and other code goes here--%>
        <%--html and other code goes here--%>
        html and other code goes here (share project)
        <%--html and other code goes here--%>
        <%--html and other code goes here--%>
        <%--html and other code goes here--%>
        <%--html and other code goes here--%>
        <%--html and other code goes here--%>
        <%--html and other code goes here--%>


    </sec:authorize>
    <sec:authorize access="!hasAuthority('REGULAR_USER')">
        <% response.sendRedirect("/"); %>
    </sec:authorize>
</sec:authorize>




</body>
</html>
