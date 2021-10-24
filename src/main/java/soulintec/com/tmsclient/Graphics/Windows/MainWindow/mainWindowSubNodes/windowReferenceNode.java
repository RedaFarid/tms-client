package soulintec.com.tmsclient.Graphics.Windows.MainWindow.mainWindowSubNodes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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
import java.util.function.Consumer;

public class windowReferenceNode extends HBox implements Observer {

    private boolean userControl = false;
    private StringProperty windowInterface = new SimpleStringProperty();
    private String windowIconRef;
    private String labelName;
    private BooleanProperty iconic = new SimpleBooleanProperty();

    final private double HEIGHT = 45;
    final private double WIDTH = 300;

    private StringProperty tempStringProperty;

    private ImageView icon = new ImageView();
    private ImageView status = new ImageView();

    private Consumer<String> callback = null;

    private Label label = new Label();

    private Background normalBackground = new Background(new BackgroundFill(Color.CADETBLUE, new CornerRadii(0), Insets.EMPTY));
    private Background logoffBackground = new Background(new BackgroundFill(Color.CADETBLUE, new CornerRadii(0.1), Insets.EMPTY));
    private Background activeBackground = new Background(new BackgroundFill(Color.ORANGERED, new CornerRadii(0), Insets.EMPTY));
    private Background focusedBackground = new Background(new BackgroundFill(Color.ORANGE, new CornerRadii(0), Insets.EMPTY));

    private RoleDTO authority;
    private boolean authorized = false;

    private List<AppUserDTO> users = new LinkedList<>();
    private List<RoleDTO> roleDTOS = new LinkedList<>();
    private List<RoleRef> roleRefs = new LinkedList<>();

    private LoginService loginService;
    private UserService userService;
    private RolesService rolesService;
    private RoleRefService roleRef;

    public windowReferenceNode(String windowIconRef, String labelName, StringProperty tempStringProperty) {
        this.tempStringProperty = tempStringProperty;
        this.windowIconRef = windowIconRef;
        this.labelName = labelName;
        iconic.setValue(true);
        graphicsBuilder();
    }

    private void graphicsBuilder() {
        setSpacing(20);
        setPadding(new Insets(5, 10, 5, 10));
        setPrefHeight(HEIGHT);
        setBackground(logoffBackground);

        if (windowIconRef != null) {
            Image image = new Image(windowIconRef);
            icon.setImage(image);
        }
        icon.setFitWidth(35);
        icon.setFitHeight(35);
        getChildren().add(icon);

        label.setText(labelName);
        label.setAlignment(Pos.CENTER);
        label.setId("buttnrefrence");
        label.prefHeightProperty().bind(this.heightProperty());
        label.prefWidth(250);
        label.minWidth(250);
        label.maxWidth(250);

        iconic.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                getChildren().remove(label);
            } else {
                getChildren().add(label);
            }
        });
        this.setOnMouseEntered(a -> {
            if (userControl) {
                this.setCursor(Cursor.HAND);
                label.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:black;-fx-font-size:18;");
                if (!this.getBackground().equals(activeBackground)) {
                    setBackground(focusedBackground);
                }
            }
        });
        this.setOnMouseExited(a -> {
            this.setCursor(Cursor.DEFAULT);
            if (userControl) {
                if (!this.getBackground().equals(activeBackground)) {
                    label.setStyle("-fx-font-weight:normal;-fx-font-style:normal;-fx-text-fill:black;-fx-font-size:18;");
                    setBackground(normalBackground);
                }
            }
        });
        this.setOnMouseClicked(a -> {
            if (userControl) {
                if (a.getClickCount() == 1) {
                    setBackground(activeBackground);
                    tempStringProperty.setValue(labelName);
                    if (callback != null) {
                        callback.accept("");
                    }
                }
            }
        });

        tempStringProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(labelName)) {
            } else {
                if (userControl) {
                    setBackground(normalBackground);
                    label.setStyle("-fx-font-weight:normal;-fx-font-style:normal;-fx-text-fill:black;-fx-font-size:18;");
                } else {
                    setBackground(logoffBackground);
                }
            }
        });

        windowInterface.addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(WindowInterfaceMessages.EnableMonitoring.name())) {
                logInMode();
            } else if (newValue.equals(WindowInterfaceMessages.DisableMonitoring.name())) {
                logfOffMode();
            } else if (newValue.equals(WindowInterfaceMessages.Error.name())) {
                logfOffMode();
            } else if (newValue.equals(WindowInterfaceMessages.Warning.name())) {
                logfOffMode();
            } else if (newValue.equals(WindowInterfaceMessages.Normal.name())) {
                logfOffMode();
            }
        });
    }

    private void logInMode() {
        userControl = true;
        setBackground(normalBackground);
        label.setStyle("-fx-font-weight:normal;-fx-font-style:normal;-fx-text-fill:Black;-fx-font-size:18;");
    }

    private void logfOffMode() {
        userControl = false;
        setBackground(logoffBackground);
        label.setStyle("-fx-font-weight:normal;-fx-font-style:Oblique;-fx-text-fill:darkblue;-fx-font-size:18;");
    }

    public String getWindowIconRef() {
        return windowIconRef;
    }

    public void setWindowIconRef(String windowIconRef) {
        this.windowIconRef = windowIconRef;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public StringProperty getWindowInterface() {
        return windowInterface;
    }

    @Override
    public String toString() {
        return "WindowReferenceNode  " + labelName + "-->  UserControl : " + userControl + "  Window Message : " + windowInterface.getValue();
    }

    public void setIconic(boolean Iconic) {
        this.iconic.setValue(Iconic);
    }

    public Consumer<String> getCallback() {
        return callback;
    }

    public void setOnIconClicked(Consumer<String> callback) {
        this.callback = callback;
    }

    public void setAuthority(RoleDTO authority) {
        loginService = ApplicationContext.applicationContext.getBean(LoginService.class);
        userService = ApplicationContext.applicationContext.getBean(UserService.class);
        rolesService = ApplicationContext.applicationContext.getBean(RolesService.class);
        roleRef = ApplicationContext.applicationContext.getBean(RoleRefService.class);
        if (users.isEmpty() || roleDTOS.isEmpty()) {
            users.addAll(userService.findAll());
            roleDTOS.addAll(rolesService.findAll());
            roleRefs.addAll(roleRef.findAll());
        }
        this.authority = authority;
        loginService.addObserver(this);
        windowInterface.setValue(WindowInterfaceMessages.DisableMonitoring.name());
    }

    @Override
    public void update(String username) {
        checkAuthority(username);
    }

    public void checkAuthority(String string) {
        users.stream().filter(userObject -> userObject.getName().trim().equalsIgnoreCase(string.trim()))
                .findFirst()
                .ifPresentOrElse(userDTO -> {
                            roleDTOS.stream().filter(roleDTO -> roleDTO.getName().equals(authority.getName()))
                                    .findFirst()
                                    .ifPresentOrElse(role -> {
                                        roleRefs.stream().filter(ref -> ref.getUserId() == userDTO.getUserId()).forEach(item -> {
                                            if (item.getRoleId() == role.getId()) {
                                                authorized = true;
                                                windowInterface.setValue(WindowInterfaceMessages.EnableMonitoring.name());
                                            }
                                        });
                                    }, () -> {
                                        authorized = false;
                                        windowInterface.setValue(WindowInterfaceMessages.DisableMonitoring.name());
                                    });
                        }
                        , () -> {
                            authorized = false;
                            windowInterface.setValue(WindowInterfaceMessages.DisableMonitoring.name());

                        });
    }
}
