<html>
    <head>
        <meta charset="utf-8">
        <title>Test Temp Index</title>
        <link rel="shortcut icon" href="favicon.png"/>
    </head>
    <body id="backgroundColor">
        <div class="container" >
            <jsp:include page="jsp/navbar.jsp"/>
            <div>
                <div class="panel panel-default shadowOnly" id="headerColor"><div class="panel-body">
                    <h1 style="text-align:center">GoToros! Bank</h1>
                    <img src="pictures/bank-clipart.png" style="display: block; margin: 0 auto">
                    <hr/>
                    <form class="form-horizontal">
                        <div class="form-group" style="margin:10px 50px">
                            <div class="col-xs-6">
                                <label for="user">Name:</label>
                                <input type="text" class="form-control" id="userName"/>
                            </div>
                        </div>
                        <div class="form-group" style="margin:10px 50px">
                            <div class="col-xs-6">
                                <label for="password">Password:</label>
                                <input type="password" class="form-control" id="userPass"/>
                                <!--<p style="color:red">Bad Input!</p>-->
                            </div>
                        </div>
                        <div class="form-group" style="margin:10px 50px">
                            <div class="col-xs-6">
                                <button type="button" id="submit" class="btn btn-default">Submit</button>
                            </div>
                        </div>
                        <div id="returnDiv"></div>
                    </form>
                    <br/>
                </div></div>
                <!--add the header navbar depending if logged in or not-->
                <jsp:include page="jsp/genericFileLoads.jsp"/>
                <script type="text/javascript" src="js/loginManager.js"></script>
            </div>
        </div>
    </body>
</html>
