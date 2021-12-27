
function changePhoto(obj) {

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
        timeout: 1000000,
        success: function (data, textStatus, jqXHR) {

            $(".avatarEdit").attr("src", data.url);
            $(".inputAvatarUrl").val(data.url);
            console.log("SUCCESS : ", data);
            $("#submitButton").prop("disabled", false);
            $('#fileUploadForm')[0].reset();
            document.getElementById('avatar').src = URL.createObjectURL(obj.files[0]);
        },
        error: function (jqXHR, textStatus, errorThrown) {

            $("#result").html(jqXHR.responseText);
            console.log("ERROR : ", jqXHR.responseText);
            $("#submitButton").prop("disabled", false);

        }
    });

}
function changeNgaySinh(obj){
    document.getElementById('ngaySinh').innerText = obj.value;
}

