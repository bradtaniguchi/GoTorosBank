/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csudh.goTorosBank;

/**
 *
 * @author Rudy and Juicy J
 * TODO:
 * 1. Date in Jesus' function
 * 2. Impleement the amount in words class in Jesus' function
 * 3. Pass the REASON from front-end to Jesus' function (rudy)
 * 4. Get the Full name from the database, and put it on where it says "use the force"
 *      pass to Jesus's function
 * 5. Where it says BULLSHIT, use the memo from the parameters.
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
           String personGettingPaid = request.getParameter("personGettingPaid");
           String billType = request.getParameter("billType");
           String memo = request.getParameter("memo");

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
           //creates check for user and makes changes to the database...
           else{   
               
               //response.setContentType("image/jpeg");
               File blueCheck = new File ("blank-blue-check");
               String pathToWeb = getServletContext().getRealPath("/" + blueCheck);
               //File blueCheck = new File(pathToWeb + "blank-blue-check.jpg");
               returnJSON.put("pathToWeb", pathToWeb);

               String fullpath = writeIntoCheck(pathToWeb, username, Float.toString(amount), "AMOUNT IN WORDS",
                       "DATE", personGettingPaid, "BULLSHIT");
               String[] fullpathSplit= fullpath.split("/");
               String filename = fullpathSplit[fullpathSplit.length-1];
               database.withdraw(accountID, amount, username);
               returnJSON.put("filename", filename);
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
        //File blueCheck = new File("blank-blue-check.jpg");
        File blueCheck = new File(request.getParameter("filename"));
        String pathToWeb = getServletContext().getRealPath("/" + blueCheck);

        ServletContext cntx= request.getServletContext();
        String mime = cntx.getMimeType(pathToWeb);

        response.setContentType(mime);
        try {
            File file = new File(pathToWeb);
            response.setContentLength((int) file.length());

            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            out.close();
            in.close();

            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + blueCheck.getName() + "\"");//fileName;
        } catch(Exception e) {
            response.getWriter().write(e.getMessage());
        }


    }
    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }
    public String fileNameGenerator(String username) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        return sdf.format(new Date()) + "_" + username;
    }
    public String writeIntoCheck(String filePath, String username, String theAmount, String amountInWords,
            String dateWrote, String person_payingto, String billType) throws IOException, NullPointerException{

        File blueCheck = new File("blank-blue-check.jpg");
        String pathToOriginal = getServletContext().getRealPath("/" + blueCheck);

        File file = new File(pathToOriginal);
        BufferedImage imageToCopy = null;
        try {
            imageToCopy = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String amount = theAmount;
        String person_gettingPayed = person_payingto;
        String amountinWords = amountInWords;
        String date = dateWrote;
        String bill_type = billType;

        int w = imageToCopy.getWidth();
        int h = imageToCopy.getHeight();
        BufferedImage img = new BufferedImage(
                w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(g2d.getBackground());
        g2d.fillRect(0, 0, w, h);
        g2d.drawImage(imageToCopy, 0, -100, null);
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
        /*write check to file*/
        String filename = fileNameGenerator(username);
        String fullname = filePath + "_" + filename + ".jpg";
        ImageIO.write(img, "jpg", new File(fullname));
        return fullname;
    }

}
