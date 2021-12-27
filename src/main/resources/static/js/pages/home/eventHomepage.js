$(document).ready(function (){
    $(".eventInprogress").show();
    $(".eventExpried").hide();
});
$(".expried").click(function (){
    $(".inprogress").removeClass("text-blue-500");
    $(".expried").addClass("text-blue-500");
    $(".eventInprogress").hide();
    $(".eventExpried").show();
});
$(".inprogress").click(function (){
    $(".inprogress").addClass("text-blue-500");
    $(".expried").removeClass("text-blue-500");
    $(".eventInprogress").show();
    $(".eventExpried").hide();
});