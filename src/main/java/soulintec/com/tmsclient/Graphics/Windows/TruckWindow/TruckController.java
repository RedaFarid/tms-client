package soulintec.com.tmsclient.Graphics.Windows.TruckWindow;

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
import soulintec.com.tmsclient.Entities.TruckTrailerDTO;
import soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow.MaterialsView;
import soulintec.com.tmsclient.Services.TruckContainerService;
import soulintec.com.tmsclient.Services.TruckTrailerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
                MaterialsView.showErrorWindow("Data doesn't exist", "Error getting data for selected truck container");
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
            TruckView.showWarningWindow("Missing Data", "Please enter container number");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            TruckView.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (maximumWeightConstrain <= 0.0) {
            TruckView.showWarningWindow("Missing Data", "Please enter max weight");
            return;
        }
        if (licenceExpirationDate == null) {
            TruckView.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            TruckView.showWarningWindow("Missing Data", "Please select permission");
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
            TruckView.showInformationWindow("Info", save);
            updateContainerDataList();

        } else {
            TruckView.showErrorWindow("Error inserting data", save);
        }

        resetContainerModel();
            } else {
                TruckView.showErrorWindow("Error inserting data", "Truck container already exist , please check entered data");
            }
        } else {
            TruckView.showErrorWindow("Error inserting data", "Truck container already exist , please check entered data");
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
            TruckView.showWarningWindow("Missing Data", "Please enter container number");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            TruckView.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (maximumWeightConstrain <= 0.0) {
            TruckView.showWarningWindow("Missing Data", "Please enter maximum weight");
            return;
        }
        if (licenceExpirationDate == null) {
            TruckView.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            TruckView.showWarningWindow("Missing Data", "Please select permission");
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
                TruckView.showInformationWindow("Info", save);
                updateContainerDataList();

            } else {
                TruckView.showErrorWindow("Error updating data", save);
            }
            resetContainerModel();
        } else {
            TruckView.showErrorWindow("Error updating data", "Truck container doesn't  exist ");
        }
    }

    @Async
    public void onContainerDelete(MouseEvent mouseEvent) {
        long materialId = truckContainerModel.getTruckContainerId();
        String deletedById = truckContainerService.deleteById(materialId);
        if (deletedById.equals("deleted")) {
            TruckView.showInformationWindow("Info", deletedById);
            updateContainerDataList();

        } else {
            TruckView.showErrorWindow("Error deleting record", deletedById);
        }
        resetContainerModel();
    }

    @Async
    public void updateContainerDataList() {
        List<TruckContainerDTO> list = truckContainerService.findAll();
        if(list!=null) {
            getTruckContainerDataList().removeAll(list.stream().map(TruckContainerModel.TableObject::createFromTruckContainerDTO)
                    .filter(item -> !truckContainerTableList.contains(item))
                    .collect(this::getTruckContainerDataList, ObservableList::add, ObservableList::addAll)
                    .stream()
                    .filter(tableListItem -> list.stream().map(TruckContainerModel.TableObject::createFromTruckContainerDTO)
                            .noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem))).sorted()
                    .collect(Collectors.toList()));
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
                TruckView.showErrorWindow("Data doesn't exist", "Error getting data for selected truck trailer");
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
            TruckView.showWarningWindow("Missing Data", "Please enter trailer number");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            TruckView.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (licenceExpirationDate == null) {
            TruckView.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            TruckView.showWarningWindow("Missing Data", "Please select permission");
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
                    TruckView.showInformationWindow("Info", save);
                    updateTrailerDataList();

                } else {
                    TruckView.showErrorWindow("Error inserting data", save);
                }

                resetTrailerModel();
            } else {
                TruckView.showErrorWindow("Error inserting data", "Truck trailer already exist , please check entered data");
            }
        } else {
            TruckView.showErrorWindow("Error inserting data", "Truck trailer already exist , please check entered data");
        }
    }

    @Async
    public void onUpdateTruckTrailer(MouseEvent mouseEvent) {
        String trailerNumber = truckTrailerModel.getTrailerNumber();
        String licenceNumber = truckTrailerModel.getLicenseNumber();
        LocalDate licenceExpirationDate = truckTrailerModel.getLicenceExpirationDate();
        Permissions permissions = truckTrailerModel.getPermissions();

        if (StringUtils.isBlank(trailerNumber)) {
            TruckView.showWarningWindow("Missing Data", "Please enter trailer number");
            return;
        }
        if (StringUtils.isBlank(licenceNumber)) {
            TruckView.showWarningWindow("Missing Data", "Please enter licence number");
            return;
        }
        if (licenceExpirationDate == null) {
            TruckView.showWarningWindow("Missing Data", "Please enter expiration date");
            return;
        }
        if (Objects.isNull(permissions)) {
            TruckView.showWarningWindow("Missing Data", "Please select permission");
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
                TruckView.showInformationWindow("Info", save);
                updateTrailerDataList();

            } else {
                TruckView.showErrorWindow("Error updating data", save);
            }
            resetTrailerModel();
        } else {
            TruckView.showErrorWindow("Error updating data", "Truck trailer doesn't  exist ");
        }
    }

    @Async
    public void onTrailerDelete(MouseEvent mouseEvent) {
        long materialId = truckTrailerModel.getTruckTrailerId();
        String deletedById = truckTrailerService.deleteById(materialId);
        if (deletedById.equals("deleted")) {
            TruckView.showInformationWindow("Info", deletedById);
            updateTrailerDataList();

        } else {
            TruckView.showErrorWindow("Error deleting record", deletedById);
        }
        resetTrailerModel();
    }

    @Async
    public void updateTrailerDataList() {
        List<TruckTrailerDTO> list = truckTrailerService.findAll();
        if(list != null){
        getTruckTrailerDataList().removeAll(list.stream().map(TruckTrailerModel.TableObject::createFromTruckTrailerDTO)
                .filter(item -> !truckTrailerTableList.contains(item))
                .collect(this::getTruckTrailerDataList, ObservableList::add, ObservableList::addAll)
                .stream()
                .filter(tableListItem -> list.stream().map(TruckTrailerModel.TableObject::createFromTruckTrailerDTO)
                        .noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem))).sorted()
                .collect(Collectors.toList()));
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

}
