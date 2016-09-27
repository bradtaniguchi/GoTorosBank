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
    <body id="backgroundColor">
        <div class="container">
            <jsp:include page="jsp/navbar.jsp"/>
            <div>
                <!--add the header navbar depending if logged in or not-->
                <jsp:include page="jsp/genericFileLoads.jsp"/>
                <div class="row">
                    <div class="col-md-4">
                        <div class="panel panel-default">
                            <div class="panel-heading" id="headerColor"><h4>User Tools</h4></div>
                            <div class="panel-body">
                                <jsp:include page="jsp/UserTools.jsp"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8" id="mainActivity">
                        <!--allow this to be dynamically loaded-->
                        <div class="panel panel-default">
                            <div class="panel-heading" id="headerColor"><h4>Account Overview</h4></div>
                            <div class="panel-body">
                                <jsp:include page="jsp/AccountOverview.jsp"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="js/logoutManager.js"></script>
        <script type="text/javascript" src="js/dynamicLoader.js"></script>
        <!--add other scripts to see if user logged in here?-->
    </body>
</html>
