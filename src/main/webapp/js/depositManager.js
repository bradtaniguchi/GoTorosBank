/**
 * Create by brad on 9/28/16
 * This file holds the javascript/jquery front end to handle
 * the user depositing money and displaying the result of the transaction.
 */

$(document).ready(function() {
    console.log("depositManager.js ready");

    /*jqery Click listeners: */
    $('#depositSubmit').on('click', function() {
        console.log("Submit Button clicked!");
        var amount = $('#amount').val();
        console.log("Amount found: " + amount);
        $.ajax({
            type:'POST',
            url:'util/DepositManager',
            dataType: "text",
            data: {
                amount: amount
            },
            success: function(response) {
                console.log("Response Successful");
                $('#returnDiv').text(response);
            },
            error: function(xhr, status, error) {
                //var err = eval("(" + xhr.responseText + ")");
                console.log(xhr.responseText);
                console.log("Stats: " + status);
                console.log("Error: " + error);
            }
        });
    });
});