/**
 * Created by brad on 10/12/16.
 */
$(document).ready(function(){
    console.log("payBillsManager.js ready2");

    /*when page is loaded, we will do this function*/
    function loadOptions() {
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: 'util/AccountOverview',
            success: function(response) {
                if(response["successfulQuery"]) {
                    var User = response["userAccounts"]; //assign the User object to the User
                    loadBills(User); //now create UI elements dynamically
                }
            }

        });
        $('#billToPay').html(); //bills available
        $('accountToPayFrom').html(); //accounts available
    }
    function loadBills(User){
        console.log("in load bills");
        var billHtml = "";
        var accountHtml = "";
        var billOptions = $('#billToPay');
        var accountOptions = $('#accountToPayFrom');
        billOptions.html("<option>choose</option>"); //default value
        accountOptions.html("<option>choose</option>"); //default value

        var accounts = User["accounts"];
        $.each(accounts, function(accIndex) {
            var bills = accounts[accIndex]["bills"];
            accountHtml += '<option>' +
                    accounts[accIndex]["accountType"] + ' ' +
                    accounts[accIndex]["accountNumber"] +
                '</option>';
            accountOptions.html(accountHtml);
            $.each(bills, function(billIndex){
                billHtml += '<option>' +
                    accounts[accIndex]["bills"][billIndex]["billName"] + ' ' + //get the name
                    accounts[accIndex]["bills"][billIndex]["billID"] + //get the ID of the bill
                    '</option>';
                billOptions.html(billHtml);
            });
        });
    }
    $('#makePayment').on('click', function() {
        console.log("makePayment button clicked!");
        var accountId = parseInt($('#accountToPayFrom'));
        var billId = parseInt($('#billToPay'));
        $.ajax({
            type:'POST',
            url:'util/PayBillServlet',
            dataType: "text",
            data: {
                accountId : accountId,
                billId : billId
            },
            success: function(response){
                console.log("Response Successful");
                $('#returnDiv').text(response);
                /*expand on this for other scenarios*/
            },
            error: function(xhr, status, error){
                console.log(xhr.responseText);
                console.log("Stats: " + status);
                console.log("Error: " + error);
            }
        });
    });
    loadOptions();
});
