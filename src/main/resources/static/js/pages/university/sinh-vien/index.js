if (getCookie('flag') == undefined || getCookie('flag') == null) {
    setCookie('flag', false, 1)
}
if (window.location.pathname.indexOf('/sinh-vien/thuc-tap/') === -1){
    setCookie('flag',false,1)
}
if (window.location.pathname.indexOf('/sinh-vien-thuc-tap/') === 0){
    setCookie('flag',true,1)
}
if (window.location.pathname.indexOf('/sinh-vien/do-an/') === -1){
    setCookie('flag1',false,1)
}
if (window.location.pathname.indexOf('/sinh-vien/do-an/') === 0){
    setCookie('flag1',true,1)
}
if (getCookie('flag')=='true') {
    $('#down').addClass('block');
    $('#down').removeClass('hidden');
    $('#right').addClass('hidden');
    $('#right').removeClass('block');
    $('#menuConDanhMuc').removeClass('hidden');
    $('#menuConDanhMuc').addClass('block');
} else {
    $('#down').addClass('hidden');
    $('#down').removeClass('block');
    $('#right').addClass('block');
    $('#right').removeClass('hidden');
    $('#menuConDanhMuc').removeClass('block');
    $('#menuConDanhMuc').addClass('hidden');
}
jQuery.fn.extend({
    setMenu: function () {
        return this.each(function () {
            var containermenu = $(this);

            var itemmenu = containermenu.find('.xtlab-ctmenu-item');
            itemmenu.click(function () {
                var submenuitem = containermenu.find('.xtlab-ctmenu-sub');
                submenuitem.slideToggle(200);
                if (getCookie('flag') == 'true') {
                    $('#down').addClass('hidden');
                    $('#down').removeClass('block');
                    $('#right').addClass('block');
                    $('#right').removeClass('hidden');
                    setCookie('flag', false, 1);
                } else {
                    $('#down').addClass('block');
                    $('#down').removeClass('hidden');
                    $('#right').addClass('hidden');
                    $('#right').removeClass('block');
                    setCookie('flag', true, 1);
                }
            });
        });
    },
});
$('.xt-ct-menu').setMenu();

if (getCookie('flag1') == undefined || getCookie('flag1') == null) {
    setCookie('flag1', false, 1)
}
if (getCookie('flag1') == 'true') {
    $('#down1').addClass('block');
    $('#down1').removeClass('hidden');
    $('#right1').addClass('hidden');
    $('#right1').removeClass('block');
    $('#menuConDoAn').removeClass('hidden');
} else {
    $('#down1').addClass('hidden');
    $('#down1').removeClass('block');
    $('#right1').addClass('block');
    $('#right1').removeClass('hidden');
}
jQuery.fn.extend({
    setMenu1: function () {
        return this.each(function () {
            var containermenu1 = $(this);

            var itemmenu1 = containermenu1.find('.xtlab-ctmenu-item1');
            itemmenu1.click(function () {
                var submenuitem1 = containermenu1.find('.xtlab-ctmenu-sub1');
                submenuitem1.slideToggle(200);
                if (getCookie('flag1') == 'true') {
                    $('#down1').addClass('hidden');
                    $('#down1').removeClass('block');
                    $('#right1').addClass('block');
                    $('#right1').removeClass('hidden');
                    setCookie('flag1', false, 1);
                } else {
                    $('#down1').addClass('block');
                    $('#down1').removeClass('hidden');
                    $('#right1').addClass('hidden');
                    $('#right1').removeClass('block');
                    setCookie('flag1', true, 1);
                }

            });
        });
    },
});
$('.xt-ct-menu1').setMenu1();
