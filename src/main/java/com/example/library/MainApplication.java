package com.example.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MainApplication extends Application {

  private static Stage primaryStage;
  private static final Logger logger = LogManager.getLogger(MainApplication.class);

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/login.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 520, 400);
    primaryStage.setTitle("Библиотека");
    primaryStage.setScene(scene);
    primaryStage.show();
    this.primaryStage = primaryStage;
    logger.info("Application started successfully!");
  }

  public static void main(String[] args) {
    launch(args);
  }

  public static void changeScene(String fxml, int width, int height) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
    Scene scene = new Scene(fxmlLoader.load(), width, height);
    primaryStage.setScene(scene);
    logger.info("Scene changed successfully!");
  }
}
