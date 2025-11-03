package com.bloodbridge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Updated connection URL with public key retrieval allowed
    private static final String URL = "jdbc:mysql://localhost:3306/bloodbridge?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "admin@123";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Make sure the connector JAR is on the classpath.");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

     // ✅ Add this main() method to test your connection
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("✅ Database connection successful!");
            } else {
                System.out.println("❌ Failed to connect to database!");
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to database:");
            e.printStackTrace();
        }
    }
}
