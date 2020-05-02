package com.kylelovestoad.kylebot;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    private static final Dotenv dotenv = Dotenv.load();

    /**
     * @param key The key which gets the environment variable
     * @return The value of an environment variable
     */
    public static String get(String key) {
        return dotenv.get(key.toUpperCase());
    }
}
