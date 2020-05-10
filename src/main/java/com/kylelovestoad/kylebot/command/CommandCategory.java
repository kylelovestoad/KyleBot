package com.kylelovestoad.kylebot.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CommandCategory {

    FUN(       "fun", false),
    GENERAL(   "general", false),
    MODERATION("moderation", false),
    TROLL(     "troll", true);

    private final String key;
    private final boolean isHidden;

    CommandCategory(String key, boolean isHidden) {
        this.key = key;
        this.isHidden = isHidden;
    }

    public static CommandCategory fromKey(String key) {

        for (CommandCategory commandCategory : values()) {
            if (commandCategory.key.equalsIgnoreCase(key)) {
                return commandCategory;
            }
        }

        return null;
    }

    public static CommandCategory fromString(String str) {

        for (CommandCategory commandCategory : values()) {
            if (commandCategory.name().equalsIgnoreCase(str)) {
                return commandCategory;
            }
        }

        return null;
    }

    public static List<CommandCategory> getNonHiddenCategories() {
        List<CommandCategory> nonHiddenCategories = new ArrayList<>();
        Arrays.stream(values()).forEach(commandCategory -> {
            if (!commandCategory.isHidden) {nonHiddenCategories.add(commandCategory);}
        });
        return nonHiddenCategories;
    }


}
