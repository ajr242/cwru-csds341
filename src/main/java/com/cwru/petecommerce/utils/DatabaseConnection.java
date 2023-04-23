package com.cwru.petecommerce.utils;

//import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public final class DatabaseConnection {

    private static String connectionUrl = 
            "jdbc:sqlserver://localhost;"
                    + "database=petecommerce;"
                    + "user=dbuser;"
                    + "password=scsd431134dscs;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";

    // Get a connection from the connection pool
    // This method is thread-safe, because the BasicDataSource is thread-safe
    // and the method is synchronized, so only one thread can execute this method at a time (thread-safe singleton)
    public static synchronized Connection getConnection() throws SQLException {

        return DriverManager.getConnection(connectionUrl);

    }

    private DatabaseConnection() {
    }
}


