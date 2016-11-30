/**
 * Created by brad on 9/19/16.
 * This file holds the dynamic loader that is used to change "views"
 * on the Account Activity page.
 */

$(document).ready(function() {
    console.log("Dynamic loader ready");
    var transferLoaded = 0;
    var payBillsLoaded = 0;
    function showPage(pageString, title) {
        console.log("Changing mainActivity to: " + pageString);
        document.title = 'GoTorosBank '+ title;
        $('#mainActivity').parent().find('.panel-heading').html('<h4>' + title + '</h4>');
        $('#mainActivity').load(pageString);
    }
    /*Ghetto way to enable the rest of the buttons..*/
    function unEnable() {
        //var userLoggedIn="<%=session.getAttribute('username')%>";
        if(userLoggedIn.localeCompare("null") == 0) {
            alert("You must first log in to use this site!!!");
            document.location.href="/";
        }
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
        showPage("jsp/AccountOverview.jsp", "Account Overview");
        unEnable();
        $(this).addClass('active');
    });
    $("#Deposit").on('click', function() {
        showPage("jsp/deposit.jsp", "Deposit");
        unEnable();
        $(this).addClass('active');
    });
    $("#Withdraw").on('click', function() {
        showPage("jsp/withdraw.jsp", "Withdraw");
        unEnable();
        $(this).addClass('active');
    });
    $("#Transfer").on('click', function() {
        console.log("Clicked transfer");
        showPage("jsp/transfer.jsp", "Transfer");
        if(!transferLoaded) { // protect against loading this file twice
            $.getScript("js/transferManager.js", function () {
                transferLoaded = 1; //file is loaded on the page already
            })
                .fail(function (jqxhr, settings, exception) {
                alert("Failure to load javascript!\n" + jqxhr.responseText);
            });
        }
        unEnable();
        $(this).addClass('active');
    });
    $("#PayBills").on('click', function() {
        showPage("jsp/payBills.jsp", "PayBills");
        if(!payBillsLoaded) {
            $.getScript("js/payBillsManager.js", function() {
                payBillsLoaded = 1;
            })
                .fail(function(jqxhr, settings, exception){
                    alert("Failure to load javascript!\n" + jqxhr.responseText);
                });
        }
        unEnable();
        $(this).addClass('active');
    });
    console.log("loaded");
});