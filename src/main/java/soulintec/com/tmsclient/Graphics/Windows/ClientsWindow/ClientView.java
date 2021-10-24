package soulintec.com.tmsclient.Graphics.Windows.ClientsWindow;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.Authorization.RoleDTO;
import soulintec.com.tmsclient.Graphics.Controls.DataEntryPartitionTitled;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedButton;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedLongField;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedTextField;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindowController;
import soulintec.com.tmsclient.Services.ClientsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClientView implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private ClientsController controller;
    private MainWindowController mainWindowController;
    private ClientsModel model;

    protected static MainWindow initialStage;

    private DataEntryPartitionTitled dataEntryPartitionTitled;

    private VBox clientsTab;
    private VBox clientsPane;

    private VBox clientsVbox;
    private ToolBar clientsHbox;
    private Label headerLabel;

    private BorderPane root;
    private TabPane tabContainer;

    private Stage mainStage;

    private TableFilter<ClientsModel.TableObject> clientsTableFilter;

    private TableView<ClientsModel.TableObject> table;
    private TableColumn<ClientsModel.TableObject, LongProperty> clientIDColumn;
    private TableColumn<ClientsModel.TableObject, StringProperty> clientNameColumn;
    private TableColumn<ClientsModel.TableObject, StringProperty> clientMainOfficeColumn;
    private TableColumn<ClientsModel.TableObject, StringProperty> clientContactNameColumn;
    private TableColumn<ClientsModel.TableObject, StringProperty> clientContactTelNumberColumn;
    private TableColumn<ClientsModel.TableObject, StringProperty> cientContactEmailColumn;
    private TableColumn<ClientsModel.TableObject, StringProperty> createdByColumn;
    private TableColumn<ClientsModel.TableObject, StringProperty> lastModifiedByColumn;
    private TableColumn<ClientsModel.TableObject, StringProperty> onTerminalColumn;
    private TableColumn<ClientsModel.TableObject, ObjectProperty<LocalDateTime>> creationDateColumn;
    private TableColumn<ClientsModel.TableObject, ObjectProperty<LocalDateTime>> modifyDateColumn;

    private EnhancedButton insertClient;
    private EnhancedButton deleteClient;
    private EnhancedButton updateClient;
    private EnhancedButton report;

    private Label idLabel;
    private Label nameLabel;
    private Label mainOfficeAdressLabel;
    private Label contactNameLabel;
    private Label contactTelNumberLabel;
    private Label contactEmailLabel;
    private Label creationDateLabel;
    private Label modificationLabel;
    private Label onTerminalLabel;
    private Label createdByLabel;
    private Label modifiedByLabel;

    private EnhancedLongField idField;
    private EnhancedTextField nameField;
    private EnhancedTextField mainOfficeAdressField;
    private EnhancedTextField contactNameField;
    private EnhancedTextField contactTelNumberField;
    private EnhancedTextField contactEmailField;
    private EnhancedTextField creationDateField;
    private EnhancedTextField modificationDateField;
    private EnhancedTextField createdByField;
    private EnhancedTextField modifiedByField;
    private EnhancedTextField onTerminalField;

    @Autowired
    ClientsService clientService;

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);

    public List<RoleDTO> authorityDTOSList = new ArrayList<>();

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainStage = event.getStage();
        initialization();
        userAuthorities();
        graphicsBuilder();
        actionHandling();
    }

    private void initialization() {
        initialStage = ApplicationContext.applicationContext.getBean(MainWindow.class);
        controller = ApplicationContext.applicationContext.getBean(ClientsController.class);
        mainWindowController = ApplicationContext.applicationContext.getBean(MainWindowController.class);
        model = controller.getModel();

        dataEntryPartitionTitled = new DataEntryPartitionTitled("Client");
        clientsTab = new VBox();
        clientsPane = new VBox();
        clientsVbox = new VBox();
        clientsHbox = new ToolBar();
        headerLabel = new Label("Clients");

        root = new BorderPane();
        tabContainer = new TabPane();

        table = new TableView<>();

        clientIDColumn = new TableColumn<>("ID");
        clientNameColumn = new TableColumn<>("Name");
        clientMainOfficeColumn = new TableColumn<>("Office Address");
        clientContactNameColumn = new TableColumn<>("Contact name");
        clientContactTelNumberColumn = new TableColumn<>("Tel Number");
        cientContactEmailColumn = new TableColumn<>("Email");
        createdByColumn = new TableColumn<>("Created By");
        lastModifiedByColumn = new TableColumn<>("Modified By");
        onTerminalColumn = new TableColumn<>("On Terminal");
        creationDateColumn = new TableColumn<>("Creation Date");
        modifyDateColumn = new TableColumn<>("Modification Date");

        insertClient = new EnhancedButton("Create new client");
        deleteClient = new EnhancedButton("Delete selected client");
        updateClient = new EnhancedButton("Update selected client");
        report = new EnhancedButton("Show Report");

        idLabel = new Label("Id :");
        nameLabel = new Label("Name :");
        mainOfficeAdressLabel = new Label("Office Address :");
        contactNameLabel = new Label("Contact name :");
        contactTelNumberLabel = new Label("Tel Number :");
        contactEmailLabel = new Label("Email :");
        creationDateLabel = new Label("Creation Date :");
        modificationLabel = new Label("Modification Date :");
        onTerminalLabel = new Label("On Terminal :");
        createdByLabel = new Label("Created By :");
        modifiedByLabel = new Label("Modified By :");

        idField = new EnhancedLongField();
        nameField = new EnhancedTextField();
        mainOfficeAdressField = new EnhancedTextField();
        contactNameField = new EnhancedTextField();
        contactTelNumberField = new EnhancedTextField();
        contactEmailField = new EnhancedTextField();
        creationDateField = new EnhancedTextField();
        modificationDateField = new EnhancedTextField();
        createdByField = new EnhancedTextField();
        onTerminalField = new EnhancedTextField();
        modifiedByField = new EnhancedTextField();
    }

    private void userAuthorities() {
        createWindowAuthoritiesTemplate();
        assignAuthoritiesTemplate();
    }

    private void createWindowAuthoritiesTemplate() {
        authorityDTOSList.clear();
        //Authorities
        RoleDTO saving = new RoleDTO("Save " + this);
        RoleDTO deleting = new RoleDTO("Delete " + this);

        authorityDTOSList.add(saving);
        authorityDTOSList.add(deleting);

        mainWindowController.createWindowAuthorities(authorityDTOSList);
    }

    private void assignAuthoritiesTemplate() {
        if (authorityDTOSList.size() != 0) {
            insertClient.setAuthority(authorityDTOSList.get(0));
            updateClient.setAuthority(authorityDTOSList.get(0));
            deleteClient.setAuthority(authorityDTOSList.get(1));
        }
    }

    private void graphicsBuilder() {
        root.setTop(headerLabel);
        root.setCenter(clientsTab);
        root.setPadding(new Insets(10));

        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(root.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        //control buttons configuration
        insertClient.setPrefWidth(150);
        deleteClient.setPrefWidth(150);
        updateClient.setPrefWidth(150);
        report.setPrefWidth(150);
        //dataentery region configuration

        idLabel.setPrefWidth(150);
        nameLabel.setPrefWidth(150);
        mainOfficeAdressLabel.setPrefWidth(150);
        contactNameLabel.setPrefWidth(150);
        contactTelNumberLabel.setPrefWidth(150);
        contactEmailLabel.setPrefWidth(150);
        creationDateLabel.setPrefWidth(150);
        modificationLabel.setPrefWidth(150);
        createdByLabel.setPrefWidth(150);
        modifiedByLabel.setPrefWidth(150);
        onTerminalLabel.setPrefWidth(150);

        idField.setEditable(false);
        creationDateField.setEditable(false);
        modificationDateField.setEditable(false);
        createdByField.setEditable(false);
        modifiedByField.setEditable(false);
        onTerminalField.setEditable(false);

        idLabel.setTextAlignment(TextAlignment.RIGHT);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        mainOfficeAdressLabel.setTextAlignment(TextAlignment.RIGHT);
        contactNameLabel.setTextAlignment(TextAlignment.RIGHT);
        contactTelNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        contactEmailLabel.setTextAlignment(TextAlignment.RIGHT);
        creationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        modificationLabel.setTextAlignment(TextAlignment.RIGHT);
        createdByLabel.setTextAlignment(TextAlignment.RIGHT);
        modifiedByLabel.setTextAlignment(TextAlignment.RIGHT);
        onTerminalLabel.setTextAlignment(TextAlignment.RIGHT);

        idLabel.setAlignment(Pos.BASELINE_RIGHT);
        nameLabel.setAlignment(Pos.BASELINE_RIGHT);
        mainOfficeAdressLabel.setAlignment(Pos.BASELINE_RIGHT);
        contactNameLabel.setAlignment(Pos.BASELINE_RIGHT);
        contactTelNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        contactEmailLabel.setAlignment(Pos.BASELINE_RIGHT);
        creationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        modificationLabel.setAlignment(Pos.BASELINE_RIGHT);
        createdByLabel.setAlignment(Pos.BASELINE_RIGHT);
        modifiedByLabel.setAlignment(Pos.BASELINE_RIGHT);
        onTerminalLabel.setAlignment(Pos.BASELINE_RIGHT);

        idLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        nameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        mainOfficeAdressLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contactNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contactTelNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contactEmailLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        creationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        modificationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        createdByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        modifiedByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        onTerminalLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        idField.setPrefWidth(250);
        mainOfficeAdressField.setPrefWidth(520);
        contactNameField.setPrefWidth(250);
        contactTelNumberField.setPrefWidth(250);
        nameField.setPrefWidth(250);
        creationDateField.setPrefWidth(250);
        modificationDateField.setPrefWidth(250);
        createdByField.setPrefWidth(250);
        onTerminalField.setPrefWidth(250);
        modifiedByField.setPrefWidth(250);

        //restriction handling
        nameField.setRestrict("[a-zA-Z-_ ]");
        nameField.setMaxLength(45);
        mainOfficeAdressField.setRestrict("[a-zA-Z-_ ]");
        mainOfficeAdressField.setMaxLength(45);
        contactNameField.setRestrict("[a-zA-Z-_ ]");
        contactNameField.setMaxLength(14);
        contactTelNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                contactTelNumberField.setText(oldValue);
            }
        });
        contactTelNumberField.setMaxLength(11);
        contactEmailField.setMaxLength(30);

        dataEntryPartitionTitled.add(idLabel, 1, 1);
        dataEntryPartitionTitled.add(idField, 2, 1);

        dataEntryPartitionTitled.add(nameLabel, 3, 1);
        dataEntryPartitionTitled.add(nameField, 4, 1);

        dataEntryPartitionTitled.add(mainOfficeAdressLabel, 5, 1);
        dataEntryPartitionTitled.add(mainOfficeAdressField, 6, 1, 3, 1);

        dataEntryPartitionTitled.add(contactNameLabel, 1, 2);
        dataEntryPartitionTitled.add(contactNameField, 2, 2);

        dataEntryPartitionTitled.add(contactTelNumberLabel, 3, 2);
        dataEntryPartitionTitled.add(contactTelNumberField, 4, 2);

        dataEntryPartitionTitled.add(contactEmailLabel, 5, 2);
        dataEntryPartitionTitled.add(contactEmailField, 6, 2, 3, 1);

        dataEntryPartitionTitled.add(creationDateLabel, 1, 3);
        dataEntryPartitionTitled.add(creationDateField, 2, 3);

        dataEntryPartitionTitled.add(modificationLabel, 3, 3);
        dataEntryPartitionTitled.add(modificationDateField, 4, 3);

        dataEntryPartitionTitled.add(onTerminalLabel, 1, 4);
        dataEntryPartitionTitled.add(onTerminalField, 2, 4);

        dataEntryPartitionTitled.add(createdByLabel, 5, 3);
        dataEntryPartitionTitled.add(createdByField, 6, 3);

        dataEntryPartitionTitled.add(modifiedByLabel, 7,3);
        dataEntryPartitionTitled.add(modifiedByField, 8,3);

        //table configuration
        clientIDColumn.setCellValueFactory(new PropertyValueFactory<>("clientIdColumn"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        clientMainOfficeColumn.setCellValueFactory(new PropertyValueFactory<>("mainOfficeAddressColumn"));
        clientContactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactNameColumn"));
        clientContactTelNumberColumn.setCellValueFactory(new PropertyValueFactory<>("contactTelNumberColumn"));
        cientContactEmailColumn.setCellValueFactory(new PropertyValueFactory<>("contactEmailColumn"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDateColumn"));
        modifyDateColumn.setCellValueFactory(new PropertyValueFactory<>("modifyDateColumn"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdByColumn"));
        lastModifiedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastModifiedByColumn"));
        onTerminalColumn.setCellValueFactory(new PropertyValueFactory<>("onTerminalColumn"));

        table.getColumns().addAll(clientIDColumn, clientNameColumn, clientMainOfficeColumn, clientContactNameColumn, clientContactTelNumberColumn, cientContactEmailColumn, creationDateColumn, modifyDateColumn, createdByColumn,lastModifiedByColumn, onTerminalColumn);
        table.prefHeightProperty().bind(root.heightProperty().subtract(clientsVbox.heightProperty()));
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(controller.getDataList());
        clientsTableFilter = TableFilter.forTableView(table).apply();

        dataEntryPartitionTitled.setVgap(5);
        dataEntryPartitionTitled.setHgap(5);

        clientsHbox.getItems().addAll(insertClient, updateClient, deleteClient);

        clientsVbox.getChildren().addAll(dataEntryPartitionTitled, clientsHbox);
        clientsVbox.setPadding(new Insets(5));
        clientsVbox.setSpacing(5);

        clientsPane.getChildren().add(clientsVbox);
        clientsPane.getChildren().add(table);

        clientsTab.getChildren().add(clientsPane);

        idField.longValueProperty().bindBidirectional(model.clientIdProperty());
        nameField.textProperty().bindBidirectional(model.nameProperty());
        mainOfficeAdressField.textProperty().bindBidirectional(model.mainOfficeAddressProperty());
        contactNameField.textProperty().bindBidirectional(model.contactNameProperty());
        contactTelNumberField.textProperty().bindBidirectional(model.contactTelNumberProperty());
        contactEmailField.textProperty().bindBidirectional(model.contactEmailProperty());
        createdByField.textProperty().bindBidirectional(model.createdByProperty());
        onTerminalField.textProperty().bindBidirectional(model.onTerminalProperty());
        modificationDateField.textProperty().bindBidirectional(model.modifyDateProperty());
        modifiedByField.textProperty().bindBidirectional(model.lastModifiedByProperty());
        creationDateField.textProperty().bindBidirectional(model.creationDateProperty());
    }

    private void actionHandling() {
        insertClient.setOnMouseClicked(controller::onInsert);
        deleteClient.setOnMouseClicked(controller::onDelete);
        updateClient.setOnMouseClicked(controller::onUpdate);

        table.setOnMouseClicked((a) -> {
            controller.onTableSelection(table.getSelectionModel().getSelectedItems());
        });
    }

    public Node getTabContainer() {
        return root;
    }

    public void update() {
        ReadOnlyBooleanProperty update = controller.update();
        table.cursorProperty().bind(Bindings.when(update).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));
    }

    @Override
    public String toString() {
        return "Clients";
    }
}
