if (getCookie('flagAdmin') == undefined || getCookie('flagAdmin') == null) {
    setCookie('flagAdmin', false, 1)
}
if (window.location.pathname.indexOf('/admin/quan-tri-tai-khoan/') === -1){
    setCookie('flagAdmin',false,1)
}
if (window.location.pathname.indexOf('/admin/quan-tri-tai-khoan/') === 0){
    setCookie('flagAdmin',true,1)
}
if (getCookie('flagAdmin')=='true') {
    $('#down').addClass('block');
    $('#down').removeClass('hidden');
    $('#right').addClass('hidden');
    $('#right').removeClass('block');
    $('#menuConTaiKhoan').removeClass('hidden');
    $('#menuConTaiKhoan').addClass('block');
} else {
    $('#down').addClass('hidden');
    $('#down').removeClass('block');
    $('#right').addClass('block');
    $('#right').removeClass('hidden');
    $('#menuConTaiKhoan').removeClass('block');
    $('#menuConTaiKhoan').addClass('hidden');
}
jQuery.fn.extend({
    setMenu: function () {
        return this.each(function () {
            let containermenu = $(this);

            let itemmenu = containermenu.find('.xtlab-ctmenu-item');
            itemmenu.click(function () {
                let submenuitem = containermenu.find('.xtlab-ctmenu-sub');
                submenuitem.slideToggle(200);
                if (getCookie('flagAdmin') == 'true') {
                    $('#down').addClass('hidden');
                    $('#down').removeClass('block');
                    $('#right').addClass('block');
                    $('#right').removeClass('hidden');
                    setCookie('flagAdmin', false, 1);
                } else {
                    $('#down').addClass('block');
                    $('#down').removeClass('hidden');
                    $('#right').addClass('hidden');
                    $('#right').removeClass('block');
                    setCookie('flagAdmin', true, 1);
                }
            });
        });
    },
});
$('.xt-ct-menu').setMenu();

// cung-cầu
if (getCookie('flagCungCau') == undefined || getCookie('flagCungCau') == null) {
    setCookie('flagCungCau', false, 1)
}
if (window.location.pathname.indexOf('/admin/quan-tri-cung-cau/') === -1){
    setCookie('flagCungCau',false,1)
}
if (window.location.pathname.indexOf('/admin/quan-tri-cung-cau/') === 0){
    setCookie('flagCungCau',true,1)
}
if (getCookie('flagCungCau')=='true') {
    $('#down1').addClass('block');
    $('#down1').removeClass('hidden');
    $('#right1').addClass('hidden');
    $('#right1').removeClass('block');
    $('#menuConCungCau').removeClass('hidden');
    $('#menuConCungCau').addClass('block');
} else {
    $('#down1').addClass('hidden');
    $('#down1').removeClass('block');
    $('#right1').addClass('block');
    $('#right1').removeClass('hidden');
    $('#menuConCungCau').removeClass('block');
    $('#menuConCungCau').addClass('hidden');
}
jQuery.fn.extend({
    setMenu1: function () {
        return this.each(function () {
            let containermenu = $(this);

            let itemmenu = containermenu.find('.xtlab-ctmenu-item1');
            itemmenu.click(function () {
                let submenuitem = containermenu.find('.xtlab-ctmenu-sub1');
                submenuitem.slideToggle(200);
                if (getCookie('flagCungCau') == 'true') {
                    $('#down1').addClass('hidden');
                    $('#down1').removeClass('block');
                    $('#right1').addClass('block');
                    $('#right1').removeClass('hidden');
                    setCookie('flagCungCau', false, 1);
                } else {
                    $('#down1').addClass('block');
                    $('#down1').removeClass('hidden');
                    $('#right1').addClass('hidden');
                    $('#right1').removeClass('block');
                    setCookie('flagCungCau', true, 1);
                }
            });
        });
    },
});
$('.xt-ct-menu1').setMenu1();


// vinet
if (getCookie('flagVINET') == undefined || getCookie('flagVINET') == null) {
    setCookie('flagVINET', false, 1)
}
if (window.location.pathname.indexOf('/admin/quan-tri-vinet/') === -1){
    setCookie('flagVINET',false,1)
}
if (window.location.pathname.indexOf('/admin/quan-tri-vinet/') === 0){
    setCookie('flagVINET',true,1)
}
if (getCookie('flagVINET')=='true') {
    $('#down2').addClass('block');
    $('#down2').removeClass('hidden');
    $('#right2').addClass('hidden');
    $('#right2').removeClass('block');
    $('#menuConVINET').removeClass('hidden');
    $('#menuConVINET').addClass('block');
} else {
    $('#down2').addClass('hidden');
    $('#down2').removeClass('block');
    $('#right2').addClass('block');
    $('#right2').removeClass('hidden');
    $('#menuConVINET').removeClass('block');
    $('#menuConVINET').addClass('hidden');
}
jQuery.fn.extend({
    setMenu2: function () {
        return this.each(function () {
            let containermenu = $(this);

            let itemmenu = containermenu.find('.xtlab-ctmenu-item2');
            itemmenu.click(function () {
                let submenuitem = containermenu.find('.xtlab-ctmenu-sub2');
                submenuitem.slideToggle(200);
                if (getCookie('flagVINET') == 'true') {
                    $('#down2').addClass('hidden');
                    $('#down2').removeClass('block');
                    $('#right2').addClass('block');
                    $('#right2').removeClass('hidden');
                    setCookie('flagVINET', false, 1);
                } else {
                    $('#down2').addClass('block');
                    $('#down2').removeClass('hidden');
                    $('#right2').addClass('hidden');
                    $('#right2').removeClass('block');
                    setCookie('flagVINET', true, 1);
                }
            });
        });
    },
});
$('.xt-ct-menu2').setMenu2();

