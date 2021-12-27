var language= $("#language").val()
$('.btnRegister').click(function (e){
    e.preventDefault();
    var action = $('#register').attr('action');
    $.ajax({
       url: action,
       method: 'POST',
       success: function (){
           location.reload();
       }
    });
});
var register = $('#register');
var registered = $('#registered');

register.click(function (){
    var action = register.data("url") + '/' + register.data("id") + '/' + register.data("name") + "/";
    $.ajax({
        url: action,
        method: 'POST',
        success: function (){
            registered.removeClass("hidden");
            register.addClass("hidden");
            // history.go(0);
        },
    });
})

$('#endCall').click(function () {
    var action = $(this).data('url') + $(this).data('id')
    $.ajax({
        url: action,
        method: 'PUT',
        success: function (){
            $('#btnEndCall').remove();
            $('#valueAndEndCall').append('\n' +
                '                <div class="flex justify-end" th:if="${data.status == 6}">\n' +
                '                    <button\n' +
                '                         class="w-28 cursor-not-allowed h-10 text-white rounded bg-gray-500 flex justify-center items-center"\n' +
                '                         disabled>Đã Kết thúc</button>\n' +
                '                </div>');
            myModalEndCall.style.display = "none";
        }
    })
})

if ($('#callId').val() != "" && $('#orgId').val() != ""){
    $.ajax({
        url:"/ajax/check-follow",
        method:"GET",
        data: {
            orgId : $('#orgId').val(),
            callId : $('#callId').val(),
        },
        success:function (n){
            if (n==true){
                if (language == "vi"){
                    $("#callCare").html("Đã quan tâm");
                }else{
                    $("#callCare").html("Followed");
                }
            }
            else{
                if (language == "vi"){
                    $("#callCare").html("Quan tâm");
                }else{
                    $("#callCare").html("Follow");
                }
            }
        },
        error: function (){

        }
    })
}
if ($('#callId').val() != "" && $('#orgId').val() != "") {
    $("#callCare").click(function (){
        $.ajax({
            url:"/ajax/follow",
            method:"POST",
            data: {
                orgId : $('#orgId').val(),
                callId : $('#callId').val(),
            },
            success:function (n){
                if (n==true){
                    if (language == "vi"){
                        $("#callCare").html("Đã quan tâm");
                    }else{
                        $("#callCare").html("Followed");
                    }
                }
                else{
                    if (language == "vi"){
                        $("#callCare").html("Quan tâm");
                    }else{
                        $("#callCare").html("Follow");
                    }
                }
                $.ajax({
                    url:"/ajax/check-follow",
                    method:"GET",
                    data: {
                        orgId : $('#orgId').val(),
                        callId : $('#callId').val(),
                    },
                    success:function (n){
                        if (n==true){
                            if (language == "vi"){
                                $("#callCare").html("Đã quan tâm");
                            }else{
                                $("#callCare").html("Followed");
                            }
                        }
                        else{
                            if (language == "vi"){
                                $("#callCare").html("Quan tâm");
                            }else{
                                $("#callCare").html("Follow");
                            }
                        }
                    },
                    error: function (){

                    }
                })
            },
            error: function (){

            }
        })
    })
}


