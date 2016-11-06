<%@ page import="edu.csudh.goTorosBank.DatabaseInterface" %>
<%@ page import="edu.csudh.goTorosBank.Account" %>
<%@ page import="edu.csudh.goTorosBank.Transaction" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="edu.csudh.goTorosBank.User" %>

<html>
<head>
    <style>
        #textbox{
            text-align: left;
        }
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 2px solid #000000;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
        .alignleft,.alignright{
            font-size:20px;
            font-weight:bold;
        }
        .alignleft{
            float:left;
            width: 40%;
        }
        .alignright {
            float:right;
            width: 40%;
        }
        img {
            display: block;
            margin: 0 auto;
            height: 100px;
            width:auto;
        }
        h2, h3{

            -webkit-margin-before: 0.40em;
            -webkit-margin-after: 0;
            -webkit-margin-start: 0;
            -webkit-margin-end: 0;
        }
    </style>
</head>
<body>
    <div align="center" style="border: 2px solid black; width: 95%; padding: 10px 30px 10px 30px;">
        <%
            DatabaseInterface data = new DatabaseInterface();
            ArrayList<Account> accountList = null;
            User use = null;
            String username = "toro";
            try {
                use = data.getUser(username);
                accountList = use.getUserAccounts();
            } catch (ParseException e){
                e.printStackTrace();
            }catch (SQLException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        %>
        <img src="../pictures/bank-clipart.png" >
        <div id="textbox">
            <%
                for(Account x:accountList)
                    out.print("<h3>Account Number: "+x.getAccountNumber()+
                    "</h3>");
            %>
            <div class="alignleft">
                <h2><%out.print(use.getUserFirstName()+" "+use.getUserLastName());%></h2>
                <h3><%out.print(use.getUserAccountName());%></h3>
            </div>
            <div class="alignright">
                <p>Ending Balance: <%out.print(use.getTotalbalance());%></p>
                <button onclick="window.print();">Print</button>
            </div>
        </div>
        <div style="clear: both;"><p></br></p></div>
        <table>
            <tr>
                <th>Account</th>
                <th>Date</th>
                <th>Amount</th>
                <th>Description</th>
            </tr>
                <%
                ArrayList<Transaction> trans = new ArrayList<Transaction>();

                for (Account x :accountList) {
                    trans.addAll(x.getTransactions());
                }

                for (Transaction tran : trans) {
                    String line = "<tr>" +
                            "<td>" + tran.getAccount().getAccountNumber() + "</td>" +
                            "<td>" + tran.getDate() + "</td>" +
                            "<td>" + tran.getTransactionAmount() + "</td>" +
                            "<td>" + tran.getTransactionDescription() + "</td>" +
                            "</tr>";
                    out.print(line);
                }
                %>
            </table>
        </div>
    </body>
</html>