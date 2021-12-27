// $('#myModal').css({"display":"block"});
if (getCookie('notifyKhoa')=='true'){
    $('#notify').removeClass('hidden');
    $('#notify').addClass('block');
    setCookie('notifyKhoa',false,0)
}
let listTeacher= new Array();
const url="/quan-tri-nha-truong/tien-ich/danh-muc/khoa/tao";
$('#submit').click(function (){
    $('#listTeacher').each(function (index,item){
        //tìm tất cả input trong table có type=checkbox
        $(this).find('input[type=checkbox]').each(function () {
            // kiểm tra input type có chọn k
            var sThisVal = (this.checked ? $(this).val() : "");
            if (sThisVal != ""){
                listTeacher.push(Number(sThisVal));
            }
        });
    });
    let data={
        name:$('#khoa').val(),
        listIdGiaoVien:listTeacher
    }
    $.ajax({
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url:url,
        method: 'POST',
        data:JSON.stringify(data),
        success:function (){
            setCookie('notifyKhoa',true,1)
            listTeacher=[];
            location.reload();
        },
        error: function (e){
            listTeacher=[];
            console.log(e)
        }
    })
})

$(document).ready(function() {
    $('#myInput').on('keyup', function(event) {
        event.preventDefault();
        /* Act on the event */
        var tukhoa = $(this).val().toLowerCase();
        $('#myTable tr').filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(tukhoa)>-1);
        });
    });
});

$("#btnSearch").click(function (){
    $.ajax({
        url:"",
        method: "GET",
        data: {
            search: $('#searchGV').val()
        },
        success: function (data){
            var _html="";
            for (let i=0; i<data.length; i++){
                _html+="<tr>";
                _html+= "<td>";
                _html+=     (i+1)
                _html+= "</td>";
                _html+= "<td>";
                _html+=     "<input type=\"checkbox\" name=\"idGiaoVien\">"
                _html+= "</td>";
                _html+= "<td>";
                _html+=     data.name
                _html+= "</td>";
                _html+="</tr>";
            }
            $('#myTable').html(_html);
        }
    })
})