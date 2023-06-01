package com.example.library.controllers;

import com.example.library.MainApplication;
import com.example.library.dao.BookDao;
import com.example.library.dao.BorrowedBookDao;
import com.example.library.dto.UserBook;
import com.example.library.entities.Book;
import com.example.library.entities.BorrowedBook;
import com.example.library.entities.User;
import com.example.library.helpers.ServiceLocator;
import com.example.library.mappers.UserBookMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReturnBookSelectionController extends Controller {
  @FXML
  private TableView<UserBook> books;

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
  private TableColumn<User, LocalDateTime> loanDateColumn;

  @FXML
  private TableColumn<User, LocalDateTime> returnDateColumn;

  @FXML
  private TextField searchFilterInput;

  private FilteredList<UserBook> filteredBooks;

  private Integer readerId;
  private final BorrowedBookDao borrowedBookDao = ServiceLocator.getInstance().getBorrowedBookDao();
  private final BookDao bookDao = ServiceLocator.getInstance().getBookDao();
  private static final Logger logger = LogManager.getLogger(ReturnBookSelectionController.class);


  @FXML
  void giveBook() {
    UserBook selectedBook = books.getSelectionModel().getSelectedItem();
    if (Objects.isNull(selectedBook)) {
      logger.info(String.format("User %s tried to borrow a book, but no book was selected.", this.readerId));
      return;
    }

    Optional<BorrowedBook> userBorrowedBooks = borrowedBookDao.getBorrowedReaderId(this.readerId, selectedBook.getId());
    if (userBorrowedBooks.isEmpty()) {
      logger.info(String.format("User %s tried to return book %s, but has not borrowed it.", this.readerId, selectedBook.getId()));
      return;
    }

    updateBooks(selectedBook, userBorrowedBooks);
    refreshTable();
  }

  private boolean updateBooks(UserBook selectedBook, Optional<BorrowedBook> userBorrowedBooks) {
    BorrowedBook borrowedBook = userBorrowedBooks.get();
    borrowedBook.setReturned(true);
    borrowedBookDao.update(borrowedBook);

    Optional<Book> book = bookDao.get(selectedBook.getId());
    if (book.isEmpty()) {
      logger.info(String.format("User %s tried to return book %s, but it does not exist.", this.readerId, selectedBook.getId()));
      return true;
    }

    Book returnedBook = book.get();
    returnedBook.setQuantity(returnedBook.getQuantity() + 1);
    returnedBook.setBorrowedQuantity(returnedBook.getBorrowedQuantity() - 1);
    bookDao.update(returnedBook);
    return false;
  }

  void refreshTable() {
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
    loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
    returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

    formatLocalDate(loanDateColumn);
    formatLocalDate(returnDateColumn);

    loadData();
    setupSearchFilter();
  }

  private void loadData() {
    if (readerId == null) {
      return;
    }
    try {
      List<UserBook> allBooks = borrowedBookDao.getAllByReaderId(readerId).stream()
          .map(borrowedBook -> {
            var optional = bookDao.get(borrowedBook.getBookId());
            if (optional.isPresent()) {
              Book book = optional.get();
              return UserBookMapper.toUserBook(book, borrowedBook);
            }
            return null;
          })
          .filter(Objects::nonNull)
          .toList();
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
