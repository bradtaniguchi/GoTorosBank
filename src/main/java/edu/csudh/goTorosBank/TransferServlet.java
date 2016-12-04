package edu.csudh.goTorosBank;

import org.json.simple.JSONObject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.http.HttpSession;

/**
 * This class will provide a connection from the front-end to the backend.
 * This class will provide a connection from the front-end to the backend and doing
 * the business logic of the use case of a user transfering money
 * between accounts.
 *
 * Created by brad on 10/27/16.
 */
public class TransferServlet extends HttpServlet
{
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        getServletContext().log("Init() called");
    }

    @Override
    @SuppressWarnings("unchecked") //need this to suppress warnings for our json.put
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        getServletContext().log("doPost() called");

        JSONObject returnJson = new JSONObject();
        response.setContentType("application/json");

        /*use cases steps:
        * 1. GetUser from the database, and check information,
        *   if no user given, return bad state
        * 2. GetUser, and check to see if the accounts picked from the front end are valid
        *   if not then return bad state
        * 3. If they are both valid, verify if the amount is a valid transaction
        *   if not, then return bad state
        * 4. If it is a valid transaction, call the back-end transaction function.*/

        HttpSession userSession = request.getSession();
        String username = (String) userSession.getAttribute("username"); //get the user name, and validate they exist

        try
        {
            DatabaseInterface database = new DatabaseInterface();
            User myUser = database.getUser(username);

            int accountIDFrom = Integer.parseInt(request.getParameter("accountIDFrom"));
            int accountIDTo = Integer.parseInt(request.getParameter("accountIDTo"));
            float amount = Float.parseFloat(request.getParameter("amount"));

            /*This case is when the user has no accounts*/
            if (myUser.getUserAccounts().size() == 0)
            {
                returnJson.put("successfulTransaction", false);
                returnJson.put("message", "No Accounts to transfer between!");
            }
            /*This case is when the user has only 1 account*/
            else if (myUser.getUserAccounts().size() == 1)
            {
                returnJson.put("successfulTransaction", false);
                returnJson.put("message", "You cannot transfer between a single account!");
            }
            else
            {
                /* Get the account with the given ID. */
                Account fromAccount = myUser.getUserAccount(accountIDFrom);

                /* Get the account with the given ID. */
                Account toAccount = myUser.getUserAccount(accountIDTo);

                /*and check to make sure the user can afford the amount to transfer*/
                if (0 > (fromAccount.getAccountBalance()-amount))
                {
                    returnJson.put("successfulTransaction", false);
                    returnJson.put("message", "Insufficient funds in account " + fromAccount.getAccountNumber());
                }
                else
                {
                    /*Transfer the money...*/
                    database.transfer(accountIDFrom, accountIDTo, amount);

                    returnJson.put("successfulTransaction", true);
                    returnJson.put("message", "Successfully transferred: $" + amount + " From account: "
                            + fromAccount.getAccountType() + " to account: " + toAccount.getAccountType());
                }
            }
        }
        catch(SQLException e)
        {
            returnJson.put("successfulTransaction", false);
            returnJson.put("message", "SQLException: " + e.getMessage());
        }
        catch(ClassNotFoundException e)
        {
            returnJson.put("successfulTransaction", false);
            returnJson.put("message", "ClassNotFoundException: " + e.getMessage());
        }
        catch(ParseException e)
        {
            returnJson.put("successfulTransaction", false);
            returnJson.put("message", "ParseException: " + e.getMessage());
        }

        response.getWriter().write(returnJson.toJSONString());
    }

    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
}