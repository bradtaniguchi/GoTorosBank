/**
 * Created by brad on 10/12/16.
 */
$(document).ready(function(){
    console.log("payBillsManager.js ready");
    
    $('#makePayment').on('click', function() {
        console.log("makePayment button clicked!");
        var paymentAmount = $('#paymentAmount').val();
        console.log("Payment amount found: " + paymentAmount);
        $.ajax({
            type:'POST',
            url:'util/Bill',
            dataType: "text",
            data: {
                paymentAmount: paymentAmount
            },
            success: function(response){
                console.log("Response Successful");
                $('#returnDiv').text(response);
            },
            error: function(xhr, status, error){
                console.log(xhr.responseText);
                console.log("Stats: " + status);
                console.log("Error: " + error);
            }
        });
    });
});
