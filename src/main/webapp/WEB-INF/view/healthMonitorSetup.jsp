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

<div> HM project Connection</div>
<br>
<form>
    Server name:<br>
    <input type="text" id="serverName" name="serverName" value="">
    <br>
    <br>
    Port:<br>
    <input type="text" id="port" name="port" value="">
    <br>
    <br>
    SID:<br>
    <input type="text" id="sid" name="sid" value="">
    <br>
    <br>
    User Name:<br>
    <input type="text" id="username" name="username" value="">
    <br>
    <br>
    Password:<br>
    <input type="text" id="password" name="password" value="">
    <br>
    <br>
    <input type="button" id="submit" value="Test"><br>
    <br>
    <input type="button" id="next" value="Next"><br>
</form>

<script>
    $("#submit").click(function () {
        var data = isData();
        if (data == false) {
            return false
        }

        $.ajax({
            url: "/project/health-monitor-setup-test-conn",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            error: function (responce) {

                $("div")
                    .text(responce.responseText)
                    .css({'color': 'red', 'position': 'absolute', 'bottom': '0'});
            }
        });
    });

    $("#next").click(function () {
        var data = isData();
        if (data == false) {
            return false
        }

        $.ajax({
            url: "/project/health-monitor-setup-save",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            error: function (responce) {
                if (responce.responseText == "successful") {
                    window.location.assign("/project/health-monitor-settings");
                }
                else {
                    $("div")
                        .text(responce.responseText)
                        .css({'color': 'red', 'position': 'absolute', 'bottom': '0'});
                }
            }
        });
    });

    function isData() {
        var serverName = $("#serverName").val();
        var port = $("#port").val();
        var sid = $("#sid").val();
        var username = $("#username").val();
        var password = $("#password").val();

        if (!serverName || !port || !sid || !username || !password) {
            alert("Fill all the fields");
            return false;
        }
        var data = {
            serverName: serverName,
            port: port,
            sid: sid,
            username: username,
            password: password
        };
        return data;
    }
</script>
</body>
</html>
