/**
 * Created by brad on 9/19/16.
 * This script handles the login/index page of the site.
 * Then handles the redirects of the user from the backend.
 */
$(document).ready(function() {
    //console.log("loginManager.js loaded");
    /*Main function to submit*/
    function login() {
        //console.log("Attempting to login");
        var name = $('#userName').val();
        var pass = $('#userPass').val();
        if ( !name.length || !pass.length) {
            showModal("Login Error", "No Username or Password Found!", "okey");
        } else {
            $.ajax({
                type: 'POST',
                url: 'LoginManager',
                data: {
                    userName: name,
                    password: pass
                },
                success: function (response) {
                    //console.log("Response Successful");
                    if (response["successfulLogin"] == false) {
                        showModal("Login Error", response["message"], "ok");

                    } else if (response["successfulLogin"] == true) { //if true we should be getting re-directed
                        /*Redirect the user here...*/
                        window.location = "/profile.jsp";
                    }
                },
                error: function (xhr, status, error) {
                    var errorMessage = "xhr: " + xhr.responseText;
                    errorMessage += "\nStatus: " + status;
                    errorMessage += "\nError: " + error;
                    showModal("AJAX ERROR" + errorMessage, "ok");

                    console.log(xhr.responseText);
                    console.log("Stats: " + status);
                    console.log("Error: " + error);
                }
            });
        }
    }

    function showModal(title, message, buttonprompt) {
        var returnModal = $('#returnModal'); //my return modal that I can use to return message to the user
        //var modalTitle = returnModal.add(".modal-title");//select the title
        returnModal.find('.modal-header .modal-title').text(title);
        returnModal.find('.modal-body').text(message);
        returnModal.find('.modal-footer .btn').text(buttonprompt);

        returnModal.modal('show'); //show the modal
    }
    /*Click listener*/
    $('#userName').keypress(function(e){
        if(e.which == 13) {
            login();
        }
    }) ;
    $('#userPass').keypress(function(e) {
        if(e.which == 13) {
            login();
        }
    });
    $('#submit').on('click', function() {
        login();
    });
});
