package soulintec.com.tmsclient.Graphics.Windows.DriversWindow;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.DriverDTO;
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Services.DriverService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class DriversController {

    private final DriversModel model = new DriversModel();
    private final ObservableList<DriversModel.TableObject> tableList = FXCollections.observableArrayList();

    @Autowired
    private DriverService driverService;

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
            }, () -> {
                DriversView.showErrorWindow("Data doesn't exist", "Error getting data for selected driver");
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
            DriversView.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            DriversView.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (StringUtils.isBlank(mobileNumber)) {
            DriversView.showWarningWindow("Missing Data", "Please enter mobile number");
            return;
        }
        if (licenceExpirationDate == null) {
            DriversView.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            DriversView.showWarningWindow("Missing Data", "Please select permission");
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
                DriversView.showInformationWindow("Info", save);
                updateDataList();

            } else {
                DriversView.showErrorWindow("Error updating data", save);
            }

            resetModel();
        } else {
            DriversView.showErrorWindow("Error inserting data", "Driver already exist , please check entered data");
        }
    }

    @Async
    public void onUpdate(MouseEvent mouseEvent) {

        String name = model.getName();
        String licenceNumber = model.getLicenseNumber();
        LocalDate licenceExpirationDate = model.getLicenceExpirationDate();
        String mobileNumber = model.getMobileNumber();
        Permissions permissions = model.getPermissions();


        if (StringUtils.isBlank(name)) {
            DriversView.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            DriversView.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (StringUtils.isBlank(mobileNumber)) {
            DriversView.showWarningWindow("Missing Data", "Please enter mobile number");
            return;
        }
        if (licenceExpirationDate == null) {
            DriversView.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            DriversView.showWarningWindow("Missing Data", "Please select permission");
            return;
        }
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(model.getDriverId());
        driverDTO.setName(name);
        driverDTO.setLicenseNumber(licenceNumber);
        driverDTO.setLicenceExpirationDate(licenceExpirationDate);
        driverDTO.setMobileNumber(mobileNumber);
        driverDTO.setPermissions(model.getPermissions());
        driverDTO.setComment(model.getComment());

        String save = driverService.save(driverDTO);

        if (save.equals("saved")) {
            DriversView.showInformationWindow("Info", save);
            updateDataList();

        } else {
            DriversView.showErrorWindow("Error updating data", save);
        }
        resetModel();
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {
        long id = model.getDriverId();
        String deletedById = driverService.deleteById(id);
        if (deletedById.equals("deleted")) {
            DriversView.showInformationWindow("Info", deletedById);
            updateDataList();

        } else {
            DriversView.showErrorWindow("Error deleting record", deletedById);
        }
        resetModel();
    }

    @Async
    public void updateDataList() {
        List<DriverDTO> list = driverService.findAll();
        getDataList().removeAll(list.stream().map(DriversModel.TableObject::createFromDriverDTO)
                .filter(item -> !tableList.contains(item))
                .collect(this::getDataList, ObservableList::add, ObservableList::addAll)
                .stream()
                .filter(tableListItem -> list.stream().map(DriversModel.TableObject::createFromDriverDTO)
                        .noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem))).sorted()
                .collect(Collectors.toList()));
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
            } catch (Exception e) {
                log.fatal(e);
            }
        });
    }
}
