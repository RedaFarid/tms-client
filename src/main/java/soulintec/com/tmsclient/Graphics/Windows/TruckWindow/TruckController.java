package soulintec.com.tmsclient.Graphics.Windows.TruckWindow;

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
import soulintec.com.tmsclient.Entities.*;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.StationsWindow.StationsModel;
import soulintec.com.tmsclient.Services.LogsService;
import soulintec.com.tmsclient.Services.TransactionService;
import soulintec.com.tmsclient.Services.TruckContainerService;
import soulintec.com.tmsclient.Services.TruckTrailerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class TruckController {

    //Truck Container
    private final TruckContainerModel truckContainerModel = new TruckContainerModel();
    private final ObservableList<TruckContainerModel.TableObject> truckContainerTableList = FXCollections.observableArrayList();

    @Autowired
    private TruckContainerService truckContainerService;

    //Truck Trailer
    private final TruckTrailerModel truckTrailerModel = new TruckTrailerModel();
    private final ObservableList<TruckTrailerModel.TableObject> truckTrailerTableList = FXCollections.observableArrayList();

    @Autowired
    private TruckTrailerService truckTrailerService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LogsService logsService;

    @Autowired(required = false)
    private Executor containerExecutor;

    @Autowired(required = false)
    private Executor trailerExecutor;

    //Truck Container
    public TruckContainerModel getTruckContainerModel() {
        return truckContainerModel;
    }

    public ObservableList<TruckContainerModel.TableObject> getTruckContainerDataList() {
        return truckContainerTableList;
    }

    public void onTruckContainerTableSelection(ObservableList<? extends TruckContainerModel.TableObject> list) {
        if (list.size() > 0) {
            TruckContainerModel.TableObject tableObject = list.get(0);
            truckContainerService.findById(tableObject.getTruckContainerIdColumn()).ifPresentOrElse(truckContainerDTO -> {
                truckContainerModel.setTruckContainerId(truckContainerDTO.getId());
                truckContainerModel.setContainerNumber(truckContainerDTO.getContainerNumber());
                truckContainerModel.setLicenseNumber(truckContainerDTO.getLicenceNumber());
                truckContainerModel.setLicenceExpirationDate(truckContainerDTO.getLicenceExpirationDate());
                truckContainerModel.setMaximumWeightConstrain(truckContainerDTO.getMaximumWeightConstrain());
                truckContainerModel.setPermissions(truckContainerDTO.getPermissions());
                truckContainerModel.setComment(truckContainerDTO.getComment());
                truckContainerModel.setCreatedBy(tableObject.getCreatedByColumn());
                truckContainerModel.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                truckContainerModel.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                truckContainerModel.setOnTerminal(tableObject.getOnTerminalColumn());
            }, () -> {
//                logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Error getting truck container data"));
                MainWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected truck container");
            });
        }
    }

    @Async
    public void onInsertTruckContainer(MouseEvent mouseEvent) {

        String containerNumber = truckContainerModel.getContainerNumber();
        String licenceNumber = truckContainerModel.getLicenseNumber();
        LocalDate licenceExpirationDate = truckContainerModel.getLicenceExpirationDate();
        Double maximumWeightConstrain = truckContainerModel.getMaximumWeightConstrain();
        Permissions permissions = truckContainerModel.getPermissions();

        if (StringUtils.isBlank(containerNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter container number");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (maximumWeightConstrain <= 0.0) {
            MainWindow.showWarningWindow("Missing Data", "Please enter max weight");
            return;
        }
        if (licenceExpirationDate == null) {
            MainWindow.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            MainWindow.showWarningWindow("Missing Data", "Please select permission");
            return;
        }

        if (!truckContainerService.findByLicenceNo(licenceNumber).isPresent()) {
            if (!truckContainerService.findByContainerNo(containerNumber).isPresent()) {
                TruckContainerDTO truckContainerDTO = new TruckContainerDTO();
                truckContainerDTO.setContainerNumber(containerNumber);
                truckContainerDTO.setLicenceNumber(licenceNumber);
                truckContainerDTO.setLicenceExpirationDate(licenceExpirationDate);
                truckContainerDTO.setMaximumWeightConstrain(maximumWeightConstrain);
                truckContainerDTO.setPermissions(permissions);
                truckContainerDTO.setComment(truckContainerModel.getComment());

                String save = truckContainerService.save(truckContainerDTO);
                if (save.equals("saved")) {
                    logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Inserting new truck container : " + containerNumber));
                    MainWindow.showInformationWindow("Info", save);
                    updateContainer();

                } else {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                    MainWindow.showErrorWindow("Error inserting data", save);
                }

                resetContainerModel();
            } else {
                MainWindow.showErrorWindow("Error inserting data", "Truck container already exist , please check entered data");
            }
        } else {
            MainWindow.showErrorWindow("Error inserting data", "Truck container already exist , please check entered data");
        }
    }

    @Async
    public void onUpdateTruckContainer(MouseEvent mouseEvent) {
        String containerNumber = truckContainerModel.getContainerNumber();
        String licenceNumber = truckContainerModel.getLicenseNumber();
        LocalDate licenceExpirationDate = truckContainerModel.getLicenceExpirationDate();
        Double maximumWeightConstrain = truckContainerModel.getMaximumWeightConstrain();
        Permissions permissions = truckContainerModel.getPermissions();

        if (StringUtils.isBlank(containerNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter container number");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (maximumWeightConstrain <= 0.0) {
            MainWindow.showWarningWindow("Missing Data", "Please enter maximum weight");
            return;
        }
        if (licenceExpirationDate == null) {
            MainWindow.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            MainWindow.showWarningWindow("Missing Data", "Please select permission");
            return;
        }

        if (truckContainerService.findById(truckContainerModel.getTruckContainerId()).isPresent()) {

            TruckContainerDTO truckContainerDTO = new TruckContainerDTO();
            truckContainerDTO.setContainerNumber(containerNumber);
            truckContainerDTO.setLicenceNumber(licenceNumber);
            truckContainerDTO.setLicenceExpirationDate(licenceExpirationDate);
            truckContainerDTO.setMaximumWeightConstrain(maximumWeightConstrain);
            truckContainerDTO.setPermissions(permissions);
            truckContainerDTO.setComment(truckContainerModel.getComment());
            truckContainerDTO.setId(truckContainerModel.getTruckContainerId());

            String save = truckContainerService.save(truckContainerDTO);
            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Updating data for truck container : " + containerNumber));
                MainWindow.showInformationWindow("Info", save);
                updateContainer();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MainWindow.showErrorWindow("Error updating data", save);
            }
            resetContainerModel();
        } else {
            MainWindow.showErrorWindow("Error updating data", "Truck container doesn't  exist ");
        }
    }

    @Async
    public void onContainerDelete(MouseEvent mouseEvent) {
        long truckContainerId = truckContainerModel.getTruckContainerId();
        if (transactionService.findAll().stream().filter(i -> i.getTruckContainer() == truckContainerId).count() != 0) {
            logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Container :  " + truckContainerModel.getContainerNumber() + " can't be deleted because there are transactions relate to it "));
            MainWindow.showErrorWindow("Error deleting record", "Container can't be deleted because there are transactions relate to it \n please delete related transactions first");
        } else {
            String deletedById = truckContainerService.deleteById(truckContainerId);
            if (deletedById.equals("deleted")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Deleting truck container : " + truckContainerModel.getContainerNumber()));
                MainWindow.showInformationWindow("Info", deletedById);
                updateContainer();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), deletedById));
                MainWindow.showErrorWindow("Error deleting record", deletedById);
            }
            resetContainerModel();
        }
    }

    public synchronized Task<String> updateTaskContainer() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                List<TruckContainerDTO> dataBaseList = Lists.newArrayList(truckContainerService.findAll());

                Platform.runLater(() -> {
                    getTruckContainerDataList().removeAll(
                            dataBaseList
                                    .stream()
                                    .map(TruckContainerModel.TableObject::createFromTruckContainerDTO)
                                    .filter(item -> !getTruckContainerDataList().contains(item))
                                    .collect(() -> getTruckContainerDataList(), ObservableList::add, ObservableList::addAll)
                                    .stream()
                                    .filter(tableListItem -> dataBaseList.stream().map(TruckContainerModel.TableObject::createFromTruckContainerDTO).noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
                                    .collect(Collectors.toList()));
                });
                detailedUpdatesContainer(dataBaseList);
                return null;
            }
        };


    }

    public ReadOnlyBooleanProperty updateContainer() {
        final Task<String> updateTask = updateTaskContainer();
        ReadOnlyBooleanProperty readOnlyBooleanProperty = updateTask.runningProperty();
        containerExecutor.execute(updateTask);
        return readOnlyBooleanProperty;
    }

    public void detailedUpdatesContainer(List<TruckContainerDTO> dataBaseList) {
        if (getTruckContainerDataList() != null) {

            final Map<Long, TruckContainerDTO> containerDTOMap = dataBaseList.stream().collect(Collectors.toMap(TruckContainerDTO::getId, Function.identity()));
            for (TruckContainerModel.TableObject item : getTruckContainerDataList()) {
                try {
                    final TruckContainerDTO truckContainerDTO = containerDTOMap.get(item.getTruckContainerIdColumn());
                    if (truckContainerDTO != null) {
                        item.setContainerNumberColumn(truckContainerDTO.getContainerNumber());
                        item.setLicenseNumberColumn(truckContainerDTO.getLicenceNumber());
                        item.setCommentColumn(truckContainerDTO.getComment());
                        item.setLicenceExpirationDateColumn(truckContainerDTO.getLicenceExpirationDate());
                        item.setPermissionsColumn(truckContainerDTO.getPermissions());
                        item.setCreationDateColumn(truckContainerDTO.getCreationDate());
                        item.setModifyDateColumn(truckContainerDTO.getModifyDate());
                        item.setCreatedByColumn(truckContainerDTO.getCreatedBy());
                        item.setOnTerminalColumn(truckContainerDTO.getOnTerminal());
                    }

                } catch (Exception e) {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), e.getMessage()));
                    log.fatal(e);
                }
            }
        }
    }


    public void resetContainerModel() {
        Platform.runLater(() -> {
            try {
                truckContainerModel.setTruckContainerId(0);
                truckContainerModel.setContainerNumber("");
                truckContainerModel.setLicenseNumber("");
                truckContainerModel.setLicenceExpirationDate(null);
                truckContainerModel.setMaximumWeightConstrain(0);
                truckContainerModel.setPermissions(null);
                truckContainerModel.setComment("");
                truckContainerModel.setCreatedBy((""));
                truckContainerModel.setCreationDate("");
                truckContainerModel.setModifyDate("");
                truckContainerModel.setOnTerminal("");
            } catch (Exception e) {
                log.fatal(e);
            }
        });
    }

    //Truck Trailer
    public TruckTrailerModel getTruckTrailerModel() {
        return truckTrailerModel;
    }

    public ObservableList<TruckTrailerModel.TableObject> getTruckTrailerDataList() {
        return truckTrailerTableList;
    }

    public void onTruckTrailerTableSelection(ObservableList<? extends TruckTrailerModel.TableObject> list) {
        if (list.size() > 0) {
            TruckTrailerModel.TableObject tableObject = list.get(0);
            truckTrailerService.findById(tableObject.getTruckTrailerIdColumn()).ifPresentOrElse(truckTrailerDTO -> {
                truckTrailerModel.setTruckTrailerId(truckTrailerDTO.getId());
                truckTrailerModel.setTrailerNumber(truckTrailerDTO.getTrailerNumber());
                truckTrailerModel.setLicenseNumber(truckTrailerDTO.getLicenceNumber());
                truckTrailerModel.setLicenceExpirationDate(truckTrailerDTO.getLicenceExpirationDate());
                truckTrailerModel.setPermissions(truckTrailerDTO.getPermissions());
                truckTrailerModel.setComment(truckTrailerDTO.getComment());
                truckTrailerModel.setCreatedBy(tableObject.getCreatedByColumn());
                truckTrailerModel.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                truckTrailerModel.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                truckTrailerModel.setOnTerminal(tableObject.getOnTerminalColumn());
            }, () -> {
//                logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Error getting truck trailer data "));
                MainWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected truck trailer");
            });
        }
    }

    @Async
    public void onInsertTruckTrailer(MouseEvent mouseEvent) {

        String trailerNumber = truckTrailerModel.getTrailerNumber();
        String licenceNumber = truckTrailerModel.getLicenseNumber();
        LocalDate licenceExpirationDate = truckTrailerModel.getLicenceExpirationDate();
        Permissions permissions = truckTrailerModel.getPermissions();

        if (StringUtils.isBlank(trailerNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter trailer number");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (licenceExpirationDate == null) {
            MainWindow.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            MainWindow.showWarningWindow("Missing Data", "Please select permission");
            return;
        }

        if (!truckTrailerService.findByLicenceNo(licenceNumber).isPresent()) {
            if (!truckTrailerService.findByTrailerNo(trailerNumber).isPresent()) {
                TruckTrailerDTO truckTrailerDTO = new TruckTrailerDTO();
                truckTrailerDTO.setTrailerNumber(trailerNumber);
                truckTrailerDTO.setLicenceNumber(licenceNumber);
                truckTrailerDTO.setLicenceExpirationDate(licenceExpirationDate);
                truckTrailerDTO.setPermissions(permissions);
                truckTrailerDTO.setComment(truckTrailerModel.getComment());

                String save = truckTrailerService.save(truckTrailerDTO);
                if (save.equals("saved")) {
                    logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Inserting new truck trailer : " + trailerNumber));
                    MainWindow.showInformationWindow("Info", save);
                    updateTrailer();
                } else {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                    MainWindow.showErrorWindow("Error inserting data", save);
                }

                resetTrailerModel();
            } else {
                MainWindow.showErrorWindow("Error inserting data", "Truck trailer already exist , please check entered data");
            }
        } else {
            MainWindow.showErrorWindow("Error inserting data", "Truck trailer already exist , please check entered data");
        }
    }

    @Async
    public void onUpdateTruckTrailer(MouseEvent mouseEvent) {
        String trailerNumber = truckTrailerModel.getTrailerNumber();
        String licenceNumber = truckTrailerModel.getLicenseNumber();
        LocalDate licenceExpirationDate = truckTrailerModel.getLicenceExpirationDate();
        Permissions permissions = truckTrailerModel.getPermissions();

        if (StringUtils.isBlank(trailerNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter trailer number");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (licenceExpirationDate == null) {
            MainWindow.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            MainWindow.showWarningWindow("Missing Data", "Please select permission");
            return;
        }

        if (truckTrailerService.findById(truckTrailerModel.getTruckTrailerId()).isPresent()) {

            TruckTrailerDTO truckTrailerDTO = new TruckTrailerDTO();
            truckTrailerDTO.setTrailerNumber(trailerNumber);
            truckTrailerDTO.setLicenceNumber(licenceNumber);
            truckTrailerDTO.setLicenceExpirationDate(licenceExpirationDate);
            truckTrailerDTO.setPermissions(permissions);
            truckTrailerDTO.setComment(truckTrailerModel.getComment());
            truckTrailerDTO.setId(truckTrailerModel.getTruckTrailerId());

            String save = truckTrailerService.save(truckTrailerDTO);
            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Updating data for truck trailer : " + trailerNumber));
                MainWindow.showInformationWindow("Info", save);
                updateTrailer();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MainWindow.showErrorWindow("Error updating data", save);
            }
            resetTrailerModel();
        } else {
            MainWindow.showErrorWindow("Error updating data", "Truck trailer doesn't  exist ");
        }
    }

    @Async
    public void onTrailerDelete(MouseEvent mouseEvent) {
        long truckTrailerId = truckTrailerModel.getTruckTrailerId();
        if (transactionService.findAll().stream().filter(i -> i.getTruckContainer() == truckTrailerId).count() != 0) {
            logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Trailer :  " + truckTrailerModel.getTrailerNumber() + " can't be deleted because there are transactions relate to it "));
            MainWindow.showErrorWindow("Error deleting record", "Trailer can't be deleted because there are transactions relate to it \n please delete related transactions first");
        } else {
            String deletedById = truckTrailerService.deleteById(truckTrailerId);
            if (deletedById.equals("deleted")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Deleting truck trailer : " + truckTrailerModel.getTrailerNumber()));
                MainWindow.showInformationWindow("Info", deletedById);
                updateTrailer();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), deletedById));
                MainWindow.showErrorWindow("Error deleting record", deletedById);
            }
            resetTrailerModel();
        }
    }

    public synchronized Task<String> updateTaskTrailer() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                List<TruckTrailerDTO> dataBaseList = Lists.newArrayList(truckTrailerService.findAll());

                Platform.runLater(() -> {
                    getTruckTrailerDataList().removeAll(
                            dataBaseList
                                    .stream()
                                    .map(TruckTrailerModel.TableObject::createFromTruckTrailerDTO)
                                    .filter(item -> !getTruckTrailerDataList().contains(item))
                                    .collect(() -> getTruckTrailerDataList(), ObservableList::add, ObservableList::addAll)
                                    .stream()
                                    .filter(tableListItem -> dataBaseList.stream().map(TruckTrailerModel.TableObject::createFromTruckTrailerDTO).noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
                                    .collect(Collectors.toList()));
                });
                detailedUpdates(dataBaseList);
                return null;
            }
        };


    }

    public ReadOnlyBooleanProperty updateTrailer() {
        final Task<String> updateTask = updateTaskTrailer();
        ReadOnlyBooleanProperty readOnlyBooleanProperty = updateTask.runningProperty();
        trailerExecutor.execute(updateTask);
        return readOnlyBooleanProperty;
    }

    public void detailedUpdates(List<TruckTrailerDTO> dataBaseList) {
        if (getTruckTrailerDataList() != null) {

            final Map<Long, TruckTrailerDTO> trailerDTOMap = dataBaseList.stream().collect(Collectors.toMap(TruckTrailerDTO::getId, Function.identity()));
            for (TruckTrailerModel.TableObject item : getTruckTrailerDataList()) {
                try {
                    final TruckTrailerDTO truckTrailerDTO = trailerDTOMap.get(item.getTruckTrailerIdColumn());
                    if (truckTrailerDTO != null) {
                        item.setTrailerNumberColumn(truckTrailerDTO.getTrailerNumber());
                        item.setLicenseNumberColumn(truckTrailerDTO.getLicenceNumber());
                        item.setCommentColumn(truckTrailerDTO.getComment());
                        item.setLicenceExpirationDateColumn(truckTrailerDTO.getLicenceExpirationDate());
                        item.setPermissionsColumn(truckTrailerDTO.getPermissions());
                        item.setCreationDateColumn(truckTrailerDTO.getCreationDate());
                        item.setModifyDateColumn(truckTrailerDTO.getModifyDate());
                        item.setCreatedByColumn(truckTrailerDTO.getCreatedBy());
                        item.setOnTerminalColumn(truckTrailerDTO.getOnTerminal());
                    }

                } catch (Exception e) {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), e.getMessage()));
                    log.fatal(e);
                }
            }
        }
    }

    public void resetTrailerModel() {
        Platform.runLater(() -> {
            try {
                truckTrailerModel.setTruckTrailerId(0);
                truckTrailerModel.setTrailerNumber("");
                truckTrailerModel.setLicenseNumber("");
                truckTrailerModel.setLicenceExpirationDate(null);
                truckTrailerModel.setPermissions(null);
                truckTrailerModel.setComment("");
                truckTrailerModel.setCreatedBy((""));
                truckTrailerModel.setCreationDate("");
                truckTrailerModel.setModifyDate("");
                truckTrailerModel.setOnTerminal("");
            } catch (Exception e) {
                log.fatal(e);
            }
        });
    }

    @Override
    public String toString() {
        return "Trucks";
    }
}
