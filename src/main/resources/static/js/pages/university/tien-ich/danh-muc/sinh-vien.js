document.getElementById("male").setAttribute("checked","checked");
$('#khoa').change(function (){
    const url="/quan-tri-nha-truong/tien-ich/danh-muc/danh-sach-lop";
    $.ajax({
        type: "GET",
        url: url,
        data:{
            search: $('#khoa').val()
        },
        success:function (data){
            var _html = '';
            for(let i=0;i<data.length;i++){
                _html += "<option value='"+data[i].id+"'>"+data[i].name;
                _html += "</option>";
            }
            if(data.length<=0){
                _html += "<option value='' hidden></option>";
            }
            $('#class_edit').html(_html);
            $('#class').html(_html)
        }
    })
})
function check(){
    if ($('#khoa').val() == '' || $('#khoa').val().length<1){
        $('#validate_khoa').removeClass('hidden');
        $('#validate_khoa').addClass('block');
        return false;
    } else if($('#class').val() == '' || $('#class').val().length<1){
        $('#validate_class').removeClass('hidden');
        $('#validate_class').addClass('block');
        return false;
    }
    $('#validate_khoa').removeClass('block');
    $('#validate_khoa').addClass('hidden');
    return true;
}
$('#importExcel').on('change',function (){
    $('form#formImport').submit();
})
$('#import').click(function (){
    $('#importExcel').click();
})