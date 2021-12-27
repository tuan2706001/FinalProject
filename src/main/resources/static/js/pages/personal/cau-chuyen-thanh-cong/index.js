var modal = $('#modal');
var buttonAdd = $('#btnAdd');
var close = $('#closeModal');
var closeBTN = $('#closeBTN');
var modalEdit = $('#modalEdit');
buttonAdd.click(function(){
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
$(window).click(function (e){
    if($(e.target).is(modal)){
        modal.addClass('hidden');
        modal.removeClass('block');
        modalEdit.addClass('hidden');
        modalEdit.removeClass('block');
    }
});