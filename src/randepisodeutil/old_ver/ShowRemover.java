package randepisodeutil.old_ver;

import java.io.File;

/**
 * Removes a show from the LocalAppData folder.
 * 
 * @author Neil Wiborg
 */
public class ShowRemover
{
    String title;
    
    /**
     * Constructs an empty ShowRemover with no show attached.
     */
    public ShowRemover()
    {
    }
    
    /**
     * Constructs a ShowRemover with the show title given as the argument.
     * 
     * @param title the title of the show that will be removed
     */
    public ShowRemover(String title)
    {
        this.title = title;
    }
    
    /**
     * Removes show from the LocalAppData folder with the title defined as a
     * class level variable. If the program directory does not exist, creates
     * the directory.
     * 
     * @return whether the show was successfully removed or not
     */
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