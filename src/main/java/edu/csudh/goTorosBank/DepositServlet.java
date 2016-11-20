package edu.csudh.goTorosBank;

/**
 *
 * @author Rudy
 */
import java.io.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
                        amount = 0; //We couldn't read the check
                        readable = false;
                    }
                    /*DO DATABASE TRANSACTIONS HERE?!?*/
                    returnJson.put("successfulUpload", true);
                    returnJson.put("message", "file successfully uploaded: " + fileName);
                    returnJson.put("path", fullPath);
                    returnJson.put("amount", amount);
                    returnJson.put("readable", readable);
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
