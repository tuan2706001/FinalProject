$(window).on('load',function (){
    $.ajax({
        type: "GET",
        url: "/reaction/exist",
        data: {
            target_type: $("#targetType").val(),
            target_id: $("#userId").val(),
            reaction_type: 'LIKE'
        },
        success: function (data) {
            if(data == true){
                if($("#language").val() == "vi"){
                    document.getElementById("buttonLike").innerText = "Đã thích";
                    document.getElementById("buttonLike").style.color = "#007DC5";
                }else {
                    document.getElementById("buttonLike").innerText = "Liked";
                    document.getElementById("buttonLike").style.color = "#007DC5";
                }

            }else{
                if($("#language").val() == "vi"){
                    document.getElementById("buttonLike").innerText = "Thích";
                    document.getElementById("buttonLike").style.color = "";
                }else{
                    document.getElementById("buttonLike").innerText = "Like";
                    document.getElementById("buttonLike").style.color = "";
                }
            }
        },
    })
    $.ajax({
        type: "GET",
        url: "/reaction/exist",
        data: {
            target_type: $("#targetType").val(),
            target_id: $("#userId").val(),
            reaction_type: 'FOLLOW'
        },
        success: function (data) {
            if(data == true){
                if($("#language").val() == "vi"){
                    document.getElementById("buttonFollow").innerText = "Đã theo dõi";
                    document.getElementById("buttonFollow").style.color = "#007DC5";
                }else {
                    document.getElementById("buttonFollow").innerText = "Followed";
                    document.getElementById("buttonFollow").style.color = "#007DC5";
                }

            }else{
                if($("#language").val() == "vi"){
                    document.getElementById("buttonFollow").innerText = "Theo dõi";
                    document.getElementById("buttonFollow").style.color = "";
                }else{
                    document.getElementById("buttonFollow").innerText = "Follow";
                    document.getElementById("buttonFollow").style.color = "";
                }
            }
        },
    })
});

$(document).ready(function () {
    $("#buttonLike").click(function () {
        $.ajax({
            type: "POST",
            url: "/reaction",
            data: {
                target_type: $("#targetType").val(),
                target_id: $("#userId").val(),
                reaction_type: 'LIKE'
            },
            success: function (data){
                if(data.status == 200){
                    $.ajax({
                        type: "GET",
                        url: "/reaction/exist",
                        data: {
                            target_type: $("#targetType").val(),
                            target_id: $("#userId").val(),
                            reaction_type: 'LIKE'
                        },
                        success: function (data) {
                            if(data == true){
                                if($("#language").val() == "vi"){
                                    document.getElementById("buttonLike").innerText = "Đã thích";
                                    document.getElementById("buttonLike").style.color = "#007DC5";
                                }else {
                                    document.getElementById("buttonLike").innerText = "Liked";
                                    document.getElementById("buttonLike").style.color = "#007DC5";
                                }

                            }else{
                                if($("#language").val() == "vi"){
                                    document.getElementById("buttonLike").innerText = "Thích";
                                    document.getElementById("buttonLike").style.color = "";
                                }else{
                                    document.getElementById("buttonLike").innerText = "Like";
                                    document.getElementById("buttonLike").style.color = "";
                                }
                            }

                            $("#reactOverview").load(location.href+" #reactOverview>*","");
                        },
                    })
                }
            }
        });

    })
    $("#buttonFollow").click(function () {

        $.ajax({
            type: "POST",
            url: "/reaction",
            data: {
                target_type: $("#targetType").val(),
                target_id: $("#userId").val(),
                reaction_type: 'FOLLOW'
            },
            success: function (data){
                if(data.status == 200){
                    $.ajax({
                        type: "GET",
                        url: "/reaction/exist",
                        data: {
                            target_type: $("#targetType").val(),
                            target_id: $("#userId").val(),
                            reaction_type: 'FOLLOW'
                        },
                        success: function (data) {
                            if(data == true){
                                if($("#language").val() == "vi"){
                                    document.getElementById("buttonFollow").innerText = "Đã theo dõi";
                                    document.getElementById("buttonFollow").style.color = "#007DC5";
                                }else {
                                    document.getElementById("buttonFollow").innerText = "Followed";
                                    document.getElementById("buttonFollow").style.color = "#007DC5";
                                }

                            }else{
                                if($("#language").val() == "vi"){
                                    document.getElementById("buttonFollow").innerText = "Theo dõi";
                                    document.getElementById("buttonFollow").style.color = "";
                                }else{
                                    document.getElementById("buttonFollow").innerText = "Follow";
                                    document.getElementById("buttonFollow").style.color = "";
                                }
                            }

                            $("#reactOverview").load(location.href+" #reactOverview>*","");
                        },
                    })
                }
            }
        });

    })
})