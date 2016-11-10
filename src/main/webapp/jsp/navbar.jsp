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
            <a id="logout" href="/index.jsp"> Logout <span class="glyphicon glyphicon-user"></span></a>
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
</nav>

