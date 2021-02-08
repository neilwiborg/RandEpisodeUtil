package randepisodeutil;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author Neil Wiborg
 */
public class FillEpisodes
{
    String title;
    int[] eps;
    
    public FillEpisodes()
    {
    }

    public FillEpisodes(String title, int[] eps)
    {
        this.title = title;
        this.eps = eps;
    }
    
    public void WriteFile()
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
            String fileText = BuildText();
            FileWriter writer = new FileWriter(showFile);
            writer.write(fileText);
            writer.close();
            System.out.println("Show saved!");
        } catch (Exception exp)
        {
            System.out.println(exp.getMessage());
        }
    }
    
    /*
    [V1.3] [# of seasons] [# of eps in sea 1] [# of eps in sea 2] ...
    1,2,3,4,5,6,7,8,9,10A,10B,13,...[all eps in season 1]
    1,2,3,4,5,6,7,8A,12,...[all eps in season 2]
    .
    .
    .
    */
    private String BuildText()
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
//        list.append("[V1.2]");
//        for (int i = 0; i < eps.length; i++)
//        {
//            for (int j = 0; j < eps[i]; j++)
//            {
//                list.append("\nS" + (i + 1) + "E" + (j + 1));
//                first = false;
//            }
//        }
        return list.toString();
    }
}
