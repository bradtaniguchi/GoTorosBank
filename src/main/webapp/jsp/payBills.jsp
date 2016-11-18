<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 10/12/16
  Time: 7:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="org.sqlite.*" %>
<%@ page import="edu.csudh.goTorosBank.DatabaseInterface" %>
<h1>Pay Bills</h1>

<!--<table border="1" width="100%">-->
<table class="table">
    <tr>
       <!-- <th>Bill ID</th>-->
        <th>Bill Name</th>
        <th>Bill Description</th>
        <th>Bill Amount</th>
        <th>Bill Due Date</th>
        <th>Bill Status</th>
    </tr>
    <% try {
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite::resource:GoTorosBank.db");
        Statement statement = c.createStatement();
        HttpSession sess = request.getSession();
        Object id = sess.getAttribute("username");
        String username = id.toString();

        DatabaseInterface database = new DatabaseInterface();
        ResultSet resultSet = statement.executeQuery("SELECT *"
                + "FROM BILLS WHERE UID = " + database.getUser(username).getId() + ";");
        while (resultSet.next()) {
            out.println("<tr>");
            //out.println("<td>" + resultSet.getInt("BILL_ID") + "</td>");
            out.println("<td>" + resultSet.getString("BILL_NAME") + "</td>");
            out.println("<td>" + resultSet.getString("BILL_DESCRIPTION") + "</td>");
            out.println("<td>" + resultSet.getDouble("BILL_AMOUNT") + "</td>");
            out.println("<td>" + resultSet.getString("BILL_DUE_DATE") + "</td>");
            out.println("<td>" + resultSet.getString("BILL_STATUS") + "</td>");
            out.println("</tr>");
        }

        resultSet.close();
        c.close();
    } catch ( ClassNotFoundException e) {
        out.println("JSP ClassNotFound Exception: " + e.getMessage());
    } catch (SQLException e) {
        out.println("JSP SQLException: " + e.getMessage());
    }
    %>
</table>
    <div class="form-group">
            <label for="billToPay">Select bill to pay:</label>
            <select class="form-control" id="billToPay"></select>
    </div>
    
    <div class="form-group">
        <label for="accountToPayFrom">Select bank account to make payment from:</label>
        <select class="form-control" id="accountToPayFrom"></select>
    </div>

    <div class="form-group">
            <button type="button" id="makePayment" class="btn btn-default">Make Payment</button>
    </div>

    <div id="statusDiv"></div>
    <script type="text/javascript" src="js/payBillsManager.js"></script>
