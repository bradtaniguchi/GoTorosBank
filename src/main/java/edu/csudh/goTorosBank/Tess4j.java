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
import net.sourceforge.tess4j.*;

/**
 *
 * @author Cortez
 */
class Tess4j {

    private File TheimageFile;
    private Tesseract instance;
    private String connection;

    public Tess4j(File imageFile) throws IOException {

        this.TheimageFile = imageFile;
        instance = Tesseract.getInstance();
        connection = "jdbc:sqlite::resource";
        //must set path to where target language is at
        instance.setDatapath(connection);
        //must set the language
        instance.setLanguage("eng");
    }
     public File getText() throws IOException{
        try {

            String result = instance.doOCR(TheimageFile);
            File file = new File("Scanned ouput.txt");
            BufferedWriter output = null;
            output = new BufferedWriter(new FileWriter(file));
            //writes the extracted data from image into text file
            output.write(result);
            output.close();
            
            //System.out.println(result);
            return file;
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
