/**
 * Created by brad on 9/23/16.
 * This script is attached to the navbar, and provides functionality to
 * Login/logout of the entire site. This calls the LogoutManager class
 * in the backend.
 * TODO: Add better printouts when you cannot logout
 */
var debugging = true;  //temp flag
$(document).ready(function() {
    if(debugging) console.log("logoutManager.js loaded");
    function logout() {
        if(debugging) console.log("Attempting to logout");
        $.ajax({
            url:'LogoutManager',
            type:'POST',
            data: {},
            success: function(response) {
                if(debugging) console.log("Response Successful");
                if(debugging) console.log("Response: " + response["successfulLogout"]);
                if(response["successfulLogout"] == "true") {
                    /*redirect the user back to the index page*/
                    window.location = "/index.jsp";
                } else if (response["successfulLogout"] == "false") {
                    if(debugging) console.log("There was an error Logging out!");
                    alert("There was an issue logging out! [sorry]");
                    window.location = "/index.jsp";
                }
            }
        });
    }
    /*Click listeners*/
    $('#logout').on('click', function() {
        if(debugging) console.log("clicking logout!");
        logout();

    });
});

