var language = $("#language").val();
var flag = 1;
if (language == "vi"){
    var mCodeR = "Vui lòng điền mã bảo mật";
    var mPasswordR = "Vui lòng điền mật khẩu !";
    var mRePasswordE = "Mật khẩu không khớp !";
    var mRepasswordR = "Không được để trống !";
}else{
    var mCodeR = "Please enter security code";
    var mPasswordR = "Please enter password !";
    var mRePasswordE = "Password incorrect !";
    var mRepasswordR = "Can't be blank !";
}

$(".btnForgot").click(function (e){
    var code = $("#code").val();
    var password = $("#password").val();
    var repassword = $("#repassword").val();
    var email = localStorage.getItem("email");
    e.preventDefault();
    if (code == ""){
        $("#code").focus();
        $(".errorC").text(mCodeR);
        flag = 0;
    }
    if (password == ""){
        $("#password").focus();
        $(".errorP").text(mRepasswordR);
        flag = 0;
    }
    if (repassword == ""){
        $("#repassword").focus();
        $(".errorRP").text(mRePasswordR);
        flag = 0;
    }else{
        if (repassword != password){
            $("#repassword").focus();
            $(".errorRP").text(mRePasswordE);
            flag = 0;
        }
    }
    if (flag == 1){
        $.ajax({
            url: "/user/forgot-password/step2",
            method: "POST",
            data: {
                email: email,
                code: code,
                password: password
            },
            success: function (data){
                if (data.status == 200){
                    localStorage.removeItem("email");
                    location.replace("/user/forgot-password/step3");
                }else{
                    $(".errorData").text(data.message);
                }
            }
        })
    }
});
$("#password").keyup(function (){
    if ($(this).val() != ""){
        $(".errorP").text("");
        flag = 1;
    }
});
$("#repassword").keyup(function (){
    if ($(this).val() != "" && $(this).val() == $("#password").val()){
        $(".errorRP").text("");
        flag = 1;
    }
});
$body = $("body");
$(document).on({
    ajaxStart: function() { $body.addClass("loading");},
    ajaxStop: function() { $body.removeClass("loading"); }
});
