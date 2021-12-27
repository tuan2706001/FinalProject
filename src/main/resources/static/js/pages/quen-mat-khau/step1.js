var language = $("#language").val();
var flag = 1;
var RegexEmail = /^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$/;
if (language == "vi"){
    var mEmailR = "Email không được bỏ trống !";
    var mEmailF = "Email không hợp lệ !";
    var mEmailNF = "Không tìm thấy email trong hệ thống";
}else{
    var mEmailR = "Email is not empty !";
    var mEmailF = "Invalid email !";
    var mEmailNF = "This email not found!";
}
$(".btnForgot").click(function (e){
    var email = $("#email").val();
    e.preventDefault();
    if (email == ""){
        $(".errorE").text(mEmailR);
        flag = 0;
    }else{
        if (!RegexEmail.test(email)){
            $("#email").focus();
            $(".errorE").text(mEmailF);
            flag = 0;
        }else {
            flag = 1;
        }
    }
    if (flag == 1){
        $.ajax({
            url: "/user/forgot-password",
            method: "POST",
            data: {
                email: email
            },
            success: function (data){
                console.log(data);
                if(data.status == 200){
                    localStorage.setItem("email", email);
                    $(".content").load("/user/forgot-password/next");
                }else {
                    $("#email").focus();
                    $(".errorE").text(data.message);
                }
            },
        })
    }
});
$("#email").keyup(function (){
    if ($(this).val() != ""){
        $(".errorE").text("");
        flag = 1;
    }
});
$body = $("body");
$(document).on({
    ajaxStart: function() { $body.addClass("loading");},
    ajaxStop: function() { $body.removeClass("loading"); }
});
