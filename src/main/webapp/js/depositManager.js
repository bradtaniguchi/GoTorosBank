/**
 * Create by brad on 9/28/16
 * This file holds the javascript/jquery front end to handle
 * the user depositing money and displaying the result of the transaction.
 */
//http://stackoverflow.com/questions/3582671/how-to-open-a-local-disk-file-with-javascript
$(document).ready(function() {
    var returnModal = $('#returnModal');
    /*when page is loaded, add the accounts dynamically*/
    function updateAccounts() {
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: 'util/AccountOverview',
            success: function(response) {
                if(response["successfulQuery"]) {
                    /*test print*/
                    var User = response["userAccounts"];//create a javascript object from the json
                    makeAccounts(User);
                }
            },
            error: function(xhr, status, error) {
                displayError(xhr, status, error, "AJAX CALL ERROR UPON LOADING ACCOUNTS!");
            }
        });
    }
    function makeAccounts(User) {
        var html = ""; //html to add to the page
        var accountSelector = $('#account');
        accountSelector.html("<option>choose</option>"); /*add default values*/

        var accounts = User["accounts"];
        $.each(accounts, function(index){
            html += '<option>' + accounts[index]["accountNumber"] + ' ' +
                    accounts[index]["accountType"] + '</option>';
        });
        accountSelector.append(html);
    }
    function showModal(title, message, buttonPrompt, redirect){
        var button = returnModal.find('.modal-footer').find('.btn');
        button.off('click'); //remove any click actions
        /*make sure the button is turned on correctly*/
        returnModal.find('.modal-footer').find('.btn').show();

        returnModal.find('.modal-header').find('.modal-title').text(title);
        returnModal.find('.modal-body').text(message);
        button.text(buttonPrompt);

        returnModal.modal('show'); //show the modal
        /*add a jquery listener if we gave a redirect as true*/
        if (redirect) {
            button.on('click', function() {
                window.location = '/profile.jsp';
            });
        }
    }
    function showModalLoading(title) {
        var html = '<p style="text-align: center"><img src="pictures/hex-loader2.gif" alt="Loading Gif" ' +
            'style="width:304px;height:228px;"></p>'
        returnModal.find('.modal-header').find('.modal-title').text(title);
        returnModal.find('.modal-body').html(html);
        returnModal.find('.modal-footer').find('.btn').hide(); //hide the close button... for now
        returnModal.modal('show');
    }
    function displayError(xhr, status, error, modalTitle){
        console.log(xhr.responseText);
        console.log(status.responseText);
        console.log(error.responseText);
        showModal(modalTitle,
            "There was an error trying to upload your accounts" +
            "xhr: " + xhr.responseText + "\n" +
            "status: " + status.responseText + "\n" +
            "error: " + error.responseText, "ok");

    }
    $('#finalSubmit').off('click').on('click', function(){
        var file = $('#input').get(0).files[0];
        var description = $('#description').val().trim();
        var accountID = $('#account').val().trim();
        accountID = accountID.replace(/[^\d.]/g, '' );
        description = description.replace(/[|&;$%@"<>()+,]/g, ""); //clean
        if(file == null|| description == ""|| accountID == null || accountID == 'choose') {
            showModal("Missing entry!", "You need to fill out all sections before submitting", "ok");
        } else {
            var formdata = new FormData(); //add the file to the form data
            formdata.append("file", file);
            formdata.append("account", accountID); //test
            formdata.append("description", description);
            $.ajax({
                type: 'POST',
                url: 'util/DepositServlet',
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                data: formdata,
                success: function(response) {
                    console.log("Response Successful " + response["message"].toString());
                    console.log("Amount: " + response["amount"].toString());
                    console.log("Readable: " + response["readable"].toString());
                    //$('#returnDiv').text(response["message"].toString());
                    /*we got a successful response, and want to show the user the modal popup*/
                    var message;
                    var redirect
                    if(response["readable"]) { /*only if we could read the check*/
                        message = response["message"].toString() + "\n" +
                            "Amount: " + response["amount"].toString();
                        redirect = true;
                    } else { /*The check was not readable!*/
                        message = response["message"].toString(); /*this should say couldn't read the check..*/
                        redirect = false;
                    }
                    showModal("Check Uploaded", message, "ok", redirect);
                },
                error: function(xhr, status, error) {
                    displayError(xhr, status, error, "ERROR DURING AJAX CALL TO UPLOAD DATAFORM!");
                }
            });
            /*while we wait for ajax to come back, lets show a loading screen*/
            showModalLoading("Uploading check...");

        }
    });
    updateAccounts();
});