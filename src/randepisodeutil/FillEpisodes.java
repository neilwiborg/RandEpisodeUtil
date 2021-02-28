package randepisodeutil;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Takes show data (title and seasons) and saves it into a file with name
 * "showName.txt" located in "C:/Users/%USER%/AppData/Local/RandEpisodeUtil/".
 * 
 * @author Neil Wiborg
 */
public class FillEpisodes
{
    String title;
    int[] eps;
    
    /**
     * Constructs an empty FillEpisodes with no show information.
     */
    public FillEpisodes()
    {
    }
    
    /**
     * Constructs a FillEpisodes with the show information given as the 
     * arguments.
     * 
     * @param title the title of the show to be added
     * @param eps   the array with each index being a season, and the contents
     *              of the index being the number of episodes
     */
    public FillEpisodes(String title, int[] eps)
    {
        this.title = title;
        this.eps = eps;
    }
    
    /**
     * Writes all relevant show data to a file. If the program directory does
     * not exist, creates the directory.
     */
    public void writeFile()
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
                prgrmDir.mkdir();
            }
            if (showFile.isFile())
            {
                Scanner userConf = new Scanner(System.in);
                System.out.print("This show already exists! Overwrite old file? [Y/n] ");
                if (userConf.next().toLowerCase().charAt(0) == 'n')
                {
                    throw new Exception("File already exists!");
                }
            }
            String fileText = buildText();
            FileWriter writer = new FileWriter(showFile);
            writer.write(fileText);
            writer.close();
            System.out.println("Show saved!");
        } catch (Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }
    
    /**
     * Creates the show data in the format that needs to be saved by using a
     * StringBuffer and then converts it into a String to be written. The format
     * is:
     * [V1.3] [# of seasons] [# of eps in sea 1] [# of eps in sea 2] ...
     * 1,2,3,4,5,6,7,8,9,10A,10B,13,...[all eps in season 1]
     * 1,2,3,4,5,6,7,8A,12,...[all eps in season 2]
     * .
     * .
     * .
     * 
     * @return the built String to be written to a file.
     */
    private String buildText()
    {
        StringBuffer list = new StringBuffer();
        //boolean first = true;
        list.append("[V1.3]"); //BuildText() version number
        list.append(" " + eps.length); //Number of seasons
        for (int i = 0; i < eps.length; i++) //For each season
        {
            list.append(" " + eps[i]); //Number of episodes in season i
        }
        for (int i = 0; i < eps.length; i++) //For each season
        {
            list.append("\n"); //Go down one line
            list.append("1"); //Start with 1
            for (int j = 2; j < eps[i] + 1; j++) //add all episodes to line
            {
                list.append("," + j);
            }
        }
        return list.toString();
    }
}