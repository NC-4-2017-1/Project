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


<!--Navigation-->
<div class="container">
    <div class="row">
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a href="<c:url value="/" />" class="navbar-brand hidden-sm">
                        <img class="brand-img" src="#" alt="Brand">
                    </a>
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#respinsive-menu">
                        <span class="sr-only">Open navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="respinsive-menu">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="<c:url value="/"/>">Main</a></li>
                        <li><a href="<c:url value="/user/dashboard"/>">Projects</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="#">${sessionScope.userObject.getFullName()}</a>
                        </li>
                        <li>
                            <a href="#">Exit</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="page-wrap">
    <div style="height: 100px;"></div>
</div>
<div class="container bg-white">
    <div class="row">
        <div class="content">
            <div class="col-md-12">



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
            </div>
        </div>

    </div>
</div>


<script type="text/javascript">
    function validate()
    {
     var form = $('form');
       console.log(form) ;

      /*  if( document.myForm.Country.value == "-1" )
        {
            return false;
        }*/
        return( false );
    }
</script>




<!--Footer-->


<div class="container">
    <div class="row">
        <div class="page-wrap">

        </div>
        <footer class="site-footer bg-white">
            <div class="col-md-12">
                <div class="padding-text-footer">Copyright © DREAMTEAM 2017. All rights reserved.</div>
            </div>
            <div class="padding-text-footer">
            </div>


        </footer>
    </div>
</div>
</body>
</html>
