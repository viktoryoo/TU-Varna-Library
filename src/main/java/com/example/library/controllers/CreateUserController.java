package com.example.library.controllers;

import com.example.library.MainApplication;
import com.example.library.dao.UserDao;
import com.example.library.entities.Role;
import com.example.library.entities.User;
import com.example.library.errors.ErrorMessages;
import com.example.library.exceptions.ValidationInputException;
import com.example.library.helpers.HashPasswordHelper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class CreateUserController {

  @FXML
  private TextField nameInput;

  @FXML
  private PasswordField passwordInput;

  @FXML
  private TextField emailInput;

  @FXML
  private TextField mobileNumberInput;

  @FXML
  private ComboBox<String> typeUser;

  @FXML
  private TextArea addressTextArea;

  @FXML
  private Label validationMessage;

  @FXML
  protected void OnCreateButtonClick()
      throws ValidationInputException, NoSuchAlgorithmException, IOException {
    try {
      validateInputs();
      setNewUserInDatabase();
    } catch (ValidationInputException | NoSuchAlgorithmException | IOException e) {
    }
  }

  private void validateInputs() throws ValidationInputException {
    int passwordValidLength = 8;
    int minimumMobileNumberSize = 10;
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    if (nameInput.getText().isBlank() || emailInput.getText().isBlank() || passwordInput.getText()
        .isBlank() || mobileNumberInput.getText().isBlank()) {
      validationMessage.setText(
          ErrorMessages.emptyInputs);
      throw new ValidationInputException(ErrorMessages.emptyInputs);
    }

    if (passwordInput.getText().length() < passwordValidLength) {
      validationMessage.setText(
          ErrorMessages.minimumPasswordLength);
      throw new ValidationInputException(ErrorMessages.minimumPasswordLength);
    }

    if (mobileNumberInput.getText().length() != minimumMobileNumberSize) {
      validationMessage.setText(
          ErrorMessages.invalidMobileNumberLength);
      throw new ValidationInputException(ErrorMessages.invalidMobileNumberLength);
    }

    if (!emailInput.getText().matches(emailRegex)) {
      validationMessage.setText(
          ErrorMessages.invalidEmail);
      throw new ValidationInputException(ErrorMessages.invalidEmail);
    }
  }

  public void initialize() {
    setMobileNumberInputToBeOnlyDigits();
    setTypeUsers();
  }

  private void setMobileNumberInputToBeOnlyDigits() {
    Pattern pattern = Pattern.compile("\\d*"); // Only allow digits
    UnaryOperator<TextFormatter.Change> filter = change -> {
      String newText = change.getControlNewText();
      if (pattern.matcher(newText).matches()) {
        return change;
      } else {
        return null;
      }
    };
    TextFormatter<String> textFormatter = new TextFormatter<>(filter);
    mobileNumberInput.setTextFormatter(textFormatter);
  }

  private void setTypeUsers() {
    typeUser.getItems().addAll("Читател", "Администратор");
    typeUser.setValue("Читател");
  }

  private void setNewUserInDatabase() throws NoSuchAlgorithmException, IOException {
    String hashPassword = HashPasswordHelper.hashPassword(passwordInput.getText());
    Role role = typeUser.getValue().equals("Читател") ? Role.READER : Role.ADMIN;
    int phoneNumber = Integer.parseInt(mobileNumberInput.getText());

    User newUser = new User(nameInput.getText(), addressTextArea.getText(),
        phoneNumber, emailInput.getText(), role,
        hashPassword);
    new UserDao().save(newUser);

    MainApplication.changeScene("views/admin-operations.fxml", 520, 500);
  }
}