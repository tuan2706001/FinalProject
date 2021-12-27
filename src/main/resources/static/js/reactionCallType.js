$(document).ready(function () {
    var svgReaction = $('#ReactionIconSVG').html();


    $(".btnCurrentReaction").click(function (){
        var btnCurrentReactionId = $(this).attr("id");
        var class_reaction_type = $("#"+btnCurrentReactionId).attr("class").split(' ')[5];
        var targetId = $(this).attr("class").split(' ')[0];
        var targetType = $(this).attr("class").split(' ')[1];
        var divReactionIcon = 'divReactionIcon_' + $(this).attr("class").split(' ')[0];
        var btnReaction = 'btnReaction_' + $(this).attr("class").split(' ')[0];

        switch(class_reaction_type) {
            case 'reaction_type_like':
                $.ajax({
                    type: "POST",
                    url: "/reaction",
                    data: {
                        target_type: targetType,
                        target_id: targetId,
                        reaction_type: 'LIKE'
                    },
                    success: function (data) {
                        if($("#language").val() == "vi"){
                            document.getElementById(btnReaction).innerText = "Thích";
                            document.getElementById(btnReaction).style.color = "";
                            $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                            $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");

                        }else{
                            document.getElementById(btnReaction).innerText = "Like";
                            document.getElementById(btnReaction).style.color = "";
                            $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                            $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");

                        }
                    }
                })
                break;
            case 'reaction_type_love':
                $.ajax({
                    type: "POST",
                    url: "/reaction",
                    data: {
                        target_type: targetType,
                        target_id: targetId,
                        reaction_type: 'LOVE'
                    },
                    success: function (data) {
                        if($("#language").val() == "vi"){
                            document.getElementById(btnReaction).innerText = "Thích";
                            document.getElementById(btnReaction).style.color = "";
                            $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                            $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");

                        }else{
                            document.getElementById(btnReaction).innerText = "Like";
                            document.getElementById(btnReaction).style.color = "";
                            $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                            $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");

                        }
                    }
                })
                break;
            case 'reaction_type_haha':
                $.ajax({
                    type: "POST",
                    url: "/reaction",
                    data: {
                        target_type: targetType,
                        target_id: targetId,
                        reaction_type: 'SMILE'
                    },
                    success: function (data) {
                        if($("#language").val() == "vi"){
                            document.getElementById(btnReaction).innerText = "Thích";
                            document.getElementById(btnReaction).style.color = "";
                            $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                            $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");

                        }else{
                            document.getElementById(btnReaction).innerText = "Like";
                            document.getElementById(btnReaction).style.color = "";
                            $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                            $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");

                        }
                    }
                })
                break;
            case 'reaction_type_sad':
                $.ajax({
                    type: "POST",
                    url: "/reaction",
                    data: {
                        target_type: targetType,
                        target_id: targetId,
                        reaction_type: 'SAD'
                    },
                    success: function (data) {
                        if($("#language").val() == "vi"){
                            document.getElementById(btnReaction).innerText = "Thích";
                            document.getElementById(btnReaction).style.color = "";
                            $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                            $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");

                        }else{
                            document.getElementById(btnReaction).innerText = "Like";
                            document.getElementById(btnReaction).style.color = "";
                            $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                            $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");

                        }
                    }
                })
                break;
            default:
            break;
        }
    })
    $(".btnIconLike").click(function () {
        var targetId = $(this).attr("class").split(' ')[0];
        var targetType = $(this).attr("class").split(' ')[1];
        var content = $(this).html();
        var divReactionIcon = 'divReactionIcon_' + $(this).attr("class").split(' ')[0];
        var btnReaction = 'btnReaction_' + $(this).attr("class").split(' ')[0];

        $.ajax({
            type: "POST",
            url: "/reaction",
            data: {
                target_type: targetType,
                target_id: targetId,
                reaction_type: 'LIKE'
            },
            success: function (data){
                if(data.status == 200){
                    $.ajax({
                        type: "GET",
                        url: "/reaction/exist",
                        data: {
                            target_type: targetType,
                            target_id: targetId,
                            reaction_type: 'LIKE'
                        },
                        success: function (data) {
                            if(data == true){
                                if($("#language").val() == "vi"){
                                    document.getElementById(btnReaction).innerText = "Đã thích";
                                    document.getElementById(btnReaction).style.color = "#007DC5";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ content + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                    $("#"+btnReaction).addClass("reaction_type_like");
                                }else {
                                    document.getElementById(btnReaction).innerText = "Liked";
                                    document.getElementById(btnReaction).style.color = "#007DC5";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ content + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                    $("#"+btnReaction).addClass("reaction_type_like");
                                }

                            }else{
                                if($("#language").val() == "vi"){
                                    document.getElementById(btnReaction).innerText = "Thích";
                                    document.getElementById(btnReaction).style.color = "";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                }else{
                                    document.getElementById(btnReaction).innerText = "Like";
                                    document.getElementById(btnReaction).style.color = "";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                }
                            }
                        },
                    })
                }
            }
        });

    })
    $(".btnIconHeart").click(function () {
        var targetId = $(this).attr("class").split(' ')[0];
        var targetType = $(this).attr("class").split(' ')[1];
        var content = $(this).html();
        var divReactionIcon = 'divReactionIcon_' + $(this).attr("class").split(' ')[0];
        var btnReaction = 'btnReaction_' + $(this).attr("class").split(' ')[0];
        console.log($("#"+btnReaction).attr("class"));
        $.ajax({
            type: "POST",
            url: "/reaction",
            data: {
                target_type: targetType,
                target_id: targetId,
                reaction_type: 'LOVE'
            },
            success: function (data){
                if(data.status == 200){
                    $.ajax({
                        type: "GET",
                        url: "/reaction/exist",
                        data: {
                            target_type: targetType,
                            target_id: targetId,
                            reaction_type: 'LOVE'
                        },
                        success: function (data) {
                            if(data == true){
                                if($("#language").val() == "vi"){
                                    document.getElementById(btnReaction).innerText = "Yêu thích";
                                    document.getElementById(btnReaction).style.color = "#FF262E";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ content + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                    $("#"+btnReaction).addClass("reaction_type_love");
                                }else {
                                    document.getElementById(btnReaction).innerText = "Love";
                                    document.getElementById(btnReaction).style.color = "#FF262E";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ content + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                    $("#"+btnReaction).addClass("reaction_type_love");
                                }

                            }else{
                                if($("#language").val() == "vi") {
                                    document.getElementById(btnReaction).innerText = "Thích";
                                    document.getElementById(btnReaction).style.color = "";
                                    $("#" + divReactionIcon).replaceWith("<div id='" + divReactionIcon + "'>" + svgReaction + "</div>");
                                    $("#" + btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                }else {
                                    document.getElementById(btnReaction).innerText = "Like";
                                    document.getElementById(btnReaction).style.color = "";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                }
                            }
                        },
                    })
                }
            }
        });

    })
    $(".btnIconHaha").click(function () {
        var targetId = $(this).attr("class").split(' ')[0];
        var targetType = $(this).attr("class").split(' ')[1];
        var content = $(this).html();
        var divReactionIcon = 'divReactionIcon_' + $(this).attr("class").split(' ')[0];
        var btnReaction = 'btnReaction_' + $(this).attr("class").split(' ')[0];
        console.log($("#"+btnReaction).attr("class"));
        $.ajax({
            type: "POST",
            url: "/reaction",
            data: {
                target_type: targetType,
                target_id: targetId,
                reaction_type: 'SMILE'
            },
            success: function (data){
                if(data.status == 200){
                    $.ajax({
                        type: "GET",
                        url: "/reaction/exist",
                        data: {
                            target_type: targetType,
                            target_id: targetId,
                            reaction_type: 'SMILE'
                        },
                        success: function (data) {
                            if(data == true){
                                if($("#language").val() == "vi"){
                                    document.getElementById(btnReaction).innerText = "Haha";
                                    document.getElementById(btnReaction).style.color = "#FCD34D";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ content + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                    $("#"+btnReaction).addClass("reaction_type_haha");
                                }else {
                                    document.getElementById(btnReaction).innerText = "Haha";
                                    document.getElementById(btnReaction).style.color = "#FCD34D";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ content + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                    $("#"+btnReaction).addClass("reaction_type_haha");
                                }
                            }else{
                                if($("#language").val() == "vi"){
                                    document.getElementById(btnReaction).innerText = "Thích";
                                    document.getElementById(btnReaction).style.color = "";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                }else{
                                    document.getElementById(btnReaction).innerText = "Like";
                                    document.getElementById(btnReaction).style.color = "";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                }
                            }
                        },
                    })
                }
            }
        });

    })
    $(".btnIconSad").click(function () {
        var targetId = $(this).attr("class").split(' ')[0];
        var targetType = $(this).attr("class").split(' ')[1];
        var content = $(this).html();
        var divReactionIcon = 'divReactionIcon_' + $(this).attr("class").split(' ')[0];
        var btnReaction = 'btnReaction_' + $(this).attr("class").split(' ')[0];
        console.log($("#"+btnReaction).attr("class"));
        $.ajax({
            type: "POST",
            url: "/reaction",
            data: {
                target_type: targetType,
                target_id: targetId,
                reaction_type: 'SAD'
            },
            success: function (data){
                if(data.status == 200){
                    $.ajax({
                        type: "GET",
                        url: "/reaction/exist",
                        data: {
                            target_type: targetType,
                            target_id: targetId,
                            reaction_type: 'SAD'
                        },
                        success: function (data) {
                            if(data == true){
                                if($("#language").val() == "vi"){
                                    document.getElementById(btnReaction).innerText = "Buồn";
                                    document.getElementById(btnReaction).style.color = "#FCD34D";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ content + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                    $("#"+btnReaction).addClass("reaction_type_sad");
                                }else {
                                    document.getElementById(btnReaction).innerText = "Sad";
                                    document.getElementById(btnReaction).style.color = "#FCD34D";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ content + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                    $("#"+btnReaction).addClass("reaction_type_sad");
                                }

                            }else{
                                if($("#language").val() == "vi"){
                                    document.getElementById(btnReaction).innerText = "Thích";
                                    document.getElementById(btnReaction).style.color = "";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                }else{
                                    document.getElementById(btnReaction).innerText = "Like";
                                    document.getElementById(btnReaction).style.color = "";
                                    $("#"+divReactionIcon).replaceWith("<div id='"+divReactionIcon+"'>"+ svgReaction + "</div>");
                                    $("#"+btnReaction).removeClass("reaction_type_like reaction_type_love reaction_type_haha reaction_type_sad");
                                    
                                }
                            }
                        },
                    })
                }
            }
        });

    })
})