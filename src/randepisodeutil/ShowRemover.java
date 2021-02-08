/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randepisodeutil;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author Neil Wiborg
 */
public class ShowRemover
{
    String title;

    public ShowRemover()
    {
    }

    public ShowRemover(String title)
    {
        this.title = title;
    }
    
    public boolean removeShow()
    {
        try
        {
            //String filepath = ".\\" + title + ".txt";
            String filepath = System.getenv("LOCALAPPDATA") + 
                    "/RandEpisodeUtil/" + title + ".txt";
            File showFile = new File(filepath);
            File prgrmDir = new File(System.getenv("LOCALAPPDATA") +
                    "/RandEpisodeUtil/");
            if (!prgrmDir.exists())
            {
                return false;
            }
            if (showFile.isFile())
            {
                showFile.delete();
                return true;
            }
        } catch (Exception exp)
        {
            System.out.println(exp.getMessage());
            return false;
        }
        return false;
    }
}
