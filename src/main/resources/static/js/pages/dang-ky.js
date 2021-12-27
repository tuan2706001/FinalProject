
$(document).ready(function (){
    $(".country").select2();
    $(".city").select2();
    var supplies = [];
    var demands = [];
    var name = "new-";
    var regex = /\b\S*[AĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴAĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴAĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴAĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴAĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴAĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴA-Z]+\S*\b/g;
    $(".demands").select2({
        tags: true,
        placeholder: "Chọn nhu cầu",
        createTag: function(params) {
            var term = $.trim(params.term);
            if (regex.test(term)){
                return {
                    id: name+term,
                    text: term
                }
            }else{
                return null;
            }
        }
    });
    $(".demands").on("select2:select", function (e) {
        let tag = e.params.data;
        var object;
        var demandId = tag.id;
        if (~demandId.indexOf("new-")){
            object = {
                id: "",
                name: tag.text
            }
        }else{
            object = {
                id: tag.id,
                name: tag.text
            }
        }
        demands.push(object);
    });
    $(".demands").on("select2:unselecting",function (e){
        let tag = e.params.args.data;
        demands.splice(demands.findIndex(item => item.name === tag.text), 1)
    });
    $(".supplies").select2({
        tags: true,
        placeholder: "Chọn khả năng cung cấp",
        createTag: function(params) {
            var term = $.trim(params.term);
            if (regex.test(term)){
                return {
                    id: name+term,
                    text: term
                }
            }else{
                return null;
            }
        }
    });
    $(".supplies").on("select2:select", function (e) {
        let tag = e.params.data;
        var object;
        var supplyId = tag.id;
        if (~supplyId.indexOf("new-")){
            object = {
                id: "",
                name: tag.text
            }
        }else{
            object = {
                id: tag.id,
                name: tag.text
            }
        }
        supplies.push(object);
    });
    $(".supplies").on("select2:unselecting",function (e){
        let tag = e.params.args.data;
        supplies.splice(supplies.findIndex(item => item.name === tag.text), 1)
    });
    $(".fields").select2({
        placeholder: "Chọn lĩnh vực",
    });
    $(".fields").on("select2:select", function (e) {


    });
    $("#formSignUp").submit(function (e){
        e.preventDefault();
        // Personal infomation
        var fullname = $("#fullName").val();
        var email = $("#email").val();
        var password = $("#password").val();
        var repassword = $("#repassword").val();
// University information
        var uname = $("#uname").val() // university name
        var website = $("#website").val();
        var country = $("#country").val();
        var city = $("#city").val();
        var street= $("#street").val();
        var uemail = $("#uemail").val(); // email lãnh đạo nhà trường
        var linhvuc = $("#linhvuc").val();
// Thông tin khác
        var nhucau = demands;
        var khanang = supplies;
// Lấy ra ngôn ngữ
        var language = $("#language").val();
        var flag = 1;
        /* Dịch validate */
        var RegexEmail = /^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$/;
        if (language == "vi"){
            var mFnameR = "Vui lòng nhập đầy đủ họ tên !";
            var mEmailR = "Email không được bỏ trống !";
            var mEmailF = "Email không hợp lệ !";
            var mPasswordR = "Vui lòng điền mật khẩu !";
            var mRePasswordE = "Mật khẩu không khớp !";
            var mRepasswordR = "Không được để trống !";
        }else{
            var mFnameR = "Please enter your full name !";
            var mEmailR = "Email is not empty !";
            var mEmailF = "Invalid email !";
            var mPasswordR = "Please enter password !";
            var mRePasswordE = "Password incorrect !";
            var mRepasswordR = "Can't be blank !";
        }
        if (fullname == ""){
            $("#fullName").focus();
            $(".efname").text(mFnameR);
            flag = 0;
        }
        if (email == ""){
            $("#email").focus();
            $(".eemail").text(mEmailR);
            flag = 0;
        }else{
            if (!RegexEmail.test(email)){
                $("#email").focus();
                $(".eemail").text(mEmailF);
                flag = 0;
            }
        }
        if (password == ""){
            $("#password").focus();
            $(".epassword").text(mPasswordR);
            flag = 0;
        }
        if (repassword == ""){
            $("#repassword").focus();
            $(".erepassword").text(mRepasswordR);
            flag = 0;
        }else{
            if (repassword != password){
                $("#repassword").focus();
                $(".erepassword").text(mRePasswordE);
                flag = 0;
            }
        }
        var url, urlORG;
        var role = location.search.split("role=")[1];
        switch (role){
            case "university":
                url = "/user/signup/university";
                urlORG = "/user/signup/university/information";
                console.log(urlORG);
                break;
            case "enterprise":
                url = "/user/signup/enterprise"
                urlORG = "/user/signup/enterprise/information";
                break;
        }
        if (flag == 1){
            $.ajax({
                url: url,
                method: "post",
                traditional: true,
                data:{
                    fullName: fullname,
                    email: email,
                    password: password
                },success: function (data){
                    switch (data.status){
                        case 200:
                            var userId = data.object.userId;
                            var data = {
                                name: uname,
                                organizationRepresentativeEmail: uemail,
                                website: website,
                                organizationAddress: street,
                                tinhThanhId: city,
                                nationId: country,
                                listDemand1: nhucau,
                                listSupply1: khanang,
                                listFieldId: linhvuc,
                                isOrg: true,
                                userCreate: userId
                            }
                            $.ajax({
                                url: urlORG,
                                method: "POST",
                                data:JSON.stringify(data),
                                dataType: "json",
                                contentType: "application/json",
                                success: function (data1) {
                                    if (data1.status == 200){
                                        location.replace("/user/signup/success");
                                    }
                                }
                            })
                            break;
                        case 409:
                            // alert(data.message);
                            $("#email").focus();
                            $(".eemail").text(data.message);
                            break;
                        case 400:
                            $("#email").focus();
                            $(".eemail").text(data.message);
                            break;
                    }
                }
            });
        }
    });

    $("#fullName").keyup(function (){
        if ($(this).val() != ""){
            $(".efname").text("");
            flag = 1;
        }
    });
    $("#email").keyup(function (){
        if ($(this).val() != ""){
            $(".eemail").text("");
            flag = 1;
        }
    });
    $("#password").keyup(function (){
        if ($(this).val() != ""){
            $(".epassword").text("");
            flag = 1;
        }
    });
    $("#repassword").keyup(function (){
        if ($(this).val() != "" && $(this).val() == $("#password").val()){
            $(".erepassword").text("");
            flag = 1;
        }
    });
});
$("#country").change(function (){
   var countryId = $(this).val();
   $("#city").html("");
   $.ajax({
        url: "/user/signup/tinh-thanh",
       method: "post",
       data: {
            nationId: countryId
       },
       success: function (data){
           var option = "";
           $("#city").empty();
            for (var i = 0; i < data.length; i++){
                option = "<option value='"+ data[i].id + "'>" + data[i].tenTinh + "</option>"
                $("#city").append(option);
            }
       }
   });
});
$body = $("body");
$(document).on({
    ajaxStart: function() { $body.addClass("loading");    },
    ajaxStop: function() { $body.removeClass("loading"); }
});
