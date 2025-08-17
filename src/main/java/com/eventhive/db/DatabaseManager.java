package com.eventhive.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {


    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventhive_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sumit@0803";
    private static boolean driverLoaded = false;

    // Static block to load the MySQL JDBC driver once
    static {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            driverLoaded = true;
        } catch (Exception ex) {
            System.err.println("!!!!!!!! FATAL: Failed to load MySQL JDBC driver. !!!!!!!! ");
            ex.printStackTrace();

            throw new RuntimeException("Failed to load JDBC driver.", ex);
        }
    }


    public static Connection getConnection() throws SQLException {
        if (!driverLoaded) {
            throw new SQLException("MySQL JDBC Driver not loaded.");
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}