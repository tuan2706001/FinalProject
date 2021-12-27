
var form1=document.getElementById("form1");
var form2=document.getElementById("form2");
var menu1=document.getElementById("menu1");
var menu2=document.getElementById("menu2");
if($('#form1').length>0){
    appearForm1();
}
function appearForm1(){
    form1.style.cssText = "display:block;";
    form2.style.cssText = "display:none;";
    menu1.style.cssText = "color: rgba(245, 158, 11,0.8);border-bottom: 2px solid rgba(245, 158, 11,0.8);";
    menu2.style.cssText = "color: black;border:none;";
}
function appearForm2(){
    form1.style.cssText = "display:none;";
    form2.style.cssText = "display:block;";
    menu1.style.cssText = "color: black;border:none;";
    menu2.style.cssText = "color: rgba(245, 158, 11,0.8);border-bottom: 2px solid rgba(245, 158, 11,0.8);";
}