package soulintec.com.tmsclient.Graphics.Windows.AuthorizationView.old.AuthoraizationWindow;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.elusive.Elusive;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.Authorization.RoleDTO;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedButton;
import soulintec.com.tmsclient.Graphics.Windows.AuthorizationView.UserAuthorizationController;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Services.RoleRefService;
import soulintec.com.tmsclient.Services.RolesService;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorizationListView implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private UserAuthorizationController controller;
    private AuthorizationModel model;

    @Autowired
    private RoleRefService roleRefService;
    @Autowired
    private RolesService rolesService;

    protected TableView<AuthorizationModel.TableObject> table;
    private TableColumn<AuthorizationModel.TableObject, LongProperty> authorizationIdColumn;
    private TableColumn<AuthorizationModel.TableObject, StringProperty> authorityNameColumn;

    //--------------------------------------------------------------Right Section---------------------------------------\\
    // new Table
    protected TableView<AuthorizationModel.SecondTableObject> addTable;
    private TableColumn<AuthorizationModel.SecondTableObject, StringProperty> addTableAuthorityColumn;
    private TableColumn<AuthorizationModel.SecondTableObject, LongProperty> addTableIdColumn;

    //Added Buttons
    private EnhancedButton addButton;
    private EnhancedButton removeButton;

    //Added FontIcons
    // Icons
    private FontIcon addFontIcon;
    private FontIcon removeFontIcon;

    //HBox
    private HBox hBox;

    //VBox
    private VBox vBox;
    private BorderPane borderPane;

    protected static MainWindow initialStage;

    public Stage mainWindow;
    public List<RoleDTO> authorityDTOSList = new ArrayList<>();

    protected void initialize() {

        initialStage = ApplicationContext.applicationContext.getBean(MainWindow.class);
        controller = ApplicationContext.applicationContext.getBean(UserAuthorizationController.class);

        model = controller.getAuthModel();
        table = new TableView<>();

        authorizationIdColumn = new TableColumn<>("Id");
        authorityNameColumn = new TableColumn<>("Authority");

        //--------------------------------------------------------------Right Section---------------------------------------\\
        addButton = new EnhancedButton("");
        removeButton = new EnhancedButton("");

        addFontIcon = new FontIcon(Elusive.CARET_LEFT);
        removeFontIcon = new FontIcon(Elusive.CARET_RIGHT);

        //new table
        addTableAuthorityColumn = new TableColumn<>("Available Authorities");
        addTableIdColumn = new TableColumn<>("ID");
        addTable = new TableView<>();
        //new table
        addTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        hBox = new HBox();
        vBox = new VBox();
        borderPane = new BorderPane();
    }

    private void userAuthorities() {
        createWindowAuthoritiesTemplate();
        assignAuthoritiesTemplate();
    }

    private void createWindowAuthoritiesTemplate() {
        authorityDTOSList.clear();
        //Authorities
        RoleDTO addAndRemove = new RoleDTO("User Administration");

        authorityDTOSList.add(addAndRemove);
    }

    private void assignAuthoritiesTemplate() {
        if (authorityDTOSList.size() != 0) {
            addButton.setAuthority(authorityDTOSList.get(0));
            removeButton.setAuthority(authorityDTOSList.get(0));
        }
    }

    protected void actionHandling() {
        addTable.setOnMouseClicked((a) -> {
            controller.onAddTableSelection(addTable.getSelectionModel().getSelectedItems());
        });
        controller.getAuthorizationList().addListener((ListChangeListener<AuthorizationModel.TableObject>) change -> {
//            controller.updateAddAuthoritiesList();
            controller.selectItem(table);
            controller.setSelectedItem(addTable.getSelectionModel().getSelectedIndex());
            controller.onAuthTableSelection(table.getSelectionModel().getSelectedItems());
        });
        controller.getAddAuthorizationList().addListener((ListChangeListener<AuthorizationModel.SecondTableObject>) change -> {
            controller.selectAddItem(addTable);
            controller.setAddSelectedItem(addTable.getSelectionModel().getSelectedIndex());
            controller.onAddTableSelection(addTable.getSelectionModel().getSelectedItems());
        });

        addTable.getSelectionModel().selectedItemProperty().addListener((observableValue, secondTableObjectTableViewSelectionModel, t1) -> {
            controller.setAddSelectedItem(addTable.getSelectionModel().getSelectedIndex());
            controller.onAddTableSelection(addTable.getSelectionModel().getSelectedItems());
        });

        table.getSelectionModel().selectedItemProperty().addListener((observableValue, tableObjectTableViewSelectionModel, t1) -> {
            controller.setSelectedItem(table.getSelectionModel().getSelectedIndex());
            controller.onAuthTableSelection(table.getSelectionModel().getSelectedItems());
        });

        addButton.setOnAction(e -> controller.addAuth());
        removeButton.setOnAction(e -> controller.removeAuthority());
    }

    protected void graphicsBuild() {
        authorizationIdColumn.setCellValueFactory(new PropertyValueFactory<>("idColumn"));
        authorityNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));

        table.getColumns().addAll(authorityNameColumn);
        authorityNameColumn.setPrefWidth(200);
        authorityNameColumn.setReorderable(false);
        table.setItems(controller.getAuthorizationList());
        //--------------------------------------------------------------Right Section---------------------------------------\\
        // Icons
        addFontIcon.setIconColor(Color.BLACK);
        removeFontIcon.setIconColor(Color.BLACK);

        addFontIcon.setIconSize(25);
        removeFontIcon.setIconSize(25);

        addButton.setGraphic(addFontIcon);
        removeButton.setGraphic(removeFontIcon);

        vBox.setSpacing(5);
        vBox.setPadding(new Insets(130));
        vBox.getChildren().addAll(addButton, removeButton);

        //new Table
        addTable.prefWidthProperty().bind(borderPane.widthProperty().divide(2).subtract(130));

        addTableAuthorityColumn.prefWidthProperty().bind(addTable.widthProperty().subtract(15));
        addTableAuthorityColumn.setCellValueFactory(new PropertyValueFactory<>("addAuthorityColumn"));
        addTable.getColumns().add(addTableAuthorityColumn);
        addTable.setItems(controller.getAddAuthorizationList());
        hBox.getChildren().addAll(vBox, addTable);

        borderPane.setCenter(table);
        borderPane.setRight(hBox);
    }


    public BorderPane getBorderPane() {
        return borderPane;
    }

    @Override
    public String toString() {
        return "Authorization";
    }

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainWindow = event.getStage();
        initialize();
        userAuthorities();
        graphicsBuild();
        actionHandling();
    }
}
