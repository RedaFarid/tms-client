package soulintec.com.tmsclient.Graphics.Controls;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

public class EnhancedLongField extends TextField {
    private IntegerProperty longValue = new SimpleIntegerProperty(-1);
    private IntegerProperty maxLength = new SimpleIntegerProperty(this, "maxLength", -1);
    private StringProperty restrict = new SimpleStringProperty(this, "restrict");
    private Callback<String, Double> callback = (String param) -> null;
    private Background back;
    private String oldText = "";

    public EnhancedLongField() {
        back = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
        setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0.5))));
        setMaxHeight(20);
        textProperty().addListener((observableValue, s, s1) -> {
            setText(s1);
            if (StringUtils.isBlank(s1)) {
                return;
            }
            if (maxLength.get() > -1 && s1.length() > maxLength.get()) {
                setText(s1.substring(0, maxLength.get()));
            }
            if (restrict.get() != null && !restrict.get().equals("") && !s1.matches(restrict.get() + "*")) {
                setText(s);
            }
            try {
                longValue.setValue(Long.parseLong(getText()));
            }catch (Exception e){
                Platform.runLater(() -> {
                    setText(String.valueOf(getLongValue()));
                });
            }
        });
        addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (isEditable()) {
                switch (event.getCode()) {
                    case ENTER:
                        oldText = getText();
                        callback.call("");
//                        setBackground(back);
                        break;
                    case ESCAPE:
                        setText(oldText);
//                        setBackground(back);
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
        longValue.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                try {
                    double v = Long.parseLong(getText());
                    if (t1.longValue() != v) {
                        setText(String.valueOf(t1));
                    }
                } catch (Exception e) {
                    setText(String.valueOf(t1));
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

    public int getLongValue() {
        return longValue.get();
    }
    public IntegerProperty longValueProperty() {
        return longValue;
    }
    public void setLongValue(int longValue) {
        this.longValue.set(longValue);
    }
}
