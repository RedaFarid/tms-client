package soulintec.com.tmsclient.Graphics.Windows.LogsWindow;

import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Graphics.Windows.TanksWindow.TanksModel;
import soulintec.com.tmsclient.Services.LogsService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class LogController {
    private final LogsModel model = new LogsModel();

    private final ObservableList<LogsModel.TableObject> tableList = FXCollections.observableArrayList();

    @Autowired(required = false)
    private Executor executor;

    @Autowired
    private LogsService logsService;

    public LogsModel getModel() {
        return model;
    }

    public ObservableList<LogsModel.TableObject> getDataList() {
        return tableList;
    }

    public ReadOnlyBooleanProperty update() {
        Task<String> updateTask = updateTask();
        ReadOnlyBooleanProperty readOnlyBooleanProperty = updateTask.runningProperty();
        executor.execute(updateTask);
        return readOnlyBooleanProperty;
    }

    private synchronized Task<String> updateTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                List<LogDTO> dataBaseList = Lists.newArrayList(logsService.findAll());

                Platform.runLater(() -> {
                    getDataList().removeAll(
                            dataBaseList
                                    .stream()
                                    .map(LogsModel.TableObject::createFromLogDTO)
                                    .filter(item -> !getDataList().contains(item))
                                    .collect(() -> getDataList(), ObservableList::add, ObservableList::addAll)
                                    .stream()
                                    .filter(tableListItem -> dataBaseList.stream().map(LogsModel.TableObject::createFromLogDTO).noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
                                    .collect(Collectors.toList()));
                });
                return null;
            }
        };
    }

    public ReadOnlyBooleanProperty onExportToTXT(List<LogsModel.TableObject> list, Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export log to csv");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csv format", "*.csv"));
        chooser.setInitialFileName("log.csv");
        File file = chooser.showSaveDialog(stage);
        return this.onExportCSV(file, list);
    }

    public ReadOnlyBooleanProperty onExportCSV(File file, List<LogsModel.TableObject> list) {
        Task<Boolean> updateTask = exportTask(file, list);
        ReadOnlyBooleanProperty readOnlyBooleanProperty = updateTask.runningProperty();
        executor.execute(updateTask);
        return readOnlyBooleanProperty;
    }

    public synchronized Task<Boolean> exportTask(File file, List<LogsModel.TableObject> list) {
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
                                    line += logDTO.getLogIdColumnColumn() + ",";
                                    line += logDTO.getLogIdentifierColumn() + ",";
                                    line += logDTO.getSourceColumn() + ",";
                                    line += logDTO.getEventColumn() + ",";
                                    line += logDTO.getUserNameColumn() + ",";
                                    line += logDTO.getOnTerminalColumn() + ",";
                                    line += logDTO.getDateTimeColumn();

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


    private enum InHeader {
        LogId,
        LogIdentifier,
        Source,
        Event,
        Username,
        OnTerminal,
        Date
    }


}
