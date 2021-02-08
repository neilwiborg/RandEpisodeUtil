package randepisodeutil;

import java.util.Scanner;

/**
 *
 * @author Neil Wiborg
 */
public class RandEpisodeUtil
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
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
            myEpisodes.WriteFile();
        }
        else
        {
            for (int i = 0; i < args.length; i++)
            {
                String val = args[i];
                if (val.toLowerCase().equals("-c"))
                {
                    String showName = args[i+1];
                    Randomizer showRandom = new Randomizer(showName);
                    String randEpisode = showRandom.ChooseRandom();
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
