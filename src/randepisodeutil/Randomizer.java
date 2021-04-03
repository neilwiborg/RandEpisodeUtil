package randepisodeutil;

import java.util.Random;

/**
 * Generates a random number between [0,number of episodes] and finds that 
 * episode in the show file. Starts by loading show file and interpreting
 * the information inside to get the total number of episodes.
 * 
 * @author Neil Wiborg
 */
public class Randomizer
{
    Show currentShow;
    /**
     * Constructs an empty Randomizer with no Show attached.
     */
    public Randomizer()
    {
    }
    
    /**
     * Constructs a Randomizer with the Show object given as the argument.
     * 
     * @param show the Show object that a random episode will be chosen from
     */
    public Randomizer(Show show)
    {
        currentShow = show;
    }
    
    /**
     * The main method that invokes the rest of the methods to find an episode
     * to watch.
     * 
     * @return the randomly chosen season and episode in the format "S#E#"
     */
    public String chooseRandom()
    {
        currentShow.loadShowData();
        Random rand = new Random();
        int val = rand.nextInt(currentShow.getNumberOfEpisodes()) + 1;
        //val between [1,episodeCount]
        return currentShow.findSeasonAndEpisode(val);
    }
    
    public String chooseRandomAlt()
    {
        currentShow.loadShowData();
        Random rand = new Random();
        if (Boolean.parseBoolean(RandEpisodeUtil.appProps.getProperty("history")))
        {
            //currentShow.
        }
        int randSeason = rand.nextInt(currentShow.getNumberOfSeasons()) + 1;
        int randEpisode = rand.nextInt(currentShow.getNumberOfEpisodesInSeason(randSeason)) + 1;
        return currentShow.findSeasonAndEpisode(randSeason, randEpisode);
    }
}