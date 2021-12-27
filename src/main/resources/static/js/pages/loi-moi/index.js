
$(".call_type:first" ).removeClass('closeCallTypeMenu');
$(".call_type:first" ).addClass('text-blue-700 activeMenu');
$( "#CALL_TYPE_8 ").show();

$(".call_type" ).click(function(obj) {
    var call_type_content_id = 'CALL_TYPE_' +  $(this).attr("class").split(' ')[0];
    console.log($(this).attr("class"));
    $( '.call_type' ).removeClass('text-blue-700 activeMenu');
    $( '.call_type' ).addClass('closeCallTypeMenu');

    $(this).removeClass('closeCallTypeMenu');
    $(this).addClass('text-blue-700 activeMenu');

    $( "#"+ call_type_content_id ).prevAll().hide();
    $( "#"+ call_type_content_id ).nextAll().hide();
    $( "#"+ call_type_content_id ).show();
});