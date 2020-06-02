package com.kylelovestoad.kylebot.command;

import com.kylelovestoad.kylebot.Config;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;

import java.util.HashMap;
import java.util.Map;

public class PrefixManager {

    private static PrefixManager instance = null;
    private final static Map<Long,String> PREFIXES = new HashMap<>();
    
    private PrefixManager(){}

    /**
     *
     * @return The instance of prefix manager
     */
    public static PrefixManager getInstance() {
        if (instance == null) {
            instance = new PrefixManager();
        }

        return instance;
    }

    public Map<Long, String> getMap() {
        return PREFIXES;
    }

    /**
     *
     * @param event The guild event
     * @return The prefix as a String
     */
    public String getGuildPrefix(GenericGuildEvent event) {
        return getMap().computeIfAbsent(event.getGuild().getIdLong(), id -> Config.get("default_prefix"));
    }

    /**
     *
     * @param guildId The guild Id as a long
     * @return The prefix as a String
     */
    public String getGuildPrefix(long guildId) {
        return getMap().computeIfAbsent(guildId, id -> Config.get("default_prefix"));
    }
}
