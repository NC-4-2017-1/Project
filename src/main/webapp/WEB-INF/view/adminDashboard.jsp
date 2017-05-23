<%@ page import="com.dreamteam.datavisualizator.models.UserTypes" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <title>admin dashboard</title>
</head>
<body>
﻿
<script
        src="https://code.jquery.com/jquery-3.2.1.js"
        integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
        crossorigin="anonymous">
</script>


<sec:authorize access="!isAuthenticated()">
    <% response.sendRedirect("login"); %>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <sec:authorize access="hasAuthority('ADMIN')">
        This content will only be visible to users who have
        the "ADMIN" authority in their list of <tt>GrantedAuthority</tt>s.
    </sec:authorize>
    <sec:authorize access="!hasAuthority('ADMIN')">
        NOPE
    </sec:authorize>
</sec:authorize>

</body>
</html>
