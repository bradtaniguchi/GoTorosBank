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
    function withdraw(amount, personGettingPaid, account, memo) {
        console.log("amount: " + amount);
        console.log("Person: " + personGettingPaid);
        console.log("accountID: " + account);
        console.log("memo: " + memo);
        $.ajax({
            type:'POST',
            dataType: 'json',
            url: 'util/WithdrawServlet',
            data: {
                amount: amount,
                personGettingPaid: personGettingPaid,
                accountID: account,
                billType: memo
            },
            success: function(response) {
                console.log("message: " + response["message"]);
                console.log("successfulWithdraw " + response["successfulWithdraw"]);
                console.log("filename: " + response["filename"]);
                var filename = response["filename"];
                url= "/util/WithdrawServlet?filename=" + filename;
                $('#image-return').html("<iframe id='iframe' src=" + url + "></iframe>");

                /*now change the size of the iframe*/
                $("#iframe").width(800);
                $("#iframe").height(600);
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
            /*We get all the selected-amount buttons, and get their id number*/
            var amounts = $('#btn-group').children(); /*there should be only 1*/
            var amount = amounts.find(':checked').val();
            var personGettingPaid = $('#person-getting-paid').val().trim();
            var account = $('#userAccounts').val().trim();
            account = account.replace( /[^\d.]/g, '' );/*just get the number*/

            var memo = $('#memo').val().trim();

            withdraw(amount, personGettingPaid, account, memo);
    });

    getAccounts();
});