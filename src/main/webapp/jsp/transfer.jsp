<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 10/12/16
  Time: 7:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Transfer Between Accounts</h1>

<div class="form-group">
    <label for="bankAccountFrom">Select Account to transfer <u>From</u>:</label>
    <select class="form-control" id="bankAccountFrom">
    </select>
</div>
<div class="form-group">
    <label for="bankAccountTo">Select Account to transfer <u>To</u>:</label>
    <select class="form-control" id="bankAccountTo">
    </select>
</div>
<div class="form-group">
    <label for="amount">Amount:</label>
    <input type="number" class="form-control" id="amount"/>
</div>
<div class="form-group">
    <button type="button" id="transferSubmit" class="btn btn-default">Transfer</button>
</div>
<div id="statusDiv"></div> <!--return any errors, status statements here-->
<!-- Modal -->
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
<script type="text/javascript" src="js/transferManager.js"></script>