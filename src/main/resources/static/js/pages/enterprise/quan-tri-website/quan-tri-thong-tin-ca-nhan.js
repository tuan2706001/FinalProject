
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

            $("#avatar").attr("src", data.url);
            $("#inputAvatarUrl").val(data.url);
            console.log("SUCCESS : ", data);
            $("#submitButton").prop("disabled", false);
            $('#fileUploadForm')[0].reset();
            var src = data.url;
            console.log(src);
            $("#avatar").attr("src",src);
        },
        error: function (jqXHR, textStatus, errorThrown) {

            $("#result").html(jqXHR.responseText);
            console.log("ERROR : ", jqXHR.responseText);
            $("#submitButton").prop("disabled", false);

        }
    });

}

function changeBanner(obj) {

    // Get form
    var form = $('#fileUploadFormBanner')[0];

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
        success: function (data) {

            $("#bannerPhoto").attr("src", data.url);
            $("#inputBannerUrl").val(data.url);
            $('#fileUploadFormBanner')[0].reset();
            var src = data.url;
            $("#bannerPhoto").attr("src",src);
        },

    });

}
