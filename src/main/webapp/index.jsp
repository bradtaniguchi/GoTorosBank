<html>
    <head>
        <meta charset="utf-8">
        <title>GoTorosBank</title>
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
                                <label for="username">Name:</label>
                                <input type="text" class="form-control" id="username"/>
                            </div>
                        </div>
                        <div class="form-group" style="margin:10px 50px">
                            <div class="col-xs-6">
                                <label for="userPass">Password:</label>
                                <input type="password" class="form-control" id="userPass"/>
                                <!--<p style="color:red">Bad Input!</p>-->
                            </div>
                        </div>
                        <div class="form-group" style="margin:10px 50px">
                            <div class="col-xs-6">
                                <button type="button" id="submit" class="btn btn-default">Submit</button>
                            </div>
                        </div>
                        <!--modal popup, to use instead of the nasty return div-->
                        <div id="returnModal" class="modal fade" role="dialog">
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
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    </div>
                                </div>

                            </div>
                        </div>
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
