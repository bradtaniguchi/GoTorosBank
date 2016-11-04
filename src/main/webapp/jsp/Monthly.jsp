<%@ page import="edu.csudh.goTorosBank.Account" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="edu.csudh.goTorosBank.Transaction" %>
<%@ page import="edu.csudh.goTorosBank.DatabaseInterface" %>

<!DOCTYPE html>
<!--
<a href="https://www.thesitewizard.com/" target="_blank">thesitewizard.com</a>



<button onclick="myFunction()">Try it</button>

<script>
function myFunction() {
window.open("http://www.w3schools.com");
}
</script>

-->

<html>
<head>
    <style>
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
        }
        .alignright {
            float:right;
        }
        img {
            display: block;
            margin: 0 auto;
            height: 100px;
            width:auto;
        }
        .line{
            width: 112px;
            height: 47px;
            border-bottom: 1px solid black;
            position: absolute;
        }
        h2{

            -webkit-margin-before: 0.83em;
            -webkit-margin-after: 0px;
            -webkit-margin-start: 0px;
            -webkit-margin-end: 0px;
        }
    </style>
</head>
<body>
    <img src="bank-clipart.png" >
    <div id="textbox">
        <h2>Account Number: 200912132</h2>
        <div class="alignleft">
            <p>Crosby Lanham</p>
        </div>
        <div class="alignright">
            <p>Ending Balance: 5000.94</p>
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
        <tr>
            <td>122938</td>
            <td>10/14/2009</td>
            <td>2000</td>
            <td>Macys</td>
        </tr>
            <%
                HttpSession userSession = request.getSession();
                String userName = userSession.getAttribute("userName").toString();

                DatabaseInterface data = new DatabaseInterface();
                ArrayList<Account> accountList = data.getUser(userName).getUserAccounts();
                ArrayList<Transaction> trans = new ArrayList<>();

                for (Account x :accountList) {
                    trans.addAll(x.getTransactions());
                }

                for(int i=0;i<trans.size();i++) {
                    String line = "<tr>" +
                            "<td>" + trans.get(i).getAccount().getAccountNumber() + "</td>" +
                            "<td>" + trans.get(i).getDate() + "</td>" +
                            "<td>" + trans.get(i).getTransactionAmount() + "</td>" +
                            "<td>" + trans.get(i).getTransactionDescription() + "</td>" +
                            "</tr>";
                }
            %>
        </table>
    </body>
</html>