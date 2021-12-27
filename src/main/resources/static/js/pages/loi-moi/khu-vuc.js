$(document).ready(function (){
    $('#area').change(function (){
        var khuVuc = $('#area').children("option:selected").html()
        $('#textArea').html(khuVuc);
    })
})