$(document).ready(function () {
    var modal = $('.modal');
    var btn = $('.btn');
    var span = $('.close');

    btn.click(function () {
        modal.show();
    });

    span.click(function () {
        modal.hide();
    });

    $(window).on('click', function (e) {
        if ($(e.target).is('.modal')) {
            modal.hide();
        }
    });
});

$('.icon').click(function (u) {
    var icon = $(this).html();
    $('.frame-chat').val(function (i,text){
        return text + icon;
    })
})

$(function() {
    $('textarea[max-rows]').each(function () {
        var textarea = $(this);
        var minRows = Number(textarea.attr('rows'));
        var maxRows = Number(textarea.attr('max-rows'));
        var textareaClone = $('<textarea/>', {
            rows: minRows,
            maxRows: maxRows,
            class: textarea.attr('class')
        }).css({
            position: 'absolute',
            left: -$(document).width() * 2
        }).insertAfter(textarea);
        var textareaCloneNode = textareaClone.get(0);
        textarea.on('input', function () {
            textareaClone.val(textarea.val());
            textareaClone.attr('rows', 1);
            var scrollHeight = textareaCloneNode.scrollHeight;
            for (var rows = minRows; rows < maxRows; rows++) {
                textareaClone.attr('rows', rows);

                if (textareaClone.height() >= scrollHeight) {
                    break;
                }
            }
            textarea.attr('rows', textareaClone.attr('rows'));
        }).trigger('input');
    });
});