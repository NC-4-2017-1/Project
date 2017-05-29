<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

﻿<div> HM project Connection</div>


<form method="get" action="/project/health-monitor-setup-connection">
    Server name:<br><br>
    <input type="text" name="serverName" value="">
    <br><br>
    Port:<br>
    <input type="text" name="port" value="">
    <br><br>
    SID:<br>
    <input type="text" name="sid" value="">
    <br><br>
    User Name:<br>
    <input type="text" name="username" value="">
    <br><br>
    Password:<br>
    <input type="text" name="username" value="">
    <br>
    <br><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>
