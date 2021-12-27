$(document).ready(function () {
    var hidden = $('.hiddenDiv');
    var idmd = 0;


//scroll to top
    $(window).scroll(function () {
        if ($(window).scrollTop() > 300) {
            $('#goToTop').addClass('opacity-100 -translate-y-3 ');
        } else {
            $('#goToTop').removeClass('opacity-100 -translate-y-3 ');
        }
    });

    $('#goToTop').click(function (even) {
        even.preventDefault();
        $('html, body').animate({scrollTop: 0}, 300);
    });

    $('#hiddenModal').click(function () {
        $("#myModal").hide();
    })
    $('.actionBtn').click(function () {
        var id = $(this).data('id');
        $('.actionBtn').each(function () {
            if ($(this).data('id') === id) {
                $('#' + id).toggleClass('hidden');
            } else {
                $('#' + $(this).data('id')).addClass('hidden');
            }
        });

    });
});

function toastr() {
    var toastr_success = document.getElementById("toastr_success");
    var toastr_info = document.getElementById("toastr_info");
    var toastr_warning = document.getElementById("toastr_warning");
    var toastr_error = document.getElementById("toastr_error");
    if ($('#toastr_success').length > 0) {
        toastr_success.classList.add("hidden");
    }
    if ($('#toastr_info').length > 0) {
        toastr_info.classList.add("hidden");
    }
    if ($('#toastr_warning').length > 0) {
        toastr_warning.classList.add("hidden");
    }
    if ($('#toastr_error').length > 0) {
        toastr_error.classList.add("hidden");
    }
}
function closeModal() {
    var modalClose = document.getElementById("myModal");
    modalClose.style.display = null;
}

function diennhanh(id) {
    if (id === 1) {
        document.getElementById('usName').value = "enterprise";
        document.getElementById('pass').value = "admin";
    }
    if (id === 2) {
        document.getElementById('usName').value = "university";
        document.getElementById('pass').value = "admin";
    }
    if (id === 3) {
        document.getElementById('usName').value = "personal";
        document.getElementById('pass').value = "admin";
    }
}

var btnAdd = $('#btnAddNews');
var close = $('#closeModal');
var closeBTN = $('#closeBTN');
var modal = $('#modal');
var modalEdit = $('#modalEdit');
var closeBtnEdit = $('#closeModalEdit');
var closeEdit = $('.closeModalEdit');

$(window).click(function (e) {
    if ($(e.target).is(modal)) {
        modal.addClass('hidden');
        modal.removeClass('block');
        modalEdit.addClass('hidden');
        modalEdit.removeClass('block');
    }
    if ($(e.target).is($('.actionBtn')) || $(e.target).is($('path')) || $(e.target).is($('svg'))) {
    } else {
        $('.action').addClass('hidden');
        $('.actionBtn').removeClass('text-blue-700')
    }

});

// Hàm thiết lập Cookie
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

// Hàm lấy Cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

function eraseCookie(name) {
    document.cookie = name +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

$body = $("body");
$(document).on({
    ajaxStart: function() { $body.addClass("loading");    },
    ajaxStop: function() { $body.removeClass("loading"); }
});

