package com.kylelovestoad.kylebot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

import java.util.EnumSet;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;

/**
 * ---------------------------------------------------------------------------
 *
 * @author Kyle Richards
 * @version 1.1.0
 * @since April 15, 2020
 * <p>
 * KyleBot is a really awesome discord bot created as a hobby.
 * ---------------------------------------------------------------------------
 */
public class Bot {

    /**
     * Initializes the bot
     *
     * @throws LoginException If the login fails
     */
    private Bot() throws LoginException {

        JDABuilder.create(Config.get("token"), EnumSet.of(
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES
                )
        )
                .disableCache(EnumSet.of(
                        CacheFlag.VOICE_STATE,
                        CacheFlag.EMOTE
                        )
                )
                .addEventListeners(new Listener())
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("JESSICA"))
                .build();
    }

    /**
     * Instantiation of the utility class Bot
     */
    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
