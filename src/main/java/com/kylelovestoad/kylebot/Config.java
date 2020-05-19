package com.kylelovestoad.kylebot;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {


    private static final Properties p = new Properties();

    private static FileReader reader;

    private static FileOutputStream fos;


    /**
     * @param key The key which gets the property
     * @return The value of a property
     */
    public static String get(String key) {
        try {
            reader = new FileReader("config.properties");
            p.load(reader);
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
        return p.getProperty(key.toUpperCase());
    }

    public static void add(String key, String value) {
        p.setProperty(key.toUpperCase(), value);
        try {
            p.store(new FileWriter("config.properties"), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
