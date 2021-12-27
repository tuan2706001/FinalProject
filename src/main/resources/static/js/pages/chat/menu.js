$(document).ready(function (){
    var menu_cx = $('#menu_cx');
    var menu_dv = $('#menu_dv');
    var menu_c = $('#menu_c');
    var menu_at = $('#menu_at');
    var menu_dv_tn = $('#menu_dv_tn');
    var menu_hd = $('#menu_hd');
    var menu_xc = $('#menu_xc');
    var menu_bt = $('#menu_bt');

    var do_vat = $('#do_vat');
    var cam_xuc = $('#cam_xuc');
    var co = $('#co');
    var am_thuc = $('#am_thuc');
    var dong_vat_thien_nhien = $('#dong_vat_thien_nhien');
    var hoat_dong = $('#hoat_dong');
    var xe_co = $('#xe_co');
    var bieu_tuong = $('#bieu_tuong');

    menu_cx.click(function () {
        cam_xuc.removeClass('hidden');
        do_vat.addClass('hidden');
        co.addClass('hidden');
        am_thuc.addClass('hidden');
        dong_vat_thien_nhien.addClass('hidden');
        hoat_dong.addClass('hidden');
        xe_co.addClass('hidden');
        bieu_tuong.addClass('hidden');
    })
    menu_dv.click(function () {
        do_vat.removeClass('hidden');
        co.addClass('hidden');
        cam_xuc.addClass('hidden');
        am_thuc.addClass('hidden');
        dong_vat_thien_nhien.addClass('hidden');
        hoat_dong.addClass('hidden');
        xe_co.addClass('hidden');
        bieu_tuong.addClass('hidden');
    })
    menu_c.click(function () {
        co.removeClass('hidden');
        do_vat.addClass('hidden');
        cam_xuc.addClass('hidden');
        am_thuc.addClass('hidden');
        dong_vat_thien_nhien.addClass('hidden');
        hoat_dong.addClass('hidden');
        xe_co.addClass('hidden');
        bieu_tuong.addClass('hidden');
    })
    menu_at.click(function () {
        am_thuc.removeClass('hidden');
        do_vat.addClass('hidden');
        cam_xuc.addClass('hidden');
        co.addClass('hidden');
        dong_vat_thien_nhien.addClass('hidden');
        hoat_dong.addClass('hidden');
        xe_co.addClass('hidden');
        bieu_tuong.addClass('hidden');
    })
    menu_dv_tn.click(function () {
        dong_vat_thien_nhien.removeClass('hidden');
        do_vat.addClass('hidden');
        cam_xuc.addClass('hidden');
        am_thuc.addClass('hidden');
        co.addClass('hidden');
        hoat_dong.addClass('hidden');
        xe_co.addClass('hidden');
        bieu_tuong.addClass('hidden');
    })
    menu_hd.click(function () {
        hoat_dong.removeClass('hidden');
        do_vat.addClass('hidden');
        cam_xuc.addClass('hidden');
        am_thuc.addClass('hidden');
        dong_vat_thien_nhien.addClass('hidden');
        co.addClass('hidden');
        xe_co.addClass('hidden');
        bieu_tuong.addClass('hidden');
    })
    menu_xc.click(function () {
        xe_co.removeClass('hidden');
        do_vat.addClass('hidden');
        cam_xuc.addClass('hidden');
        am_thuc.addClass('hidden');
        dong_vat_thien_nhien.addClass('hidden');
        hoat_dong.addClass('hidden');
        co.addClass('hidden');
        bieu_tuong.addClass('hidden');
    })
    menu_bt.click(function () {
        bieu_tuong.removeClass('hidden');
        do_vat.addClass('hidden');
        cam_xuc.addClass('hidden');
        am_thuc.addClass('hidden');
        dong_vat_thien_nhien.addClass('hidden');
        hoat_dong.addClass('hidden');
        xe_co.addClass('hidden');
        co.addClass('hidden');
    })
});
$('.menuicon').click(function (){
    $('.menuicon').removeClass('menu_icon2').addClass(' menu_icon menu_icon1 menu_icon2')
    $(this).removeClass('menu_icon1 menu_icon2').addClass('menu_icon menu_icon2')
})
