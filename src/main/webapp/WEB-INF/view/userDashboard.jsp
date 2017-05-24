<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
<head>
    <title>user dashboard</title>
</head>
<body>

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
       html and other code goes here (user dashboard)
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

﻿
<script
        src="https://code.jquery.com/jquery-3.2.1.js"
        integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
        crossorigin="anonymous">
</script>

</body>
</html>
