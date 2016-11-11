/**
 * Created by brad on 10/24/16.
 */

$(document).ready(function(){
    console.log("withdrawManager.js called");
    function getAccounts() {
        console.log("Calling getAccountNumber");
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: 'util/AccountOverview',
            success: function(response) {
                console.log("Successful response with message: " + response["message"]);
                console.log("successfulQuery: " + response["successfulQuery"]);
                if(response["successfulQuery"]) {
                    var User = response["userAccounts"];//create a javascript object from the json

                    makeAccounts(User);
                }
            },
            error: function(xhr, status, error) {
                console.log("ERROR with gettingAccountData! See Below for statements");
                console.log("XHR: "+xhr.responseText);
                console.log("Status: "+status.responseText);
                console.log("Error: " + error.responseText);

            }
        });
    }
    function makeAccounts(User) {
        var html=""; //the full html to return, radial button for each account
        var accounts = User["accounts"];
        $.each(accounts, function(index) {
           html += '<div class="radio">';
           html += '<label><input type="radio" name="optradio">' + accounts[index]["accountType"] + '</label>';
           html += '</div>';
        });
        $('#userAccounts').html(html);
    }
    getAccounts();
});