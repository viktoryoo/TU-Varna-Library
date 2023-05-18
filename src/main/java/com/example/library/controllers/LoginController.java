package com.example.library.controllers;

import com.example.library.MainApplication;
import com.example.library.database.DatabaseConnection;
import com.example.library.helpers.HashPasswordHelper;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

  @FXML
  private TextField emailInput;

  @FXML
  private PasswordField passwordInput;

  @FXML
  private Label wrongCredentials;

  @FXML
  protected void onLoginButtonClick() throws NoSuchAlgorithmException {
    validateLogin();
  }

  public void validateLogin() throws NoSuchAlgorithmException {
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();
    String cryptedPassword = HashPasswordHelper.cryptPassword(passwordInput.getText());

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
          MainApplication.changeScene("views/admin-operations.fxml", 520, 500);
        } else {
          wrongCredentials.setText("Грешен имейл или парола!");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}