package com.example.library.controllers;

import com.example.library.entities.ComboBoxType;
import com.example.library.entities.InputFormat;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.LocalDateTimeStringConverter;

public class Controller {

  protected void setInputTextFormat(InputFormat format, TextField input) {
    switch (format) {
      case ONLY_DIGITS -> {
        Pattern pattern = Pattern.compile("^0\\d*"); // Only allow digits
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

        break;
      }

      default -> {
        break;
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

  protected void formatDate(TableColumn dateColumn) {
    // Set the cell factory for the TableColumn variable to format the LocalDateTime value
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    dateColumn.setCellFactory(column -> new TextFieldTableCell<>(
        new LocalDateTimeStringConverter(formatter, formatter)));
  }
}
