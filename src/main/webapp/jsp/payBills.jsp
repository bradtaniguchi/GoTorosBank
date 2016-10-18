<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 10/12/16
  Time: 7:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>paybills</h1>
<hr/>
<form class="form-horizontal">
    <div class="form-gorup">
        <div class="col-xs-6">
            <label for="Bill">Select bill to pay:<label/>
            <select class="form-control" id="billToPay">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
            </select>
        </div>
    </div>
    
    <div class="form-gourp">
        <label for="bankAccountFrom">Select bank account to make payment from:</label>
        <select class="form-control" id="bankAccountFrom"><!--???-->
            <option>1</option>
            <option>2</option>
        </select>
    </div>
    
    <div class="form-group" style="margin: 10px 50px">
        <div class="col-xs-6">
            <label for="ammont">Amount:</label>
            <input type="text" class="form-control" id="amount"><!--??-->
        </div>
    </div>
    
    <div class="form-gorup" style="margin:10px 50px">
        <div class="col-xs-6">
            <button type="button" id="makePayment" class="btn btn-default">Make Payment</button>
        </div>
    </div>
    
    <div id="statusDiv"></div><!--or should it be returnDiv-->
    <script type="text/javascript" src="js/payBillsManager.js"></script>
