$('#myBtn').click(function (){
    $('#modalCreate').css('display','block')
    $('body,html').css('overflow','hidden');
})
$('.edit').click(function (){
    $('#modalEdit').css('display','block')
    $('body,html').css('overflow','hidden');
})

$('.closeModal').click(function (){
    $('#modalCreate').css('display','none')
    $('#modalEdit').css('display','none')
    $('#modalShow').css('display','none')
    $(".modalViewSubject").hide()
    $(".modalViewStudent").hide()
    $(".modalViewMess").hide()
    $('body,html').css('overflow','auto');
})

$('.show').click(function (){
    $('#modalShow').css('display','block')

    $('body,html').css('overflow','hidden');
})

$(window).click(function (e){
    if ($(e.target).is($('#modalCreate'))){
        $('#modalCreate').css('display','none')
        $('body,html').css('overflow','auto');
    }
    if ($(e.target).is($('#modalEdit'))){
        $('#modalEdit').css('display','none')
        $('body,html').css('overflow','auto');
    }
    if ($(e.target).is($('#modalShow'))){
        $('#modalShow').css('display','none')
        $('body,html').css('overflow','auto');
    }
})

