$(document).ready(function () {
    var language = $('#language').val();

    $(".btnCare").click(function (){
        var btnCareId = $(this).attr("id");
        var checkCare = $(this).attr("class").split(' ')[7];
        var targetId = $(this).attr("class").split(' ')[0];
        console.log(checkCare);
        $.ajax({
            type: "POST",
            url: "/reaction",
            data: {
                target_type: 'EVENT',
                target_id: targetId,
                reaction_type: 'CARE'
            },
            success: function (data){
                if(data.status == 200){
                    $.ajax({
                        type: "GET",
                        url: "/reaction/exist",
                        data: {
                            target_type: 'EVENT',
                            target_id: targetId,
                            reaction_type: 'CARE'
                        },
                        success: function (data) {
                            if(data == true){
                                if(language == 'vi'){
                                    document.getElementById(btnCareId).innerText = "Đã quan tâm";
                                }else {
                                    document.getElementById(btnCareId).innerText = "Cared";
                                }
                            }else{
                                if(language == 'vi'){
                                    document.getElementById(btnCareId).innerText = "Quan tâm";
                                }else {
                                    document.getElementById(btnCareId).innerText = "Care";
                                }
                            }
                        },
                    })
                }
            }
        });
    })

    $(".btnRegister").click(function (){
        var btnRegisterId = $(this).attr("id");
        var checkRegister = $(this).attr("class").split(' ')[11];
        console.log(checkRegister);
        if(checkRegister == 'notRegister'){
            if(language == 'vi'){
                document.getElementById(btnRegisterId).innerText = "Đã đăng ký";
                $('#'+btnRegisterId).removeClass('notRegister');
            }else {
                document.getElementById(btnRegisterId).innerText = "Registered";
                $('#'+btnRegisterId).removeClass('notRegister');
            }

        }else {
            if(language == 'vi'){
                document.getElementById(btnRegisterId).innerText = "Đăng ký";
                $('#'+btnRegisterId).addClass('notRegister');
            }else{
                document.getElementById(btnRegisterId).innerText = "Register";
                $('#'+btnRegisterId).addClass('notRegister');
            }
        }
    })
})
