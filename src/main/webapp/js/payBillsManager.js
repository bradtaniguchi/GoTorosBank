/**
 * Created by brad on 10/12/16.
 */
$(document).ready(function(){
    console.log("payBillsManager.js ready");

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
    function payBill(accountID, billID){
        console.log("accountID: " + accountID);
        console.log("billID: " + billID);
        /*do some logical checks first*/
        $.ajax({
            type:'POST',
            url:'util/PayBillServlet',
            dataType: "json",
            data: {
                "accountID" : accountID,
                "billID" : billID
            },
            success: function(response){
                if(response["successfulBillPay"]) {
                    showModal("Successful Paid Bill", response["message"], "ok");
                } else { /*we were not able to commit*/
                    showModal("Unable to Pay The Bill", response["message"], "ok");
                }
            },
            error: function(xhr, status, error){
                showModal("AJAX CALL ERROR!", xhr.responseText +"\n" + status + "\n" + error, "ok");
                console.log(xhr.responseText);
                console.log("Stats: " + status);
                console.log("Error: " + error);
            }
        });
    }
    function showModal(title, message, buttonprompt) {
        var returnModal = $('#returnModal'); //my return modal that I can use to return message to the user
        //var modalTitle = returnModal.add(".modal-title");//select the title
        returnModal.find('.modal-header .modal-title').text(title);
        returnModal.find('.modal-body').text(message);
        returnModal.find('.modal-footer .btn').text(buttonprompt);

        returnModal.modal('show'); //show the modal
    }
    $('#makePayment').off('click').on('click', function() {
        var accountID = $('#accountToPayFrom').val().trim();
        var billID = $('#billToPay').val().trim();
        accountID = accountID.replace(/[^\d.]/g, '');
        billID = billID.replace(/[^\d.]/g, '');

        if(isNaN(accountID) || isNaN(billID)) {
            alert("Account id or bill isn't correct!"); //change to modal popup
        } else {
            payBill(accountID, billID); //call the function
        }
    });
    $('#closeButton').off('click').on('click', function() {
            window.location = "/profile.jsp";
    });
    loadOptions();
});
