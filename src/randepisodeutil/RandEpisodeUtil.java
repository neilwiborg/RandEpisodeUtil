package randepisodeutil;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * Controller that manages the RandEpisodeUtil program. Instantiates other
 * classes based on what user wants to do. Program can add new show files when
 * no arguments are added. The -c option can be used to choose a new random
 * episode. The -r option can be used to remove a show file. Both -c and -r
 * require the show name to be specified as an argument.
 * 
 * @author Neil Wiborg
 */
public class RandEpisodeUtil
{
    public static String appDir;
    public static Properties appProps;

    /**
     * The first method run by the program, controls which function of the
     * program will be executed.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        appDir = System.getenv("LOCALAPPDATA") + "/RandEpisodeUtil/";
        try
        {
            appProps = new Properties();
            appProps.load(new FileInputStream(appDir + "app.properties"));
            
        } catch (Exception exp)
        {
            exp.printStackTrace();
        }
        if (args.length == 0)
        {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Welcome to RandEpisodeUtil!");
            System.out.print("Enter the name of the show: ");
            String show = keyboard.nextLine().toLowerCase();
            System.out.print("Great! How many seasons are in " + show + "? ");
            int seasons = keyboard.nextInt();
            int[] episodes = new int[seasons];
            for (int j = 0; j < seasons; j++)
            {
                System.out.print("How many episodes are in season " + (j + 1) + "? ");
                episodes[j] = keyboard.nextInt();
            }
            FillEpisodes myEpisodes = new FillEpisodes(show, episodes);
            myEpisodes.writeFile();
        }
        else
        {
            for (int i = 0; i < args.length; i++)
            {
                String val = args[i];
                if (val.toLowerCase().equals("-c"))
                {
                    String showName = args[i+1];
//                    if (Boolean.parseBoolean(appProps.getProperty("history")))
//                    {
//                        showName += " history";
//                    }
                    Show myShow = new Show(showName);
                    Randomizer showRandom = new Randomizer(myShow);
                    String randEpisode;
                    switch (appProps.getProperty("randType"))
                    {
                        default:
                        case "main":
                            randEpisode = showRandom.chooseRandom();
                            break;
                        case "alt":
                            randEpisode = showRandom.chooseRandomAlt();
                            break;
                    }
                    System.out.println("The episode you should watch is: " + randEpisode);
                    break;
//                    for (int j = 0; j < 50; j++)
//                    {
//                        System.out.println(showRandom.ChooseRandom());
//                    }
                }
                else if (val.toLowerCase().equals("-r"))
                {
                    String showName = args[i+1];
                    ShowRemover remover = new ShowRemover(showName);
                    boolean didWork = remover.removeShow();
                    if (didWork)
                        System.out.println(showName + " successfully removed.");
                    else
                        System.out.println(showName + " was NOT removed");
                }
                else
                {
                    
                }
            }
        }
    }
}