'use strict';

var messageInput = $(".frame-chat");
var messageArea = $('.messageArea');
var connectingElement = $('.connecting');

var stompClient = null;
var chatId = $("#userId").val();
var pageIndex = 0;
var point = 0;

var conversationId = null;
var conversationList = new Array();
var subscription = null;
var publicSubscription = null;

var starFill =
    "<svg class='ml-1 mr-1 star cursor-pointer' width='19' height='19' viewBox='0 0 19 19' fill='none' xmlns='http://www.w3.org/2000/svg'>" +
    "<path d='M4.28912 18.3387C3.83074 18.5738 3.31062 18.1617 3.40324 17.6357L4.38887 12.0188L0.205303 8.03354C-0.185384 7.66067 0.0176783 6.97904 0.541366 6.90542L6.35774 6.07892L8.95124 0.940605C9.18518 0.47748 9.81812 0.47748 10.0521 0.940605L12.6456 6.07892L18.4619 6.90542C18.9856 6.97904 19.1887 7.66067 18.7968 8.03354L14.6144 12.0188L15.6001 17.6357C15.6927 18.1617 15.1726 18.5738 14.7142 18.3387L9.49987 15.6597L4.28793 18.3387H4.28912Z' fill='#FFDA44'/>" +
    "</svg>";

var starEmpty =
    "<svg class='ml-1 mr-1 star cursor-pointer' width='19' height='19' viewBox='0 0 19 19' fill='none' xmlns='http://www.w3.org/2000/svg'>" +
    "<path d='M3.40324 17.6343C3.31062 18.1615 3.83074 18.5736 4.28912 18.3385L9.50224 15.6595L14.7142 18.3385C15.1726 18.5736 15.6927 18.1615 15.6001 17.6355L14.6144 12.0186L18.7968 8.03333C19.1887 7.66046 18.9868 6.97883 18.4619 6.90521L12.6456 6.07871L10.0521 0.940393C10.0026 0.836236 9.9247 0.748236 9.82727 0.686616C9.72984 0.624995 9.61693 0.592285 9.50165 0.592285C9.38637 0.592285 9.27345 0.624995 9.17602 0.686616C9.07859 0.748236 9.00065 0.836236 8.95124 0.940393L6.35774 6.07989L0.541366 6.90639C0.0176783 6.98002 -0.185384 7.66164 0.205303 8.03452L4.38887 12.0198L3.40324 17.6366V17.6343ZM9.22793 14.3485L4.8508 16.5976L5.67493 11.8986C5.69423 11.7905 5.68668 11.6792 5.65293 11.5747C5.61918 11.4701 5.56027 11.3754 5.48137 11.299L2.03049 8.00958L6.84224 7.32558C6.94188 7.31053 7.03638 7.27156 7.11766 7.212C7.19894 7.15244 7.26457 7.07406 7.30893 6.98358L9.49987 2.63971L11.6932 6.98358C11.7375 7.07406 11.8032 7.15244 11.8844 7.212C11.9657 7.27156 12.0602 7.31053 12.1599 7.32558L16.9716 8.00839L13.5207 11.2978C13.4417 11.3744 13.3826 11.4692 13.3489 11.574C13.3151 11.6788 13.3077 11.7903 13.3272 11.8986L14.1513 16.5976L9.77418 14.3485C9.68954 14.3048 9.59569 14.282 9.50046 14.282C9.40523 14.282 9.31138 14.3048 9.22674 14.3485H9.22793Z' fill='#FFDA44'/>" +
    "</svg>";

if ($('#language').val() == 'en'){
    connectingElement.text('Connecting...');
}else{
    connectingElement.text('Đang kết nối...');
}

$(document).ready(function () {
    $.ajaxSetup({
        xhrFields: {
            withCredentials: true
        }
    });

    var stringSearch = location.search.substring(1);
    var partnerIdUrl=stringSearch.split("=")[1];
    $.ajax({
        type: 'POST',
        url: domain + "/api/messenger/get-conversation-by-user-id",
        contentType: "application/json",
        dataType: "json",
        data: {},
        success:function(data) {
            console.log(data);
            var partnerLength = data.length;
            $.each(data, function(i, item) {
                conversationList.push(item.id);
                if(item.logo == null || item.logo == ""){
                    item.logo = "/images/user.png";
                }
                if(item.lastMessageType == 60){
                    if(item.lastUserId == $("#userId").val()){
                        var partnerElement =
                            "<div class='hover:bg-blue-100  flex cursor-pointer partner-element"+(partnerIdUrl != undefined ?(item.id==partnerIdUrl?' bg-blue-100':''):(i===0?' bg-blue-100':''))+"' data-id='"+ item.id +"' data-name='"+ item.title +"' data-org='"+ item.linkId +"' data-close='"+ item.isClosed +"'>" +
                                "<div class='ml-5 my-3 h-12 w-12'>" +
                                    "<img src='"+ item.logo +"' alt=''>" +
                                "</div>" +
                                "<div class='ml-2 my-2'>" +
                                    "<div class='font-bold w-48'>"+ item.title +"</div>" +
                                    "<div class='truncate w-48 text-yellow-500'>"+ item.connectionName +"</div>" +
                                    "<div class='ext-sm text-gray-500 truncate w-48 last-message'>Bạn đã gửi một ảnh</div>" +
                                "</div>" +
                            "</div>"
                        ;
                    }else{
                        var partnerElement =
                            "<div class='hover:bg-blue-100  flex cursor-pointer partner-element"+(partnerIdUrl != undefined ?(item.id==partnerIdUrl?' bg-blue-100':''):(i===0?' bg-blue-100':''))+"' data-id='"+ item.id +"' data-name='"+ item.title +"' data-org='"+ item.linkId +"' data-close='"+ item.isClosed +"'>" +
                                "<div class='ml-5 my-3 h-12 w-12'>" +
                                    "<img src='"+ item.logo +"' alt=''>" +
                                "</div>" +
                                "<div class='ml-2 my-2'>" +
                                    "<div class='font-bold w-48'>"+ item.title +"</div>" +
                                    "<div class='truncate w-48 text-yellow-500'>"+ item.connectionName +"</div>" +
                                    "<div class='ext-sm text-gray-500 truncate w-48 last-message'>"+ String(item.lastUserName) + " đã gửi một ảnh</div>" +
                                "</div>" +
                            "</div>"
                        ;
                    }
                }else{
                    if(item.lastUserId == $("#userId").val()){
                        var partnerElement =
                            "<div class='hover:bg-blue-100  flex cursor-pointer partner-element"+(partnerIdUrl != undefined ?(item.id==partnerIdUrl?' bg-blue-100':''):(i===0?' bg-blue-100':''))+"' data-id='"+ item.id +"' data-name='"+ item.title +"' data-org='"+ item.linkId +"' data-close='"+ item.isClosed +"'>" +
                                "<div class='ml-5 my-3 h-12 w-12'>" +
                                    "<img src='"+ item.logo +"' alt=''>" +
                                "</div>" +
                                "<div class='ml-2 my-2'>" +
                                    "<div class='font-bold w-48'>"+ item.title +"</div>" +
                                    "<div class='truncate w-48 text-yellow-500'>"+ item.connectionName +"</div>" +
                                    "<div class='ext-sm text-gray-500 truncate w-48 last-message'>Bạn: "+ item.lastMessage +"</div>" +
                                "</div>" +
                            "</div>"
                        ;
                    }else{
                        var partnerElement =
                            "<div class='hover:bg-blue-100  flex cursor-pointer partner-element"+(partnerIdUrl != undefined ?(item.id==partnerIdUrl?' bg-blue-100':''):(i===0?' bg-blue-100':''))+"' data-id='"+ item.id +"' data-name='"+ item.title +"' data-org='"+ item.linkId +"' data-close='"+ item.isClosed +"'>" +
                                "<div class='ml-5 my-3 h-12 w-12'>" +
                                    "<img src='"+ item.logo +"' alt=''>" +
                                "</div>" +
                                "<div class='ml-2 my-2'>" +
                                    "<div class='font-bold w-48'>"+ item.title +"</div>" +
                                    "<div class='truncate w-48 text-yellow-500'>"+ item.connectionName +"</div>" +
                                    "<div class='ext-sm text-gray-500 truncate w-48 last-message'>"+ String(item.lastMessage) +"</div>" +
                                "</div>" +
                            "</div>"
                        ;
                    }
                }


                $(".partner").append(partnerElement);

                $(this).fadeOut(200, function() {
                    if(i == partnerLength - 1){
                        conversationId = getParameterByName("conversation");

                        if(conversationId == ""){
                            conversationId = $(".partner div").first().data("id");
                        }

                        $(".partner-title").html("Đối tác liên hệ: <a href='/doi-tac/"+ $(".partner div[data-id='"+ conversationId +"']").data("org") +"'>" + $(".partner div[data-id='"+ conversationId +"']").data("name") + "</a><span class='text-sm text-gray-300 ml-1'>(Click tên đối tác để xem thông tin)</span> ");
                        $('.link-partner').attr('href','/doi-tac/'+ $(".partner div[data-id='"+ conversationId +"']").data("org"));
                        $(".current-partner-name").text($(".partner div[data-id='"+ conversationId +"']").data("name"));
                        $(".current-partner-logo").attr('src', $(".partner div[data-id='"+ conversationId +"'] img").attr('src'));

                        $.ajax({
                            type: 'POST',
                            url: domain + "/api/messenger/get-resource-from-conversation",
                            contentType: "application/json",
                            dataType: "json",
                            data: JSON.stringify({
                                conversationId: conversationId
                            }),
                            success:function(data) {
                                $(".resource-image").text(data.image);
                                $(".resource-video").text(data.video);
                                $(".resource-file").text(data.file);
                                $(".resource-link").text(data.link);
                            }
                        });

                        console.log(conversationId)
                        $.ajax({
                            type: 'POST',
                            url: domain + "/api/messenger/get-message-from-conversation",
                            contentType: "application/json",
                            dataType: "json",
                            data: JSON.stringify({
                                conversationId: conversationId,
                                pageIndex: pageIndex
                            }),
                            success:function(data) {
                                console.log(data);
                                if (item.isClosed){
                                    $('#divChat').hide();
                                }
                                else{
                                    $('#divChat').show();
                                }
                                $.each(data, function(i, item) {
                                    if(item.senderId == 0){
                                        var messageElement = "<li class='table-row w-full'>" +
                                                "<div data-id='" + item.id + "' class='mx-auto p-1 max-w-xs mt-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                    "<div>" +
                                                        // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                        "<div class='whitespace-pre-wrap'>" + urlify(String(item.content)) + "</div>" +
                                                    "</div>" +
                                                "</div>" +
                                            "</li>"
                                        ;
                                    }else{
                                        if(item.type == 50){
                                            // TEXT
                                            if(item.senderId == $("#userId").val()){
                                                var messageElement =
                                                    "<li class='table-row w-full'>" +
                                                        "<div data-id='" + item.id + "' class='float-right p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1' style='background: #35B5FF1F;'>" +
                                                            "<div>" +
                                                                // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                                "<div class='whitespace-pre-wrap'>" + urlify(String(item.content)) + "</div>" +
                                                            "</div>" +
                                                        "</div>" +
                                                    "</li>"
                                                ;
                                            }else{
                                                var messageElement =
                                                    "<li class='table-row w-full'>" +
                                                        "<div data-id='" + item.id + "' class='float-left p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                            "<div>" +
                                                                // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                                "<div class='whitespace-pre-wrap'>" + urlify(String(item.content)) + "</div>" +
                                                            "</div>" +
                                                        "</div>" +
                                                    "</li>"
                                                ;
                                            }
                                        }else if(item.type == 60){
                                            // IMAGE
                                            if(item.senderId == $("#userId").val()){
                                                var messageElement =
                                                    "<li class='table-row w-full'>" +
                                                        "<div data-id='" + item.id + "' style='background: #35B5FF1F;' class='float-right p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                            "<div>" +
                                                                // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                                "<div><img src='" + item.content + "' alt=' '></div>" +
                                                            "</div>" +
                                                        "</div>" +
                                                    "</li>"
                                                ;
                                            }else{
                                                var messageElement =
                                                    "<li class='table-row w-full'>" +
                                                        "<div data-id='" + item.id + "' class='float-left p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                            "<div>" +
                                                                // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                                "<div><img src='" + item.content + "' alt=' '></div>" +
                                                            "</div>" +
                                                        "</div>" +
                                                    "</li>"
                                                ;
                                            }
                                        }

                                    }

                                    messageArea.prepend(messageElement);
                                    $("#overflowChat").animate({ scrollTop: '9000' }, 0);
                                });

                                $.ajax({
                                    type: 'POST',
                                    url: domain + "/api/messenger/get-review",
                                    contentType: "application/json",
                                    dataType: "json",
                                    data: JSON.stringify({
                                        conversationId: conversationId,
                                    }),
                                    success:function(data) {
                                        if(data.canReview == true){
                                            $('#danhGia').removeClass("hidden").addClass("block");
                                            if(data.yourReview != null){
                                                $("#starRate > .star").eq(data.yourReview - 1).prevAll().replaceWith(starFill);
                                                $("#starRate > .star").eq(data.yourReview - 1).nextAll().replaceWith(starEmpty);
                                                $("#starRate > .star").eq(data.yourReview - 1).replaceWith(starFill);
                                            }
                                            if(data.isClosed == true){
                                                $("#divChat").hide();
                                                $("#dangDienRa").text("Đã hoàn thành");
                                                $("#starRate").removeClass("enable");
                                                $("#hoanThanh").removeClass('bg-blue-500 hover:bg-blue-700 enable').addClass('bg-gray-300 hover:bg-gray-500 ');
                                                $(".star").removeClass("cursor-pointer");
                                            }
                                        }
                                    }
                                });
                            }
                        });

                        connect();
                    }

                });

            });
            if(data.length == 0){
                if ($('#language').val() == 'en'){
                    connectingElement.text('No conversation');
                }else{
                    connectingElement.text('Không có kết nối');
                }
            }
        }
    });

    $(".partner").on("click", ".partner-element", function (){
        if ($(this).data("close")){
            $('#divChat').hide();
        }
        else {
            $('#divChat').show();
        }
        window.history.pushState({}, document.title, window.location.pathname);
        $(".partner-title").html("Đối tác liên hệ: <a href='/doi-tac/"+ $(this).data("org") +"'>" + $(this).data("name") + "</a> <span class='text-sm text-gray-300 ml-1'>(Click tên đối tác để xem thông tin)</span>");
        $('.link-partner').attr('href','/doi-tac/'+ $(this).data("org"));
        $(".current-partner-name").text($(this).data("name"));
        $(".current-partner-logo").attr('src',$(this).find("img").attr('src'));
        $(this).find(".last-message").removeClass("font-bold");
        $('#danhGia').removeClass("block").addClass("hidden");
        $("#dangDienRa").text("Đang diễn ra");
        $("#dangDienRa").removeClass('border-gray-300 text-gray-300 hover:border-blue-700').addClass('border-blue-700 text-blue-700 ');
        $("#hoanThanh").removeClass('bg-blue-500 hover:bg-blue-700 enable').addClass('bg-gray-300 hover:bg-gray-500 ');
        $("#starRate").addClass("enable");
        $(".star").addClass("cursor-pointer");
        $("#starRate > .star").replaceWith(starEmpty);
        pageIndex = 0;
        point = 0;
        if($(this).data("id") != conversationId){
            subscription.unsubscribe();
            conversationId = $(this).data("id");
            $(".partner-element").removeClass("bg-blue-100")
            $(this).addClass("bg-blue-100");
            connect();

            $.ajax({
                type: 'POST',
                url: domain + "/api/messenger/get-resource-from-conversation",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    conversationId: conversationId
                }),
                success:function(data) {
                    $(".resource-image").text(data.image);
                    $(".resource-video").text(data.video);
                    $(".resource-file").text(data.file);
                    $(".resource-link").text(data.link);
                }
            });

            $.ajax({
                type: 'POST',
                url: domain + "/api/messenger/get-message-from-conversation",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    conversationId: conversationId,
                    pageIndex: pageIndex
                }),
                success:function(data) {
                    console.log(data);
                    messageArea.empty();
                    $.each(data, function(i, item) {
                        if(item.senderId == 0){
                            var messageElement =
                                "<li class='table-row'>" +
                                    "<div data-id='" + item.id + "' class='mx-auto p-1 max-w-xs mt-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                        "<div>" +
                                            // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                            "<div class='whitespace-pre-wrap'>" + urlify(String(item.content)) + "</div>" +
                                       "</div>" +
                                   "</div>" +
                               "</li>"
                           ;
                        }else{
                            if(item.type == 50){
                                if(item.senderId == $("#userId").val()){
                                    var messageElement =
                                        "<li class='table-row' style='display: block'>" +
                                            "<div data-id='" + item.id + "' style='background: #35B5FF1F;' class='float-right p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                "<div>" +
                                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                    "<div class='whitespace-pre-wrap'>" + urlify(String(item.content)) + "</div>" +
                                                "</div>" +
                                            "</div>" +
                                        "</li>"
                                    ;
                                }else{
                                    var messageElement =
                                        "<li class='table-row' style='display: block'>" +
                                            "<div data-id='" + item.id + "' class='float-left p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                "<div>" +
                                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                    "<div class='whitespace-pre-wrap'>" + urlify(String(item.content)) + "</div>" +
                                                "</div>" +
                                            "</div>" +
                                        "</li>"
                                    ;
                                }
                            }else if(item.type == 60){
                                if(item.senderId == $("#userId").val()){
                                    var messageElement =
                                        "<li class='table-row' style='display: block'>" +
                                            "<div data-id='" + item.id + "' style='background: #35B5FF1F;' class='float-right p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                "<div>" +
                                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                    "<div><img src='" + item.content + "' alt=' '></div>" +
                                                "</div>" +
                                            "</div>" +
                                        "</li>"
                                    ;
                                }else{
                                    var messageElement =
                                        "<li class='table-row' style='display: block'>" +
                                            "<div data-id='" + item.id + "' class='float-left p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                "<div>" +
                                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                    "<div><img src='" + item.content + "' alt=' '></div>" +
                                                "</div>" +
                                            "</div>" +
                                        "</li>"
                                    ;
                                }
                            }

                        }

                        messageArea.prepend(messageElement);
                        $("#overflowChat").animate({ scrollTop: '9000' }, 0);
                    });

                    $.ajax({
                        type: 'POST',
                        url: domain + "/api/messenger/get-review",
                        contentType: "application/json",
                        dataType: "json",
                        data: JSON.stringify({
                            conversationId: conversationId,
                        }),
                        success:function(data) {
                            if(data.canReview == true){
                                $('#danhGia').removeClass("hidden").addClass("block");
                                if(data.yourReview != null){
                                    $("#starRate > .star").eq(data.yourReview - 1).prevAll().replaceWith(starFill);
                                    $("#starRate > .star").eq(data.yourReview - 1).nextAll().replaceWith(starEmpty);
                                    $("#starRate > .star").eq(data.yourReview - 1).replaceWith(starFill);
                                }
                                if(data.isClosed == true){
                                    $("#divChat").hide();
                                    $("#dangDienRa").text("Đã hoàn thành");
                                    $("#starRate").removeClass("enable");
                                    $(".star").removeClass("cursor-pointer");
                                    $("#hoanThanh").removeClass('bg-blue-500 hover:bg-blue-700 enable').addClass('bg-gray-300 hover:bg-gray-500 ');
                                }
                            }
                        }
                    });
                }
            });
        }
    });
})

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

function connect(event) {

    var socket = new SockJS(domain + '/stomp-endpoint');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
    // event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    subscription = stompClient.subscribe('/topic/conversation/'+conversationId, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat/conversation/"+conversationId,
        {},
        JSON.stringify({type: 'REGISTER'})
    )

    connectingElement.remove();
    publicConnect();
}


function onError(error) {
    if ($('#language').val() == 'en'){
        connectingElement.text('Could not connect to server. Please refresh this page to try again!');
        connectingElement.css('color','red');
    }else{
        connectingElement.text('Không thể kết nối đến máy chủ. Vui lòng thử lại sau!');
        connectingElement.css('color','red');
    }

}

function publicConnect() {
    var socket = new SockJS(domain + '/stomp-endpoint');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onPublicConnected, onPublicError);
}

function onPublicConnected() {
    // Subscribe to the Public Topic
    publicSubscription = stompClient.subscribe('/topic/public', onPublicMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat/public",
        {},
        JSON.stringify({type: 'REGISTER'})
    )

    connectingElement.remove();
}


function onPublicError(error) {
    if ($('#language').val() == 'en'){
        connectingElement.text('Could not connect to server. Please refresh this page to try again!');
        connectingElement.css('color','red');
    }else{
        connectingElement.text('Không thể kết nối đến máy chủ. Vui lòng thử lại sau!');
        connectingElement.css('color','red');
    }

}


$(document).ready(function () {
    $('#send').click(function () {
        var messageContent = messageInput.val().trim();

        if(messageContent && stompClient) {
            var chatMessage = {
                content: messageContent,
                type: 'CHAT_TEXT'
            };
            stompClient.send("/app/chat/conversation/"+conversationId, {}, JSON.stringify(chatMessage));
            messageInput.val('');

            var publicChatMessage = {
                content: messageContent,
                type: 'CHAT_TEXT',
                conversationId: conversationId
            };
            stompClient.send("/app/chat/public", {}, JSON.stringify(publicChatMessage));
        }

        if($('#images > .relative').length > 0){
            $('#images > .relative').each(function () {
                var chatMessage = {
                    content: $(this).find('img').attr('src'),
                    type: 'CHAT_IMAGE'
                };
                stompClient.send("/app/chat/conversation/"+conversationId, {}, JSON.stringify(chatMessage));
                $('#images').empty();
            });
        }
    });

    $('#dangDienRa').click(function (e){
        e.preventDefault();
        $('#danhGia').removeClass("block hidden").addClass("hidden");
    });

    $('#starRate').on('click','.star', function (e){
        if($('#starRate').hasClass('enable')){
            e.preventDefault();
            point = $(this).index() + 1;
            $(this).prevAll().replaceWith(starFill);
            $(this).nextAll().replaceWith(starEmpty);
            $(this).replaceWith(starFill);
            $("#dangDienRa").removeClass('border-blue-700 text-blue-700').addClass('border-gray-300 text-gray-300 hover:border-blue-700');
            $("#hoanThanh").removeClass('bg-gray-300 hover:bg-gray-500').addClass('bg-blue-500 hover:bg-blue-700 enable');
        }
    });

    $('#danhGia').on('click', '#hoanThanh.enable', function (e){
        e.preventDefault();
        console.log(point);
        if(point > 0){
            $.ajax({
                type: 'POST',
                url: domain + "/api/messenger/review",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    conversationId: conversationId,
                    point: point
                }),
                success:function(data) {
                    if(data.isClosed == true){
                        $("#divChat").hide();
                        $("#dangDienRa").text("Đã hoàn thành");
                        $("#starRate").removeClass("enable");
                        $("#hoanThanh").removeClass('bg-blue-500 hover:bg-blue-700 enable').addClass('bg-gray-300 hover:bg-gray-500 ');
                    }
                }
            });
        }
    })
    messageArea.scroll(function(e) {
        e.preventDefault();
        var pos = $("#overflowChat").scrollTop();
        if (pos == 0) {
            pageIndex += 1;
            console.log(pageIndex);
            $.ajax({
                type: 'POST',
                url: domain + "/api/messenger/get-message-from-conversation",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    conversationId: conversationId,
                    pageIndex: pageIndex
                }),
                success:function(data) {
                    console.log(data);
                    $.each(data, function(i, item) {
                        if(item.senderId == 0){
                            var messageElement =
                                "<li class='table-row'>" +
                                    "<div data-id='" + item.id + "' class='mx-auto p-1 max-w-xs mt-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                        "<div>" +
                                            // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                            "<div class='whitespace-pre-wrap'>" + urlify(String(item.content)) + "</div>" +
                                        "</div>" +
                                    "</div>" +
                                "</li>"
                            ;
                        }else{
                            if(item.type == 50){
                                if(item.senderId == $("#userId").val()){
                                    var messageElement =
                                        "<li class='table-row' style='display: block'>" +
                                            "<div data-id='" + item.id + "' style='background: #35B5FF1F;' class='float-right p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                "<div>" +
                                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                    "<div class='whitespace-pre-wrap'>" + urlify(String(item.content)) + "</div>" +
                                                "</div>" +
                                            "</div>" +
                                        "</li>"
                                    ;
                                }else{
                                    var messageElement =
                                        "<li class='table-row' style='display: block'>" +
                                            "<div data-id='" + item.id + "' class='float-left p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                "<div>" +
                                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                    "<div class='whitespace-pre-wrap'>" + urlify(String(item.content)) + "</div>" +
                                                "</div>" +
                                            "</div>" +
                                        "</li>"
                                    ;
                                }
                            }else if(item.type == 60){
                                if(item.senderId == $("#userId").val()){
                                    var messageElement =
                                        "<li class='table-row' style='display: block'>" +
                                            "<div data-id='" + item.id + "' style='background: #35B5FF1F;' class='float-right p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                "<div>" +
                                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                    "<div><img src='" + item.content + "' alt=' '></div>" +
                                                "</div>" +
                                            "</div>" +
                                        "</li>"
                                    ;
                                }else{
                                    var messageElement =
                                        "<li class='table-row' style='display: block'>" +
                                            "<div data-id='" + item.id + "' class='float-left p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                                "<div>" +
                                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                                    "<div><img src='" + item.content + "' alt=' '></div>" +
                                                "</div>" +
                                            "</div>" +
                                        "</li>"
                                    ;
                                }
                            }

                        }

                        messageArea.prepend(messageElement);
                    });
                }
            });
        }
    });
})

function onPublicMessageReceived(payload){
    var message = JSON.parse(payload.body);

    if(conversationList.length > 0){
        var bool = conversationList.some(function(conversationItem) {
            return message.conversationId == conversationItem;
        });
        if(bool == true){
            if(message.conversationId != conversationId){
                if(message.type === 'CHAT_IMAGE'){
                    $(".partner div[data-id='"+ message.conversationId +"'] .last-message").html(message.senderName + " đã gửi một ảnh");
                    $(".partner div[data-id='"+ message.conversationId +"'] .last-message").addClass("font-bold");
                    $(".partner div[data-id='"+ message.conversationId +"']").prependTo(".partner");
                }else{
                    $(".partner div[data-id='"+ message.conversationId +"'] .last-message").html(message.senderName + ": " + String(message.content));
                    $(".partner div[data-id='"+ message.conversationId +"'] .last-message").addClass("font-bold");
                    $(".partner div[data-id='"+ message.conversationId +"']").prependTo(".partner");
                }
            }
        }
    }

}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    if(message.type === 'CHAT_IMAGE'){
        if(message.senderId == $("#userId").val()){
            $(".partner div[data-id='"+ message.conversationId +"'] .last-message").text("Bạn đã gửi một ảnh");
        }else{
            $(".partner div[data-id='"+ message.conversationId +"'] .last-message").text(message.senderName + " đã gửi một ảnh");
        }
    }else{
        if(message.senderId == $("#userId").val()){
            $(".partner div[data-id='"+ message.conversationId +"'] .last-message").html("Bạn:" + String(message.content));
        }else{
            $(".partner div[data-id='"+ message.conversationId +"'] .last-message").html(message.senderName + ": " + String(message.content));
        }
    }

    // CHuyển lên đầu
    $(".partner div[data-id='"+ message.conversationId +"']").prependTo(".partner");
    // var messageElement = document.createElement('div');
    if(message.type !== 'REGISTER') {
        if(message.type === 'JOIN') {
            // messageElement.classList.add('event-message');
            message.content = message.senderName + ' joined!';
        } else if (message.type === 'LEAVE') {
            // messageElement.classList.add('event-message');
            message.content = message.senderName + ' left!';
        } else {
            if(message.type === 'CHAT_TEXT') {
                if($("#userId").val() == message.senderId){
                    var messageElement =
                        "<li class='table-row'>" +
                            "<div data-id='" + message.id + "' style='background: #35B5FF1F;' class='float-right p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                "<div>" +
                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                    "<div class='whitespace-pre-wrap'>" + urlify(String(message.content)) + "</div>" +
                                "</div>" +
                            "</div>" +
                        "</li>"
                    ;
                }else{
                    var messageElement =
                        "<li class='table-row'>" +
                            "<div data-id='" + message.id + "' class='float-left p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                "<div>" +
                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                    "<div class='whitespace-pre-wrap'>" + urlify(String(message.content)) + "</div>" +
                                "</div>" +
                            "</div>" +
                        "</li>"
                    ;
                }
            }else if(message.type === 'CHAT_IMAGE') {
                if($("#userId").val() == message.senderId){
                    var messageElement =
                        "<li class='table-row'>" +
                            "<div data-id='" + message.id + "'  style='background: #35B5FF1F;' class='float-right p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                "<div>" +
                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                    "<div><img src='" + message.content + "' alt=''></div>" +
                                "</div>" +
                            "</div>" +
                        "</li>"
                    ;
                }else{
                    var messageElement =
                        "<li class='table-row'>" +
                            "<div data-id='" + message.id + "' class='float-left p-1 max-w-xs m-2 bg-white rounded-xl shadow-md flex items-center space-x-1'>" +
                                "<div>" +
                                    // "<div class='text-xl font-medium text-black'>" + message.senderName + "</div>" +
                                    "<div><img src='" + message.content + "' alt=''></div>" +
                                "</div>" +
                            "</div>" +
                        "</li>"
                    ;
                }
            }

            messageArea.append(messageElement);
            $("#overflowChat").animate({ scrollTop: '9000' }, 0);
        }
        messageArea.scrollTop = messageArea.scrollHeight;
    }
}

function urlify(text) {
    var urlRegex = /(https?:\/\/[^\s]+)/g;
    return text.replace(urlRegex, function(url) {
        return '<a class="underline text-blue-600 hover:text-blue-800 visited:text-purple-600" href="' + url + '">' + url + '</a>';
    })
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)");
    var results = regex.exec(location.search);

    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

//--------------------------------

var images = $("#images");
function uploadFile(obj) {
    let file = document.getElementById("inputUpload");
    var fileName = file.value,
        idxDot = fileName.lastIndexOf(".") + 1,
        extFile = fileName.substr(idxDot, fileName.length).toLowerCase();
    // ifjf
    if (extFile == 'png' || extFile == 'jpg' || extFile == 'gif' || extFile == 'jpeg') {
        // Get form
        var form = $('#fileUploadForm')[0];

        var data = new FormData(form);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/file/upload",
            data: data,

            // prevent jQuery from automatically transforming the data into a query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 10000000,
            success: function (data, textStatus, jqXHR) {
                images.append("<div class='relative'><img src='" + data.url + "' class='w-12 h-12 rounded-lg mx-1 mt-1'/><div onclick='closeImage(this)' class='closeImage hover:text-gray-700 cursor-pointer text-gray-400 absolute top-0 right-3 h-2 w-2'>&times;</div><input type='text' name='' class='hidden' value='" + data.url + "'/></div>");
                $('#inputUpload').val('');
            },
        });
    } else {
        if ($('#language').val() == 'en') {
            alert("Only jpg / jpeg / png and gif files are allowed!");
        } else {
            alert("Chỉ cho phép các tệp jpg / jpeg / png và gif!");
        }
        file.value = "";
    }
};
$("#uploadImage").click(function () {
    $("#inputUpload").click();
});


// $(document).ready(function () {
//     const url = "";
//     $('#send').click(function () {
//         $.ajax({
//             type: "POST",
//             url: url,
//             data: {
//                 chat: $(".frame-chat").val(),
//                 user_id: $("#userId").val(),
//                 partner_id: $("#partnerId").val(),
//             },
//         });
//     });
// })

function closeImage(obj) {
    obj.parentElement.remove();
    $('#inputUpload').val('');
}

$("#formatText").click(function (){
    var plainText = $(".frame-chat").val();
    if ($(".frame-chat").attr("id") == "editor1"){
        var data =  CKEDITOR.instances.editor1.editable().getText();
        CKEDITOR.instances.editor1.destroy();
        // $(".frame-chat").attr("style","");
        $(".frame-chat").attr("id","textchat");
        $(".frame-chat").val(data);
    }else{
        $(".frame-chat").attr("id","editor1");
        $(".frame-chat").val(plainText);
        CKEDITOR.replace( 'editor1', {
            customConfig: '',
            initiated : true,
            removePlugins: 'elementspath',
            resize_enabled: false,
            forcePasteAsPlainText: true,
            toolbar: [
                ['Styles','Format','Font','FontSize'],
                ['Bold','Italic','Underline','StrikeThrough','-','Undo','Redo','-','Cut','Copy','Paste','Find','Replace','-','Outdent','Indent','-','Print'],
                ['NumberedList','BulletedList','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
                ['-','Link','Smiley','TextColor','BGColor']
            ],
            height: "4rem",
            width: "100%"
        });
    }

})
function uploadFilePdf(obj) {

    // Get form
    var formPdf = $('#fileUploadPdfForm')[0];

    var dataPdf = new FormData(formPdf);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/file/upload",
        data: dataPdf,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function (data, textStatus, jqXHR) {
            $("#Pdf").attr("src", data.url);
            $("#urlPdf").val(data.url);
            $("#submitButton").prop("disabled", false);
            $('#fileUploadPdfForm')[0].reset();
            // document.getElementById('avatar').src = URL.createObjectURL(obj.files[0]);
        },
        error: function (e){
            console.log(e)
        },
    });
}
$( "#uploadPdf" ).click(function() {
    $( "#inputUploadPdf" ).click();
});

var txtArea=document.getElementById("textchat") ;
function sendform() {
    $('#send').click();
    $("#overflowChat").animate({ scrollTop: '9000' }, 0);
    $("#textchat").val("");
    txtArea.rows="1";
}
$("#textchat").keypress(function (e) {
    if (e.keyCode == 13){
        if(e.shiftKey){
            // txtArea.value +=  '\r\n';
            txtArea.onkeydown(function (e){
                e.preventDefault();
            })
            return ;
        }else{
            sendform();
        }
    }
    if (e.keyCode != 13){
        return;
    }
    return false;
});
