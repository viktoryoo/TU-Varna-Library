package com.example.library.controllers;

import com.example.library.MainApplication;
import com.example.library.database.DatabaseConnection;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
  @FXML
  private Button loginButton;

  @FXML
  private TextField emailInput;

  @FXML
  private PasswordField passwordInput;

  @FXML
  private Label wrongCredentials;

  @FXML
  protected void onLoginButtonClick() throws IOException, NoSuchAlgorithmException {
    validateLogin();
  }

  public void validateLogin() throws NoSuchAlgorithmException {
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();
    String cryptedPassword = cryptPassword(passwordInput.getText());

    String userQuery = "SELECT count(1) from Users WHERE email = '"
        + emailInput.getText()
        + "' AND password = '"
        + cryptedPassword
        + "'";

    try {
      Statement statement = connectDB.createStatement();
      ResultSet queryResult = statement.executeQuery(userQuery);

      while (queryResult.next()) {
        if (queryResult.getInt(1) == 1) {
          MainApplication.changeScene("hello-view.fxml");
        } else {
          wrongCredentials.setText("Wrong email or password!");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String cryptPassword(String password) throws NoSuchAlgorithmException {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(StandardCharsets.UTF_8.encode(password));

    return String.format("%032x", new BigInteger(1, md5.digest()));
  }
}