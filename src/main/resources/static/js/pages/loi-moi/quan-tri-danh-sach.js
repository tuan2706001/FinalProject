var modalEdit = $('#modalEdit');
// nút bánh răng {
    $('.actionBtn').click(function () {
        var id = $(this).data('id');
        $('.actionBtn').each(function () {
            if ($(this).data('id') === id) {
                if ($('#' + id).hasClass('hidden')){
                    $('#' + id).removeClass('hidden');
                }
                else {
                    $('#' + id).addClass('hidden');
                }
            } else {
                $('#' + $(this).data('id')).addClass('hidden');
            }
        });
    });
// }

$('.btnTimeRange').click(function (){

    // var now = new Date();
    //
    // var day = ("0" + now.getDate()).slice(-2);
    // let monthStart;
    // let monthEnd;
    // var yearStart = now.getFullYear();
    // var yearEnd = now.getFullYear();
    // if (now.getMonth() + 1 >= 13) {
    //     monthStart = '01';
    //     yearStart = now.getFullYear() + 1;
    // } else {
    //     monthStart = ("0" + (now.getMonth() + 1)).slice(-2);
    // }
    // if (now.getMonth() + 2 >= 13){
    //     monthEnd = '01';
    //     yearEnd = now.getFullYear() + 1;
    // }
    // else {
    //     monthEnd = ("0" + (now.getMonth() + 2)).slice(-2);
    // }
    //
    // var today = (yearStart)+"-"+(monthStart)+"-"+(day) ;
    // var nextMoth = (yearEnd)+"-"+(monthEnd)+"-"+(day);
    //
    // $('#start').val(today);
    // $('#end').val(nextMoth);
    $('#timeRange').toggleClass("hidden")
})

// modal create {
    var myModal = document.getElementById("myModal");


    var btn = document.getElementById("myBtn");


    var span = document.getElementById("closeModalCreate");


    if (btn!=undefined){
        btn.onclick = function () {
            myModal.style.display = "block";
            $('body,html').css('overflow','hidden');
        }
    }

    if (span != undefined){
        span.onclick = function () {
            myModal.style.display = "none";
            $('body,html').css('overflow','auto');
        }
    }


    window.onclick = function (event) {
        if (event.target == myModal) {
            myModal.style.display = "none";
            $('body,html').css('overflow','auto');
        }
    }
// }

// modal edit {

    $('.closeModalEdit').click(function (){
        $('#modalEdit').addClass('hidden').removeClass('block');
        $('body,html').css('overflow','auto');
    });
// }

$('#deleteMultiple').click(function () {
    if ($(this).prop('checked') === true){
        $('.delete').prop('checked',true)
    }
    else {
        $('.delete').prop('checked',false)
    }
})

$(window).click(function (e) {
    if ($(e.target).is($('.actionBtn')) || $(e.target).is($('path')) || $(e.target).is($('svg'))) {
    } else {
        $('.action').addClass('hidden');
        $('#myModal').addClass('hidden');
    }
    if ($(e.target).is($('#timeRange')) || $(e.target).is($('.btnTimeRange')) || $(e.target).is($('.timeRange'))) {
    } else {
        $('#timeRange').addClass("hidden")
    }

    if($(e.target).is(modalEdit)){
        modalEdit.addClass('hidden');
        modalEdit.removeClass('block');
        $('body,html').css('overflow','auto');
    }
});

function choosePdf(){
    $( "#inputUploadPdf" ).click();
}

// submit form
$('#submit').click(function (event){
    // event.preventDefault();
    let files= new Array();
    const url = "/quan-tri-nha-truong/loi-moi/danh-sach";

    $('.files').each(function (index,item){
        $(this).find('input[type=file]').each(function (index,item) {
            var sThisVal =$(this)[0].files[0];
            if (sThisVal !== ""){
                // let dataFile = new FormData($(this).parent()[0])
                // files = new FormData($(this).parent()[0])
                // files.append('file[]',$(this).parent()[0]);
                console.log(sThisVal);
                files.push(sThisVal);
            }
        });
    })
    let form = $('#createCallMulti')[0];
    let data=new FormData(form);
    let content = CKEDITOR.instances['noi_dung'].getData();
    $.ajax({
        url:url,
        type: "POST",
        // dataType: "json",
        // contentType: "application/json; charset=utf-8",
        enctype: 'multipart/form-data',
        // data:{
        //     picture: $('#urlImage').val(),
        //     status: $('#status').val(),
        //     time_start: $('#time_start').val(),
        //     time_end: $('#time_end').val(),
        //     value: $('#gia-tri').val(),
        //     name: $('#ten_loi_moi').val(),
        //     content: content,
        //     dieu_khoan: $('#dieuKhoan').val(),
        //     files: JSON.stringify(files)
        // },
        data:data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 10000000,
        success: function (){

            files=[];
            // location.reload();
        },
        error: function (){
            files=[]
        }
    })
})

function selectFile(obj){
    obj.getElementsByClassName('fileOne')[0].click();
    // console.log(obj.getElementsByClassName('fileOne')[0]);
}


$('.callType').click(function () {
    var callType = $(this).val();

    if (callType === "15"){
        console.log(1)
        $('.value').html('Giá trị')
        $('.phunit').attr('placeholder','VND, USD,...')
        $('.unit').html('Mệnh giá')
    }
    if (callType === "20"){
        $('.value').html('Số lượng')
        $('.phunit').attr('placeholder','Người, sản phẩm...')
        $('.unit').html('Đơn vị')
    }
})

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

var modalDelete = $('#modalDelete');
var modalSendNotify = $('#modalSendNotify');
var myModalSendNotify = document.getElementById("myModalSendNotify");
$(window).click(function (f){
    if($(f.target).is(modalDelete)){
        modalDelete.addClass('hidden');
        modalDelete.removeClass('block');
    }
    if($(f.target).is(modalSendNotify)){
        modalSendNotify.addClass('hidden');
        modalSendNotify.removeClass('block');
    }
});
$('.btnDelete').click(function (){
    var id = $(this).data('id');
    var url = $(this).data('url')+id;
    $.ajax({
        url: url,
        method: "GET",
        success: function (n){
            modalDelete.html("");
            modalDelete.removeClass('hidden');
            modalDelete.addClass('block');
            modalDelete.html(n);
        }

    });
});

$('.btnSendNotify').click(function (){
    var id = $(this).data('id');
    var name = $(this).data('name');
    var url = $(this).data('url')+id+"/"+name;
    $.ajax({
        url: url,
        method: "POST",
        success: function (n){
            myModalSendNotify.style.display = "none";
        }
    });
});

$(document).keyup(function (e){
    if (e.which == 27){
        if (myModal != undefined){
            myModal.style.display = "none";
        }
        if (myModalEndCall != undefined){
            myModalEndCall.style.display = "none";
        }
        if (myModalSendNotify != undefined){
            myModalSendNotify.style.display = "none";
        }
        if (modalDelete.hasClass("block")){
            modalDelete.removeClass('block');
            modalDelete.addClass('hidden');
        }
        if (modalEdit.hasClass('block')){
            modalEdit.removeClass('block');
            modalEdit.addClass('hidden');
        }
    }
})

function chackedAll() {
    if ($('#checkAll').is(":checked")){
        $('.ckeck').prop("checked", true).addClass("count")
    }
    else {
        $('.ckeck').prop("checked", false).removeClass("count")
    }
}

$('input[type="checkbox"]').click(function(){
    if($(this).prop("checked") === true){
        $(this).addClass("count")
    }
    else if($(this).prop("checked") === false){
        $(this).removeClass("count")
    }
})

$('.delete').click(function () {
    if ($('.count').length > 0){
        $('#dataTable').submit()
    }
    else {
        alert("Hãy chọn lời mời muốn xóa")
    }
})