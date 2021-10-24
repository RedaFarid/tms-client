package soulintec.com.tmsclient.Graphics.Windows.ClientsWindow;

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
import soulintec.com.tmsclient.Entities.ClientDTO;
import soulintec.com.tmsclient.Entities.LogDTO;
import soulintec.com.tmsclient.Entities.StationDTO;
import soulintec.com.tmsclient.Graphics.Windows.DriversWindow.DriversView;
import soulintec.com.tmsclient.Graphics.Windows.LogsWindow.LogIdentifier;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.StationsWindow.StationsModel;
import soulintec.com.tmsclient.Services.ClientsService;
import soulintec.com.tmsclient.Services.LogsService;
import soulintec.com.tmsclient.Services.TransactionService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class ClientsController {

    private final ClientsModel model = new ClientsModel();
    private final ObservableList<ClientsModel.TableObject> tableList = FXCollections.observableArrayList();

    @Autowired
    private ClientsService clientsService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private LogsService logsService;

    @Autowired(required = false)
    private Executor executor;

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
                model.setContactTelNumber(clientDTO.getContactTelNumber());
                model.setMainOfficeAddress(clientDTO.getMainOfficeAddress());
                model.setContactTelNumber(clientDTO.getContactTelNumber());
                model.setContactName(clientDTO.getContactName());
                model.setCreatedBy(tableObject.getCreatedByColumn());
                model.setCreationDate(String.valueOf(tableObject.getCreationDateColumn()));
                model.setModifyDate(String.valueOf(tableObject.getModifyDateColumn()));
                model.setOnTerminal(tableObject.getOnTerminalColumn());
                model.setLastModifiedBy(tableObject.getLastModifiedByColumn());
            }, () -> {
//                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Error getting client data"));
                MainWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected client");
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
            MainWindow.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(contactEmail)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter email");
            return;
        }
        if (StringUtils.isBlank(mainOffice)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter main office");
            return;
        }
        if (StringUtils.isBlank(contactTel)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter phone number");
            return;
        }
        if (StringUtils.isBlank(contactName)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter  contact name");
            return;
        }

        if (!clientsService.findByName(name).isPresent()) {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setName(name);
            clientDTO.setContactName(contactName);
            clientDTO.setContactTelNumber(contactTel);
            clientDTO.setContactEmail(contactEmail);
            clientDTO.setMainOfficeAddress(mainOffice);

            String save = clientsService.save(clientDTO);
            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Inserting new client :  " + name));
                MainWindow.showInformationWindow("Info", save);
                update();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MainWindow.showErrorWindow("Error inserting data", save);
            }
            resetModel();
        } else {
            MainWindow.showErrorWindow("Error inserting data", "Client already exist , please check entered data");
        }
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
            MainWindow.showWarningWindow("Missing Data", "Please select client");
            return;
        }

        if (StringUtils.isBlank(name)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter name");
            return;
        }
        if (StringUtils.isBlank(contactEmail)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter email");
            return;
        }
        if (StringUtils.isBlank(mainOffice)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter main office");
            return;
        }
        if (StringUtils.isBlank(contactTel)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter phone number");
            return;
        }
        if (StringUtils.isBlank(contactName)) {
            MainWindow.showWarningWindow("Missing Data", "Please enter  contact name");
            return;
        }

        if (clientsService.findById(id).isPresent()) {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(id);
            clientDTO.setName(name);
            clientDTO.setContactName(contactName);
            clientDTO.setContactTelNumber(contactTel);
            clientDTO.setContactEmail(contactEmail);
            clientDTO.setMainOfficeAddress(mainOffice);

            String save = clientsService.save(clientDTO);
            if (save.equals("saved")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Updating data for client : " + name));
                MainWindow.showInformationWindow("Info", save);
                update();

            } else {
                logsService.save(new LogDTO(LogIdentifier.Error, toString(), save));
                MainWindow.showErrorWindow("Error updating data", save);
            }
            resetModel();
        } else {
            MainWindow.showErrorWindow("Error updating data", "Client doesn't exist , please check entered data");
        }
    }

    @Async
    public void onDelete(MouseEvent mouseEvent) {

        long id = model.getClientId();
        if (transactionService.findAll().stream().filter(i -> i.getClient() == id).count() != 0) {
            logsService.save(new LogDTO(LogIdentifier.Error, toString(), "Client :  " + model.getName() + " can't be deleted because there are transactions relate to it "));
            MainWindow.showErrorWindow("Error deleting record", "Client can't be deleted because there are transactions relate to it \n please delete related transactions first");
        } else {
            String deletedById = clientsService.deleteById(id);
            if (deletedById.equals("deleted")) {
                logsService.save(new LogDTO(LogIdentifier.Info, toString(), "Deleting client : " + model.getName()));
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
                model.setLastModifiedBy("");
            } catch (Exception e) {
                log.fatal(e);
            }
        });
    }

    public synchronized Task<String> updateTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                List<ClientDTO> dataBaseList = Lists.newArrayList(clientsService.findAll());

                Platform.runLater(() -> {
                    getDataList().removeAll(
                            dataBaseList
                                    .stream()
                                    .map(ClientsModel.TableObject::createFromClientDTO)
                                    .filter(item -> !getDataList().contains(item))
                                    .collect(() -> getDataList(), ObservableList::add, ObservableList::addAll)
                                    .stream()
                                    .filter(tableListItem -> dataBaseList.stream().map(ClientsModel.TableObject::createFromClientDTO).noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
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


    public void detailedUpdates(List<ClientDTO> dataBaseList) {
        if (getDataList() != null) {

            final Map<Long, ClientDTO> clientDTOMap = dataBaseList.stream().collect(Collectors.toMap(ClientDTO::getId, Function.identity()));
            for (ClientsModel.TableObject item : getDataList()) {
                try {
                    final ClientDTO clientDTO = clientDTOMap.get(item.getClientIdColumn());
                    if (clientDTO != null) {
                        item.setNameColumn(clientDTO.getName());
                        item.setMainOfficeAddressColumn(clientDTO.getMainOfficeAddress());
                        item.setContactNameColumn(clientDTO.getContactName());
                        item.setContactTelNumberColumn(clientDTO.getContactTelNumber());
                        item.setContactEmailColumn(clientDTO.getContactEmail());
                        item.setCreationDateColumn(clientDTO.getCreationDate());
                        item.setModifyDateColumn(clientDTO.getModifyDate());
                        item.setCreatedByColumn(clientDTO.getCreatedBy());
                        item.setOnTerminalColumn(clientDTO.getOnTerminal());
                        item.setLastModifiedByColumn(clientDTO.getLastModifiedBy());
                    }

                } catch (Exception e) {
                    logsService.save(new LogDTO(LogIdentifier.Error, toString(), e.getMessage()));
                    log.fatal(e);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Clients";
    }
}
