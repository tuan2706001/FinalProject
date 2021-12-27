$(window).scroll(function() {
    var scroll = $(window).scrollTop();
    if (scroll >= 100) {
        $("#header").addClass("shadow-xl");
    } else {
        $("#header").removeClass("shadow-xl");
    }
});
$(".click").click(function (){
    $(".select2-selection--single").css({'background':'white','color':'black'});
    $(".select2-selection__rendered").css("color","black");
    $(".selectOther").css("background","white");
    $(".click").css({'background':'white','color':'black'});
    $(this).css({'background':'#007DC5','color':'white'});
})

$(".clickOther").change(function (){
    $(".selectOther").css("background","#007DC5");
    $(".select2-selection--single").css({'background':'#007DC5','color':'white'});
    $(".select2-selection__rendered").css("color","white");
    // $(".clickOther").removeClass("w-24").addClass("max-w-max");
    $(".click").css({'background':'white','color':'black'})
    $(this).css({'background':'#007DC5','color':'white'})
})

// slideshow
var slideIndex = 1;
showSlides(slideIndex);

//cho tự động chạy 5s lướt 1 ảnh
setInterval(()=>{
    showSlides(slideIndex+=1);
},5000)

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function currentSlide(n) {
    showSlides(slideIndex = n);
}


function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    var dots = document.getElementsByClassName("dot");
    if (n > slides.length) {slideIndex = 1}
    if (n < 1) {slideIndex = slides.length}
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" bg-blue-700", "");
    }
    if (slides[slideIndex-1] != undefined){
        slides[slideIndex-1].style.display = "block";
    }
    if (dots[slideIndex-1] != undefined){
        dots[slideIndex-1].className += " bg-blue-700";
    }
}


$(".call_type:first" ).removeClass('closeCallTypeMenu');
$(".call_type:first" ).addClass('text-blue-700 activeMenu');
$( "#CALL_TYPE_0 ").show();

$(".call_type" ).click(function(obj) {
    var call_type_content_id = 'CALL_TYPE_' +  $(this).attr("class").split(' ')[0];
    console.log($(this).attr("class"));
    $( '.call_type' ).removeClass('text-blue-700 activeMenu');
    $( '.call_type' ).addClass('closeCallTypeMenu');

    $(this).removeClass('closeCallTypeMenu');
    $(this).addClass('text-blue-700 activeMenu');

    $( "#"+ call_type_content_id ).prevAll().hide();
    $( "#"+ call_type_content_id ).nextAll().hide();
    $( "#"+ call_type_content_id ).show();
});

// slideshow2
var slideIndex2 = 1;
showSlides2(slideIndex2);

//cho tự động chạy 5s lướt 1 ảnh
setInterval(()=>{
    showSlides2(slideIndex2+=1);
},5000)

function plusSlides2(n) {
    showSlides2(slideIndex2 += n);
}

function currentSlide2(n) {
    showSlides2(slideIndex2 = n);
}

function showSlides2(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides2");
    var dots = document.getElementsByClassName("dot2");
    if (n > slides.length) {slideIndex2 = 1}
    if (n < 1) {slideIndex2 = slides.length}
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" bg-blue-700", "");
    }
    if (slides[slideIndex2-1] != undefined){
        slides[slideIndex2-1].style.display = "block";
    }
    if (dots[slideIndex2-1] != undefined){
        dots[slideIndex2-1].className += " bg-blue-700";
    }
}

