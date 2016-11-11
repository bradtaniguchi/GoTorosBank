<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 9/26/16
  Time: 3:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Withdrawl</h1>
<hr/>
<form class="form-horizontal">
    <div class="form-group">
        <div class="col-xs-6">
            <label>Account to Withdraw from:</label>
            <div id="userAccounts"></div> <!--place the accounts here-->
            <!--Dynamic buttons here-->
            <button type="button" id="submit" class="btn btn-default">Submit</button>
        </div>
    </div>
    <!--Brad QuickNote-->
    <!-- use this to get data:
      http://stackoverflow.com/questions/596351/how-can-i-know-which-radio-button-is-selected-via-jquery
    -->
    <script type="text/javascript" src="js/withdrawManager.js"></script>
</form>
