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
package com.kylelovestoad.kylebot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;

public class Bot {

    /**
     * Initializes the bot
     * @throws LoginException If the login fails
     *
     */
    private Bot() throws LoginException {

        JDABuilder
                .create(Config.get("token"), GUILD_PRESENCES, GUILD_MEMBERS, GUILD_VOICE_STATES, GUILD_EMOJIS, GUILD_MESSAGES)
                .addEventListeners(new Listener())
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.streaming("GARBAGE" ,"https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
                .build();
    }

    /**
     * Instantiation of the utility class Bot
     */
    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
