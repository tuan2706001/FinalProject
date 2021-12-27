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
var validate_quyen_loi=document.getElementById('validate_quyen_loi');
validate_quyen_loi.classList.add("hidden");
var validate_chi_tiet=document.getElementById('validate_chi_tiet');
validate_chi_tiet.classList.add("hidden");
var validate_dieu_khoan=document.getElementById('validate_dieu_khoan');
validate_dieu_khoan.classList.add("hidden");
var validate_tieu_chi=document.getElementById('validate_tieu_chi');
validate_tieu_chi.classList.add("hidden");

function validate() {
    // get content CKEDITOR
    let noi_dung = CKEDITOR.instances['noi_dung'].getData();
    let quyen_loi = CKEDITOR.instances['quyen_loi'].getData();
    let chi_tiet = CKEDITOR.instances['chi_tiet'].getData();
    let dieu_khoan = CKEDITOR.instances['dieu_khoan'].getData();
    let tieu_chi = CKEDITOR.instances['tieu_chi'].getData();

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
    if (quyen_loi != ''){
        validate_quyen_loi.classList.add("hidden");
        validate_quyen_loi.classList.remove("block");
    }
    if (chi_tiet != ''){
        validate_chi_tiet.classList.add("hidden");
        validate_chi_tiet.classList.remove("block");
    }
    if (dieu_khoan != ''){
        validate_dieu_khoan.classList.add("hidden");
        validate_dieu_khoan.classList.remove("block");
    }
    if (tieu_chi != ''){
        validate_tieu_chi.classList.add("hidden");
        validate_tieu_chi.classList.remove("block");
    }

    if ($('#thoi_gian_bat_dau').val() > $('#thoi_gian_ket_thuc').val() && $('#thoi_gian_ket_thuc').val() < today) {
        appearForm1();
        alert("Thời gian kết thúc phải sau thời gian bắt đầu");
        return false;
    }
    if (ten_loi_moi.value.trim() == "" || noi_dung == '' || quyen_loi == '' || chi_tiet == '' || dieu_khoan == '' || tieu_chi == '' ) {

        if(chi_tiet == ''){
            appearForm2();
            validate_chi_tiet.classList.remove("hidden")
            validate_chi_tiet.classList.add("block")
        }
        if(dieu_khoan == ''){
            appearForm2();
            validate_dieu_khoan.classList.remove("hidden")
            validate_dieu_khoan.classList.add("block")
        }
        if(tieu_chi==''){
            appearForm2();
            validate_tieu_chi.classList.remove("hidden")
            validate_tieu_chi.classList.add("block")
        }
        if(quyen_loi == ''){
            appearForm1();
            validate_quyen_loi.classList.remove("hidden")
            validate_quyen_loi.classList.add("block")
        }
        if (noi_dung == ''){
            appearForm1();
            validate_noi_dung.classList.remove("hidden")
            validate_noi_dung.classList.add("block")
        }
        if (ten_loi_moi.value.trim() == ''){
            appearForm1();
            validate_ten_loi_moi.classList.remove("hidden")
            validate_ten_loi_moi.classList.add("block")
        }
        return false;
    }
    return true;
}

function validateEmail() {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test($('#email').val())){
        alert("Sai định dạng email");
        return false;
    }
    return true;
}