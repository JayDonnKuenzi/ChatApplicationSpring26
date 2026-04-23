/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lukew
 */
public class Config {
    // TODO: Set your database host, port, username, password, and database name

    private static String host = "172.172.161.211";        // Example: "localhost" or IP of your DB server
    private static String port = "3306";           // MySQL default port is 3306
    private static String uname = "student";      // Your DB username
    private static String pwd = "password123";        // Your DB password
    private static String databaseName = "chat_db";// Name of your database
    private static Connection connection;          // Singleton connection object

    private static final String URL
            = "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require";

    private Config() {
        // prevent instantiation
    }

    /**
     * Returns a singleton database connection.
     * @return Connection
     * 
     * - Loads the MySQL JDBC driver
     * - Creates the connection only once (singleton pattern)
     * - Handles SQL and ClassNotFound exceptions
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(2)) {

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, uname, pwd);

                System.out.println("Database connected.");
            }
        } catch (Exception e) {
            System.out.println("Database connection error.");
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
