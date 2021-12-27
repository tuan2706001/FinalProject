
$('.btnTimeRange').click(function (){
    $('#timeRange').toggleClass("hidden")
})

$(window).click(function (e) {
    if ($(e.target).is($('#timeRange')) || $(e.target).is($('.btnTimeRange')) || $(e.target).is($('.timeRange'))) {
    } else {
        $('#timeRange').addClass("hidden")
    }
});

function checkDate(){
    var start = $('#start').val();
    var end = $('#end').val();

    if (start === '' && end !== ''){
        $('.err').html('Ngày bắt đầu chưa điền');
        return false;
    }
    if (start !== '' && end === ''){
        $('.err').html('Ngày kết thúc chưa điền');
        return false;
    }
    return true;
}