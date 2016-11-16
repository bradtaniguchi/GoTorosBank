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
        var bankAccountFrom = $("#bankAccountFrom");
        var bankAccountTo = $("#bankAccountTo");
        bankAccountFrom.html("<option>choose</option>");
        bankAccountTo.html("<option>choose</option>");

        //console.log("Accounts from user:" + User["accounts"]);
        var accounts = User["accounts"];
        $.each(accounts, function(index){
            /*add an option for each accountNumber and AccountType, */
            html += '<option>' + accounts[index]["accountNumber"] + ' ' +
                accounts[index]["accountType"] + '</option>';
        });
        bankAccountFrom.append(html);
        bankAccountTo.append(html);
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
        //alert("Choose both to and from accounts");
            showModal("Error!", "Choose both to and from accounts", "ok");
        } else if(idFrom == idTo) {
            //alert("Invalid Transfer! The to and from accounts are the same!");
            showModal("Error!", "Invalid Transfer! The to and from accounts are the same!", "ok");
        } else if(Number(amount) <= 0) {
            showModal("Error!", "Invalid Input! The amount " + Number(amount) + " is not big enough!", "ok");
            /*TODO: truncate the input amount, and transfer to our backend amount*/
        } else { /*add any more cases here*/
            console.log("Correct input, transferring...");
            /*add transfer code here*/
            /*TODO: Finish this ajax call once transferServlet is finished...*/
            showModal("Loading..", "Starting Transfer...", "ok");
            $.ajax({
                type:'POST',
                dataType:'JSON', //return type
                url: '/util/TransferServlet',
                data: {
                    "accountIDFrom" : idFrom,
                    "accountIDTo" : idTo,
                    "amount" : amount
                },
                success: function(response) {
                    console.log("response successful");
                    if(response["successfulTransaction"]) {
                        console.log("Transaction Successful");
                        showModal("Success!", "Transfer Successful!");
                    } else {
                        showModal("DatabaseError!", response["message"], "ok");
                    }
                },
                error: function(xhr, status, error) {
                    console.log(xhr.responseText);
                    console.log("Stats: " + status);
                    console.log("Error: " + error);
                }
            });
            console.log("Finished with ajax?");
        }
    }
    function showModal(title, message, buttonprompt) {
        var returnModal = $('#returnModal'); //my return modal that I can use to return message to the user
        //var modalTitle = returnModal.add(".modal-title");//select the title
        returnModal.find('.modal-header .modal-title').text(title);
        returnModal.find('.modal-body').text(message);
        returnModal.find('.modal-footer .btn').text(buttonprompt);

        returnModal.modal('show'); //show the modal
    }
    /*add jquery listeners to the page here*/
    $('#transferSubmit').off('click')  //remove any previous click handlers
        .on('click', function() {
        var idFrom = $('#bankAccountFrom').val().trim();
        idFrom = idFrom.replace( /[^\d.]/g, '' );
        var idTo = $('#bankAccountTo').val().trim();
        idTo = idTo.replace( /[^\d.]/g, '' );
        var amount = $('#amount').val().trim();
        amount = amount.replace( /[^\d.]/g, '' );
        transfer(idFrom, idTo, amount);
    });
    updateAccounts(); //get any information for the user.
});