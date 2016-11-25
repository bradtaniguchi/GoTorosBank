/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csudh.goTorosBank;

/**
 *
 * @author Rudy and Juicy J
 */

import com.lowagie.text.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WithdrawServlet extends HttpServlet{
    private static final String SAVE_DIR = "TempUpload";
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("Init() called");
    }
    
    /**
     * TODO: Finish filling this out....
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    @SuppressWarnings("unchecked") //need this to suppress warnings for our json.put
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
           if (request.getParameter("accountID")==null|| request.getParameter("amount")==null){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "Not valid arguments!");
               response.getWriter().write(returnJSON.toJSONString());
               return;
           }
           int accountID = Integer.parseInt(request.getParameter("accountID"));
           float amount = Float.parseFloat(request.getParameter("amount"));
           Account accountFrom = myUser.getUserAccount(accountID); 
           String person_gettingpayed = (String) request.getAttribute("person_gettingpayed");
           String billType = (String) request.getAttribute("billType");
           
           //Checks to see if user is logged in
           if(database.validate(request.getParameter("username"), request.getParameter("password"))){
               userSession.setAttribute("username", request.getParameter("username"));
               
               returnJSON.put("userLoggedin", true);
               returnJSON.put("message","Valid Login");
           }
           else{
               returnJSON.put("userLoggedin", false);
               returnJSON.put("message","No user Found");
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
           //checks that user withdraws in amounts of 20
           else if(amount%20 != 0){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "Must withdraw in amounts of 20");
           }
           //checks that uer doesn't withdraw more than $500.00
           else if(amount > 500){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "Withdraw amount cannot exceed $500.00");
           }
           else if(person_gettingpayed == null){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "There is no person getting payed");
           }
           else if(billType == null){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "There is no bill decription");
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
               
               
               /**
                * *******************JEUS MODIFICATIONS******************
                */
               String appPath = request.getServletContext().getRealPath("");
               // constructs path of the directory to save uploaded file
               String savePath = appPath + File.separator + SAVE_DIR;

               //Folder Creation 
               File f = null;
               try {
                   f = new File(savePath);
                   if (!f.exists()) {
                       f.mkdir();
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }

               //path to folder
               //this will be the path that we will write the image into
               String folderPath = f.getAbsolutePath();

               //Date generator function call
               String Dates[] = dateGenerator();
               String date = Dates[0]; //date to be written into check
               String dateForWritingToPath = Dates[1]; //date for when saving image to a path
               
               //making the amount from a float to a String
               String amountInWords = new EnglishNumberToWords().convert((long)amount);
               String amountAsString = String.valueOf(amount)+"0";
               //assigning the function returned image to variable writtenCheck
               
               /***********************************************************************************
                * NOTE TO RUDY: NEED TO PASS THE_PERSON_GETTING_PAYED AND BILL_TYPE
                * JUST MAKE SURE THEY ARE STRINGS WHEN PASSED INTO THE FUNCTION
               BufferedImage writtenCheck = writeIntoCheck(downloadFile,amountAsString,amountInWords,date, person_gettingpayed, billType);
               try {
                   //writing created checkImage into the folder path
                   ImageIO.write(writtenCheck, "jpg", new File(folderPath + "/" + person_gettingpayed + "_" 
                           +dateForWritingToPath + "_" + amountAsString + ".jpg"));
               } catch (Exception e) {
                   e.printStackTrace();
               }
               ******************************************************************************************/ 
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
       /*added new case, where parseInt finds nothing*/
       catch (NumberFormatException e) {
           returnJSON.put("successfulWithdraw", false);
           returnJSON.put("message", "NumberFormatException" + e.getMessage());
       }
        response.getWriter().write(returnJSON.toJSONString());
    }
    
    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
 
    public String[] dateGenerator() {

        LocalDateTime localDate = LocalDateTime.now();

        String rawDate = DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate);

        String Datemod[] = rawDate.split("\\W");
        //re-organizing the date format from yyyy/MM/dd to MM/dd/yyyy
        String theDate = Datemod[1] + "/" + Datemod[2] + "/" + Datemod[0];
        //replacing the '/' for '-' so that there could be no error when saving it to a path
        String theDateforSavingCheck = Datemod[1] + "-" + Datemod[2] + "-" + Datemod[0];
        //inserting both modified dates into an array to return to the function call.
        String Dates[] = {theDate, theDateforSavingCheck};

        return Dates;
    }

    public BufferedImage writeIntoCheck(File ImageFile, String theAmount, String amountInWords,
            String dateWrote, String person_payingto, String billType) throws IOException {

        BufferedImage image = null;
        try {
            image = ImageIO.read(ImageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String amount = theAmount;
        String person_gettingPayed = person_payingto;
        String amountinWords = amountInWords;
        String date = dateWrote;
        String bill_type = billType;

        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage img = new BufferedImage(
                w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(g2d.getBackground());
        g2d.fillRect(0, 0, w, h);
        g2d.drawImage(image, 0, -100, null);
        g2d.setPaint(Color.black);
        g2d.setFont(new java.awt.Font("Serif", Font.BOLD, 36));
        //g2d.setFont(new Font("Serif", Font.BOLD, 36));

        FontMetrics fm = g2d.getFontMetrics();
        int x = img.getWidth() - fm.stringWidth(amount) - 100;
        int y = fm.getHeight();
        g2d.drawString(amount, x - 70, y + 335);
        g2d.drawString(person_gettingPayed, x - 800, y + 329);
        g2d.drawString(amountinWords, x - 940, y + 390);
        g2d.drawString(date, x - 340, y + 245);
        g2d.drawString(bill_type, x - 900, y + 530);

        String signature = "Use The Force";
        g2d.setFont(new java.awt.Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 36));
        g2d.drawString(signature, x - 340, y + 530);
        g2d.dispose();
        return img;
        //returns the new created image called img 
    }
}

