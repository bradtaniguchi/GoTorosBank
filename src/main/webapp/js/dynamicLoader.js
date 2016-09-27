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
    function reEnable() {
        $("#ProfilePage").enable();
        $("#Deposit").enable();
        $("#Withdraw").enable();
        $("#Transfer").enable();
        $("#PayBills").enable();
    }

    /*jquery Listeners, and how to disable the buttons dynamically
    * http://stackoverflow.com/questions/17327668/best-way-to-disable-button-in-twitters-bootstrap
    */
   $("#ProfilePage").on('click', function() {
       showPage("profilePage");
       reEnable(); //seriously how I am going to do this!
   }).disable();

    $("#Deposit").on('click', function() {
        showPage("deposit");
        reEnable(); //seriously how I am going to do this!
    }).disable();

    $("Withdraw").on('click', function() {
        showPage("withdraw");
        reEnable(); //seriously how I am going to do this!
    }).disable();
});