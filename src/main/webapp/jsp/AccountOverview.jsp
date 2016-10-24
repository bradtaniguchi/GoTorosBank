<%--
  Created by IntelliJ IDEA.
  User: brad
  Date: 9/20/16
  Time: 4:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Bank Account Information</h1>
<div>
    <h3>Welcome: <c:out value="${sessionScope.userName}"/></h3> <!--EL, or expression language-->
    <label><h3>Accounts:</h3></label>
    <p id="numberOfAccounts">Number of Accounts:  0</p>
</div>
<div id="accounts"></div>
<script type="text/javascript" src="js/accountOverviewManager.js"></script>