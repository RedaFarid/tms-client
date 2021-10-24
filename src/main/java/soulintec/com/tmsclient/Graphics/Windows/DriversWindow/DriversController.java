package soulintec.com.tmsclient.Graphics.Windows.DriversWindow;

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
import soulintec.com.tmsclient.Entities.DriverDTO;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Entities.StationDTO;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.StationsWindow.StationsModel;
import soulintec.com.tmsclient.Services.DriverService;
import soulintec.com.tmsclient.Services.LogsService;
import soulintec.com.tmsclient.Services.TransactionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class DriversController {

    private final DriversModel model = new DriversModel();
    private final ObservableList<DriversModel.TableObject> tableList = FXCollections.observableArrayList();

    @Autowired
    private DriverService driverService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private LogsService logsService;

    @Autowired(required = false)
    private Executor executor;

    public DriversModel getModel() {
        return model;
    }

    public ObservableList<DriversModel.TableObject> getDataList() {
        return tableList;
    }

    public void onTableSelection(ObservableList<? extends DriversModel.TableObject> list) {
        if (list.size() > 0) {
            DriversModel.TableObject tableObject = list.get(0);
            driverService.findById(tableObject.getDriverIdColumn()).ifPresentOrElse(driverDTO -> {
                model.setDriverId(driverDTO.getId());
                model.setName(driverDTO.getName());
                model.setLicenseNumber(driverDTO.getLicenseNumber());
                model.setName(driverDTO.getName());
                model.setLicenceExpirationDate(driverDTO.getLicenceExpirationDate());
                model.setMobileNumber(driverDTO.getMobileNumber());
                model.setPermissions(driverDTO.getPermissions());
                model.setComment(driverDTO.getComment());
                model.setCreatedBy(tableObject.getCreatedByColumn());
                model.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                model.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                model.setOnTerminal(tableObject.getOnTerminalColumn());
                model.setModifiedBy(tableObject.getModifiedByColumn());
            }, () -> {
//                logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Error getting driver data"));
                MainWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected driver");
            });
        }
    }

    @Async
    public void onInsert(MouseEvent mouseEvent) {

        String name = model.getName();
        String licenceNumber = model.getLicenseNumber();
        LocalDate licenceExpirationDate = model.getLicenceExpirationDate();
        String mobileNumber = model.getMobileNumber();
        Permissions permissions = model.getPermissions();

        if (StringUtils.isBlank(name)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (StringUtils.isBlank(mobileNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter mobile number");
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

        if (!driverService.findByLicenceId(licenceNumber).isPresent()) {
            DriverDTO driverDTO = new DriverDTO();
            driverDTO.setName(name);
            driverDTO.setLicenseNumber(licenceNumber);
            driverDTO.setLicenceExpirationDate(licenceExpirationDate);
            driverDTO.setMobileNumber(mobileNumber);
            driverDTO.setPermissions(model.getPermissions());
            driverDTO.setComment(model.getComment());

            String save = driverService.save(driverDTO);

            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Inserting new driver : " + name));
                MainWindow.showInformationWindow("Info", save);
                update();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MainWindow.showErrorWindow("Error inserting data", save);
            }

            resetModel();
        } else {
            MainWindow.showErrorWindow("Error inserting data", "Driver already exist , please check entered data");
        }
    }

    @Async
    public void onUpdate(MouseEvent mouseEvent) {

        long id = model.getDriverId();
        String name = model.getName();
        String licenceNumber = model.getLicenseNumber();
        LocalDate licenceExpirationDate = model.getLicenceExpirationDate();
        String mobileNumber = model.getMobileNumber();
        Permissions permissions = model.getPermissions();

        if (id == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select driver");
            return;
        }

        if (StringUtils.isBlank(name)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (StringUtils.isBlank(mobileNumber)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter mobile number");
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

        if (driverService.findById(id).isPresent()) {
            DriverDTO driverDTO = new DriverDTO();
            driverDTO.setId(id);
            driverDTO.setName(name);
            driverDTO.setLicenseNumber(licenceNumber);
            driverDTO.setLicenceExpirationDate(licenceExpirationDate);
            driverDTO.setMobileNumber(mobileNumber);
            driverDTO.setPermissions(model.getPermissions());
            driverDTO.setComment(model.getComment());

            String save = driverService.save(driverDTO);

            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Updating data for driver : " + name));
                MainWindow.showInformationWindow("Info", save);
                update();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MainWindow.showErrorWindow("Error updating data", save);
            }

            resetModel();
        } else {
            MainWindow.showErrorWindow("Error updating data", "Driver doesn't exist , please check selected data");
        }
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {
        long id = model.getDriverId();

        if (transactionService.findAll().stream().filter(i -> i.getDriver() == id).count() != 0) {
            logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Driver :  " + model.getName() + " can't be deleted because there are transactions relate to it "));
            MainWindow.showErrorWindow("Error deleting record", "Driver can't be deleted because there are transactions relate to it \n please delete related transactions first");
        } else {
            String deletedById = driverService.deleteById(id);
            if (deletedById.equals("deleted")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Deleting driver : " + model.getName()));
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
                List<DriverDTO> dataBaseList = Lists.newArrayList(driverService.findAll());

                Platform.runLater(() -> {
                    getDataList().removeAll(
                            dataBaseList
                                    .stream()
                                    .map(DriversModel.TableObject::createFromDriverDTO)
                                    .filter(item -> !getDataList().contains(item))
                                    .collect(() -> getDataList(), ObservableList::add, ObservableList::addAll)
                                    .stream()
                                    .filter(tableListItem -> dataBaseList.stream().map(DriversModel.TableObject::createFromDriverDTO).noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
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

    public void detailedUpdates(List<DriverDTO> dataBaseList) {
        if (getDataList() != null) {

            final Map<Long, DriverDTO> driverDTOMap = dataBaseList.stream().collect(Collectors.toMap(DriverDTO::getId, Function.identity()));
            for (DriversModel.TableObject item : getDataList()) {
                try {
                    final DriverDTO driverDTO = driverDTOMap.get(item.getDriverIdColumn());
                    if (driverDTO != null) {
                        item.setNameColumn(driverDTO.getName());
                        item.setLicenseNumberColumn(driverDTO.getLicenseNumber());
                        item.setCommentColumn(driverDTO.getComment());
                        item.setLicenceExpirationDateColumn(driverDTO.getLicenceExpirationDate());
                        item.setMobileNumberColumn(driverDTO.getMobileNumber());
                        item.setPermissionsColumn(driverDTO.getPermissions());
                        item.setCreationDateColumn(driverDTO.getCreationDate());
                        item.setModifyDateColumn(driverDTO.getModifyDate());
                        item.setCreatedByColumn(driverDTO.getCreatedBy());
                        item.setOnTerminalColumn(driverDTO.getOnTerminal());
                        item.setModifiedByColumn(driverDTO.getLastModifiedBy());
                    }

                } catch (Exception e) {
                    logsService.save(new LogDTO(LogIdentifier.Error , toString() , e.getMessage()));
                    log.fatal(e);
                }
            }
        }
    }

    public void resetModel() {
        Platform.runLater(() -> {
            try {
                model.setDriverId(0);
                model.setName("");
                model.setLicenseNumber("");
                model.setLicenceExpirationDate(null);
                model.setMobileNumber("");
                model.setPermissions(null);
                model.setComment("");
                model.setCreatedBy((""));
                model.setCreationDate("");
                model.setModifyDate("");
                model.setOnTerminal("");
                model.setModifiedBy("");
            } catch (Exception e) {
                log.fatal(e);
            }
        });
    }

    @Override
    public String toString() {
        return "Drivers";
    }
}
