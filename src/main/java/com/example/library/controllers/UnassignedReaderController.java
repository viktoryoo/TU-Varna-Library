package com.example.library.controllers;

import com.example.library.dao.UserDao;
import com.example.library.entities.User;
import java.time.LocalDateTime;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

  @FXML
  private TextField searchFilterInput;

  private FilteredList<User> filteredReaders;

  @FXML
  void removeReader() {
    User selectedUser = readers.getSelectionModel().getSelectedItem();
    if (selectedUser != null) {
      selectedUser.setIsAssign(false);
      new UserDao().update(selectedUser);
      filteredReaders.getSource().remove(selectedUser);
    }
  }

  public void initialize() {
    // Set the cell value factories for each TableColumn
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

    formatDate(createdAtColumn);

    loadData();
    setupSearchFilter();
  }

  private void loadData() {
    try {
      List<User> allUsers = new UserDao().getAllAssignedReaders();
      readers.getItems().clear();

      filteredReaders = new FilteredList<>(FXCollections.observableArrayList(allUsers));
      readers.setItems(filteredReaders);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setupSearchFilter() {
    searchFilterInput.textProperty().addListener((observable, oldValue, newValue) -> {
      filteredReaders.setPredicate(user -> {
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowercaseFilter = newValue.toLowerCase();

        return user.getName().toLowerCase().contains(lowercaseFilter);
      });
    });
  }
}
