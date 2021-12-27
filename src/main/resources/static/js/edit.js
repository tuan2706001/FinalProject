var btnAdd = $('#btnAddNews');
var close = $('#closeModal');
var closeBTN = $('#closeBTN');
var modal = $('#modal');
var modalEdit = $('#modalEdit');
var closeBtnEdit = $('#closeModalEdit');
var closeEdit = $('.closeModalEdit');

closeEdit.click(function (){
    modalEdit.addClass('hidden');
    modalEdit.removeClass('block');
});
closeBtnEdit.click(function (e){
    modalEdit.addClass('hidden');
    modalEdit.removeClass('block');
});
$(window).click(function (f){
    if($(f.target).is(modalEdit)){
        modalEdit.addClass('hidden');
        modalEdit.removeClass('block');
    }
    if($(f.target).is(modal)){
        modal.addClass('hidden');
        modal.removeClass('block');
        modalEdit.addClass('hidden');
        modalEdit.removeClass('block');
    }
});
btnAdd.click(function (){
    modal.addClass('block');
    modal.removeClass('hidden');
});
close.click(function (){
    modal.addClass('hidden');
    modal.removeClass('block');
});

closeBTN.click(function (){
    modal.addClass('hidden');
    modal.removeClass('block');
});
$('.btnEdit').click(function (){
    var id = $(this).data('id');
    var url = $(this).data('url')+id;
    $.ajax({
        url: url,
        method: "GET",
        success: function (n){
            modalEdit.html("");
            modalEdit.removeClass('hidden');
            modalEdit.addClass('block');
            modalEdit.html(n);
        }

    });
});