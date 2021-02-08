/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randepisodeutil;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Neil Wiborg
 */
public class Randomizer
{
    String title;

    public Randomizer()
    {
    }

    public Randomizer(String title)
    {
        this.title = title;
    }
    
    public String ChooseRandom()
    {
        Random rand = new Random();
        int lineCount = countLines();
        int val = rand.nextInt(lineCount - 2) + 2;
        //val between [2,lineCount]
        return findEpisode(val);
    }
    
    private int countLines()
    {
        try
        {
            //String filepath = ".\\" + title + ".txt";
            String filepath = System.getenv("LOCALAPPDATA") + 
                    "/RandEpisodeUtil/" + title + ".txt";
            File show = new File(filepath);
            File prgrmDir = new File(System.getenv("LOCALAPPDATA") +
                    "/RandEpisodeUtil/");
            if (!prgrmDir.exists())
            {
                prgrmDir.mkdir();
            }
            if (show.isFile())
            {
                Scanner fileReader = new Scanner(show);
                int lines = 0;
                while (fileReader.hasNextLine())
                {
                    fileReader.nextLine();
                    lines++;
                }
                return lines;
            }
        } catch (Exception exp)
        {
            System.out.println("countLines Exception");
        }
        return 0;
    }
    
    private String findEpisode(int lineNumber)
    {
        try
        {
            //String filepath = ".\\" + title + ".txt";
            String filepath = System.getenv("LOCALAPPDATA") + 
                    "/RandEpisodeUtil/" + title + ".txt";
            File show = new File(filepath);
            if (show.isFile())
            {
                Scanner fileReader = new Scanner(show);
                int lines = 1;
                while (lines < lineNumber)
                {
                    fileReader.nextLine();
                    lines++;
                }
                String text = fileReader.nextLine();
                return text;
            }
        } catch (Exception exp)
        {
            System.out.println("findEpisode Exception");
            exp.printStackTrace();
        }
        return "";
    }
}
