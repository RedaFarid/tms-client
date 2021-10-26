package soulintec.com.tmsclient.Graphics.Windows.AuthorizationView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.Authorization.AppUserDTO;
import soulintec.com.tmsclient.Entities.Authorization.RoleDTO;
import soulintec.com.tmsclient.Entities.Authorization.RoleRef;
import soulintec.com.tmsclient.Graphics.Windows.AuthorizationView.AuthoraizationWindow.AuthorizationModel;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Services.RoleRefService;
import soulintec.com.tmsclient.Services.RolesService;
import soulintec.com.tmsclient.Services.UserService;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Component
@Log4j2
public class UserAuthorizationController {

    @Autowired
    UserService userService;
    @Autowired
    RolesService rolesService;
    @Autowired
    RoleRefService roleRefService;
    @Autowired
    private AuthorizationModel authorizationModel;

    private final UsersModel model = new UsersModel();

    private final ObservableList<UsersModel.UsersTableObject> usersObservableList = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
    private final ObservableList<String> rolesObservableList = FXCollections.observableArrayList();
    private final ObservableList<AuthorizationModel.TableObject> authorizationsList = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
    private final ObservableList<AuthorizationModel.SecondTableObject> addAuthorizationsList = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

//    private List<RoleDTO> authorityDTOs = new LinkedList<>();
//    private List<AppUserDTO> appUserDTOS = new LinkedList<>();
//    private List<RoleRef> roleRefs = new LinkedList<>();

    private int addSelected = 0;
    private int selected = 0;

//    public void updateDTOs() {
//        appUserDTOS.clear();
//        authorityDTOs.clear();
//        roleRefs.clear();
//
//        appUserDTOS.addAll(userService.findAll());
//        authorityDTOs.addAll(authoritiesDAO.findAll());
//        roleRefs.addAll(roleRefService.findAll());
//    }

    public ObservableList<UsersModel.UsersTableObject> getUsersList() {
        return usersObservableList;
    }

    public ObservableList<String> getRolesList() {
        return rolesObservableList;
    }

    private void addUser(UsersModel.UsersTableObject user) {
        AppUserDTO userDTO = new AppUserDTO(user.getUserName(), user.getPassword());
        userService.save(userDTO);
        updateUsersList();
    }

    @Async
    public void deleteUser(UsersModel.UsersTableObject user) {
        userService.deleteById(user.getId());
        updateUsersList();
    }

    @Async
    public void updateUsersList() {
//        updateDTOs();
        final List<UsersModel.UsersTableObject> usersTableObjects = userService.findAll().stream().map(UsersModel.UsersTableObject::CreateFromUserDTO).collect(Collectors.toList());
        final List<UsersModel.UsersTableObject> addedUsers;
        final List<UsersModel.UsersTableObject> removedUsers;

        addedUsers = usersTableObjects
                .stream()
                .filter(usersTableObject -> !getUsersList().contains(usersTableObject))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        removedUsers = getUsersList()
                .stream()
                .filter(usersTableObject ->
                        usersTableObjects
                                .stream()
                                .noneMatch(usersTableObject::equals))
                .collect(Collectors.toList());

        Platform.runLater(() -> {
            getUsersList().removeAll(removedUsers);
            getUsersList().addAll(addedUsers);
            UsersModel.UsersTableObject newUser = new UsersModel.UsersTableObject(0L, "new user", "");
            if (!getUsersList().contains(newUser)) {
                getUsersList().add(newUser);
            }
        });
    }

    @Async
    public void updateUserData(String userName, UsersModel.UsersTableObject rowValue) {
        if (userName.equals("new user")) {
            addUser(rowValue);
        } else {
            AppUserDTO appUserDTO = new AppUserDTO(rowValue.getId(), rowValue.getUserName(), rowValue.getPassword());
            userService.updateUsername(appUserDTO);
            updateUsersList();
        }
    }

    @Async
    public void onPasswordChanged(UsersModel.UsersTableObject rowValue) {
        AppUserDTO appUserDTO = new AppUserDTO(rowValue.getId(), rowValue.getUserName(), rowValue.getPassword());
        userService.save(appUserDTO);
        updateUsersList();
    }

    public UsersModel getModel() {
        return model;
    }

    //-------------Down Window-----------------\\
    public ObservableList<AuthorizationModel.SecondTableObject> getAddAuthorizationList() {
        return addAuthorizationsList;
    }


    public ObservableList<AuthorizationModel.TableObject> getAuthorizationList() {
        return authorizationsList;
    }

    public void onAddTableSelection(ObservableList<AuthorizationModel.SecondTableObject> selectedItems) {
        if (selectedItems.size() != 0) {
            authorizationModel.setAddAuthority(selectedItems.get(0).getAddAuthorityColumn());
        }
    }

    @Async
    public void updateAddAuthoritiesList() {
        final List<String> list = rolesService.findAll().stream()
                .filter(authorityDTO -> !authorizationsList.contains(AuthorizationModel.TableObject.createFromAuthorization(model.getId(), authorityDTO)))
                .map(authorityDTO -> authorityDTO.getName()).collect(Collectors.toList());

        Platform.runLater(() -> {
            addAuthorizationsList.clear();
            list.stream().distinct().forEach(item -> addAuthorizationsList.add(AuthorizationModel.SecondTableObject.createFromAddAuthorization(item)));
        });
    }

    public void selectItem(TableView<AuthorizationModel.TableObject> table) {
        if (selected < getAuthorizationList().size())
            table.getSelectionModel().select(selected);
        else
            table.getSelectionModel().select(selected - 1);
    }

    public void setSelectedItem(int selectedIndex) {
        if (selectedIndex > -1)
            selected = selectedIndex;
    }

    public void onAuthTableSelection(ObservableList<AuthorizationModel.TableObject> selectedItems) {
        if (selectedItems.size() > 0) {
            AuthorizationModel.TableObject tableObject = selectedItems.get(0);
            authorizationModel.setId(tableObject.getIdColumn());
            authorizationModel.setName(tableObject.getNameColumn());
        }
    }

    public void selectAddItem(TableView<AuthorizationModel.SecondTableObject> addTable) {
        if (addSelected < getAddAuthorizationList().size())
            addTable.getSelectionModel().select(addSelected);
        else
            addTable.getSelectionModel().select(addSelected - 1);
    }

    public void setAddSelectedItem(int selectedIndex) {
        if (selectedIndex > -1)
            addSelected = selectedIndex;
    }

    @Async
    public void addAuth() {
        if (authorizationModel.getAddAuthority() != null) {
            final AuthorizationModel.TableObject rowValue = new AuthorizationModel.TableObject(0L, authorizationModel.getAddAuthority(), authorizationModel.getUserId());
            final AuthorizationModel.SecondTableObject removedRowValue = new AuthorizationModel.SecondTableObject(authorizationModel.getAddAuthority());
            final String authName = authorizationModel.getAddAuthority();
            rolesService.findAll().stream().filter(authorityDTO -> authorityDTO.getName().equals(authName)
            ).findFirst().ifPresent(o -> rowValue.setIdColumn(o.getId()));
            Platform.runLater(() -> {
                authorizationsList.add(rowValue);
                addAuthorizationsList.remove(removedRowValue);
            });
            saveAuth(rowValue.getIdColumn(), model.getId());
        }
    }

    private void saveAuth(long authorizationIdColumn, long userId) {
       roleRefService.save(new RoleRef(authorizationIdColumn, userId));

        if (getAddAuthorizationList().size() == 0)
            authorizationModel.setAddAuthority(null);
    }

    public void removeAuthority() {
        if (authorizationModel.getId() != 0) {
            roleRefService.deleteByRoleRef(new RoleRef(authorizationModel.getId(),model.getId()));
            authorizationsList.stream().filter(tableObject -> tableObject.getIdColumn() == authorizationModel.getId())
                    .findFirst().ifPresent(authorizationsList::remove);
            if (getAuthorizationList().size() == 0)
                authorizationModel.setId(0);
            updateAddAuthoritiesList();
        }
    }

    public AuthorizationModel getAuthModel() {
        return authorizationModel;
    }

    public void onTableSelection(ObservableList<UsersModel.UsersTableObject> list) {
        if (list.size() > 0) {
            UsersModel.UsersTableObject tableObject = list.get(0);
            if (!tableObject.getUserName().equals("new user")) {
                userService.findById(tableObject.getId()).ifPresentOrElse(userDTO -> {
                    updateModel(tableObject);
                    updateAddAuthoritiesList();
                }, () -> {
                    MainWindow.showErrorWindow("Data doesn't exist", "Error getting data for selected user");
                });
            }
        }
    }

    private void updateModel(UsersModel.UsersTableObject tableObject) {
        try {
            model.setId(tableObject.getId());
            model.setUserName(tableObject.getUserName());
            model.setPassword(tableObject.getPassword());
            authorizationsList.clear();
            authorizationsList.setAll(getUserAuthorizations(model));

        } catch (
                Exception e) {
            log.fatal(e);
        }
    }

    public List<AuthorizationModel.TableObject> getUserAuthorizations(UsersModel model) {
        final List<AuthorizationModel.TableObject> list = new LinkedList<>();
        roleRefService.findAll().stream().filter(item -> model.getId() == item.getUserId()).forEach(i -> {
            List<AuthorizationModel.TableObject> collect = rolesService.findAll().stream().filter(auth -> auth.getId() == i.getRoleId())
                    .map((RoleDTO authorityDTO) -> AuthorizationModel.TableObject.createFromAuthorization(model.getId(), authorityDTO))
                    .collect(Collectors.toList());
            list.addAll(collect);
        });
        return list;
    }

}
