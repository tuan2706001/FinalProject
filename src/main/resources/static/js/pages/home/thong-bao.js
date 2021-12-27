$(document).ready(function (){
    if($('.newNotify').length < 1){
        $('#emptyNotify').show();
    }if($('.oldNotify').length < 1){
        $('#emptyNotify2').show();
    }
    if($('.newNotify2').length < 1){
        $('#emptyNotify3').show();
    }if($('.oldNotify2').length < 1){
        $('#emptyNotify4').show();
    }
    if($('.newNotify3').length < 1){
        $('#emptyNotify5').show();
    }if($('.oldNotify3').length < 1){
        $('#emptyNotify6').show();
    }
    if($('.newNotify4').length < 1){
        $('#emptyNotify7').show();
    }if($('.oldNotify4').length < 1){
        $('#emptyNotify8').show();
    }
})

var count = 1;
$('#manageNotify').click(function () {
    count += 1;
    if(count % 2 == 0){
        $('#boxNotify').hide();
        $('#boxConfig').show();
    }else {
        $('#boxNotify').show();
        $('#boxConfig').hide();
    }

})

$('#btnAll').click(function () {
    $('#notifyNotRead').hide();
    $('#notifyAll').show();
    $('#btnAll').removeClass('btnNotRead');
    $('#btnAll').addClass('btnAll');
    $('#btnNotRead').removeClass('btnAll');
    $('#btnNotRead').addClass('btnNotRead');
})
$('#btnAll2').click(function () {
    $('#notifyNotRead2').hide();
    $('#notifyAll2').show();
    $('#btnAll2').removeClass('btnNotRead');
    $('#btnAll2').addClass('btnAll');
    $('#btnNotRead2').removeClass('btnAll');
    $('#btnNotRead2').addClass('btnNotRead');
})

$('#btnNotRead').click(function () {
    $('#notifyAll').hide();
    $('#notifyNotRead').show();
    $('#btnAll').removeClass('btnAll');
    $('#btnAll').addClass('btnNotRead');
    $('#btnNotRead').removeClass('btnNotRead');
    $('#btnNotRead').addClass('btnAll');
})
$('#btnNotRead2').click(function () {
    $('#notifyAll2').hide();
    $('#notifyNotRead2').show();
    $('#btnAll2').removeClass('btnAll');
    $('#btnAll2').addClass('btnNotRead');
    $('#btnNotRead2').removeClass('btnNotRead');
    $('#btnNotRead2').addClass('btnAll');
})
$('.manageNotify').mouseenter(function () {
    $('#svgPath').attr('stroke','white');
    $('#svgPath2').attr('stroke','white');
})

$('.manageNotify').mouseleave(function () {
    $('#svgPath').attr('stroke','black');
    $('#svgPath2').attr('stroke','black');
}).mouseleave();

$('.newNotify').mouseenter(function () {
    var box3dotId = '3dot-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).show();
})

$('.newNotify').mouseleave(function () {
    var box3dotId = '3dot-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).hide();
}).mouseleave();

$('.newNotify2').mouseenter(function () {
    var box3dotId = '3dot2-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).show();
})

$('.newNotify2').mouseleave(function () {
    var box3dotId = '3dot2-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).hide();
}).mouseleave();

$('.newNotify3').mouseenter(function () {
    var box3dotId = '3dotPopup-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).show();
})

$('.newNotify3').mouseleave(function () {
    var box3dotId = '3dotPopup-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).hide();
}).mouseleave();

$('.newNotify4').mouseenter(function () {
    var box3dotId = '3dot2Popup-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).show();
})

$('.newNotify4').mouseleave(function () {
    var box3dotId = '3dot2Popup-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).hide();
}).mouseleave();

$('.oldNotify').mouseenter(function () {
    var box3dotId = '3dot-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).show();
})

$('.oldNotify').mouseleave(function () {
    var box3dotId = '3dot-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).hide();
}).mouseleave();

$('.oldNotify2').mouseenter(function () {
    var box3dotId = '3dot2-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).show();
})

$('.oldNotify2').mouseleave(function () {
    var box3dotId = '3dot2-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).hide();
}).mouseleave();

$('.oldNotify3').mouseenter(function () {
    var box3dotId = '3dotPopup-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).show();
})

$('.oldNotify3').mouseleave(function () {
    var box3dotId = '3dotPopup-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).hide();
}).mouseleave();

$('.oldNotify4').mouseenter(function () {
    var box3dotId = '3dot2Popup-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).show();
})

$('.oldNotify4').mouseleave(function () {
    var box3dotId = '3dot2Popup-' + $(this).attr('class').split(' ')[0];
    $('#'+box3dotId).hide();
}).mouseleave();


$('.btnContinue').click(function () {
    var notifyId = $(this).attr('class').split(' ')[0];
    var path = $(this).attr('class').split(' ')[1];
    var seen = $('.getData-' + notifyId).attr('class').split(' ')[5];

    if(seen !== true) {
        fetch("/thong-bao/seen-notify", {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: Number(notifyId)
        })
            .then(function (data) {
                console.log('Request succeeded with JSON response', data);
                location.href = path;
            })
            .catch(function (error) {
                console.log('Request failed', error);
            });
    }else {
        location.href = path;
    }
})

$('.boxSeenNotify').click(function () {
    var notifyId = $(this).attr('class').split(' ')[0];

    var actionType = $('.getData-' + notifyId).attr('class').split(' ')[0];
    var targetType = $('.getData-' + notifyId).attr('class').split(' ')[1];
    var targetId = $('.getData-' + notifyId).attr('class').split(' ')[2];
    var urlConnectDetail = $('.getData-' + notifyId).attr('class').split(' ')[3];
    var urlViewSolution = $('.getData-' + notifyId).attr('class').split(' ')[4];
    var seen = $('.getData-' + notifyId).attr('class').split(' ')[5];

    var targetName = $('.targetName-' + notifyId).val();

    var pathChiTiet = "/chi-tiet-loi-moi/";
    var pathYeuCau = "/thong-bao/yeu-cau-them-thong-tin/";
    var path = "";

    if ((actionType == 0 || actionType == 1) && targetType == 0){
        path = pathChiTiet + targetId;
    }else if((actionType == 0 || actionType == 1) && targetType == 250){
        path = urlConnectDetail + targetId;
    }else if((actionType == 0 || actionType == 1) && (targetType == 300 && targetName == "createGiaiPhapTraoDoi")){
        path = urlViewSolution + targetId;
    }else if((actionType == 0 || actionType == 1) && targetType == 300){
        path = pathYeuCau + targetId;
    }else{
        path= "";
    }
console.log(seen);
    if(seen != true){
        fetch("/thong-bao/seen-notify", {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: Number(notifyId)
        })
            .then(function (data) {
                console.log('Request succeeded with JSON response', data);
                location.href = path;
            })
            .catch(function (error) {
                console.log('Request failed', error);
            });
    }else {
        location.href = path;
    }


})

$('.btnYes').click(function () {
    var notifyId = $(this).attr('class').split(' ')[0];
    var seen = $('.getData-' + notifyId).attr('class').split(' ')[5];

    if(seen != true) {
        fetch("/thong-bao/seen-notify", {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: Number(notifyId)
        })
            .then(function (data) {
                console.log('Request succeeded with JSON response', data);
                location.href = "/";
            })
            .catch(function (error) {
                console.log('Request failed', error);
            });
    }else{
        location.href = "/";
    }
})
$('.deleteNotify').click(function (e) {
    e.stopPropagation();
    var notifyId = $(this).attr('class').split(' ')[0];
    fetch("/thong-bao/delete-notify", {
        method: 'post',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: Number(notifyId)
    })
        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
            $('.viewNotify-'+notifyId).hide();
        })
        .catch(function (error) {
            console.log('Request failed', error);
        });
})

$(document).ready(function () {
    $.ajax({
        url: "/thong-bao/getConfig-notify",
        method: "GET",
        success: function (data){
                data.object.forEach(x => {
                    switch (x.deviceType) {
                        case 1:
                            if(x.isEnabled == true){
                                $('#webConfig').prop('checked',true);
                            }else{
                                $('#webConfig').prop('checked',false);
                            }
                            break;
                        case 2:
                            if(x.isEnabled == true){
                                $('#mobileConfig').prop('checked',true);
                            }else{
                                $('#mobileConfig').prop('checked',false);
                            }
                            break;
                        case 3:
                            if(x.isEnabled == true){
                                $('#emailConfig').prop('checked',true);
                            }else {
                                $('#emailConfig').prop('checked',false);
                            }
                            break;
                    }

            })
        }
    })

    $('.btnConfig').click(function () {
        var deviceType = $(this).attr('class').split(' ')[0];
        fetch("/thong-bao/toggleConfig-notify", {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: Number(deviceType)
        })
            .then(function (data) {
                console.log('Request succeeded with JSON response', data);
            })
            .catch(function (error) {
                console.log('Request failed', error);
            });
    })
})

window.addEventListener( "pageshow", function ( event ) {
    var historyTraversal = event.persisted ||
        ( typeof window.performance != "undefined" &&
            window.performance.navigation.type === 2 );
    if ( historyTraversal ) {
        // Handle page restore.
        window.location.reload();
    }
});


$(document).ready(function () {
    let FCMCookie = getFCMCookie("FCMCookie");

    function getFCMCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }

    $.ajax({
        url: "/thong-bao/getActive-notify",
        method: "POST",
        data:JSON.stringify({
            firebaseToken: '',
            deviceId: FCMCookie,
        }),
        dataType: "json",
        contentType: "application/json",
        success: function (data){
            if(document.getElementById("onOffNotify") != undefined && document.getElementById("onOffNotify2") != undefined){
                if(data == true){
                    document.getElementById("onOffNotify").innerText = "Tắt thông báo";
                    document.getElementById("onOffNotify2").innerText = "Tắt thông báo";
                }else {
                    document.getElementById("onOffNotify").innerText = "Bật thông báo";
                    document.getElementById("onOffNotify2").innerText = "Bật thông báo";
                }
            }

        }
    })

    $('.threeDot').click(function () {
        $.ajax({
            url: "/thong-bao/setActive-notify",
            method: "POST",
            data:JSON.stringify({
                firebaseToken: '',
                deviceId: FCMCookie,
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data){
                switch (data.status){
                    case 200:
                        $.ajax({
                            url: "/thong-bao/getActive-notify",
                            method: "POST",
                            data:JSON.stringify({
                                firebaseToken: '',
                                deviceId: FCMCookie,
                            }),
                            dataType: "json",
                            contentType: "application/json",
                            success: function (data){
                                if(document.getElementById("onOffNotify") != undefined && document.getElementById("onOffNotify2") != undefined) {
                                    if (data == true) {
                                        document.getElementById("onOffNotify").innerText = "Tắt thông báo";
                                        document.getElementById("onOffNotify2").innerText = "Tắt thông báo";
                                    } else {
                                        document.getElementById("onOffNotify").innerText = "Bật thông báo";
                                        document.getElementById("onOffNotify2").innerText = "Bật thông báo";
                                    }
                                }
                            }
                        })
                        break;
                    case 400:
                        alert(data.message);
                        location.reload();
                }
            }
        })
    })
})

$(document).ready(function () {
    let firstDate = new Date();
    let secondDate = new Date();
    const index = $('.index').val();
    const index2 = $('.index2').val();
    const index3 = $('.index3').val();
    const index4 = $('.index4').val();
    const index5 = $('.index5').val();
    const index6 = $('.index6').val();
    const index7 = $('.index7').val();
    const index8 = $('.index8').val();
    for (let i = 1; i <= index; i++) {
        firstDate = new Date();
        secondDate = new Date($('#createdAt-' + i).val());
        const diffDays = (firstDate - secondDate) / 1000;
        let days = 0;
        if (diffDays <= 86400) {
            if ((diffDays / 60 / 60) < 1) {
                days = Math.round(diffDays / 60)
                document.getElementById('notifyTime-' + i).innerText = days.toString() + ' phút trước';
            } else {
                days = Math.round(diffDays / 60 / 60)
                document.getElementById('notifyTime-' + i).innerText = days.toString() + ' giờ trước';
            }
        } else if (diffDays > 86400 && diffDays < (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24)
            document.getElementById('notifyTime-' + i).innerText = days.toString() + ' ngày trước';
        } else if (diffDays >= (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24 / 30)
            document.getElementById('notifyTime-' + i).innerText = days.toString() + ' tháng trước';
        }

    }
    for (let i = 1; i <= index2; i++) {
        firstDate = new Date();
        secondDate = new Date($('#createdAt2-' + i).val());
        const diffDays = (firstDate - secondDate) / 1000;
        let days = 0;
        if (diffDays <= 86400) {
            if ((diffDays / 60 / 60) < 1) {
                days = Math.round(diffDays / 60)
                document.getElementById('notifyTime2-' + i).innerText = days.toString() + ' phút trước';
            } else {
                days = Math.round(diffDays / 60 / 60)
                document.getElementById('notifyTime2-' + i).innerText = days.toString() + ' giờ trước';
            }
        } else if (diffDays > 86400 && diffDays < (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24)
            document.getElementById('notifyTime2-' + i).innerText = days.toString() + ' ngày trước';
        } else if (diffDays >= (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24 / 30)
            document.getElementById('notifyTime2-' + i).innerText = days.toString() + ' tháng trước';
        }

    }
    for (let i = 1; i <= index3; i++) {
        firstDate = new Date();
        secondDate = new Date($('#createdAt3-' + i).val());
        const diffDays = (firstDate - secondDate) / 1000;
        let days = 0;
        if (diffDays <= 86400) {
            if ((diffDays / 60 / 60) < 1) {
                days = Math.round(diffDays / 60)
                document.getElementById('notifyTime3-' + i).innerText = days.toString() + ' phút trước';
            } else {
                days = Math.round(diffDays / 60 / 60)
                document.getElementById('notifyTime3-' + i).innerText = days.toString() + ' giờ trước';
            }
        } else if (diffDays > 86400 && diffDays < (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24)
            document.getElementById('notifyTime3-' + i).innerText = days.toString() + ' ngày trước';
        } else if (diffDays >= (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24 / 30)
            document.getElementById('notifyTime3-' + i).innerText = days.toString() + ' tháng trước';
        }

    }
    for (let i = 1; i <= index4; i++) {
        firstDate = new Date();
        secondDate = new Date($('#createdAt4-' + i).val());
        const diffDays = (firstDate - secondDate) / 1000;
        let days = 0;
        if (diffDays <= 86400) {
            if ((diffDays / 60 / 60) < 1) {
                days = Math.round(diffDays / 60)
                document.getElementById('notifyTime4-' + i).innerText = days.toString() + ' phút trước';
            } else {
                days = Math.round(diffDays / 60 / 60)
                document.getElementById('notifyTime4-' + i).innerText = days.toString() + ' giờ trước';
            }
        } else if (diffDays > 86400 && diffDays < (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24)
            document.getElementById('notifyTime4-' + i).innerText = days.toString() + ' ngày trước';
        } else if (diffDays >= (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24 / 30)
            document.getElementById('notifyTime4-' + i).innerText = days.toString() + ' tháng trước';
        }

    }for (let i = 1; i <= index5; i++) {
        firstDate = new Date();
        secondDate = new Date($('#createdAt5-' + i).val());
        const diffDays = (firstDate - secondDate) / 1000;
        let days = 0;
        if (diffDays <= 86400) {
            if ((diffDays / 60 / 60) < 1) {
                days = Math.round(diffDays / 60)
                document.getElementById('notifyTime5-' + i).innerText = days.toString() + ' phút trước';
            } else {
                days = Math.round(diffDays / 60 / 60)
                document.getElementById('notifyTime5-' + i).innerText = days.toString() + ' giờ trước';
            }
        } else if (diffDays > 86400 && diffDays < (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24)
            document.getElementById('notifyTime5-' + i).innerText = days.toString() + ' ngày trước';
        } else if (diffDays >= (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24 / 30)
            document.getElementById('notifyTime5-' + i).innerText = days.toString() + ' tháng trước';
        }

    }for (let i = 1; i <= index6; i++) {
        firstDate = new Date();
        secondDate = new Date($('#createdAt6-' + i).val());
        const diffDays = (firstDate - secondDate) / 1000;
        let days = 0;
        if (diffDays <= 86400) {
            if ((diffDays / 60 / 60) < 1) {
                days = Math.round(diffDays / 60)
                document.getElementById('notifyTime6-' + i).innerText = days.toString() + ' phút trước';
            } else {
                days = Math.round(diffDays / 60 / 60)
                document.getElementById('notifyTime6-' + i).innerText = days.toString() + ' giờ trước';
            }
        } else if (diffDays > 86400 && diffDays < (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24)
            document.getElementById('notifyTime6-' + i).innerText = days.toString() + ' ngày trước';
        } else if (diffDays >= (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24 / 30)
            document.getElementById('notifyTime6-' + i).innerText = days.toString() + ' tháng trước';
        }

    }for (let i = 1; i <= index7; i++) {
        firstDate = new Date();
        secondDate = new Date($('#createdAt7-' + i).val());
        const diffDays = (firstDate - secondDate) / 1000;
        let days = 0;
        if (diffDays <= 86400) {
            if ((diffDays / 60 / 60) < 1) {
                days = Math.round(diffDays / 60)
                document.getElementById('notifyTime7-' + i).innerText = days.toString() + ' phút trước';
            } else {
                days = Math.round(diffDays / 60 / 60)
                document.getElementById('notifyTime7-' + i).innerText = days.toString() + ' giờ trước';
            }
        } else if (diffDays > 86400 && diffDays < (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24)
            document.getElementById('notifyTime7-' + i).innerText = days.toString() + ' ngày trước';
        } else if (diffDays >= (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24 / 30)
            document.getElementById('notifyTime7-' + i).innerText = days.toString() + ' tháng trước';
        }

    }for (let i = 1; i <= index8; i++) {
        firstDate = new Date();
        secondDate = new Date($('#createdAt8-' + i).val());
        const diffDays = (firstDate - secondDate) / 1000;
        let days = 0;
        if (diffDays <= 86400) {
            if ((diffDays / 60 / 60) < 1) {
                days = Math.round(diffDays / 60)
                document.getElementById('notifyTime8-' + i).innerText = days.toString() + ' phút trước';
            } else {
                days = Math.round(diffDays / 60 / 60)
                document.getElementById('notifyTime8-' + i).innerText = days.toString() + ' giờ trước';
            }
        } else if (diffDays > 86400 && diffDays < (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24)
            document.getElementById('notifyTime8-' + i).innerText = days.toString() + ' ngày trước';
        } else if (diffDays >= (86400 * 30)) {
            days = Math.round(diffDays / 60 / 60 / 24 / 30)
            document.getElementById('notifyTime8-' + i).innerText = days.toString() + ' tháng trước';
        }

    }

})
var myModalNotify = document.getElementById("myModalNotify");
var btnNotify = document.getElementById("myBtnNotify");
if (btnNotify != undefined){
    btnNotify.onclick = function () {
        myModalNotify.style.display = "block";
    }
}

$(window).click(function (event){
    if (event.target == myModalNotify) {
        myModalNotify.style.display = "none";
    }
})