function checkEditValidate() {
    var check = true;

    // console.log($('#skill1').val())

    if ($('#theory1').val() === ""){
        check = false
        var id = "theory1"
        var err = "errTheory1"
        var mess = "Hãy nhập điểm"
        checkEmptyValidateEdit(id, mess, err)
    } else {
        if ($('#theory1').val() >= 5) {
            $('#errTheory2').html('Đầu điểm đã đủ không thể nhập thêm')
            check = false
        }
    }

    if ($('#editName').val() === ""){
        check = false
        var id = "editName"
        var err = "errEditName"
        var mess = "Tên không được để trống"
        checkEmptyValidateEdit(id, mess, err)
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