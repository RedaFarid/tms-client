package soulintec.com.tmsclient.Graphics.Controls;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class EnhancedTextField extends TextField {
    private IntegerProperty maxLength = new SimpleIntegerProperty(this, "maxLength", -1);
    private StringProperty restrict = new SimpleStringProperty(this, "restrict");
    private Callback<String, Double> callback = (String param) -> null;
    private Background background;
    private String oldText = "";

    public EnhancedTextField() {
        setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0.5))));
        background = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
        setMaxHeight(20);
//        textProperty().addListener(new ChangeListener<String>() {
//            private boolean ignore;
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
////                setText(StringUtils.isBlank(s1)? "":s1.toUpperCase());
//                if (ignore || s1.isBlank()) {
//                    return;
//                }
//                if (maxLength.get() > -1 && s1.length() > maxLength.get()) {
//                    ignore = true;
//                    setText(s1.substring(0, maxLength.get()));
//                    ignore = false;
//                }
//                if (restrict.get() != null && !restrict.get().equals("") && !s1.matches(restrict.get() + "*")) {
//                    ignore = true;
//                    setText(s);
//                    ignore = false;
//                }
//            }
//        });
        addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (isEditable()) {
                switch (event.getCode()) {
                    case ENTER:
                        oldText = getText();
                        callback.call("");
//                        setBackground(background);
                        break;
                    case ESCAPE:
                        setText(oldText);
//                        setBackground(background);
                        break;
                    case TAB:
                        break;
                    default:
                        if (isEditable()) {
//                            setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                        break;
                }
            }
        });
    }

    public IntegerProperty maxLengthProperty() {
        return maxLength;
    }

    public int getMaxLength() {
        return maxLength.get();
    }

    public void setMaxLength(int maxLength) {
        this.maxLength.set(maxLength);
    }

    public StringProperty restrictProperty() {
        return restrict;
    }

    public String getRestrict() {
        return restrict.get();
    }

    public void setRestrict(String restrict) {
        this.restrict.set(restrict);
    }

    public void onEnterKeyPressed(Callback<String, Double> callback) {
        this.callback = callback;
    }

}
