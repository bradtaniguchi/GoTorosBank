<%@ page import="edu.csudh.goTorosBank.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="edu.csudh.goTorosBank.Account" %>
<%@ page import="edu.csudh.goTorosBank.DatabaseInterface" %><%--
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
            <label for="Account">Account to Withdraw from:</label>
            <!--Dynamic buttons here-->
            <%
                DatabaseInterface data = new DatabaseInterface();
                User use = data.getUser("toro");

                ArrayList<Account> accounts = use.getUserAccounts();

                for(Account x:accounts){
                    out.print("<div class='radio'><label>");
                    out.print("<input type='radio' name='optradio'>");
                    out.print(x.getAccountType());
                    out.print("</label></div>");
                }
            %>
            <button type="button" id="submit" class="btn btn-default">Submit</button>
        </div>
    </div>
    <!--Brad QuickNote-->
    <!-- use this to get data:
      http://stackoverflow.com/questions/596351/how-can-i-know-which-radio-button-is-selected-via-jquery
    -->
</form>
