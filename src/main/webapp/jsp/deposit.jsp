<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 9/28/16
  Time: 1:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
Deposit page


<div class="form-group">
    <input type="file" id="input">
    <label for="account">Select Account to deposit <u>To</u>:</label>
    <select class="form-control" id="account">
    </select> <!--add accounts dynamically-->

    <label for="description">Description</label>
    <input type="text" class="form-control" id="description"/>

    <button type="button" id="finalSubmit" class="btn btn-default">Deposit bill</button>
</div>
<div id="returnDiv"></div>
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
<!--test to see if we can add the javascript here!-->
<script type="text/javascript" src="js/depositManager.js"></script>


