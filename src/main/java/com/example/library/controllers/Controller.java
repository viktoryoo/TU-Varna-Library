package com.example.library.controllers;

import com.example.library.entities.ComboBoxType;
import com.example.library.entities.InputFormat;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

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

    protected void formatDate(TableColumn dateColumn) {
        // Set the cell factory for the TableColumn variable to format the LocalDateTime value
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        dateColumn.setCellFactory(column -> new TextFieldTableCell<>(
                new LocalDateTimeStringConverter(formatter, formatter)));
    }

    protected void showDateInputDialog(String titleDialog, String headerTextDialog,
                                       String contentTextDialog) {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(FIFTEEN_DAYS, List.of(FIFTEEN_DAYS, THIRTY_DAYS));
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
