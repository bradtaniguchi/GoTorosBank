/**
 * Created by brad on 10/12/16.
 */
$(document).ready(function() {
    console.log("accountOverviewmanager.js loaded");
    function getAccountNumber() {
        console.log("Calling getAccountNumber");
        $.ajax({
            type: 'POST',
            url: 'util/AccountOverview',
            success: function(response) {
                console.log("Successful response with message: " + response["message"]);
                console.log("successfulQuery: " + response["successfulQuery"]);
                if(response["successfulQuery"]) {
                    $('#numberOfAccounts').text("Number of Accounts: " + response["accounts"]);
                    /*test print*/
                    console.log("test1");
                    console.log("JSON: " + JSON.stringify(response["userAccounts"]));
                    console.log("test");
                }
            },
            error: function(xhr, status, error) {
                console.log(xhr.responseText);
            }
        });
    }
    getAccountNumber(); //call function
});