var modalEdit = $('#modalEdit');
var modalChangePass = $('#modalChangePass');
var modalCreate = $('#myModal')
var modalLockAccount = $('#modalLockAccount')

//action
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

//create
$('#myBtn').click(function () {
    modalCreate.addClass('block').removeClass('hidden')
    $('body,html').css('overflow','hidden');
})
$('.closeModalCreate').click(function () {
    modalCreate.addClass('hidden').removeClass('block')
    $('body,html').css('overflow','auto');
})

// edit
$('.btnEdit').click(function (){
    var id = $(this).data('id');
    var url = $(this).data('url')+id;
    $.ajax({
        url: url,
        method: "GET",
        success: function (n){
            $('body,html').css('overflow','hidden');
            modalEdit.html("");
            modalEdit.removeClass('hidden');
            modalEdit.addClass('block');
            modalEdit.html(n);
        }

    });
});

// change password
$('.changePass').click(function (){
    var id = $(this).data('id');
    var url = $(this).data('url')+id;
    $.ajax({
        url: url,
        method: "GET",
        success: function (n){
            $('body,html').css('overflow','hidden');
            modalChangePass.html("");
            modalChangePass.removeClass('hidden');
            modalChangePass.addClass('block');
            modalChangePass.html(n);
        },
    });
});

// lock
$('.lock').click(function (){
    var id = $(this).data('id');
    var url = $(this).data('url')+id;
    $.ajax({
        url: url,
        method: "GET",
        success: function (n){
            $('body,html').css('overflow','hidden');
            modalLockAccount.html("");
            modalLockAccount.removeClass('hidden');
            modalLockAccount.addClass('block');
            modalLockAccount.html(n);
        },
    });
})


function checkCreate(){
    var checkphone = false;
    var checkemail = false;
    var checkpass = false;
    var checkemptyPass = false;
    var checkemptyFullname = false;
    var checkemptyPoisition = false;
    var regExPhone = /\d{10}|(?:\d{3}-){2}\d{4}|\(\d{3}\)\d{3}-?\d{4}/i;
    var regExUserName = /^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$/i;

    // check phone number create
    if($('#phone').val() !== ''){
        if(regExPhone.test($('#phone').val()) === false){
            document.getElementById('phone').style.borderColor = '#fc403e';
            document.getElementById('errorPhoneNumberCreate').innerText = 'Cần nhập đúng số điện thoại!';
            checkphone = false;
        }else{
            document.getElementById('phone').style.borderColor = '#09c002';
            document.getElementById('errorPhoneNumberCreate').innerText = '';
            checkphone = true;
        }
    }else{
        document.getElementById('phone').style.borderColor = '#fc403e';
        document.getElementById('errorPhoneNumberCreate').innerText = 'Số điện thoại không được để trống!';
        checkphone = false;
    }

    // check repass create
    if($('#rePass').val() != ''){
        if ($('#pass').val() === $('#rePass').val()){
            document.getElementById('rePass').style.borderColor = '#09c002';
            document.getElementById('errorPassCreate').innerText = '';
            checkpass = true;
        }
        else{
            document.getElementById('rePass').style.borderColor = '#fc403e';
            document.getElementById('errorPassCreate').innerText = 'Mật khẩu không trùng khớp!';
            checkpass = false;
        }
    }else{
        document.getElementById('rePass').style.borderColor = '#fc403e';
        document.getElementById('errorPassCreate').innerText = 'Mật khẩu không được để trống!';
        checkpass = false;
    }

    //check email create
    if($('#username').val() != ''){
        if(regExUserName.test($('#username').val()) == false){
            document.getElementById('username').style.borderColor = '#fc403e';
            document.getElementById('errorUserName').innerText = 'Cần nhập đúng Tên tài khoản là email!';
            checkemail = false;
        }else{
            document.getElementById('username').style.borderColor = '#09c002';
            document.getElementById('errorUserName').innerText = '';
            checkemail = true;
        }
    }else{
        document.getElementById('username').style.borderColor = '#fc403e';
        document.getElementById('errorUserName').innerText = 'Tên tài khoản không được để trống!';
        checkemail = false;
    }

    //check empty
    if( $('#pass').val() == '' ){
        document.getElementById('pass').style.borderColor = '#fc403e';
        document.getElementById('errorPass').innerText = 'Mật khẩu không được để trống!';
        checkemptyPass = false;
    }else{
        document.getElementById('pass').style.borderColor = '#09c002';
        document.getElementById('errorPass').innerText = '';
        checkemptyPass = true;
    }
    if($('#fullName').val() == ''){

        document.getElementById('fullName').style.borderColor = '#fc403e';
        document.getElementById('errorFullName').innerText = 'Họ và tên không được để trống!';
        checkemptyFullname = false;
    }else{
        document.getElementById('fullName').style.borderColor = '#09c002';
        document.getElementById('errorFullName').innerText = '';
        checkemptyFullname = true;
    }
    if($('#position').val()  == 'null'){
        document.getElementById('position').style.borderColor = '#fc403e';
        document.getElementById('errorPosition').innerText = 'Hãy chọn chức vụ!';
        checkemptyPoisition = false;
    }else{
        document.getElementById('position').style.borderColor = '#09c002';
        document.getElementById('errorPosition').innerText = '';
        checkemptyPoisition = true;
    }

    if(checkpass === true && checkemptyFullname === true && checkemail === true && checkphone === true && checkemptyPoisition === true && checkemptyPass === true){
        return true;
    }else {
        return false;
    }

}


$(window).click(function (e) {
    if ($(e.target).is($('path')) || $(e.target).is($('svg'))) {}
    else {
        $('.action').addClass('hidden');
        $('body,html').css('overflow','auto');
    }

    if($(e.target).is(modalEdit)){
        modalEdit.addClass('hidden');
        modalEdit.removeClass('block');
        $('body,html').css('overflow','auto');
    }

    if ($(e.target).is(modalCreate)) {
        modalCreate.addClass('hidden').removeClass('block')
        $('body,html').css('overflow','auto');
    }

    if ($(e.target).is(modalChangePass)) {
        modalChangePass.addClass('hidden').removeClass('block')
        $('body,html').css('overflow','auto');
    }

    if ($(e.target).is(modalLockAccount)) {
        modalLockAccount.addClass('hidden').removeClass('block')
        $('body,html').css('overflow','auto');
    }
});