<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

sysvar1 <c:out value="${sysvar1}" /> <br>
sysvar2 <c:out value="${sysvar2}" /> <br>
sysvar3 <c:out value="${sysvar3}" /> <br>
datasource <c:out value="${datasource}" /> <br>
userdao <c:out value="${userdao}" /> <br>
projetdao <c:out value="${projectdao}" /> <br><br><br><br><br><br>

users: <c:forEach  items="${users}" var="user">
    <c:out value="${user.fullName}"/> <br>
</c:forEach>
<br><br><br><br>
stack trace: <c:out value="${exception.message}">
<br><br><br><br>
</body>
</html>
