package com.example.demo.database;

import com.example.demo.entities.MyUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRequest {
    private String username;
    private String password;

    // Standardkonstruktor (krävs av Spring)
    public MyUser LoginRequest(String username, String password) {
        MyUser loggedinUser = null;

    //
        String query = "SELECT * FROM users WHERE username =?"; // AND password = ?";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
           // preparedStatement.setString(2, encodedPwd);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean result = resultSet.next();
                if (result) {
//                    String encodedPwd = passwordEncoder.encode(password);
                    loggedinUser = new MyUser();
  //                  loggedinUser.setFirstName(resultSet.getNString(2));
    //                loggedinUser.setLastName(resultSet.getNString(3));
                    loggedinUser.setUsername(resultSet.getNString(5));
                    loggedinUser.setId((resultSet.getLong(1)));
                }
                return loggedinUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    // Getter-metoder (krävs för att kunna anropa request.getUsername() och request.getPassword())
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setter-metoder (krävs om Spring ska kunna fylla i fälten)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
