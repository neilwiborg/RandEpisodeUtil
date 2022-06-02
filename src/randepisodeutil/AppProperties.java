package randepisodeutil;

import java.io.FileInputStream;
import java.util.Properties;

public class AppProperties {
    private static AppProperties instance = null;
    
    private Properties props = null;
    
    private AppProperties() {
        props = getPropertiesFromFile(getPropertiesFile());
    }
    
    private Properties getPropertiesFromFile(String file) {
        Properties fileProps = null;
        try {
            fileProps = new Properties();
            fileProps.load(new FileInputStream(file));
        } catch (Exception e) {} // TODO: change to specific exception and handle
        return fileProps;
    }
    
    private String getPropertiesFile() {
        String appDir = System.getenv("XDG_DATA_HOME") + "/RandEpisodeUtil/";
        //appDir = System.getenv("LOCALAPPDATA") + "/RandEpisodeUtil/";
        
        return appDir + "app.properties";
    }
    
    public AppProperties getInstance() {
        if (instance == null) {
            instance = new AppProperties();
        }
        return instance;
    }
    
    public String getProperty(String key) {
        return props.getProperty(key);
    }
    
    public void setProperty(String key, String value) {
        props.setProperty(key, value);
    }
}
