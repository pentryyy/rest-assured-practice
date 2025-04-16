package com.pentryyy.component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TokenManager {
    private static final String PROPERTIES_FILE = "token.properties";
    
    public static String getToken()  {
        Properties properties = new Properties();

        try (InputStream input = TokenManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            
            if (input == null) {
                throw new FileNotFoundException();
            }
            
            properties.load(input);
            return properties.getProperty("token");

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении токена", e);
        }
    }
}
