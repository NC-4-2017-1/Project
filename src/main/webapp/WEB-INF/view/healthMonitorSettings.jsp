<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>HM Settings</title>

    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery-validate.bootstrap-tooltip.js"></script>
    <script type="text/javascript" src="/resources/js/selectorsForm.js"></script>
</head>

<body>
<jsp:include page="header.jsp"/>
    <div class="container">
        <div class="row col-sm-12">
            <c:if test = "${(errorSelector != null) || (errorGraphic != null) || (errorProject != null)}">
                <div class="alert alert-danger" role="alert">
                    <div>${errorProject} </div>
                    <div>${errorSelector}</div>
                    <div>${errorGraphic}</div>
                </div>
            </c:if>
        </div>
        <div class="col-sm-offset-3">

            <form method="POST"  role="form" action="/project/health-monitor-settings-post" class="selectors">


                <div class="row col-sm-12">
                    <h3 class="pageName col-sm-6"> Select parameters:</h3>
                </div>
                <table class="selectorsTable">
                    <tr><td><input type="checkbox" name="instshow"  id="instshow" class="custom-control-input" value="0" checked disabled>
                        <input type="checkbox" name="selectors[]"  id="checkinstance" class="custom-control-input" value="8" checked hidden>
                        <label for="instshow" class="custom-control custom-checkbox">Instance information</label>
                    </td>
                        <td><input type="checkbox" name="selectors[]"  id="lasterrors" class="custom-control-input" value="11">
                            <label for="lasterrors" class="custom-control custom-checkbox">Last DB errors</label></td>
                    </tr>
                    <tr><td><input type="checkbox" name="selectors[]"  id="dblocks" class="custom-control-input" value="16">
                        <label for="dblocks" class="custom-control custom-checkbox">DB locks</label></td>
                        <td><input type="checkbox" name="selectors[]"  id="sizetablespace" class="custom-control-input" value="9">
                        <label for="sizetablespace" class="custom-control custom-checkbox">Size for tablespace</label></td>
                    </tr>
                    <tr><td> <input type="checkbox" name="selectors[]"  id="sizetablelobCheck" class="custom-control-input" value="10">
                        <label for="sizetablelobCheck" class="custom-control custom-checkbox">Size for table-index-lob</label></td>
                        <td> <input type="text" class="form-control input-sm" id="tableindexlob" name="tableindexlob" placeholder="Default: 'user'"></td>
                    </tr>
                    <tr><td>
                        <input type="checkbox" name="selectors[]"  id="activesessioncheck" class="custom-control-input" value="12">
                        <label for="activesessioncheck" class="custom-control custom-checkbox">Active sessions</label>
                    </td><td>
                        <input type="text" class="form-control input-sm selectorsPar" id="activesession" name="activesession" placeholder="Top(default: 10)">
                    </td></tr>
                    <tr><td>
                        <input type="checkbox" name="selectors[]"  id="activequeriescheck" class="custom-control-input" value="13">
                        <label for="activequeriescheck" class="custom-control custom-checkbox">Active queries</label>
                    </td><td>
                        <input type="text" class="form-control input-sm selectorsPar" id="activequeries" name="activequeries" placeholder="Top(default: 10)">
                    </td></tr>
                    <tr><td>
                        <input type="checkbox" name="selectors[]"  id="queriesrescheck" class="custom-control-input" value="14">
                        <label for="queriesrescheck" class="custom-control custom-checkbox">Queries results</label>
                    </td><td>
                        <input type="text" class="form-control input-sm selectorsPar" id="queriesres" name="queriesres" placeholder="Top(default: 10)">
                    </td></tr>
                    <tr><td>
                        <input type="checkbox" name="selectors[]"  id="sqlmonitorcheck" class="custom-control-input" value="15">
                        <label for="sqlmonitorcheck" class="custom-control custom-checkbox">SQL queries monitor</label>
                    </td><td>
                        <input type="text" class="form-control input-sm selectorsPar" id="sqlmonitor" name="sqlmonitor" placeholder="Top(default: 10)">
                    </td></tr>
                    <tr><td>
                        <input type="checkbox" name="selectors[]"  id="activejobscheck" class="custom-control-input" value="17">
                        <label for="activejobscheck" class="custom-control custom-checkbox">Active jobs</label>
                    </td><td>
                        <input type="text" class="form-control input-sm selectorsPar" id="activejobs" name="activejobs" placeholder="Hour count(default: 24)">
                    </td></tr>
                    <tr><td>
                        <input type="checkbox" name="selectors[]"  id="graphcheck" class="custom-control-input" value="0">
                        <label for="graphcheck" class="custom-control custom-checkbox">Graph</label>
                    </td><td>
                        <input type="text" class="form-control input-sm selectorsPar" id="graph" name="graph" placeholder="Hour count(default: 24)">
                    </td></tr>
                </table>
                <div class="pull-right">
                    <!-- <button type="submit" class="btn btn-primary">Create</button>-->
                    <button class="btn btn-sm btn-primary" type="submit" id="next">
                        <b>Finish</b>&nbsp;<i class="fa fa-arrow-right" aria-hidden="true"></i>
                    </button>
                </div>
                <div class="pull-right">
                    <button class="btn btn-link" type="button" id="back">
                        <b>Back</b>
                    </button>
                </div>
            </form>
                <%--<form method="POST"  role="form" action="/project/health-monitor-settings-post" class="selectors">

                    <div class="row col-sm-12">
                        <c:if test = "${(errorSelector != null) || (errorGraphic != null) || (errorProject != null)}">
                            <div class="alert alert-danger col-sm-6" role="alert">
                                <div>${errorProject} </div>
                                <div>${errorSelector}</div>
                                <div>${errorGraphic}</div>
                            </div>
                        </c:if>
                    </div>
                    <div class="row col-sm-12">
                        <div class="row col-sm-12">
                            <h3 class="pageName col-sm-6"> Select parameters:</h3>
                        </div>
                        <div class="form-group row col-sm-4">
                            <input type="checkbox" name="instshow"  id="instshow" class="custom-control-input" value="0" checked disabled>
                            <input type="checkbox" name="selectors[]"  id="checkinstance" class="custom-control-input" value="8" checked hidden>
                            <label for="instshow" class="custom-control custom-checkbox">Instance information</label>
                        </div>

                        <div class="form-group row col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="lasterrors" class="custom-control-input" value="11">
                            <label for="lasterrors" class="custom-control custom-checkbox">Last DB errors</label>
                        </div>
                    </div>
                    <div class="row col-sm-12">
                        <div class="form-group row col-sm-4">
                            <input type="checkbox" name="selectors[]"  id="dblocks" class="custom-control-input" value="16">
                            <label for="dblocks" class="custom-control custom-checkbox">DB locks</label>
                        </div>
                        <div class="form-group row col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="sizetablespace" class="custom-control-input" value="9">
                            <label for="sizetablespace" class="custom-control custom-checkbox">Size for tablespace</label>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="sizetablelobCheck" class="custom-control-input" value="10">
                            <label for="sizetablelobCheck" class="custom-control custom-checkbox">Size for table-index-lob</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control input-sm" id="tableindexlob" name="tableindexlob" placeholder="Default: 'user'">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="activesessioncheck" class="custom-control-input" value="12">
                            <label for="activesessioncheck" class="custom-control custom-checkbox">Active sessions</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control input-sm selectorsPar" id="activesession" name="activesession" placeholder="Top(default: 10)">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="activequeriescheck" class="custom-control-input" value="13">
                            <label for="activequeriescheck" class="custom-control custom-checkbox">Active queries</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control input-sm selectorsPar" id="activequeries" name="activequeries" placeholder="Top(default: 10)">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="queriesrescheck" class="custom-control-input" value="14">
                            <label for="queriesrescheck" class="custom-control custom-checkbox">Queries results</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control input-sm selectorsPar" id="queriesres" name="queriesres" placeholder="Top(default: 10)">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="sqlmonitorcheck" class="custom-control-input" value="15">
                            <label for="sqlmonitorcheck" class="custom-control custom-checkbox">SQL queries monitor</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control input-sm selectorsPar" id="sqlmonitor" name="sqlmonitor" placeholder="Top(default: 10)">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="activejobscheck" class="custom-control-input" value="17">
                            <label for="activejobscheck" class="custom-control custom-checkbox">Active jobs</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control input-sm selectorsPar" id="activejobs" name="activejobs" placeholder="Hour count(default: 24)">
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-sm-3">
                            <input type="checkbox" name="selectors[]"  id="graphcheck" class="custom-control-input" value="0">
                            <label for="graphcheck" class="custom-control custom-checkbox">Graph</label>
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control input-sm selectorsPar" id="graph" name="graph" placeholder="Hour count(default: 24)">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="pull-right">
                           <!-- <button type="submit" class="btn btn-primary">Create</button>-->
                            <button class="btn btn-sm btn-primary" type="submit" id="next">
                                <b>Finish</b>&nbsp;<i class="fa fa-arrow-right" aria-hidden="true"></i>
                        </button>
                        </div>
                        <div class="pull-right">
                            <button class="btn btn-link" type="button" id="back">
                                <b>Back</b>
                            </button>
                        </div>
                    </div>

                </form>--%>
        </div>
    </div>

<script>
    $("#back").click(function () {
        window.location.assign("/project/health-monitor-setup");
    });
</script>

<jsp:include page="footer.jsp"/>
</body>
</html>
