var myModalEndCall = document.getElementById("modalEndCall");
var btnEndCall = document.getElementById("btnEndCall");
var spanEndCall = document.getElementsByClassName("endCall")[0];
var spanEndCall1 = document.getElementsByClassName("endCall")[1];
var myModalSendNotify = document.getElementById("myModalSendNotify");
var btnSendNotify = document.getElementById("btnSendNotify");
var spanSendNotify = document.getElementsByClassName("spanSendNotify")[0];


var myModal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
if (btn != undefined){
    btn.onclick = function () {
        myModal.style.display = "block";
    }
}


// When the user clicks on <span> (x), close the modal
if (span !== undefined){
    span.onclick = function () {
        myModal.style.display = "none";
        $('body,html').css('overflow','auto');
    }
}



// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target == myModal) {
        myModal.style.display = "none";
    }
    if (event.target == myModalEndCall) {
        myModalEndCall.style.display = "none";
    }
    if (event.target == myModalSendNotify) {
        myModalSendNotify.style.display = "none";
    }

}

var span_close_modal_img = document.getElementsByClassName("close-img")[0];

var modalImgClose = document.getElementById("img01");
var modalClickNone= document.getElementById("myModal-img");

// When the user clicks on <span> (x), close the modal
if(span_close_modal_img != undefined){
    span_close_modal_img.onclick = function() {
        document.getElementById("myModal-img").style.display = "none";
    }
}

var background=document.getElementsByClassName("modal-img")[0];
if(background != undefined){
    background.onclick = function (event){
        if(event.target != modalImgClose){
            document.getElementById("myModal-img").style.display = "none";
        }
    }
}


function showImg(obj){
    var modal_img = document.getElementById("myModal-img");
    // Get the image and insert it inside the modal - use its "alt" text as a caption
    var img = document.getElementById(obj.id);
    var modalImg = document.getElementById("img01");
    modal_img.style.display = "block";
    modalImg.src = obj.src;
}

// modal end call

if (btnEndCall != undefined){
    btnEndCall.onclick = function () {
        myModalEndCall.style.display = "block";
    }
}

if (btnSendNotify != undefined){
    btnSendNotify.onclick = function () {
        myModalSendNotify.style.display = "block";
    }
}

// When the user clicks on <span> (x), close the modal
if (spanEndCall !== undefined){
    spanEndCall.onclick = function () {
        myModalEndCall.style.display = "none";
        $('body,html').css('overflow','auto');
    }
}

if (spanSendNotify !== undefined){
    spanSendNotify.onclick = function () {
        myModalSendNotify.style.display = "none";
        $('body,html').css('overflow','auto');
    }
}


if (spanEndCall1 !== undefined){
    spanEndCall1.onclick = function () {
        myModalEndCall.style.display = "none";
        $('body,html').css('overflow','auto');
    }
}