package com.pentryyy.component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UserManager {
    private static final String PROPERTIES_FILE = "user.properties";
    
    public static String getId()  {
        Properties properties = new Properties();

        try (InputStream input = TokenManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            
            if (input == null) {
                throw new FileNotFoundException();
            }
            
            properties.load(input);
            return properties.getProperty("id");

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении id", e);
        }
    }

    public static String getType()  {
        Properties properties = new Properties();

        try (InputStream input = TokenManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            
            if (input == null) {
                throw new FileNotFoundException();
            }
            
            properties.load(input);
            return properties.getProperty("type");

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении type", e);
        }
    }
}
