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
       
       HttpSession userSession = request.getSession();
       String username = (String) userSession.getAttribute("username");
       
       try{
           DatabaseInterface database = new DatabaseInterface();               
           User myUser = database.getUser(username);
           if (request.getParameter("accountID")==null|| request.getParameter("amount")==null){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "Not valid arguments!");
               //response.getWriter().write(returnJSON.toJSONString());
               return;
           }
           int accountID = Integer.parseInt(request.getParameter("accountID"));
           float amount = Float.parseFloat(request.getParameter("amount"));
           Account accountFrom = myUser.getUserAccount(accountID); 
           //String person_gettingpayed = (String) request.getAttribute("person_gettingpayed");
           String personGettingPaid = request.getParameter("personGettingPaid");
           String billType = request.getParameter("billType");

           //Checks if user has selected an account
           if(myUser.getUserAccounts().size() == 0){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "No account to withdraw from");
           }

           //checks if user has sufficient funds
           else if(0 > (accountFrom.getAccountBalance() - amount)){
               returnJSON.put("successfulWithdraw", false);
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
           else if(personGettingPaid == null){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "There is no person getting payed");
           }
           else if(billType == null){
               returnJSON.put("successfulWithdraw", false);
               returnJSON.put("message", "There is no bill description");
           }
           //creates check for user and makes chages to the database...
           else{   
               
               //response.setContentType("image/jpeg");
               File blueCheck = new File ("blank-blue-check");
               String pathToWeb = getServletContext().getRealPath("/" + blueCheck);
               //File blueCheck = new File(pathToWeb + "blank-blue-check.jpg");
               /* Call Jesus functions here:
               *
               *
               */
                returnJSON.put("pathToWeb", pathToWeb);
               //BufferedImage bufferedImage = ImageIO.read(new File(pathToWeb));
               //OutputStream out = response.getOutputStream();
               //ImageIO.write(bufferedImage, "jpg", out);
               //out.close();
               

                database.withdraw(accountID, amount, username);
                returnJSON.put("successfulWithdraw", true);
                returnJSON.put("message", "Successfully withdrew $" + amount + " from account " + accountID);
          }
       }
       catch(SQLException s){
           returnJSON.put("errorMessage", "Sorry we have a SQLException");
           returnJSON.put("errorMessage2", s);
       }
       catch(ClassNotFoundException cl){
           returnJSON.put("errorMessage", "Sorry we have a ClassNotFoundException");
           returnJSON.put("errorMessage2", cl);
       }
       catch(ParseException p){
           returnJSON.put("successfulWithdraw", false);
           returnJSON.put("message", "ParseException: " + p.getMessage());
       }
       /*added new case, where parseInt finds nothing*/
       catch (NumberFormatException e) {
           returnJSON.put("successfulWithdraw", false);
           returnJSON.put("message", "NumberFormatException " + e.getMessage());
       }
        response.getWriter().write(returnJSON.toJSONString());
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String filename = request.getParameter("filename");
        ServletContext cntx= request.getServletContext();
        String mime = cntx.getMimeType(filename);

        response.setContentType(mime);
        try {
            File file = new File(filename);
            response.setContentLength((int) file.length());

            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            out.close();
            in.close();

            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");//fileName;
        } catch(Exception e) {
            response.getWriter().write(e.getMessage());
        }


    }
    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
    public String[] dateGenerator() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String rawDate = sdf.format(new Date());


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
