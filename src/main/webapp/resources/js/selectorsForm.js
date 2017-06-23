$(document).ready(function () {
    $( ".selectors" ).validate( {
        rules: {
            activequeries: {number: true,   min: 1},
            activesession: {number: true,   min: 1},
            queriesres: {number: true,   min: 1},
            sqlmonitor: {number: true,   min: 1},
            activejobs: {number: true,   min: 1},
            graph: {number: true,   min: 1}
        },
        messages: {
            activequeries: {number: "Enter number",    min: "Value must be more than 0"},
            activesession: {number: "Enter number",    min: "Value must be more than 0"},
            queriesres: {number: "Enter number",    min: "Value must be more than 0"},
            sqlmonitor: {number: "Enter number",    min: "Value must be more than 0"},
            activejobs: {number: "Enter number",    min: "Value must be more than 0"},
            graph: {number: "Enter number",    min: "Value must be more than 0"}
        },
        tooltip_options: {
            activequeries: {placement:'right'},
            activesession: {placement:'right'},
            queriesres: {placement:'right'},
            sqlmonitor: {placement:'right'},
            activejobs: {placement:'right'},
            graph: {placement:'right'}
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

