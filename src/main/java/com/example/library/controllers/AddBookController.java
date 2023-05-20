package com.example.library.controllers;

import com.example.library.MainApplication;
import com.example.library.dao.BookDao;
import com.example.library.entities.Book;
import com.example.library.entities.InputFormat;
import com.example.library.errors.ErrorMessages;
import com.example.library.exceptions.ValidationInputException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddBookController extends Controller {

  @FXML
  private TextField titleInput;

  @FXML
  private TextField authorInput;

  @FXML
  private TextField genreInput;

  @FXML
  private TextField publisherInput;

  @FXML
  private TextField yearOfPublicationInput;

  @FXML
  private TextField inventoryNumberInput;

  @FXML
  private TextField quantityInput;

  @FXML
  private Label validationMessage;

  @FXML
  protected void onAddButtonClick()
      throws ValidationInputException, NoSuchAlgorithmException, IOException {
    try {
      validateInputs();
      setNewBookInDatabase();
    } catch (ValidationInputException | NoSuchAlgorithmException | IOException e) {
    }
  }

  public void initialize() {
    setInputTextFormat(InputFormat.ONLY_DIGITS, yearOfPublicationInput);
  }

  private void validateInputs() throws ValidationInputException {
    if (titleInput.getText().isBlank() || authorInput.getText().isBlank() || genreInput.getText()
        .isBlank()) {
      validationMessage.setText(
          ErrorMessages.emptyInputs);
      throw new ValidationInputException(ErrorMessages.emptyInputs);
    }
  }

  private void setNewBookInDatabase() throws NoSuchAlgorithmException, IOException {
    Book newBook = new Book(null, titleInput.getText(), authorInput.getText(), genreInput.getText(),
        publisherInput.getText(), Integer.parseInt(yearOfPublicationInput.getText()), true,
        inventoryNumberInput.getText(),
        Integer.parseInt(quantityInput.getText()), LocalDateTime.now(), LocalDateTime.now());
    new BookDao().save(newBook);

    MainApplication.changeScene("views/admin-operations.fxml", 520, 500);
  }
}