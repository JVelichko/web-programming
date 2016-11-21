$(document).ready(function () {
    $( "#target" ).load( "log.txt" );
     refreshText();

});

 function refreshText(){
        $("#target").load("log.txt", function(){
           setTimeout(refreshText, 5000);
        });
    }