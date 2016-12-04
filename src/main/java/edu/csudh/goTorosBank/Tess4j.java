package edu.csudh.goTorosBank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import net.sourceforge.tess4j.*;

/**
 *
 * @author Jesus Cortez
 */
class Tess4j
{
    private File theImageFile;
    private Tesseract instance;
    private String result;

    public Tess4j(File imageFile) throws IOException
    {
        this.theImageFile = imageFile;
        instance = Tesseract.getInstance();
    }

    public File getText()
    {
        try
        {
            result = instance.doOCR(theImageFile);
        }
        catch (TesseractException ex)
        {
           ex.printStackTrace();
        }

        File file = new File("Scanned ouput.txt");
        BufferedWriter output = null;

        try
        {
            output = new BufferedWriter(new FileWriter(file));
        
            //writes the extracted data from image into text file
            output.write(result);
            output.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return file;
    }
}