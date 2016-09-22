/**
 * Created by brad on 9/19/16.
 */
console.log("basicCall was called")
$(document).ready(function() {
    /*Main function to submit*/
    function submitData() {
        console.log("Button Clicked");
        var name = $('#userName').val();
        var pass = $('#userPass').val();
        $.ajax({
            url:'LoginManager',
            data: {
                userName: name,
                password: pass
            },
            success: function(response) {
                console.log("Response Successful");
                if(response["test"] == "no works") {
                    $('#returnDiv').text("no works");
                }else {
                    $('#returnDiv').text("works");
                }

            }
        });
    }
    /*Click listener*/
    $('#userPass').keypress(function(e) {
        if(e.which == 13) {
            submitData();
        }
    });
    $('#submit').on('click', function() {
        submitData();
    });
});
