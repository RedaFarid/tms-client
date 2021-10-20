package soulintec.com.tmsclient.Graphics.Windows;

import com.google.common.io.Resources;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

@Log4j2
@Component
public class LoginWindow extends VBox {

    private static LoginWindow singleton = null;
    private Scene scene = new Scene(this);

    private final Button LogIn = new Button("Log in");
    private final Button Cancel = new Button("Cancel");

    private final Label mainLabel = new Label("Terminal management\nsystem\n\n\n");

    private final TextField username = new TextField("ChiefAdmin");
    private final PasswordField password = new PasswordField();

    public LoginWindow() {
        graphicsBuilder();
    }
    public static LoginWindow getWindow() {
        if (singleton == null) {
            synchronized (LoginWindow.class) {
                singleton = new LoginWindow();
            }
        }
        return singleton;
    }

    private void graphicsBuilder() {
        password.setText("");

        LogIn.setPrefWidth(300);

        Cancel.setPrefWidth(300);
        Cancel.setCancelButton(true);

        mainLabel.setTextAlignment(TextAlignment.CENTER);

        username.setMaxWidth(300);
        username.setPromptText("Username");
        username.setMinHeight(10);

        password.setMaxWidth(300);
        password.setPromptText("Password");
        password.setMinHeight(10);

        getChildren().addAll(mainLabel, username, password, new Pane(), LogIn, Cancel);
        setSpacing(5);
//        setPadding(new Insets(0, 5, 50, 5));
        setAlignment(Pos.CENTER);

        scene.getStylesheets().add(String.valueOf(Resources.getResource("Styles/loginWindow.css")));

    }

    public CompletableFuture<LoginWindowReturnObject> showAndReturnUser(Stage ownerWindow, Executor executorService) {
        return CompletableFuture
                .supplyAsync(() -> createLoginTask(ownerWindow), executorService)
                .thenApply(this::getDataFromLoginTask);
    }
    public CompletableFuture<LoginWindowReturnObject> showAndReturnUserForChangePassword(Stage ownerWindow, String currentUser, Executor executorService) {
        return CompletableFuture
                .supplyAsync(() -> createChangePasswordTask(ownerWindow, currentUser), executorService)
                .thenApply(this::getDataFromLoginTask);
    }
    private FutureTask<LoginWindowReturnObject> createLoginTask(Stage owner) {
        return new FutureTask<>(() -> {
            Stage stage = new Stage();
            stage.setTitle("Log in");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.setHeight(400);
            stage.setWidth(400);
            stage.initOwner(owner);
            stage.initStyle(StageStyle.UTILITY);

            LoginWindowReturnObject returnObject = new LoginWindowReturnObject();

            password.setText("");
            LogIn.setText("Log in");
            username.setEditable(true);
            password.requestFocus();

            password.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    returnObject.setUserName(username.getText());
                    returnObject.setPassword(password.getText());
                    returnObject.setStatus(LoginStatus.OK);
                    stage.hide();
                }
            });
            stage.setOnCloseRequest(action -> {
                returnObject.setUserName("");
                returnObject.setPassword("");
                returnObject.setStatus(LoginStatus.CANCEL);
                stage.hide();
            });
            LogIn.setOnMouseClicked(action -> {
                returnObject.setUserName(username.getText());
                returnObject.setPassword(password.getText());
                returnObject.setStatus(LoginStatus.OK);
                stage.hide();
            });
            Cancel.setOnMouseClicked(action -> {
                returnObject.setUserName("");
                returnObject.setPassword("");
                returnObject.setStatus(LoginStatus.CANCEL);
                stage.hide();
            });
            stage.showAndWait();
            return returnObject;
        });
    }
    private FutureTask<LoginWindowReturnObject> createChangePasswordTask(Stage owner, String currentUser) {
        return new FutureTask<>(() -> {
            Stage stage = new Stage();
            stage.setTitle("Save");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.setHeight(400);
            stage.setWidth(400);
            stage.initOwner(owner);

            LoginWindowReturnObject returnObject = new LoginWindowReturnObject();
            username.setEditable(false);
            password.requestFocus();
            LogIn.setText("Save password");
            username.setText(currentUser);
            password.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        returnObject.setUserName(username.getText());
                        returnObject.setPassword(password.getText());
                        returnObject.setStatus(LoginStatus.OK);
                        stage.hide();
                    }
                }
            });
            stage.setOnCloseRequest(action -> {
                returnObject.setUserName("");
                returnObject.setPassword("");
                returnObject.setStatus(LoginStatus.CANCEL);
                stage.hide();
            });
            LogIn.setOnMouseClicked(action -> {
                returnObject.setUserName(username.getText());
                returnObject.setPassword(password.getText());
                returnObject.setStatus(LoginStatus.OK);
                stage.hide();
            });
            Cancel.setOnMouseClicked(action -> {
                returnObject.setUserName("");
                returnObject.setPassword("");
                returnObject.setStatus(LoginStatus.CANCEL);
                stage.hide();
            });
            return returnObject;
        });
    }
    private LoginWindowReturnObject getDataFromLoginTask(FutureTask<LoginWindowReturnObject> loginTask) {
        Platform.runLater(loginTask);
        try {
            return loginTask.get();
        } catch (Exception e) {
            log.fatal(e);
            return new LoginWindowReturnObject();
        }
    }
    private LoginWindowReturnObject getDataFromChangePasswordTask(FutureTask<LoginWindowReturnObject> loginTask) {
        Platform.runLater(loginTask);
        try {
            return loginTask.get();
        } catch (Exception e) {
            log.fatal(e);
            return new LoginWindowReturnObject();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginWindowReturnObject {
        private String userName;
        private String password;
        private LoginStatus status;
    }

    public enum LoginStatus{
        OK, CANCEL;
    }
}