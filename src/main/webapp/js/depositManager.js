/**
 * Create by brad on 9/28/16
 * This file holds the javascript/jquery front end to handle
 * the user depositing money and displaying the result of the transaction.
 */
//http://stackoverflow.com/questions/3582671/how-to-open-a-local-disk-file-with-javascript
$(document).ready(function() {
    console.log("depositManager.js ready");
    $('#finalSubmit').on('click', function(){
        var file = $('#input').get(0).files[0];
        console.log(file.type);
        var formdata = new FormData(); //add the file to the form data
        formdata.append("file", file);
        $.ajax({
            type: 'POST',
            url: 'util/UploadServlet',
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            data: formdata,
            success: function(response) {
                console.log("Response Successful " + response.toString());
                $('#returnDiv').text(response.toString());
            },
            error: function(xhr, status, error) {
                console.log(xhr.responseText);
                console.log("Stats: " + status);
                console.log("Error: " + error);
            }
        });
    });
    console.log("loaded test");
});