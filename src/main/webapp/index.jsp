<html>
    <head>
        <title>Test Temp Index</title>
    </head>
    <body>
        <div class="container">
            <jsp:include page="jsp/navbar.jsp"/>
            <div id="dropShadow">
                <h1 style="text-align:center">GoToros! Bank</h1>
                <img src="pictures/bank-clipart.png" style="display: block; margin: 0 auto">
                <hr/>
                <form class="form-horizontal">
                    <div class="form-group" style="margin:10px 50px">
                        <div class="col-xs-6">
                            <label for="user">Name:</label>
                            <input type="text" class="form-control" id="userName">
                        </div>
                    </div>
                    <div class="form-group" style="margin:10px 50px">
                        <div class="col-xs-6">
                            <label for="password">Password:</label>
                            <input type="text" class="form-control" id="userPass">
                            <!--<p style="color:red">Bad Input!</p>-->
                        </div>
                    </div>
                    <div class="form-group" style="margin:10px 50px">
                        <div class="col-xs-6">
                            <button type="button" class="btn btn-default">Submit</button>
                        </div>
                    </div>
                </form>
                <br/>
                <!--jquery implimentation test-->
                <button type="button" class="btn btn-default" id="testButton">Clickme!</button>
                This button gets data from the backend...
                <div id="returnDiv"></div>
                <!--add the header navbar depending if logged in or not-->
                <jsp:include page="jsp/genericFileLoads.jsp"/>
                <script type="text/javascript" src="js/basicCall.js"></script>
            </div>
        </div>
    </body>
</html>
