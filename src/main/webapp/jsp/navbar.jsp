<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 9/19/16
  Time: 7:15 PM
--%>
<nav class="navbar navbar-inverse" id="shadowOnly"  style="padding-right: 20px;">
    <div class="navbar-header">
        <a class="navbar-brand" href="index.jsp">GoToros! Bank</a>
    </div>
    <ul class="nav navbar-nav" >
        <li><a href="profile.jsp">ProfileTest</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">

        <li id="here">
            <a id="logout" style="cursor:pointer"> Logout <span class="glyphicon glyphicon-user"></span></a>
            <script>
                /*quick and dirty script to hide the logout button if your on the index page*/
                var url = window.location.pathname;
                var logoutButton = document.getElementById("logout");
                console.log("location: " + url);
                logoutButton.style.display = 'block';
                if(url == "/index.jsp" || url == "/") { //if on these pages, don't show it tho!
                    console.log("Hiding element");
                    logoutButton.style.display = 'none';
                }
            </script>
        </li>
    </ul>
    <!--modal popup, to use instead of the nasty return div-->
    <div id="navbarModal" class="modal fade" role="dialog">
        <div class="modal-dialog modal-sm">
            <!--modal contents-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" style="color:black">ERROR!</h4> <!--access via modal-title-->
                </div>
                <div class="modal-body" style="color:black"> <!--access via modal-body-->
                    <p>ERROR! GENERIC ERROR, NO ERROR SPECIFIED!</p>
                </div>
                <div class="modal-footer">
                    <button id="YesLogout" type="button" class="btn btn-default">Yes</button>
                    <button id="NoLogout" type="button" class="btn btn-default" data-dismiss="modal">No</button>
                </div>
            </div>

        </div>
    </div>
</nav>

