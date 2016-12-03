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

<div class="form-group">

    <label for="btn-group">Amount:</label>
    <br/>
    <!--Button group-->
    <div id="btn-group" class="btn-group" data-toggle="buttons">
        <label class="btn btn-default selected-amount">
            <input type="radio" name="options" value="20" autocomplete="off"> $20
        </label>
        <label class="btn btn-default selected-amount">
            <input type="radio" name="options" value="40" autocomplete="off"> $40
        </label>
        <label class="btn btn-default selected-amount">
            <input type="radio" name="options" value="60" autocomplete="off"> $60
        </label>
        <label class="btn btn-default selected-amount">
            <input type="radio" name="options" value="80" autocomplete="off"> $80
        </label>
        <label class="btn btn-default selected-amount">
            <input type="radio" name="options" value="100" autocomplete="off"> $100
        </label>
        <label class="btn btn-default selected-amount">
            <input type="radio" name="options" value="200" autocomplete="off"> $200
        </label>
        <label class="btn btn-default selected-amount">
            <input type="radio" name="options" value="400" autocomplete="off"> $400
        </label>
    </div>
    <!--End Button Group-->
</div>
<div class="form-group">
    <br/>
    <label for="person-getting-paid">To</label>
    <input type="text" class="form-control" id="person-getting-paid"/>
    <br/>
    <label for="userAccounts">Account to withdraw from:</label>
    <select class="form-control" id="userAccounts"></select>
    <br/>
    <label for="memo">Memo</label>
    <input type="text" class="form-control" id="memo"/>
    <br/>
    <button type="button" id="submit" class="btn btn-default">Withdraw</button>
    <div id="image-return"></div>
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
