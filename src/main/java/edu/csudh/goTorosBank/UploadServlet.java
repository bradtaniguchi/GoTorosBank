/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csudh.goTorosBank;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rudy
 */
import java.io.*;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



public class UploadServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config)throws ServletException{
        super.init(config);
        getServletContext().log("Init() clalled");
    }

    @Override
    @SuppressWarnings("uncheked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        
        if(!ServletFileUpload.isMultipartContent(request)){
            out.println("Nothing to upload");
            
        }
        FileItemFactory itemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(itemFactory);
        try{
            List<FileItem> items = upload.parseRequest(request);
            for(FileItem item:items){
                String contentType = item.getContentType();
                if(!contentType.equals("image/png")){
                    out.println("only png format image files supported");
                    
                }
            }
        }catch(FileUploadException e){
            out.println("Upload failed");
        }
    }
  
}
