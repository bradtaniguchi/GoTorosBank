package edu.csudh.goTorosBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

import org.json.simple.JSONObject;

/**
 * Login Manager - manages user logins.
 *
 * @author Bradley Taniguchi
 * @see HttpServlet
 */
public class LoginManager extends HttpServlet
{
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        getServletContext().log("Init() called");
    }

    @Override
    @SuppressWarnings("unchecked") //need this to suppress warnings for our json.put
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        getServletContext().log("doPost() called");

        JSONObject returnJson = new JSONObject();
        response.setContentType("application/json");

        try
        {
            if (new DatabaseInterface().validate(request.getParameter("username"), request.getParameter("password")))
            {
                HttpSession userSession = request.getSession();
                userSession.setAttribute("username", request.getParameter("username"));

                returnJson.put("successfulLogin", true);
                returnJson.put("message", "Valid Login");
            }
            else
            {
                returnJson.put("successfulLogin", false);
                returnJson.put("message", "Invalid Login");
            }
        }
        catch (SQLException s)
        {
            returnJson.put("successfulLogin", false);
            returnJson.put("errormessage","Sorry we have a SQLException");
            returnJson.put("errormessage2",s);
        }
        catch (ClassNotFoundException cl)
        {
            returnJson.put("successfulLogin", false);
            returnJson.put("errormessage","Sorry we have a ClassNotFoundProblem");
            returnJson.put("errormessage2",cl);
        }

        response.getWriter().write(returnJson.toJSONString());
    }

    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
}