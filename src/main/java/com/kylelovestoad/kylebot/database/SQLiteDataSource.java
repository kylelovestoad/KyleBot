package com.kylelovestoad.kylebot.database;

import com.kylelovestoad.kylebot.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQLiteDataSource.class);
    private static final HikariConfig CONFIG = new HikariConfig();
    private static final HikariDataSource DS;

    static {
        try {
            final File dbFile = new File("src/main/resources");

            if (!dbFile.exists()) {
                if (dbFile.createNewFile()) {
                    LOGGER.info("Created database file");
                } else {
                    LOGGER.error("Could not create database file");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        CONFIG.setJdbcUrl("jdbc:sqlite:src/main/resources/database.db");
        CONFIG.setConnectionTestQuery("SELECT 1");
        CONFIG.addDataSourceProperty("cachePrepStmts", "true");
        CONFIG.addDataSourceProperty("prepStmtCacheSize", "250");
        CONFIG.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        DS = new HikariDataSource(CONFIG);

        try (final Statement statement = getConnection().createStatement()) {

            final String defaultPrefix = Config.get("default_prefix");

            // Language = SQLite
            statement.execute("CREATE TABLE IF NOT EXISTS guild_settings (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "guildId VARCHAR(20) NOT NULL," +
                    "prefix VARCHAR(255) NOT NULL DEFAULT '" + defaultPrefix + "'" +
                    ");");

            LOGGER.info("Initialized Table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SQLiteDataSource() {}

    public static Connection getConnection() throws SQLException {
        return DS.getConnection();
    }

}
