
var form1_edit=document.getElementById("formEdit1");
var form2_edit=document.getElementById("formEdit2");
var menu1_edit=document.getElementById("menuEdit1");
var menu2_edit=document.getElementById("menuEdit2");
if($('#form1').length>0){
    appearFormEdit1();
}
function appearFormEdit1(){
    form1_edit.style.cssText = "display:block;";
    form2_edit.style.cssText = "display:none;";
    menu1_edit.style.cssText = "color: rgba(245, 158, 11,0.8);border-bottom: 2px solid rgba(245, 158, 11,0.8);";
    menu2_edit.style.cssText = "color: black;border:none;";
}
function appearFormEdit2(){
    form1_edit.style.cssText = "display:none;";
    form2_edit.style.cssText = "display:block;";
    menu1_edit.style.cssText = "color: black;border:none;";
    menu2_edit.style.cssText = "color: rgba(245, 158, 11,0.8);border-bottom: 2px solid rgba(245, 158, 11,0.8);";
}