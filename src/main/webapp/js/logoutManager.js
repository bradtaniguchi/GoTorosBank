/**
 * Created by brad on 9/23/16.
 * This script is attached to the navbar, and provides functionality to
 * Login/logout of the entire site. This calls the LogoutManager class
 * in the backend.
 */
var debugging = true;
$(document).ready(function() {
    function logout() {
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
    function showModal(title, message) {
        var returnModal = $('#navbarModal'); //my return modal that I can use to return message to the user
        //var modalTitle = returnModal.add(".modal-title");//select the title
        returnModal.find('.modal-header .modal-title').text(title);
        returnModal.find('.modal-body').text(message);

        returnModal.modal('show'); //show the modal
    }

    /*Click listeners*/
    $('#logout').on('click', function() {
        showModal("Logout?", "Would you Like to Logout of GoTorosBank?");
    });
    /*This is if they clicked on yes*/
    $('#YesLogout').on('click', function() {
        logout();
    });
});

