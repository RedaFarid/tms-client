package soulintec.com.tmsclient.Graphics.Controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class PasswordFieldTableCell<S, T> extends TableCell<S, T> {

    private final ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty<StringConverter<T>>(this, "converter");
    private final PasswordField passwordField;

    public PasswordFieldTableCell() {
        this(null);
    }
    public PasswordFieldTableCell(StringConverter<T> converter) {
        this.getStyleClass().add("text-field-table-cell");
        setConverter(converter);

        passwordField = new PasswordField();
        passwordField.setOnAction(event -> {
            if (converter == null) {
                throw new IllegalStateException(
                        "Attempting to convert text input into Object, but provided "
                                + "StringConverter is null. Be sure to set a StringConverter "
                                + "in your cell factory.");
            }
            commitEdit(converter.fromString(passwordField.getText()));
            event.consume();
        });
        passwordField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
                t.consume();
            }
        });
    }

    public static <S> Callback<TableColumn<S,String>, TableCell<S,String>> forTableColumn() {
        return forTableColumn(new DefaultStringConverter());
    }
    public static <S,T> Callback<TableColumn<S,T>, TableCell<S,T>> forTableColumn(final StringConverter<T> converter) {
        return list -> new PasswordFieldTableCell<S,T>(converter);
    }

    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }

    public final void setConverter(StringConverter<T> value) {
        converterProperty().set(value);
    }
    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }

    @Override public void startEdit() {
        if (! isEditable() || ! getTableView().isEditable() || ! getTableColumn().isEditable()) {
            return;
        }

        super.startEdit();

        setText(null);
        setGraphic(passwordField);
    }
    @Override public void cancelEdit() {
        super.cancelEdit();

        setText("••••••••");
        setGraphic(null);
    }
    @Override public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (passwordField != null) {
                    passwordField.setText("••••••••");
                }
                setText(null);
                setGraphic(passwordField);
            } else {
                setText("••••••••");
                setGraphic(null);
            }
        }
    }
}
