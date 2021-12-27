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
var validate_hinh_thuc=document.getElementById('validate_hinh_thuc');
validate_hinh_thuc.classList.add("hidden");
var validate_dieu_kien=document.getElementById('validate_dieu_kien');
validate_dieu_kien.classList.add("hidden");
var validate_quyen_loi=document.getElementById('validate_quyen_loi');
validate_quyen_loi.classList.add("hidden");

function validate() {
    // get content CKEDITOR
    let noi_dung = CKEDITOR.instances['noi_dung'].getData();
    let hinh_thuc = CKEDITOR.instances['hinh_thuc'].getData();
    let dieu_kien = CKEDITOR.instances['dieu_kien'].getData();
    let quyen_loi = CKEDITOR.instances['quyen_loi'].getData();

    let ten_loi_moi = document.getElementById("ten_loi_moi");

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
    if (hinh_thuc != ''){
        validate_hinh_thuc.classList.add("hidden");
        validate_hinh_thuc.classList.remove("block");
    }
    if (dieu_kien != ''){
        validate_dieu_kien.classList.add("hidden");
        validate_dieu_kien.classList.remove("block");
    }
    if (quyen_loi != ''){
        validate_thoi_gian_ket_thuc_thi.classList.add("hidden");
        validate_thoi_gian_ket_thuc_thi.classList.remove("block");
    }

    if ($('#thoi_gian_bat_dau').val() > $('#thoi_gian_ket_thuc').val() && $('#thoi_gian_ket_thuc').val() < today) {
        appearForm1();
        alert("Thời gian kết thúc phải sau thời gian bắt đầu");
        return false;
    }
    if (ten_loi_moi.value.trim() == "" || noi_dung == '' || hinh_thuc == '' || dieu_kien == '' || quyen_loi == '' ) {

        if(ten_loi_moi.value.trim() == ''){
            validate_ten_loi_moi.classList.remove("hidden")
            validate_ten_loi_moi.classList.add("block")
        }
        if(noi_dung == ''){
            validate_noi_dung.classList.remove("hidden")
            validate_noi_dung.classList.add("block")
        }
        if(hinh_thuc == ''){
            validate_hinh_thuc.classList.remove("hidden")
            validate_hinh_thuc.classList.add("block")
        }
        if(dieu_kien == ''){
            validate_dieu_kien.classList.remove("hidden")
            validate_dieu_kien.classList.add("block")
        }
        if (quyen_loi == ''){
            validate_quyen_loi.classList.remove("hidden")
            validate_quyen_loi.classList.add("block")
        }
        return false;
    }
    return true;
}