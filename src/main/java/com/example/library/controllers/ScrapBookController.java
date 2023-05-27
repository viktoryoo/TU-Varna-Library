package com.example.library.controllers;

import com.example.library.MainApplication;
import com.example.library.dao.BookDao;
import com.example.library.entities.Book;
import com.example.library.entities.InputFormat;
import com.example.library.entities.User;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ScrapBookController extends Controller {
  @FXML
  private TableView<Book> books;

  @FXML
  private TableColumn<Book, String> inventoryNumberColumn;

  @FXML
  private TableColumn<Book, String> titleColumn;

  @FXML
  private TableColumn<Book, String> authorColumn;

  @FXML
  private TableColumn<Book, String> genreColumn;

  @FXML
  private TableColumn<Book, String> publisherColumn;

  @FXML
  private TableColumn<Book, Integer> yearOfPublicationColumn;

  @FXML
  private TableColumn<Book, Integer> quantityColumn;

  @FXML
  private TableColumn<User, LocalDateTime> createdAtColumn;

  @FXML
  private TableColumn<User, LocalDateTime> updatedAtColumn;

  @FXML
  private TextField searchFilterInput;

  private FilteredList<Book> filteredBooks;

  private int quantityToScrap;

  @FXML
  void scrapBook() {
    Book selectedBook = books.getSelectionModel().getSelectedItem();
    if (selectedBook != null) {
      int currentQuantity = selectedBook.getQuantity();
      showConfirmationModal(currentQuantity);

      if (quantityToScrap != 0) {
        int remainingQuantity = currentQuantity - quantityToScrap;

        if (remainingQuantity > 0) {
          selectedBook.setAvailable(true);
          selectedBook.setQuantity(remainingQuantity);
          new BookDao().update(selectedBook);

          for (Book book : filteredBooks) {
            if (book.equals(selectedBook)) {
              book.setQuantity(remainingQuantity);
              break;
            }
          }

          books.refresh();
        } else if (remainingQuantity == 0) {
          selectedBook.setAvailable(false);
          selectedBook.setQuantity(remainingQuantity);
          new BookDao().update(selectedBook);
          filteredBooks.getSource().remove(selectedBook);
        }
      }
    }
  }

  private void showConfirmationModal(int currentQuantity) {
    TextInputDialog modal = new TextInputDialog();
    TextField inputField = modal.getEditor();
    setInputTextFormat(InputFormat.ONLY_DIGITS, inputField);
    modal.setTitle("Количество");
    modal.setHeaderText("Колко броя книги желаете да бракувате?");
    modal.setContentText("Брой книги:");

    Optional<String> result = modal.showAndWait();

    if (result.isPresent() && !result.get().isBlank()) {
      int providedQuantity = Integer.valueOf(result.get());

      if (providedQuantity <= currentQuantity) {
        quantityToScrap = providedQuantity;
      } else {
        // Show notification for exceeded quantity
        Notifications.create()
            .title("Надвишено количество")
            .text("Предоставеното количество надвишава броя на книгите")
            .hideAfter(Duration.seconds(5)) // Duration to display the notification (optional)
            .showWarning();
      }
    }
  }

  @FXML
  void getBack() throws IOException {
    MainApplication.changeScene("views/admin-operations.fxml", 520, 500);
  }

  public void initialize() {
    inventoryNumberColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryNumber"));
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    yearOfPublicationColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfPublication"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

    formatDate(createdAtColumn);
    formatDate(updatedAtColumn);

    loadData();
    setupSearchFilter();
  }

  private void loadData() {
    try {
      List<Book> allBooks = new BookDao().getAllAvailable();
      books.getItems().clear();

      filteredBooks = new FilteredList<>(FXCollections.observableArrayList(allBooks));
      books.setItems(filteredBooks);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setupSearchFilter() {
    searchFilterInput.textProperty().addListener((observable, oldValue, newValue) -> {
      filteredBooks.setPredicate(book -> {
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowercaseFilter = newValue.toLowerCase();

        if (book.getTitle().toLowerCase().contains(lowercaseFilter)) {
          return true;
        } else if (book.getAuthor().toLowerCase().contains(lowercaseFilter)) {
          return true;
        } else if (book.getPublisher().toLowerCase().contains(lowercaseFilter)) {
          return true;
        } else if (book.getGenre().toLowerCase().contains(lowercaseFilter)) {
          return true;
        }

        // No match is found
        return false;
      });
    });
  }
}
