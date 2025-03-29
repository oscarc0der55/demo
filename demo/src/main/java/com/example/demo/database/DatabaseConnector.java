package com.example.demo.database;

import java.sql.*;

public class DatabaseConnector {
    private static final String JDBC_URL = "jdbc:mysql://localhost/db_demo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }
}
