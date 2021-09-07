package soulintec.com.tmsclient.Graphics.Windows.MainWindow.mainWindowSubNodes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class IconicButton extends HBox {

    private String windowIconRef;
    
    private ImageView icon = new ImageView();

    private Callback<String, String> callback = null;
    
    final private double height = 50;
    final private double width = 300;


    private Background normalBackground = new Background(new BackgroundFill(Color.CADETBLUE, new CornerRadii(0), Insets.EMPTY));

    public IconicButton(String windowIconRef) {
        this.windowIconRef = windowIconRef;
        graphicsBuilder();
    }

    private void graphicsBuilder() {
        setSpacing(5);
        setPadding(new Insets(5, 10, 5, 10));
        setPrefHeight(height);
        setBackground(normalBackground);
        setAlignment(Pos.TOP_RIGHT);
        
        if (windowIconRef != null) {
            Image image = new Image(windowIconRef);
            icon.setImage(image);
        }
        icon.setFitWidth(35);
        icon.setFitHeight(35);
        getChildren().add(icon);

        this.setOnMouseEntered(action -> this.setCursor(Cursor.HAND));
        this.setOnMouseExited(action -> this.setCursor(Cursor.DEFAULT));

    }
}
