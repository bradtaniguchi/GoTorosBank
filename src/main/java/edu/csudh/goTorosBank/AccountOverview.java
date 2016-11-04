package edu.csudh.goTorosBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Creates an overview of all the accounts for a user.
 * Created by brad on 10/19/16.
 * This class returns all information for current logged in user
 */
public class AccountOverview extends HttpServlet {
    private JSONObject json;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("Init() called");
    }

    @Override
    @SuppressWarnings("unchecked") //need this to suppress warnings for our json.put
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username;
        User user;
        DatabaseInterface database = new DatabaseInterface();
        JSONObject json = new JSONObject();

        getServletContext().log("doPost() called");

        /*gets the username from the session*/
        HttpSession userSession = request.getSession();
        username = userSession.getAttribute("userName").toString();

        /*get a User Object from the database that has all the information.*/
        response.setContentType("application/json");
        try {
            user = database.getUser(username);
            json.put("successfulQuery", true);
            /*get the user*/
            json.put("userAccounts", user.toJSON());
            json.put("accounts", user.getUserAccounts().size()); //returns how many accounts

        } catch(ClassNotFoundException e) {
            json.put("successfulQuery", false);
            json.put("message", "username: " + username + e.getMessage());

        } catch(SQLException e) {
            json.put("successfulQuery", false);
            json.put("message", e.getMessage());
        } catch (ParseException e){
            json.put("successfulQuery", false);
            json.put("message", e.getMessage());
        }
        response.getWriter().write(json.toJSONString());
    }
    @Override
    public void destroy(){
        getServletContext().log("destroy() called");
    }
}

