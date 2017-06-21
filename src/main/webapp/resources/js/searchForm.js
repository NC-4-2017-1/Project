$(document).ready(function () {
    $( ".searchUserForm" ).validate( {
        rules: {
            SearchUser: {
                required: false,
                maxlength: 30
            }
        },
        messages: {
            SearchUser: {
                maxlength: "Please enter not more than 30 characters"
            }
        },
        tooltip_options: {
            SearchUser: {placement:'top'}
        },
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

    $( ".searchProjectForm" ).validate( {
        rules: {
            SearchProject: {
                required: false,
                maxlength: 150
            }
        },
        messages: {
            SearchProject: {
                maxlength: "Please enter not more than 150 characters"
            }
        },
        tooltip_options: {
            SearchProject: {placement:'top'}
        },
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

    $( ".searchSharedProjectForm" ).validate( {
        rules: {
            SearchShareProject: {
                required: false,
                maxlength: 150
            }
        },
        messages: {
            SearchShareProject: {
                maxlength: "Please enter not more than 150 characters"
            }
        },
        tooltip_options: {
            SearchShareProject: {placement:'top'}
        },
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

