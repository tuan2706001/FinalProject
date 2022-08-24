function checkEditValidate() {
    var check = true;

    // if ($('.theory2').val() === ""){
    //     check = false
    //     var id = "theory2"
    //     var err = "errTheory2"
    //     var mess = "Hãy nhập điểm"
    //     checkEmptyValidateEdit(id, mess, err)
    //     check = false
    // } else {
        if ($('.theory2').val() !== "" && $('.theory1').val() >= 5) {
            $('#errTheory2').html('Đầu điểm đã đủ không thể nhập thêm')
            check = false
        }
    // }

    // if ($('.skill2').val() === ""){
    //     check = false
    //     var id = "skill2"
    //     var err = "errSkill2"
    //     var mess = "Hãy nhập điểm"
    //     checkEmptyValidateEdit(id, mess, err)
    //     check = false
    // } else {
        if ($('.skill2').val() !== "" &&$('.skill1').val() >= 5) {
            $('#errSkill2').html('Đầu điểm đã đủ không thể nhập thêm')
            check = false
        }
    // }

    if ($('#editName').val() === ""){
        check = false
        var id = "editName"
        var err = "errEditName"
        var mess = "Tên không được để trống"
        checkEmptyValidateEdit(id, mess, err)
    }

    if ($('#editFullName').val() === ""){
        check = false
        var id = "editFullName"
        var err = "errEditFullName"
        var mess = "Họ và tên không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#editStudentCode').val() === ""){
        check = false
        var id = "editStudentCode"
        var err = "errEditStudentCode"
        var mess = "Mã sinh viên không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#editEmail').val() === ""){
        check = false
        var id = "editEmail"
        var err = "errEditEmail"
        var mess = "Email không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#editPhone').val() === ""){
        check = false
        var id = "editPhone"
        var err = "errEditPhone"
        var mess = "Số điện thoại không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#editAddress').val() === ""){
        check = false
        var id = "editAddress"
        var err = "errEditAddress"
        var mess = "Địa chỉ không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#editDuration').val() === ""){
        check = false
        var id = "editDuration"
        var err = "errEditDuration"
        var mess = "Số giờ không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    return check
}
//---------------------------------------------------------
function checkEmptyValidateEdit(id, mess, err) {
    if ($('#' + id).val() === "") {
        $('#' + err).html(mess)
    } else {
        $('#' + err).html("")
    }
}

    // $('#theory2').change(function () {
    //     if ($('#theory2').val().length >= 5) {
    //         var id = "theory2"
    //         var err = "errTheory2"
    //         var mess = "Điểm thi lần 1 đã qua môn"
    //         checkEmptyValidateEdit(id, mess, err)
    //     }
    // })