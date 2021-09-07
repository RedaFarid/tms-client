package soulintec.com.tmsclient.Graphics.Windows.ClientsWindow;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.ClientDTO;
import soulintec.com.tmsclient.Graphics.Windows.ClientWindow;
import soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow.MaterialsWindow;
import soulintec.com.tmsclient.Services.ClientsService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class ClientsController {

    private final ClientsModel model = new ClientsModel();
    private final ObservableList<ClientsModel.TableObject> tableList = FXCollections.observableArrayList();

    @Autowired
    private ClientsService clientsService;

    public ClientsModel getModel() {
        return model;
    }

    public ObservableList<ClientsModel.TableObject> getDataList() {
        return tableList;
    }

    public void onTableSelection(ObservableList<? extends ClientsModel.TableObject> list) {
        if (list.size() > 0) {
            ClientsModel.TableObject tableObject = list.get(0);
            clientsService.findById(tableObject.getClientIdColumn()).ifPresentOrElse(clientDTO -> {
                model.setClientId(clientDTO.getId());
                model.setName(clientDTO.getName());
                model.setContactEmail(clientDTO.getContactEmail());
                model.setMainOfficeAddress(clientDTO.getMainOfficeAddress());
                model.setContactTelNumber(clientDTO.getContactTelNumber());
                model.setContactName(clientDTO.getContactName());
                model.setCreatedBy(tableObject.getCreatedByColumn());
                model.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                model.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                model.setOnTerminal(tableObject.getOnTerminalColumn());
            }, () -> {
                ClientWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected client");
            });
        }
    }

    @Async
    public void onInsert(MouseEvent mouseEvent) {

        String name = model.getName();
        String contactEmail = model.getContactEmail();
        String mainOffice = model.getMainOfficeAddress();
        String contactTel = model.getContactTelNumber();
        String contactName = model.getContactName();

        if (StringUtils.isBlank(name)) {
            ClientWindow.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(contactEmail)) {
            ClientWindow.showWarningWindow("Missing Data", "Please enter email");
            return;
        }
        if (StringUtils.isBlank(mainOffice)) {
            ClientWindow.showWarningWindow("Missing Data", "Please enter main office");
            return;
        }
        if (StringUtils.isBlank(contactTel)) {
            ClientWindow.showWarningWindow("Missing Data", "Please enter phone number");
            return;
        }
        if (StringUtils.isBlank(contactName)) {
            ClientWindow.showWarningWindow("Missing Data", "Please enter  contact name");
            return;
        }

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(name);
        clientDTO.setContactName(contactName);
        clientDTO.setContactTelNumber(contactTel);
        clientDTO.setContactEmail(contactEmail);
        clientDTO.setMainOfficeAddress(mainOffice);

        String save = clientsService.save(clientDTO);

        if (save == null) {
            MaterialsWindow.showErrorWindow("Error", "Error saving new client");
        }
        updateDataList();
        resetModel();
    }

    @Async
    public void onUpdate(MouseEvent mouseEvent) {
        Long id = model.getClientId();
        String name = model.getName();
        String contactEmail = model.getContactEmail();
        String mainOffice = model.getMainOfficeAddress();
        String contactTel = model.getContactTelNumber();
        String contactName = model.getContactName();

        if (id == 0) {
            ClientWindow.showWarningWindow("Missing Data", "Please select client");
            return;
        }

        if (StringUtils.isBlank(name)) {
            ClientWindow.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(contactEmail)) {
            ClientWindow.showWarningWindow("Missing Data", "Please enter email");
            return;
        }
        if (StringUtils.isBlank(mainOffice)) {
            ClientWindow.showWarningWindow("Missing Data", "Please enter main office");
            return;
        }
        if (StringUtils.isBlank(contactTel)) {
            ClientWindow.showWarningWindow("Missing Data", "Please enter phone number");
            return;
        }
        if (StringUtils.isBlank(contactName)) {
            ClientWindow.showWarningWindow("Missing Data", "Please enter  contact name");
            return;
        }

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(id);
        clientDTO.setName(name);
        clientDTO.setContactName(contactName);
        clientDTO.setContactTelNumber(contactTel);
        clientDTO.setContactEmail(contactEmail);
        clientDTO.setMainOfficeAddress(mainOffice);

        String save = clientsService.save(clientDTO);

        if (save == null) {
            MaterialsWindow.showErrorWindow("Error", "Error updating client");
        }
        updateDataList();
        resetModel();
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {
        long id = model.getClientId();
        String deletedById = clientsService.deleteById(id);
        if (deletedById == null) {
            MaterialsWindow.showErrorWindow("Error", "Error deleting selected client");
        }
        updateDataList();
        resetModel();
    }

    @Async
    public void updateDataList() {
        List<ClientDTO> list = clientsService.findAll();
        getDataList().removeAll(list.stream().map(ClientsModel.TableObject::createFromClientDTO)
                .filter(item -> !tableList.contains(item))
                .collect(this::getDataList, ObservableList::add, ObservableList::addAll)
                .stream()
                .filter(tableListItem -> list.stream().map(ClientsModel.TableObject::createFromClientDTO)
                        .noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem))).sorted()
                .collect(Collectors.toList()));
    }

    public void resetModel() {
        Platform.runLater(() -> {
            try {
                model.setClientId(0);
                model.setName("");
                model.setMainOfficeAddress("");
                model.setContactTelNumber("");
                model.setContactEmail("");
                model.setContactName("");
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
