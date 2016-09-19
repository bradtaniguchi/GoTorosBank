package edu.csudh.GoTorosBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class loginManager extends HttpServlet {
    String userName;
    String passWord;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("Init() called");
    }
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        getServletContext().log("Service() called");
        response.getWriter().write("This is the Reponse");
    }

    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
}