package com.example.library.controllers;

import com.example.library.entities.ComboBoxType;
import com.example.library.entities.InputFormat;
import com.example.library.entities.NotificationType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Duration;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.controlsfx.control.Notifications;

public class Controller {

  private static final int FIFTEEN_DAYS = 15;
  private static final int THIRTY_DAYS = 30;
  protected LocalDate returnDate;

  protected void setInputTextFormat(InputFormat format, TextField input) {
    switch (format) {
      case ONLY_DIGITS -> {
        Pattern pattern = Pattern.compile("\\d*"); // Only allow digits and start with 0
        setFormat(pattern, input);
      }
      case MOBILE_NUMBER -> {
        Pattern pattern = Pattern.compile("^(?:\\+|0)[0-9]{9,15}$\n"); // Only allow digits
        setFormat(pattern, input);
      }
      default -> {
      }
    }
  }

  protected void setValuesToCombox(ComboBoxType type, ComboBox comboBox) {
    switch (type) {
      case TYPE_USER -> {
        comboBox.getItems().addAll("Читател", "Администратор");
        comboBox.setValue("Читател");
      }

      default -> {
        break;
      }
    }
  }

  protected void formatLocalDateTime(TableColumn dateColumn) {
    // Set the cell factory for the TableColumn variable to format the LocalDateTime value
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    dateColumn.setCellFactory(column -> new TextFieldTableCell<>(
        new LocalDateTimeStringConverter(formatter, formatter)));
  }

  protected void formatLocalDate(TableColumn dateColumn) {
    // Set the cell factory for the TableColumn variable to format the LocalDate value
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    dateColumn.setCellFactory(column -> new TextFieldTableCell<>(
        new LocalDateStringConverter(formatter, formatter)));
  }

  protected void showDeadlineDialog(String titleDialog, String headerTextDialog,
      String contentTextDialog) {
    ChoiceDialog<Integer> dialog =
        new ChoiceDialog<>(FIFTEEN_DAYS, List.of(FIFTEEN_DAYS, THIRTY_DAYS));
    dialog.setTitle(titleDialog);
    dialog.setHeaderText(headerTextDialog);
    dialog.setContentText(contentTextDialog);

    ObservableValue<Integer> selected = dialog.selectedItemProperty();
    selected.addListener((observable, oldValue, newValue) -> {

    });

    Optional<Integer> result = dialog.showAndWait();
    if (result.isPresent()) {
      Integer inputDays = result.get();
      returnDate = LocalDate.now().plusDays(inputDays);
    }
  }

  protected void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Грешка");
    alert.setHeaderText(title);
    alert.setContentText(content);
    alert.showAndWait();
  }

  protected void showNotification(String content, NotificationType type) {

    switch (type) {
      case SUCCESS -> {
        Notifications.create()
            .title("Успешна операция")
            .text(content)
            .hideAfter(Duration.seconds(5))
            .showConfirm();
      }
      case WARNING -> {
        Notifications.create()
            .title("Неуспешна операция")
            .text(content)
            .hideAfter(Duration.seconds(5))
            .showWarning();
      }

      case ERROR -> {
        Notifications.create()
            .title("Неуспешна операция")
            .text(content)
            .hideAfter(Duration.seconds(5))
            .showError();
      }
    }
  }

  private boolean isValidDateInput(String input) {
    return Pattern.matches("\\d{4}-\\d{2}-\\d{2}", input);
  }

  private void setFormat(Pattern pattern, TextField input) {
    UnaryOperator<TextFormatter.Change> filter = change -> {
      String newText = change.getControlNewText();
      if (pattern.matcher(newText).matches()) {
        return change;
      } else {
        return null;
      }
    };
    TextFormatter<String> textFormatter = new TextFormatter<>(filter);
    input.setTextFormatter(textFormatter);
  }
}
