package soulintec.com.tmsclient.Graphics.Windows.AuthorizationView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import soulintec.com.tmsclient.Entities.Authorization.AppUserDTO;
import soulintec.com.tmsclient.Services.RoleRefService;
import soulintec.com.tmsclient.Services.RolesService;
import soulintec.com.tmsclient.Services.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Component
@Log4j2
public class UserAuthorizationController {

    @Autowired
    UserService userService;
    @Autowired
    RolesService authoritiesDAO;
    @Autowired
    RoleRefService roleRefService;

    private final UsersModel model = new UsersModel();

    private final ObservableList<UsersModel.UsersTableObject> usersObservableList = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
    private final ObservableList<String> rolesObservableList = FXCollections.observableArrayList();

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
}
