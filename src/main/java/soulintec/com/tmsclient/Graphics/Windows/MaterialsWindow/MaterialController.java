package soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow;

import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.MaterialDTO;
import soulintec.com.tmsclient.Entities.StationDTO;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.StationsWindow.StationsModel;
import soulintec.com.tmsclient.Services.LogsService;
import soulintec.com.tmsclient.Services.MaterialService;
import soulintec.com.tmsclient.Services.TransactionService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class MaterialController {
    private final MaterialsModel model = new MaterialsModel();
    private final ObservableList<MaterialsModel.TableObject> tableList = FXCollections.observableArrayList();

    @Autowired(required = false)
    private Executor executor;

    @Autowired
    private MaterialService materialService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private LogsService logsService;

    public MaterialsModel getModel() {
        return model;
    }

    public ObservableList<MaterialsModel.TableObject> getDataList() {
        return tableList;
    }

    public void onTableSelection(ObservableList<? extends MaterialsModel.TableObject> list) {
        if (list.size() > 0) {
            MaterialsModel.TableObject tableObject = list.get(0);
            materialService.findById(tableObject.getMaterialIdColumn()).ifPresentOrElse(materialDTO -> {
                model.setMaterialId(materialDTO.getId());
                model.setName(materialDTO.getName());
                model.setDescription(materialDTO.getDescription());
                model.setCreatedBy(tableObject.getCreatedByColumn());
                model.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                model.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                model.setOnTerminal(tableObject.getOnTerminalColumn());
            }, () -> {
//                logsService.save(new LogDTO(LogIdentifier.Error, toString(), " Error getting material data"));
                MainWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected material");
            });
        }
    }

    @Async
    public void onInsert(MouseEvent mouseEvent) {

        String name = model.getName();
        String desc = model.getDescription();

        if (StringUtils.isBlank(name)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter material");
            return;
        }
        if (!materialService.findByName(name).isPresent()) {
            MaterialDTO materialDTO = new MaterialDTO();
            materialDTO.setName(name);
            materialDTO.setDescription(desc);

            String save = materialService.save(materialDTO);

            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Inserting new material : " + name));
                MainWindow.showInformationWindow("Info", save);
                update();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MainWindow.showErrorWindow("Error inserting data", save);
            }
            resetModel();
        } else {
            MainWindow.showErrorWindow("Error inserting data", "Material already exist , please check entered data");
        }
    }

    @Async
    public void onUpdate(MouseEvent mouseEvent) {
        Long id = model.getMaterialId();
        String name = model.getName();
        String desc = model.getDescription();

        if (id == 0) {
            MainWindow.showWarningWindow("Missing Data", "Please select material");
            return;
        }

        if (StringUtils.isBlank(name)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter material");
            return;
        }
        if (materialService.findById(id).isPresent()) {
            MaterialDTO materialDTO = new MaterialDTO();
            materialDTO.setId(id);
            materialDTO.setName(name);
            materialDTO.setDescription(desc);

            String save = materialService.save(materialDTO);

            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Updating data for material :  " + name));
                MainWindow.showInformationWindow("Info", save);
                update();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MainWindow.showErrorWindow("Error updating data", save);
            }
            resetModel();
        } else {
            MainWindow.showErrorWindow("Error updating data", "Material doesn't exist , please check entered data");
        }
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {
        long materialId = model.getMaterialId();
        if (transactionService.findAll().stream().filter(i -> i.getMaterial() == materialId).count() != 0) {
            logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Material :  " + model.getName() + " can't be deleted because there are transactions relate to it "));
            MainWindow.showErrorWindow("Error deleting record", "Material can't be deleted because there are transactions relate to it \n please delete related transactions first");
        } else {
            String deletedById = materialService.deleteById(materialId);
            if (deletedById.equals("deleted")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Deleting material : " + model.getName()));
                MainWindow.showInformationWindow("Info", deletedById);
                update();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), deletedById));
                MainWindow.showErrorWindow("Error deleting record", deletedById);
            }
            resetModel();
        }
    }

    public void resetModel() {
        Platform.runLater(() -> {
            try {
                model.setMaterialId(0);
                model.setName("");
                model.setDescription("");
                model.setCreatedBy((""));
                model.setCreationDate("");
                model.setModifyDate("");
                model.setOnTerminal("");
            } catch (Exception e) {
                log.fatal(e);
            }
        });
    }

    public synchronized Task<String> updateTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                List<MaterialDTO> dataBaseList = Lists.newArrayList(materialService.findAll());

                Platform.runLater(() -> {
                    getDataList().removeAll(
                            dataBaseList
                                    .stream()
                                    .map(MaterialsModel.TableObject::createFromMaterialDTO)
                                    .filter(item -> !getDataList().contains(item))
                                    .collect(() -> getDataList(), ObservableList::add, ObservableList::addAll)
                                    .stream()
                                    .filter(tableListItem -> dataBaseList.stream().map(MaterialsModel.TableObject::createFromMaterialDTO).noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
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

    public void detailedUpdates(List<MaterialDTO> dataBaseList) {
        if (getDataList() != null) {

            final Map<Long, MaterialDTO> materialDTOMap = dataBaseList.stream().collect(Collectors.toMap(MaterialDTO::getId, Function.identity()));
            for (MaterialsModel.TableObject item : getDataList()) {
                try {
                    final MaterialDTO materialDTO = materialDTOMap.get(item.getMaterialIdColumn());
                    if (materialDTO != null) {
                        item.setNameColumn(materialDTO.getName());
                        item.setDescriptionColumn(materialDTO.getDescription());
                        item.setCreationDateColumn(materialDTO.getCreationDate());
                        item.setModifyDateColumn(materialDTO.getModifyDate());
                        item.setCreatedByColumn(materialDTO.getCreatedBy());
                        item.setOnTerminalColumn(materialDTO.getOnTerminal());
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
        return "Materials";
    }
}

