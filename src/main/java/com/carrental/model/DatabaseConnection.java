package main.java.com.carrental.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_CONNECTION = "mysql";
    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 3306;
    private static final String DB_DATABASE = "car_rental_db";
    private static final String DB_URL = "jdbc:" + DB_CONNECTION + "://" + DB_HOST + ":" + DB_PORT +"/"+ DB_DATABASE;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}