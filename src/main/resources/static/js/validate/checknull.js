function checkValidate(){
    var check = true;
    if ($('#name').val() === ""){
        check = false
        var id = "name"
        var err = "errName"
        var mess = "Tên không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#majorId').val() === ""){
        check = false
        var id = "majorId"
        var err = "errMajor"
        var mess = "Hãy chọn ngành"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#courseId').val() === ""){
        check = false
        var id = "courseId"
        var err = "errCourse"
        var mess = "Hãy chọn khóa"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#fullName').val() === ""){
        check = false
        var id = "fullName"
        var err = "errFullName"
        var mess = "Họ và tên không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#email').val() === ""){
        check = false
        var id = "email"
        var err = "errEmail"
        var mess = "Email không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#phone').val() === ""){
        check = false
        var id = "phone"
        var err = "errPhone"
        var mess = "Số điện thoại không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#birthday').val() === ""){
        check = false
        var id = "birthday"
        var err = "errBirthday"
        var mess = "Ngày sinh không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    // if ($('#gender').val() === ""){
    //     check = false
    //     var id = "gender"
    //     var err = "errGender"
    //     var mess = "Giới tính không được để trống"
    //     checkEmptyValidate(id, mess, err)
    // }

    if ($('#address').val() === ""){
        check = false
        var id = "address"
        var err = "errAddress"
        var mess = "Địa chỉ không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    // if ($('#role').val() === ""){
    //     check = false
    //     var id = "role"
    //     var err = "errRole"
    //     var mess = "Quyền không được để trống"
    //     checkEmptyValidate(id, mess, err)
    // }

    // if ($('#status').val() === ""){
    //     check = false
    //     var id = "status"
    //     var err = "errStatus"
    //     var mess = "Trạng thái không được để trống"
    //     checkEmptyValidate(id, mess, err)
    // }

    if ($('#studentCode').val() === ""){
        check = false
        var id = "studentCode"
        var err = "errStudentCode"
        var mess = "Mã sinh viên không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#gradeId').val() === ""){
        check = false
        var id = "gradeId"
        var err = "errGrade"
        var mess = "Hãy chọn lớp"
        checkEmptyValidate(id, mess, err)
    }


    if ($('#duration').val() === ""){
        check = false
        var id = "duration"
        var err = "errDuration"
        var mess = "Số giờ không được để trống"
        checkEmptyValidate(id, mess, err)
    }

    // if ($('#choose').val() === ""){
    //     check = false
    //     var id = "choose"
    //     var err = "errChoose"
    //     var mess = "Phải chọn 1 trong 2"
    //     checkEmptyValidate(id, mess, err)
    // }

    if ($('#studentId').val() === ""){
        check = false
        var id = "studentId"
        var err = "errStudent"
        var mess = "Hãy chọn sinh viên"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#subjectId').val() === ""){
        check = false
        var id = "subjectId"
        var err = "errSubject"
        var mess = "Hãy chọn môn học"
        checkEmptyValidate(id, mess, err)
    }

    if ($('#theory1').val() === ""){
        check = false
        var id = "theory1"
        var err = "errTheory1"
        var mess = "Hãy nhập điểm"
        checkEmptyValidate(id, mess, err)
    }


    if ($('#skill1').val() === ""){
        check = false
        var id = "skill1"
        var err = "errSkill1"
        var mess = "Hãy nhập điểm"
        checkEmptyValidate(id, mess, err)
    }

    return check
}



//---------------------------------------------------------
    function checkEmptyValidate(id, mess, err) {
        if ($('#' + id).val() === "") {
            $('#' + err).html(mess)
        } else {
            $('#' + err).html("")
        }
    }

    // //Sinh viên-------------------
    //
    // $('#gradeId').change(function () {
    //     var id = "gradeId"
    //     var err = "errGrade"
    //     var mess = "Hãy chọn lớp"
    //     checkEmptyValidate(id, mess, err)
    // })
    //
    // $('#studentCode').change(function () {
    //     var id = "studentCode"
    //     var err = "errStudentCode"
    //     var mess = "Mã sinh viên không được để trống"
    //     checkEmptyValidate(id, mess, err)
    // })

    //khóa ngành lớp--------------------------------------

    // $('#name').change(function () {
    //     var id = "name"
    //     var err = "errName"
    //     var mess = "Tên không được để trống"
    //     checkEmptyValidate(id, mess, err)
    // })

//     $('#majorId').change(function () {
//         var id = "majorId"
//         var err = "errMajor"
//         var mess = "Hãy chọn ngành"
//         checkEmptyValidate(id, mess, err)
//     })
//
//     $('#courseId').change(function () {
//         var id = "courseId"
//         var err = "errCourse"
//         var mess = "Hãy chọn khóa"
//         checkEmptyValidate(id, mess, err)
//     })
//
//
// //Giáo vụ------------------------------------------------
//
//     $('#fullName').change(function () {
//         var id = "fullName"
//         var err = "errFullName"
//         var mess = "Họ và tên không được để trống"
//         checkEmptyValidate(id, mess, err)
//     })
//
//     $('#email').change(function () {
//         var id = "email"
//         var err = "errEmail"
//         var mess = "Email không được để trống"
//         checkEmptyValidate(id, mess, err)
//     })
//
//     $('#phone').change(function () {
//         var id = "phone"
//         var err = "errPhone"
//         var mess = "Số điện thoại không được để trống"
//         checkEmptyValidate(id, mess, err)
//     })
//
//     $('#birthday').change(function () {
//         var id = "birthday"
//         var err = "errBirthday"
//         var mess = "Ngày sinh không được để trống"
//         checkEmptyValidate(id, mess, err)
//     })
//
//     // $('#gender').change(function () {
//     //     var id = "gender"
//     //     var err = "errGender"
//     //     var mess = "Giới tính không được để trống"
//     //     checkEmptyValidate(id, mess, err)
//     // })
//
//     $('#address').change(function () {
//         var id = "address"
//         var err = "errAddress"
//         var mess = "Địa chỉ không được để trống"
//         checkEmptyValidate(id, mess, err)
//     })
//
//     // $('#role').change(function () {
//     //     var id = "role"
//     //     var err = "errRole"
//     //     var mess = "Quyền không được để trống"
//     //     checkEmptyValidate(id, mess, err)
//     // })
//
//     // $('#status').change(function () {
//     //     var id = "status"
//     //     var err = "errStatus"
//     //     var mess = "Trạng thái không được để trống"
//     //     checkEmptyValidate(id, mess, err)
//     // })
//
//     //Môn học -------------------------------------------
//
//     $('#duration').change(function () {
//         var id = "duration"
//         var err = "errDuration"
//         var mess = "Số giờ không được để trống"
//         checkEmptyValidate(id, mess, err)
//     })
//
//     // $('#choose').change(function () {
//     //     var id = "choose"
//     //     var err = "errChoose"
//     //     var mess = "Phải chọn 1 trong 2"
//     //     checkEmptyValidate(id, mess, err)
//     // })
//
//     //Điểm thi ----------------------------------------------
//
//     $('#studentId').change(function () {
//         var id = "studentId"
//         var err = "errStudent"
//         var mess = "Hãy chọn sinh viên"
//         checkEmptyValidate(id, mess, err)
//     })
//
//     $('#subjectId').change(function () {
//         var id = "subjectId"
//         var err = "errSubject"
//         var mess = "Hãy chọn môn học"
//         checkEmptyValidate(id, mess, err)
//     })
//
//     $('#theory1').change(function () {
//         var id = "theory1"
//         var err = "errTheory1"
//         var mess = "Hãy nhập điểm"
//         checkEmptyValidate(id, mess, err)
//     })
//
//     $('#skill1').change(function () {
//         var id = "skill1"
//         var err = "errSkill1"
//         var mess = "Hãy nhập điểm"
//         checkEmptyValidate(id, mess, err)
//     })


