package main.java.com.carrental.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides database connection management for the car rental system.
 * This class handles the creation and configuration of database connections
 * using JDBC for MySQL database.
 */
public class DatabaseConnection {
    // Database connection configuration constants
    private static final String DB_CONNECTION = "mysql";  // Database type
    private static final String DB_HOST = "localhost";    // Database server host
    private static final int DB_PORT = 3306;              // Database server port
    private static final String DB_DATABASE = "car_rental_db";  // Database name
    
    // Database credentials
    private static final String DB_USER = "root";         // Database username
    private static final String DB_PASSWORD = "";         // Database password (empty in this case)
    
    // JDBC connection URL constructed from configuration constants
    private static final String DB_URL = "jdbc:" + DB_CONNECTION + "://" + 
                                        DB_HOST + ":" + DB_PORT + "/" + DB_DATABASE;
    
    /**
     * Establishes and returns a connection to the database.
     * 
     * @return A Connection object representing the database connection
     * @throws SQLException If a database access error occurs or the connection fails
     * 
     * @implNote This method uses DriverManager to create a new connection
     *           using the pre-configured database URL, username, and password.
     *           The caller is responsible for properly closing the connection.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}