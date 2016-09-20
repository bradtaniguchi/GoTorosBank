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
        <title>ProfileTest</title>

    </head>
    <body>
        <div class="container">
            <jsp:include page="jsp/navbar.jsp"/>
            <div>
                <!--add the header navbar depending if logged in or not-->
                <jsp:include page="jsp/genericFileLoads.jsp"/>
                <div class="row">
                    <div class="col-md-4" id="dropShadow">

                    </div>
                    <div class="col-md-8" id="dropShadow">
                        <h4>Account Overview</h4>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
