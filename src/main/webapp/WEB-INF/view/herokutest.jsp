<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

sysvar1<c:out value="${sysvar1}" /> <br>
sysvar2<c:out value="${sysvar2}" /> <br>
sysvar3<c:out value="${sysvar3}" /> <br>
datasource<c:out value="${userdao}" /> <br>
userdao<c:out value="${projectdao}" /> <br>
projetdao<c:out value="${datasource}" /> <br>


</body>
</html>
