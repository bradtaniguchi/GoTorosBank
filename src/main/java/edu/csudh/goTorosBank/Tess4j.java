/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csudh.goTorosBank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.tess4j.*;

/**
 *
 * @author Cortez
 */
class Tess4j {

    private File TheimageFile;
    private Tesseract instance;
    private String connection;
    private String connection2;
    private String result;

    public Tess4j(File imageFile) throws IOException {

        this.TheimageFile = imageFile;
        instance = Tesseract.getInstance();
        
        ClassLoader classLoader = getClass().getClassLoader();
        
        //connection = "resources";
        
        /***********************************************************************
        //must set path to where target language is at
        File file = new File(classLoader.getResource(connection).getFile());
        instance.setDatapath(file.getAbsolutePath());
        //must set the language
        connection2 = "eng.trainddata";
        File file2 = new File(classLoader.getResource(connection2).getFile());
        instance.setLanguage(file2.getAbsolutePath());
        //instance.setLanguage("eng");
        ************************************************************************/
     
    }
     public File getText(){
                    
        try {
            result = instance.doOCR(TheimageFile);
        } catch (TesseractException ex) {
           ex.printStackTrace();
        }
            File file = new File("Scanned ouput.txt");
            BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
        
            //writes the extracted data from image into text file
            output.write(result);
            output.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }    
            //System.out.println(result);
            return file;
    }
}