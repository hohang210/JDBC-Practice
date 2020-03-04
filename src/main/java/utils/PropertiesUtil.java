package utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties properties = new Properties();

    public PropertiesUtil() {}

    public static boolean loadProperties(String fileName) {
        try {
            properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String getPropertyValue(String key) {
        return properties.getProperty(key);
    }
}
