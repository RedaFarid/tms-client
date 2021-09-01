package soulintec.com.tmsclient.Graphics.Controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class DataEntryPartitionTitled extends VBox {
    private final GridPane pane = new GridPane();
    private final Label partitionLabel = new Label();
    private final TitledPane tilePane = new TitledPane();


    public DataEntryPartitionTitled(String label) {
        partitionLabel.setText(label);
        partitionLabel.setAlignment(Pos.BASELINE_LEFT);
        partitionLabel.setTextAlignment(TextAlignment.LEFT);
        partitionLabel.prefWidthProperty().bind(widthProperty());
        partitionLabel.setBackground(new Background(new BackgroundFill(Color.valueOf("61a2b1"), new CornerRadii(0), new Insets(0))));
        partitionLabel.setPadding(new Insets(3,0,3,10));
        partitionLabel.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.GRAY,0.1, 0.1, 0.1, 0.1));
        partitionLabel.setStyle("-fx-text-fill:white;");

        pane.setVgap(2);
        pane.setHgap(5);
        pane.prefHeightProperty().bindBidirectional(prefHeightProperty());

        tilePane.setText(label);
        tilePane.setContent(pane);
        getChildren().addAll(tilePane);
    }

    public final void add(Node node, int i, int i1) {
        pane.add(node, i, i1);
    }
    public final void add(Node node, int i, int i1, int i2, int i3) {
        pane.add(node, i, i1, i2, i3);
    }
    public final void addRow(int i, Node... nodes) {
        pane.addRow(i, nodes);
    }
    public final void addColumn(int i, Node... nodes) {
        pane.addColumn(i, nodes);
    }
    public final void setVgap(double var1) {
        pane.vgapProperty().set(var1);
    }
    public final void setHgap(double var1) {
        pane.hgapProperty().set(var1);
    }
    public final void setPanePadding(Insets var1) {
        pane.paddingProperty().set(var1);
    }

}