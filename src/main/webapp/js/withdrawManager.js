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
                    user = User; //set global variable
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
     * This function shows a modal with the check inside.
     * @param url
     */
    function returnCheckModal(url){
        console.log("Trying to show returnCheckModal");
        var modal = $('#returnModal');
        var title = "Your Check is Below";
        modal.find('.modal-header .modal-title').text(title);

        modal.find(".modal-body").html("<p>Right-Click and save this image</p>" + "<br/>" +
            "<iframe id='iframe' src=" + url + "></iframe>");
        /*add a button listener, when they click on it, we redirect to main page*/
        modal.find("btn").off('click').on('click',function() {
            window.location = "/";
        });
        modal.modal('show');
    }

    /**
     * This function shows the modal, with the loading gif,
     * shown before putting up check.
     */
    function showLoadingModal(title, message, showImage) {
        var modal = $('#returnModal');

        modal.find('.modal-header .modal-title').text(title);
        if (showImage == true) {
            var html = '<p style="text-align: center">' + message +
                '<img src="pictures/hex-loader2.gif" alt="Loading Gif" ' +
                'style="width:304px;height:228px;"></p>';
            modal.find('.modal-body').html(html)
        } else {
            modal.find('.modal-body').text(message);
        }

        modal.modal('show');
    }
    /**
     * This account sends the data to the backend via an ajax call.
     * @param account - the account ID to remove from
     * @param personGettingPaid - the person that will get paid
     * @param amount - the amount to transfer
     * @param memo - the memo.
     */
    function withdraw(amount, personGettingPaid, account, memo) {
        console.log("amount: " + amount);
        console.log("Person: " + personGettingPaid);
        console.log("accountID: " + account);
        console.log("memo: " + memo);
        /*do a little front-end business logic*/
        if (personGettingPaid == "" || memo == "") {
            showLoadingModal("Error!", "You must fill out all forms to proceed!");
        } else {
            $.ajax({
                type: 'POST',
                dataType: 'json',
                url: 'util/WithdrawServlet',
                data: {
                    amount: amount,
                    personGettingPaid: personGettingPaid,
                    accountID: account,
                    memo: memo
                },
                success: function (response) {
                    console.log("message: " + response["message"]);
                    console.log("successfulWithdraw " + response["successfulWithdraw"]);
                    console.log("filename: " + response["filename"]);
                    var filename = response["filename"];
                    url = "/util/WithdrawServlet?filename=" + filename;
                    if (response["successfulWithdraw"]) {
                        /*were good so show the check*/
                        returnCheckModal(url);
                    } else {
                        showLoadingModal("ERROR with withdraw!", "There was an error on the server side, we were not able "+
                        "withdraw " + amount + "from account with ID: " + account + "for these reasons: \n\n" +
                        response["message"]);
                    }
                },
                error: function (xhr, status, error) {
                    console.log("ERROR with  withdraw!");
                    console.log("XHR: " + xhr.responseText);
                    console.log("Status: " + status.responseText);
                    console.log("Error: " + error.responseText);
                    showLoadingModal("ERROR with withdraw!", "XHR: " + xhr.responseText + "\n" +
                    "Status: " + status.responseText + "\n" + "Error: " + error.responseText);
                }
            });
            /*This is after the ajax call, we wait for it to load...*/
            showLoadingModal("Loading...", "We are loading your check from the server, please wait", true);
        }
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
            if (amount == null) {
                showLoadingModal("Error!", "You must click on a correct amount!");
            }else {
                withdraw(amount, personGettingPaid, account, memo);
            }
    });

    getAccounts();
});