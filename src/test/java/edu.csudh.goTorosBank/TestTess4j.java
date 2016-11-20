/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csudh.goTorosBank;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author Cortez
 */

public class TestTess4j extends TestCase {

    protected Tess4j tess;
    protected String Image_Path;
    protected File imageFile;
    protected File OutputText;

    @Override
    public void setUp(){
        //Image_Path = "ModifiedCheckTemplate.jpg";
        //ClassLoader classLoader = getClass().getClassLoader();

        //imageFile = new File(classLoader.getResource(Image_Path).getFile());
        //String pathToImage = "goTorosIndexServer Maven Webapp/src/main/resources/tessdata/ModifiedCheckTemplate.jpg";

        //imageFile = new File("C:\\Users\\Cortez\\Desktop\\ModifiedCheckTemplate.jpg");
        //imageFile = new File(pathToImage);
        
        //try {
        //    tess = new Tess4j(imageFile);
        //} catch (IOException ex) {
        //    fail("IOException error" + ex.getMessage());
        //}
    }

    public void testgetText() {
    /*
            try {
                OutputText = tess.getText();
        } catch (Exception e) {
        e.printStackTrace();
        }*/
 
         
    }
}
