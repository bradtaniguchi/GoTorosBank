/**
 * Created by brad on 9/19/16.
 * This script handles the login/index page of the site.
 * Then handles the redirects of the user from the backend.
 */
$(document).ready(function() {
    console.log("loginManager.js loaded");
    /*Main function to submit*/
    function login() {
        console.log("Attempting to login");
        var name = $('#userName').val();
        var pass = $('#userPass').val();
        $.ajax({
            type: 'POST',
            url:'LoginManager',
            data: {
                userName: name,
                password: pass
            },
            success: function(response) {
                console.log("Response Successful");
                if(response["successfulLogin"] == false) {
                    $('#returnDiv').text(response["message"]);
                } else if(response["successfulLogin"] == true) { //if true we should be getting re-directed
                    $('#returnDiv').text(response["message"]);
                    /*Redirect the user here...*/
                    window.location = "/profile.jsp";
                }
            }
        });
    }
    /*Click listener*/
    $('#userPass').keypress(function(e) {
        if(e.which == 13) {
            login();
        }
    });
    $('#submit').on('click', function() {
        login();
    });
});
