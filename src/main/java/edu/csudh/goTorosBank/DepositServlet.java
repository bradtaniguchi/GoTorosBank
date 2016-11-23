package edu.csudh.goTorosBank;

/**
 *
 * @author Rudy
 */
import java.io.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;


/**
 * uploads servlets.
 */
@MultipartConfig
public class DepositServlet extends HttpServlet {
    private static final String SAVE_DIR = "TempUpload"; //where we want to save the files
    private static String DATA_PATH;
    private static boolean readable;
    @Override
    public void init(ServletConfig config)throws ServletException{
        super.init(config);
        DATA_PATH = getServletContext().getRealPath("");
        DATA_PATH = DATA_PATH + "WEB-INF/classes";
        getServletContext().log("Init() called");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject returnJson = new JSONObject();
        /*check if the user is logged in first*/
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username"); //get the user name, and validate they exist

        if(username==null) {
            returnJson.put("successfulUpload", false);
            returnJson.put("message", "Not Logged in!");
            response.getWriter().write(returnJson.toJSONString());
            return;
        }
        if(!ServletFileUpload.isMultipartContent(request)){
            //out.println("Nothing to upload, not multipart content!");
            returnJson.put("successfulUpload", false);
            returnJson.put("message", "Nothing to upload, not multipart content!");
        } else {
            Part fileItem = request.getPart("file"); //get the file part
            String fileName = fileItem.getSubmittedFileName(); //and the name
            String fileType = fileItem.getContentType(); //get type
            if (fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                // gets absolute path of the web application
                String appPath = request.getServletContext().getRealPath("");
                // constructs path of the directory to save uploaded file
                String savePath = appPath + File.separator + SAVE_DIR;

                try {
                    File fileSaveDir = new File(savePath);
                    if (!fileSaveDir.exists()) {
                        fileSaveDir.mkdir();
                    }
                    fileItem.write(savePath + File.separator + fileName);
                    String fullPath = getServletContext().getRealPath(SAVE_DIR + File.separator + fileName);
                    float amount = getCheckAmount(fullPath);
                    if(amount == -1) {
                        returnJson.put("message", "Could not Read the Check! Sorry!");
                        returnJson.put("readable", false);
                        returnJson.put("amount", 0);
                    } else {
                        /*Get the account to deposit to...*/
                        DatabaseInterface database = new DatabaseInterface();
                        User user = database.getUser(username);
                        /*Get the amount*/
                        int accountID = Integer.parseInt(request.getParameter("account"));
                        /*Get the description for the check*/
                        String description = request.getParameter("description");
                        /*add a checker to this parser...*/
                        boolean foundAccount = false;
                        for (Account acc : user.getUserAccounts()) {
                            if (acc.getAccountNumber() == accountID) {
                                returnJson.put("amount", amount);
                                returnJson.put("readable", readable);
                                database.deposit(accountID, amount, description);
                                foundAccount = true;
                            }
                        }
                        if (foundAccount) {
                            returnJson.put("successfulUpload", true); //full transaction
                            returnJson.put("message", "file successfully uploaded: " + fileName + "\n" +
                                    "for amount: " + amount);
                            returnJson.put("readable", true);
                        } else {
                            returnJson.put("successfulUpload", false);
                            returnJson.put("message", "We didn't find the account with id: " + accountID);
                        }
                    }

                } catch (Exception e) {
                    returnJson.put("successfulUpload", false);
                    returnJson.put("message", "Error with upload: " + e.getMessage());
                }
            } else {
                returnJson.put("successfulUpload", false);
                returnJson.put("message", "Error with upload, image type not recognized: " + fileType);
            }
        }
        response.getWriter().write(returnJson.toJSONString());
    }
    
    public float getCheckAmount(String filepath) throws TesseractException, StringIndexOutOfBoundsException {
        String result;
        float returnAmount;
        File checkFile = new File(filepath);
        Tesseract instance = new Tesseract();
        instance.setDatapath(DATA_PATH); //assign the training data

        //runs the tesseract and catches exceptions, assigns scanned output into result
        result = instance.doOCR(checkFile);

        //Check amount extraction
        /*TODO: add catchers, if we can't find the amount!*/
        int startIndex = result.indexOf('$');
        int resultIndex = result.indexOf(',');
        if( startIndex == -1 || resultIndex == -1) { //one isn't found!
            returnAmount = -1; //return a bad value back
        } else {
            String Extract1 = result.substring(startIndex, resultIndex);
            /*wait does this just get the first 2 integers from the amount???*/
            String Extract2 = Extract1.substring(Extract1.indexOf('$')+2);
            String amount = Extract2.trim();
            amount = amount+".00";
            returnAmount = Float.parseFloat(amount);
        }
        return returnAmount;
    }
    
    
}
