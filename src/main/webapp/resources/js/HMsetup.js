$(document).ready(function () {
    $('#next').prop('disabled', 'disabled');
    $('#submit').prop('disabled', 'disabled');

    $("#submit").on('click', function() {
        $("#connform").valid();
    });

    $("#next").on('click', function() {
        $("#connform").valid();
    });

    checkField('#serverName');
    checkField('#port');
    checkField('#sid');
    checkField('#username');

    $('#password').on('keyup blur', function () {
        if ($('#connform').valid()) {
            $('#submit').prop('disabled', false);
            $('#next').prop('disabled', false);
        } else {
            $('#next').prop('disabled', 'disabled');
            $('#submit').prop('disabled', 'disabled');
        }
    });

    function checkField(em) {
        $(em).on('keyup blur', function () {
            if ($(em).val()) {
                $(em).valid();
            }
        });
    }

    $("#submit").click(function () {
        $('#submit').button('loading');
        var data = isData();
       if (data == false) {
           /* $("#error_conn").html("Connection fields can not be empty!");
            $("#right_conn").addClass('hide');
            $("#error_conn").removeClass('hide');*/
           $("#connform").valid();
        }  else {
            $.ajax({
                url: "/project/health-monitor-setup-test-conn",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(data),
                error: function (responce) {
                    $('#submit').button('reset');
                    if (responce.responseText == "successful") {
                       $("#right_conn").html("Connect successful.");
                        $("#error_conn").addClass('hide');
                        $("#right_conn").removeClass('hide');
                    } else {
                        $("#error_conn").html(responce.responseText);
                        $("#right_conn").addClass('hide');
                        $("#error_conn").removeClass('hide');
                    }
                }
            });
        }
    });

    $("#next").click(function () {
        $('#next').button('loading');
        var data = isData();
        if (data == false) {
            /*$("#error_conn").html("Connection fields can not be empty!");
            $("#right_conn").addClass('hide');
            $("#error_conn").removeClass('hide');*/
            $("#connform").valid();
        } else {
            $.ajax({
                    url: "/project/health-monitor-setup-test-conn",
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    error: function (responce) {
                        $('#next').button('reset');
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
                                        $("#error_conn").html(responce.responseText);
                                        $("#right_conn").addClass('hide');
                                        $("#error_conn").removeClass('hide');
                                    }
                                }
                            });
                        }
                        else {
                            $("#error_conn").html(responce.responseText);
                            $("#right_conn").addClass('hide');
                            $("#error_conn").removeClass('hide');
                        }
                    }
                });
            }
    });

    function isData() {
        var serverName = $("#serverName").val();
        var port = $("#port").val();
        var sid = $("#sid").val();
        var username = $("#username").val();
        var password = $("#password").val();
        if (!serverName || !port || !sid || !username || !password) {
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

    $( "#connform" ).validate( {
        rules: {
            serverName: {
                required: true
            },
            port: {
                required: true,
                number: true,
                min: 0,
                max: 65000
            },
            sid: {
                required: true,
                minlength: 2
            },
            password: {
                required: true,
                minlength: 5
            },
            username: {
                required: true
            }
        },
        messages: {
            serverName: {
                required: "Please enter server name"
            },
            port: {
                required: "Please enter port",
                number: "Enter number",
                min: "Port value must be between 0 and 65000",
                max: "Port value must be between 0 and 65000"
            },
            sid: {
                required: "Please enter SID",
                minlength: "SID must consist of at least 2 characters"
            },
            password: {
                required: "Please provide a password",
                minlength: "Password must be at least 5 characters long"
            },
            username: {
                required: "Please enter user name"
            }
        },
        tooltip_options: {
            serverName: {placement:'right'},
            port: {placement:'right'},
            sid: {placement:'right'},
            password: {placement:'right'},
            username: {placement:'right'}
        },
         /*errorElement: "em",*/
         errorPlacement: function ( error, element ) {
         error.addClass( "help-block alert-danger" );
         error.insertAfter( element );
         },
         highlight: function ( element, errorClass, validClass ) {
         $( element ).parents( ".col-sm-3" ).addClass( "has-error" ).removeClass( "has-success" );
         },
         unhighlight: function (element, errorClass, validClass) {
         $( element ).parents( ".col-sm-3" ).addClass( "has-success" ).removeClass( "has-error" );
         }
    } );
});
