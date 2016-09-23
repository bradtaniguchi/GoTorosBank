<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 9/19/16
  Time: 7:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>ProfileTest</title>
        <link rel="shortcut icon" href="favicon.png"/>
    </head>
    <body>
        <div class="container">
            <jsp:include page="jsp/navbar.jsp"/>
            <div>
                <!--add the header navbar depending if logged in or not-->
                <jsp:include page="jsp/genericFileLoads.jsp"/>
                <div class="row">
                    <div class="col-md-4" id="dropShadow">
                        <jsp:include page="jsp/UserTools.jsp"/>
                    </div>
                    <div class="col-md-8" id="dropShadow">
                        <jsp:include page="jsp/AccountOverview.jsp"/>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="js/logoutManager.js"></script>
        <!--add other scripts to see if user logged in here?-->
    </body>
</html>
