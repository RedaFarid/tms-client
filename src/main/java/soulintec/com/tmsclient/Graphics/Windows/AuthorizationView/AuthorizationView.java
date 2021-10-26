package soulintec.com.tmsclient.Graphics.Windows.AuthorizationView;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.Authorization.RoleDTO;
import soulintec.com.tmsclient.Graphics.Controls.PasswordFieldTableCell;
import soulintec.com.tmsclient.Graphics.Windows.AuthorizationView.old.AuthoraizationWindow.AuthorizationListView;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorizationView implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private UserAuthorizationController controller;
    private UsersModel model;

    protected static MainWindow initialStage;

    private VBox root;
    private VBox vbox;
    private Label headerLabel;

    //Users Table
    TableView<UsersModel.UsersTableObject> table;
    TableColumn<UsersModel.UsersTableObject, String> userNameColumn;
    TableColumn<UsersModel.UsersTableObject, String> passwordColumn;
    TableColumn<UsersModel.UsersTableObject, LongProperty> idColumn;

    public Stage mainWindow;

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);

    public List<RoleDTO> authorityDTOSList = new ArrayList<>();

    @Autowired
    private AuthorizationListView authorizationListView;
    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainWindow = event.getStage();
        initialization();
        userAuthorities();
        graphicsBuilder();
        actionHandling();
    }

    private void initialization() {
        initialStage = ApplicationContext.applicationContext.getBean(MainWindow.class);
        controller = ApplicationContext.applicationContext.getBean(UserAuthorizationController.class);

        model = controller.getModel();

        root = new VBox();
        vbox = new VBox();
        headerLabel = new Label("User Administration");

        table = new TableView<>();

        idColumn = new TableColumn<>("Id");
        userNameColumn = new TableColumn<>("Name");
        passwordColumn = new TableColumn<>("Password");
    }

    private void graphicsBuilder() {
        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(root.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        table.setEditable(true);

        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        userNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setCellFactory(PasswordFieldTableCell.forTableColumn());

        userNameColumn.setEditable(true);
        passwordColumn.setEditable(true);

        table.getColumns().addAll(userNameColumn, passwordColumn);

        userNameColumn.prefWidthProperty().bind(table.widthProperty().divide(5));
        userNameColumn.setSortable(false);
        userNameColumn.setReorderable(false);

        passwordColumn.prefWidthProperty().bind(table.widthProperty().divide(5));
        passwordColumn.setSortable(false);
        passwordColumn.setReorderable(false);

        table.prefHeightProperty().bind(root.heightProperty());
        table.setItems(controller.getUsersList());

        vbox.getChildren().addAll(table);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        root.getChildren().addAll(headerLabel, vbox,authorizationListView.getBorderPane());
        root.setPadding(new Insets(10));

    }

    private void userAuthorities() {
    }

    private void actionHandling() {
        table.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Delete User!");
                alert.setContentText("Are you sure you want to delete user (" + table.getSelectionModel().getSelectedItem().getUserName() + ")");
                alert.initOwner(mainWindow);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        controller.deleteUser(table.getSelectionModel().getSelectedItem());
                    } catch (NullPointerException ex) {
//                        log.fatal(ex, ex);
                        e.consume();
                    }
                } else {
                    e.consume();
                }
            } else e.consume();
        });
        userNameColumn.setOnEditCommit(this::onUserNameChanged);
        passwordColumn.setOnEditCommit(this::onPasswordChanged);

        table.setOnMouseClicked((a) -> {
            controller.onTableSelection(table.getSelectionModel().getSelectedItems());
        });

    }

    public void onUserNameChanged(TableColumn.CellEditEvent<UsersModel.UsersTableObject, String> usersTableObjectStringCellEditEvent) {
        controller.updateUserData(usersTableObjectStringCellEditEvent.getOldValue(), usersTableObjectStringCellEditEvent.getRowValue());
        usersTableObjectStringCellEditEvent.getRowValue().setUserName(usersTableObjectStringCellEditEvent.getNewValue());
    }

    public void onPasswordChanged(TableColumn.CellEditEvent<UsersModel.UsersTableObject, String> usersTableObjectStringCellEditEvent) {
        controller.onPasswordChanged(usersTableObjectStringCellEditEvent.getRowValue());
        usersTableObjectStringCellEditEvent.getRowValue().setPassword(usersTableObjectStringCellEditEvent.getNewValue());
    }


    public synchronized void update() {
       controller.updateUsersList();
    }

    public Node getTabContainer() {
        return root;
    }

}
