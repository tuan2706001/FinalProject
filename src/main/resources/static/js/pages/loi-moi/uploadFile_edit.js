function uploadFileEdit(obj) {

    // Get form
    var formImg = $('#fileUploadFormEdit')[0];

    var dataImg = new FormData(formImg);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/file/upload",
        data: dataImg,

        // prevent jQuery from automatically transforming the data into a query string
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function (data, textStatus, jqXHR) {
            $("#textUpfile").addClass("bg-opacity-0")
            $("#imageEdit").attr("src", data.url);
            $("#urlImageEdit").val(data.url);
            $("#submitButton").prop("disabled", false);
            $('#fileUploadFormEdit')[0].reset();
            // document.getElementById('avatar').src = URL.createObjectURL(obj.files[0]);
        },
    });
}
$( "#uploadImageEdit" ).click(function() {
    $( "#inputUploadEdit" ).click();
});


// upload file pdf
function uploadFilePdfEdit(obj) {

    // Get form
    var formPdfEdit = $('#fileUploadPdfFormEdit')[0];

    var dataPdfEdit = new FormData(formPdfEdit);
    if($("#language")[0].innerText=="en"){
        document.getElementById('ten_file_edit').className="";
        document.getElementById('ten_file_edit').innerHTML="<div class='flex'><div class='animate-spin h-5 w-5 mr-3 border-t-2 border-b-2 rounded-full border-yellow-500'></div>Loading files</div>";
        document.getElementById('ten_file_edit').classList.add("text-yellow-500");
    }
    else {
        document.getElementById('ten_file_edit').className="";
        document.getElementById('ten_file_edit').innerHTML="<div class='flex'><div class='animate-spin h-5 w-5 mr-3 border-t-2 border-b-2 rounded-full border-yellow-500'></div>Đang tải file</div>";
        document.getElementById('ten_file_edit').classList.add("text-yellow-500");
    }
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/file/upload",
        data: dataPdfEdit,
        // prevent jQuery from automatically transforming the data into a query string
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function (data, textStatus, jqXHR) {
            if($("#language")[0].innerText=="en"){
                document.getElementById('ten_file_edit').className="";
                document.getElementById('ten_file_edit').innerHTML="File download successful: "+$('#inputUploadPdfEdit')[0].files[0].name;
                document.getElementById('ten_file_edit').classList.add("text-green-500");
            }
            else {
                document.getElementById('ten_file_edit').className="";
                document.getElementById('ten_file_edit').innerHTML="Tải file lên thành công: "+$('#inputUploadPdfEdit')[0].files[0].name;
                document.getElementById('ten_file_edit').classList.add("text-green-500");
            }
            $("#PdfEdit").attr("href", data.url);
            $("#urlPdfEdit").val(data.url);
            $("#submitButton").prop("disabled", false);
            $('#fileUploadPdfFormEdit')[0].reset();
            // document.getElementById('avatar').src = URL.createObjectURL(obj.files[0]);
        },
        error: function (){
            if($("#language")[0].innerText=="en"){
                document.getElementById('ten_file_edit').className="";
                document.getElementById('ten_file_edit').innerHTML="File download failed: "+$('#inputUploadPdfEdit')[0].files[0].name;
                document.getElementById('ten_file_edit').classList.add("text-red-500");
            }
            else {
                document.getElementById('ten_file_edit').className="";
                document.getElementById('ten_file_edit').innerHTML="Tải file lên thất bại: "+$('#inputUploadPdfEdit')[0].files[0].name;
                document.getElementById('ten_file_edit').classList.add("text-red-500");
            }
        },
    });
}
$( "#uploadPdfEdit" ).click(function() {
    $( "#inputUploadPdfEdit" ).click();
});