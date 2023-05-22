package com.example.library.controllers;

import com.example.library.MainApplication;
import java.io.IOException;
import javafx.fxml.FXML;

public class AdminOperationsController {

  @FXML
  protected void createNewUser() throws IOException {
    MainApplication.changeScene("views/create-user.fxml", 520, 600);
  }

  @FXML
  protected void cancelUser() throws IOException {
    MainApplication.changeScene("views/unassign-user.fxml", 620, 600);
  }

  @FXML
  protected void renderBooks() throws IOException {
    MainApplication.changeScene("views/hello-view.fxml", 520, 500);
  }

  @FXML
  protected void addBooks() throws IOException {
    MainApplication.changeScene("views/add-book.fxml", 520, 600);
  }

  @FXML
  protected void rentBooks() throws IOException {
    MainApplication.changeScene("views/hello-view.fxml", 520, 500);
  }

  @FXML
  protected void returnBooks() throws IOException {
    MainApplication.changeScene("views/hello-view.fxml", 520, 500);
  }

  @FXML
  protected void scrapBooks() throws IOException {
    MainApplication.changeScene("views/scrap-book.fxml", 820, 600);
  }

  @FXML
  protected void archiveBooks() throws IOException {
    MainApplication.changeScene("views/hello-view.fxml", 520, 500);
  }

  @FXML
  protected void countReferences() throws IOException {
    MainApplication.changeScene("views/hello-view.fxml", 520, 500);
  }

  @FXML
  protected void booksReferences() throws IOException {
    MainApplication.changeScene("views/hello-view.fxml", 520, 500);
  }

  @FXML
  protected void readersReferences() throws IOException {
    MainApplication.changeScene("views/hello-view.fxml", 520, 500);
  }

  @FXML
  protected void readersRatingReferences() throws IOException {
    MainApplication.changeScene("views/hello-view.fxml", 520, 500);
  }

  @FXML
  void getBack() throws IOException {
    MainApplication.changeScene("views/login.fxml", 520, 400);
  }
}