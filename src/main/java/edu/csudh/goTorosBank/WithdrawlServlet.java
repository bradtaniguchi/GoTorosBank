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

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WithdrawlServlet extends HttpServlet{
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("Init() called");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    }
}
