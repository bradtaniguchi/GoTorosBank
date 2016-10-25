/**
 * Created by brad on 10/12/16.
 */

console.log("desperate test!");
$(document).ready(function(){
    console.log("transferManager.js loaded");
    /*this function gets the User information*/
    function updateAccounts() {
        console.log("Updating Accounts");
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: 'util/AccountOverview',
            success: function(response) {
                console.log("Successful response with message: " + response["message"]);
                console.log("successfulQuery: " + response["successfulQuery"]);
                if(response["successfulQuery"]) {
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
     * This function manipulates adds to the DOM to dynamically allow for transfers between accounts.
     * @param User
     */
    function makeAccounts(User) {
        var html = ""; //html to add to the page
        var account = User["accounts"];
        $.each(account, function(index){
            /*add an option for each accountNumber and AccountType, */
            html += '<option>' + User["accounts"][index]["accountNumber"] + ' ' +
                User["accounts"][index]["accountType"]+'</option>';
        });
    }

    /**
     * This calls the transfer servlet and actually transfers the money.
     * @param idFrom - account to transfer from
     * @param idTo - account to transfer too
     * @param amount - amount to transfer between the accounts, in float.
     */
    function transfer(idFrom, idTo, amount) {
        console.log("transfer called with values: " + idFrom + " " + idTo + " " + amount);
        /*add transfer code here*/
    }

    /*add jquery listeners to the page here*/
    $('#transferSubmit').on('click', function() {
        console.log("submited to transfer!");
        var idFrom = $('#bankAccountFrom').val();
        var idTo = $('#bankAccountTo').val();
        var amount = $('#amount').val();
        transfer(idFrom, idTo, amount);
    });
    updateAccounts(); //get any information for the user.
    console.log("Finished loading transfer manager");
});