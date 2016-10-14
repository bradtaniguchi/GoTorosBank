package edu.csudh.goTorosBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by brad on 10/14/16.
 * This class acts a servlet that provides basic IO "console-like" output to the front-end
 * Provide classes and functions that you want to test, below...
 * TODO: Migrate this code to a unit test
 */
public class developmentHelper extends HttpServlet {
    private String html;
    private boolean result;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("Init() called");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        getServletContext().log("[developmentHelper] doGet() called");

        response.setContentType("text/html"); //this response will be directly placed onto the page!
        /*Validate User test*/
        DatabaseInterface myInterface = new DatabaseInterface();
        try {
            html = "<h2>Testing myInterface.validate</h2>:\n<p>Results</p>:";
            result = myInterface.validate("toro", "password"); //check only 1 case
            html += result; //returns the result..

        } catch(ClassNotFoundException e) {
            html += "<h2>ClassNotFound ERROR</h2>: " + e.getMessage();
        } catch(SQLException e) {
            html += "<h2>SQLException ERROR</h2>: " + e.getMessage();
        }
        response.getWriter().write(html);
    }
    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
}
