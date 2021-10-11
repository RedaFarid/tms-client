package soulintec.com.tmsclient.Graphics.Windows.LogsWindow;


import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableFilter;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.TanksWindow.TanksController;

import java.time.LocalDateTime;

@Component
public class LogManagerView implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private LogController controller;
    private LogsModel model;

    protected static MainWindow initialStage;

    private Stage mainWindow;

    private VBox root;
    private ToolBar toolbar;

    private TableFilter<LogsModel.TableObject> tableFilter;
    private TableView<LogsModel.TableObject> table;
    private TableColumn<LogsModel.TableObject, StringProperty> sourceColumn;
    private TableColumn<LogsModel.TableObject, StringProperty> eventColumn;
    private TableColumn<LogsModel.TableObject, ObjectProperty<LogIdentifier>> identifierColumn;
    private TableColumn<LogsModel.TableObject, StringProperty> usernameColumn;
    private TableColumn<LogsModel.TableObject, StringProperty> onTerminalColumn;
    private TableColumn<LogsModel.TableObject, ObjectProperty<LocalDateTime>> timeColumn;

    private Button export_to_txt_file;

    private ObjectProperty<Cursor> CURSOR_DEFAULT;
    private ObjectProperty<Cursor> CURSOR_WAIT;

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainWindow = event.getStage();

        init();
        graphicsBuilder();
        actionHandling();
    }

    private void init() {
        initialStage = ApplicationContext.applicationContext.getBean(MainWindow.class);
        controller = ApplicationContext.applicationContext.getBean(LogController.class);
        model = controller.getModel();

        root = new VBox();
        toolbar = new ToolBar();

        table = new TableView<>();
        sourceColumn = new TableColumn<>("Source");
        eventColumn = new TableColumn<>("Event");
        identifierColumn = new TableColumn<>("Identifier");
        usernameColumn = new TableColumn<>("User name");
        onTerminalColumn = new TableColumn<>("On terminal");
        timeColumn = new TableColumn<>("Time");

        export_to_txt_file = new Button("Export to csv file");

        CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
        CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);
    }

    private void graphicsBuilder() {
        //table configuration
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("sourceColumn"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("eventColumn"));
        identifierColumn.setCellValueFactory(new PropertyValueFactory<>("logIdentifierColumn"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userNameColumn"));
        onTerminalColumn.setCellValueFactory(new PropertyValueFactory<>("onTerminalColumn"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTimeColumn"));

        table.getColumns().addAll(sourceColumn, eventColumn, identifierColumn, usernameColumn, onTerminalColumn, timeColumn);
        table.prefHeightProperty().bind(root.heightProperty().subtract(toolbar.heightProperty()));
        table.setItems(controller.getDataList());
        tableFilter = TableFilter.forTableView(table).apply();

        sourceColumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        eventColumn.prefWidthProperty().bind(table.widthProperty().divide(2).subtract(5));
        identifierColumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        usernameColumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        onTerminalColumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        timeColumn.prefWidthProperty().bind(table.widthProperty().divide(10));

        table.setRowFactory((TableView<LogsModel.TableObject> param) -> new TableRow<LogsModel.TableObject>() {
            String style;

            @Override
            protected void updateItem(LogsModel.TableObject item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || item.getSourceColumn() == null) {
                    style = ("");
                } else if (item.getLogIdentifierColumn().equals(LogIdentifier.System)) {
                    style = ("-fx-background-color: black; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
                } else if (item.getLogIdentifierColumn().equals(LogIdentifier.Error)) {
                    style = ("-fx-background-color: Red; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
                } else if (item.getLogIdentifierColumn().equals(LogIdentifier.Warning)) {
                    style = ("-fx-background-color: DarkOrange; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
                } else if (item.getLogIdentifierColumn().equals(LogIdentifier.Info)) {
                    style = ("-fx-background-color: blue; -fx-dark-text-color: white;-fx-mid-text-color: white;");
                } else {
                    style = ("");
                }
            }

            @Override
            public void updateSelected(boolean selected) {
                if (selected) {
                    setStyle("-fx-background-color: DARKCYAN; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
                } else {
                    setStyle(style);
                }
            }
        });

        toolbar.getItems().add(export_to_txt_file);

        root.getChildren().addAll(toolbar, table);
        root.setPadding(new Insets(10));
        root.setSpacing(10);
    }

    private void actionHandling() {
        export_to_txt_file.setOnMouseClicked(a -> this.export());
    }

    public synchronized void update() {
        ReadOnlyBooleanProperty update = controller.update();
        table.cursorProperty().bind(Bindings.when(update).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));
    }

    public synchronized void export() {
        ReadOnlyBooleanProperty update = controller.onExportToTXT(tableFilter.getFilteredList(), initialStage.getInitialStage());
        table.cursorProperty().bind(Bindings.when(update).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));
    }


    public Node getRoot() {
        return root;
    }


}
