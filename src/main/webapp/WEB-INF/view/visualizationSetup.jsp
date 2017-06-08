<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>DV Setup</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="stylesheet">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.js"
            integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE=" crossorigin="anonymous"></script>
</head>

<body>
<jsp:include page="header.jsp"/>
<div class="container bg-white">
    <div class="content">
        <h2 class="col-sm-offset-3"> Visualization Setup</h2>
        <form data-toggle="validator" class="form-horizontal" role="form" method="POST" action="/project/upload"
              enctype="multipart/form-data">
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3">Select date format:</label>
                <div class="col-sm-3">
                    <select class="form-control" id="dateFormat" name="dateFormat">
                        <c:forEach items="${dateFormat}" var="entry">
                            <option value="${entry.key}">${entry.value}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3">Select File:</label>
                <div class="col-sm-3">
                    <input type="file" accept=".xml, .csv" name="file"/><br/>
                </div>
            </div>
            <div class="form-group">
                <input class="btn btn-lg btn-primary col-sm-2 col-sm-offset-5" type="submit" value="Next"/>
            </div>
        </form>
        <div style="text-align: center; color: red" >
            ${message}
        </div>
    </div>
</div>
</body>
</html>
