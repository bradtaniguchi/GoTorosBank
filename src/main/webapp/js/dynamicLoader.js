/**
 * Created by brad on 9/19/16.
 * This file holds the dynmaic loader that is used to change "views"
 * on the Account Activity page.
 */

$(document).ready(function() {
    console.log("Dynamic loader ready");
    function showPage(pageString) {
        console.log("Changing mainActivity to: " + pageString);
        $("mainActivity").load(pageString);
    }
    /*Ghetto way to enable the rest of the buttons..*/
    function unEnable() {
        $("#ProfilePage").removeClass('active');
        $("#Deposit").removeClass('active');
        $("#Withdraw").removeClass('active');
        $("#Transfer").removeClass('active');
        $("#PayBills").removeClass('active');
    }

    /*jquery Listeners, and how to disable the buttons dynamically
    * http://stackoverflow.com/questions/17327668/best-way-to-disable-button-in-twitters-bootstrap
    */
   $("#ProfilePage").on('click', function() {
       showPage("profilePage");
       unEnable();
       $(this).addClass('active');
       $("#mainActivity").load('jsp/AccountOverview.jsp');
   });

    $("#Deposit").on('click', function() {
        showPage("deposit");
        unEnable();
        $(this).addClass('active');
        $("#mainActivity").load('jsp/deposit.jsp');
    });

    $("#Withdraw").on('click', function() {
        showPage("withdraw");
        unEnable();
        $(this).addClass('active');
        $("#mainActivity").load('jsp/withdraw.jsp');
    });
});