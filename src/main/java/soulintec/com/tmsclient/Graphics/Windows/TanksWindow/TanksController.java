package soulintec.com.tmsclient.Graphics.Windows.TanksWindow;


import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.TankDTO;
import soulintec.com.tmsclient.Graphics.Windows.ClientsWindow.ClientView;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow.MaterialsModel;
import soulintec.com.tmsclient.Services.MaterialService;
import soulintec.com.tmsclient.Services.TanksService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class TanksController {
    private final TanksModel model = new TanksModel();
    private final MaterialsModel materialsModel = new MaterialsModel();

    private final ObservableList<TanksModel.TableObject> tableList = FXCollections.observableArrayList();
    private final ObservableList<MaterialsModel.TableObject> productContextList = FXCollections.observableArrayList();

    @Autowired(required = false)
    private Executor executor;

    @Autowired
    private TanksService tanksService;

    @Autowired
    private MaterialService materialService;

    public TanksModel getModel() {
        return model;
    }

    public MaterialsModel getMaterialsModel() {
        return materialsModel;
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
                model.setDateOfQtySet(String.valueOf(tankDTO.getDateOfQtySet()));
                model.setUserOfQtySet(tankDTO.getUserOfQtySet());
                model.setMaterialID(tankDTO.getMaterialID());
                model.setCalculatedQty(tankDTO.getCalculatedQty());
                model.setCreatedBy(tableObject.getCreatedByColumn());
                model.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                model.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                model.setOnTerminal(tableObject.getOnTerminalColumn());

                materialService.findById(tankDTO.getMaterialID()).ifPresentOrElse(materialDTO -> {
                    materialsModel.setName(materialDTO.getName());
                    materialsModel.setDescription(materialDTO.getDescription());
                }, () -> {
                    materialsModel.setName("");
                    materialsModel.setDescription("");
                });

            }, () -> {
                TanksView.showErrorWindow("Data doesn't exist", "Error getting data for selected tank");
            });
        }
    }

    @Async
    public void onInsert(MouseEvent mouseEvent) {

        String name = model.getName();
        String station = model.getStation();
        Double capacity = model.getCapacity();

        if (StringUtils.isBlank(name)) {
            TanksView.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(station)) {
            TanksView.showWarningWindow("Missing Data", "Please enter station");
            return;
        }
        if (capacity <= 0) {
            TanksView.showWarningWindow("Missing Data", "Please enter capacity");
            return;
        }

        try {

            if (!tanksService.findByNameAndStation(model.getName(), model.getStation()).isPresent()) {
                TankDTO tank = new TankDTO();
                tank.setName(name);
                tank.setStation(station);
                tank.setCapacity(capacity);
                tank.setDateOfQtySet(LocalDateTime.now());
//                tank.setQty(model.getQty());
//                tank.setDateOfQtySet(LocalDateTime.now());
//                tank.setUserOfQtySet(model.getUserOfQtySet());
                tank.setMaterialID(model.getMaterialID());

                String save = tanksService.save(tank);

                if (save.equals("saved")) {
                    TanksView.showInformationWindow("Info", save);
                    update();

                } else {
                    TanksView.showErrorWindow("Error inserting data", save);
                }
                resetModel();
            } else {
                TanksView.showErrorWindow("Error inserting data", "Tank already exist , please check entered data");
            }


        } catch (Exception e) {
            TanksView.showErrorWindowForException("Error inserting data", e);
        }
    }

    @Async
    public void onUpdate(MouseEvent mouseEvent) {

        String name = model.getName();
        String station = model.getStation();
        Double capacity = model.getCapacity();
        long tankId = model.getTankId();

        if (tankId == 0) {
            TanksView.showWarningWindow("Missing Data", "Please select tank");
            return;
        }

        if (StringUtils.isBlank(name)) {
            TanksView.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(station)) {
            TanksView.showWarningWindow("Missing Data", "Please enter station");
            return;
        }
        if (capacity <= 0) {
            TanksView.showWarningWindow("Missing Data", "Please enter capacity");
            return;
        }

        try {

            if (tanksService.findById(tankId).isPresent()) {
                TankDTO tank = new TankDTO();
                tank.setId(tankId);
                tank.setName(name);
                tank.setStation(station);
                tank.setCapacity(capacity);
                tank.setDateOfQtySet(LocalDateTime.now());
//                tank.setQty(model.getQty());
//                tank.setDateOfQtySet(LocalDateTime.now());
//                tank.setUserOfQtySet(model.getUserOfQtySet());
                tank.setMaterialID(model.getMaterialID());

                String save = tanksService.save(tank);

                if (save.equals("saved")) {
                    TanksView.showInformationWindow("Info", save);
                    update();

                } else {
                    TanksView.showErrorWindow("Error updating data", save);
                }
                resetModel();
            } else {
                TanksView.showErrorWindow("Error inserting data", "tank doesn't exist , please check entered data");
            }


        } catch (Exception e) {
            TanksView.showErrorWindowForException("Error updating data", e);
        }
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {

        long id = model.getTankId();
        String deletedById = tanksService.deleteById(id);
        if (deletedById.equals("deleted")) {
            TanksView.showInformationWindow("Info", deletedById);
            update();

        } else {
            TanksView.showErrorWindow("Error deleting record", deletedById);
        }
        resetModel();
    }

    public synchronized Task<String> updateTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
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

    public void resetModel() {
        Platform.runLater(() -> {
            try {
                model.setTankId(0);
                model.setName("");
                model.setStation("");
                model.setCapacity(0.0);
                model.setMaterialID(0);
                model.setQty(0.0);
                model.setDateOfQtySet(null);
                model.setUserOfQtySet("");
                model.setCreatedBy((""));
                model.setCreationDate("");
                model.setModifyDate("");
                model.setOnTerminal("");

                materialsModel.setName("");
                materialsModel.setDescription("");

            } catch (Exception e) {
                log.fatal(e);
            }
        });
    }

    //    @Scheduled(fixedDelay = 20000)
    public void detailedUpdates(List<TankDTO> dataBaseList) {
        if (getDataList() != null) {

            final Map<Long, TankDTO> tankDTOMap = dataBaseList.stream().collect(Collectors.toMap(TankDTO::getId, Function.identity()));
            for (TanksModel.TableObject item : getDataList()) {
                try {
                    final TankDTO tank = tankDTOMap.get(item.getTankIdColumn());

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
                    item.setOnTerminalColumn(tank.getOnTerminal());
                    item.setCalculatedQtyColumn(tank.getCalculatedQty());

                } catch (Exception e) {
                    log.fatal(e);
                }
            }
        }
    }

    public void setProductData(MaterialsModel.TableObject selected) {

        materialsModel.setMaterialId(selected.getMaterialIdColumn());
        materialsModel.setName(selected.getNameColumn());
        materialsModel.setDescription(selected.getDescriptionColumn());

    }
}
