package edu.csudh.goTorosBank;

/**
 *
 * @author Rudy
 */
import java.io.*;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;


/**
 * uploads servlets.
 */
@MultipartConfig
public class DepositServlet extends HttpServlet {
    private static final String SAVE_DIR = "TempUpload"; //where we want to save the files
    @Override
    public void init(ServletConfig config)throws ServletException{
        super.init(config);
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
            if (fileType.equals("image/png")) {
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

                    returnJson.put("successfulUpload", true);
                    returnJson.put("message", "file successfully uploaded: " + fileName);
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
}
