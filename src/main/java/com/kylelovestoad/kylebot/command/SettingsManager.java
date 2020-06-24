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


    public void set(String column, Object newValue, String conditionColumn, Object condition) {

        String regex = "[^A-Za-z_]";

        try (final Connection connection = SQLiteDataSource.getConnection();
             final PreparedStatement preparedStatement = connection
                     // Language = SQLite
                     .prepareStatement("UPDATE guild_settings SET " + column.replaceAll(regex, "") + " = ? WHERE " + conditionColumn + " = ?")) {

            preparedStatement.setObject(1, newValue);
            preparedStatement.setObject(2, condition);

            preparedStatement.executeUpdate();

            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Object get(String column, String conditionColumn, Object condition) {

        String regex = "[^A-Za-z_]";

        try (final Connection connection = SQLiteDataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     // Language = SQLite
                     .prepareStatement("SELECT " + column.replaceAll(regex, "")
                             + " FROM guild_settings WHERE " + conditionColumn + " = ?")) {


            preparedStatement.setObject(1, condition);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getObject(column);
                }
            }

            try (final PreparedStatement insertStatement = connection
                    // Language = SQLite
                    .prepareStatement("INSERT INTO guild_settings(" + conditionColumn + ") VALUES(?)")) {

                insertStatement.setObject(1, condition);

                insertStatement.execute();

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
