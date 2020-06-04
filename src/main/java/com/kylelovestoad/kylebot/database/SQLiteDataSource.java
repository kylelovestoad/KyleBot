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
    private static final HikariDataSource POOL;

    static {

        String path = "src/main/java/com/kylelovestoad/kylebot/database/database.db";


        try {
            final File dbFile = new File(path);

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


        CONFIG.setJdbcUrl("jdbc:sqlite:" + path);
        CONFIG.setConnectionTestQuery("SELECT 1");
        CONFIG.setMinimumIdle(5);
        CONFIG.setMaximumPoolSize(50);
        CONFIG.setConnectionTimeout(10000);
        CONFIG.setIdleTimeout(600000);
        CONFIG.setMaxLifetime(1800000);
        CONFIG.addDataSourceProperty("cachePrepStmts", "true");
        CONFIG.addDataSourceProperty("prepStmtCacheSize", "250");
        CONFIG.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        POOL = new HikariDataSource(CONFIG);


        try (final Connection connection = getConnection();
             final Statement statement = connection.createStatement()) {



            final String defaultPrefix = Config.get("default_prefix");

            // Language = SQLite
            statement.execute("CREATE TABLE IF NOT EXISTS guild_settings (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "guild_id VARCHAR(20) NOT NULL," +
                    "prefix VARCHAR(255) NOT NULL DEFAULT '" + defaultPrefix + "'" +
                    ");");

            connection.close();

            LOGGER.info("Initialized Table");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SQLiteDataSource() {}

    public static Connection getConnection() throws SQLException {
        return POOL.getConnection();
    }

}
