<%@ page import="edu.csudh.goTorosBank.Account" %>
<%@ page import="edu.csudh.goTorosBank.DatabaseInterface" %>
<%@ page import="edu.csudh.goTorosBank.Transaction" %>
<%@ page import="edu.csudh.goTorosBank.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.lang.reflect.Array" %>

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
            HttpSession sess = request.getSession();
            Object iddd = sess.getAttribute("username");
            String username= iddd.toString();
            User use = data.getUser(username);

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
                //Gets all transactioins from the person from all accounts
                for (Account x :accountList) {
                    trans.addAll(x.getTransactions());
                }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateAndTime = sdf.format(new Date());

                    ArrayList<Transaction> thismonthstrans = new ArrayList<Transaction>();

                    for (Transaction x:trans) {
                        if (x.getDate().contains(dateAndTime.substring(0, 7))){
                            thismonthstrans.add(x);
                        }
                    }


                //this is to sort the ArrayList
                Collections.sort(trans, new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction o1, Transaction o2) {
                        return o2.getDate().compareTo(o1.getDate());
                    }
                });
                if (thismonthstrans.size() == 0){
                    out.print("<tr><td colspan=\"4\">Sorry you have no purchases this month</td></tr>");
                }
                for (Transaction tran : thismonthstrans) {
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