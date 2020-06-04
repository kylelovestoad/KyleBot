package com.kylelovestoad.kylebot.command;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.database.SQLiteDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrefixManager {

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


    public static String getPrefix(long guildId) {

        try (final PreparedStatement preparedStatement = SQLiteDataSource
                .getConnection()
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Config.get("default_prefix");
    }

    public static void updatePrefix(Long guildId, String newPrefix) {

        try(final PreparedStatement preparedStatement = SQLiteDataSource
                .getConnection()
                // Language = SQLite
                .prepareStatement("UPDATE guild_settings SET prefix = ? WHERE guild_id = ?")) {

            preparedStatement.setString(1, newPrefix);
            preparedStatement.setString(2, String.valueOf(guildId));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
