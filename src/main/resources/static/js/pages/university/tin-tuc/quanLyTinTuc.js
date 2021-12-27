var btnAdd = $('#btnAddNews');
var close = $('#closeModal');
var closeBTN = $('#closeBTN');
var modal = $('#modal');
var modalEdit = $('#modalEdit');
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

    $(window).click(function (e){
        if($(e.target).is(modal)){
            modal.addClass('hidden');
            modal.removeClass('block');
            modalEdit.addClass('hidden');
            modalEdit.removeClass('block');
        }
    });
    $('.btnEdit').click(function (){
        var id = $(this).data('id');
        var url = "/website-truong/tin-tuc/"+id+"/edit";
        $.ajax({
           url: url,
            method: "GET",
           success: function (n){
               $('#modalEdit').html("");
               $('#modalEdit').removeClass('hidden');
               $('#modalEdit').addClass('block');
               $('#modalEdit').html(n);
           }
        });
    });