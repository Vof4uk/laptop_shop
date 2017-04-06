var id = 0;
var laptop;
var brands;
var urlGet;
var urlUpload;
var urlSaveChanges;
var urlGetBrands;
$(document).ready(function () {
    $('#laptop_div').empty();
    id = $('#id').text();
    urlGet = $('#get_url').text();
    urlUpload = $('#upload_url').text();
    urlSaveChanges = $('#update_url').text();
    urlGetBrands = $('#brands_url').text();
    getBrandsList();
    getLaptop();
});

function getLaptop() {
    $.ajax({
        type: 'GET',
        url: urlGet,
        dataType: 'json',
        success: function (data) {
            laptop = data;
            displayLaptop();
        },
        error: function (xhr, str) {
            alert('Error:::: ' + xhr.responseCode + str);
        }
    });
}

function getBrandsList() {
    $.ajax({
        type: 'GET',
        url: urlGetBrands,
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        success: function (data) {
            brands = data;
        },
        error: function (xhr, str) {
            alert('Error:::: ' + xhr.responseCode + str);
        }
    });
}

function uploadPhotoForm(data, button) {
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: urlUpload,
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        dataType: 'text',
        success: function (result) {
            var img = button.parent().parent().parent().find('img');
            img.prop('src', '/' + result);
            var idx = $(button).attr("id").charAt(3);
            $('#inp' + idx).prop("value", result);
            button.prop("disabled", false);
        },
        error: function (e) {
            button.prop("disabled", false);
        }
    });
}

function displayLaptop() {
    $('#laptop_div').empty();
    displayMainForm();
    displayPhotos();
    placeHooks();
}

function displayMainForm() {
    var str = "";
    str += "<div class='laptop-form'>";
    str += "<form id='form1' name='form-to-send' method='POST' action='" + urlSaveChanges + "'>";
    str += "<input name='id' type='hidden' value='" + laptop.id + "' />";
    str += "<h4>Model: </h4><input name='model' type='text' value='" + laptop.model + "' />";
    str += "<h4>Brand: </h4>" + generateBrandOption();//TODO
    str += "<h4>Description: </h4><input name='description' type='text' value='" + laptop.description + "' />";
    str += "<h4>Cpu: </h4><input name='cpuFrequency' type='number' step='0.01' value='" + laptop.cpuFrequency + "' />";
    str += "<h4>Ram: </h4><input name='ram' type='number' step='0.1' value='" + laptop.ram + "' />";
    str += "<h4>Price: </h4><input name='price' type='number' step='0.01' value='" + laptop.price + "' />";
    str += "<h4>In stock: </h4><input name='stock' type='number' value='" + laptop.stock + "' />";
    str += "<button type='submit'>save changes</button>"
    str += "</form>";
    str += "</div>";

    $('#laptop_div').append(str);
}

function displayPhotos() {
    var images = laptop.imagesLocations;
    if (images != undefined) {
        for (var i = 0; i < 4; i++) {
            // if (images[i] != undefined && images[i] != "") {
            var str = "";
            str += "<div class='photo-with-form'>";
            str += "<img height='120' width='150' src='/" + images[i] + "'/>";
            str += "<form method='POST' enctype='multipart/form-data' action='" + urlUpload + "'>";
            str += "<div>";
            str += "<input name='photo_file' type='file'/>";
            str += "</div>";
            str += "<div>";
            str += "<button id='btn" + i + "' class='photo-submit-btn' type='submit'>upload</button>";
            str += "</div>";
            str += "</form>";
            str += "</div>";
            $('#laptop_div').append(str);

            var inp = "";
            inp += "<input id='inp" + i + "' name='imagesLocations' type='hidden' value='" + laptop.imagesLocations[i] + "'/>";
            $('#form1').append(inp);
            // }TODO
        }
    }
}

function placeHooks() {
    $('.photo-submit-btn').click(function (event) {
        event.preventDefault();
        var form = $(this).parent().parent()[0];
        var data = new FormData(form);
        var button = $(this);
        button.prop("disabled", true);
        uploadPhotoForm(data, button);
    });

    $('#form1').on('submit', function (event) {
        event.preventDefault();
        var msg = $(this).serialize();


        $.ajax({
            type: 'POST',
            url: urlSaveChanges,
            dataType: 'json',
            data: msg,
            success: function (data) {
                alert('saved');
            },
            error: function (xhr, str) {
                alert('Error: ' + xhr.responseCode);
            }
        });
    })
}

function generateBrandOption() {
    var count = brands.length;
    var selected = laptop.brand.id;
    var str = "";
    str += "<select name='brand'>";
    for(var i = 0; i < count; i++){
        str += "<option ";
        str += "name='" + brands[i].shortName + "' value='" + brands[i].id + "'";
        if(selected == brands[i].id){
            str += " selected='selected'"
        }
        str += ">";
        str += brands[i].shortName + "</option>";
    }
    str += "</select>";

    return str;
}