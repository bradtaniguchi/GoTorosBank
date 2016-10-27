package edu.csudh.goTorosBank;

import org.json.simple.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by brad on 10/27/16.
 * This class will provide a connection from the front-end to the backend
 * and doing the business logic of the use case of a user transfering money
 * between accounts.
 */
public class TransferServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("Init() called");
    }

    @Override
    @SuppressWarnings("unchecked") //need this to suppress warnings for our json.put
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
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
    }

    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }

}
