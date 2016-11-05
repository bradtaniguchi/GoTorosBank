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


/**
 * uploads servlets.
 */
@MultipartConfig
public class UploadServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config)throws ServletException{
        super.init(config);
        getServletContext().log("Init() clalled");
    }

    @Override
    @SuppressWarnings("uncheked")
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType("text");
        response.setCharacterEncoding("UTF-8");

        //http://www.technicalkeeda.com/java/how-to-upload-file-using-servlet-jsp
        PrintWriter out = response.getWriter();

        if(!ServletFileUpload.isMultipartContent(request)){
            out.println("Nothing to upload");
        }
        Part file = request.getPart("file");
        out.println("it worked! " + file.getContentType());
        /*DiskFileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        try{
            List<FileItem> items = upload.parseRequest(request);
            out.println(items.size());

            for(FileItem item:items){
                if(!item.isFormField()) {
                    out.println("   fieldName: " + item.getFieldName());
                    out.println("   content type " + item.getContentType());
                    out.println("   name " + item.getName());
                } else {
                    out.println("We got Form Field Data");
                }
            }
            out.println("--End--");
        }catch(FileUploadException e){
            out.println("Upload failed");
        }*/
    }

  
}
