package soulintec.com.tmsclient.Graphics.Windows.LogsWindow;


import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Services.LogsService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@Component
public class LogManagerWindow implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private Stage mainWindow = null;
    private final VBox root = new VBox();
    private final ToolBar toolbar = new ToolBar();

    private TableFilter<LogDTO> tableFilter;
    private final ObservableList<LogDTO> list = FXCollections.observableArrayList();
    private final TableView<LogDTO> table = new TableView<>();
    private final TableColumn<LogDTO, String> SourceColumn = new TableColumn<>("Source");
    private final TableColumn<LogDTO, String> EventColumn = new TableColumn<>("Event");
    private final TableColumn<LogDTO, LogIdentifier> IdentifierColumn = new TableColumn<>("Identifier");
    private final TableColumn<LogDTO, String> UsernameColumn = new TableColumn<>("Username");
    private final TableColumn<LogDTO, String> GroupColumn = new TableColumn<>("Group");
    private final TableColumn<LogDTO, String> TimeColumn = new TableColumn<>("Time");

    private final Button export_to_txt_file = new Button("Export to csv file");

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);

    @Autowired
    private LogsService loggingService;

    @Autowired(required = false)
    private Executor executor;

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainWindow = event.getStage();

        graphicsBuilder();
        actionHandling();
    }

    private void graphicsBuilder() {
        //table configuration
        SourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        EventColumn.setCellValueFactory(new PropertyValueFactory<>("event"));
        IdentifierColumn.setCellValueFactory(new PropertyValueFactory<>("logIdentifier"));
        UsernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        GroupColumn.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        TimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        table.getColumns().addAll(SourceColumn, EventColumn, IdentifierColumn, UsernameColumn, GroupColumn, TimeColumn);
        table.prefHeightProperty().bind(root.heightProperty().subtract(toolbar.heightProperty()));
        table.setItems(list);
        tableFilter = TableFilter.forTableView(table).apply();

        SourceColumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        EventColumn.prefWidthProperty().bind(table.widthProperty().divide(2).subtract(5));
        IdentifierColumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        UsernameColumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        GroupColumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        TimeColumn.prefWidthProperty().bind(table.widthProperty().divide(10));

        table.setRowFactory((TableView<LogDTO> param) -> new TableRow<LogDTO>() {
            String style;

            @Override
            protected void updateItem(LogDTO item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || item.getSource() == null) {
                    style = ("");
                } else if (item.getLogIdentifier().equals(LogIdentifier.System)) {
                    style = ("-fx-background-color: black; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
                } else if (item.getLogIdentifier().equals(LogIdentifier.Error)) {
                    style = ("-fx-background-color: Red; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
                } else if (item.getLogIdentifier().equals(LogIdentifier.Warning)) {
                    style = ("-fx-background-color: DarkOrange; -fx-dark-text-color: white;-fx-mid-text-color: white;-fx-light-text-color: white;");
                } else if (item.getLogIdentifier().equals(LogIdentifier.Info)) {
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
        export_to_txt_file.setOnMouseClicked(this::onExportToTXT);
    }

    private void onExportToTXT(MouseEvent mouseEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export log to csv");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csv format", "*.csv"));
        chooser.setInitialFileName("log.csv");
        File file = chooser.showSaveDialog(mainWindow);
        this.onExportCSV(file, tableFilter.getFilteredList());
    }

    public void onExportCSV(File file, List<LogDTO> list) {
        Task<Boolean> updateTask = exportTask(file, list);
        table.cursorProperty().bind(Bindings.when(updateTask.runningProperty()).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));
        executor.execute(updateTask);
    }

    public Task<Boolean> exportTask(File file, List<LogDTO> list) {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                try (FileWriter fW = new FileWriter(file)) {
                    try (BufferedWriter bR = new BufferedWriter(fW)) {
                        //Fill header
                        StringBuilder headerLine = new StringBuilder();
                        for (int i = 0; i < 6; i++) {
                            headerLine.append(InHeader.values()[i]).append(",");
                        }
                        headerLine.append("\n");
                        bR.append(headerLine.toString());

                        //Fill data
                        list.forEach(logDTO -> {

                                    String line = "";
                                    line += logDTO.getId() + ",";
                                    line += logDTO.getLogIdentifier() + ",";
                                    line += logDTO.getSource() + ",";
                                    line += logDTO.getEvent() + ",";
                                    line += logDTO.getUserName() + ",";
                                    line += logDTO.getDateTime();

                                    line += "\n";

                                    try {
                                        bR.append(line);
                                    } catch (IOException ignored) {
                                    }
                                }
                        );
                    }
                } catch (Exception ignored) {
                }
                return null;
            }
        };
    }


    public void update() {
        Task<Boolean> updateTask = updateTask();
        table.cursorProperty().bind(Bindings.when(updateTask.runningProperty()).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));
        executor.execute(updateTask);
    }

    private synchronized Task<Boolean> updateTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                final ArrayList<LogDTO> logDTOS = Lists.newArrayList(loggingService.findAll());
                Platform.runLater(() -> {
                    list.clear();
                    list.addAll(logDTOS);
                });
                return false;
            }
        };
    }

    public Node getRoot() {
        return root;
    }


    private enum InHeader {
        LogId,
        LogIdentifier,
        Source,
        Event,
        Username,
        Date
    }
}
