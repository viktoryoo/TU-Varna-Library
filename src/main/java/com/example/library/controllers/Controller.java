package com.example.library.controllers;

import com.example.library.MainApplication;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
  @FXML
  private Label welcomeText;

  @FXML
  private TextField username;

  @FXML
  private PasswordField password;


  @FXML
  protected void onLoginButtonClick() throws IOException {
    if (username.getText().equals("admin") && password.getText().equals("admin")) {
      MainApplication.changeScene("hello-view.fxml");
    } else {
      welcomeText.setText("Wrong email or password!");
    }


  }


}