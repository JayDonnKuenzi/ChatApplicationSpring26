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
    // database settings

    private static String host = "172.172.161.211";        // database host
    private static String port = "3306";           // database port
    private static String uname = "student";      // database username
    private static String pwd = "password123";        // database password
    private static String databaseName = "chat_db";// database name
    private static Connection connection;          // shared connection

    // jdbc connection url
    private static final String URL
            = "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require";

    private Config() {
        // prevent instantiation
    }

    /**
     * Returns a singleton database connection.
     *
     * @return Connection
     *
     * - Loads the MySQL JDBC driver - Creates the connection only once
     * (singleton pattern) - Handles SQL and ClassNotFound exceptions
     */
    public static Connection getConnection() {
        try {
            // create or refresh connection if needed
            if (connection == null || connection.isClosed() || !connection.isValid(2)) {

                // load mysql driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // open connection
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
            // close connection if open
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
