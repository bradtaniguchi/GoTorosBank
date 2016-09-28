package edu.csudh.goTorosBank;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Bradley Taniguchi
 * 9/28/16
 * Temp Deposit manager
 */
public class DepositManager extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("Init() called");
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        getServletContext().log("doPost() called");
        //response.setContentType("application/json"); //change this later

        HttpSession userSession = request.getSession();
        if(userSession != null ) { /*if no user session*/
            String userName = (String) userSession.getAttribute("userName");
            if(userName != null) { /*if there is no userName attribute*/
                /*get what the user wants to request from database*/
                String amount = request.getParameter("amount");

                /*Since this isn't implimented yet, just return success, and money*/
                response.getWriter().write("[backend] " + userName + " Deposited this amount: " + amount);

            } else {
             response.getWriter().write("ERROR, No user logged in!");
            }
        } else {
            response.getWriter().write("ERROR, No Session Found!");
            /*Redirect the user here to error jsp page?*/
        }
    }
    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
}
