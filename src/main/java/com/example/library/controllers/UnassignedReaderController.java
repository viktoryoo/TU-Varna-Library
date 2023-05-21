package com.example.library.controllers;

import com.example.library.dao.UserDao;
import com.example.library.entities.User;
import java.time.LocalDateTime;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UnassignedReaderController extends Controller {
  @FXML
  private TableView<User> readers;

  @FXML
  private TableColumn<User, String> nameColumn;

  @FXML
  private TableColumn<User, String> emailColumn;

  @FXML
  private TableColumn<User, Integer> phoneNumberColumn;

  @FXML
  private TableColumn<User, LocalDateTime> createdAtColumn;

  public void initialize() {
    // Set the cell value factories for each TableColumn
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

    formatDate(createdAtColumn);

    loadData();
  }

  private void loadData() {
    try {
      List<User> allUsers = new UserDao().getAllReaders();
      readers.getItems().clear();

      readers.getItems().addAll(allUsers);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
