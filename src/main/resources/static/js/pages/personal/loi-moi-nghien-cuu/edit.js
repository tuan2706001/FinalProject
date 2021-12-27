var modalEdit = $('#modalEdit');
var closeBtnEdit = $('#closeModalEdit');
var closeEdit = $('.closeModalEdit');

closeEdit.click(function () {
    modalEdit.addClass('hidden');
    modalEdit.removeClass('block');
});
closeBtnEdit.click(function (e) {
    modalEdit.addClass('hidden');
    modalEdit.removeClass('block');
});
$(window).click(function (f) {
    if ($(f.target).is(modalEdit)) {
        modalEdit.addClass('hidden');
        modalEdit.removeClass('block');
    }
});

$(document).ready(function () {
    $('.menu1').click(function () {
        $('.form1').removeClass('hidden')
        $('.form2').addClass('hidden')
        $('.menu1').addClass('activeMenu text-blue-700')
        $('.menu2').removeClass('activeMenu text-blue-700')
    });

    $('.menu2').click(function () {
        $('.form2').removeClass('hidden')
        $('.form1').addClass('hidden')
        $('.menu2').addClass('activeMenu text-blue-700')
        $('.menu1').removeClass('activeMenu text-blue-700')
    });
});

