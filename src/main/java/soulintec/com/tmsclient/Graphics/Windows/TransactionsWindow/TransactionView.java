package soulintec.com.tmsclient.Graphics.Windows.TransactionsWindow;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.scene.control.LocalDateTimeTextField;
import lombok.extern.log4j.Log4j2;
import org.controlsfx.control.table.TableFilter;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.Authorization.RoleDTO;
import soulintec.com.tmsclient.Entities.OperationType;
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Graphics.Controls.*;
import soulintec.com.tmsclient.Graphics.Windows.ClientsWindow.ClientsModel;
import soulintec.com.tmsclient.Graphics.Windows.DriversWindow.DriversModel;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindowController;
import soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow.MaterialsModel;
import soulintec.com.tmsclient.Graphics.Windows.StationsWindow.StationsModel;
import soulintec.com.tmsclient.Graphics.Windows.TanksWindow.TanksModel;
import soulintec.com.tmsclient.Graphics.Windows.TransactionsWindow.Utils.EnhancedTableRow;
import soulintec.com.tmsclient.Graphics.Windows.TruckWindow.TruckContainerModel;
import soulintec.com.tmsclient.Graphics.Windows.TruckWindow.TruckTrailerModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class TransactionView implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private TransactionsController controller;
    private MainWindowController mainWindowController;
    private TransactionsModel model;

    private MaterialsModel materialsModel;
    private StationsModel stationsModel;
    private TanksModel tanksModel;
    private DriversModel driversModel;
    private TruckContainerModel truckContainerModel;
    private TruckTrailerModel truckTrailerModel;
    private ClientsModel clientsModel;

    protected static MainWindow initialStage;

    private DataEntryPartitionTitled transactionsDataEntry;
    private DataEntryPartitionTitled contextProductsDataEntry;
    private DataEntryPartitionTitled contextStationDataEntry;
    private DataEntryPartitionTitled contextTanksDataEntry;
    private DataEntryPartitionTitled contextDriversDataEntry;
    private DataEntryPartitionTitled contextTruckContainersDataEntry;
    private DataEntryPartitionTitled contextTruckTrailersDataEntry;
    private DataEntryPartitionTitled contextClientsDataEntry;

    private Tab transactionsTab;
    private VBox transactionsPane;
    private Label headerLabel;

    private VBox transactionsVbox;
    private ToolBar transactionsHbox;

    private BorderPane root;
    private TabPane tabContainer;

    private Stage mainWindow;
    private Stage contextProductStage;
    private Stage contextStationStage;
    private Stage contextTanksStage;
    private Stage contextDriversStage;
    private Stage contextTruckContainersStage;
    private Stage contextTruckTrailersStage;
    private Stage contextClientsStage;

    private TableFilter<TransactionsModel.TableObject> tableFilter;

    private TableView<TransactionsModel.TableObject> tableView;
    private TableColumn<TransactionsModel.TableObject, LongProperty> transactionIdColumn;
    private TableColumn<TransactionsModel.TableObject, LongProperty> materialColumn;
    private TableColumn<TransactionsModel.TableObject, LongProperty> stationColumn;
    private TableColumn<TransactionsModel.TableObject, LongProperty> clientColumn;
    private TableColumn<TransactionsModel.TableObject, LongProperty> tankColumn;
    private TableColumn<TransactionsModel.TableObject, LongProperty> driverColumn;
    private TableColumn<TransactionsModel.TableObject, StringProperty> truckTrailerColumn;
    private TableColumn<TransactionsModel.TableObject, DoubleProperty> truckContainerColumn;
    private TableColumn<TransactionsModel.TableObject, ObjectProperty<OperationType>> operationTypeColumn;
    private TableColumn<TransactionsModel.TableObject, DoubleProperty> qtyColumn;
    private TableColumn<TransactionsModel.TableObject, ObjectProperty<LocalDateTime>> dateTimeColumn;
    private TableColumn<TransactionsModel.TableObject, StringProperty> createdByColumn;
    private TableColumn<TransactionsModel.TableObject, StringProperty> onTerminalColumn;
    private TableColumn<TransactionsModel.TableObject, ObjectProperty<LocalDateTime>> creationDateColumn;
    private TableColumn<TransactionsModel.TableObject, ObjectProperty<LocalDateTime>> modifyDateColumn;

    private TableFilter<MaterialsModel.TableObject> contextProductTableFilter;

    private TableView<MaterialsModel.TableObject> contextProductTableView;
    private TableColumn<MaterialsModel.TableObject, StringProperty> contextProductNameColumn;
    private TableColumn<MaterialsModel.TableObject, StringProperty> contextProductDescriptionColumn;

    private TableFilter<StationsModel.TableObject> contextStationsTableFilter;

    private TableView<StationsModel.TableObject> contextStationsTableView;
    private TableColumn<StationsModel.TableObject, StringProperty> contextStationNameColumn;
    private TableColumn<StationsModel.TableObject, StringProperty> contextStationLocationColumn;
    private TableColumn<StationsModel.TableObject, ObjectProperty<String>> contextStationComputerColumn;

    private TableFilter<TanksModel.TableObject> contextTanksTableFilter;

    private TableView<TanksModel.TableObject> contextTanksTableView;
    private TableColumn<TanksModel.TableObject, StringProperty> contextTankNameColumn;
    private TableColumn<TanksModel.TableObject, DoubleProperty> contextTankQtyColumn;
    private TableColumn<TanksModel.TableObject, DoubleProperty> contextTankCalcQtyColumn;

    private TableFilter<DriversModel.TableObject> contextDriverTableFilter;

    private TableView<DriversModel.TableObject> contextDriverTableView;
    private TableColumn<DriversModel.TableObject, StringProperty> contextDriverNameColumn;
    private TableColumn<DriversModel.TableObject, StringProperty> contextDriverLicenceNoColumn;

    private TableFilter<TruckTrailerModel.TableObject> contextTruckTrailerTableFilter;

    private TableView<TruckTrailerModel.TableObject> contextTruckTrailerTableView;
    private TableColumn<TruckTrailerModel.TableObject, StringProperty> contextTruckTrailerNoColumn;
    private TableColumn<TruckTrailerModel.TableObject, StringProperty> contextTruckTrailerLicenceNoColumn;

    private TableFilter<TruckContainerModel.TableObject> contextTruckContainerFilter;

    private TableView<TruckContainerModel.TableObject> contextTruckContainerTableView;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> contextTruckContainerNoColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> contextTruckContainerLicenceNoColumn;

    private TableFilter<ClientsModel.TableObject> contextClientsFilter;

    private TableView<ClientsModel.TableObject> contextClientsTableView;
    private TableColumn<ClientsModel.TableObject, StringProperty> contextClientNameColumn;
    private TableColumn<ClientsModel.TableObject, StringProperty> contextClientContactNameColumn;
    private TableColumn<ClientsModel.TableObject, StringProperty> contextClientMobileColumn;

    private EnhancedButton insert;
    private EnhancedButton delete;
    private EnhancedButton update;
    private EnhancedButton refresh;
    private EnhancedButton report;

    private Label idLabel;
    private Label materialLabel;
    private Label stationLabel;
    private Label tankLabel;
    private Label driverLabel;
    private Label truckContainerLabel;
    private Label truckTrailerLabel;
    private Label clientLabel;
    private Label qtyLabel;
    private Label operationTypeLabel;
    private Label dateTimeLabel;
    private Label creationDateLabel;
    private Label modificationLabel;
    private Label onTerminalLabel;
    private Label createdByLabel;

    private Label contextProductNameLabel;
    private Label contextProductDescriptionLabel;

    private Label contextStationNameLabel;
    private Label contextStationLocationLabel;
    private Label contextStationComputerLabel;

    private Label contextTankNameLabel;
    private Label contextTankQtyLabel;
    private Label contextTankCalcQtyLabel;

    private Label contextDriverNameLabel;
    private Label contextDriverLicenceNoLabel;

    private Label contextTruckTrailerNoLabel;
    private Label contextTruckTrailerLicenceNoLabel;

    private Label contextTruckContainerNoLabel;
    private Label contextTruckContainerLicenceNoLabel;

    private Label contextClientNameLabel;
    private Label contextClientContactNameLabel;
    private Label contextClientMobileLabel;

    private EnhancedLongField transactionIdField;
    private EnhancedLongField materialIdField;
    private EnhancedLongField stationIdField;
    private EnhancedLongField tankIdField;
    private EnhancedLongField driverIdField;
    private EnhancedLongField truckTrailerField;
    private EnhancedLongField truckContainerField;
    private EnhancedLongField clientField;
    private EnhancedDoubleField qtyField;
    private ComboBox<OperationType> operationTypeComboBox;
    private LocalDateTimeTextField dateTimeField;
    private EnhancedTextField creationDateField;
    private EnhancedTextField modificationDateField;
    private EnhancedTextField createdByField;
    private EnhancedTextField onTerminalField;

    private Label contextProductNameFieldField;
    private Label contextProductDescriptionField;

    private Label contextStationNameField;
    private Label contextStationLocationField;
    private Label contextStationComputerField;

    private Label contextTankNameField;
    private EnhancedDoubleField contextTankQtyField;
    private EnhancedDoubleField contextTankCalcQtyField;

    private Label contextDriverNameField;
    private Label contextDriverLicenceNoField;

    private Label contextTruckTrailerNoField;
    private Label contextTruckTrailerLicenceNoField;

    private Label contextTruckContainerNoField;
    private Label contextTruckContainerLicenceNoField;

    private Label contextClientNameField;
    private Label contextClientContactField;
    private Label contextClientMobileField;

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);

    public List<RoleDTO> authorityDTOSList = new ArrayList<>();

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainWindow = event.getStage();
        contextProductStage = new Stage();
        contextStationStage = new Stage();
        contextTanksStage = new Stage();
        contextDriversStage = new Stage();
        contextTruckContainersStage = new Stage();
        contextTruckTrailersStage = new Stage();
        contextClientsStage = new Stage();

        init();
        userAuthorities();
        graphicsBuilder();
        actionHandling();
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
            insert.setAuthority(authorityDTOSList.get(0));
            update.setAuthority(authorityDTOSList.get(0));
            delete.setAuthority(authorityDTOSList.get(1));
        }
    }

    private void init() {
        initialStage = ApplicationContext.applicationContext.getBean(MainWindow.class);
        controller = ApplicationContext.applicationContext.getBean(TransactionsController.class);
        mainWindowController = ApplicationContext.applicationContext.getBean(MainWindowController.class);

        model = controller.getModel();
        materialsModel = controller.getMaterialsModel();
        stationsModel = controller.getStationModel();
        tanksModel = controller.getTanksModel();
        driversModel = controller.getDriversModel();
        truckContainerModel = controller.getTruckContainersModel();
        truckTrailerModel = controller.getTruckTrailersModel();
        clientsModel = controller.getClientsModel();

        transactionsDataEntry = new DataEntryPartitionTitled("Transaction");
        contextProductsDataEntry = new DataEntryPartitionTitled("Selected material");
        contextStationDataEntry = new DataEntryPartitionTitled("Selected station");
        contextTanksDataEntry = new DataEntryPartitionTitled("Selected tank");
        contextDriversDataEntry = new DataEntryPartitionTitled("Selected driver");
        contextTruckTrailersDataEntry = new DataEntryPartitionTitled("Selected truck trailer");
        contextTruckContainersDataEntry = new DataEntryPartitionTitled("Selected truck container");
        contextClientsDataEntry = new DataEntryPartitionTitled("Selected client");

        transactionsTab = new Tab("Transactions Management");
        transactionsPane = new VBox();
        headerLabel = new Label("Transactions");

        transactionsVbox = new VBox();
        transactionsHbox = new ToolBar();

        root = new BorderPane();
        tabContainer = new TabPane();
        tableView = new TableView<>();
        transactionIdColumn = new TableColumn<>("Id");
        materialColumn = new TableColumn<>("Material");
        tankColumn = new TableColumn<>("Tank");
        clientColumn = new TableColumn<>("Client");
        driverColumn = new TableColumn<>("Driver");
        stationColumn = new TableColumn<>("Station");
        qtyColumn = new TableColumn<>("Quantity");
        dateTimeColumn = new TableColumn<>("Date time");
        truckTrailerColumn = new TableColumn<>("Trailer");
        truckContainerColumn = new TableColumn<>("Container");
        operationTypeColumn = new TableColumn<>("Operation type");
        createdByColumn = new TableColumn<>("Created By");
        onTerminalColumn = new TableColumn<>("On Terminal");
        creationDateColumn = new TableColumn<>("Creation Date");
        modifyDateColumn = new TableColumn<>("Modification Date");

        contextProductTableView = new TableView<>();

        contextProductNameColumn = new TableColumn<>("Name");
        contextProductDescriptionColumn = new TableColumn<>("Description");

        contextStationsTableView = new TableView<>();

        contextStationNameColumn = new TableColumn<>("Name");
        contextStationComputerColumn = new TableColumn<>("Computer");
        contextStationLocationColumn = new TableColumn<>("Location");

        contextTanksTableView = new TableView<>();

        contextTankNameColumn = new TableColumn<>("Name");
        contextTankQtyColumn = new TableColumn<>("Quantity");
        contextTankCalcQtyColumn = new TableColumn<>("Calculated quantity");

        contextDriverTableView = new TableView<>();

        contextDriverNameColumn = new TableColumn<>("Name");
        contextDriverLicenceNoColumn = new TableColumn<>("Licence number");

        contextTruckTrailerTableView = new TableView<>();

        contextTruckTrailerNoColumn = new TableColumn<>("Number");
        contextTruckTrailerLicenceNoColumn = new TableColumn<>("Licence number");

        contextTruckContainerTableView = new TableView<>();

        contextTruckContainerNoColumn = new TableColumn<>("Number");
        contextTruckContainerLicenceNoColumn = new TableColumn<>("Licence number");

        contextClientsTableView = new TableView<>();

        contextClientNameColumn = new TableColumn<>("Name");
        contextClientContactNameColumn = new TableColumn<>("Contact name");
        contextClientMobileColumn = new TableColumn<>("Mobile number");

        insert = new EnhancedButton("Insert new transaction");
        delete = new EnhancedButton("Delete selected transaction");
        update = new EnhancedButton("Update selected transaction");
        refresh = new EnhancedButton("Refresh data");
        report = new EnhancedButton("Show Report");

        idLabel = new Label("Transaction ID :");
        materialLabel = new Label("Material :");
        tankLabel = new Label("Tank :");
        qtyLabel = new Label("Quantity :");
        clientLabel = new Label("Client :");
        driverLabel = new Label("Driver :");
        stationLabel = new Label("Station :");
        truckTrailerLabel = new Label("Truck trailer :");
        dateTimeLabel = new Label("Date time :");
        truckContainerLabel = new Label("Truck container :");
        operationTypeLabel = new Label("Operation type :");
        creationDateLabel = new Label("Creation Date :");
        modificationLabel = new Label("Modification Date :");
        onTerminalLabel = new Label("On Terminal :");
        createdByLabel = new Label("Created By :");

        contextProductNameLabel = new Label("Name :");
        contextProductDescriptionLabel = new Label("Description :");

        contextStationNameLabel = new Label("Name :");
        contextStationComputerLabel = new Label("Computer :");
        contextStationLocationLabel = new Label("Location :");

        contextTankNameLabel = new Label("Name :");
        contextTankQtyLabel = new Label("Quantity :");
        contextTankCalcQtyLabel = new Label("Calculated quantity :");

        contextDriverNameLabel = new Label("Name :");
        contextDriverLicenceNoLabel = new Label("Licence Number :");

        contextTruckTrailerNoLabel = new Label("Number :");
        contextTruckTrailerLicenceNoLabel = new Label("Licence Number :");

        contextTruckContainerNoLabel = new Label("Number :");
        contextTruckContainerLicenceNoLabel = new Label("Licence Number :");

        contextClientNameLabel = new Label("Name :");
        contextClientContactNameLabel = new Label("Contact name :");
        contextClientMobileLabel = new Label("Mobile number :");

        transactionIdField = new EnhancedLongField();
        tankIdField = new EnhancedLongField();
        driverIdField = new EnhancedLongField();
        stationIdField = new EnhancedLongField();
        materialIdField = new EnhancedLongField();
        clientField = new EnhancedLongField();
        operationTypeComboBox = new ComboBox<>();
        qtyField = new EnhancedDoubleField();
        truckContainerField = new EnhancedLongField();
        dateTimeField = new LocalDateTimeTextField();
        truckTrailerField = new EnhancedLongField();
        creationDateField = new EnhancedTextField();
        modificationDateField = new EnhancedTextField();
        createdByField = new EnhancedTextField();
        onTerminalField = new EnhancedTextField();

        contextProductNameFieldField = new Label();
        contextProductDescriptionField = new Label();

        contextStationNameField = new Label();
        contextStationLocationField = new Label();
        contextStationComputerField = new Label();

        contextTankNameField = new Label();
        contextTankQtyField = new EnhancedDoubleField();
        contextTankCalcQtyField = new EnhancedDoubleField();

        contextDriverNameField = new Label();
        contextDriverLicenceNoField = new Label();

        contextTruckTrailerNoField = new Label();
        contextTruckTrailerLicenceNoField = new Label();

        contextTruckContainerNoField = new Label();
        contextTruckContainerLicenceNoField = new Label();

        contextClientNameField = new Label();
        contextClientContactField = new Label();
        contextClientMobileField = new Label();
    }

    private void graphicsBuilder() {

        mainGrapgicBuilder();
        productsContextGraphicsBuilder();
        stationsContextGraphicsBuilder();
        tanksContextGraphicsBuilder();
        driversContextGraphicsBuilder();
        truckTrailersContextGraphicsBuilder();
        truckContainersContextGraphicsBuilder();
        clientsContextGraphicsBuilder();

        tabContainer.getTabs().addAll(transactionsTab);

        root.setTop(headerLabel);
        root.setCenter(transactionsPane);
        root.setPadding(new Insets(10));
    }

    private void mainGrapgicBuilder() {
        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(root.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        //control buttons configuration
        insert.setPrefWidth(200);
        delete.setPrefWidth(200);
        update.setPrefWidth(200);
        refresh.setPrefWidth(200);
        report.setPrefWidth(200);

        //dataentery region configuration
        idLabel.setPrefWidth(150);
        materialLabel.setPrefWidth(150);
        tankLabel.setPrefWidth(150);
        qtyLabel.setPrefWidth(150);
        driverLabel.setPrefWidth(150);
        clientLabel.setPrefWidth(150);
        stationLabel.setPrefWidth(150);
        truckTrailerLabel.setPrefWidth(150);
        dateTimeLabel.setPrefWidth(150);
        truckContainerLabel.setPrefWidth(150);
        stationLabel.setPrefWidth(150);
        operationTypeLabel.setPrefWidth(150);
        truckContainerLabel.setPrefWidth(150);
        creationDateLabel.setPrefWidth(150);
        modificationLabel.setPrefWidth(150);
        onTerminalLabel.setPrefWidth(150);
        createdByLabel.setPrefWidth(150);

        idLabel.setTextAlignment(TextAlignment.LEFT);
        materialLabel.setTextAlignment(TextAlignment.LEFT);
        tankLabel.setTextAlignment(TextAlignment.LEFT);
        clientLabel.setTextAlignment(TextAlignment.LEFT);
        qtyLabel.setTextAlignment(TextAlignment.LEFT);
        driverLabel.setTextAlignment(TextAlignment.LEFT);
        stationLabel.setTextAlignment(TextAlignment.LEFT);
        truckTrailerLabel.setTextAlignment(TextAlignment.LEFT);
        operationTypeLabel.setTextAlignment(TextAlignment.LEFT);
        dateTimeLabel.setTextAlignment(TextAlignment.LEFT);
        truckContainerLabel.setTextAlignment(TextAlignment.LEFT);
        stationLabel.setTextAlignment(TextAlignment.LEFT);
        truckContainerLabel.setTextAlignment(TextAlignment.LEFT);
        creationDateLabel.setTextAlignment(TextAlignment.LEFT);
        modificationLabel.setTextAlignment(TextAlignment.LEFT);
        onTerminalLabel.setTextAlignment(TextAlignment.LEFT);
        createdByLabel.setTextAlignment(TextAlignment.LEFT);

        idLabel.setAlignment(Pos.BASELINE_LEFT);
        materialLabel.setAlignment(Pos.BASELINE_LEFT);
        tankLabel.setAlignment(Pos.BASELINE_LEFT);
        clientLabel.setAlignment(Pos.BASELINE_LEFT);
        qtyLabel.setAlignment(Pos.BASELINE_LEFT);
        driverLabel.setAlignment(Pos.BASELINE_LEFT);
        stationLabel.setAlignment(Pos.BASELINE_LEFT);
        truckTrailerLabel.setAlignment(Pos.BASELINE_LEFT);
        operationTypeLabel.setAlignment(Pos.BASELINE_LEFT);
        dateTimeLabel.setAlignment(Pos.BASELINE_LEFT);
        truckContainerLabel.setAlignment(Pos.BASELINE_LEFT);
        stationLabel.setAlignment(Pos.BASELINE_LEFT);
        truckContainerLabel.setAlignment(Pos.BASELINE_LEFT);
        creationDateLabel.setAlignment(Pos.BASELINE_LEFT);
        modificationLabel.setAlignment(Pos.BASELINE_LEFT);
        onTerminalLabel.setAlignment(Pos.BASELINE_LEFT);
        createdByLabel.setAlignment(Pos.BASELINE_LEFT);

        idLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        materialLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        tankLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        qtyLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        clientLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        driverLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        stationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        truckTrailerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        operationTypeLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        dateTimeLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        truckContainerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        stationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        truckContainerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        creationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        modificationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        onTerminalLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        createdByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        transactionIdField.setPrefWidth(250);
        tankIdField.setPrefWidth(250);
        driverIdField.setPrefWidth(250);
        clientField.setPrefWidth(250);
        stationIdField.setPrefWidth(250);
        materialIdField.setPrefWidth(250);
        operationTypeComboBox.setPrefWidth(250);
        truckContainerField.setPrefWidth(250);
        operationTypeComboBox.setPrefWidth(250);
        dateTimeField.setPrefWidth(250);
        truckTrailerField.setPrefWidth(250);
        creationDateField.setPrefWidth(250);
        modificationDateField.setPrefWidth(250);
        createdByField.setPrefWidth(250);
        onTerminalField.setPrefWidth(250);

        transactionIdField.setEditable(false);

        creationDateField.setEditable(false);
        modificationDateField.setEditable(false);
        createdByField.setEditable(false);
        onTerminalField.setEditable(false);

        //allocating in the window
        transactionsDataEntry.add(idLabel, 1, 1);
        transactionsDataEntry.add(transactionIdField, 2, 1);

        transactionsDataEntry.add(materialLabel, 3, 1);
        transactionsDataEntry.add(materialIdField, 4, 1);

        transactionsDataEntry.add(stationLabel, 5, 1);
        transactionsDataEntry.add(stationIdField, 6, 1);

        transactionsDataEntry.add(tankLabel, 7, 1);
        transactionsDataEntry.add(tankIdField, 8, 1);

        transactionsDataEntry.add(qtyLabel, 9, 1);
        transactionsDataEntry.add(qtyField, 10, 1);


        transactionsDataEntry.add(driverLabel, 1, 2);
        transactionsDataEntry.add(driverIdField, 2, 2);

        transactionsDataEntry.add(truckTrailerLabel, 3, 2);
        transactionsDataEntry.add(truckTrailerField, 4, 2);

        transactionsDataEntry.add(truckContainerLabel, 5, 2);
        transactionsDataEntry.add(truckContainerField, 6, 2);

        transactionsDataEntry.add(clientLabel, 7, 2);
        transactionsDataEntry.add(clientField, 8, 2);


        transactionsDataEntry.add(operationTypeLabel, 1, 3);
        transactionsDataEntry.add(operationTypeComboBox, 2, 3);

        transactionsDataEntry.add(dateTimeLabel, 3, 3);
        transactionsDataEntry.add(dateTimeField, 4, 3);

        transactionsDataEntry.add(creationDateLabel, 1, 4);
        transactionsDataEntry.add(creationDateField, 2, 4);

        transactionsDataEntry.add(modificationLabel, 3, 4);
        transactionsDataEntry.add(modificationDateField, 4, 4);

        transactionsDataEntry.add(createdByLabel, 5, 4);
        transactionsDataEntry.add(createdByField, 6, 4);

        transactionsDataEntry.add(onTerminalLabel, 7, 4);
        transactionsDataEntry.add(onTerminalField, 8, 4);

        //table configuration
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionIdColumn"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("materialColumn"));
        tankColumn.setCellValueFactory(new PropertyValueFactory<>("tankColumn"));
        driverColumn.setCellValueFactory(new PropertyValueFactory<>("driverColumn"));
        stationColumn.setCellValueFactory(new PropertyValueFactory<>("stationColumn"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("clientColumn"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qtyColumn"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTimeColumn"));
        truckTrailerColumn.setCellValueFactory(new PropertyValueFactory<>("truckTrailerColumn"));
        truckContainerColumn.setCellValueFactory(new PropertyValueFactory<>("truckContainerColumn"));
        operationTypeColumn.setCellValueFactory(new PropertyValueFactory<>("operationTypeColumn"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDateColumn"));
        modifyDateColumn.setCellValueFactory(new PropertyValueFactory<>("modifyDateColumn"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdByColumn"));
        onTerminalColumn.setCellValueFactory(new PropertyValueFactory<>("onTerminalColumn"));

        tableView.getColumns().addAll(transactionIdColumn, materialColumn, stationColumn, tankColumn, clientColumn, driverColumn, truckTrailerColumn, truckContainerColumn, qtyColumn, operationTypeColumn, dateTimeColumn,
                creationDateColumn, modifyDateColumn, createdByColumn, onTerminalColumn);
        tableView.prefHeightProperty().bind(root.heightProperty().subtract(transactionsVbox.heightProperty()));
        tableView.setItems(controller.getDataList());
        tableView.setRowFactory((TableView<TransactionsModel.TableObject> param) -> new EnhancedTableRow());
        tableFilter = TableFilter.forTableView(tableView).apply();

        transactionIdColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(1));
        materialColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(1));
        tankColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(1));
        driverColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(1));
        stationColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(1));
        clientColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(1));
        operationTypeColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(2));
        qtyColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(2));
        truckContainerColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(2));
        dateTimeColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(3));
        truckTrailerColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(2));
        creationDateColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(3));
        modifyDateColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(3));
        createdByColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(3));
        onTerminalColumn.prefWidthProperty().bind(tableView.widthProperty().divide(29).multiply(3));

        operationTypeComboBox.getItems().addAll(FXCollections.observableArrayList(OperationType.values()));

        //stage configuration
        transactionsHbox.getItems().addAll(insert, update, delete/*, new Separator(), refreshTank*/);
        transactionsHbox.setPadding(new Insets(10, 10, 10, 10));

        transactionsVbox.getChildren().addAll(contextProductsDataEntry, contextStationDataEntry, contextTanksDataEntry, contextClientsDataEntry, contextDriversDataEntry, contextTruckTrailersDataEntry, contextTruckContainersDataEntry, transactionsDataEntry, transactionsHbox);
        transactionsVbox.setSpacing(5);
        transactionsVbox.setAlignment(Pos.CENTER);

        transactionsPane.getChildren().addAll(transactionsVbox, tableView);
        transactionsPane.setPadding(new Insets(0));

        transactionsTab.setContent(transactionsPane);
        transactionsTab.setClosable(false);

        transactionIdField.longValueProperty().bindBidirectional(model.transactionIdProperty());
        tankIdField.longValueProperty().bindBidirectional(model.tankProperty());
        driverIdField.longValueProperty().bindBidirectional(model.driverProperty());
        stationIdField.longValueProperty().bindBidirectional(model.stationProperty());
        materialIdField.longValueProperty().bindBidirectional(model.materialProperty());
        operationTypeComboBox.valueProperty().bindBidirectional(model.operationTypeProperty());
        truckContainerField.longValueProperty().bindBidirectional(model.truckContainerProperty());
        clientField.longValueProperty().bindBidirectional(model.clientProperty());
        qtyField.doubleValueProperty().bindBidirectional(model.qtyProperty());
        dateTimeField.localDateTimeProperty().bindBidirectional(model.dateTimeProperty());
        truckTrailerField.longValueProperty().bindBidirectional(model.truckTrailerProperty());
        creationDateField.textProperty().bindBidirectional(model.creationDateProperty());
        modificationDateField.textProperty().bindBidirectional(model.modifyDateProperty());
        createdByField.textProperty().bindBidirectional(model.createdByProperty());
        onTerminalField.textProperty().bindBidirectional(model.onTerminalProperty());

    }

    private void productsContextGraphicsBuilder() {
        //dataentery region configuration
        contextProductNameLabel.setPrefWidth(150);
        contextProductDescriptionLabel.setPrefWidth(150);

        contextProductNameLabel.setTextAlignment(TextAlignment.LEFT);
        contextProductDescriptionLabel.setTextAlignment(TextAlignment.LEFT);

        contextProductNameLabel.setAlignment(Pos.BASELINE_LEFT);
        contextProductDescriptionLabel.setAlignment(Pos.BASELINE_LEFT);

        contextProductNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextProductDescriptionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        contextProductNameFieldField.setPrefWidth(250);
        contextProductDescriptionField.setPrefWidth(250);

        contextProductsDataEntry.add(contextProductNameLabel, 1, 0);
        contextProductsDataEntry.add(contextProductDescriptionLabel, 3, 0);

        contextProductsDataEntry.add(contextProductNameFieldField, 2, 0);
        contextProductsDataEntry.add(contextProductDescriptionField, 4, 0);

        contextProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        contextProductDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionColumn"));

        contextProductTableView.getColumns().addAll(contextProductNameColumn, contextProductDescriptionColumn);
        contextProductTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //stage configuration

        Pane pane = new Pane(contextProductTableView);
        pane.setBorder(new Border(new BorderStroke(Color.valueOf("#0099cc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        pane.setPadding(new Insets(0));
        contextProductStage.setScene(new Scene(pane));
        contextProductStage.initStyle(StageStyle.UNDECORATED);
        contextProductStage.initOwner(mainWindow);

        contextProductTableView.prefWidthProperty().bind(contextProductStage.widthProperty());
        contextProductTableView.prefHeightProperty().bind(contextProductStage.heightProperty());

        materialIdField.longValueProperty().bindBidirectional(materialsModel.materialIdProperty());
        contextProductNameFieldField.textProperty().bindBidirectional(materialsModel.nameProperty());
        contextProductDescriptionField.textProperty().bindBidirectional(materialsModel.descriptionProperty());
    }


    private void stationsContextGraphicsBuilder() {

        //dataentery region configuration
        contextStationNameLabel.setPrefWidth(150);
        contextStationLocationLabel.setPrefWidth(150);
        contextStationLocationLabel.setPrefWidth(150);

        contextStationNameLabel.setTextAlignment(TextAlignment.LEFT);
        contextStationLocationLabel.setTextAlignment(TextAlignment.LEFT);
        contextStationComputerLabel.setTextAlignment(TextAlignment.LEFT);

        contextStationNameLabel.setAlignment(Pos.BASELINE_LEFT);
        contextStationLocationLabel.setAlignment(Pos.BASELINE_LEFT);
        contextStationComputerLabel.setAlignment(Pos.BASELINE_LEFT);

        contextStationNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextStationLocationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextStationComputerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        contextStationNameField.setPrefWidth(250);
        contextStationLocationField.setPrefWidth(250);
        contextStationComputerField.setPrefWidth(250);

        contextStationDataEntry.add(contextStationNameLabel, 1, 0);
        contextStationDataEntry.add(contextStationLocationLabel, 3, 0);
        contextStationDataEntry.add(contextStationComputerLabel, 5, 0);

        contextStationDataEntry.add(contextStationNameField, 2, 0);
        contextStationDataEntry.add(contextStationLocationField, 4, 0);
        contextStationDataEntry.add(contextStationComputerField, 6, 0);

        contextStationNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        contextStationLocationColumn.setCellValueFactory(new PropertyValueFactory<>("locationColumn"));
        contextStationComputerColumn.setCellValueFactory(new PropertyValueFactory<>("computerNameColumn"));

        contextStationsTableView.getColumns().addAll(contextStationNameColumn, contextStationLocationColumn, contextStationComputerColumn);
        contextStationsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //stage configuration

        Pane pane = new Pane(contextStationsTableView);
        pane.setBorder(new Border(new BorderStroke(Color.valueOf("#0099cc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        pane.setPadding(new Insets(2, 2, 2, 2));
        contextStationStage.setScene(new Scene(pane));
        contextStationStage.initStyle(StageStyle.UNDECORATED);
        contextStationStage.initOwner(mainWindow);

        contextStationsTableView.prefWidthProperty().bind(contextStationStage.widthProperty());
        contextStationsTableView.prefHeightProperty().bind(contextStationStage.heightProperty());

        stationIdField.longValueProperty().bindBidirectional(stationsModel.stationIdProperty());
        contextStationNameField.textProperty().bindBidirectional(stationsModel.nameProperty());
        contextStationLocationField.textProperty().bindBidirectional(stationsModel.locationProperty());
        contextStationComputerField.textProperty().bindBidirectional(stationsModel.commentProperty());
    }

    private void truckContainersContextGraphicsBuilder() {
        //dataentery region configuration
        contextTruckContainerNoLabel.setPrefWidth(150);
        contextTruckContainerLicenceNoLabel.setPrefWidth(150);

        contextTruckContainerNoLabel.setTextAlignment(TextAlignment.LEFT);
        contextTruckContainerLicenceNoLabel.setTextAlignment(TextAlignment.LEFT);

        contextTruckContainerNoLabel.setAlignment(Pos.BASELINE_LEFT);
        contextTruckContainerLicenceNoLabel.setAlignment(Pos.BASELINE_LEFT);

        contextTruckContainerNoLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextTruckContainerLicenceNoLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        contextTruckContainerNoField.setPrefWidth(250);
        contextTruckContainerLicenceNoField.setPrefWidth(250);

        contextTruckContainersDataEntry.add(contextTruckContainerNoLabel, 1, 0);
        contextTruckContainersDataEntry.add(contextTruckContainerLicenceNoLabel, 3, 0);

        contextTruckContainersDataEntry.add(contextTruckContainerNoField, 2, 0);
        contextTruckContainersDataEntry.add(contextTruckContainerLicenceNoField, 4, 0);

        contextTruckContainerNoColumn.setCellValueFactory(new PropertyValueFactory<>("containerNumberColumn"));
        contextTruckContainerLicenceNoColumn.setCellValueFactory(new PropertyValueFactory<>("licenseNumberColumn"));

        contextTruckContainerTableView.getColumns().addAll(contextTruckContainerNoColumn, contextTruckContainerLicenceNoColumn);
        contextTruckContainerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //stage configuration

        Pane pane = new Pane(contextTruckContainerTableView);
        pane.setBorder(new Border(new BorderStroke(Color.valueOf("#0099cc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        pane.setPadding(new Insets(2, 2, 2, 2));
        contextTruckContainersStage.setScene(new Scene(pane));
        contextTruckContainersStage.initStyle(StageStyle.UNDECORATED);
        contextTruckContainersStage.initOwner(mainWindow);

        contextTruckContainerTableView.prefWidthProperty().bind(contextTruckContainersStage.widthProperty());
        contextTruckContainerTableView.prefHeightProperty().bind(contextTruckContainersStage.heightProperty());

        truckContainerField.longValueProperty().bindBidirectional(truckContainerModel.truckContainerIdProperty());
        contextTruckContainerNoField.textProperty().bindBidirectional(truckContainerModel.containerNumberProperty());
        contextTruckContainerLicenceNoField.textProperty().bindBidirectional(truckContainerModel.licenseNumberProperty());
    }

    private void truckTrailersContextGraphicsBuilder() {
        //dataentery region configuration
        contextTruckTrailerNoLabel.setPrefWidth(150);
        contextTruckTrailerLicenceNoLabel.setPrefWidth(150);

        contextTruckTrailerNoLabel.setTextAlignment(TextAlignment.LEFT);
        contextTruckTrailerLicenceNoLabel.setTextAlignment(TextAlignment.LEFT);

        contextTruckTrailerNoLabel.setAlignment(Pos.BASELINE_LEFT);
        contextTruckTrailerLicenceNoLabel.setAlignment(Pos.BASELINE_LEFT);

        contextTruckTrailerNoLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextTruckTrailerLicenceNoLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        contextTruckTrailerNoField.setPrefWidth(250);
        contextTruckTrailerLicenceNoField.setPrefWidth(250);

        contextTruckTrailersDataEntry.add(contextTruckTrailerNoLabel, 1, 0);
        contextTruckTrailersDataEntry.add(contextTruckTrailerLicenceNoLabel, 3, 0);

        contextTruckTrailersDataEntry.add(contextTruckTrailerNoField, 2, 0);
        contextTruckTrailersDataEntry.add(contextTruckTrailerLicenceNoField, 4, 0);

        contextTruckTrailerNoColumn.setCellValueFactory(new PropertyValueFactory<>("trailerNumberColumn"));
        contextTruckTrailerLicenceNoColumn.setCellValueFactory(new PropertyValueFactory<>("licenseNumberColumn"));

        contextTruckTrailerTableView.getColumns().addAll(contextTruckTrailerNoColumn, contextTruckTrailerLicenceNoColumn);
        contextTruckTrailerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //stage configuration

        Pane pane = new Pane(contextTruckTrailerTableView);
        pane.setBorder(new Border(new BorderStroke(Color.valueOf("#0099cc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        pane.setPadding(new Insets(2, 2, 2, 2));
        contextTruckTrailersStage.setScene(new Scene(pane));
        contextTruckTrailersStage.initStyle(StageStyle.UNDECORATED);
        contextTruckTrailersStage.initOwner(mainWindow);

        contextTruckTrailerTableView.prefWidthProperty().bind(contextTruckTrailersStage.widthProperty());
        contextTruckTrailerTableView.prefHeightProperty().bind(contextTruckTrailersStage.heightProperty());

        truckTrailerField.longValueProperty().bindBidirectional(truckTrailerModel.truckTrailerIdProperty());
        contextTruckTrailerNoField.textProperty().bindBidirectional(truckTrailerModel.trailerNumberProperty());
        contextTruckTrailerLicenceNoField.textProperty().bindBidirectional(truckTrailerModel.licenseNumberProperty());

    }

    private void driversContextGraphicsBuilder() {
        //dataentery region configuration
        contextDriverNameLabel.setPrefWidth(150);
        contextDriverLicenceNoLabel.setPrefWidth(150);

        contextDriverNameLabel.setTextAlignment(TextAlignment.LEFT);
        contextDriverLicenceNoLabel.setTextAlignment(TextAlignment.LEFT);

        contextDriverNameLabel.setAlignment(Pos.BASELINE_LEFT);
        contextDriverLicenceNoLabel.setAlignment(Pos.BASELINE_LEFT);

        contextDriverNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextDriverLicenceNoLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        contextDriverNameField.setPrefWidth(250);
        contextDriverLicenceNoField.setPrefWidth(250);

        contextDriversDataEntry.add(contextDriverNameLabel, 1, 0);
        contextDriversDataEntry.add(contextDriverLicenceNoLabel, 3, 0);

        contextDriversDataEntry.add(contextDriverNameField, 2, 0);
        contextDriversDataEntry.add(contextDriverLicenceNoField, 4, 0);

        contextDriverNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        contextDriverLicenceNoColumn.setCellValueFactory(new PropertyValueFactory<>("licenseNumberColumn"));

        contextDriverTableView.getColumns().addAll(contextDriverNameColumn, contextDriverLicenceNoColumn);
        contextDriverTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //stage configuration

        Pane pane = new Pane(contextDriverTableView);
        pane.setBorder(new Border(new BorderStroke(Color.valueOf("#0099cc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        pane.setPadding(new Insets(2, 2, 2, 2));
        contextDriversStage.setScene(new Scene(pane));
        contextDriversStage.initStyle(StageStyle.UNDECORATED);
        contextDriversStage.initOwner(mainWindow);

        contextDriverTableView.prefWidthProperty().bind(contextDriversStage.widthProperty());
        contextDriverTableView.prefHeightProperty().bind(contextDriversStage.heightProperty());

        driverIdField.longValueProperty().bindBidirectional(driversModel.driverIdProperty());
        contextDriverNameField.textProperty().bindBidirectional(driversModel.nameProperty());
        contextDriverLicenceNoField.textProperty().bindBidirectional(driversModel.licenseNumberProperty());
    }

    private void tanksContextGraphicsBuilder() {
        //dataentery region configuration
        contextTankNameLabel.setPrefWidth(150);
        contextTankQtyLabel.setPrefWidth(150);
        contextTankCalcQtyLabel.setPrefWidth(150);

        contextTankNameLabel.setTextAlignment(TextAlignment.LEFT);
        contextTankCalcQtyLabel.setTextAlignment(TextAlignment.LEFT);
        contextTankQtyLabel.setTextAlignment(TextAlignment.LEFT);

        contextTankNameLabel.setAlignment(Pos.BASELINE_LEFT);
        contextTankQtyLabel.setAlignment(Pos.BASELINE_LEFT);
        contextTankCalcQtyLabel.setAlignment(Pos.BASELINE_LEFT);

        contextTankNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextTankQtyLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextTankCalcQtyLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        contextTankQtyField.setStyle("-fx-background-color:transparent;-fx-border-color: transparent;");
        contextTankCalcQtyField.setStyle("-fx-background-color:transparent;-fx-border-color: transparent;");

        contextTankNameField.setPrefWidth(250);
        contextTankQtyField.setPrefWidth(250);
        contextTankCalcQtyField.setPrefWidth(250);

        contextTanksDataEntry.add(contextTankNameLabel, 1, 0);
        contextTanksDataEntry.add(contextTankQtyLabel, 3, 0);
        contextTanksDataEntry.add(contextTankCalcQtyLabel, 5, 0);

        contextTanksDataEntry.add(contextTankNameField, 2, 0);
        contextTanksDataEntry.add(contextTankQtyField, 4, 0);
        contextTanksDataEntry.add(contextTankCalcQtyField, 6, 0);

        contextTankNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        contextTankQtyColumn.setCellValueFactory(new PropertyValueFactory<>("qtyColumn"));
        contextTankCalcQtyColumn.setCellValueFactory(new PropertyValueFactory<>("calculatedQtyColumn"));

        contextTanksTableView.getColumns().addAll(contextTankNameColumn, contextTankQtyColumn, contextTankCalcQtyColumn);
        contextTanksTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //stage configuration

        Pane pane = new Pane(contextTanksTableView);
        pane.setBorder(new Border(new BorderStroke(Color.valueOf("#0099cc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        pane.setPadding(new Insets(2, 2, 2, 2));
        contextTanksStage.setScene(new Scene(pane));
        contextTanksStage.initStyle(StageStyle.UNDECORATED);
        contextTanksStage.initOwner(mainWindow);

        contextTanksTableView.prefWidthProperty().bind(contextTanksStage.widthProperty());
        contextTanksTableView.prefHeightProperty().bind(contextTanksStage.heightProperty());

        tankIdField.longValueProperty().bindBidirectional(tanksModel.tankIdProperty());
        contextTankNameField.textProperty().bindBidirectional(tanksModel.nameProperty());
        contextTankQtyField.doubleValueProperty().bindBidirectional(tanksModel.qtyProperty());
        contextTankCalcQtyField.doubleValueProperty().bindBidirectional(tanksModel.calculatedQtyProperty());

    }

    private void clientsContextGraphicsBuilder() {
        //dataentery region configuration
        contextClientNameLabel.setPrefWidth(150);
        contextClientContactNameLabel.setPrefWidth(150);
        contextClientMobileLabel.setPrefWidth(150);

        contextClientNameLabel.setTextAlignment(TextAlignment.LEFT);
        contextClientMobileLabel.setTextAlignment(TextAlignment.LEFT);
        contextClientContactNameLabel.setTextAlignment(TextAlignment.LEFT);

        contextClientNameLabel.setAlignment(Pos.BASELINE_LEFT);
        contextClientContactNameLabel.setAlignment(Pos.BASELINE_LEFT);
        contextClientMobileLabel.setAlignment(Pos.BASELINE_LEFT);

        contextClientNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextClientContactNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextClientMobileLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        contextClientNameField.setPrefWidth(250);
        contextClientContactField.setPrefWidth(250);
        contextClientMobileField.setPrefWidth(250);

        contextClientsDataEntry.add(contextClientNameLabel, 1, 0);
        contextClientsDataEntry.add(contextClientContactNameLabel, 3, 0);
        contextClientsDataEntry.add(contextClientMobileLabel, 5, 0);

        contextClientsDataEntry.add(contextClientNameField, 2, 0);
        contextClientsDataEntry.add(contextClientContactField, 4, 0);
        contextClientsDataEntry.add(contextClientMobileField, 6, 0);

        contextClientNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        contextClientContactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactNameColumn"));
        contextClientMobileColumn.setCellValueFactory(new PropertyValueFactory<>("contactTelNumberColumn"));

        contextClientsTableView.getColumns().addAll(contextClientNameColumn, contextClientContactNameColumn, contextClientMobileColumn);
        contextClientsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //stage configuration
        Pane pane = new Pane(contextClientsTableView);
        pane.setBorder(new Border(new BorderStroke(Color.valueOf("#0099cc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        pane.setPadding(new Insets(2, 2, 2, 2));
        contextClientsStage.setScene(new Scene(pane));
        contextClientsStage.initStyle(StageStyle.UNDECORATED);
        contextClientsStage.initOwner(mainWindow);

        contextClientsTableView.prefWidthProperty().bind(contextClientsStage.widthProperty());
        contextClientsTableView.prefHeightProperty().bind(contextClientsStage.heightProperty());

        clientField.longValueProperty().bindBidirectional(clientsModel.clientIdProperty());
        contextClientNameField.textProperty().bindBidirectional(clientsModel.nameProperty());
        contextClientContactField.textProperty().bindBidirectional(clientsModel.contactNameProperty());
        contextClientMobileField.textProperty().bindBidirectional(clientsModel.contactTelNumberProperty());

    }


    private void actionHandling() {
        mainActionHandling();
        mainWindow.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (contextProductStage.isShowing()) {
                contextProductStage.close();
            }
            if (contextStationStage.isShowing()) {
                contextStationStage.close();
            }
            if (contextTanksStage.isShowing()) {
                contextTanksStage.close();
            }
            if (contextTruckTrailersStage.isShowing()) {
                contextTruckTrailersStage.close();
            }
            if (contextTruckContainersStage.isShowing()) {
                contextTruckContainersStage.close();
            }
            if (contextClientsStage.isShowing()) {
                contextClientsStage.close();
            }
        });

    }

    private void mainActionHandling() {
        insert.setOnMouseClicked(controller::onInsert);
        delete.setOnMouseClicked(controller::onDelete);
        update.setOnMouseClicked(controller::onUpdate);
        refresh.setOnMouseClicked(action -> update());

        tableView.setOnMouseClicked((a) -> {
            controller.onTableSelection(tableView.getSelectionModel().getSelectedItems());
        });

        //managing context
        materialIdField.setOnMouseClicked(this::onRunProductContextWidow);
        contextProductTableView.setOnMouseClicked(action -> {

            if (action.getClickCount() == 2) {
                MaterialsModel.TableObject selected = contextProductTableView.getSelectionModel().getSelectedItem();
                controller.setProductData(selected);
                contextProductStage.close();
            }
        });

        stationIdField.setOnMouseClicked(this::onRunStationContextWidow);
        contextStationsTableView.setOnMouseClicked(action -> {

            if (action.getClickCount() == 2) {
                StationsModel.TableObject selected = contextStationsTableView.getSelectionModel().getSelectedItem();
                controller.setStationData(selected);

                contextStationStage.close();
            }
        });

        driverIdField.setOnMouseClicked(this::onRunDriverContextWidow);
        contextDriverTableView.setOnMouseClicked(action -> {

            if (action.getClickCount() == 2) {
                DriversModel.TableObject selected = contextDriverTableView.getSelectionModel().getSelectedItem();
                controller.setDriverData(selected);

                contextDriversStage.close();
            }
        });

        truckTrailerField.setOnMouseClicked(this::onRunTruckTrailerContextWidow);
        contextTruckTrailerTableView.setOnMouseClicked(action -> {

            if (action.getClickCount() == 2) {
                TruckTrailerModel.TableObject selected = contextTruckTrailerTableView.getSelectionModel().getSelectedItem();
                controller.setTruckTrailerData(selected);

                contextTruckTrailersStage.close();
            }
        });

        truckContainerField.setOnMouseClicked(this::onRunTruckContainerContextWidow);
        contextTruckContainerTableView.setOnMouseClicked(action -> {

            if (action.getClickCount() == 2) {
                TruckContainerModel.TableObject selected = contextTruckContainerTableView.getSelectionModel().getSelectedItem();
                controller.setTruckContainerData(selected);

                contextTruckContainersStage.close();
            }
        });

        tankIdField.setOnMouseClicked(this::onRunTankContextWidow);
        contextTanksTableView.setOnMouseClicked(action -> {

            if (action.getClickCount() == 2) {
                TanksModel.TableObject selected = contextTanksTableView.getSelectionModel().getSelectedItem();
                controller.setTankData(selected);

                contextTanksStage.close();
            }
        });

        clientField.setOnMouseClicked(this::onRunClientContextWidow);
        contextClientsTableView.setOnMouseClicked(action -> {

            if (action.getClickCount() == 2) {
                ClientsModel.TableObject selected = contextClientsTableView.getSelectionModel().getSelectedItem();
                controller.setClientData(selected);

                contextClientsStage.close();
            }
        });

        mainWindow.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (contextProductStage.isShowing()) {
                contextProductStage.close();
            }
            if (contextStationStage.isShowing()) {
                contextStationStage.close();
            }
            if (contextDriversStage.isShowing()) {
                contextDriversStage.close();
            }
            if (contextTanksStage.isShowing()) {
                contextTanksStage.close();
            }
            if (contextTruckContainersStage.isShowing()) {
                contextTruckContainersStage.close();
            }
            if (contextTruckTrailersStage.isShowing()) {
                contextTruckTrailersStage.close();
            }
            if (contextClientsStage.isShowing()) {
                contextClientsStage.close();
            }
        });
    }

    private void onRunDriverContextWidow(MouseEvent action) {
        Platform.runLater(() -> {
            contextDriverTableView.getItems().removeAll();
            contextDriverTableView.setItems(controller.getDriverContextList());
            contextDriverTableFilter = TableFilter.forTableView(contextDriverTableView).apply();
            contextDriversStage.setWidth(600);
            contextDriversStage.setHeight(500);
            contextDriversStage.setResizable(false);
            contextDriversStage.setX(action.getScreenX());
            contextDriversStage.setY(action.getScreenY());
            contextDriversStage.show();
        });
    }

    private void onRunTruckContainerContextWidow(MouseEvent action) {
        Platform.runLater(() -> {
            contextTruckContainerTableView.getItems().removeAll();
            contextTruckContainerTableView.setItems(controller.getTruckContainerContextList());
            contextTruckContainerFilter = TableFilter.forTableView(contextTruckContainerTableView).apply();
            contextTruckContainersStage.setWidth(600);
            contextTruckContainersStage.setHeight(500);
            contextTruckContainersStage.setResizable(false);
            contextTruckContainersStage.setX(action.getScreenX());
            contextTruckContainersStage.setY(action.getScreenY());
            contextTruckContainersStage.show();
        });
    }

    private void onRunTruckTrailerContextWidow(MouseEvent action) {
        Platform.runLater(() -> {
            contextTruckTrailerTableView.getItems().removeAll();
            contextTruckTrailerTableView.setItems(controller.getTruckTrailerContextList());
            contextTruckTrailerTableFilter = TableFilter.forTableView(contextTruckTrailerTableView).apply();
            contextTruckTrailersStage.setWidth(600);
            contextTruckTrailersStage.setHeight(500);
            contextTruckTrailersStage.setResizable(false);
            contextTruckTrailersStage.setX(action.getScreenX());
            contextTruckTrailersStage.setY(action.getScreenY());
            contextTruckTrailersStage.show();
        });
    }

    private void onRunTankContextWidow(MouseEvent action) {
        Platform.runLater(() -> {
            contextTanksTableView.getItems().removeAll();
            contextTanksTableView.setItems(controller.getTanksContextList());
            contextTanksTableFilter = TableFilter.forTableView(contextTanksTableView).apply();
            contextTanksStage.setWidth(600);
            contextTanksStage.setHeight(500);
            contextTanksStage.setResizable(false);
            contextTanksStage.setX(action.getScreenX() - 75);
            contextTanksStage.setY(action.getScreenY());
            contextTanksStage.show();
        });
    }

    private void onRunProductContextWidow(MouseEvent action) {
        Platform.runLater(() -> {
            contextProductTableView.getItems().removeAll();
            contextProductTableView.setItems(controller.getMaterialContextList());
            contextProductTableFilter = TableFilter.forTableView(contextProductTableView).apply();
            contextProductStage.setWidth(600);
            contextProductStage.setHeight(500);
            contextProductStage.setResizable(false);
            contextProductStage.setX(action.getScreenX());
            contextProductStage.setY(action.getScreenY());
            contextProductStage.show();
        });
    }

    private void onRunStationContextWidow(MouseEvent action) {
        Platform.runLater(() -> {
            contextStationsTableView.getItems().removeAll();
            contextStationsTableView.setItems(controller.getStationContextList());
            contextStationsTableFilter = TableFilter.forTableView(contextStationsTableView).apply();
            contextStationStage.setWidth(600);
            contextStationStage.setHeight(500);
            contextStationStage.setResizable(false);
            contextStationStage.setX(action.getScreenX());
            contextStationStage.setY(action.getScreenY());
            contextStationStage.show();
        });
    }

    private void onRunClientContextWidow(MouseEvent action) {
        Platform.runLater(() -> {
            contextClientsTableView.getItems().removeAll();
            contextClientsTableView.setItems(controller.getClientsContextList());
            contextClientsFilter = TableFilter.forTableView(contextClientsTableView).apply();
            contextClientsStage.setWidth(600);
            contextClientsStage.setHeight(500);
            contextClientsStage.setResizable(false);
            contextClientsStage.setX(action.getScreenX() - 75);
            contextClientsStage.setY(action.getScreenY());
            contextClientsStage.show();
        });
    }

    public synchronized void update() {
        ReadOnlyBooleanProperty update = controller.update();
        tableView.cursorProperty().bind(Bindings.when(update).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));
        refresh.disableProperty().bind(update);
    }

    public Node getTabContainer() {
        return root;
    }

    @Override
    public String toString() {
        return "Transactions";
    }
}
