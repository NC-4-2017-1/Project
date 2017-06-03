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

    <title>HM Settings</title>

    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/customstyles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/bootstrap.min.js" />" rel="stylesheet">
    <link href="<c:url value="/resources/js/jquery-3.2.1.min.js" />" rel="stylesheet">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
</head>

<body>
<jsp:include page="header.jsp"/>


                <form method="POST" action="/project/health-monitor-settings-post" onsubmit="return(validate());">

                    <div class="form-group has-success">
                        <label for="checkinstance" class="custom-control custom-checkbox">
                            <input id="checkinstance" type="checkbox" class="custom-control-input" checked disabled>
                            <span class="custom-control-indicator"></span>
                            <span class="custom-control-description">Instance information.</span>
                        </label>
                    </div>
                    <div class="form-group has-success">
                        <label class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input" checked disabled>
                            <span class="custom-control-indicator"></span>
                            <span class="custom-control-description">Last DB errors.</span>
                        </label>
                    </div>
                    <div class="form-group has-success">
                        <label class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input" checked disabled>
                            <span class="custom-control-indicator"></span>
                            <span class="custom-control-description">DB locks.</span>
                        </label>
                    </div>
                    <div class="form-group has-success">
                        <label class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input" checked disabled>
                            <span class="custom-control-indicator"></span>
                            <span class="custom-control-description">Size for tablespace.</span>
                        </label>
                    </div>


                    <div class="form-group row">
                        <label for="tableindexlob" class="col-sm-3 col-form-label">Size for table-index-lob</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="tableindexlob" name="tableindexlob" placeholder="Segment name">
                        </div>
                    </div>


                    <div class="form-group row">
                        <label for="activesessions" class="col-sm-3 col-form-label">Active sessions</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="activesessions" name="activesessions" placeholder="Top value">
                        </div>
                    </div>


                    <div class="form-group row">
                        <label for="activequeries" class="col-sm-3 col-form-label">Active queries</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="activequeries" name="activequeries" placeholder="Top value">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="queriesresults" class="col-sm-3 col-form-label">Queries results</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="queriesresults" name="queriesresults" placeholder="Top value">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="queriesmonitor" class="col-sm-3 col-form-label">SQL queries monitor</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="queriesmonitor" name="queriesmonitor" placeholder="Top value">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="activejobs" class="col-sm-3 col-form-label">Active jobs</label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="activejobs" name="activejobs" placeholder="Hour count (max: 48h)">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="offset-sm-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Create</button>
                        </div>
                    </div>
                </form>



<jsp:include page="footer.jsp"/>


</body>
</html>
