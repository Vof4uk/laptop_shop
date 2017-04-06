$(document).ready(function () {
    var url = "/r/laptops/all";
    doGet(url);

    $('#refresh_button').click(function () {
        var url = $(this).attr('value');
        doGet(url);

    });

});

function doGet(url) {

    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        // data: 'msg',
        success: function (data) {
            display_laptops(data);
        },
        error: function (xhr, str) {
            alert('Error:::: ' + xhr.responseCode + str);
        }
    });
}

function display_laptops(laptops) {
    var parent = $('#laptops_container');
    parent.empty();
    if(Array.isArray(laptops)){
        for (var i = 0; i < laptops.length; i++) {
            parent.append(parseLaptopToHtml(laptops[i]));
        }
    }else{
        parent.append(parseLaptopToHtml(laptops));
    }

    $('.rest-get').click(function (event) {
        event.preventDefault();
        var url = $(this).attr('href');
        doGet(url);
    });
}

function parseLaptopToHtml(laptop) {
    var model = laptop.model;
    if(!(laptop.imagesLocations == undefined)) {
        var imgSource = laptop.imagesLocations[0];
    }
    var brand = laptop.brand;
    var price = laptop.price;
    var url = $('#laptops_container').attr('myprop') + laptop.id;

    var divString = "";
    divString += "<a class='rest-get' href='" + url + "'>";
    divString += "<div class='laptop' id='l_"+ laptop.id +"'>";
    divString += brand.shortName + " " + model;
    divString += "<div>";
    divString +=  "<img src='/" + imgSource + "'>";
    divString +=  "</div>";
    divString +=  price + "grn.";
    divString +=  "</div>";
    divString += "</a>";

    return divString;
}