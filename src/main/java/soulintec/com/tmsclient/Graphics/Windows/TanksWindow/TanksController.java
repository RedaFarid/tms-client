package soulintec.com.tmsclient.Graphics.Windows.TanksWindow;


import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.MaterialDTO;
import soulintec.com.tmsclient.Entities.StationDTO;
import soulintec.com.tmsclient.Entities.TankDTO;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow.MaterialsModel;
import soulintec.com.tmsclient.Graphics.Windows.StationsWindow.StationsModel;
import soulintec.com.tmsclient.Reporting.ReportsDTO.DTO;
import soulintec.com.tmsclient.Reporting.ReportsDTO.Tanks;
import soulintec.com.tmsclient.Reporting.ReportsDetails.ReportDetailsFactory;
import soulintec.com.tmsclient.Services.GeneralServices.LoggingService.LoginService;
import soulintec.com.tmsclient.Services.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class TanksController {
    private final TanksModel model = new TanksModel();
    private final MaterialsModel materialsModel = new MaterialsModel();
    private final StationsModel stationModel = new StationsModel();

    private final ObservableList<TanksModel.TableObject> tableList = FXCollections.observableArrayList();
    private final ObservableList<MaterialsModel.TableObject> productContextList = FXCollections.observableArrayList();
    private final ObservableList<StationsModel.TableObject> stationContextList = FXCollections.observableArrayList();

    @Autowired(required = false)
    private Executor executor;

    @Autowired
    private TanksService tanksService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private StationService stationService;
    @Autowired
    private LogsService logsService;
    @Autowired
    private ReportDetailsFactory reportDetailsFactory;

    private final ObservableList<StationDTO> stationDTOS = FXCollections.observableArrayList();
    private final ObservableList<MaterialDTO> materialDTOS = FXCollections.observableArrayList();

    private void updateDTOS() {
        stationDTOS.removeAll();
        stationDTOS.addAll(stationService.findAll());

        materialDTOS.removeAll();
        materialDTOS.addAll(materialService.findAll());
    }

    public TanksModel getModel() {
        return model;
    }

    public MaterialsModel getMaterialsModel() {
        return materialsModel;
    }

    public StationsModel getStationsModel() {
        return stationModel;
    }

    public ObservableList<TanksModel.TableObject> getDataList() {
        return tableList;
    }

    public void onTableSelection(ObservableList<? extends TanksModel.TableObject> list) {
        if (list.size() > 0) {
            TanksModel.TableObject tableObject = list.get(0);

            tanksService.findById(tableObject.getTankIdColumn()).ifPresentOrElse(tankDTO -> {
                model.setTankId(tankDTO.getId());
                model.setName(tankDTO.getName());
                model.setStation(tankDTO.getStation());
                model.setCapacity(tankDTO.getCapacity());
                model.setQty(tankDTO.getQty());
                model.setCalculatedQty(tankDTO.getCalculatedQty());
                model.setDateOfQtySet(String.valueOf(tankDTO.getDateOfQtySet()));
                model.setUserOfQtySet(tankDTO.getUserOfQtySet());
                model.setMaterialID(tankDTO.getMaterialID());
                model.setCreatedBy(tableObject.getCreatedByColumn());
                model.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                model.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                model.setOnTerminal(tableObject.getOnTerminalColumn());
                model.setModifiedBy(tableObject.getModifiedByColumn());
                materialService.findById(tankDTO.getMaterialID()).ifPresentOrElse(materialDTO -> {
                    materialsModel.setName(materialDTO.getName());
                    materialsModel.setDescription(materialDTO.getDescription());
                }, () -> {
                    materialsModel.setName("");
                    materialsModel.setDescription("");
                });

                stationService.findById(tankDTO.getStation()).ifPresentOrElse(stationDTO -> {
                    stationModel.setName(stationDTO.getStationName());
                    stationModel.setLocation(stationDTO.getLocation());
                    stationModel.setComputerName(stationDTO.getComputerName());
                }, () -> {
                    stationModel.setName("");
                    stationModel.setLocation("");
                    stationModel.setComputerName("");
                });

            }, () -> {
//                logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Error getting tanks data"));
                MainWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected tank");
            });
        }
    }

    @Async
    public void onInsert(MouseEvent mouseEvent) {

        String name = model.getName();
        Long station = model.getStation();
        Double capacity = model.getCapacity();
        long materialID = model.getMaterialID();

        if (StringUtils.isBlank(name)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (station == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please enter station");
            return;
        }
        if (capacity <= 0) {
            MainWindow.showWarningWindow("Missing Data", "Please enter capacity");
            return;
        }

        try {

            if (!tanksService.findByNameAndStation(model.getName(), model.getStation()).isPresent()) {
                TankDTO tank = new TankDTO();
                tank.setName(name);
                tank.setStation(station);
                tank.setCapacity(capacity);
                tank.setDateOfQtySet(LocalDateTime.now());

                if (materialID == 0) {
                    tank.setMaterialID(null);
                } else {
                    tank.setMaterialID(materialID);

                }

                String save = tanksService.save(tank);

                if (save.equals("saved")) {
                    logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Inserting new tank : " + name + " in station : " + stationModel.getName()));
                    MainWindow.showInformationWindow("Info", save);
                    update();

                } else {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                    MainWindow.showErrorWindow("Error inserting data", save);
                }
                resetModel();
            } else {
                MainWindow.showErrorWindow("Error inserting data", "Tank already exist , please check entered data");
            }


        } catch (Exception e) {
            MainWindow.showErrorWindowForException("Error inserting data", e);
        }
    }

    @Async
    public void onUpdate(MouseEvent mouseEvent) {

        String name = model.getName();
        Long station = model.getStation();
        Double capacity = model.getCapacity();
        long tankId = model.getTankId();
        long materialID = model.getMaterialID();

        if (tankId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select tank");
            return;
        }

        if (StringUtils.isBlank(name)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (station == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please enter station");
            return;
        }
        if (capacity <= 0) {
            MainWindow.showWarningWindow("Missing Data", "Please enter capacity");
            return;
        }

        try {
            if (tanksService.findById(tankId).isPresent()) {
                TankDTO tank = new TankDTO();
                tank.setId(tankId);
                tank.setName(name);
                tank.setStation(station);
                tank.setCapacity(capacity);
                tank.setDateOfQtySet((LocalDateTime.parse(model.getDateOfQtySet())));

                if (materialID == 0) {
                    tank.setMaterialID(null);
                } else {
                    tank.setMaterialID(materialID);
                }

                String save = tanksService.save(tank);

                if (save.equals("saved")) {
                    logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Updating data for tank : " + model.getName() + " in station : " + stationModel.getName()));
                    MainWindow.showInformationWindow("Info", save);
                    update();

                } else {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                    MainWindow.showErrorWindow("Error updating data", save);
                }
                resetModel();
            } else {
                MainWindow.showErrorWindow("Error inserting data", "tank doesn't exist , please check entered data");
            }


        } catch (Exception e) {
            MainWindow.showErrorWindowForException("Error updating data", e);
        }
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {

        long id = model.getTankId();
        if (transactionService.findAll().stream().filter(i -> i.getTank() == id).count() != 0) {
            logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Tank :  " + model.getName() + " can't be deleted because there are transactions relate to it "));
            MainWindow.showErrorWindow("Error deleting record", "Tank can't be deleted because there are transactions relate to it \n please delete related transactions first");
        } else {
            String deletedById = tanksService.deleteById(id);
            if (deletedById.equals("deleted")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Deleting tank : " + model.getName() + " in station : " + stationModel.getName()));
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
            protected String call() {
                List<TankDTO> dataBaseList = Lists.newArrayList(tanksService.findAll());

                Platform.runLater(() -> {
                    getDataList().removeAll(
                            dataBaseList
                                    .stream()
                                    .map(TanksModel.TableObject::createFromTankDTO)
                                    .filter(item -> !getDataList().contains(item))
                                    .collect(() -> getDataList(), ObservableList::add, ObservableList::addAll)
                                    .stream()
                                    .filter(tableListItem -> dataBaseList.stream().map(TanksModel.TableObject::createFromTankDTO).noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
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

    public ObservableList<MaterialsModel.TableObject> getProductContextList() {
        productContextList.clear();
        productContextList.addAll(materialService.findAll().stream().map(MaterialsModel.TableObject::createFromMaterialDTO).collect(Collectors.toList()));
        return productContextList;
    }

    public ObservableList<StationsModel.TableObject> getStationContextList() {
        stationContextList.clear();
        stationContextList.addAll(stationService.findAll().stream().map(StationsModel.TableObject::createFromStationDTO).collect(Collectors.toList()));
        return stationContextList;
    }

    public void resetModel() {
        Platform.runLater(() -> {
            try {
                model.setTankId(0);
                model.setName("");
                model.setStation(0);
                model.setCapacity(0.0);
                model.setMaterialID(0);
                model.setQty(0.0);
                model.setCalculatedQty(0.0);
                model.setDateOfQtySet(null);
                model.setUserOfQtySet("");
                model.setCreatedBy((""));
                model.setCreationDate("");
                model.setModifyDate("");
                model.setModifiedBy("");
                model.setOnTerminal("");

                materialsModel.setName("");
                materialsModel.setDescription("");

                stationModel.setName("");
                stationModel.setLocation("");
                stationModel.setComputerName("");

            } catch (Exception e) {
                log.fatal(e);
            }
        });
    }

    public void detailedUpdates(List<TankDTO> dataBaseList) {
        if (getDataList() != null) {

            final Map<Long, TankDTO> tankDTOMap = dataBaseList.stream().collect(Collectors.toMap(TankDTO::getId, Function.identity()));
            for (TanksModel.TableObject item : getDataList()) {
                try {
                    final TankDTO tank = tankDTOMap.get(item.getTankIdColumn());
                    if (tank != null) {
                        item.setNameColumn(tank.getName());
                        item.setStationColumn(tank.getStation());
                        item.setCapacityColumn(tank.getCapacity());
                        item.setQtyColumn(tank.getQty());
                        item.setDateOfQtySetColumn(tank.getDateOfQtySet());
                        item.setUserOfQtySetColumn(tank.getUserOfQtySet());
                        item.setMaterialIDColumn(tank.getMaterialID());
                        item.setCreationDateColumn(tank.getCreationDate());
                        item.setModifyDateColumn(tank.getModificationDate());
                        item.setCreatedByColumn(tank.getCreatedBy());
                        item.setModifiedByColumn(tank.getLastModifiedBy());
                        item.setOnTerminalColumn(tank.getOnTerminal());
                        item.setCalculatedQtyColumn(tank.getCalculatedQty());
                    }
                } catch (Exception e) {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), e.getMessage()));
                    log.fatal(e);
                }
            }
        }
    }

    public void setProductData(MaterialsModel.TableObject selected) {
        if (selected != null) {
            materialsModel.setMaterialId(selected.getMaterialIdColumn());
            materialsModel.setName(selected.getNameColumn());
            materialsModel.setDescription(selected.getDescriptionColumn());
        }
    }


    public void setStationData(StationsModel.TableObject selected) {
        if (selected != null) {
            stationModel.setName(selected.getNameColumn());
            stationModel.setLocation(selected.getLocationColumn());
            stationModel.setComputerName(selected.getComputerNameColumn());
            stationModel.setStationId(selected.getStationIdColumn());
        }
    }

    public void setQty(double qty) {
        long tankId = model.getTankId();

        if (tankId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select tank");
            return;
        }
        try {
            tanksService.findById(tankId).ifPresentOrElse((item -> {
                item.setUserOfQtySet(LoginService.getObservedUsername());
                item.setQty(qty);
                item.setDateOfQtySet(LocalDateTime.now());
                String save = tanksService.save(item);
                if (save.equals("saved")) {
                    logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Updating quantity for tank : " + model.getName() + " in station : " + stationModel.getName()));
                    MainWindow.showInformationWindow("Info", save);
                    update();

                } else {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                    MainWindow.showErrorWindow("Error updating data", save);
                }
                resetModel();
            }), () -> {
                MainWindow.showWarningWindow("No selected tank", "Please select tank");
            });

        } catch (Exception e) {
            MainWindow.showErrorWindowForException("Error updating data", e);
        }
    }

    @Async
    public CompletableFuture<Pane> onReport(List<TanksModel.TableObject> list) {
        updateDTOS();
        try {
            List<DTO> collect = list.stream().map(listItem -> new Tanks(
                            String.valueOf(listItem.getTankIdColumn()),
                            String.valueOf(listItem.getNameColumn()),
                            getStationName(listItem.getStationColumn()),
                            getStationLocation(listItem.getStationColumn()),
                            getMaterialName(listItem.getMaterialIDColumn()),
                            String.valueOf(listItem.getCapacityColumn()),
                            String.valueOf(listItem.getQtyColumn()),
                            String.valueOf(listItem.getCalculatedQtyColumn()),
                            String.valueOf(listItem.getDateOfQtySetColumn()),
                            String.valueOf(listItem.getUserOfQtySetColumn())
                    )
            ).collect(Collectors.toList());

            Pane reportPane = reportDetailsFactory.getReportDetailsPaneFor("Tanks", collect);

            return CompletableFuture.completedFuture(reportPane);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    private String getStationName(Long id) {
        AtomicReference<String> stationName = new AtomicReference<>("");
        stationDTOS.stream().filter(stationDTO -> stationDTO.getId().equals(id)).findFirst().map(StationDTO::getStationName).ifPresentOrElse(stationName::set
                , () -> stationName.set(""));
        return stationName.get();
    }

    private String getStationLocation(Long id) {
        AtomicReference<String> stationLocation = new AtomicReference<>("");
        stationDTOS.stream().filter(stationDTO -> stationDTO.getId().equals(id)).findFirst().map(StationDTO::getLocation).ifPresentOrElse(stationLocation::set
                , () -> stationLocation.set(""));
        return stationLocation.get();
    }

    private String getMaterialName(Long id) {
        AtomicReference<String> materialName = new AtomicReference<>("");
        materialDTOS.stream().filter(materialDTO -> materialDTO.getId().equals(id)).findFirst().map(MaterialDTO::getName).ifPresentOrElse(materialName::set
                , () -> materialName.set(""));
        return materialName.get();
    }


    @Override
    public String toString() {
        return "Tanks";
    }


}
