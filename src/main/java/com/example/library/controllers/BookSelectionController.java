package com.example.library.controllers;

import com.example.library.MainApplication;
import com.example.library.dao.BookDao;
import com.example.library.dao.BorrowedBookDao;
import com.example.library.entities.Book;
import com.example.library.entities.BorrowedBook;
import com.example.library.entities.NotificationType;
import com.example.library.entities.User;
import com.example.library.helpers.ServiceLocator;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class BookSelectionController extends Controller {
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
  private TableColumn<Book, Integer> borrowedQuantityColumn;

  @FXML
  private TableColumn<User, LocalDateTime> createdAtColumn;

  @FXML
  private TableColumn<User, LocalDateTime> updatedAtColumn;

  @FXML
  private TextField searchFilterInput;

  private FilteredList<Book> filteredBooks;

  private Integer readerId;
  private final BorrowedBookDao borrowedBookDao = ServiceLocator.getInstance().getBorrowedBookDao();
  private final BookDao bookDao = ServiceLocator.getInstance().getBookDao();
  private static final Logger logger = LogManager.getLogger(BookSelectionController.class);

  @FXML
  void giveBook() {
    Book selectedBook = books.getSelectionModel().getSelectedItem();
    if (Objects.isNull(selectedBook)) {
      showAlert("Грешна операция!",
          "Потребителят се пробва да извърши операция преди да е избрал елемент.");
      logger.info(String.format("User %s tried to borrow a book, but no book was selected.",
          this.readerId));
      return;
    }

    if (selectedBook.getQuantity() <= selectedBook.getBorrowedQuantity()) {
      showAlert("Няма налични бройки от тази книга!", "Моля, изберете друга книга.");
      logger.info(
          String.format("User %s tried to borrow book %s, but there are no available copies.",
              this.readerId, selectedBook.getId()));
      return;
    }

    List<BorrowedBook> userBorrowedBooks = borrowedBookDao.getAllByReaderId(this.readerId);
    if (userBorrowedBooks.stream()
        .anyMatch(borrowedBook -> borrowedBook.getBookId() == selectedBook.getId())) {
      showAlert("Читателят вече е взел тази книга!", "Моля, изберете друга книга.");
      logger.info(
          String.format("User %s tried to borrow book %s, but already has it.", this.readerId,
              selectedBook.getId()));
      return;
    }

    showDeadlineDialog("Изберете срок за връщане",
        "Изберете краен срок за връщане на книгата 15 или 30 дни.",
        "Срок на отдаване (дни):");
    if (returnDate != null) {
      BorrowedBook borrowedBook = new BorrowedBook(null, selectedBook.getId(), this.readerId,
          LocalDate.now(), returnDate, false, LocalDateTime.now(), LocalDateTime.now());
      borrowedBookDao.save(borrowedBook);
      bookDao.get(selectedBook.getId()).ifPresent(book -> {
        book.setBorrowedQuantity(book.getBorrowedQuantity() + 1);
        bookDao.update(book);
      });
      refreshTable();
      logger.info(String.format("User %s borrowed book %s.", this.readerId, selectedBook.getId()));
      showNotification("Успещно отдадохте книга.", NotificationType.SUCCESS);
    }
  }

  private void refreshTable() {
    loadData();
    setupSearchFilter();
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
    borrowedQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedQuantity"));
    createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

    formatLocalDateTime(createdAtColumn);
    formatLocalDateTime(updatedAtColumn);

    loadData();
    setupSearchFilter();
  }

  private void loadData() {
    try {
      List<Book> allBooks = bookDao.getAllAvailable();
      filteredBooks = new FilteredList<>(FXCollections.observableArrayList(allBooks));
      books.setItems(filteredBooks);
      books.refresh();
    } catch (Exception e) {
      logger.error("Error while loading books.", e);
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

  public void setReaderId(Integer readerId) {
    this.readerId = readerId;
  }
}
