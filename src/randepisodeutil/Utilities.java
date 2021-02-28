package randepisodeutil;

/**
 *
 * @author Neil Wiborg
 */
public class Utilities
{
    public static String modifyFilename(String originalName, String add)
    {
        int index  = 0;
        while (originalName.charAt(index) != '.')
        {
            index++;
        }
        String firstPart = originalName.substring(0, index);
        String extension = originalName.substring(index + 1);
        return firstPart + add + extension;
    }
    
    public static String removeWord(String originalName, String remove)
    {
        for (int i = 0; i < originalName.length(); i++)
        {
            if (originalName.charAt(i) == ' ')
            {
                String check = originalName.substring(i, i + (remove.length() + 1));
                if (check.equals(remove))
                {
                    String firstPart = originalName.substring(0, i);
                    String lastPart = originalName.substring(i + remove.length() + 2, originalName.length());
                    return firstPart + lastPart;
                }
            }
        }
        return null;
    }
}
