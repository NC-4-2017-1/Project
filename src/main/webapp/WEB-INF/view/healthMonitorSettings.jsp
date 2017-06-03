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
<div class = "modal-content">
    <div class="container">
        <h2> Select parameters:</h2>
                <form method="POST"  role="form" action="/project/health-monitor-settings-post" onsubmit="return(validate());">
                    <div class="form-group">
                        <input type="checkbox" name="selectors[]"  id="checkinstance" class="custom-control-input" value="8" checked disabled>
                        <label for="checkinstance" class="custom-control custom-checkbox">Instance information</label>
                    </div>
                    <div class="form-group">
                        <input type="checkbox" name="selectors[]"  id="lasterrors" class="custom-control-input" value="11">
                        <label for="lasterrors" class="custom-control custom-checkbox">Last DB errors</label>
                    </div>
                    <div class="form-group">
                        <input type="checkbox" name="selectors[]"  id="dblocks" class="custom-control-input" value="16">
                        <label for="dblocks" class="custom-control custom-checkbox">DB locks</label>
                    </div>
                    <div class="form-group">
                        <input type="checkbox" name="selectors[]"  id="sizetablespace" class="custom-control-input" value="9">
                        <label for="sizetablespace" class="custom-control custom-checkbox">Size for tablespace</label>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="sizetablelobCheck" class="custom-control-input" value="10">
                            <label for="sizetablelobCheck" class="custom-control custom-checkbox">Size for table-index-lob</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="tableindexlob" name="tableindexlob" placeholder="Segment name">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="activesessioncheck" class="custom-control-input" value="12">
                            <label for="activesessioncheck" class="custom-control custom-checkbox">Active sessions</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="activesession" name="activesession" placeholder="Top value">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="activequeriescheck" class="custom-control-input" value="13">
                            <label for="activequeriescheck" class="custom-control custom-checkbox">Active queries</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="activequeries" name="activequeries" placeholder="Top value">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="queriesrescheck" class="custom-control-input" value="14">
                            <label for="queriesrescheck" class="custom-control custom-checkbox">Queries results</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="queriesres" name="queriesres" placeholder="Top value">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="sqlmonitorcheck" class="custom-control-input" value="15">
                            <label for="sqlmonitorcheck" class="custom-control custom-checkbox">SQL queries monitor</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="sqlmonitor" name="sqlmonitor" placeholder="Top value">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="activejobscheck" class="custom-control-input" value="17">
                            <label for="activejobscheck" class="custom-control custom-checkbox">Active jobs</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="activejobs" name="activejobs" placeholder="Hour count (max: 48h)">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="graphcheck" class="custom-control-input" value="17">
                            <label for="graphcheck" class="custom-control custom-checkbox">Graph</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="graph" name="graph" placeholder="Hour count (max: 48h)">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="offset-sm-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Create</button>
                        </div>
                    </div>
                </form>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
