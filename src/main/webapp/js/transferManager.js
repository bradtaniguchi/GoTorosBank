/**
 * Created by brad on 10/12/16.
 *
 */
$(document).ready(function(){
    console.log("transferManager.js loaded");
    /*this function gets the User information*/
    function updateAccounts() {
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
                console.log(status.responseText);
                console.log(error.responseText);
            }
        });
    }

    /**
     * This function manipulates adds to the DOM to dynamically allow for transfers between accounts.
     * @param User
     */
    function makeAccounts(User) {
        var html = ""; //html to add to the page
        //console.log("Accounts from user:" + User["accounts"]);
        var accounts = User["accounts"];
        html += "<option>choose</option>"; //default nothing value
        $.each(accounts, function(index){
            /*add an option for each accountNumber and AccountType, */
            html += '<option>' + accounts[index]["accountNumber"] + ' ' +
                accounts[index]["accountType"] + '</option>';
        });
        $("#bankAccountFrom").append(html);
        $("#bankAccountTo").append(html);
    }

    /**
     * This calls the transfer servlet and actually transfers the money.
     * @param idFrom - account to transfer from
     * @param idTo - account to transfer too
     * @param amount - amount to transfer between the accounts, in float.
     */
    function transfer(idFrom, idTo, amount) {
        console.log("transfer called with values: " + idFrom + " " + idTo + " " + amount);
        /*first sanitize the amount input (1st level of defence)*/
        //amount = $(amount).text();

        /*check to see if values are the same, or if the input field is valid*/
        if (idFrom == "choose" || idTo == "choose") {
        alert("Choose both to and from accounts");
        } else if(idFrom == idTo) {
            alert("Invalid Transfer! The to and from accounts are the same!");
        } else if(Number(amount) <= 0) {
            alert("Invalid Input! The amount " + Number(amount) + " is not big enough!");
            /*TODO: truncate the input amount, and transfer to our backend amount*/
        } else { /*add any more cases here*/
            console.log("Correct input, transfering...");
            /*add transfer code here*/
            /*TODO: Finish this ajax call once transferServlet is finished...*/
            /*$.ajax({
                type:'POST',
                dataType:'JSON', //return type


            });*/
        }
    }

    /*add jquery listeners to the page here*/
    $('#transferSubmit').on('click', function() {
        var idFrom = $('#bankAccountFrom').val();
        var idTo = $('#bankAccountTo').val();
        var amount = $('#amount').val();
        transfer(idFrom, idTo, amount);
    });
    updateAccounts(); //get any information for the user.
});