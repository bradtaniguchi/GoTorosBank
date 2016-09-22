package edu.csudh.goTorosBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Login Manager - manages user logins. Currently only checks a temp account
 * TODO: Add a return cookie, to provide access to other parts of the application
 * @author Bradley Taniguchi
 * @see HttpServlet
 */
public class LoginManager extends HttpServlet {
    private String userName;
    private String password;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("Init() called");
        /*TODO: Initialize our 1 temp user ...*/
        userName = "toro";
        password = "gotoro";
    }
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        getServletContext().log("Service() called");
        /*TODO: change this code, to do some actual legit checking...*/
        if(request.getParameter("userName").equals(userName) &&
                request.getParameter("password").equals(password)) {
             
            response.setContentType("application/json");
            response.getWriter().write("{\"test\":\"works\"}");
        }
        else {
            response.setContentType("application/json");
            response.getWriter().write("{\"test\":\"no works\"}");
        }
    }

    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
}