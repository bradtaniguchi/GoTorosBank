/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.csudh.goTorosBank;

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;

/**
 *
 * @author Cortez
 */
public class TestTess4j extends TestCase{
    protected Tess4j tess;
    protected File imageFile = new File ("jdbc:sqlite::resource:ModifiedCheckTemplate.jpg");
    protected File OutputText;
    
    @Override
    public void setUp() throws IOException{
        tess = new Tess4j(imageFile);
    }
    
    public void testgetText() throws IOException{
        OutputText = tess.getText();
    }
    
}
