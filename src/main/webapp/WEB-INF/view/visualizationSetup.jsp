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

<div>Visualization Setup</div>

﻿<form method="POST" action="/project/upload" enctype="multipart/form-data">
    <input type="file" accept=".xml, .csv"  name="file" /><br/>
    <input type="submit" value="Submit" />
</form>

</body>
</html>
