package com.kylelovestoad.kylebot.command;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.database.SQLiteDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SettingsManager {


    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsManager.class);
    private static volatile SettingsManager instance = null;

    private SettingsManager() {
    }

    /**
     * @return The instance of prefix manager
     */
    public static SettingsManager getInstance() {
        if (instance == null) {
            synchronized (SettingsManager.class) {
                instance = new SettingsManager();
            }
        }

        return instance;
    }


    public void set(String column, Object newValue, Long guildId) {

        try (final Connection connection = SQLiteDataSource.getConnection();
             final PreparedStatement preparedStatement = connection
                     // Language = SQLite
                     .prepareStatement("UPDATE guild_settings SET " + column.replaceAll("[\"';\\- ]", "") + " = ? WHERE guild_id = ?")) {

            preparedStatement.setObject(1, newValue);
            preparedStatement.setLong(2, guildId);

            preparedStatement.executeUpdate();

            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Object get(String column, Object guildId) {
        try (final Connection connection = SQLiteDataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     // Language = SQLite
                     .prepareStatement("SELECT " + column.replaceAll("[\"';\\- ]", "")
                             + " FROM guild_settings WHERE guild_id = ? ORDER BY id")) {


            preparedStatement.setString(1, String.valueOf(guildId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getObject(column);
                }
            }

            try (final PreparedStatement insertStatement = connection
                    // Language = SQLite
                    .prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")) {

                insertStatement.setString(1, String.valueOf(guildId));

                insertStatement.execute();

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
