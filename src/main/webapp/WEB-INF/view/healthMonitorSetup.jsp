<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>HM Setup</title>

    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery-validate.bootstrap-tooltip.js"></script>
    <script type="text/javascript" src="/resources/js/HMsetup.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class = "container-hm-setup">
    <div class="setup-error">
        <div class="alert alert-danger hide text-center" id = "error_conn"></div>
        <div class="alert alert-success hide text-center" id = "right_conn"></div>
    </div>
    <h3 class="pageName"> Configuring the database connection</h3>
       <br>
        <form id="connform" class="form-horizontal" role="form">
            <div class="form-group">
                <label class="control-label col-sm-5" for="serverName">Server name:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="serverName" name="serverName" placeholder="Server name" required autofocus>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-5" for="port">Port:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="port" name="port" placeholder="Port" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-5" for="sid">SID:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="sid" name="sid" placeholder="SID" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-5" for="username">User name:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="username" name="username" placeholder="User name" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-5" for="password">Password:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="password" name="password" placeholder="Password" required>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-2 col-sm-offset-7">
                     <button class="btn btn-sm btn-success" type="button" id="submit"
                            data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> Testing...">
                        <i class="fa fa-cog" aria-hidden="true"></i><b>&nbsp;Test</b>
                    </button>
                </div>

            </div>

  <%--          <div>
                <div class="col-sm-5 center-block">
                <button class="btn btn-sm btn-primary col-sm-4 col-sm-offset-8" type="button" id="submit"
                        data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> Testing...">
                    <i class="fa fa-cog" aria-hidden="true"></i><b>&nbsp;Test</b>
                </button>
                </div>

                <div class="col-sm-5">
                <button class="btn btn-sm btn-primary col-sm-4" type="button" id="next"
                        data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> Connecting...">
                    <b>Next</b>&nbsp;<i class="fa fa-arrow-right" aria-hidden="true"></i>
                </button>
                </div>
            </div>
            --%>
        </form>

</div>    <div class="pull-right">
    <button class="btn btn-sm btn-primary " type="button" id="next"
            data-loading-text="<i class='fa fa-circle-o-notch fa-spin'></i> Connecting...">
        <b>Next</b>&nbsp;<i class="fa fa-arrow-right" aria-hidden="true"></i>
    </button>
</div>
<div class="pull-right">
    <button class="btn btn-link" type="button" id="back">
        <b>Back</b>
    </button>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
