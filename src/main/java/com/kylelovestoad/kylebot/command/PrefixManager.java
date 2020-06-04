package com.kylelovestoad.kylebot.command;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.database.SQLiteDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PrefixManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrefixManager.class);
    private static PrefixManager instance = null;
    private PrefixManager() {}

    /**
     * @return The instance of prefix manager
     */
    public static PrefixManager getInstance() {
        if (instance == null) {
            instance = new PrefixManager();
        }

        return instance;
    }

    /**
     *
     * @param guildId The guild's id
     * @return a String representing the prefix that comes before a command
     */
    public static String getPrefix(long guildId) {

        try (final Connection connection = SQLiteDataSource.getConnection();
                final PreparedStatement preparedStatement = connection
                // Language = SQLite
                .prepareStatement("SELECT prefix FROM guild_settings WHERE guild_id = ?")) {

            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("prefix");
                }
            }

            try (final PreparedStatement insertStatement = SQLiteDataSource
                    .getConnection()
                    // Language = SQLite
                    .prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")) {

                insertStatement.setString(1, String.valueOf(guildId));
                insertStatement.execute();
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Config.get("default_prefix");
    }


    /**
     *
     * @param guildId The guild's id
     * @param newPrefix The prefix that is replacing the original prefix.
     */
    public static void updatePrefix(Long guildId, String newPrefix) {

        try(final Connection connection = SQLiteDataSource.getConnection();
            final PreparedStatement preparedStatement = connection
                // Language = SQLite
                .prepareStatement("UPDATE guild_settings SET prefix = ? WHERE guild_id = ?")) {

            preparedStatement.setString(1, newPrefix);
            preparedStatement.setString(2, String.valueOf(guildId));

            preparedStatement.executeUpdate();

            connection.close();

            LOGGER.info("Set prefix to " + newPrefix + " for " + guildId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
