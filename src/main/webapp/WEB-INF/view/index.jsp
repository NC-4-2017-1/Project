<%@ page import="com.dreamteam.datavisualizator.dao.impl.UserDAOImpl" %>
<%@ page import="com.dreamteam.datavisualizator.dao.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>Hello</p>
<%--<h1>System env <%= System.getenv("SQL_JDBC_URL") %></h1>--%>
<h1>exec result:
    <p>${userdao}</p></h1>
</body>
</html>
