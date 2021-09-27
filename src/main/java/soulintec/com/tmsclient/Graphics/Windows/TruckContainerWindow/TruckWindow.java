package soulintec.com.tmsclient.Graphics.Windows.TruckContainerWindow;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Entities.TruckTrailerDTO;
import soulintec.com.tmsclient.Graphics.Controls.DataEntryPartitionTitled;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedButton;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedLongField;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedTextField;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Services.TruckContainerService;
import soulintec.com.tmsclient.Services.TruckTrailerService;

import java.time.LocalDateTime;

@Component
public class TruckWindow implements ApplicationListener<ApplicationContext.ApplicationListener> {


    private TruckController controller;
    private TruckContainerModel containerModel;

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
    private Label headerLabel;

    private Stage mainWindow;

    private TableView<TruckTrailerDTO> trailersTableView;
    private TableColumn<TruckTrailerDTO, LongProperty> trailerIdColumn;
    private TableColumn<TruckTrailerDTO, StringProperty> trailerNumberColumn;
    private TableColumn<TruckTrailerDTO, StringProperty> trailerLicenceNumberColumn;
    private TableColumn<TruckTrailerDTO, StringProperty> trailerLicenceExpirationDateColumn;
    private TableColumn<TruckTrailerDTO, StringProperty> permissionsColumn;
    private TableColumn<TruckTrailerDTO, StringProperty> commentColumn;

    private TableView<TruckContainerModel.TableObject> containersTableView;
    private TableColumn<TruckContainerModel.TableObject, LongProperty> containerIdColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerNumColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerLicenceColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerLicenceExpirationDateColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerMaximumWeightColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerPermissionsColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerCommentColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerCreatedByColumn;
    private TableColumn<TruckContainerModel.TableObject, StringProperty> containerOnTerminalColumn;
    private TableColumn<TruckContainerModel.TableObject, ObjectProperty<LocalDateTime>> containerCreationDateColumn;
    private TableColumn<TruckContainerModel.TableObject, ObjectProperty<LocalDateTime>> containerModifyDateColumn;

    private EnhancedButton insertTrailer;
    private EnhancedButton deleteTrailer;
    private EnhancedButton updateTrailer;
    private EnhancedButton report;

    private EnhancedButton insertContainer;
    private EnhancedButton deleteContainer;
    private EnhancedButton updateContainer;
    private EnhancedButton containersReport;

    private Label trailerIdLabel;
    private Label trailerNumberLabel;
    private Label trailerLicenceNumberLabel;
    private Label trailerLicenceExpirationDateLabel;
    private Label trailerPermissionLabel;
    private Label trailerCommentLabel;

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

    private EnhancedTextField trailerIdField;
    private EnhancedTextField trailerNumberField;
    private EnhancedTextField trailerLicenceNumberField;
    private DatePicker trailerLicenceExpirationDateField;
    private ComboBox trailerPermissionField;
    private EnhancedTextField trailerCommentField;

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
    private EnhancedTextField containerOnTerminalField;

    @Autowired
    private TruckContainerService truckContainerService;
    
    @Autowired
    private TruckTrailerService truckTrailerService;

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
        controller = ApplicationContext.applicationContext.getBean(TruckController.class);
        containerModel = controller.getTruckContainerModel();

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
        trailerNumberColumn = new TableColumn<>("Trailer Number");
        trailerLicenceNumberColumn = new TableColumn<>("Licence Num");
        trailerLicenceExpirationDateColumn = new TableColumn<>("Licence Expiry");
        permissionsColumn = new TableColumn<>("Permission");
        commentColumn = new TableColumn<>("Comment");

        insertTrailer = new EnhancedButton("Insert new trailer");
        deleteTrailer = new EnhancedButton("Delete selected trailer");
        updateTrailer = new EnhancedButton("Update selected trailer");
        report = new EnhancedButton("Show Report");

        trailerIdLabel = new Label("Trailer Id :");
        trailerNumberLabel = new Label("Trailer Number :");
        trailerLicenceNumberLabel = new Label("Licence number :");
        trailerLicenceExpirationDateLabel = new Label("Licence Expiry :");
        trailerPermissionLabel = new Label("Permission :");
        trailerCommentLabel = new Label("Comment :");

        trailerIdField = new EnhancedTextField();
        trailerNumberField = new EnhancedTextField();
        trailerLicenceNumberField = new EnhancedTextField();
        trailerLicenceExpirationDateField = new DatePicker();
        trailerPermissionField = new ComboBox();
        trailerCommentField = new EnhancedTextField();

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
        containerOnTerminalField = new EnhancedTextField();
    }


    private void userAuthorities() {

    }

    private  void graphicsBuilder() {
        trailersGraphicsBuilder();
        containersGraphicBuilder();

        tabContainer.getTabs().addAll(trailersTab, containersTab);

        root.setTop(headerLabel);
        root.setCenter(tabContainer);
        root.setPadding(new Insets(10));
    }
    private  void trailersGraphicsBuilder() {
        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(tabContainer.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        //control buttons configuration
        insertTrailer.setPrefWidth(150);
        deleteTrailer.setPrefWidth(150);
        updateTrailer.setPrefWidth(150);
        report.setPrefWidth(150);
        
        //dataentery region configuration
        trailerIdLabel.setPrefWidth(150);
        trailerNumberLabel.setPrefWidth(150);
        trailerLicenceNumberLabel.setPrefWidth(150);
        trailerLicenceExpirationDateLabel.setPrefWidth(150);
        trailerPermissionLabel.setPrefWidth(150);
        trailerCommentLabel.setPrefWidth(150);

        trailerIdLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerLicenceNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerLicenceExpirationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerPermissionLabel.setTextAlignment(TextAlignment.RIGHT);
        trailerCommentLabel.setTextAlignment(TextAlignment.RIGHT);

        trailerIdLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerLicenceNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerLicenceExpirationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerPermissionLabel.setAlignment(Pos.BASELINE_RIGHT);
        trailerCommentLabel.setAlignment(Pos.BASELINE_RIGHT);

        trailerIdLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerLicenceNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerLicenceExpirationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerPermissionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        trailerCommentLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");


        trailerIdField.setPrefWidth(250);
        trailerLicenceNumberField.setPrefWidth(250);
        trailerLicenceExpirationDateField.setPrefWidth(250);
        trailerPermissionField.setPrefWidth(250);
        trailerCommentField.setPrefWidth(250);
        trailerNumberField.setPrefWidth(250);

        //restriction handling
        trailerLicenceNumberField.setRestrict("[0-9]");
        trailerLicenceNumberField.setMaxLength(14);
        trailerNumberField.setMaxLength(14);
        trailerCommentField.setMaxLength(500);

        //disabling aditable datepickers
        trailerLicenceExpirationDateField.setEditable(false);

        trailersDataEntry.add(trailerIdLabel, 5, 2);
        trailersDataEntry.add(trailerNumberLabel, 1, 2);
        trailersDataEntry.add(trailerLicenceNumberLabel, 3, 2);
        trailersDataEntry.add(trailerLicenceExpirationDateLabel, 1, 3);
        trailersDataEntry.add(trailerPermissionLabel, 3, 3);
        trailersDataEntry.add(trailerCommentLabel, 1, 4);

        trailersDataEntry.add(trailerIdField, 6, 2);
        trailersDataEntry.add(trailerNumberField, 2, 2);
        trailersDataEntry.add(trailerLicenceNumberField, 4, 2);
        trailersDataEntry.add(trailerLicenceExpirationDateField, 2, 3);
        trailersDataEntry.add(trailerPermissionField, 4, 3);
        trailersDataEntry.add(trailerCommentField, 2, 4);

        //table configuration
        trailerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        trailerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("TrailerNumber"));
        trailerLicenceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("LicenceNumber"));
        trailerLicenceExpirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("LicenceExpirationDate"));
        permissionsColumn.setCellValueFactory(new PropertyValueFactory<>("Permissions"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("Comment"));

        trailersTableView.getColumns().addAll(trailerIdColumn, trailerNumberColumn, trailerLicenceNumberColumn, trailerLicenceExpirationDateColumn, permissionsColumn);
        trailersTableView.prefHeightProperty().bind(tabContainer.heightProperty().subtract(trailersVbox.heightProperty()));

        trailersHbox.getItems().addAll(insertTrailer, updateTrailer, deleteTrailer);
        trailersHbox.setPadding(new Insets(10, 10, 10, 10));

        trailersVbox.getChildren().addAll(trailersDataEntry, trailersHbox);
        trailersVbox.setPadding(new Insets(10, 10, 10, 10));
        trailersVbox.setSpacing(10);
        trailersVbox.setAlignment(Pos.CENTER);

        trailersPane.getChildren().add(trailersVbox);
        trailersPane.getChildren().add(trailersTableView);
        trailersPane.setPadding(new Insets(10));

        trailersTab.setContent(trailersPane);
        trailersTab.setClosable(false);
    }
    private  void containersGraphicBuilder() {
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
            if (newValue!=null){
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
        containersDataEntry.add(containerCommentField, 6, 2,3,1);

        containersDataEntry.add(containerCreationDateLabel, 1, 3);
        containersDataEntry.add(containerCreationDateField, 2, 3);
        containersDataEntry.add(containerModificationLabel, 3, 3);
        containersDataEntry.add(containerModificationDateField, 4, 3);
        containersDataEntry.add(containerCreatedByLabel, 5, 3);
        containersDataEntry.add(containerCreatedByField, 6, 3);
        containersDataEntry.add(containerOnTerminalLabel, 7, 3);
        containersDataEntry.add(containerOnTerminalField, 8, 3);

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
        containerOnTerminalColumn.setCellValueFactory(new PropertyValueFactory<>("onTerminalColumn"));
        containerCreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDateColumn"));
        containerModifyDateColumn.setCellValueFactory(new PropertyValueFactory<>("modifyDateColumn"));

        containersTableView.getColumns().addAll(containerIdColumn, containerNumColumn, containerLicenceColumn, containerLicenceExpirationDateColumn,
                containerMaximumWeightColumn,
                containerPermissionsColumn, containerCommentColumn, containerCreationDateColumn,
                containerModifyDateColumn, containerCreatedByColumn, containerOnTerminalColumn);
        trailersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        containersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        containersTableView.prefHeightProperty().bind(tabContainer.heightProperty().subtract(containersVvox.heightProperty()));
        containersTableView.setItems(controller.getTruckContainerDataList());

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
        containerOnTerminalField.textProperty().bindBidirectional(containerModel.onTerminalProperty());
        containerModificationDateField.textProperty().bindBidirectional(containerModel.modifyDateProperty());
        containerCreationDateField.textProperty().bindBidirectional(containerModel.creationDateProperty());
    }

    private  void actionHandling() {
        trailersActionHandling();
        containersActionHandling();
    }
    private  void trailersActionHandling() {
        insertTrailer.setOnMouseClicked(this::onCreateTrailer);
        deleteTrailer.setOnMouseClicked(this::onDeleteTrailer);
        updateTrailer.setOnMouseClicked(this::onUpdateTrailer);

        trailersTableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TruckTrailerDTO>) action -> {
            try {
                if (action.getList().size() > 0) {
                    TruckTrailerDTO selected = trailersTableView.getSelectionModel().getSelectedItem();
                    trailerIdField.setText(String.valueOf(selected.getId()));
                    trailerNumberField.setText(selected.getTrailerNumber());
                    trailerLicenceNumberField.setText(selected.getLicenceNumber());
                    trailerLicenceExpirationDateField.setValue(selected.getLicenceExpirationDate());
                    trailerPermissionField.setValue(selected.getPermissions());
                    trailerCommentField.setText(selected.getComment());
                }
            } catch (Exception e) {
                showErrorWindow("Error Importing data", "Please select table row again");
            }
        });
    }
    private  void containersActionHandling() {
        insertContainer.setOnMouseClicked(controller::onInsertTruckContainer);
        deleteContainer.setOnMouseClicked(controller::onDelete);
        updateContainer.setOnMouseClicked(controller::onUpdateTruckContainer);

        containersTableView.setOnMouseClicked((a) -> {
            controller.onTruckContainerTableSelection(containersTableView.getSelectionModel().getSelectedItems());
        });
    }

    @Async
    private void onCreateTrailer(MouseEvent action) {
//        if ((trailerNumberField.getText().length() > 0) && (trailerLicenceNumberField.getText().length() > 0)) {
//            if ((truckTrailerService.findByLicence(Long.parseLong(trailerLicenceNumberField.getText())).isEmpty())) {
//                TruckTrailerDTO trailer = new TruckTrailerDTO(trailerNumberField.getText(), trailerLicenceNumberField.getText(),
//                      trailerLicenceExpirationDateField.getValue(), trailerPermissionField.getSelectionModel().getSelectedItem().toString(),
//                        trailerCommentField.getText());
//                truckTrailerService.save(trailer);
//                update();
//            } else {
//                showErrorWindow("Error Inserting data", "Truck Trailer already exist , please check entered data ...");
//            }
//        } else {
//            showErrorWindow("Error inserting data", "Please check entered data .. No possible empty fields");
//        }
    }

    @Async
    private void onDeleteTrailer(MouseEvent action) {
        try {

                truckTrailerService.deleteById(Long.parseLong(trailerNumberField.getText()));
                update();

        }catch (Exception e){
            showErrorWindowForException("Error deleting trailer", e);
        }
    }

    @Async
    private void onUpdateTrailer(MouseEvent action) {
//        if ((TrailerNumberField.getText().length() > 0) && (TrailerLicenceNumberField.getText().length() > 0)) {
//            if (truckTrailerService.findById(Long.parseLong(TrailerIdField.getText())).isPresent()) {
//                TruckTrailerDTO trailer = new TruckTrailerDTO(Long.parseLong(TrailerIdField.getText()), TrailerNumberField.getText(), TrailerLicenceNumberField.getText(),
//                       TrailerLicenceExpirationDateField.getValue(), TrailerPermissionField.getSelectionModel().getSelectedItem().toString(),
//                        TrailerCommentField.getText());
//
//                truckTrailerService.save(trailer);
//                update();
//            } else {
//                showErrorWindow("Error Inserting data", "Truck Trailer not exist , please check entered data ...");
//            }
//        } else {
//            showErrorWindow("Error updating data", "Please check entered data .. No possible empty fields");
//        }
    }

    @Async
    public  void update() {
       controller.updateDataList();
    }

    public Node getTabContainer() {
        return root;
    }
    public static void showErrorWindow(String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.getDialogPane().setMaxWidth(500);
            alert.initOwner(initialStage.getInitialStage());
            alert.initModality(Modality.WINDOW_MODAL);
            alert.show();
        });
    }

    public static void showErrorWindowForException(String header, Throwable e) {
        Platform.runLater(() -> {
            ExceptionDialog exceptionDialog = new ExceptionDialog(e);
            exceptionDialog.setHeaderText(header);
            exceptionDialog.getDialogPane().setMaxWidth(500);
            exceptionDialog.initOwner(initialStage.getInitialStage());
            exceptionDialog.initModality(Modality.WINDOW_MODAL);
            exceptionDialog.show();

        });
    }

    protected static void showErrorWindowForException(String header, Throwable e, Stage stage) {
        Platform.runLater(() -> {
            ExceptionDialog exceptionDialog = new ExceptionDialog(e);
            exceptionDialog.setHeaderText(header);
            exceptionDialog.getDialogPane().setMaxWidth(500);
            exceptionDialog.initOwner(stage);
            exceptionDialog.initModality(Modality.WINDOW_MODAL);
            exceptionDialog.show();

        });
    }

    protected static void showErrorWindowOneTime(String header, String content, Stage stage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.getDialogPane().setMaxWidth(500);
            alert.initOwner(stage);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.show();
        });
    }

    public static void showWarningWindow(String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.getDialogPane().setMaxWidth(500);
            alert.initOwner(initialStage.getInitialStage());
            alert.initModality(Modality.WINDOW_MODAL);
            alert.show();
        });
    }

    protected static void showInformationWindow(String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.getDialogPane().setMaxWidth(500);
            alert.initOwner(initialStage.getInitialStage());
            alert.initModality(Modality.NONE);
            alert.show();
        });
    }
}
