package com.example.library.controllers;

import com.example.library.MainApplication;
import com.example.library.dao.UserDao;
import com.example.library.entities.User;
import com.example.library.helpers.ServiceLocator;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class GiveBookController extends Controller {

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

  private int selectedUserId;
  private UserDao userDao = ServiceLocator.getInstance().getUserDao();

  @FXML
  void getBack() throws IOException {
    MainApplication.changeScene("views/admin-operations.fxml", 520, 500);
  }

  @FXML
  void giveBook() throws IOException {
    User selectedUser = readers.getSelectionModel().getSelectedItem();
    selectedUserId = selectedUser.getId();

    // Load the target view FXML file
    FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("views/book-selection.fxml"));
    Parent root = loader.load();
    // Get the controller of the target view
    BookSelectionController bookSelectionController = loader.getController();
    bookSelectionController.setReaderId(selectedUserId);
    // Create a new scene with the target view
    Scene scene = new Scene(root);
    // Get the current stage and set the new scene
    Stage stage = (Stage) searchFilterInput.getScene().getWindow();
    stage.setScene(scene);
  }

  public void initialize() {
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
      List<User> allUsers = userDao.getAllAssignedReaders();

      filteredReaders = new FilteredList<>(FXCollections.observableArrayList(allUsers));
      readers.setItems(filteredReaders);
      readers.refresh();
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
