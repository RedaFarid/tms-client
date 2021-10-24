package soulintec.com.tmsclient.Graphics.Windows.TruckWindow;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
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
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Graphics.Controls.DataEntryPartitionTitled;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedButton;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedLongField;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedTextField;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindowController;
import soulintec.com.tmsclient.Services.TruckContainerService;
import soulintec.com.tmsclient.Services.TruckTrailerService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TruckView implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private TruckController controller;
    private MainWindowController mainWindowController;
    private TruckContainerModel containerModel;
    private TruckTrailerModel trailerModel;


    protected static MainWindow initialStage;

    private DataEntryPartitionTitled trailersDataEntry;
    private DataEntryPartitionTitled containersDataEntry;

    private Tab trailersTab;
    private Tab containersTab;

    private VBox trailersPane;
    private VBox containersPane;

    private VBox trailersVbox;
    private ToolBar trailersHbox;

    private VBox containersVvox;
    private ToolBar containersHbox;

    private BorderPane root;
    private TabPane tabContainer;
    private TabPane tabTrailer;
    private Label headerLabel;

    private Stage mainWindow;

    private TableFilter<TruckTrailerModel.TableObject> trailerTableFilter;

    private TableView<TruckTrailerModel.TableObject> trailersTableView;
    private TableColumn<TruckTrailerModel.TableObject, LongProperty> trailerIdColumn;
    private TableColumn<TruckTrailerModel.TableObject, StringProperty> trailerNumColumn;
    private TableColumn<TruckTrailerModel.TableObject, StringProperty> trailerLicenceColumn;
    private TableColumn<TruckTrailerModel.TableObject, StringProperty> trailerLicenceExpirationDateColumn;
    private TableColumn<TruckTrailerModel.TableObject, StringProperty> trailerPermissionsColumn;
    private TableColumn<TruckTrailerModel.TableObject, StringProperty> trailerCommentColumn;
    private TableColumn<TruckTrailerModel.TableObject, StringProperty> trailerCreatedByColumn;
    private TableColumn<TruckTrailerModel.TableObject, StringProperty> trailerModifiedByColumn;
    private TableColumn<TruckTrailerModel.TableObject, StringProperty> trailerOnTerminalColumn;
    private TableColumn<TruckTrailerModel.TableObject, ObjectProperty<LocalDateTime>> trailerCreationDateColumn;
    private TableColumn<TruckTrailerModel.TableObject, ObjectProperty<LocalDateTime>> trailerModifyDateColumn;

    private TableFilter<TruckContainerModel.TableObject> containerTableFilter;

    private TableView<TruckContainerModel.TableObject> containersTableView;
    private TableColumn<TruckContainerModel.TableObject, LongProperty> containerIdColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerNumColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerLicenceColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerLicenceExpirationDateColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerMaximumWeightColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerPermissionsColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerCommentColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerCreatedByColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerModifiedByColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerOnTerminalColumn;
    private TableColumn<TruckContainerModel.TableObject, ObjectProperty<LocalDateTime>> containerCreationDateColumn;
    private TableColumn<TruckContainerModel.TableObject, ObjectProperty<LocalDateTime>> containerModifyDateColumn;

    private EnhancedButton insertTrailer;
    private EnhancedButton deleteTrailer;
    private EnhancedButton updateTrailer;
    private EnhancedButton trailerReport;

    private EnhancedButton insertContainer;
    private EnhancedButton deleteContainer;
    private EnhancedButton updateContainer;
    private EnhancedButton containersReport;

    private Label trailerIdLabel;
    private Label trailerNumberLabel;
    private Label trailerLicenceLabel;
    private Label trailerLicenceExpirationDateLabel;
    private Label trailerPermissionLabel;
    private Label trailerCommentLabel;
    private Label trailerCreationDateLabel;
    private Label trailerModificationLabel;
    private Label trailerOnTerminalLabel;
    private Label trailerCreatedByLabel;
    private Label trailerModifiedByLabel;

    private Label containerIdLabel;
    private Label containerNumberLabel;
    private Label containerLicenceLabel;
    private Label containerLicenceExpirationDateLabel;
    private Label containerMaxWeightLabel;
    private Label containerPermissionLabel;
    private Label containerCommentLabel;
    private Label containerCreationDateLabel;
    private Label containerModificationLabel;
    private Label containerOnTerminalLabel;
    private Label containerCreatedByLabel;
    private Label containerModifiedByLabel;

    private EnhancedLongField trailerIdField;
    private EnhancedTextField trailerNumberField;
    private EnhancedTextField trailerLicenceNumberField;
    private DatePicker trailerLicenceExpirationDateField;
    private ComboBox trailerPermissionField;
    private EnhancedTextField trailerCommentField;
    private EnhancedTextField trailerCreationDateField;
    private EnhancedTextField trailerModificationDateField;
    private EnhancedTextField trailerCreatedByField;
    private EnhancedTextField trailerModifiedByField;
    private EnhancedTextField trailerOnTerminalField;

    private EnhancedLongField containerIdField;
    private EnhancedTextField containerNumberField;
    private EnhancedTextField containerLicenceNumberField;
    private DatePicker containerLicenceExpirationDateField;
    private EnhancedLongField containerMaxWeightField;
    private ComboBox containerPermissionField;
    private EnhancedTextField containerCommentField;
    private EnhancedTextField containerCreationDateField;
    private EnhancedTextField containerModificationDateField;
    private EnhancedTextField containerCreatedByField;
    private EnhancedTextField containerModifiedByField;
    private EnhancedTextField containerOnTerminalField;

    @Autowired
    private TruckContainerService truckContainerService;

    @Autowired
    private TruckTrailerService truckTrailerService;

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);

    public List<RoleDTO> authorityDTOSList = new ArrayList<>();

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainWindow = event.getStage();
        initialization();
        graphicsBuilder();
        actionHandling();
        userAuthorities();
    }

    private void initialization() {
        initialStage = ApplicationContext.applicationContext.getBean(MainWindow.class);
        controller = ApplicationContext.applicationContext.getBean(TruckController.class);
        mainWindowController = ApplicationContext.applicationContext.getBean(MainWindowController.class);

        containerModel = controller.getTruckContainerModel();
        trailerModel = controller.getTruckTrailerModel();

        trailersDataEntry = new DataEntryPartitionTitled("Trailer");
        containersDataEntry = new DataEntryPartitionTitled("Container");

        trailersTab = new Tab("Trailers Management");
        containersTab = new Tab("Containers Management");
        trailersPane = new VBox();
        containersPane = new VBox();

        trailersVbox = new VBox();
        trailersHbox = new ToolBar();
        containersVvox = new VBox();
        containersHbox = new ToolBar();

        root = new BorderPane();
        tabContainer = new TabPane();
        headerLabel = new Label("Trucks");

        //Trailers
        trailersTableView = new TableView<>();
        trailerIdColumn = new TableColumn<>("Trailer Id");
        trailerNumColumn = new TableColumn<>("Number");
        trailerLicenceColumn = new TableColumn<>("Licence");
        trailerLicenceExpirationDateColumn = new TableColumn<>("LicenceExpiration date");
        trailerPermissionsColumn = new TableColumn<>("Permissions");
        trailerCommentColumn = new TableColumn<>("Comment");
        trailerCreatedByColumn = new TableColumn<>("Created By");
        trailerModifiedByColumn = new TableColumn<>("Modified By");
        trailerOnTerminalColumn = new TableColumn<>("On Terminal");
        trailerCreationDateColumn = new TableColumn<>("Creation Date");
        trailerModifyDateColumn = new TableColumn<>("Modification Date");

        insertTrailer = new EnhancedButton("Insert new trailer");
        deleteTrailer = new EnhancedButton("Delete selected trailer");
        updateTrailer = new EnhancedButton("Update selected trailer");
        trailerReport = new EnhancedButton("Show Report");

        trailerIdLabel = new Label("Trailer Id :");
        trailerNumberLabel = new Label("Trailer Number :");
        trailerLicenceLabel = new Label("Licence Number :");
        trailerLicenceExpirationDateLabel = new Label("Licence Expiry :");
        trailerPermissionLabel = new Label("Permission :");
        trailerCommentLabel = new Label("Comment :");
        trailerCreationDateLabel = new Label("Creation Date :");
        trailerModificationLabel = new Label("Modification Date :");
        trailerOnTerminalLabel = new Label("On Terminal :");
        trailerCreatedByLabel = new Label("Created By :");
        trailerModifiedByLabel = new Label("Modified By :");

        trailerIdField = new EnhancedLongField();
        trailerNumberField = new EnhancedTextField();
        trailerLicenceNumberField = new EnhancedTextField();
        trailerLicenceExpirationDateField = new DatePicker();
        trailerPermissionField = new ComboBox();
        trailerCommentField = new EnhancedTextField();
        trailerCreationDateField = new EnhancedTextField();
        trailerModificationDateField = new EnhancedTextField();
        trailerCreatedByField = new EnhancedTextField();
        trailerOnTerminalField = new EnhancedTextField();
        trailerModifiedByField = new EnhancedTextField();

        //Containers
        containersTableView = new TableView<>();
        containerIdColumn = new TableColumn<>("Container Id");
        containerNumColumn = new TableColumn<>("Number");
        containerLicenceColumn = new TableColumn<>("Licence");
        containerLicenceExpirationDateColumn = new TableColumn<>("LicenceExpiration date");
        containerMaximumWeightColumn = new TableColumn<>("Maximum Weight");
        containerPermissionsColumn = new TableColumn<>("Permissions");
        containerCommentColumn = new TableColumn<>("Comment");
        containerCreatedByColumn = new TableColumn<>("Created By");
        containerModifiedByColumn = new TableColumn<>("Modified By");
        containerOnTerminalColumn = new TableColumn<>("On Terminal");
        containerCreationDateColumn = new TableColumn<>("Creation Date");
        containerModifyDateColumn = new TableColumn<>("Modification Date");

        insertContainer = new EnhancedButton("Insert new container");
        deleteContainer = new EnhancedButton("Delete selected container");
        updateContainer = new EnhancedButton("Update selected container");
        containersReport = new EnhancedButton("Show Report");

        containerIdLabel = new Label("Container Id :");
        containerNumberLabel = new Label("Container Number :");
        containerLicenceLabel = new Label("Licence Number :");
        containerLicenceExpirationDateLabel = new Label("Licence Expiry :");
        containerMaxWeightLabel = new Label("Maximum Weight :");
        containerPermissionLabel = new Label("Permission :");
        containerCommentLabel = new Label("Comment :");
        containerCreationDateLabel = new Label("Creation Date :");
        containerModificationLabel = new Label("Modification Date :");
        containerOnTerminalLabel = new Label("On Terminal :");
        containerCreatedByLabel = new Label("Created By :");
        containerModifiedByLabel = new Label("Modified By :");

        containerIdField = new EnhancedLongField();
        containerNumberField = new EnhancedTextField();
        containerLicenceNumberField = new EnhancedTextField();
        containerLicenceExpirationDateField = new DatePicker();
        containerMaxWeightField = new EnhancedLongField();
        containerPermissionField = new ComboBox();
        containerCommentField = new EnhancedTextField();
        containerCreationDateField = new EnhancedTextField();
        containerModificationDateField = new EnhancedTextField();
        containerCreatedByField = new EnhancedTextField();
        containerModifiedByField = new EnhancedTextField();
        containerOnTerminalField = new EnhancedTextField();
    }

    private void userAuthorities() {
        createWindowAuthoritiesTemplate();
        assignAuthoritiesTemplate();
    }

    private void createWindowAuthoritiesTemplate() {
        authorityDTOSList.clear();
        //Authorities
        RoleDTO savingTrailer = new RoleDTO("Save Truck Trailers");
        RoleDTO deletingTrailer = new RoleDTO("Delete Truck Trailers");
        RoleDTO savingContainer = new RoleDTO("Save Truck Containers");
        RoleDTO deletingContainer = new RoleDTO("Delete Truck Containers");

        authorityDTOSList.add(savingTrailer);
        authorityDTOSList.add(deletingTrailer);
        authorityDTOSList.add(savingContainer);
        authorityDTOSList.add(deletingContainer);

        mainWindowController.createWindowAuthorities(authorityDTOSList);
    }

    private void assignAuthoritiesTemplate() {
        if (authorityDTOSList.size() != 0) {
            insertTrailer.setAuthority(authorityDTOSList.get(0));
            updateTrailer.setAuthority(authorityDTOSList.get(0));
            deleteTrailer.setAuthority(authorityDTOSList.get(1));

            insertContainer.setAuthority(authorityDTOSList.get(2));
            updateContainer.setAuthority(authorityDTOSList.get(2));
            deleteContainer.setAuthority(authorityDTOSList.get(3));
        }
    }

    private void graphicsBuilder() {
        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(root.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        trailersGraphicsBuilder();
        containersGraphicBuilder();

        tabContainer.getTabs().addAll(trailersTab, containersTab);

        root.setTop(headerLabel);
        root.setCenter(tabContainer);
        root.setPadding(new Insets(10));
    }

    private void trailersGraphicsBuilder() {
        //control buttons configuration
        insertTrailer.setPrefWidth(160);
        deleteTrailer.setPrefWidth(160);
        updateTrailer.setPrefWidth(160);
        trailerReport.setPrefWidth(160);

        //dataentery region configuration
        trailerIdLabel.setPrefWidth(150);
        trailerNumberLabel.setPrefWidth(150);
        trailerLicenceLabel.setPrefWidth(150);
        trailerLicenceExpirationDateLabel.setPrefWidth(150);
        trailerPermissionLabel.setPrefWidth(150);
        trailerCommentLabel.setPrefWidth(150);
        trailerCreatedByLabel.setPrefWidth(150);
        trailerModifiedByLabel.setPrefWidth(150);
        trailerOnTerminalLabel.setPrefWidth(150);
        trailerModificationLabel.setPrefWidth(150);
        trailerCreationDateLabel.setPrefWidth(150);

        trailerIdLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerLicenceLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerLicenceExpirationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerPermissionLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerCommentLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerCreatedByLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerOnTerminalLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerModificationLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerCreationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerModifiedByLabel.setTextAlignment(TextAlignment.RIGHT);

        trailerIdLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerLicenceLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerLicenceExpirationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerPermissionLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerCommentLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerCreatedByLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerOnTerminalLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerModificationLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerCreationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerModifiedByLabel.setAlignment(Pos.BASELINE_RIGHT);

        trailerIdLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerLicenceLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerLicenceExpirationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerPermissionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerCommentLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerCreatedByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerOnTerminalLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerModificationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerCreationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerModifiedByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        trailerIdField.setPrefWidth(250);
        trailerNumberField.setPrefWidth(250);
        trailerLicenceNumberField.setPrefWidth(250);
        trailerLicenceExpirationDateField.setPrefWidth(250);
        trailerPermissionField.setPrefWidth(250);
        trailerCommentField.setPrefWidth(250);
        trailerCreatedByField.setPrefWidth(250);
        trailerCreationDateField.setPrefWidth(250);
        trailerOnTerminalField.setPrefWidth(250);
        trailerModificationDateField.setPrefWidth(250);
        trailerModifiedByField.setPrefWidth(250);

        trailerIdField.setEditable(false);
        trailerCreatedByField.setEditable(false);
        trailerCreationDateField.setEditable(false);
        trailerOnTerminalField.setEditable(false);
        trailerModificationDateField.setEditable(false);
        trailerModifiedByField.setEditable(false);

        //restriction handling
        trailerLicenceNumberField.setRestrict("[0-9]");
        trailerLicenceNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                trailerLicenceNumberField.setText(oldValue);
            }
        });
        trailerLicenceNumberField.setMaxLength(14);
        trailerNumberField.setMaxLength(14);
        trailerNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!newValue.matches("[0-9]*")) {
                    trailerNumberField.setText(oldValue);
                }
            }
        });
        trailerCommentField.setMaxLength(500);

        //positioning labels and fields
        trailersDataEntry.add(trailerIdLabel, 1, 1);
        trailersDataEntry.add(trailerIdField, 2, 1);
        trailersDataEntry.add(trailerNumberLabel, 3, 1);
        trailersDataEntry.add(trailerNumberField, 4, 1);
        trailersDataEntry.add(trailerLicenceLabel, 5, 1);
        trailersDataEntry.add(trailerLicenceNumberField, 6, 1);

        trailersDataEntry.add(trailerPermissionLabel, 1, 2);
        trailersDataEntry.add(trailerPermissionField, 2, 2);
        trailersDataEntry.add(trailerLicenceExpirationDateLabel, 3, 2);
        trailersDataEntry.add(trailerLicenceExpirationDateField, 4, 2);
        trailersDataEntry.add(trailerCommentLabel, 5, 2);
        trailersDataEntry.add(trailerCommentField, 6, 2, 3, 1);

        trailersDataEntry.add(trailerCreationDateLabel, 1, 3);
        trailersDataEntry.add(trailerCreationDateField, 2, 3);
        trailersDataEntry.add(trailerModificationLabel, 3, 3);
        trailersDataEntry.add(trailerModificationDateField, 4, 3);
        trailersDataEntry.add(trailerCreatedByLabel, 5, 3);
        trailersDataEntry.add(trailerCreatedByField, 6, 3);
        trailersDataEntry.add(trailerModifiedByLabel, 7, 3);
        trailersDataEntry.add(trailerModifiedByField, 8, 3);

        trailersDataEntry.add(trailerOnTerminalLabel, 1, 4);
        trailersDataEntry.add(trailerOnTerminalField, 2, 4);

        trailerPermissionField.getItems().addAll(FXCollections.observableArrayList(Permissions.values()));

        //table configuration
        trailerIdColumn.setCellValueFactory(new PropertyValueFactory<>("truckTrailerIdColumn"));
        trailerNumColumn.setCellValueFactory(new PropertyValueFactory<>("trailerNumberColumn"));
        trailerLicenceColumn.setCellValueFactory(new PropertyValueFactory<>("licenseNumberColumn"));
        trailerLicenceExpirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("licenceExpirationDateColumn"));
        trailerPermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("permissionsColumn"));
        trailerCommentColumn.setCellValueFactory(new PropertyValueFactory<>("commentColumn"));
        trailerCreatedByColumn.setCellValueFactory(new PropertyValueFactory<>("createdByColumn"));
        trailerOnTerminalColumn.setCellValueFactory(new PropertyValueFactory<>("onTerminalColumn"));
        trailerCreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDateColumn"));
        trailerModifyDateColumn.setCellValueFactory(new PropertyValueFactory<>("modifyDateColumn"));
        trailerModifiedByColumn.setCellValueFactory(new PropertyValueFactory<>("modifiedByColumn"));

        trailersTableView.getColumns().addAll(trailerIdColumn, trailerNumColumn, trailerLicenceColumn, trailerLicenceExpirationDateColumn,
                trailerPermissionsColumn, trailerCommentColumn, trailerCreationDateColumn,
                trailerModifyDateColumn, trailerCreatedByColumn, trailerModifiedByColumn,trailerOnTerminalColumn);
        trailersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        trailersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        trailersTableView.prefHeightProperty().bind(tabContainer.heightProperty().subtract(trailersVbox.heightProperty()));
        trailersTableView.setItems(controller.getTruckTrailerDataList());
        trailerTableFilter = TableFilter.forTableView(trailersTableView).apply();

        trailersHbox.getItems().addAll(insertTrailer, updateTrailer, deleteTrailer);
        trailersHbox.setPadding(new Insets(10, 10, 10, 10));

        trailersVbox.getChildren().addAll(trailersDataEntry, trailersHbox);
        trailersVbox.setPadding(new Insets(10, 10, 10, 10));
        trailersVbox.setSpacing(10);
        trailersVbox.setAlignment(Pos.CENTER);

        trailersTab.setContent(trailersPane);
        trailersTab.setClosable(false);

        trailersPane.getChildren().add(trailersVbox);
        trailersPane.getChildren().add(trailersTableView);
        trailersPane.setPadding(new Insets(10));

        trailersTab.setContent(trailersPane);
        trailersTab.setClosable(false);

        trailerIdField.longValueProperty().bindBidirectional(trailerModel.truckTrailerIdProperty());
        trailerNumberField.textProperty().bindBidirectional(trailerModel.trailerNumberProperty());
        trailerLicenceNumberField.textProperty().bindBidirectional(trailerModel.licenseNumberProperty());
        trailerLicenceExpirationDateField.valueProperty().bindBidirectional(trailerModel.licenceExpirationDateProperty());
        trailerPermissionField.valueProperty().bindBidirectional(trailerModel.permissionsProperty());
        trailerCommentField.textProperty().bindBidirectional(trailerModel.commentProperty());
        trailerCreatedByField.textProperty().bindBidirectional(trailerModel.createdByProperty());
        trailerOnTerminalField.textProperty().bindBidirectional(trailerModel.onTerminalProperty());
        trailerModificationDateField.textProperty().bindBidirectional(trailerModel.modifyDateProperty());
        trailerCreationDateField.textProperty().bindBidirectional(trailerModel.creationDateProperty());
        trailerModifiedByField.textProperty().bindBidirectional(trailerModel.modifiedByProperty());
    }

    private void containersGraphicBuilder() {
        //control buttons configuration
        insertContainer.setPrefWidth(160);
        deleteContainer.setPrefWidth(160);
        updateContainer.setPrefWidth(160);
        containersReport.setPrefWidth(160);

        //dataentery region configuration
        containerIdLabel.setPrefWidth(150);
        containerNumberLabel.setPrefWidth(150);
        containerLicenceLabel.setPrefWidth(150);
        containerLicenceExpirationDateLabel.setPrefWidth(150);
        containerMaxWeightLabel.setPrefWidth(150);
        containerPermissionLabel.setPrefWidth(150);
        containerCommentLabel.setPrefWidth(150);
        containerCreatedByLabel.setPrefWidth(150);
        containerModifiedByLabel.setPrefWidth(150);
        containerOnTerminalLabel.setPrefWidth(150);
        containerModificationLabel.setPrefWidth(150);
        containerCreationDateLabel.setPrefWidth(150);

        containerIdLabel.setTextAlignment(TextAlignment.RIGHT);
        containerNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        containerLicenceLabel.setTextAlignment(TextAlignment.RIGHT);
        containerLicenceExpirationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        containerMaxWeightLabel.setTextAlignment(TextAlignment.RIGHT);
        containerPermissionLabel.setTextAlignment(TextAlignment.RIGHT);
        containerCommentLabel.setTextAlignment(TextAlignment.RIGHT);
        containerCreatedByLabel.setTextAlignment(TextAlignment.RIGHT);
        containerOnTerminalLabel.setTextAlignment(TextAlignment.RIGHT);
        containerModificationLabel.setTextAlignment(TextAlignment.RIGHT);
        containerCreationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        containerModifiedByLabel.setTextAlignment(TextAlignment.RIGHT);

        containerIdLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerLicenceLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerLicenceExpirationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerMaxWeightLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerPermissionLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerCommentLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerCreatedByLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerOnTerminalLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerModificationLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerCreationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        containerModifiedByLabel.setAlignment(Pos.BASELINE_RIGHT);

        containerIdLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerLicenceLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerLicenceExpirationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerMaxWeightLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerPermissionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerCommentLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerCreatedByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerOnTerminalLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerModificationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerCreationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        containerModifiedByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        containerIdField.setPrefWidth(250);
        containerNumberField.setPrefWidth(250);
        containerLicenceNumberField.setPrefWidth(250);
        containerLicenceExpirationDateField.setPrefWidth(250);
        containerMaxWeightField.setPrefWidth(250);
        containerPermissionField.setPrefWidth(250);
        containerCommentField.setPrefWidth(250);
        containerCreatedByField.setPrefWidth(250);
        containerCreationDateField.setPrefWidth(250);
        containerOnTerminalField.setPrefWidth(250);
        containerModificationDateField.setPrefWidth(250);
        containerModifiedByField.setPrefWidth(250);

        containerIdField.setEditable(false);
        containerCreatedByField.setEditable(false);
        containerCreationDateField.setEditable(false);
        containerOnTerminalField.setEditable(false);
        containerModificationDateField.setEditable(false);
        containerModifiedByField.setEditable(false);

        //restriction handling
        containerLicenceNumberField.setRestrict("[0-9]");
        containerLicenceNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                containerLicenceNumberField.setText(oldValue);
            }
        });
        containerLicenceNumberField.setMaxLength(14);
        containerNumberField.setMaxLength(14);
        containerMaxWeightField.setRestrict("[0-9].");
        containerNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!newValue.matches("[0-9]*")) {
                    containerNumberField.setText(oldValue);
                }
            }
        });
        containerMaxWeightField.setMaxLength(14);
        containerCommentField.setMaxLength(500);

        //positioning labels and fields
        containersDataEntry.add(containerIdLabel, 1, 1);
        containersDataEntry.add(containerIdField, 2, 1);
        containersDataEntry.add(containerNumberLabel, 3, 1);
        containersDataEntry.add(containerNumberField, 4, 1);
        containersDataEntry.add(containerLicenceLabel, 5, 1);
        containersDataEntry.add(containerLicenceNumberField, 6, 1);
        containersDataEntry.add(containerMaxWeightLabel, 7, 1);
        containersDataEntry.add(containerMaxWeightField, 8, 1);

        containersDataEntry.add(containerPermissionLabel, 1, 2);
        containersDataEntry.add(containerPermissionField, 2, 2);
        containersDataEntry.add(containerLicenceExpirationDateLabel, 3, 2);
        containersDataEntry.add(containerLicenceExpirationDateField, 4, 2);
        containersDataEntry.add(containerCommentLabel, 5, 2);
        containersDataEntry.add(containerCommentField, 6, 2, 3, 1);

        containersDataEntry.add(containerCreationDateLabel, 1, 3);
        containersDataEntry.add(containerCreationDateField, 2, 3);
        containersDataEntry.add(containerModificationLabel, 3, 3);
        containersDataEntry.add(containerModificationDateField, 4, 3);
        containersDataEntry.add(containerCreatedByLabel, 5, 3);
        containersDataEntry.add(containerCreatedByField, 6, 3);
        containersDataEntry.add(containerModifiedByLabel, 7, 3);
        containersDataEntry.add(containerModifiedByField, 8, 3);

        containersDataEntry.add(containerOnTerminalLabel, 1, 4);
        containersDataEntry.add(containerOnTerminalField, 2, 4);

        containerPermissionField.getItems().addAll(FXCollections.observableArrayList(Permissions.values()));

        //table configuration
        containerIdColumn.setCellValueFactory(new PropertyValueFactory<>("truckContainerIdColumn"));
        containerNumColumn.setCellValueFactory(new PropertyValueFactory<>("containerNumberColumn"));
        containerLicenceColumn.setCellValueFactory(new PropertyValueFactory<>("licenseNumberColumn"));
        containerLicenceExpirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("licenceExpirationDateColumn"));
        containerMaximumWeightColumn.setCellValueFactory(new PropertyValueFactory<>("maximumWeightConstrainColumn"));
        containerPermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("permissionsColumn"));
        containerCommentColumn.setCellValueFactory(new PropertyValueFactory<>("commentColumn"));
        containerCreatedByColumn.setCellValueFactory(new PropertyValueFactory<>("createdByColumn"));
        containerModifiedByColumn.setCellValueFactory(new PropertyValueFactory<>("modifiedByColumn"));
        containerOnTerminalColumn.setCellValueFactory(new PropertyValueFactory<>("onTerminalColumn"));
        containerCreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDateColumn"));
        containerModifyDateColumn.setCellValueFactory(new PropertyValueFactory<>("modifyDateColumn"));

        containersTableView.getColumns().addAll(containerIdColumn, containerNumColumn, containerLicenceColumn, containerLicenceExpirationDateColumn,
                containerMaximumWeightColumn,
                containerPermissionsColumn, containerCommentColumn, containerCreationDateColumn,
                containerModifyDateColumn, containerCreatedByColumn,containerModifiedByColumn, containerOnTerminalColumn);
        trailersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        containersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        containersTableView.prefHeightProperty().bind(tabContainer.heightProperty().subtract(containersVvox.heightProperty()));
        containersTableView.setItems(controller.getTruckContainerDataList());
        containerTableFilter = TableFilter.forTableView(containersTableView).apply();

        containersHbox.getItems().addAll(insertContainer, updateContainer, deleteContainer);
        containersHbox.setPadding(new Insets(10, 10, 10, 10));

        containersVvox.getChildren().addAll(containersDataEntry, containersHbox);
        containersVvox.setPadding(new Insets(10, 10, 10, 10));
        containersVvox.setSpacing(10);
        containersVvox.setAlignment(Pos.CENTER);

        containersTab.setContent(containersPane);
        containersTab.setClosable(false);

        containersPane.getChildren().add(containersVvox);
        containersPane.getChildren().add(containersTableView);
        containersPane.setPadding(new Insets(10));

        containersTab.setContent(containersPane);
        containersTab.setClosable(false);

        containerIdField.longValueProperty().bindBidirectional(containerModel.truckContainerIdProperty());
        containerNumberField.textProperty().bindBidirectional(containerModel.containerNumberProperty());
        containerLicenceNumberField.textProperty().bindBidirectional(containerModel.licenseNumberProperty());
        containerLicenceExpirationDateField.valueProperty().bindBidirectional(containerModel.licenceExpirationDateProperty());
        containerMaxWeightField.longValueProperty().bindBidirectional(containerModel.maximumWeightConstrainProperty());
        containerPermissionField.valueProperty().bindBidirectional(containerModel.permissionsProperty());
        containerCommentField.textProperty().bindBidirectional(containerModel.commentProperty());
        containerCreatedByField.textProperty().bindBidirectional(containerModel.createdByProperty());
        containerModifiedByField.textProperty().bindBidirectional(containerModel.modifiedByProperty());
        containerOnTerminalField.textProperty().bindBidirectional(containerModel.onTerminalProperty());
        containerModificationDateField.textProperty().bindBidirectional(containerModel.modifyDateProperty());
        containerCreationDateField.textProperty().bindBidirectional(containerModel.creationDateProperty());
    }

    private void actionHandling() {
        trailersActionHandling();
        containersActionHandling();
    }

    private void trailersActionHandling() {
        insertTrailer.setOnMouseClicked(controller::onInsertTruckTrailer);
        deleteTrailer.setOnMouseClicked(controller::onTrailerDelete);
        updateTrailer.setOnMouseClicked(controller::onUpdateTruckTrailer);

        trailersTableView.setOnMouseClicked((a) -> {
            controller.onTruckTrailerTableSelection(trailersTableView.getSelectionModel().getSelectedItems());
        });
    }

    private void containersActionHandling() {
        insertContainer.setOnMouseClicked(controller::onInsertTruckContainer);
        deleteContainer.setOnMouseClicked(controller::onContainerDelete);
        updateContainer.setOnMouseClicked(controller::onUpdateTruckContainer);

        containersTableView.setOnMouseClicked((a) -> {
            controller.onTruckContainerTableSelection(containersTableView.getSelectionModel().getSelectedItems());
        });
    }

    public void update() {

        ReadOnlyBooleanProperty update = controller.updateTrailer();
        trailersTableView.cursorProperty().bind(Bindings.when(update).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));

        ReadOnlyBooleanProperty containerUpdate = controller.updateContainer();
        containersTableView.cursorProperty().bind(Bindings.when(containerUpdate).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));

    }

    public Node getTabContainer() {
        return root;
    }
}
