package soulintec.com.tmsclient.Graphics.Windows.TruckContainerWindow;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Entities.TruckContainerDTO;
import soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow.MaterialsWindow;
import soulintec.com.tmsclient.Services.TruckContainerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class TruckController {

    private final TruckContainerModel truckContainerModel = new TruckContainerModel();
    private final ObservableList<TruckContainerModel.TableObject> truckContainerTableList = FXCollections.observableArrayList();

    @Autowired
    private TruckContainerService truckContainerService;

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
                truckContainerModel.setCreatedBy(tableObject.getCreatedByColumn());
                truckContainerModel.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                truckContainerModel.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                truckContainerModel.setOnTerminal(tableObject.getOnTerminalColumn());
            }, () -> {
                MaterialsWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected truck container");
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
            TruckWindow.showWarningWindow("Missing Data", "Please enter container number");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            TruckWindow.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (maximumWeightConstrain <= 0.0) {
            TruckWindow.showWarningWindow("Missing Data", "Please enter max weight");
            return;
        }
        if (licenceExpirationDate == null) {
            TruckWindow.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            TruckWindow.showWarningWindow("Missing Data", "Please select permission");
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

        //TODO-- add this to all and on server side
        String save = truckContainerService.save(truckContainerDTO);
        if (save.equals("saved")) {
            TruckWindow.showInformationWindow("Info", save);
            updateDataList();

        } else {
            TruckWindow.showErrorWindow("Error inserting data", save);
        }

        resetModel();
            } else {
                TruckWindow.showErrorWindow("Error inserting data", "Truck container already exist , please check entered data");
            }
        } else {
            TruckWindow.showErrorWindow("Error inserting data", "Truck container already exist , please check entered data");
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
            TruckWindow.showWarningWindow("Missing Data", "Please enter container number");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            TruckWindow.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (maximumWeightConstrain <= 0.0) {
            TruckWindow.showWarningWindow("Missing Data", "Please enter maximum weight");
            return;
        }
        if (licenceExpirationDate == null) {
            TruckWindow.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            TruckWindow.showWarningWindow("Missing Data", "Please select permission");
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
                TruckWindow.showInformationWindow("Info", save);
                updateDataList();

            } else {
                TruckWindow.showErrorWindow("Error updating data", save);
            }
            resetModel();
        } else {
            TruckWindow.showErrorWindow("Error updating data", "Truck container doesn't  exist ");
        }
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {
        long materialId = truckContainerModel.getTruckContainerId();
        String deletedById = truckContainerService.deleteById(materialId);
        if (deletedById.equals("deleted")) {
            TruckWindow.showInformationWindow("Info", deletedById);
            updateDataList();

        } else {
            TruckWindow.showErrorWindow("Error deleting record", deletedById);
        }
        resetModel();
    }

    @Async
    public void updateDataList() {
        List<TruckContainerDTO> list = truckContainerService.findAll();
        getTruckContainerDataList().removeAll(list.stream().map(TruckContainerModel.TableObject::createFromTruckContainerDTO)
                .filter(item -> !truckContainerTableList.contains(item))
                .collect(this::getTruckContainerDataList, ObservableList::add, ObservableList::addAll)
                .stream()
                .filter(tableListItem -> list.stream().map(TruckContainerModel.TableObject::createFromTruckContainerDTO)
                        .noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem))).sorted()
                .collect(Collectors.toList()));

    }

    public void resetModel() {
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

}
