/**
 * Create by brad on 9/28/16
 * This file holds the javascript/jquery front end to handle
 * the user depositing money and displaying the result of the transaction.
 */
//http://stackoverflow.com/questions/3582671/how-to-open-a-local-disk-file-with-javascript
$(document).ready(function() {

    /*when page is loaded, add the accounts dynamicall*/
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
    $('#finalSubmit').off('click').on('click', function(){
        var file = $('#input').get(0).files[0];
        var description = $('#description').val().trim();
        var accountID = $('#account').val().trim();
        accountID = accountID.replace(/[^\d.]/g, '' );

        description = description.replace(/[|&;$%@"<>()+,]/g, ""); //clean
        if(file == null|| description == null|| accountID == null || accountID == 'choose') {
            alert("Missing something!"); //change to modal!
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
                    $('#returnDiv').text(response["message"].toString());

                },
                error: function(xhr, status, error) {
                    console.log(xhr.responseText);
                    console.log("Stats: " + status);
                    console.log("Error: " + error);
                }
            });
        }
    });
    updateAccounts();
});