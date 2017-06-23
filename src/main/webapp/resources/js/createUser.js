$(document).ready(function () {
    $( ".createUser" ).validate( {
        rules: {
            firstName: { required: true, maxlength: 20},
            lastName:  { required: true, maxlength: 20},
            email:     { required: true, email: true},
            password:  { required: true, minlength: 5, maxlength: 20}
        },
        messages: {
            firstName: { required: "Please enter user first name", maxlength: "User first name can`t be more than 20 characters"},
            lastName:  { required: "Please enter user last name", maxlength: "User last name can`t be more than 20 characters"},
            email:     { required: "Please enter user email", email: "Wrong email format"},
            password:  { required: "Please enter user password ",
                            minlength: "User password can`t be less than 5 characters",
                            maxlength: "User password can`t be more than 20 characters"
                        }
        },
        tooltip_options: {
            firstName: {placement:'right'},
            lastName: {placement:'right'},
            email: {placement:'right'},
            password: {placement:'right'}
        },
        /*errorElement: "em",*/
        errorPlacement: function ( error, element ) {
            error.addClass( "help-block alert-danger" );
            error.insertAfter( element );
        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parents( ".col-sm-9" ).addClass( "has-error" ).removeClass( "has-success" );
        },
        unhighlight: function (element, errorClass, validClass) {
            $( element ).parents( ".col-sm-9" ).addClass( "has-success" ).removeClass( "has-error" );
        }
    } );

});