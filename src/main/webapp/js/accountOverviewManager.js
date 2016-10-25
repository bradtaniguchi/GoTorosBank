/**
 * Created by brad on 10/12/16.
 */
$(document).ready(function() {
    console.log("accountOverviewmanager.js loaded");
    function getAccountNumber() {
        console.log("Calling getAccountNumber");
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: 'util/AccountOverview',
            success: function(response) {
                console.log("Successful response with message: " + response["message"]);
                console.log("successfulQuery: " + response["successfulQuery"]);
                if(response["successfulQuery"]) {
                    $('#numberOfAccounts').text("Number of Accounts: " + response["accounts"]);
                    /*test print*/
                    //console.log("JSON: " + JSON.stringify(response["userAccounts"]));
                    var User = response["userAccounts"];//create a javascript object from the json

                    makeAccounts(User);
                }
            },
            error: function(xhr, status, error) {
                console.log(xhr.responseText);
            }
        });
    }

    /**
     * This function builds an Account UI front-end per account that exists, each in their own bootstrap
     * container.
     * @param User - an Account JSON object
     */
    function makeAccounts(User) {
        var html = ""; //the full html to return
        $.ajax({
            type: 'GET',
            dataType: 'html',
            url: '/html/accountSnippet.html',
            success: function (file_html) {
                /*Add the HTML */
                for (var account in User["accounts"]) {
                    html += file_html; //add the html for the account
                }
                $("#accounts").append(html);
            },
            error: function() {
                console.log("ERROR!");
                //$("#accounts").append(html);
            }
        });
    }
    getAccountNumber(); //call function
});