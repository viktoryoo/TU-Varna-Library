package com.example.library.controllers;

import com.example.library.MainApplication;
import com.example.library.dao.UserDao;
import com.example.library.entities.User;
import com.example.library.errors.ErrorMessages;
import com.example.library.exceptions.ValidationInputException;
import com.example.library.helpers.HashPasswordHelper;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
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
  protected void onLoginButtonClick() throws NoSuchAlgorithmException, ValidationInputException {
    validateLogin();
  }

  public void validateLogin() throws NoSuchAlgorithmException, ValidationInputException {
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    if (!emailInput.getText().matches(emailRegex)) {
      wrongCredentials.setText(
          ErrorMessages.invalidEmail);
      throw new ValidationInputException(ErrorMessages.invalidEmail);
    }

    String cryptedPassword = HashPasswordHelper.hashPassword(passwordInput.getText());
    Optional<User> newUser = new UserDao().findUser(emailInput.getText(), cryptedPassword);

    try {
      //TODO: Check if is admin or reader
      if (newUser.isPresent()) {
        MainApplication.changeScene("views/admin-operations.fxml", 520, 500);
      } else {
        wrongCredentials.setText("Грешен имейл или парола!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}