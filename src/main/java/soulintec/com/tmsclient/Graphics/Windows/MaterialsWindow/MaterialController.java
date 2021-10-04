package soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.MaterialDTO;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Services.LogsService;
import soulintec.com.tmsclient.Services.MaterialService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class MaterialController {
    private final MaterialsModel model = new MaterialsModel();
    private final ObservableList<MaterialsModel.TableObject> tableList = FXCollections.observableArrayList();

    @Autowired
    private MaterialService materialService;

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
                MaterialsView.showErrorWindow("Data doesn't exist", "Error getting data for selected material");
            });
        }
    }

    @Async
    public void onInsert(MouseEvent mouseEvent) {

        String name = model.getName();
        String desc = model.getDescription();

        if (StringUtils.isBlank(name)) {
            MaterialsView.showWarningWindow("Missing Data", "Please enter material");
            return;
        }
        if (!materialService.findByName(name).isPresent()) {
            MaterialDTO materialDTO = new MaterialDTO();
            materialDTO.setName(name);
            materialDTO.setDescription(desc);

            String save = materialService.save(materialDTO);

            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Inserting material data"));
                MaterialsView.showInformationWindow("Info", save);
                updateDataList();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MaterialsView.showErrorWindow("Error inserting data", save);
            }
            resetModel();
        } else {
            MaterialsView.showErrorWindow("Error inserting data", "Material already exist , please check entered data");
        }
    }

    @Async
    public void onUpdate(MouseEvent mouseEvent) {
        Long id = model.getMaterialId();
        String name = model.getName();
        String desc = model.getDescription();

        if (id == 0) {
            MaterialsView.showWarningWindow("Missing Data", "Please select material");
            return;
        }

        if (StringUtils.isBlank(name)) {
            MaterialsView.showWarningWindow("Missing Data", "Please enter material");
            return;
        }
        if (materialService.findById(id).isPresent()) {
            MaterialDTO materialDTO = new MaterialDTO();
            materialDTO.setId(id);
            materialDTO.setName(name);
            materialDTO.setDescription(desc);

            String save = materialService.save(materialDTO);

            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Updating material data"));
                MaterialsView.showInformationWindow("Info", save);
                updateDataList();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MaterialsView.showErrorWindow("Error updating data", save);
            }
            resetModel();
        } else {
            MaterialsView.showErrorWindow("Error updating data", "Material doesn't exist , please check entered data");
        }
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {
        long materialId = model.getMaterialId();
        String deletedById = materialService.deleteById(materialId);
        if (deletedById.equals("deleted")) {
            logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Deleting material data"));
            MaterialsView.showInformationWindow("Info", deletedById);
            updateDataList();

        } else {
            logsService.save(new LogDTO(LogIdentifier.Error, toString(), deletedById));
            MaterialsView.showErrorWindow("Error deleting record", deletedById);
        }
        resetModel();
    }

    @Async
    public void updateDataList() {
        List<MaterialDTO> list = materialService.findAll();
        if (list != null) {
            getDataList().removeAll(list.stream().map(MaterialsModel.TableObject::createFromMaterialDTO)
                    .filter(item -> !tableList.contains(item))
                    .collect(this::getDataList, ObservableList::add, ObservableList::addAll)
                    .stream()
                    .filter(tableListItem -> list.stream().map(MaterialsModel.TableObject::createFromMaterialDTO)
                            .noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem))).sorted()
                    .collect(Collectors.toList()));
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

    @Override
    public String toString() {
        return "Materials";
    }
}

