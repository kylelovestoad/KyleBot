package com.kylelovestoad.kylebot.command;

import java.util.Arrays;
import java.util.List;

public enum CommandType {

    FUN(       "fun"),
    GENERAL(   "general"),
    MODERATION("moderation");

    private final String key;

    CommandType(String key) {
        this.key = key;
    }

    public static CommandType fromKey(String key) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.key.equalsIgnoreCase(key))
                .findFirst()
                .orElse(null);
    }

    public static List<CommandType> valuesAsList() {
        return List.of(values());
    }
}
