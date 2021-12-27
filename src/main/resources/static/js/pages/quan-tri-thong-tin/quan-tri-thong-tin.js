if(getCookie("notifyInformation")=="true"){
    $('#notify').removeClass('hidden');
    $('#notify').addClass('block');
    setCookie('notifyInformation',false,0)
}
CKEDITOR.replace("gioithieu");
var listField = [];
var listDemand = [];
var listSupply = [];
$(document).ready(function (){
    $.ajax({
        url: '/ajax/current/org-field',
        method: 'GET',
        success: function (data){
            $.each(data.object, function (index, item){
                listField.push(item.fieldId);
            })
            $(".field").val(listField).trigger('change');
        }
    })
    $.ajax({
        url: '/ajax/current/org-demand',
        method: 'GET',
        success: function (data){
            $.each(data.object, function (index, item){
                listDemand.push(item.demandId);
            })
            $(".demand").val(listDemand).trigger('change');
        }
    })
    $.ajax({
        url: '/ajax/current/org-supply',
        method: 'GET',
        success: function (data){
            $.each(data.object, function (index, item){
                listSupply.push(item.supplyId);
            })
            $(".supply").val(listSupply).trigger('change');
        }
    })
})

$(".nation").select2({
    placeholder: "Chọn quốc gia"
});
$(".city").select2({
    placeholder: "Chọn thành phố"
});
var listDemands = [];
var listSupplies = [];
var listFields = [];

var regex = /\b\S*[AĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴAĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴAĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴAĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴAĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴAĂÂÁẮẤÀẰẦẢẲẨÃẴẪẠẶẬĐEÊÉẾÈỀẺỂẼỄẸỆIÍÌỈĨỊOÔƠÓỐỚÒỒỜỎỔỞÕỖỠỌỘỢUƯÚỨÙỪỦỬŨỮỤỰYÝỲỶỸỴA-Z]+\S*\b/g;
var name = "new-";
$(".demand").select2({
    tags: true,
    placeholder: "Chọn nhu cầu",
    createTag: function(params) {
        var term = $.trim(params.term);
        if (regex.test(term)){
            return {
                id: name+ term,
                text: params.term
            }
        }else{
            return null;
        }
    }
});
$(".demand").on("select2:select", function (e) {
    let tag = e.params.data;
    var demandId = tag.id;
    var idn = "";
    var name = "";
    var object;
    var selected = $(this).val();
    if (~demandId.indexOf("new-")){
        name = tag.text;
        idn = "";
        object = {
            id: idn,
            name: name,
            action: 2
        }
        listDemands.push(object);
    }else{
        name = tag.text;
        idn = tag.id;
        $.ajax({
            url: "/ajax/check/enable-demand",
            method: "POST",
            data: {
                demand_id: idn
            },
            success: function (data){
                if (data == true){
                    object = {
                        id: idn,
                        name: name,
                        action: 2
                    }
                    for(var i = 0; i < listDemands.length; i++) {
                        if(listDemands[i].name == name) {
                            listDemands.splice(i, 1);
                            break;
                        }
                    }
                    listDemands.push(object);
                    console.log(object);
                }else{
                    alert("Nhu cầu chưa được kích hoạt, vui lòng chọn nhu cầu khác");
                    var index = selected.indexOf(idn);
                    if (index !== -1) {
                        selected.splice(index, 1);
                    }
                    $(".demand").val(selected).trigger('change');
                }
            }
        })
    }
});
$('.demand').on('select2:unselecting', function (e) {
    let tag = e.params.args.data;
    var idn = tag.id;
    var object = {
        id: idn,
        name: tag.text,
        action: 1
    };
    for(var i = 0; i < listDemands.length; i++) {
        if(listDemands[i].name == tag.text) {
            listDemands.splice(i, 1);
            break;
        }
    }
    listDemands.push(object);
    console.log(listDemands);
});

$(".supply").select2({
    tags: true,
    placeholder: "Chọn khả năng cung cấp",
    createTag: function(params) {
        var term = $.trim(params.term);
        if (regex.test(term)){
            return {
                id: name+term,
                text: params.term
            }
        }else{
            return null;
        }
    }
});
$(".supply").on("select2:select", function (e) {
    let tag = e.params.data;
    var supplyId = tag.id;
    var idn = "";
    var name = "";
    var object;
    var selected = $(this).val();
    if (~supplyId.indexOf("new-")){
        name = tag.text;
        idn = "";
        object = {
            id: idn,
            name: name,
            action: 2
        }
        listSupplies.push(object);
    }else{
        name = tag.text;
        idn = tag.id;
        $.ajax({
            url: "/ajax/check/enable-supply",
            method: "POST",
            data: {
                supply_id: idn
            },
            success: function (data){
                if (data == true){
                    object = {
                        id: idn,
                        name: name,
                        action: 2
                    }
                    for(var i = 0; i < listSupplies.length; i++) {
                        if(listSupplies[i].name == name) {
                            listSupplies.splice(i, 1);
                            break;
                        }
                    }
                    listSupplies.push(object);
                }else{
                    alert("Khả năng chưa được kích hoạt, vui lòng chọn nhu cầu khác");
                    var index = selected.indexOf(idn);
                    if (index !== -1) {
                        selected.splice(index, 1);
                    }
                    $(".supply").val(selected).trigger('change');
                }
            }
        })
    }
});
$('.supply').on('select2:unselecting', function (e) {
    let tag = e.params.args.data;
    var idn = tag.id;
    var object = {
        id: idn,
        name: tag.text,
        action: 1
    };
    for(var i = 0; i < listSupplies.length; i++) {
        if(listSupplies[i].name == tag.text) {
            listSupplies.splice(i, 1);
            break;
        }
    }
    listSupplies.push(object);
});
$(".field").select2({
    placeholder: "Chọn lĩnh vực",
});
$(".field").on("select2:select", function (e) {
    let tag = e.params.data;
    var idn = tag.id;
    var object = {
        id: idn,
        action: 2
    }
    for(var i = 0; i < listFields.length; i++) {
        if(listFields[i].name == tag.text) {
            listFields.splice(i, 1);
            break;
        }
    }
    listFields.push(object);
});
$('.field').on('select2:unselecting', function (e) {
    let tag = e.params.args.data;
    var idn = tag.id;
    var object = {
        id: idn,
        action: 1
    };
    for(var i = 0; i < listFields.length; i++) {
        if(listFields[i].name == tag.text) {
            listFields.splice(i, 1);
            break;
        }
    }
    listFields.push(object);
});
$(".nation").change(function (){
    var countryId = $(this).val();
    $(".city").html("");
    $.ajax({
        url: "/user/signup/tinh-thanh",
        method: "post",
        data: {
            nationId: countryId
        },
        success: function (data){
            var option = "";
            $(".city").empty();
            for (var i = 0; i < data.length; i++){
                option = "<option value='"+ data[i].id + "'>" + data[i].tenTinh + "</option>"
                $(".city").append(option);
            }
        }
    });
});
$(".btnUpdate").click(function (e){
    e.preventDefault();
    var logo = $("#inputAvatarUrl").val();
    var banner = $("#inputBannerUrl").val();
    var phone = $("#phone").val();
    var email = $("#email").val();
    var website = $("#website").val();
    var nation = $("#nation").val();
    var city = $("#city").val();
    var street = $("#street").val();
    var name = $("#name").val();
    var orgRepresentive = $("#orgRepresentive").val();
    var demands = listDemands;
    var supplies = listSupplies;
    var fields = listFields;
    var code = $("#code").val();
    var year = $("#year").val();;
    var gioithieu = CKEDITOR.instances['gioithieu'].getData();
    var action = $("#action").val();
    var  data = {
            name: name,
            organizationRepresentativeName: orgRepresentive,
            code: code,
            banner: banner,
            organizationEmail: email,
            organizationAddress: street,
            organizationPhone: phone,
            founded: year,
            introduce: gioithieu,
            website: website,
            logo: logo,
            tinhThanhId: city,
            nationId: nation,
            updateOrgDemands: demands,
            updateOrgSupplies: supplies,
            updateOrgFields: fields
        };
    $.ajax({
        url: action,
        method: 'PUT',
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data){
            if( data.status == 200){
                setCookie("notifyInformation",true,1)
                location.reload();
            }
        }
    })

})
$body = $("body");
$(document).on({
    ajaxStart: function() { $body.addClass("loading");    },
    ajaxStop: function() { $body.removeClass("loading"); }
});
