package soulintec.com.tmsclient.Graphics.Controls;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import lombok.extern.log4j.Log4j2;

import java.util.function.Consumer;

@Log4j2
public class EnhancedIntegerIOField extends TextField {

    private final ChangeListener<Number> changeListener;
    private IntegerProperty value ;
    private Consumer<Integer> consumer;

    public EnhancedIntegerIOField() {

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
                    consumer.accept(Integer.parseInt(getText()));
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
    public void setValueProperty(IntegerProperty value){
        if (value != null){
            this.value = value;
            addListenerToValue();
        }
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public int getValue() {
        if (value != null){
            return value.get();
        }
        return 0;
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public void setOnEditCommit(Consumer<Integer> consumer) {
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

    public void setAuthority() {

    }
}
