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
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

@Log4j2
public class EnhancedIntegerField extends TextField {
    private volatile boolean authorized = false;

    private final IntegerProperty integerValue = new SimpleIntegerProperty(-1);
    private final IntegerProperty maxLength = new SimpleIntegerProperty(this, "maxLength", -1);
    private final StringProperty restrict = new SimpleStringProperty(this, "restrict");
    private Callback<String, Double> callback = (String param) -> null;
    private final Background back;
    private String oldText = "";

    public EnhancedIntegerField() {
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
                integerValue.setValue(Integer.parseInt(getText()));
            }catch (Exception e){
                Platform.runLater(() -> {
                    setText(String.valueOf(getIntegerValue()));
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
        integerValue.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                try {
                    double v = Integer.parseInt(getText());
                    if (t1.intValue() != v) {
                        setText(String.valueOf(t1));
                    }
                } catch (Exception e) {
                    setText(String.valueOf(t1));
                }

            }
        });
    }

    public void setAuthority() {
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

    public int getIntegerValue() {
        return integerValue.get();
    }
    public IntegerProperty integerValueProperty() {
        return integerValue;
    }
    public void setIntegerValue(int integerValue) {
        this.integerValue.set(integerValue);
    }
}
