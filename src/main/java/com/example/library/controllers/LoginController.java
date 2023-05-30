package com.example.library.controllers;

import com.example.library.MainApplication;
import com.example.library.dao.UserDao;
import com.example.library.entities.User;
import com.example.library.errors.ErrorMessages;
import com.example.library.exceptions.ValidationInputException;
import com.example.library.helpers.HashPasswordHelper;
import com.example.library.helpers.ServiceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class LoginController {

  @FXML
  private TextField emailInput;

  @FXML
  private PasswordField passwordInput;

  @FXML
  private Label wrongCredentials;

  private static final Logger logger = LogManager.getLogger(LoginController.class);
  private UserDao userDao = ServiceLocator.getInstance().getUserDao();

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
    Optional<User> newUser = userDao.findUser(emailInput.getText(), cryptedPassword);

    try {
      //TODO: Check if is admin or reader
      if (newUser.isPresent()) {
          MainApplication.changeScene("views/admin-operations.fxml", 520, 500);
          logger.info("User {} logged in successfully!", newUser.get().getEmail());
      } else {
        wrongCredentials.setText("Грешен имейл или парола!");
        logger.warn("Wrong credentials!");
      }
    } catch (IOException e) {
      logger.error("Error while validating login: {}",  e.getMessage());
    }
  }
}