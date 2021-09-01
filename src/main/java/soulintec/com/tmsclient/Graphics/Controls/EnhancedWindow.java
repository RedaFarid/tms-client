package soulintec.com.tmsclient.Graphics.Controls;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EnhancedWindow extends BorderPane {


    private final Stage stage;

    public EnhancedWindow(Stage stage) {
        this.stage = stage;
    }

    public void setAuthority() {

    }
}
