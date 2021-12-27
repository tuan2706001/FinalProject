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
    $('body,html').css('overflow','auto');
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
})
})
