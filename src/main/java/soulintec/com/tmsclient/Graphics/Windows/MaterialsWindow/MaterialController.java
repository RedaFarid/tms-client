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
import soulintec.com.tmsclient.Entities.MaterialDTO;
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
                MaterialsWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected material");
            });
        }
    }

    @Async
    public void onInsert(MouseEvent mouseEvent) {

        String name = model.getName();
        String desc = model.getDescription();

        if (StringUtils.isBlank(name)) {
            MaterialsWindow.showWarningWindow("Missing Data", "Please enter material");
            return;
        }

        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setName(name);
        materialDTO.setDescription(desc);

        String save = materialService.save(materialDTO);

        if (save == null) {
            MaterialsWindow.showErrorWindow("Error", "Error saving new material");
        }
        updateDataList();
        resetModel();
    }

    @Async
    public void onUpdate(MouseEvent mouseEvent) {
        String name = model.getName();
        String desc = model.getDescription();
        Long id = model.getMaterialId();

        if (id == 0) {
            MaterialsWindow.showWarningWindow("Missing Data", "Please select material");
            return;
        }

        if (StringUtils.isBlank(name)) {
            MaterialsWindow.showWarningWindow("Missing Data", "Please enter material");
            return;
        }

        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setId(id);
        materialDTO.setName(name);
        materialDTO.setDescription(desc);

        String save = materialService.save(materialDTO);

        if (save == null) {
            MaterialsWindow.showErrorWindow("Error", "Error saving new material");
        }
        updateDataList();
        resetModel();
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {
        long materialId = model.getMaterialId();
        String deletedById = materialService.deleteById(materialId);
        if (deletedById == null) {
            MaterialsWindow.showErrorWindow("Error", "Error deleting selected material");
        }
        updateDataList();
        resetModel();
    }

    @Async
    public void updateDataList() {
        List<MaterialDTO> list = materialService.findAll();
        getDataList().removeAll(list.stream().map(MaterialsModel.TableObject::createFromMaterialDTO)
                .filter(item -> !tableList.contains(item))
                .collect(this::getDataList, ObservableList::add, ObservableList::addAll)
                .stream()
                .filter(tableListItem -> list.stream().map(MaterialsModel.TableObject::createFromMaterialDTO)
                        .noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem))).sorted()
                .collect(Collectors.toList()));

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

}

