document.getElementById("male").setAttribute("checked","checked");
$('#importExcel').on('change',function (){
    $('form#formImport').submit();
})
$('#import').click(function (){
    $('#importExcel').click();
})