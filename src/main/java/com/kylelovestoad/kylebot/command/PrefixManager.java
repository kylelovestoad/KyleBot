package com.kylelovestoad.kylebot.command;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.database.SQLiteDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PrefixManager {

    private static PrefixManager instance = null;
    private final static Map<Long, String> PREFIXES = new HashMap<>();
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

    public Map<Long, String> getMap() {
        return PREFIXES;
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
}
