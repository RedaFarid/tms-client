package soulintec.com.tmsclient.Graphics.Controls;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.function.Consumer;

public class EnhancedDoubleIOField extends TextField {

    private final ChangeListener<Number> changeListener;
    private DoubleProperty value = new SimpleDoubleProperty(0);
    private Consumer<Double> consumer;

    public EnhancedDoubleIOField() {

        changeListener = (observable, oldValue, newValue) -> Platform.runLater(() -> setText(String.valueOf(newValue)));

        setOnMouseClicked(action -> {
           removeListenerToValue();
        });
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                removeListenerToValue();
            }else {
                addListenerToValue();
            }
        });
        setOnKeyPressed(action -> {
            if (action.getCode().equals(KeyCode.ENTER)){
                if (consumer != null){
                    consumer.accept(Double.parseDouble(getText()));
                    addListenerToValue();
                }
            }
            if (action.getCode().equals(KeyCode.ESCAPE)){
                if (consumer != null){
                    addListenerToValue();
                }
            }
        });

    }
    public void setValueProperty(DoubleProperty value){
        if (value != null){
            this.value = value;
            addListenerToValue();
        }
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public double getValue() {
        if (value != null){
            return value.get();
        }
        return 0;
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setOnEditCommit(Consumer<Double> consumer) {
        this.consumer = consumer;
    }

    private void addListenerToValue(){
        if (value != null){
            removeListenerToValue();
            Platform.runLater(() -> setText(String.valueOf(value.getValue())));
            value.addListener(changeListener);
        }
    }
    private void removeListenerToValue(){
        if (value != null) {
            value.removeListener(changeListener);
        }
    }
}
