package com.example.library;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

  private static Stage primaryStage;

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 520, 400);
    primaryStage.setTitle("Library");
    primaryStage.setScene(scene);
    primaryStage.show();
    this.primaryStage = primaryStage;
  }

  public static void main (String[] args) {
    launch(args);
  }


  public static void changeScene(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    primaryStage.setScene(scene);
  }
}
