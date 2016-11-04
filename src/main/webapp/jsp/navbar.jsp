<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 9/19/16
  Time: 7:15 PM
--%>
<nav class="navbar navbar-inverse" id="shadowOnly">
    <div class="navbar-header">
        <a class="navbar-brand" href="index.jsp">GoToros! Bank</a>
    </div>
    <ul class="nav navbar-nav">
        <li><a href="profile.jsp">ProfileTest</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">

        <li id="here">
            <script>
                var url = document.URL;
                if(url.includes("index")) {

                }else{
                    document.write("<a id='logout' " +
                            "href='#'>Logout <span class='glyphicon glyphicon-user'>" +
                            "</span></a>");
                }
            </script>
        </li>
    </ul>
</nav>

