package com.kylelovestoad.kylebot;

import com.kylelovestoad.kylebot.database.SQLiteDataSource;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

import java.sql.SQLException;
import java.util.EnumSet;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;

/**
 * ---------------------------------------------------------------------------
 * KyleBot is a really awesome discord bot created as a hobby.
 *
 * @author Kyle Richards
 * @version 1.1.0
 * @since April 15, 2020
 * ---------------------------------------------------------------------------
 */
public class Bot {

    /**
     * Initializes the bot
     *
     * @throws LoginException If the login fails
     */
    private Bot() throws LoginException, SQLException {

        SQLiteDataSource.getConnection();

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
    public static void main(String[] args) throws LoginException, SQLException {
        new Bot();
    }
}
