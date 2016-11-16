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
                console.log("ERROR with gettingAccountData! See Below for statements");
                console.log("XHR: "+xhr.responseText);
                console.log("Status: "+status.responseText);
                console.log("Error: " + error.responseText);

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
                /*update Accounts*/
                updateAccounts(User);
            },
            error: function(xhr, status, error) {
                console.log("ERROR!");
                console.log("XHR: " + xhr.responseText);
                console.log("Status: "+status.responseText);
                console.log("Error: " + error.responseText);
            }
        });
    }

    /**
     * Accepts an account object, that gets read and
     * @param User - user object to run through
     */
    function updateAccounts(User) {
        var html = ""; //this is to append to transactions later
        $(".accountType").each(function(index){ /*get accountTypes*/
            $(this).html(User["accounts"][index]["accountType"]); //set the html for select item
        });
        $(".balance").each(function(index){ /*get balance of account*/
            $(this).html(User["accounts"][index]["accountBalance"])
        });
        $(".transaction").each(function(index){
            /*for each account, we need to go through the transactions*/
            var html="<tr>"; //start the transaction entry
            var transactions = User["accounts"][index]["transactions"];
            transactions = transactions.reverse(); //reverse the array
            //for (var i in transactions){
            $.each(transactions ,function(index){
                html += '<tr>';
                html += '<td>' + transactions[index]["transactionDate"] + '</td>';
                html += '<td>' + transactions[index]["transactionAmount"] + '</td>';
                html += '<td>' + transactions[index]["transactionDescription"] + '</td>';
                html += '<tr>';
            });
            html += "</tr>"; //end the transaction entry
            $(this).html(html); //now add the html to the page
        });
    }
    getAccountNumber(); //call function
});