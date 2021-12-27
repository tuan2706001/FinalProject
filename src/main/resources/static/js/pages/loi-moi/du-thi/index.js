$(document).ready(function () {
    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var today = now.getFullYear() + "-" + (month) + "-" + (day);
    $('#thoi_gian_bat_dau').val(today);
});

// set id appeart not null
var validate_ten_loi_moi=document.getElementById('validate_ten_loi_moi');
validate_ten_loi_moi.classList.add("hidden");
var validate_noi_dung=document.getElementById('validate_noi_dung');
validate_noi_dung.classList.add("hidden");
var validate_thoi_gian_bat_dau_thi=document.getElementById('validate_thoi_gian_bat_dau_thi');
validate_thoi_gian_bat_dau_thi.classList.add("hidden");
var validate_thoi_gian_ket_thuc_thi=document.getElementById('validate_thoi_gian_ket_thuc_thi');
validate_thoi_gian_ket_thuc_thi.classList.add("hidden");
var validate_nop_bai_thi_tai=document.getElementById('validate_nop_bai_thi_tai');
validate_nop_bai_thi_tai.classList.add("hidden");
var validate_giai_thuong=document.getElementById('validate_giai_thuong');
validate_giai_thuong.classList.add("hidden");

function validate() {
    // get content CKEDITOR
    let noi_dung = CKEDITOR.instances['noi_dung'].getData();
    let giai_thuong = CKEDITOR.instances['giai_thuong'].getData();

    let ten_loi_moi = document.getElementById("ten_loi_moi");
    let thoi_gian_bat_dau_thi = document.getElementById('thoi_gian_bat_dau_thi');
    let thoi_gian_ket_thuc_thi = document.getElementById('thoi_gian_ket_thu_thi');
    let nop_bai_thi_tai = document.getElementById('nop_bai_thi_tai');

    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var today = now.getFullYear() + "-" + (month) + "-" + (day);

    if(ten_loi_moi.value != ''){
        validate_ten_loi_moi.classList.add("hidden");
        validate_ten_loi_moi.classList.remove("block");
    }
    if (noi_dung != ''){
        validate_noi_dung.classList.add("hidden");
        validate_noi_dung.classList.remove("block");
    }
    if (giai_thuong != ''){
        validate_chi_tiet.classList.add("hidden");
        validate_chi_tiet.classList.remove("block");
    }
    if (thoi_gian_bat_dau_thi.value != ''){
        validate_thoi_gian_bat_dau_thi.classList.add("hidden");
        validate_thoi_gian_bat_dau_thi.classList.remove("block");
    }
    if (thoi_gian_ket_thuc_thi.value != ''){
        validate_thoi_gian_ket_thuc_thi.classList.add("hidden");
        validate_thoi_gian_ket_thuc_thi.classList.remove("block");
    }
    if(nop_bai_thi_tai.value != ''){
        validate_nop_bai_thi_tai.classList.add("hidden");
        validate_nop_bai_thi_tai.classList.remove("block");
    }

    if ($('#thoi_gian_bat_dau').val() > $('#thoi_gian_ket_thuc').val() && $('#thoi_gian_ket_thuc').val() < today) {
        appearForm1();
        alert("Thời gian kết thúc phải sau thời gian bắt đầu");
        return false;
    }
    if (ten_loi_moi.value.trim() == "" || thoi_gian_bat_dau_thi.value.trim() == '' || thoi_gian_ket_thuc_thi.value.trim() == '' || noi_dung == '' || giai_thuong == '' || nop_bai_thi_tai.value.trim() == '' ) {

        if(thoi_gian_bat_dau_thi.value.trim() == ''){
            validate_thoi_gian_bat_dau_thi.classList.remove("hidden")
            validate_thoi_gian_bat_dau_thi.classList.add("block")
        }
        if(thoi_gian_ket_thuc_thi.value.trim() == ''){
            validate_thoi_gian_ket_thuc_thi.classList.remove("hidden")
            validate_thoi_gian_ket_thuc_thi.classList.add("block")
        }
        if(noi_dung == ''){
            validate_noi_dung.classList.remove("hidden")
            validate_noi_dung.classList.add("block")
        }
        if(giai_thuong == ''){
            validate_giai_thuong.classList.remove("hidden")
            validate_giai_thuong.classList.add("block")
        }
        if (nop_bai_thi_tai.value.trim() == ''){
            validate_nop_bai_thi_tai.classList.remove("hidden")
            validate_nop_bai_thi_tai.classList.add("block")
        }
        if (ten_loi_moi.value.trim() == ''){
            validate_ten_loi_moi.classList.remove("hidden")
            validate_ten_loi_moi.classList.add("block")
        }
        return false;
    }
    return true;
}