/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csudh.goTorosBank;

/**
 *
 * @author Rudy
 */

import java.io.*;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WithdrawlServlet extends HttpServlet{
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("Init() called");
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       getServletContext ().log("goGet () called");
       
       JSONObject returnJSON = new JSONObject();
       response.setContentType("application/json");
       
       /* Check Case Step:
        * 1. All Good
        * 2. User Not Logged In
        * 3. User Has Accounts
        * 4. Check Amount
        *      a. check if sufficient funds in balance
        *      b. Check that user is withdrawing in ammount of 20
        *      c. Put limit on amount to withdraw
        *********************
        * - Make Check
        * - Make changes in the Database
        */
       
       HttpSession userSession = request.getSession();
       String username = (String) userSession.getAttribute("username");
       
       try{
           DatabaseInterface database = new DatabaseInterface();               
           User myUser = database.getUser(username);                                                                     
                                                                                
           int accountID = Integer.parseInt(request.getParameter("accountID")); 
           float amount = Float.parseFloat(request.getParameter("ammount"));   
           Account accountFrom = myUser.getUserAccount(accountID);              
           
           //Checks to see if user is logged in
           if(database.validate(request.getParameter("username"), request.getParameter("password"))){
               userSession.setAttribute("username", request.getParameter("username"));
               
               returnJSON.put("userLoggedin", true);
               returnJSON.put("message","Valid Login");
           }
           else{
               returnJSON.put("userLoggedin", false);
               returnJSON.put("message","Invalid Loggedin");
           }
           
           //Checks if user has selected an account
           if(myUser.getUserAccounts().size() == 0){
               returnJSON.put("succesfulWithdrawl", false);
               returnJSON.put("message", "No account to withdraw from");
           }
           //checks that user only selects one account
           else if(myUser.getUserAccounts().size() > 1 ){
               returnJSON.put("succesfulWithdrawl", false);
               returnJSON.put("message", "You cannot withdraw from both account at the same time");
           }
           //checks if user has sufficient funds
           else if(0 > (accountFrom.getAccountBalance() - amount)){
               returnJSON.put("succesfulWithdraw", false);
               returnJSON.put("message", "Insufficient funds in account" + accountFrom.getAccountNumber());   
           }
           //checks that user withdraws in amouonts of 20
           else if(amount%20 != 0){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "Must withdraw in ammounts of 20");
           }
           //checks that uer doesn't withdraw more than $500.00
           else if(amount > 500){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "Withdraw ammount cannot exceed $500.00");
           }
           //creates check for user and makes chages to the database...
           else{       
               File checkImage = new File ("/src/main/resources/checkImage.png");
               String absolutePath = checkImage.getAbsolutePath();
               File downloadFile = new File (absolutePath);
               FileInputStream inStream = new FileInputStream(downloadFile);
                      
               ServletContext context = getServletContext();
        
               String mimeType = context.getMimeType(absolutePath);
               if (mimeType == null){
               mimeType = "application/octet-stream";
               }
               System.out.println("MIME Type: " + mimeType);
        
               response.setContentType(mimeType);
               response.setContentLength((int) downloadFile.length());
        
               String headerKey = "Content-Diposition";
               String headerValue = String.format("attachment; filename = \"%s\"", downloadFile.getName());
               response.setHeader(headerKey, headerValue);
        
               OutputStream outStream = response.getOutputStream();
        
               byte[] buffer = new byte [4096];
               int bytesRead = -1;
        
               while((bytesRead = inStream.read(buffer)) != -1){
               outStream.write(buffer, 0, bytesRead);
               }
        
        inStream.close();
        outStream.close();
        
        database.withdraw(accountID, amount, username);
        returnJSON.put("successfulWithdraw", true);
        returnJSON.put("massage", "Successfully withdrew $" + amount + " from account " + accountID);
        
        
          }
       }
       catch(SQLException s){
           returnJSON.put("userLoggedin", false);
           returnJSON.put("errormessage", "Sorry we have a SQLException");
           returnJSON.put("errormessage2",s);
       }
       catch(ClassNotFoundException cl){
           returnJSON.put("userLoggedin", false);
           returnJSON.put("errormessage", "Sorry we have a ClassNotFoundException");
           returnJSON.put("errormessage2",cl);
       }
       catch(ParseException p){
           returnJSON.put("successfulWithdraw", false);
           returnJSON.put("message", "ParseException: " + p.getMessage());
       }
    }
    
    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
}

