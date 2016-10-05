package edu.csudh.goTorosBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import org.json.simple.JSONObject;

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
        /*Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie c : cookies) {
                if(c.getName().equals("userName")) {
                    c.setMaxAge(0);
                    c.setValue(null);
                    response.addCookie(c);
                    break;
                }
            }*/
        HttpSession userSession = request.getSession(false);
        if(userSession != null) {
            userSession.invalidate(); //remove user session, and all objects
            JSONObject responseJson = new JSONObject();
            responseJson.put("successfulLogout", "true"); // I get a warning here, disregard..
            response.getWriter().write(responseJson.toJSONString());
        } else { //there is no session!
            JSONObject responseJson = new JSONObject();
            responseJson.put("successfulLogout", "false"); // I get a warning here, disregard..
            response.getWriter().write(responseJson.toJSONString());
        }
    }
    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }

}
