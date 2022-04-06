$(document).ready(function () {
    $('#buttonCancel').on('click', function () {
        window.location = moduleURL;
    });
});

function showModalDialog(title, message) {
    $("#modalTitle").text(title);
    $("#modalBody").text(message);
    $("#modalDialog").modal();
}

$("#fileImage").change(function () {
    let fileSize = this.files[0].size;

    if (fileSize > 102400) {
        this.setCustomValidity("You must choose an image less than 100KB!");
        this.reportValidity();
    } else {
        this.setCustomValidity("");
        showImageThumbnail(this);
    }
});

function showImageThumbnail(fileInput) {
    let file = fileInput.files[0];
    let reader = new FileReader();

    reader.onload = function (e) {
        $("#thumbnail").attr("src", e.target.result);
    };

    reader.readAsDataURL(file);
}