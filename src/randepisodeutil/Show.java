package randepisodeutil;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Neil Wiborg
 */
public class Show
{
    File showFile;
    String title;
    int numberOfSeasons;
    int numberOfEpisodes;
    int[] seasons;

    public Show()
    {
    }

    public Show(String title)
    {
        this.title = title;
    }
    
    public void loadShowData()
    {
        loadFile();
        loadShow(showFile);
        numberOfEpisodes = arraySum(seasons);
        
    }
    
    public String findSeasonAndEpisode(int episode)
    {
        return findEpisode(showFile, episode, seasons);
    }
    
    /**
     * Loads the show file from the show title and makes the program directory
     * if it does not exist.
     * 
     * @return the show file loaded from the title given in the constructor
     */
    private void loadFile()
    {
        try
        {
            //String filepath = ".\\" + title + ".txt";
            String filepath = System.getenv("LOCALAPPDATA") + 
                    "/RandEpisodeUtil/" + title + ".txt";
            showFile = new File(filepath);
            File prgrmDir = new File(System.getenv("LOCALAPPDATA") +
                    "/RandEpisodeUtil/");
            if (!prgrmDir.exists())
            {
                prgrmDir.mkdir();
            }
        } catch (Exception exp)
        {
            System.out.println("countLines Exception");
        }
    }
    
    /**
     * Takes data from the show file argument and fills the class-level
     * variables with the data.
     * 
     * @param showFile the loaded show file to pull data from
     */
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
    
    /**
     * Returns the sum of every int in a 1D array. Sums [0, myArray.length].
     * 
     * @param myArray the array to be summed
     * @return        the summation of the array
     */
    private int arraySum(int[] myArray)
    {
        int sum = 0;
        for (int i = 0; i < myArray.length; i++)
        {
            sum += myArray[i];
        }
        return sum;
    }
    
    /**
     * Takes the raw random number that is out of the total number of episodes
     * and finds the season number and episode number.
     * 
     * @param randomNumber the random number out of the total number of episodes
     * @param show         the array with each index being a season, and the 
     *                     contents of the index being the number of episodes
     * @return             the season number and episode number of the random
     *                     number that was generated in the format [S,E]
     */
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
    
    /**
     * Finds the episode in the show file based on the season number and episode
     * number.
     * 
     * @param showFile        the file that contains all the show data
     * @param episodeNumber   the raw number out of the total number of episodes
     * @param myShow          the array with each index being a season, and the
     *                        contents of the index being the number of episodes
     * @return                the resulting random episode to watch given in the
     *                        format "S#E#"
     */
    private String findEpisode(File showFile, int episodeNumber, int[] myShow)
    {
        int[] myEp = findEpisodeNumber(episodeNumber, myShow);
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

    public String getTitle()
    {
        return title;
    }

    public int getNumberOfSeasons()
    {
        return numberOfSeasons;
    }

    public int getNumberOfEpisodes()
    {
        return numberOfEpisodes;
    }

    public File getShowFile()
    {
        return showFile;
    }
}
