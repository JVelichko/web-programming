$(document).ready(function () {
    $( "#target" ).load( "log.out" );
     refreshText();

});

 function refreshText(){
        $("#target").load("log.out", function(){
           setTimeout(refreshText, 2000);
        });
    }