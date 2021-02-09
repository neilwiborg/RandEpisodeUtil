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
    int numberOfSeasons;
    int[] seasons;

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
        File show = loadFile();
        loadShow(show);
        int episodeCount = arraySum(seasons);
        int val = rand.nextInt(episodeCount) + 1;
        //val between [1,lineCount]
        return findEpisode(show, val, seasons);
    }
    
    private File loadFile()
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
            return show;
        } catch (Exception exp)
        {
            System.out.println("countLines Exception");
        }
        return null;
    }
    
    private void loadShow(File showFile)
    {
        try
        {
            if (showFile.isFile())
            {
                Scanner fileReader = new Scanner(showFile);
                fileReader.next();
                numberOfSeasons = Integer.parseInt(fileReader.next());
                seasons = new int[numberOfSeasons + 1];
                for (int i = 0; i < numberOfSeasons; i++)
                {
                    seasons[i + 1] = Integer.parseInt(fileReader.next());
                }
            }
        } catch (Exception exp)
        {
            System.out.println("countLines Exception");
        }
    }
    
    private int arraySum (int[] myArray)
    {
        int sum = 0;
        for (int i = 0; i < myArray.length; i++)
        {
            sum += myArray[i];
        }
        return sum;
    }
    
    private int[] findEpisodeNumber(int randomNumber, int[] show)
    {
        int episodeNumber = randomNumber;
        int seasonNumber = 1;
        for (int i = 1; episodeNumber > show[i]; i++)
        {
            seasonNumber++;
            episodeNumber -= show[i];
        }
        return new int[] {seasonNumber, episodeNumber};
    }
    
    private String findEpisode(File showFile, int random, int[] myShow)
    {
        int[] myEp = findEpisodeNumber(random, myShow);
        String seasonLine = "";
        String epTitle = "";
        try
        {
            if (showFile.isFile())
            {
                Scanner fileReader = new Scanner(showFile);
                fileReader.nextLine();
                int lines = 0;
                do
                {
                    seasonLine = fileReader.nextLine();
                    lines++;
                } while (lines < myEp[0]);
                fileReader = new Scanner(seasonLine);
                fileReader.useDelimiter(",");
                for (int i = 0; i < myEp[1]; i++)
                {
                    epTitle = fileReader.next();
                }
                epTitle = "S" + myEp[0] + "E" + epTitle;
            }
        } catch (Exception exp)
        {
            System.out.println("findEpisode Exception");
            exp.printStackTrace();
        }
        return epTitle;
    }
}