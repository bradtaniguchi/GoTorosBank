/**
 * Created by brad on 9/19/16.
 */
console.log("basicCall was called")
$(document).ready(function() {
   $('#testButton').on('click', function() {
       console.log("jquery ready!");
       $.get('/LoginManager', function(data) {
          $('#returnDiv').text(data); //might have to change parts of this
       });
   });
});
