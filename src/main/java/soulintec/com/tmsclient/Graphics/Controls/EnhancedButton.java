package soulintec.com.tmsclient.Graphics.Controls;

import javafx.scene.control.Button;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ConfigurableApplicationContext;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.Authorization.AppUserDTO;
import soulintec.com.tmsclient.Entities.Authorization.RoleDTO;
import soulintec.com.tmsclient.Entities.Authorization.RoleRef;
import soulintec.com.tmsclient.Services.GeneralServices.LoggingService.LoginService;
import soulintec.com.tmsclient.Services.RoleRefService;
import soulintec.com.tmsclient.Services.RolesService;
import soulintec.com.tmsclient.Services.UserService;
import soulintec.com.tmsclient.UserObeserver.Observer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Log4j2
public class EnhancedButton extends Button implements Observer {

    private RoleDTO authority;
    private boolean authorized = false;

    private static List<AppUserDTO> users = new LinkedList<>();
    private static List<RoleDTO> roleDTOS = new LinkedList<>();
    private static List<RoleRef> roleRefs = new LinkedList<>();

    private static final ConfigurableApplicationContext applicationContext = ApplicationContext.applicationContext;
    private static final LoginService loginService = ApplicationContext.applicationContext.getBean(LoginService.class);
    private static final UserService userService = ApplicationContext.applicationContext.getBean(UserService.class);
    private static final RolesService rolesService = ApplicationContext.applicationContext.getBean(RolesService.class);
    private static final RoleRefService roleRef = ApplicationContext.applicationContext.getBean(RoleRefService.class);

    public EnhancedButton(String s) {
        super(s);
        if (users.isEmpty() || roleDTOS.isEmpty()) {
            users.addAll(userService.findAll());
            roleDTOS.addAll(rolesService.findAll());
            roleRefs.addAll(roleRef.findAll());
        }
    }

    public void setAuthority(RoleDTO authority) {
        this.authority = authority;
        loginService.addObserver(this);
        setDisable(authority != null);
    }

    @Override
    public void update(String username) {
        checkAuthority(username);
    }

    public void checkAuthority(String string) {
//        users.stream().filter(userObject -> userObject.getName().trim().equalsIgnoreCase(string.trim()))
//                .findFirst()
//                .ifPresentOrElse(userDTO -> {
//                            roleDTOS.stream().filter(roleDTO -> roleDTO.getName().equals(authority.getName())).findFirst().ifPresentOrElse(roleDTO -> {
//                                        RoleRef roleRef = new RoleRef(userDTO.getUserId(), roleDTO.getId());
//                                        authorized = roleRefs.stream().filter(roleRef1 -> roleRef1.getUserId() == userDTO.getUserId()).collect(Collectors.toList()).contains(roleRef);
//                                    }
//                                    , () -> {
//                                        authorized = false;
//                                    });
//                        }
//                        , () -> {
//                            authorized = false;
//                        });
//        setDisable(!authorized);

        users.stream().filter(userObject -> userObject.getName().trim().equalsIgnoreCase(string.trim()))
                .findFirst()
                .ifPresentOrElse(userDTO -> {
                            roleDTOS.stream().filter(roleDTO -> roleDTO.getName().equals(authority.getName()))
                                    .findFirst()
                                    .ifPresentOrElse(role -> {
                                        roleRefs.stream().filter(ref -> ref.getUserId() == userDTO.getUserId()).forEach(item -> {
                                            if (item.getRoleId() == role.getId()) {
                                                authorized = true;
                                            }
                                        });
                                    }, () -> {
                                        authorized = false;
                                    });
                        }
                        , () -> {
                            authorized = false;
                        });
        setDisable(!authorized);

    }
}


