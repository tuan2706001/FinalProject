//get location
let placholder = ($('#location').val() == 'vi' ? 'Tìm kiếm' : 'Search')
let instructTeacher = ($('#location').val() == 'vi' ? 'Giáo viên hướng dẫn' : 'Instructors')
let listTeacher = ($('#location').val() == 'vi' ? 'Danh sách giáo viên' : 'List teacher')
let downloadFromDesktop = ($('#location').val() == 'vi' ? 'Tải lên từ máy tính' : 'Upload from computer')
let searchStudent = ($('#location').val() == 'vi' ? 'Tìm kiếm sinh viên' : 'Looking for students')
let importStudent = ($('#location').val() == 'vi' ? 'Kết nhập' : 'Import')
let exportStudent = ($('#location').val() == 'vi' ? 'Kết xuất' : 'Export')
let downloadTemplate = ($('#location').val() == 'vi' ? 'Tải file mẫu' : 'Download template')
let studentCode = ($('#location').val() == 'vi' ? 'Mã sinh viên' : 'Student code')
let studentName = ($('#location').val() == 'vi' ? 'Tên sinh viên' : "Student's name")
var khoaId;
$("#khoa").change(function () {
    khoaId = $(this).val();
})
var list1 = [];
var teacher1;
var enterpriseId;
//add new tt
$('.search').hide();
$('.excel').show();
$('.renderExcel').click(function () {
    var show = $(this).attr("data-open");
    var hide = $(this).attr("data-close");
    $('' + hide).hide('fast');
    $('' + show).show('fast');
});
$('.renderSearch').click(function () {
    var show = $(this).attr("data-open");
    var hide = $(this).attr("data-close");
    $('' + hide).hide('fast');
    $('' + show).show('fast');
});
$(".enterprise").select2({
    width: "100%",
    minimumInputLength: 1,
    placeholder: "Chọn doanh nghiệp",
    language: {
        inputTooShort: function () {
            return "Nhập tên doanh nghiệp!";
        },
        noResults: function () {
            return "Không tìm thấy doanh nghiệp";
        },
    },
    ajax: {
        url: "/quan-tri-nha-truong/tien-ich/thuc-tap/danh-sach-thuc-tap",
        dataType: 'json',
        type: "GET",
        placholder: placholder,
        data: function (term) {
            return {
                search: term.term
            };
        },
        processResults: function (data) {
            return {
                results: $.map(data, function (item) {
                    return {
                        text: item.name,
                        id: item.id
                    }
                })
            };
        }
    }
});
$(".enterprise").on("select2:select", function (e) {
    let tag = e.params.data;
    var id = tag.id;
    var text = tag.text;
    $(this).val(id).trigger("change");
    enterpriseId = $(this).val();
});
$(".searchStd").on("select2:opening",function(){
    if (khoaId == null){
        $("#khoa").focus();
        alert("Vui lòng chọn khoa trước");
    }
})
$(".searchStd").select2({
    width: "100%",
    minimumInputLength: 1,
    placeholder: "Tìm kiếm sinh viên",
    language: {
        inputTooShort: function () {
            return "Nhập tên sinh viên!";
        },
        noResults: function () {
            return "Không tìm thấy sinh viên";
        },
        errorLoading: function(){
            return "Vui lòng chọn khoa/ngành trước"
        }
    },
    ajax: {
        url: "/quan-tri-nha-truong/tien-ich/thuc-tap/danh-sach-sinh-vien",
        dataType: 'json',
        type: "GET",
        placholder: placholder,
        data: function (term) {
            return {
                search: term.term,
                khoaId: khoaId
            };
        },
        processResults: function (data) {
            // console.log(data);
            return {
                results: $.map(data, function (item) {
                    return {
                        text: item.name,
                        id: item.id,
                        code: item.maSinhVien,
                        email: item.email
                    }
                })
            };
        }
    }
});
$(".searchStd").on("select2:select", function (e) {
    let tag = e.params.data;
    var id = tag.id;
    var name = tag.text;
    var code = tag.code;
    var email = tag.email;
    $(this).val(id).trigger("change");
    var variableName = $(this).parent().parent().find(".tablex").attr("data-student");
    var table = $(this).parent().parent().find(".tablex").attr("id");
    var object = {
        name: name,
        email: email,
        code: code
    }
    if (window[variableName] == null) {
        window[variableName] = [];
        window[variableName].push(object);
    } else {
        var myObj =  window[variableName].find(obj => obj.code === code);
        if (myObj == null){
            window[variableName].push(object);
        }else{
            alert("Đã tồn tại sinh viên này !");
        }
    }
    var appendToTable = $(this).parent().parent().find(".tablex").attr("id");
    $("#" + appendToTable).empty();
    $.each(window[variableName], function (index, item) {
        var icon = "<svg class='removeItem h-10 w-50 ' data-table='"+table+"' data-student='"+variableName+"' data-value='"+item.code+"' width=\"20\" height=\"20\" viewBox=\"0 0 16 16\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">";
        icon += "<path fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M8 14C11.3137 14 14 11.3137 14 8C14 4.68629 11.3137 2 8 2C4.68629 2 2 4.68629 2 8C2 11.3137 4.68629 14 8 14ZM4.66667 9H11.3333V7H4.66667V9Z\" fill=\"#D0D3DD\"/>";
        icon += "</svg>";
        var row = "<tr>";
        row += "<td>";
        row += index + 1;
        row += "</td>";
        row += "<td>";
        row += icon;
        row += "</td>";
        row += "<td>";
        row += item.code;
        row += "</td>";
        row += "<td>";
        row += item.name;
        row += "</td>";
        row += "<td>";
        row += item.email;
        row += "</td>";
        row += "</tr>";
        $("#" + appendToTable).append(row);
    })
});
$(document).on('click','.removeItem',function(){
    var arrStd = $(this).attr("data-student");
    var table = $(this).attr("data-table");
    var value = $(this).attr("data-value");
    $(""+table).empty();
    window[arrStd].splice( window[arrStd].findIndex(item => item.code === value), 1);
    $.each(window[arrStd], function (index, item) {
        var icon = "<svg class='removeItem h-10 w-50' data-table='"+table+"' data-student='"+arrStd+"' data-value='"+item.code+"' width=\"20\" height=\"20\" viewBox=\"0 0 16 16\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">";
        icon += "<path fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M8 14C11.3137 14 14 11.3137 14 8C14 4.68629 11.3137 2 8 2C4.68629 2 2 4.68629 2 8C2 11.3137 4.68629 14 8 14ZM4.66667 9H11.3333V7H4.66667V9Z\" fill=\"#D0D3DD\"/>";
        icon += "</svg>";
        var row = "<tr>";
        row += "<td>";
        row += index + 1;
        row += "</td>";
        row += "<td>";
        row += icon;
        row += "</td>";
        row += "<td>";
        row += item.code;
        row += "</td>";
        row += "<td>";
        row += item.name;
        row += "</td>";
        row += "<td>";
        row += item.email;
        row += "</td>";
        row += "</tr>";
        $("" + table).append(row);
    })
    console.log(window[arrStd]);
});
$(".teacher").select2({
    width: "100%",
    minimumInputLength: 1,
    placeholder: "Chọn giáo viên",
    language: {
        inputTooShort: function () {
            return "Nhập tên giáo viên!";
        },
        noResults: function () {
            return "Không tìm thấy giáo viên";
        },
    },
    ajax: {
        url: "/quan-tri-nha-truong/tien-ich/thuc-tap/danh-sach-giao-vien",
        dataType: 'json',
        type: "GET",
        placholder: placholder,
        data: function (term) {
            return {
                search: term.term
            };
        },
        processResults: function (data) {
            return {
                results: $.map(data, function (item) {
                    return {
                        text: item.name,
                        id: item.id
                    }
                })
            };
        }
    }
});
$(".teacher").on("select2:select", function (e) {
    let tag = e.params.data;
    var id = tag.id;
    var text = tag.text;
    $(this).val(id).trigger("change");
    var targetId = $(this).attr("id");
    window[targetId] = $("#" + targetId).val();
})
var target = $(".region");
var count = 2;
$(".addTeacher").click(function () {
    // huỷ select2 để tránh lỗi
    $(this).parent().children().first().select2("destroy");
    $(this).parent().parent().parent().find(".searchStd").select2("destroy");
    // tạo ra 1 div giống với div gốc rồi chèn id
    var clone = $("#clone1").clone(true).attr("id", "clone" + count);
    // chèn div vừa tạo ra vào trong target
    clone.appendTo(target);
    // thay đổi nút thêm thành xoá
    var cloned = $("#clone" + count);
    cloned.children().find(".addTeacher").attr("data-id", "#clone" + count).removeClass("addTeacher").addClass("remove").off('click');
    var icon = "<svg width=\"20\" height=\"20\" class=\"h-10 w-50 \" viewBox=\"0 0 16 16\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">";
    icon += "<path fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M8 14C11.3137 14 14 11.3137 14 8C14 4.68629 11.3137 2 8 2C4.68629 2 2 4.68629 2 8C2 11.3137 4.68629 14 8 14ZM4.66667 9H11.3333V7H4.66667V9Z\" fill=\"#D0D3DD\"/>";
    icon += "</svg>";
    cloned.children().find(".remove").empty().append(icon);
    // set id cho select để lấy dữ liệu teacher
    cloned.children().find(".teacher").attr("id", "").attr("id", "teacher" + count);
    cloned.children().find("#teacher" + count).val("").trigger("change");
    cloned.children().find(".renderExcel").attr("id", "").attr("id", "renderExcel" + count).attr("data-open", "#excel" + count).attr("data-close", "#search" + count);
    cloned.children().find(".renderSearch").attr("id", "").attr("id", "renderSearch" + count).attr("data-open", "#search" + count).attr("data-close", "#excel" + count);
    cloned.find(".excel").attr("id", "").attr("id", "excel" + count);
    cloned.find(".search").attr("id", "").attr("id", "search" + count);
    cloned.find(".option").attr("name", "").attr("name", "option" + count);
    cloned.find(".searchStd").attr("id", "").attr("id", "searchStd" + count);
    cloned.find(".importExcel").attr("id", "").attr("id", "importExcel" + count);
    cloned.find(".importExcel").attr("data-table", "").attr("data-table", "#table" + count);
    cloned.find(".import").attr("data-import", count);
    cloned.find(".tablex").attr("id", "").attr("id", "table" + count);
    cloned.find("#table" + count).attr("data-student", "").attr("data-student", "student" + count);
    cloned.find("#table" + count).attr("data-student", "").attr("data-teacher", "teacher" + count);
    var idTable = cloned.find(".tablex").attr("id");
    $("#" + idTable).empty();
    for (var i = 1; i <= count; i++) {
        $("#teacher" + i).select2({
            width: "100%",
            minimumInputLength: 1,
            placeholder: "Chọn giáo viên",
            language: {
                inputTooShort: function () {
                    return "Nhập tên giáo viên!";
                },
                noResults: function () {
                    return "Không tìm thấy giáo viên";
                },
            },
            ajax: {
                url: "/quan-tri-nha-truong/tien-ich/thuc-tap/danh-sach-giao-vien",
                dataType: 'json',
                type: "GET",
                placholder: placholder,
                data: function (term) {
                    return {
                        search: term.term
                    };
                },
                processResults: function (data) {
                    return {
                        results: $.map(data, function (item) {
                            return {
                                text: item.name,
                                id: item.id
                            }
                        })
                    };
                }
            }
        });
        $("#searchStd" + i).select2({
            width: "100%",
            minimumInputLength: 1,
            placeholder: "Tìm kiếm sinh viên",
            language: {
                inputTooShort: function () {
                    return "Nhập tên sinh viên!";
                },
                noResults: function () {
                    return "Không tìm thấy sinh viên";
                },
            },
            ajax: {
                url: "/quan-tri-nha-truong/tien-ich/thuc-tap/danh-sach-sinh-vien",
                dataType: 'json',
                type: "GET",
                placholder: placholder,
                data: function (term) {
                    return {
                        khoaId: khoaId,
                        search: term.term

                    };
                },
                processResults: function (data) {
                    return {
                        results: $.map(data, function (item) {
                            return {
                                text: item.name,
                                id: item.id,
                                code: item.maSinhVien,
                                email: item.email
                            }
                        })
                    };
                }
            }
        });
    }
    count++;
})
$(document).on('click', '.remove', function () {
    var him = $(this).data("id");
    $("" + him).remove();
});
$('.import').click(function (e) {
    e.preventDefault();
    var dataImport = $(this).attr('data-import');
    $("#importExcel" + dataImport).click();
})

$('.importExcel').on('change', function () {
    var formData = new FormData();
    formData.append("file", $(this)[0].files[0]);
    var khoaId = $("#khoa").val();
    formData.append("khoa", khoaId);
    var table = $(this).attr('data-table');
    var studentArr = $(""+table).attr('data-student');
    var appendToTable = $(this).parent().parent().parent().find(".tablex").attr("id");
    var variableName = $(this).parent().parent().parent().find(".tablex").attr("data-student");
    var idInput = $(this).attr('id');
    $.ajax({
        type: "POST",
        data: formData,
        url: "/quan-tri-nha-truong/tien-ich/thuc-tap/import",
        contentType: false,
        processData: false,
        success: function (data) {
            // append data to table
            var object;
            if (data.status == 200) {
                var countError = 0;
                if (window[variableName] == null) {
                    window[variableName] = [];
                }
                $.each(data.object, function (index, item) {
                    object = {
                        name: item.tenSinhVien,
                        code: item.maSinhVien,
                        email: item.email
                    };
                    if (item.error == true) {
                        countError++;
                        // object = "<tr id='item" + index +"' class='item text-red-500'>";
                    }else{
                        // tìm ra xem đã tồn tại trong mảng hay chưa, nếu chưa thì push object
                            var myObj =  window[variableName].find(obj => obj.code == item.maSinhVien);
                            if(myObj == null){
                                window[variableName].push(object);
                            }
                    }
                });
                if (countError == 0) {
                    $("#" + appendToTable).empty();
                    $.each(window[variableName], function (index, item) {
                        var icon = "<svg class='removeItem h-10 w-50' data-table='"+table+"' data-student='"+studentArr+"' data-value='"+ item.code +"' width=\"20\" height=\"20\" viewBox=\"0 0 16 16\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">";
                        icon += "<path fill-rule=\"evenodd\" clip-rule=\"evenodd\" d=\"M8 14C11.3137 14 14 11.3137 14 8C14 4.68629 11.3137 2 8 2C4.68629 2 2 4.68629 2 8C2 11.3137 4.68629 14 8 14ZM4.66667 9H11.3333V7H4.66667V9Z\" fill=\"#D0D3DD\"/>";
                        icon += "</svg>";
                        var row = "<tr>";
                        row += "<td>";
                        row += index + 1;
                        row += "</td>";
                        row += "<td class='text-center'>";
                        row += icon;
                        row += "</td>";
                        row += "<td>";
                        row += item.code;
                        row += "</td>";
                        row += "<td>";
                        row += item.name;
                        row += "</td>";
                        row += "<td>";
                        row += item.email;
                        row += "</td>";
                        row += "</tr>";
                        $("#" + appendToTable).append(row);
                        $("#"+idInput).val('');
                    })
                }else{
                    alert("Lỗi! Chưa chọn khoá ngành hoặc sinh viên không thuộc khoá ngành");
                    $("#"+idInput).val('');
                }
                ;
            }
        },
        error: function (data) {

        }
    });
})
var array = [];
$(".btnSubmit").click(function(e){
    e.preventDefault();
    var flag = 1;
    var timeStart = $("#timeStart").val();
    var timeEnd = $("#timeEnd").val();
    var enterprise = enterpriseId;
    var stt = Date.parse(timeStart);
    var endt = Date.parse(timeEnd);

    $(".tablex").each(function(index, item){
        var stdArr = $(this).attr("data-student");
        var teacher = $(this).attr("data-teacher");
        $.each(window[stdArr],function(index,item){
            var teacherIndex = index+1;
            var object = {
                stdCode: item.code,
                teacherId: $("#"+teacher).val()
            }
            if ($("#"+teacher).val() != null){
                array.push(object);
            }else{
                alert("Vui lòng chọn giáo viên!");
                $("#"+teacher).focus();
                array = [];
                return false;
            }
        });

    })
    var groupby = groupBy(array,"stdCode");

    $.each(groupby,function (index,item){
        if (item.length > 1){
            flag = 0;
            alert("Lỗi trùng dữ liệu");
            return false;
        }
    });
    if (timeStart == ""){
        alert("Chưa nhập thời gian bắt đầu");
        $("#timeStart").focus();
        flag = 0;
        array = [];
    }else{
        if (timeEnd == ""){
            alert("Chưa chọn thời gian kết thúc");
            $("#timeEnd").focus();
            flag = 0;
            array = [];
            return  false;
        }else{
            if (stt > endt){
                alert("Thời gian bắt đàu không thể nhỏ hơn thời gian kết thúc");
                flag = 0;
                array = [];
                return false;
            }else{
                if (enterprise == null){
                    alert("Vui lòng chọn doanh nghiệp");
                    $(".enterprise").focus();
                    flag = 0;
                    array =  [];
                }else{
                    if (khoaId == null){
                        alert("Vui lòng chọn khoa");
                        flag = 0;
                        array = [];
                    }
                }
            }
        }
    }
    if (flag == 1){
        var maSinhVienAndGiaoVienId = {};
        $.each(array,function (index,item){
            maSinhVienAndGiaoVienId[item.stdCode] = item.teacherId;
            // maSinhVienAndGiaoVienId.set(item.stdCode,item.teacherId);
        });
        var data = {
            maSinhVienAndGiaoVienId: maSinhVienAndGiaoVienId,
            khoaId: khoaId,
            enterpriseId: $(".enterprise").val(),
            ngayBatDau: timeStart,
            ngayKetThuc: timeEnd
        }
        $.ajax({
            url: "/quan-tri-nha-truong/tien-ich/thuc-tap/danh-sach",
            method: "POST",
            data:JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function (data){
                switch (data.status){
                    case 200:
                        location.reload();
                        break;
                    case 400:
                        alert(data.message);
                        location.reload();
                }
            }
        })
    }
});
function groupBy(arr, criteria) {
    const newObj = arr.reduce(function (acc, currentValue) {
        if (!acc[currentValue[criteria]]) {
            acc[currentValue[criteria]] = [];
        }
        acc[currentValue[criteria]].push(currentValue);
        return acc;
    }, {});
    return newObj;
}
var ModalPhanHoi = $("#ModalPhanHoi");
var modalAdd = $("#myModal");
var btnPhanHoi = $(".btnPhanHoi");
var span2 = $(".close");
btnPhanHoi.click(function (){
    ModalPhanHoi.css("display","block");
    var targetId = $(this).data('id');
    var targetName = $(this).data('name');
    ModalPhanHoi.find('.targetName').text("Đến: "+targetName);
    // ModalPhanHoi.find('.form').attr("action","/thuc-tap/phan-hoi/tao-phan-hoi");
    ModalPhanHoi.find('.btnCreatePhanHoi').attr("data-id",targetId);
})
$(".btnCreatePhanHoi").click(function (e){
    e.preventDefault();
    var chude = $(".chudephanhoi").val();
    var noidung = $(".noidungphanhoi").val();
    var targetId = $(this).attr('data-id');
    $.ajax({
        url: "/quan-tri-nha-truong/tien-ich/thuc-tap/phan-hoi/tao-phan-hoi",
        method: "POST",
        data: {
            chude:chude,
            content: noidung,
            targetId: targetId
        },
        success: function (data){
            console.log(data);
        }
    })
})
if (span2 !== undefined) {
    span2.click(function () {
        ModalPhanHoi.css("display","none");
        $('body,html').css('overflow', 'auto');
    });
}
$("#closeModalCreate").click(function (){
    modalAdd.css("display","none");
})
ModalPhanHoi.click(function (event){
    if (event.target == $(this)){
        $(this).hide();
    }
    console.log(event.target);
})
