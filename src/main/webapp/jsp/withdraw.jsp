<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 9/26/16
  Time: 3:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Withdraw</h1>
<hr/>
<form class="form-horizontal">
    <div class="form-group">
        <div class="col-xs-6">
            <label>Account to Withdraw from:</label>
            <form id="userAccounts"></form> <!--place buttons here-->
            <button type="button" id="submit" class="btn btn-default">Submit</button>
        </div>
    </div>
    <div class="form-group">
        <label for="amount">Amount:</label>
        <input type="number" class="form-control" id="amount"/>
    </div>
    <div id="returnModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">X</button>
                    <h4 class="modal-title">Error!</h4>
                </div>
                <div class="modal-body">
                    <p>Some text in the modal.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>
    <script type="text/javascript" src="js/withdrawManager.js"></script>
</form>
