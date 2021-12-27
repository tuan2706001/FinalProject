var btnAdd = $('#btnAddNews');
var close = $('#closeModal');
var closeBTN = $('#closeBTN');
var modal = $('#modal');
var closeBtnEdit = $('#closeModalEdit');
var closeEdit = $('.closeModalEdit');
btnAdd.click(function (){
    modal.addClass('block');
    modal.removeClass('hidden');
});
close.click(function (){
    modal.addClass('hidden');
    modal.removeClass('block');
});

closeBTN.click(function (e){
    modal.addClass('hidden');
    modal.removeClass('block');
});