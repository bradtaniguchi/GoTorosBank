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
        var accountSelector = $('#userAccounts');
        accountSelector.html("<option>choose</option>");
        $.each(accounts, function(index) {
           html += '<option>' + accounts[index]["accountNumber"] + ' ' +
                   accounts[index]["accountType"] + '</option>';
        });
        accountSelector.html(html);
    }
    /**
     * This account sends the data to the backend via an ajax call.
     * @param account
     * @param personGettingPaid
     * @param amount
     */
    function withdraw(amount, personGettingPaid, account) {
        console.log("amount: " + amount);
        console.log("Person: " + personGettingPaid);
        console.log("accountID: " + account);
        $.ajax({
            type:'POST',
            dataType: 'json',
            url: 'util/WithdrawServlet',
            data: {
                amount: amount,
                personGettingPaid: personGettingPaid,
                accountID: account,
                billType: 'SOMETYPE'

            },
            success: function(response) {
                console.log("message: " + response["message"]);
                console.log("successfulWithdraw " + response["successfulWithdraw"]);
                console.log("filename: " + response["filename"]);
                /*$.ajax({
                    type:'GET',
                    url: 'util/WithdrawServlet',
                    data: {
                        filename: response["filename"]
                    },
                    success: function(response) {
                        console.log("SUCCESS PUT IMAGE ON PAGE")
                        $('#image-return').html(); //just put image on page
                    }
                });*/
                var filename = response["filename"];
                window.open = "util/WithdrawServlet?filename=" + filename;

            },
            error: function(xhr, status, error) {
                console.log("ERROR with gettingAccountData! See Below for statements");
                console.log("XHR: " + xhr.responseText);
                console.log("Status: " + status.responseText);
                console.log("Error: " + error.responseText);
            }
        });
    }
    $("#submit").off('click')
        .on('click',function() {
            var amount = $('#amount').val().trim();
            amount = amount.replace( /[^\d.]/g, '' );

            var personGettingPaid = $('#personGettingPaid').val().trim();

            var userAccount = $('#userAccounts').val().trim();
            userAccount = userAccount.replace( /[^\d.]/g, '' );

            withdraw(amount, personGettingPaid, userAccount);
    });

    getAccounts();
});