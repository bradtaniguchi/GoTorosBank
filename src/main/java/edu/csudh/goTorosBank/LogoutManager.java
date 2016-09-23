package edu.csudh.goTorosBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Logout Manager - removes the user's credentials from the browser, thus removing access until login again.
 * @author Bradley Taniguchi
 * @see HttpServlet
 */
public class LogoutManager extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("Init() called");
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        getServletContext().log("doPost() called");
        response.setContentType("application/json");

        /*get the cookies from the user's browser*/
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie c : cookies) {
                if(c.getName().equals("userName")) {
                    c.setMaxAge(0); /*set the age to instantly disappear*/
                    c.setValue(null);
                    response.addCookie(c); /*return the modified cookie*/
                    break;
                }
            }

            response.getWriter().write("{\"successfulLogout\":\"true\"}");
        } else { //there are no cookies
            response.getWriter().write("{\"successfulLogout\":\"false\"}");
        }
    }
    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }

}
