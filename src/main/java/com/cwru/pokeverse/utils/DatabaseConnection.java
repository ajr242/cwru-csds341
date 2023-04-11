package com.cwru.pokeverse.utils;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        try (InputStream input = new FileInputStream("src/main/resources/db.properties");) {
            Properties properties = new Properties();
            properties.load(input);

            ds.setUrl(properties.getProperty("jdbc.url"));
            ds.setUsername(properties.getProperty("jdbc.username"));
            ds.setPassword(properties.getProperty("jdbc.password"));
            ds.setDriverClassName(properties.getProperty("jdbc.driverClassName"));

            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setInitialSize(5);
            ds.setMaxTotal(10);
            ds.setMaxWaitMillis(100);
            ds.setMaxOpenPreparedStatements(100);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get a connection from the connection pool
    // This method is thread-safe, because the BasicDataSource is thread-safe
    // and the method is synchronized, so only one thread can execute this method at a time (thread-safe singleton)
    public static synchronized Connection getConnection() throws SQLException {

        return ds.getConnection();

    }

    private DatabaseConnection() {
    }
}


