package com.example.library.controllers;

import com.example.library.MainApplication;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
  @FXML
  private Button loginButton;

  @FXML
  private TextField emailInput;

  @FXML
  private PasswordField passwordInput;

  @FXML
  private Label wrongCredentials;


  @FXML
  protected void onLoginButtonClick() throws IOException {
    if (emailInput.getText().equals("admin") && passwordInput.getText().equals("admin")) {
      MainApplication.changeScene("hello-view.fxml");
    } else {
      wrongCredentials.setText("Wrong email or password!");
    }
  }
}