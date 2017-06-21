<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Project Creation</title>
    <jsp:include page="headFragment.jsp"/>
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery-validate.bootstrap-tooltip.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>

    <div class="setup-error">
        <div class="alert alert-danger  hide text-center" id = "alert_mess"></div>
    </div>

        <h3 class="pageName"> Please select project type</h3>

        <form  id="connform" data-toggle="validator" class="form-horizontal" role="form">
            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3">Type:</label>
                <div class="col-sm-3">
                    <select class="form-control input-sm" id="type" name="type">
                        <c:forEach items="${projectTypes}" var="entry">
                            <option value="${entry.key}">${entry.value}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="name">Name:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control conn-field input-sm" id="name" name="name" placeholder="name" required>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-sm-2 col-sm-offset-3" for="description">Description:</label>
                <div class="col-sm-3 ">
                    <textarea  class="form-control conn-field input-sm" rows="5" id="description"  name="description" placeholder="description" required></textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="pull-right">
                    <button class="btn btn-sm btn-primary" type="button" id="next">
                        <b>Next</b>&nbsp;<i class="fa fa-arrow-right" aria-hidden="true"></i>
                    </button>
                </div>
            <div class="pull-right">
                <button class="btn btn-link" type="button" id="back">
                    <b>Back</b>
                </button>
            </div>
        </div>
        </form>

<script>

    $('#next').prop('disabled', 'disabled');
    $("#next").on('click', function() {
        $("#connform").valid();
    });

    checkField('#name');
    checkField('#description');

    $('#name').on('keyup blur', function () {
      if ($('#connform').valid()) {
          $('#next').prop('disabled', false);
      } else {
          $('#next').prop('disabled', 'disabled');
      }
  });

    $('#description').on('keyup blur', function () {
        if ($('#connform').valid()) {
            $('#next').prop('disabled', false);
        } else {
            $('#next').prop('disabled', 'disabled');
        }
    });

    function checkField(em) {
        $(em).on('keyup blur', function () {
            if ($(em).val()) {
                $(em).valid();
            }
        });
    }

    function isData() {
        var type = $("#type").val();
        var name = $("#name").val();
        var description = $("#description").val();

        if (!name || !description) {
            return false;
        }
        var data = {
            type: type,
            name: name,
            description: description
        };
        return data;
    };

    $("#next").click(function () {
        var data = isData();
        if (data == false) {
            $("#connform").valid();
        } else {
         /*   $("#alert_mess").addClass("hide");*/
            $.ajax({
            url: "/project/create",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            error: function (responce) {
                if (responce.responseText == "visualization-setup") {
                    window.location.assign("/project/visualization-setup");
                } else  if (responce.responseText == "health-monitor-setup"){
                    window.location.assign("/project/health-monitor-setup");
                } else{
                    $("#alert_mess").removeClass("hide");
                    var errorMes =  $("#alert_mess");
                    if(responce.responseText == "emptyField"){
                        errorMes.text("Name or description field cannot be empty");
                    } else if (responce.responseText == "name"){
                        errorMes.text("Name cannot contain more than 150 characters");
                    } else if(responce.responseText == "description"){
                        errorMes.text("Description cannot contain more than 1000 characters");
                    }
                }
            }
        });
       }
    });

    $("#back").click(function () {
        window.location.assign("/user/dashboard-get");
    });

    $( "#connform" ).validate( {
        rules: {
            name: {
                required: true,
                maxlength: 150
            },
            description: {
                required: true,
                maxlength: 1000
            }
        },
        messages: {
            name: {
                required: "Please enter name",
                maxlength: "Name cannot contain more than 150 characters"
            },
            description: {
                required: "Please enter description",
                maxlength: "Description cannot contain more than 1000 characters"
            }
        },
        tooltip_options: {
            name: {placement:'right'},
            description: {placement:'right'}
        }
    } );

</script>

<jsp:include page="footer.jsp"/>

</body>
</html>