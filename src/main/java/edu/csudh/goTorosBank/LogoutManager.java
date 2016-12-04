package edu.csudh.goTorosBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import org.json.simple.JSONObject;

/**
 * Logout Manager - ends the user's session and logs them out from the website.
 * removes the user's credentials. from the browser, thus removing access until login again.
 * @author Bradley Taniguchi
 * @see HttpServlet
 */
public class LogoutManager extends HttpServlet
{
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        getServletContext().log("Init() called");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        getServletContext().log("doPost() called");
        response.setContentType("application/json");

        HttpSession userSession = request.getSession(false);

        if(userSession != null)
        {
            userSession.invalidate(); //remove user session, and all objects

            JSONObject responseJson = new JSONObject();

            responseJson.put("successfulLogout", "true");
            response.getWriter().write(responseJson.toJSONString());
        }
        else
        {
            //there is no session!
            JSONObject responseJson = new JSONObject();

            responseJson.put("successfulLogout", "false");
            response.getWriter().write(responseJson.toJSONString());
        }
    }

    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
}