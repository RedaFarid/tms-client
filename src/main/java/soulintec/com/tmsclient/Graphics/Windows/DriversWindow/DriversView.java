package soulintec.com.tmsclient.Graphics.Windows.DriversWindow;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
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
import soulintec.com.tmsclient.Graphics.Controls.DataEntryPartitionTitled;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedButton;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedLongField;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedTextField;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Services.DriverService;

import java.time.LocalDateTime;

@Component
public class DriversView implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private DriversController controller;
    private DriversModel model;

    protected static MainWindow initialStage;

    private DataEntryPartitionTitled dataEntryPartitionTitled;
    private VBox root;
    private VBox vbox;
    private ToolBar hbox;
    private Label headerLabel;

    private Stage mainWindow;

    private TableView<DriversModel.TableObject> table;
    private TableColumn<DriversModel.TableObject, LongProperty> idColumn;
    private TableColumn<DriversModel.TableObject, StringProperty> nameColumn;
    private TableColumn<DriversModel.TableObject, StringProperty> licenceNumberColumn;
    private TableColumn<DriversModel.TableObject, StringProperty> licenceExpirationDateColumn;
    private TableColumn<DriversModel.TableObject, StringProperty> mobileNumberColumn;
    private TableColumn<DriversModel.TableObject, StringProperty> permissionsColumn;
    private TableColumn<DriversModel.TableObject, StringProperty> commentColumn;
    private TableColumn<DriversModel.TableObject, StringProperty> createdByColumn;
    private TableColumn<DriversModel.TableObject, StringProperty> onTerminalColumn;
    private TableColumn<DriversModel.TableObject, ObjectProperty<LocalDateTime>> creationDateColumn;
    private TableColumn<DriversModel.TableObject, ObjectProperty<LocalDateTime>> modifyDateColumn;

    private EnhancedButton insert;
    private EnhancedButton delete;
    private EnhancedButton update;
    private EnhancedButton report;
    private Label idLabel;
    private Label nameLabel;
    private Label licenseNumLabel;
    private Label licenceExpiryLabel;
    private Label telNumberLabel;
    private Label permissionsLabel;
    private Label commentLabel;
    private Label creationDateLabel;
    private Label modificationLabel;
    private Label onTerminalLabel;
    private Label createdByLabel;

    private EnhancedLongField idField;
    private EnhancedTextField nameField;
    private EnhancedTextField licenceNumField;
    private DatePicker licenceExpiryField;
    private EnhancedTextField telNumField;
    private ComboBox <Permissions>permissionsField;
    private EnhancedTextField commentField;
    private EnhancedTextField creationDateField;
    private EnhancedTextField modificationDateField;
    private EnhancedTextField createdByField;
    private EnhancedTextField onTerminalField;
    @Autowired
    private DriverService driverService;

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
        controller = ApplicationContext.applicationContext.getBean(DriversController.class);
        model = controller.getModel();

        dataEntryPartitionTitled = new DataEntryPartitionTitled("Driver");
        root = new VBox();
        vbox = new VBox();
        hbox = new ToolBar();
        headerLabel = new Label("Drivers");

        table = new TableView<>();
        idColumn = new TableColumn<>("Id");
        nameColumn = new TableColumn<>("Name");
        licenceNumberColumn = new TableColumn<>("License Number");
        licenceExpirationDateColumn = new TableColumn<>("License Expiry");
        mobileNumberColumn = new TableColumn<>("Tel Number");
        permissionsColumn = new TableColumn<>("Permissions");
        commentColumn = new TableColumn<>("Comment");
        createdByColumn = new TableColumn<>("Created By");
        onTerminalColumn = new TableColumn<>("On Terminal");
        creationDateColumn = new TableColumn<>("Creation Date");
        modifyDateColumn = new TableColumn<>("Modification Date");

        insert = new EnhancedButton("Create new driver");
        delete = new EnhancedButton("Delete selected driver");
        update = new EnhancedButton("Update selected driver");
        report = new EnhancedButton("Show Report");

        idLabel = new Label("Id :");
        nameLabel = new Label("Name :");
        licenseNumLabel = new Label("License Number :");
        licenceExpiryLabel = new Label("License Expiry :");
        telNumberLabel = new Label("Tel Number :");
        permissionsLabel = new Label("Permissions :");
        commentLabel = new Label("Comment :");
        creationDateLabel = new Label("Creation Date :");
        modificationLabel = new Label("Modification Date :");
        onTerminalLabel = new Label("On Terminal :");
        createdByLabel = new Label("Created By :");

        idField = new EnhancedLongField();
        nameField = new EnhancedTextField();
        licenceNumField = new EnhancedTextField();
        licenceExpiryField = new DatePicker();
        telNumField = new EnhancedTextField();
        permissionsField = new ComboBox();
        commentField = new EnhancedTextField();
        creationDateField = new EnhancedTextField();
        modificationDateField = new EnhancedTextField();
        createdByField = new EnhancedTextField();
        onTerminalField = new EnhancedTextField();
    }

    private void userAuthorities() {

    }

    private void graphicsBuilder() {
        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(root.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        //control buttons configuration
        insert.setPrefWidth(150);
        delete.setPrefWidth(150);
        update.setPrefWidth(150);
        report.setPrefWidth(150);

        //dataentery region configuration
        idLabel.setPrefWidth(150);
        nameLabel.setPrefWidth(150);
        licenseNumLabel.setPrefWidth(150);
        licenceExpiryLabel.setPrefWidth(150);
        telNumberLabel.setPrefWidth(150);
        permissionsLabel.setPrefWidth(150);
        commentLabel.setPrefWidth(150);
        creationDateLabel.setPrefWidth(150);
        modificationLabel.setPrefWidth(150);
        createdByLabel.setPrefWidth(150);
        onTerminalLabel.setPrefWidth(150);

        idLabel.setTextAlignment(TextAlignment.RIGHT);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        licenseNumLabel.setTextAlignment(TextAlignment.RIGHT);
        licenceExpiryLabel.setTextAlignment(TextAlignment.RIGHT);
        telNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        permissionsLabel.setTextAlignment(TextAlignment.RIGHT);
        commentLabel.setTextAlignment(TextAlignment.RIGHT);
        creationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        modificationLabel.setTextAlignment(TextAlignment.RIGHT);
        createdByLabel.setTextAlignment(TextAlignment.RIGHT);
        onTerminalLabel.setTextAlignment(TextAlignment.RIGHT);

        idLabel.setAlignment(Pos.BASELINE_RIGHT);
        nameLabel.setAlignment(Pos.BASELINE_RIGHT);
        licenseNumLabel.setAlignment(Pos.BASELINE_RIGHT);
        licenceExpiryLabel.setAlignment(Pos.BASELINE_RIGHT);
        telNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        permissionsLabel.setAlignment(Pos.BASELINE_RIGHT);
        commentLabel.setAlignment(Pos.BASELINE_RIGHT);
        creationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        modificationLabel.setAlignment(Pos.BASELINE_RIGHT);
        createdByLabel.setAlignment(Pos.BASELINE_RIGHT);
        onTerminalLabel.setAlignment(Pos.BASELINE_RIGHT);

        idLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        nameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        licenseNumLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        licenceExpiryLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        telNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        permissionsLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        commentLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        creationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        modificationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        createdByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        onTerminalLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        idField.setPrefWidth(250);
        nameField.setPrefWidth(250);
        licenceNumField.setPrefWidth(250);
        licenceExpiryField.setPrefWidth(250);
        telNumField.setPrefWidth(250);
        permissionsField.setPrefWidth(250);
        commentField.setPrefWidth(250);
        creationDateField.setPrefWidth(250);
        modificationDateField.setPrefWidth(250);
        createdByField.setPrefWidth(250);
        onTerminalField.setPrefWidth(250);

        idField.setEditable(false);
        creationDateField.setEditable(false);
        modificationDateField.setEditable(false);
        createdByField.setEditable(false);
        onTerminalField.setEditable(false);

        //restriction handling
        nameField.setRestrict("[a-zA-Z ]");
        nameField.setMaxLength(45);
        licenceNumField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                licenceNumField.setText(oldValue);
            }
        });
        licenceNumField.setMaxLength(14);
        telNumField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                telNumField.setText(oldValue);
            }
        });
        telNumField.setMaxLength(11);
        commentField.setRestrict("[a-zA-Z -_/]");
        commentField.setMaxLength(500);

        //disabling aditable datepickers
        licenceExpiryField.setEditable(false);

        dataEntryPartitionTitled.add(idLabel, 1, 1);
        dataEntryPartitionTitled.add(idField, 2, 1);

        dataEntryPartitionTitled.add(nameLabel, 3, 1);
        dataEntryPartitionTitled.add(nameField, 4, 1);

        dataEntryPartitionTitled.add(telNumberLabel, 5, 1);
        dataEntryPartitionTitled.add(telNumField, 6, 1);

        dataEntryPartitionTitled.add(permissionsLabel, 7, 1);
        dataEntryPartitionTitled.add(permissionsField, 8, 1);

        dataEntryPartitionTitled.add(licenseNumLabel, 1, 2);
        dataEntryPartitionTitled.add(licenceNumField, 2, 2);

        dataEntryPartitionTitled.add(licenceExpiryLabel, 3, 2);
        dataEntryPartitionTitled.add(licenceExpiryField, 4, 2);

        dataEntryPartitionTitled.add(commentLabel, 5, 2);
        dataEntryPartitionTitled.add(commentField, 6, 2,3,1);

        dataEntryPartitionTitled.add(creationDateLabel, 1, 3);
        dataEntryPartitionTitled.add(creationDateField, 2, 3);

        dataEntryPartitionTitled.add(modificationLabel, 3, 3);
        dataEntryPartitionTitled.add(modificationDateField, 4, 3);

        dataEntryPartitionTitled.add(createdByLabel, 5, 3);
        dataEntryPartitionTitled.add(createdByField, 6, 3);

        dataEntryPartitionTitled.add(onTerminalLabel, 7, 3);
        dataEntryPartitionTitled.add(onTerminalField, 8, 3);

        //table configuration
        idColumn.setCellValueFactory(new PropertyValueFactory<>("driverIdColumn"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        licenceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("licenseNumberColumn"));
        licenceExpirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("licenceExpirationDateColumn"));
        mobileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNumberColumn"));
        permissionsColumn.setCellValueFactory(new PropertyValueFactory<>("permissionsColumn"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("commentColumn"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDateColumn"));
        modifyDateColumn.setCellValueFactory(new PropertyValueFactory<>("modifyDateColumn"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdByColumn"));
        onTerminalColumn.setCellValueFactory(new PropertyValueFactory<>("onTerminalColumn"));


        table.getColumns().addAll(idColumn, nameColumn, licenceNumberColumn, licenceExpirationDateColumn,
                mobileNumberColumn, permissionsColumn, commentColumn,creationDateColumn, modifyDateColumn, createdByColumn, onTerminalColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.prefHeightProperty().bind(root.heightProperty().subtract(vbox.heightProperty()));
        table.setItems(controller.getDataList());

        hbox.getItems().addAll(insert, update, delete);
        hbox.setPadding(new Insets(10, 10, 10, 10));

        vbox.getChildren().addAll(dataEntryPartitionTitled, hbox);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        root.getChildren().addAll(headerLabel, vbox, table);
        root.setPadding(new Insets(10));

        permissionsField.getItems().addAll(FXCollections.observableArrayList(Permissions.values()));

        idField.longValueProperty().bindBidirectional(model.driverIdProperty());
        nameField.textProperty().bindBidirectional(model.nameProperty());
        licenceNumField.textProperty().bindBidirectional(model.licenseNumberProperty());
        licenceExpiryField.valueProperty().bindBidirectional(model.licenceExpirationDateProperty());
        telNumField.textProperty().bindBidirectional(model.mobileNumberProperty());
        permissionsField.valueProperty().bindBidirectional(model.permissionsProperty());
        commentField.textProperty().bindBidirectional(model.commentProperty());
        createdByField.textProperty().bindBidirectional(model.createdByProperty());
        onTerminalField.textProperty().bindBidirectional(model.onTerminalProperty());
        modificationDateField.textProperty().bindBidirectional(model.modifyDateProperty());
        creationDateField.textProperty().bindBidirectional(model.creationDateProperty());

    }

    private void actionHandling() {
        insert.setOnMouseClicked(controller::onInsert);
        delete.setOnMouseClicked(controller::onDelete);
        update.setOnMouseClicked(controller::onUpdate);

        table.setOnMouseClicked((a) -> {
            controller.onTableSelection(table.getSelectionModel().getSelectedItems());
        });
    }

    @Async
    public void update() {
        controller.updateDataList();
    }

    public Node getRoot() {
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