package edu.csudh.goTorosBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import org.json.simple.JSONObject;

/**
 * Login Manager - manages user logins. Currently only checks a temp account
 * TODO: Add a return cookie, to provide access to other parts of the application
 * TODO: Change the cookie, and utilize SessionID instead.
 * @author Bradley Taniguchi
 * @see HttpServlet
 */
public class LoginManager extends HttpServlet {

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
        /*TODO: change this code, to do some actual legit checking...*/
        /*need to add a try catch block!!!*/
        if (new DatabaseInterface().validate(
                request.getParameter("userName"),
                request.getParameter("password"))
                )
        {
            response.setContentType("application/json");
            HttpSession userSession = request.getSession();
            userSession.setAttribute("userName", request.getParameter("userName"));
            //set other attributes here
            JSONObject returnJson = new JSONObject();
            returnJson.put("successfulLogin", "true"); // I get a warning here, disregard..
            returnJson.put("message", "Valid Login");
            response.getWriter().write(returnJson.toJSONString());
        } else {
            response.setContentType("application/json");
            JSONObject returnJson = new JSONObject();
            returnJson.put("successfulLogin", "false");
            returnJson.put("message", "Invalid Login");
            response.getWriter().write(returnJson.toJSONString());
        }
    }
    @Override
    public void destroy(){
        getServletContext().log("destroy() called");
    }
}