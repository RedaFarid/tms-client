package soulintec.com.tmsclient.Graphics.Windows;

import com.google.common.collect.Lists;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.dialog.ExceptionDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Entities.TruckContainerDTO;
import soulintec.com.tmsclient.Entities.TruckTrailerDTO;
import soulintec.com.tmsclient.Graphics.Controls.DataEntryPartitionTitled;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedButton;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedTextField;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Services.TruckContainerService;
import soulintec.com.tmsclient.Services.TruckTrailerService;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TruckWindow implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private BooleanProperty masterCombartementsUserControl = new SimpleBooleanProperty();

    private DataEntryPartitionTitled TrailersDataEntry = new DataEntryPartitionTitled("Trailer");
    private DataEntryPartitionTitled ContainersDataEntry = new DataEntryPartitionTitled("Container");

    private Tab TrailersTab = new Tab("Trailers Management");
    private Tab ContainersTab = new Tab("Containers Management");
    private VBox TrailersPane = new VBox();
    private VBox ContainersPane = new VBox();

    private VBox TrailersVbox = new VBox();
    private ToolBar TrailersHbox = new ToolBar();
    private VBox ContainersVvox = new VBox();
    private ToolBar ContainersHbox = new ToolBar();

    private BorderPane root = new BorderPane();
    private TabPane tabContainer = new TabPane();
    private Label headerLabel = new Label("Trucks");

    private Stage mainWindow = null;

    private ObservableList<TruckTrailerDTO> TrailersList = FXCollections.observableArrayList();
    private TableView<TruckTrailerDTO> TrailersTableView = new TableView<>();
    private TableColumn<TruckTrailerDTO, Long> TrailerIdColumn = new TableColumn<>("Trailer Id");
    private TableColumn<TruckTrailerDTO, String> TrailerNumberColumn = new TableColumn<>("Trailer Number");
    private TableColumn<TruckTrailerDTO, String> TrailerLicenceNumberColumn = new TableColumn<>("Licence Num");
    private TableColumn<TruckTrailerDTO, String> TrailerLicenceExpirationDateColumn = new TableColumn<>("Licence Expiry");
    private TableColumn<TruckTrailerDTO, String> PermissionsColumn = new TableColumn<>("Permission");
    private TableColumn<TruckTrailerDTO, String> CommentColumn = new TableColumn<>("Comment");

    private ObservableList<TruckContainerDTO> ContainersList = FXCollections.observableArrayList();
    private TableView<TruckContainerDTO> ContainersTableView = new TableView<>();
    private TableColumn<TruckContainerDTO, Long> ContainerIdColumn = new TableColumn<>("Container Id");
    private TableColumn<TruckContainerDTO, String> ContainerNumColumn = new TableColumn<>("Number");
    private TableColumn<TruckContainerDTO, String> ContainerLicenceColumn = new TableColumn<>("Licence");
    private TableColumn<TruckContainerDTO, String> ContainerLicenceExpirationdateColumn = new TableColumn<>("LicenceExpiration date");
    private TableColumn<TruckContainerDTO, String> ContainerMaximumWeightColumn = new TableColumn<>("Maximum Weight");
    private TableColumn<TruckContainerDTO, String> ContainerCombartementNumberColumn = new TableColumn<>("Combartement Number");
    private TableColumn<TruckContainerDTO, String> ContainerCalibrationExpirationDateColumn = new TableColumn<>("Calibration Expiration Date");
    private TableColumn<TruckContainerDTO, String> ContainerPermissionsColumn = new TableColumn<>("Permissions");
    private TableColumn<TruckContainerDTO, String> ContainerCommentColumn = new TableColumn<>("Comment");

    private EnhancedButton InsertTrailer = new EnhancedButton("Insert new trailer");
    private EnhancedButton DeleteTrailer = new EnhancedButton("Delete selected trailer");
    private EnhancedButton UpdateTrailer = new EnhancedButton("Update selected trailer");
    private EnhancedButton report = new EnhancedButton("Show Report");

    private EnhancedButton InsertContainer = new EnhancedButton("Insert new container");
    private EnhancedButton DeleteContainer = new EnhancedButton("Delete selected container");
    private EnhancedButton UpdateContainer = new EnhancedButton("Update selected container");
    private EnhancedButton containersReport = new EnhancedButton("Show Report");

    private Label TrailerIdLabel = new Label("Trailer Id :");
    private Label TrailerNumberLabel = new Label("Trailer Number :");
    private Label TrailerLicenceNumberLabel = new Label("Licence number :");
    private Label TrailerLicenceExpirationDateLabel = new Label("Licence Expiry :");
    private Label TrailerPermissionLabel = new Label("Permission :");
    private Label TrailerCommentLabel = new Label("Comment :");

    private Label ContainerIdLabel = new Label("Container Id :");
    private Label ContainerNumberLabel = new Label("Container Number :");
    private Label ContainerlicenceLabel = new Label("Licance Number :");
    private Label ContainerLicenceExpirationDateLabel = new Label("Licence Expiry :");
    private Label ContainermaxWeightLabel = new Label("Maximum Weight :");
    private Label ContainerCombartementsNumberLabel = new Label("Combartements Number :");
    private Label ContainerCalibrationExpirationdateLabel = new Label("Calibration Expiry :");
    private Label ContainerPermissionLabel = new Label("Permission :");
    private Label ContainerCommentlabel = new Label("Comment :");

    private EnhancedTextField TrailerIdField = new EnhancedTextField();
    private EnhancedTextField TrailerNumberField = new EnhancedTextField();
    private EnhancedTextField TrailerLicenceNumberField = new EnhancedTextField();
    private DatePicker TrailerLicenceExpirationDateField = new DatePicker();
    private ComboBox TrailerPermissionField = new ComboBox();
    private EnhancedTextField TrailerCommentField = new EnhancedTextField();

    private EnhancedTextField ContainerIdField = new EnhancedTextField();
    private EnhancedTextField ContainerNumberField = new EnhancedTextField();
    private EnhancedTextField ContainerLicenceNumberField = new EnhancedTextField();
    private DatePicker ContainerLicenceExpirationDateField = new DatePicker();
    private EnhancedTextField ContainerMaxWeightField = new EnhancedTextField();
    private EnhancedTextField ContainerCombartementsNumberField = new EnhancedTextField();
    private DatePicker ContainerCalibrationExpirationDateField = new DatePicker();
    private ComboBox ContainerpermissionField = new ComboBox();
    private EnhancedTextField ContainerCommentField = new EnhancedTextField();

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);

    @Autowired
    private TruckContainerService truckContainerService;
    
    @Autowired
    private TruckTrailerService truckTrailerService;


    @Autowired(required = false)
    @Qualifier
    private Executor executor;

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainWindow = event.getStage();

        userAuthorities();
        graphicsBuilder();
        actionHandling();
    }
    private void userAuthorities() {

    }

    private  void graphicsBuilder() {
        trailersGraphicsBuilder();
        containersGraphicBuilder();

        tabContainer.getTabs().addAll(TrailersTab, ContainersTab);

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
        InsertTrailer.setPrefWidth(150);
        DeleteTrailer.setPrefWidth(150);
        UpdateTrailer.setPrefWidth(150);
        report.setPrefWidth(150);
        
        //dataentery region configuration
        TrailerIdLabel.setPrefWidth(150);
        TrailerNumberLabel.setPrefWidth(150);
        TrailerLicenceNumberLabel.setPrefWidth(150);
        TrailerLicenceExpirationDateLabel.setPrefWidth(150);
        TrailerPermissionLabel.setPrefWidth(150);
        TrailerCommentLabel.setPrefWidth(150);

        TrailerIdLabel.setTextAlignment(TextAlignment.RIGHT);
        TrailerNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        TrailerLicenceNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        TrailerLicenceExpirationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        TrailerPermissionLabel.setTextAlignment(TextAlignment.RIGHT);
        TrailerCommentLabel.setTextAlignment(TextAlignment.RIGHT);

        TrailerIdLabel.setAlignment(Pos.BASELINE_RIGHT);
        TrailerNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        TrailerLicenceNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        TrailerLicenceExpirationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        TrailerPermissionLabel.setAlignment(Pos.BASELINE_RIGHT);
        TrailerCommentLabel.setAlignment(Pos.BASELINE_RIGHT);

        TrailerIdLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TrailerNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TrailerLicenceNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TrailerLicenceExpirationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TrailerPermissionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TrailerCommentLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");


        TrailerIdField.setPrefWidth(250);
        TrailerLicenceNumberField.setPrefWidth(250);
        TrailerLicenceExpirationDateField.setPrefWidth(250);
        TrailerPermissionField.setPrefWidth(250);
        TrailerCommentField.setPrefWidth(250);
        TrailerNumberField.setPrefWidth(250);

        //restriction handling
        TrailerLicenceNumberField.setRestrict("[0-9]");
        TrailerLicenceNumberField.setMaxLength(14);
        TrailerNumberField.setMaxLength(14);
        TrailerCommentField.setMaxLength(500);

        //disabling aditable datepickers
        TrailerLicenceExpirationDateField.setEditable(false);

        TrailersDataEntry.add(TrailerIdLabel, 5, 2);
        TrailersDataEntry.add(TrailerNumberLabel, 1, 2);
        TrailersDataEntry.add(TrailerLicenceNumberLabel, 3, 2);
        TrailersDataEntry.add(TrailerLicenceExpirationDateLabel, 1, 3);
        TrailersDataEntry.add(TrailerPermissionLabel, 3, 3);
        TrailersDataEntry.add(TrailerCommentLabel, 1, 4);

        TrailersDataEntry.add(TrailerIdField, 6, 2);
        TrailersDataEntry.add(TrailerNumberField, 2, 2);
        TrailersDataEntry.add(TrailerLicenceNumberField, 4, 2);
        TrailersDataEntry.add(TrailerLicenceExpirationDateField, 2, 3);
        TrailersDataEntry.add(TrailerPermissionField, 4, 3);
        TrailersDataEntry.add(TrailerCommentField, 2, 4);

        //table configuration
        TrailerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TrailerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("TrailerNumber"));
        TrailerLicenceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("LicenceNumber"));
        TrailerLicenceExpirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("LicenceExpirationDate"));
        PermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("Permissions"));
        CommentColumn.setCellValueFactory(new PropertyValueFactory<>("Comment"));

        TrailersTableView.getColumns().addAll(TrailerIdColumn, TrailerNumberColumn, TrailerLicenceNumberColumn, TrailerLicenceExpirationDateColumn, PermissionsColumn, CommentColumn);
        TrailersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TrailersTableView.prefHeightProperty().bind(tabContainer.heightProperty().subtract(TrailersVbox.heightProperty()));

        TrailersHbox.getItems().addAll(InsertTrailer, UpdateTrailer, DeleteTrailer, report);
        TrailersHbox.setPadding(new Insets(10, 10, 10, 10));

        TrailersVbox.getChildren().addAll(TrailersDataEntry, TrailersHbox);
        TrailersVbox.setPadding(new Insets(10, 10, 10, 10));
        TrailersVbox.setSpacing(10);
        TrailersVbox.setAlignment(Pos.CENTER);

        TrailersPane.getChildren().add(TrailersVbox);
        TrailersPane.getChildren().add(TrailersTableView);
        TrailersPane.setPadding(new Insets(10));

        TrailersTab.setContent(TrailersPane);
        TrailersTab.setClosable(false);
    }
    private  void containersGraphicBuilder() {
        //control buttons configuration
        InsertContainer.setPrefWidth(150);
        DeleteContainer.setPrefWidth(150);
        UpdateContainer.setPrefWidth(150);
        containersReport.setPrefWidth(150);

        //dataentery region configuration
        ContainerIdLabel.setPrefWidth(150);
        ContainerNumberLabel.setPrefWidth(150);
        ContainerlicenceLabel.setPrefWidth(150);
        ContainerLicenceExpirationDateLabel.setPrefWidth(150);
        ContainermaxWeightLabel.setPrefWidth(150);
        ContainerCombartementsNumberLabel.setPrefWidth(150);
        ContainerCalibrationExpirationdateLabel.setPrefWidth(150);
        ContainerPermissionLabel.setPrefWidth(150);
        ContainerCommentlabel.setPrefWidth(150);

        ContainerIdLabel.setTextAlignment(TextAlignment.RIGHT);
        ContainerNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        ContainerlicenceLabel.setTextAlignment(TextAlignment.RIGHT);
        ContainerLicenceExpirationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        ContainermaxWeightLabel.setTextAlignment(TextAlignment.RIGHT);
        ContainerCombartementsNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        ContainerCalibrationExpirationdateLabel.setTextAlignment(TextAlignment.RIGHT);
        ContainerPermissionLabel.setTextAlignment(TextAlignment.RIGHT);
        ContainerCommentlabel.setTextAlignment(TextAlignment.RIGHT);

        ContainerIdLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContainerNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContainerlicenceLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContainerLicenceExpirationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContainermaxWeightLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContainerCombartementsNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContainerCalibrationExpirationdateLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContainerPermissionLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContainerCommentlabel.setAlignment(Pos.BASELINE_RIGHT);

        ContainerIdLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContainerNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContainerlicenceLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContainerLicenceExpirationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContainermaxWeightLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContainerCombartementsNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContainerCalibrationExpirationdateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContainerPermissionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContainerCommentlabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");


        ContainerIdField.setPrefWidth(250);
        ContainerNumberField.setPrefWidth(250);
        ContainerLicenceNumberField.setPrefWidth(250);
        ContainerLicenceExpirationDateField.setPrefWidth(250);
        ContainerMaxWeightField.setPrefWidth(250);
        ContainerCombartementsNumberField.setPrefWidth(250);
        ContainerCalibrationExpirationDateField.setPrefWidth(250);
        ContainerpermissionField.setPrefWidth(250);
        ContainerCommentField.setPrefWidth(250);

        //restriction handling
        ContainerLicenceNumberField.setRestrict("[0-9]");
        ContainerLicenceNumberField.setMaxLength(14);
        ContainerNumberField.setMaxLength(14);
        ContainerMaxWeightField.setRestrict("[0-9].");
        ContainerMaxWeightField.setMaxLength(14);
        ContainerCombartementsNumberField.setRestrict("[0-9]");
        ContainerCombartementsNumberField.setMaxLength(1);
        ContainerCommentField.setMaxLength(500);

        //disabling aditable datepickers
        ContainerLicenceExpirationDateField.setEditable(false);
        ContainerCalibrationExpirationDateField.setEditable(false);

        //positioning labels and fields
        ContainersDataEntry.add(ContainerNumberLabel, 1, 1);
        ContainersDataEntry.add(ContainerNumberField, 2, 1);

        ContainersDataEntry.add(ContainerlicenceLabel, 1, 2);
        ContainersDataEntry.add(ContainerLicenceNumberField, 2, 2);

        ContainersDataEntry.add(ContainerLicenceExpirationDateLabel, 3, 2);
        ContainersDataEntry.add(ContainerLicenceExpirationDateField, 4, 2);

        ContainersDataEntry.add(ContainermaxWeightLabel, 3, 1);
        ContainersDataEntry.add(ContainerMaxWeightField, 4, 1);

        ContainersDataEntry.add(ContainerCombartementsNumberLabel, 1, 3);
        ContainersDataEntry.add(ContainerCombartementsNumberField, 2, 3);

        ContainersDataEntry.add(ContainerCalibrationExpirationdateLabel, 3, 3);
        ContainersDataEntry.add(ContainerCalibrationExpirationDateField, 4, 3);

        ContainersDataEntry.add(ContainerPermissionLabel, 1, 4);
        ContainersDataEntry.add(ContainerpermissionField, 2, 4);

        ContainersDataEntry.add(ContainerCommentlabel, 3, 4);
        ContainersDataEntry.add(ContainerCommentField, 4, 4);

        ContainersDataEntry.add(ContainerIdLabel, 5, 1);
        ContainersDataEntry.add(ContainerIdField, 6, 1);

        //table configuration
        ContainerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ContainerNumColumn.setCellValueFactory(new PropertyValueFactory<>("ContainerNumber"));
        ContainerLicenceColumn.setCellValueFactory(new PropertyValueFactory<>("LicenceNumber"));
        ContainerLicenceExpirationdateColumn.setCellValueFactory(new PropertyValueFactory<>("LicenceExpirationDate"));
        ContainerMaximumWeightColumn.setCellValueFactory(new PropertyValueFactory<>("MaximumWeightConstrain"));
        ContainerCombartementNumberColumn.setCellValueFactory(new PropertyValueFactory<>("CompartementsNumber"));
        ContainerCalibrationExpirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("CalibrationExpirationDate"));
        ContainerPermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("Permissions"));
        ContainerCommentColumn.setCellValueFactory(new PropertyValueFactory<>("Comment"));

        ContainersTableView.getColumns().addAll(ContainerIdColumn, ContainerNumColumn, ContainerLicenceColumn, ContainerLicenceExpirationdateColumn,
                ContainerMaximumWeightColumn, ContainerCombartementNumberColumn, ContainerCalibrationExpirationDateColumn,
                ContainerPermissionsColumn, ContainerCommentColumn);
        ContainersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ContainersTableView.prefHeightProperty().bind(tabContainer.heightProperty().subtract(ContainersVvox.heightProperty()));

        ContainersHbox.getItems().addAll(InsertContainer, UpdateContainer, DeleteContainer, containersReport);
        ContainersHbox.setPadding(new Insets(10, 10, 10, 10));

        ContainersVvox.getChildren().addAll(ContainersDataEntry, ContainersHbox);
        ContainersVvox.setPadding(new Insets(10, 10, 10, 10));
        ContainersVvox.setSpacing(10);
        ContainersVvox.setAlignment(Pos.CENTER);

        ContainersTab.setContent(ContainersPane);
        ContainersTab.setClosable(false);

        ContainersPane.getChildren().add(ContainersVvox);
        ContainersPane.getChildren().add(ContainersTableView);
        ContainersPane.setPadding(new Insets(10));

        ContainersTab.setContent(ContainersPane);
        ContainersTab.setClosable(false);
    }

    private  void actionHandling() {
        trailersActionHandling();
        containersActionHandling();
    }
    private  void trailersActionHandling() {
        InsertTrailer.setOnMouseClicked(this::onCreateTrailer);
        DeleteTrailer.setOnMouseClicked(this::onDeleteTrailer);
        UpdateTrailer.setOnMouseClicked(this::onUpdateTrailer);

        TrailersTableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TruckTrailerDTO>) action -> {
            try {
                if (action.getList().size() > 0) {
                    TruckTrailerDTO selected = TrailersTableView.getSelectionModel().getSelectedItem();
                    TrailerIdField.setText(String.valueOf(selected.getId()));
                    TrailerNumberField.setText(selected.getTrailerNumber());
                    TrailerLicenceNumberField.setText(selected.getLicenceNumber());
                    TrailerLicenceExpirationDateField.setValue(selected.getLicenceExpirationDate());
                    TrailerPermissionField.setValue(selected.getPermissions());
                    TrailerCommentField.setText(selected.getComment());
                }
            } catch (Exception e) {
                showErrorWindow("Error Importing data", "Please select table row again");
            }
        });
    }
    private  void containersActionHandling() {
        InsertContainer.setOnMouseClicked(this::onCreateContainer);
        DeleteContainer.setOnMouseClicked(this::onDeleteContainer);
        UpdateContainer.setOnMouseClicked(this::onUpdateContainer);

        ContainersTableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TruckContainerDTO>) action -> {
            try {
                if (ContainersTableView.getSelectionModel().getSelectedItems().size() > 0) {
                    TruckContainerDTO selected = ContainersTableView.getSelectionModel().getSelectedItems().get(0);
                    ContainerIdField.setText(String.valueOf(selected.getId()));
                    ContainerNumberField.setText(String.valueOf(selected.getContainerNumber()));
                    ContainerLicenceNumberField.setText(selected.getLicenceNumber());
                    ContainerLicenceExpirationDateField.setValue(selected.getLicenceExpirationDate());
                    ContainerMaxWeightField.setText(String.valueOf(selected.getMaximumWeightConstrain()));
                    ContainerCombartementsNumberField.setText(String.valueOf(selected.getCompartementsNumber()));
                    ContainerCalibrationExpirationDateField.setValue(selected.getCalibrationExpirationDate());
                    ContainerpermissionField.setValue(selected.getPermissions());
                    ContainerCommentField.setText(selected.getComment());
                }
            } catch (Exception e) {
                showErrorWindowForException("Error Importing data", e);
            }
        });
    }

    @Async
    private void onCreateTrailer(MouseEvent action) {
        if ((TrailerNumberField.getText().length() > 0) && (TrailerLicenceNumberField.getText().length() > 0)) {
            if ((truckTrailerService.findByLicence(Long.parseLong(TrailerLicenceNumberField.getText())).isEmpty())) {
                TruckTrailerDTO trailer = new TruckTrailerDTO(TrailerNumberField.getText(), TrailerLicenceNumberField.getText(),
                      TrailerLicenceExpirationDateField.getValue(), TrailerPermissionField.getSelectionModel().getSelectedItem().toString(),
                        TrailerCommentField.getText());
                truckTrailerService.save(trailer);
                update();
            } else {
                showErrorWindow("Error Inserting data", "Truck Trailer already exist , please check entered data ...");
            }
        } else {
            showErrorWindow("Error inserting data", "Please check entered data .. No possible empty fields");
        }
    }

    @Async
    private void onDeleteTrailer(MouseEvent action) {
        try {

                truckTrailerService.deleteById(Long.parseLong(TrailerNumberField.getText()));
                update();

        }catch (Exception e){
            showErrorWindowForException("Error deleting trailer", e);
        }
    }

    @Async
    private void onUpdateTrailer(MouseEvent action) {
        if ((TrailerNumberField.getText().length() > 0) && (TrailerLicenceNumberField.getText().length() > 0)) {
            if (truckTrailerService.findById(Long.parseLong(TrailerIdField.getText())).isPresent()) {
                TruckTrailerDTO trailer = new TruckTrailerDTO(Long.parseLong(TrailerIdField.getText()), TrailerNumberField.getText(), TrailerLicenceNumberField.getText(),
                       TrailerLicenceExpirationDateField.getValue(), TrailerPermissionField.getSelectionModel().getSelectedItem().toString(),
                        TrailerCommentField.getText());

                truckTrailerService.save(trailer);
                update();
            } else {
                showErrorWindow("Error Inserting data", "Truck Trailer not exist , please check entered data ...");
            }
        } else {
            showErrorWindow("Error updating data", "Please check entered data .. No possible empty fields");
        }
    }


    @Async
    private void onCreateContainer(MouseEvent action) {
        if ((ContainerNumberField.getText().length() > 0) && (ContainerLicenceNumberField.getText().length() > 0)
                && (ContainerMaxWeightField.getText().length() > 0) && (ContainerCombartementsNumberField.getText().length() > 0)) {
            if (truckContainerService.findByLicence(Long.parseLong(ContainerLicenceNumberField.getText())).isEmpty()) {
                TruckContainerDTO container = new TruckContainerDTO(ContainerNumberField.getText(),
                        ContainerLicenceNumberField.getText(),ContainerLicenceExpirationDateField.getValue(),
                        Double.parseDouble(ContainerMaxWeightField.getText()), Integer.parseInt(ContainerCombartementsNumberField.getText()),
                       ContainerCalibrationExpirationDateField.getValue(),
                        ContainerpermissionField.getSelectionModel().getSelectedItem().toString(), ContainerCommentField.getText());

                truckContainerService.save(container);

                update();
            } else {
                showErrorWindow("Error Inserting data", "Truck Container already exist , please check entered data ...");
            }
        } else {
            showErrorWindow("Error inserting data", "Please check entered data .. No possible empty fields");
        }
    }

    @Async
    private void onDeleteContainer(MouseEvent action) {
        try {
                truckContainerService.deleteById(Long.parseLong(ContainerIdField.getText()));
                update();

        }catch (Exception e){
            showErrorWindowForException("Error deleting container", e);
        }
    }

    @Async
    private void onUpdateContainer(MouseEvent action) {
        if ((ContainerNumberField.getText().length() > 0) && (ContainerLicenceNumberField.getText().length() > 0)
                && (ContainerMaxWeightField.getText().length() > 0) && (ContainerCombartementsNumberField.getText().length() > 0)) {
            if (truckContainerService.findById(Long.parseLong(ContainerIdField.getText())).isEmpty()) {
                TruckContainerDTO container = new TruckContainerDTO(Long.parseLong(ContainerIdField.getText()), ContainerNumberField.getText(),
                        ContainerLicenceNumberField.getText(),ContainerLicenceExpirationDateField.getValue(),
                        Double.parseDouble(ContainerMaxWeightField.getText()), Integer.parseInt(ContainerCombartementsNumberField.getText()),
                        ContainerCalibrationExpirationDateField.getValue(),
                        ContainerpermissionField.getSelectionModel().getSelectedItem().toString(), ContainerCommentField.getText());

                truckContainerService.save(container);
                update();
            } else {
                showErrorWindow("Error Inserting data", "Truck Container not exist , please check entered data ...");
            }
        } else {
            showErrorWindow("Error updating data", "Please check entered data .. No possible empty fields");
        }
    }


    @Async
    public  void update() {
        List<String> permissions = Stream.of(Permissions.values()).map(Enum::name).collect(Collectors.toList());
        TrailersList = FXCollections.observableArrayList(truckTrailerService.findAll());
        ContainersList = FXCollections.observableArrayList(truckContainerService.findAll());
        Platform.runLater(() -> {

            TrailerPermissionField.getItems().clear();
            ContainerpermissionField.getItems().clear();
            TrailerPermissionField.getItems().addAll(permissions);
            ContainerpermissionField.getItems().addAll(permissions);

            TrailersTableView.getItems().clear();
            TrailersTableView.setItems(TrailersList);

            ContainersTableView.getItems().clear();
            ContainersTableView.setItems(ContainersList);
        });
    }

    public Node getTabContainer() {
        return root;
    }

    protected void showErrorWindow(String header, String content){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.getDialogPane().setMaxWidth(500);
            alert.initOwner(mainWindow);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.show();
        });
    }
    protected void showErrorWindowForException(String header, Throwable e) {
        Platform.runLater(() -> {
            ExceptionDialog exceptionDialog = new ExceptionDialog(e);
            exceptionDialog.setHeaderText(header);
            exceptionDialog.getDialogPane().setMaxWidth(500);
            exceptionDialog.initOwner(mainWindow);
            exceptionDialog.initModality(Modality.WINDOW_MODAL);
            exceptionDialog.show();

        });
    }
}
