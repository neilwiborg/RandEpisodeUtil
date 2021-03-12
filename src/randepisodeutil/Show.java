package randepisodeutil;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
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
    boolean history;

    public Show()
    {
    }

    public Show(String title)
    {
        this.title = title;
    }
    
    public void loadShowData()
    {
        checkHistory();
        loadFile();
        loadShow(showFile);
        numberOfEpisodes = arraySum(seasons);
        Seasons showSeasons = new Seasons(showFile);
        showSeasons.saveShow();
        
    }
    
    public String findSeasonAndEpisode(int episode)
    {
        return findEpisode(showFile, episode, seasons);
    }
    
    public String findSeasonAndEpisode(int season, int episode)
    {
        return findEpisode(showFile, new int[] {season, episode}, seasons);
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
            if (history)
            {
                String filepathHistory = System.getenv("LOCALAPPDATA") + 
                    "/RandEpisodeUtil/" + title + " history.txt";
                File showFileHistory = new File(filepathHistory);
                if (!showFileHistory.exists())
                {
                    Files.copy(showFile.toPath(), showFileHistory.toPath());
                    showFile = showFileHistory;
                    title += " history";
                }
            }
        } catch (Exception exp)
        {
            System.out.println("countLines Exception");
            exp.printStackTrace();
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
    
    private String findEpisode(File showFile, int[] seasonAndEpisode, int[] myShow)
    {
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
                } while (lines < seasonAndEpisode[0]);
                fileReader = new Scanner(seasonLine);
                fileReader.useDelimiter(",");
                for (int i = 0; i < seasonAndEpisode[1]; i++)
                {
                    epTitle = fileReader.next();
                }
                epTitle = "S" + seasonAndEpisode[0] + "E" + epTitle;
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
    
    public int getNumberOfEpisodesInSeason(int season)
    {
        return seasons[season];
    }
    
    private void checkHistory()
    {
        history = Boolean.parseBoolean(RandEpisodeUtil.appProps.getProperty("history"));
//        Scanner scanTitle = new Scanner(title);
//        scanTitle.useDelimiter(" ");
//        ArrayList<String> words = new ArrayList<>();
//        while(scanTitle.hasNext())
//        {
//            words.add(scanTitle.next());
//        }
//        if (words.get(words.size() - 1).equals("history"))
//        {
//            history = true;
//        }
//        else
//        {
//            history = false;
//        }
    }
    
//    private File getOriginalFile()
//    {
//        if (history)
//        {
//            String originalTitle = Utilities.removeWord(title, "history");
//            try
//            {
//                return new File(System.getenv("LOCALAPPDATA") + 
//                    "/RandEpisodeUtil/" + originalTitle + ".txt");
//            } catch (Exception exp)
//            {
//                
//            }
//        }
//        return null;
//    }
    
    private class Seasons
    {
        private ArrayList<ArrayList<String>> myShow = new ArrayList<>();

        public Seasons()
        {
            
        }
        
        public Seasons(File showFile)
        {
            myShow.ensureCapacity(numberOfSeasons + 1);
            myShow.add(null);
            for (int i = 1; i < numberOfSeasons + 1; i++)
            {
                myShow.add(i, new ArrayList<>());
                myShow.get(i).add(null);
                myShow.get(i).ensureCapacity(seasons[i] + 1);
            }
            try
            {
                Scanner FileReader = new Scanner(showFile);
                FileReader.nextLine();
                for (int i = 1; FileReader.hasNextLine(); i++)
                {
                    Scanner lineReader = new Scanner(FileReader.nextLine());
                    lineReader.useDelimiter(",");
                    for (int j = 1; lineReader.hasNext(); j++)
                    {
                        addValue(i, j, lineReader.next());
                    }
                }
            } catch (Exception exp)
            {
                
            }
        }
        
        private void addValue(int seasonNumber, int episodeNumber, String episodeName)
        {
            try
            {
                myShow.get(seasonNumber).add(episodeNumber, episodeName);
            } catch (IndexOutOfBoundsException exp)
            {
                myShow.add(seasonNumber, new ArrayList<>());
                myShow.get(seasonNumber).add(episodeNumber, episodeName);
            }
        }
        
        private void removeValue(int seasonNumber, String episodeName)
        {
            int episodeNumber = myShow.get(seasonNumber).indexOf(episodeName);
            myShow.get(seasonNumber).remove(episodeNumber);
        }
        
        private void splitValue(int seasonNumber, String episodeName, int parts)
        {
            int episodeNumber = myShow.get(seasonNumber).indexOf(episodeName);
            removeValue(seasonNumber, episodeName);
            for (int i = 0; i < parts; i++)
            {
                if (i > 25)
                {
                    System.out.println("Error splitting episode!");
                }
                char letter = (char)(i + 65); // ASCII 65 = 'A'
                addValue(seasonNumber, episodeNumber + i, episodeName + letter);
            }
        }
        
        //public
        
        private void saveShow()
        {
            StringBuilder list = new StringBuilder();
            list.append("[" + RandEpisodeUtil.appProps.getProperty("version") + "]");
            list.append(" " + (myShow.size() - 1));
            for (int i = 1; i < myShow.size(); i++)
            {
                list.append(" " + (myShow.get(i).size() - 1));
            }
            for (int i = 1; i < myShow.size(); i++)
            {
                list.append("\n");
                for (int j = 1; j < myShow.get(i).size(); j++)
                {
                    list.append(myShow.get(i).get(j) + ",");
                }
                list.deleteCharAt(list.length() - 1);
            }
            writeShow(list.toString());
        }
        
        private void writeShow(String fileContents)
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
//                    Scanner userConf = new Scanner(System.in);
//                    System.out.print("This show already exists! Overwrite old file? [Y/n] ");
//                    if (userConf.next().toLowerCase().charAt(0) == 'n')
//                    {
//                        throw new Exception("File already exists!");
//                    }
                }
                FileWriter writer = new FileWriter(showFile);
                writer.write(fileContents);
                writer.close();
                System.out.println("Show saved!");
            } catch (Exception exp)
            {
                System.out.println(exp.getMessage());
            }
        }
    }
}