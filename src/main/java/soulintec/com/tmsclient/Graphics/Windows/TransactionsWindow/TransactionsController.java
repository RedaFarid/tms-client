package soulintec.com.tmsclient.Graphics.Windows.TransactionsWindow;


import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.OperationType;
import soulintec.com.tmsclient.Entities.TransactionDTO;
import soulintec.com.tmsclient.Graphics.Windows.DriversWindow.DriversModel;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow.MaterialsModel;
import soulintec.com.tmsclient.Graphics.Windows.StationsWindow.StationsModel;
import soulintec.com.tmsclient.Graphics.Windows.TanksWindow.TanksModel;
import soulintec.com.tmsclient.Graphics.Windows.TruckWindow.TruckContainerModel;
import soulintec.com.tmsclient.Graphics.Windows.TruckWindow.TruckTrailerModel;
import soulintec.com.tmsclient.Services.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class TransactionsController {
    private final TransactionsModel model = new TransactionsModel();

    private final MaterialsModel materialsModel = new MaterialsModel();
    private final TanksModel tanksModel = new TanksModel();
    private final StationsModel stationModel = new StationsModel();
    private final DriversModel driversModel = new DriversModel();
    private final TruckTrailerModel truckTrailersModel = new TruckTrailerModel();
    private final TruckContainerModel truckContainersModel = new TruckContainerModel();

    private final ObservableList<TransactionsModel.TableObject> tableList = FXCollections.observableArrayList();

    private final ObservableList<MaterialsModel.TableObject> materialContextList = FXCollections.observableArrayList();
    private final ObservableList<TanksModel.TableObject> tanksContextList = FXCollections.observableArrayList();
    private final ObservableList<StationsModel.TableObject> stationContextList = FXCollections.observableArrayList();
    private final ObservableList<DriversModel.TableObject> driversContextList = FXCollections.observableArrayList();
    private final ObservableList<TruckTrailerModel.TableObject> truckTrailerContextList = FXCollections.observableArrayList();
    private final ObservableList<TruckContainerModel.TableObject> truckContainerContextList = FXCollections.observableArrayList();

    @Autowired(required = false)
    private Executor executor;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MaterialService materialService;
    @Autowired
    private TanksService tanksService;
    @Autowired
    private StationService stationService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private TruckTrailerService truckTrailerService;
    @Autowired
    private TruckContainerService truckContainerService;

    @Autowired
    private LogsService logsService;

    public TransactionsModel getModel() {
        return model;
    }

    public StationsModel getStationModel() {
        return stationModel;
    }

    public DriversModel getDriversModel() {
        return driversModel;
    }

    public TruckTrailerModel getTruckTrailersModel() {
        return truckTrailersModel;
    }

    public TruckContainerModel getTruckContainersModel() {
        return truckContainersModel;
    }

    public TanksModel getTanksModel() {
        return tanksModel;
    }

    public MaterialsModel getMaterialsModel() {
        return materialsModel;
    }

    public ObservableList<TransactionsModel.TableObject> getDataList() {
        return tableList;
    }

    public void onTableSelection(ObservableList<? extends TransactionsModel.TableObject> list) {
        if (list.size() > 0) {
            TransactionsModel.TableObject tableObject = list.get(0);

            transactionService.findById(tableObject.getTransactionIdColumn()).ifPresentOrElse(transactionDTO -> {
                model.setTransactionId(transactionDTO.getId());
                model.setMaterial(transactionDTO.getMaterial());
                model.setStation(transactionDTO.getStation());
                model.setTank(transactionDTO.getTank());
                model.setDriver(transactionDTO.getDriver());
                model.setTruckContainer(transactionDTO.getTruckContainer());
                model.setTruckTrailer(transactionDTO.getTruckTrailer());
                model.setOperationType(transactionDTO.getOperationType());
                model.setQty(transactionDTO.getQty());
                model.setDateTime(transactionDTO.getDateTime());
                model.setCreatedBy(tableObject.getCreatedByColumn());
                model.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                model.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                model.setOnTerminal(tableObject.getOnTerminalColumn());

                materialService.findById(transactionDTO.getMaterial()).ifPresentOrElse(materialDTO -> {
                    materialsModel.setMaterialId(materialDTO.getId());
                    materialsModel.setName(materialDTO.getName());
                    materialsModel.setDescription(materialDTO.getDescription());
                }, () -> {
                    materialsModel.setMaterialId(0);
                    materialsModel.setName("");
                    materialsModel.setDescription("");
                });

                stationService.findById(transactionDTO.getStation()).ifPresentOrElse(stationDTO -> {
                    stationModel.setStationId(stationDTO.getId());
                    stationModel.setName(stationDTO.getStationName());
                    stationModel.setLocation(stationDTO.getLocation());
                    stationModel.setComputerName(stationDTO.getComputerName());
                }, () -> {
                    stationModel.setStationId(0);
                    stationModel.setName("");
                    stationModel.setLocation("");
                    stationModel.setComputerName("");
                });

                tanksService.findById(transactionDTO.getTank()).ifPresentOrElse(tankDTO -> {
                    tanksModel.setTankId(tankDTO.getId());
                    tanksModel.setName(tankDTO.getName());
                    tanksModel.setQty(tankDTO.getQty());
                    tanksModel.setCalculatedQty(tankDTO.getCalculatedQty());
                }, () -> {
                    tanksModel.setTankId(0);
                    tanksModel.setName("");
                    tanksModel.setQty(0);
                    tanksModel.setCalculatedQty(0);
                });

                driverService.findById(transactionDTO.getDriver()).ifPresentOrElse(driverDTO -> {
                    driversModel.setDriverId(driverDTO.getId());
                    driversModel.setName(driverDTO.getName());
                    driversModel.setLicenseNumber(driverDTO.getLicenseNumber());
                }, () -> {
                    driversModel.setDriverId(0);
                    driversModel.setName("");
                    driversModel.setLicenseNumber("");
                });

                truckTrailerService.findById(transactionDTO.getTruckTrailer()).ifPresentOrElse(truckTrailerDTO -> {
                    truckTrailersModel.setTruckTrailerId(truckTrailerDTO.getId());
                    truckTrailersModel.setTrailerNumber(truckTrailerDTO.getTrailerNumber());
                    truckTrailersModel.setLicenseNumber(truckTrailerDTO.getLicenceNumber());
                }, () -> {
                    truckTrailersModel.setTruckTrailerId(0);
                    truckTrailersModel.setTrailerNumber("");
                    truckTrailersModel.setLicenseNumber("");
                });

                truckContainerService.findById(transactionDTO.getTruckContainer()).ifPresentOrElse(truckContainerDTO -> {
                    truckContainersModel.setTruckContainerId(truckContainerDTO.getId());
                    truckContainersModel.setContainerNumber(truckContainerDTO.getContainerNumber());
                    truckContainersModel.setLicenseNumber(truckContainerDTO.getLicenceNumber());
                }, () -> {
                    truckContainersModel.setTruckContainerId(0);
                    truckContainersModel.setContainerNumber("");
                    truckContainersModel.setLicenseNumber("");
                });

            }, () -> {
//                logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Error getting tanks data"));
                MainWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected transaction");
            });
        }
    }

    @Async
    public void onInsert(MouseEvent mouseEvent) {

        long materialID = model.getMaterial();
        long stationId = model.getStation();
        long tankId = model.getTank();
        long driverId = model.getDriver();
        long truckTrailerId = model.getTruckTrailer();
        long truckContainerId = model.getTruckContainer();
        OperationType operationType = model.getOperationType();
        Double qty = model.getQty();

        if (materialID == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select material");
            return;
        }
        if (stationId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select station");
            return;
        }
        if (tankId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select tank");
            return;
        }
        if (driverId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select driver");
            return;
        }
        if (truckTrailerId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select truck trailer");
            return;
        }
        if (truckContainerId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select truck container");
            return;
        }
        if (qty <= 0) {
            MainWindow.showWarningWindow("Missing Data", "Please enter quantity");
            return;
        }
        if (Objects.isNull(operationType)) {
            MainWindow.showWarningWindow("Missing Data", "Please select operation type");
            return;
        }

        try {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setMaterial(materialID);
            transactionDTO.setStation(stationId);
            transactionDTO.setTank(tankId);
            transactionDTO.setDriver(driverId);
            transactionDTO.setTruckTrailer(truckTrailerId);
            transactionDTO.setTruckContainer(truckContainerId);
            transactionDTO.setQty(qty);
            transactionDTO.setDateTime(model.getDateTime());
            transactionDTO.setOperationType(operationType);

            String save = transactionService.save(transactionDTO);

            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Inserting new transaction "));
                MainWindow.showInformationWindow("Info", save);
                update();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MainWindow.showErrorWindow("Error inserting data", save);
            }
            resetModel();
        } catch (Exception e) {
            MainWindow.showErrorWindowForException("Error inserting data", e);
        }
    }

    @Async
    public void onUpdate(MouseEvent mouseEvent) {
        long id = model.getTransactionId();
        long materialID = model.getMaterial();
        long stationId = model.getStation();
        long tankId = model.getTank();
        long driverId = model.getDriver();
        long truckTrailerId = model.getTruckTrailer();
        long truckContainerId = model.getTruckContainer();
        OperationType operationType = model.getOperationType();
        Double qty = model.getQty();

        if (id == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select transaction");
            return;
        }
        if (materialID == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select material");
            return;
        }
        if (stationId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select station");
            return;
        }
        if (tankId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select tank");
            return;
        }
        if (driverId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select driver");
            return;
        }
        if (truckTrailerId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select truck trailer");
            return;
        }
        if (truckContainerId == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select truck container");
            return;
        }
        if (qty <= 0) {
            MainWindow.showWarningWindow("Missing Data", "Please enter quantity");
            return;
        }
        if (operationType.equals(null)) {
            MainWindow.showWarningWindow("Missing Data", "Please select operation type");
            return;
        }

        try {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setId(id);
            transactionDTO.setMaterial(materialID);
            transactionDTO.setStation(stationId);
            transactionDTO.setTank(tankId);
            transactionDTO.setDriver(driverId);
            transactionDTO.setTruckTrailer(truckTrailerId);
            transactionDTO.setTruckContainer(truckContainerId);
            transactionDTO.setQty(qty);
            transactionDTO.setDateTime(model.getDateTime());
            transactionDTO.setOperationType(operationType);

            String save = transactionService.save(transactionDTO);

            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Updating transaction " + id));
                MainWindow.showInformationWindow("Info", save);
                update();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MainWindow.showErrorWindow("Error Updating data", save);
            }
            resetModel();
        } catch (Exception e) {
            MainWindow.showErrorWindowForException("Error Updating data", e);
        }
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {

        long id = model.getTransactionId();
        String deletedById = transactionService.deleteById(id);
        if (deletedById.equals("deleted")) {
            logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Deleting transaction : " + model.getTransactionId()));
            MainWindow.showInformationWindow("Info", deletedById);
            update();

        } else {
            logsService.save(new LogDTO(LogIdentifier.Error, toString(), deletedById));
            MainWindow.showErrorWindow("Error deleting record", deletedById);
        }
        resetModel();
    }

    public synchronized Task<String> updateTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                List<TransactionDTO> dataBaseList = Lists.newArrayList(transactionService.findAll());

                Platform.runLater(() -> {
                    getDataList().removeAll(
                            dataBaseList
                                    .stream()
                                    .map(TransactionsModel.TableObject::createFromTransactionDTO)
                                    .filter(item -> !getDataList().contains(item))
                                    .collect(() -> getDataList(), ObservableList::add, ObservableList::addAll)
                                    .stream()
                                    .filter(tableListItem -> dataBaseList.stream().map(TransactionsModel.TableObject::createFromTransactionDTO).noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
                                    .collect(Collectors.toList()));
                    detailedUpdates(dataBaseList);
                });
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

    public void detailedUpdates(List<TransactionDTO> dataBaseList) {
        if (getDataList() != null) {

            final Map<Long, TransactionDTO> transactionToMap = dataBaseList.stream().collect(Collectors.toMap(TransactionDTO::getId, Function.identity()));
            for (TransactionsModel.TableObject item : getDataList()) {
                try {
                    final TransactionDTO transactionDTO = transactionToMap.get(item.getTransactionIdColumn());

                    item.setMaterialColumn(transactionDTO.getMaterial());
                    item.setStationColumn(transactionDTO.getStation());
                    item.setTankColumn(transactionDTO.getTank());
                    item.setDriverColumn(transactionDTO.getDriver());
                    item.setTruckTrailerColumn(transactionDTO.getTruckTrailer());
                    item.setTruckContainerColumn(transactionDTO.getTruckContainer());
                    item.setOperationTypeColumn(transactionDTO.getOperationType());
                    item.setQtyColumn(transactionDTO.getQty());
                    item.setDateTimeColumn(transactionDTO.getDateTime());
                    item.setCreationDateColumn(transactionDTO.getCreationDate());
                    item.setModifyDateColumn(transactionDTO.getModifyDate());
                    item.setCreatedByColumn(transactionDTO.getCreatedBy());
                    item.setOnTerminalColumn(transactionDTO.getOnTerminal());
                } catch (Exception e) {
                    log.fatal(e);
                }
            }
        }
    }

    public void resetModel() {
        Platform.runLater(() -> {
            try {
                model.setTransactionId(0);
                model.setMaterial(0);
                model.setStation(0);
                model.setTank(0);
                model.setDriver(0);
                model.setTruckTrailer(0);
                model.setTruckContainer(0);
                model.setOperationType(null);
                model.setQty(0.0);
                model.setDateTime(null);
                model.setCreatedBy((""));
                model.setCreationDate("");
                model.setModifyDate("");
                model.setOnTerminal("");

                materialsModel.setMaterialId(0);
                materialsModel.setName("");
                materialsModel.setDescription("");

                stationModel.setStationId(0);
                stationModel.setName("");
                stationModel.setLocation("");
                stationModel.setComputerName("");

                tanksModel.setTankId(0);
                tanksModel.setName("");
                tanksModel.setCalculatedQty(0);
                tanksModel.setQty(0);

                driversModel.setDriverId(0);
                driversModel.setName("");
                driversModel.setLicenseNumber("");

                truckTrailersModel.setTruckTrailerId(0);
                truckTrailersModel.setTrailerNumber("");
                truckTrailersModel.setLicenseNumber("");

                truckContainersModel.setTruckContainerId(0);
                truckContainersModel.setContainerNumber("");
                truckContainersModel.setLicenseNumber("");

            } catch (Exception e) {
                log.fatal(e);
            }
        });
    }

    public ObservableList<MaterialsModel.TableObject> getMaterialContextList() {
        materialContextList.clear();
        materialContextList.addAll(materialService.findAll().stream().map(MaterialsModel.TableObject::createFromMaterialDTO).collect(Collectors.toList()));
        return materialContextList;
    }

    public ObservableList<StationsModel.TableObject> getStationContextList() {
        stationContextList.clear();
        stationContextList.addAll(stationService.findAll().stream().map(StationsModel.TableObject::createFromStationDTO).collect(Collectors.toList()));
        return stationContextList;
    }

    public ObservableList<TanksModel.TableObject> getTanksContextList() {
        tanksContextList.clear();
        tanksContextList.addAll(tanksService.findByMaterialAndStation(materialsModel.getMaterialId(), stationModel.getStationId()).stream().map(TanksModel.TableObject::createFromTankDTO).collect(Collectors.toList()));
        return tanksContextList;
    }

    public ObservableList<DriversModel.TableObject> getDriverContextList() {
        driversContextList.clear();
        driversContextList.addAll(driverService.findAll().stream().map(DriversModel.TableObject::createFromDriverDTO).collect(Collectors.toList()));
        return driversContextList;
    }

    public ObservableList<TruckContainerModel.TableObject> getTruckContainerContextList() {
        truckContainerContextList.clear();
        truckContainerContextList.addAll(truckContainerService.findAll().stream().map(TruckContainerModel.TableObject::createFromTruckContainerDTO).collect(Collectors.toList()));
        return truckContainerContextList;
    }

    public ObservableList<TruckTrailerModel.TableObject> getTruckTrailerContextList() {
        truckTrailerContextList.clear();
        truckTrailerContextList.addAll(truckTrailerService.findAll().stream().map(TruckTrailerModel.TableObject::createFromTruckTrailerDTO).collect(Collectors.toList()));
        return truckTrailerContextList;
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

    public void setTankData(TanksModel.TableObject selected) {
        if (selected != null) {
            tanksModel.setTankId(selected.getTankIdColumn());
            tanksModel.setName(selected.getNameColumn());
            tanksModel.setQty(selected.getQtyColumn());
            tanksModel.setCalculatedQty(selected.getCalculatedQtyColumn());
        }
    }


    public void setDriverData(DriversModel.TableObject selected) {
        if (selected != null) {
            driversModel.setDriverId(selected.getDriverIdColumn());
            driversModel.setName(selected.getNameColumn());
            driversModel.setLicenseNumber(selected.getLicenseNumberColumn());
        }
    }


    public void setTruckTrailerData(TruckTrailerModel.TableObject selected) {
        if (selected != null) {
            truckTrailersModel.setTruckTrailerId(selected.getTruckTrailerIdColumn());
            truckTrailersModel.setTrailerNumber(selected.getTrailerNumberColumn());
            truckTrailersModel.setLicenseNumber(selected.getLicenseNumberColumn());
        }
    }

    public void setTruckContainerData(TruckContainerModel.TableObject selected) {
        //TODO--In tanks
        if (selected != null) {
            truckContainersModel.setTruckContainerId(selected.getTruckContainerIdColumn());
            truckContainersModel.setContainerNumber(selected.getContainerNumberColumn());
            truckContainersModel.setLicenseNumber(selected.getLicenseNumberColumn());
        }
    }

    @Override
    public String toString() {
        return "Dashboard";
    }
}
