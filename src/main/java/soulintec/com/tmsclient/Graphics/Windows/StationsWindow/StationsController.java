package soulintec.com.tmsclient.Graphics.Windows.StationsWindow;


import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.StationDTO;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Services.LogsService;
import soulintec.com.tmsclient.Services.StationService;
import soulintec.com.tmsclient.Services.TanksService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class StationsController {
    private final StationsModel model = new StationsModel();

    private final ObservableList<StationsModel.TableObject> tableList = FXCollections.observableArrayList();
//    private final ObservableList<MaterialsModel.TableObject> productContextList = FXCollections.observableArrayList();

    @Autowired(required = false)
    private Executor executor;

    @Autowired
    private StationService stationService;
    @Autowired
    private TanksService tanksService;
    @Autowired
    private LogsService logsService;

    public StationsModel getModel() {
        return model;
    }

    public ObservableList<StationsModel.TableObject> getDataList() {
        return tableList;
    }

    public void onTableSelection(ObservableList<? extends StationsModel.TableObject> list) {
        if (list.size() > 0) {
            StationsModel.TableObject tableObject = list.get(0);

            stationService.findById(tableObject.getStationIdColumn()).ifPresentOrElse(stationDTO -> {
                model.setStationId(stationDTO.getId());
                model.setName(stationDTO.getStationName());
                model.setLocation(stationDTO.getLocation());
                model.setComputerName(stationDTO.getComputerName());
                model.setComment(stationDTO.getComment());
                model.setCreatedBy(tableObject.getCreatedByColumn());
                model.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                model.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                model.setOnTerminal(tableObject.getOnTerminalColumn());


            }, () -> {
//                logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Error getting station data"));
                MainWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected station");
            });
        }
    }

    @Async
    public void onInsert(MouseEvent mouseEvent) {

        String name = model.getName();
        String location = model.getLocation();
        String computerName = model.getComputerName();


        if (StringUtils.isBlank(name)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(location)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter location");
            return;
        }
//        if (Objects.isNull(computerName)) {
//            MainWindow.showWarningWindow("Missing Data", "Please select computer");
//            return;
//        }

        try {

            if (!stationService.findByStationName(model.getName()).isPresent()) {
                StationDTO stationDTO = new StationDTO();
                stationDTO.setStationName(name);
                stationDTO.setLocation(location);
                stationDTO.setComputerName(computerName);

                String save = stationService.save(stationDTO);
                if (save.equals("saved")) {
                    logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Inserting new station : " + name));
                    MainWindow.showInformationWindow("Info", save);
                    update();

                } else {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                    MainWindow.showErrorWindow("Error inserting data", save);
                }
                resetModel();
            } else {
                MainWindow.showErrorWindow("Error inserting data", "Station already exist , please check entered data");
            }


        } catch (Exception e) {
            MainWindow.showErrorWindowForException("Error inserting data", e);
        }
    }

    @Async
    public void onUpdate(MouseEvent mouseEvent) {
        String name = model.getName();
        String location = model.getLocation();
        String computerName = model.getComputerName();
        long id = model.getStationId();

        if (id == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select station");
            return;
        }

        if (StringUtils.isBlank(name)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(location)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter location");
            return;
        }
//        if (Objects.isNull(computerName)) {
//            MainWindow.showWarningWindow("Missing Data", "Please select computer");
//            return;
//        }

        try {

            if (stationService.findById(id).isPresent()) {
                StationDTO stationDTO = new StationDTO();
                stationDTO.setId(id);
                stationDTO.setStationName(name);
                stationDTO.setLocation(location);
                stationDTO.setComputerName(computerName);

                String save = stationService.save(stationDTO);
                if (save.equals("saved")) {
                    logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Updating data for station : " + name));
                    MainWindow.showInformationWindow("Info", save);
                    update();

                } else {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                    MainWindow.showErrorWindow("Error updating data", save);
                }
                resetModel();
            } else {

                MainWindow.showErrorWindow("Error updating data", "Station doesn't exist , please check selected data");
            }


        } catch (Exception e) {
            MainWindow.showErrorWindowForException("Error updating data", e);
        }
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {
        long id = model.getStationId();

        if (tanksService.findByNameAndStation("%", id).isPresent()) {
            logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Station :  " + model.getName() + " can't be deleted because there are tanks relate to it "));
            MainWindow.showErrorWindow("Error deleting record", "Station can't be deleted because there are tanks relate to it \n please delete related tanks first");
        } else {
            String deletedById = stationService.deleteById(id);
            if (deletedById.equals("deleted")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Deleting station : " + model.getName()));
                MainWindow.showInformationWindow("Info", deletedById);
                update();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), deletedById));
                MainWindow.showErrorWindow("Error deleting record", deletedById);
            }
            resetModel();
        }
    }

    public synchronized Task<String> updateTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                List<StationDTO> dataBaseList = Lists.newArrayList(stationService.findAll());

                Platform.runLater(() -> {
                    getDataList().removeAll(
                            dataBaseList
                                    .stream()
                                    .map(StationsModel.TableObject::createFromStationDTO)
                                    .filter(item -> !getDataList().contains(item))
                                    .collect(() -> getDataList(), ObservableList::add, ObservableList::addAll)
                                    .stream()
                                    .filter(tableListItem -> dataBaseList.stream().map(StationsModel.TableObject::createFromStationDTO).noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
                                    .collect(Collectors.toList()));
                });
                detailedUpdates(dataBaseList);
                return null;
            }
        };


    }

    public ReadOnlyBooleanProperty update() {
        final Task<String> updateTask = updateTask();
        ReadOnlyBooleanProperty readOnlyBooleanProperty = updateTask.runningProperty();
        executor.execute(updateTask);
        return readOnlyBooleanProperty;
    }

    public void resetModel() {
        Platform.runLater(() -> {
            try {
                model.setStationId(0);
                model.setName("");
                model.setLocation("");
                model.setComment("");
                model.setComputerName("");
                model.setCreatedBy((""));
                model.setCreationDate("");
                model.setModifyDate("");
                model.setOnTerminal("");

            } catch (Exception e) {
                log.fatal(e);
            }
        });
    }

    public void detailedUpdates(List<StationDTO> dataBaseList) {
        if (getDataList() != null) {

            final Map<Long, StationDTO> stationDTOMap = dataBaseList.stream().collect(Collectors.toMap(StationDTO::getId, Function.identity()));
            for (StationsModel.TableObject item : getDataList()) {
                try {
                    final StationDTO stationDTO = stationDTOMap.get(item.getStationIdColumn());
                    if (stationDTO != null) {
                        item.setNameColumn(stationDTO.getStationName());
                        item.setLocationColumn(stationDTO.getLocation());
                        item.setCommentColumn(stationDTO.getComment());
                        item.setComputerNameColumn(stationDTO.getComputerName());
                        item.setCreationDateColumn(stationDTO.getCreationDate());
                        item.setModifyDateColumn(stationDTO.getModifyDate());
                        item.setCreatedByColumn(stationDTO.getCreatedBy());
                        item.setOnTerminalColumn(stationDTO.getOnTerminal());
                    }

                } catch (Exception e) {
                    logsService.save(new LogDTO(LogIdentifier.Error , toString() , e.getMessage()));
                    log.fatal(e);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Stations";
    }
}
