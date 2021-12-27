function uploadFile(obj) {

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

            $("#image").attr("src", data.url);
            $("#urlImage").val(data.url);
            $("#submitButton").prop("disabled", false);
            $('#fileUploadForm')[0].reset();
            // document.getElementById('avatar').src = URL.createObjectURL(obj.files[0]);
        },
    });
}
$( "#uploadImage" ).click(function() {
    $( "#inputUpload" ).click();
});


// upload file pdf
function uploadFilePdf(obj) {

    // Get form
    var formPdf = $('#fileUploadPdfForm')[0];

    var dataPdf = new FormData(formPdf);

    if($("#language")[0].innerText=="en"){
        document.getElementById('ten_file').className="";
        document.getElementById('ten_file').innerHTML="<div class='flex'><div class='animate-spin h-5 w-5 mr-3 border-t-2 border-b-2 rounded-full border-yellow-500'></div>Loading files</div>";
        document.getElementById('ten_file').classList.add("text-yellow-500");
    }
    else {
        document.getElementById('ten_file').className="";
        document.getElementById('ten_file').innerHTML="<div class='flex'><div class='animate-spin h-5 w-5 mr-3 border-t-2 border-b-2 rounded-full border-yellow-500'></div>Đang tải file</div>";
        document.getElementById('ten_file').classList.add("text-yellow-500");
    }
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/file/upload",
        data: dataPdf,

        // prevent jQuery from automatically transforming the data into a query string
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function (data, textStatus, jqXHR) {
            if($("#language")[0].innerText=="en"){
                document.getElementById('ten_file').className="";
                document.getElementById('ten_file').innerHTML="File download successful: "+$('#inputUploadPdf')[0].files[0].name;
                document.getElementById('ten_file').classList.add("text-green-500");
            }
            else {
                document.getElementById('ten_file').className="";
                document.getElementById('ten_file').innerHTML="Tải file lên thành công: "+$('#inputUploadPdf')[0].files[0].name;
                document.getElementById('ten_file').classList.add("text-green-500");
            }
            $("#Pdf").attr("src", data.url);
            $("#urlPdf").val(data.url);
            $("#submitButton").prop("disabled", false);
            $('#fileUploadPdfForm')[0].reset();
            // document.getElementById('avatar').src = URL.createObjectURL(obj.files[0]);
        },
        error: function (){
            if($("#language")[0].innerText=="en"){
                document.getElementById('ten_file').className="";
                document.getElementById('ten_file').innerHTML="File download failed: "+$('#inputUploadPdf')[0].files[0].name;
                document.getElementById('ten_file').classList.add("text-red-500");
            }
            else {
                document.getElementById('ten_file').className="";
                document.getElementById('ten_file').innerHTML="Tải file lên thất bại: "+$('#inputUploadPdf')[0].files[0].name;
                document.getElementById('ten_file').classList.add("text-red-500");
            }
        },
    });
}
$( ".uploadPdf" ).click(function() {
    $( "#inputUploadPdf" ).click();
});
