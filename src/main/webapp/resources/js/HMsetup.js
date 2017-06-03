$(document).ready(function () {
    $("#submit").click(function () {
        var data = isData();
        if (data == false) {
            return false;
        }
        $.ajax({
            url: "/project/health-monitor-setup-test-conn",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            error: function (responce) {
                if (responce.responseText == "successful") {
                    $(".alert-success").text("Connect successful");
                    $(".alert-danger").addClass('hide');
                    $(".alert-success").removeClass('hide');
                }
                else {
                    $(".alert-danger").text(responce.responseText);
                    $(".alert-success").addClass('hide');
                    $(".alert-danger").removeClass('hide');
                }
            }
        });
    });

    $("#next").click(function () {
        var data = isData();
        if (data == false) {
            return false;
        }
        $.ajax({
            url: "/project/health-monitor-setup-test-conn",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            error: function (responce) {
                if (responce.responseText == "successful") {
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
                                $(".alert-danger").text(responce.responseText);
                                $(".alert-success").addClass('hide');
                                $(".alert-danger").removeClass('hide');
                            }
                        }
                    });
                }
                else {
                    $(".alert-danger").text(responce.responseText);
                    $(".alert-success").addClass('hide');
                    $(".alert-danger").removeClass('hide');
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
    };
});
