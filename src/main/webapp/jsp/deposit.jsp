<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 9/28/16
  Time: 1:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
Deposit page
<form class="form-horizontal">
    <div class="form-group" style="margin:10px 50px">
        <div class="col-xs-6">
            <label for="amount">Deposit:</label>
            <input type="text" class="form-control" id="amount"/>
            <!--<p style="color:red">Bad Input!</p>-->
        </div>
    </div>
    <div class="form-group" style="margin:10px 50px">
        <div class="col-xs-6">
            <button type="button" id="depositSubmit" class="btn btn-default">Submit</button>
        </div>
    </div>
    <div id="returnDiv"></div>
</form>
<!--test to see if we can add the javascript here!-->
<script type="text/javascript" src="js/depositManager.js"></script>

