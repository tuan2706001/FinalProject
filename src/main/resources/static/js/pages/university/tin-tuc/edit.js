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
});