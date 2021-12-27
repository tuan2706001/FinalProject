$(document).ready(function (){
    $(".care").click(function (){
        var care = $(this).attr('id');
        care = $("#"+care);
        var redirect = "<!DOCTYPE html>";
        var targetId = $(this).data('id');
        $.ajax({
            type: "POST",
            url: "/reaction",
            data:{
                target_type: 'EVENT',
                target_id: targetId,
                reaction_type: 'CARE'
            },
            success: function (data){
                    if(typeof data == "string"){
                        if(data.search(redirect) != -1){
                            location.replace("/user/login");
                        }
                    }
                    if (data.status == 200){
                        $.ajax({
                            url : "/reaction/exist",
                            type: "GET",
                            data:{
                                target_type: 'EVENT',
                                target_id: targetId,
                                reaction_type: 'CARE'
                            },
                            success: function (data) {
                                if(data == true){
                                    if($("#language").val() == "vi"){
                                        care.text("Đã quan tâm");
                                        care.css("color:#007DC5");
                                    }else {
                                        care.text("Cared");
                                        care.css("color:#007DC5");
                                    }
                                }else{
                                    if($("#language").val() == "vi"){
                                        care.text("Quan tâm");
                                        care.css("color:#007DC5");
                                    }else{
                                        care.text("Care");
                                        care.css("color:#007DC5");
                                    }
                                }
                            },
                        })
                    }
            },
            // error: function (data){
            //     if(data.indexOf("<!DOCTYPE html>") != -1){
            //        location.replace("/user/login");
            //        alert(1);
            //     }
            // }
        });

    });
});