package edu.csudh.goTorosBank;

import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 * Created by Daniel Hernandez on 11/4/16
 * This class will provide a connection from the front-end to the backend
 * and doing the business logic of the use case of a user paying bills
 */

public class PayBillServlet extends HttpServlet
{
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        getServletContext().log("Init() called");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        getServletContext().log("doPost() called");

        JSONObject returnJSON = new JSONObject();
        response.setContentType("application/json");

        /* Use case steps:
         * 1. Load bills
         * 2. Select bill
         * 3. Pay Bill
         */

        HttpSession userSession = request.getSession();
        String username = (String) userSession.getAttribute("userName");

        try
        {
            DatabaseInterface database = new DatabaseInterface();
            User myUser = database.getUser(username);
            Bill payBill = null;
            int billID = 0;

            int accountID = Integer.parseInt(request.getParameter("accountID"));
            billID = Integer.parseInt(request.getParameter("billID"));
            //float amount = Float.parseFloat(request.getParameter("amount")); // Removes amount as requested issue #6


            if(myUser.getUserAccounts().size() == 0)
            {
                returnJSON.put("successfulBillPay", false);
                returnJSON.put("message", "No account to pay bills.");
            }
            else if (billID == 0)
            {
                returnJSON.put("successfulBillPay", false);
                returnJSON.put("message", "No vaild bill selected.");
            }
            else
            {
                Account fromAccount = myUser.getUserAccount(accountID);
                ArrayList<Bill> bills = fromAccount.getBills();

                for(int i = 0; i < bills.size(); i++)
                {
                    if(bills.get(i).getBillID() == billID)
                    {
                        payBill = bills.get(i);
                    }
                }

                if((fromAccount.getAccountBalance() - payBill.getBillAmmount()) < 0)
                {
                    returnJSON.put("successfulBillPay", false);
                    returnJSON.put("message", "Insufficient funds on account GoToros " + fromAccount.getAccountType() + " (# " + fromAccount.getAccountNumber() + ")");
                }
                else
                {
                    database.withdraw(accountID, payBill.getBillAmmount(), "Successfully paid bill " + billID);
                    database.payBill(billID);

                    returnJSON.put("successfulBillPay", true);
                }
            }
        }
        catch (SQLException e)
        {
            returnJSON.put("successfulBillPay", false);
            returnJSON.put("message", "SQLException: " + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            returnJSON.put("successfulBillPay", false);
            returnJSON.put("message", "ClassNotFoundException: " + e.getMessage());
        }
        catch (ParseException e)
        {
            returnJSON.put("successfulBillPay", false);
            returnJSON.put("message", "ParseException: " + e.getMessage());
        }

        response.getWriter().write(returnJSON.toJSONString());
    }

    @Override
    public void destroy()
    {
        getServletContext().log("destroy() called.");
    }
}
