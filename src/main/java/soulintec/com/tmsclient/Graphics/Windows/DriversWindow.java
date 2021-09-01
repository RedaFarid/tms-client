package soulintec.com.tmsclient.Graphics.Windows;

import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.DriverDTO;
import soulintec.com.tmsclient.Graphics.Controls.DataEntryPartitionTitled;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedButton;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedTextField;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Services.DriverService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Component
public class DriversWindow implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private DataEntryPartitionTitled dataentery = new DataEntryPartitionTitled("Driver");
    private VBox root = new VBox();
    private VBox vbox = new VBox();
    private ToolBar hbox = new ToolBar();
    private Label headerLabel = new Label("Drivers");

    private Stage mainWindow = null;

    private ObservableList<DriverDTO> list = FXCollections.observableArrayList();
    private TableView<DriverDTO> table = new TableView<>();
    private TableColumn<DriverDTO, Long> idColumn = new TableColumn<>("Id");
    private TableColumn<DriverDTO, String> NameColumn = new TableColumn<>("Name");
    private TableColumn<DriverDTO, String> LicenceNumberColumn = new TableColumn<>("License Number");
    private TableColumn<DriverDTO, String> LicenceExpirationDateColumn = new TableColumn<>("License Expiry");
    private TableColumn<DriverDTO, String> MobileNumberColumn = new TableColumn<>("Tel Numer");
    private TableColumn<DriverDTO, String> PermissionsColumn = new TableColumn<>("Permissions");
    private TableColumn<DriverDTO, String> CommentColumn = new TableColumn<>("Comment");

    private EnhancedButton Insert = new EnhancedButton("Create new driver");
    private EnhancedButton Delete = new EnhancedButton("Delete selected driver");
    private EnhancedButton Update = new EnhancedButton("Update selected driver");
    private EnhancedButton report = new EnhancedButton("Show Report");
    private Label idLabel = new Label("Id :");
    private Label nameLabel = new Label("Name :");
    private Label licensenumLabel = new Label("License Number :");
    private Label licenceExpiryLabel = new Label("License Expiry :");
    private Label telNumberLabel = new Label("Tel Number :");
    private Label permissionsLabel = new Label("Permissions :");
    private Label commentLabel = new Label("Comment :");

    private EnhancedTextField idField = new EnhancedTextField();
    private EnhancedTextField nameFieldField = new EnhancedTextField();
    private EnhancedTextField licenceNumField = new EnhancedTextField();
    private DatePicker LicenceExpiryField = new DatePicker();
    private EnhancedTextField telNumField = new EnhancedTextField();
    private ComboBox permissionsField = new ComboBox();
    private EnhancedTextField commentField = new EnhancedTextField();

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);

    @Autowired
    private DriverService driverService;

    @Autowired(required = false)
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

    private void graphicsBuilder() {
        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(root.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        //control buttons configuration
        Insert.setPrefWidth(150);
        Delete.setPrefWidth(150);
        Update.setPrefWidth(150);
        report.setPrefWidth(150);

        //dataentery region configuration
        idLabel.setPrefWidth(150);
        nameLabel.setPrefWidth(150);
        licensenumLabel.setPrefWidth(150);
        licenceExpiryLabel.setPrefWidth(150);
        telNumberLabel.setPrefWidth(150);
        permissionsLabel.setPrefWidth(150);
        commentLabel.setPrefWidth(150);

        idLabel.setTextAlignment(TextAlignment.RIGHT);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        licensenumLabel.setTextAlignment(TextAlignment.RIGHT);
        licenceExpiryLabel.setTextAlignment(TextAlignment.RIGHT);
        telNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        permissionsLabel.setTextAlignment(TextAlignment.RIGHT);
        commentLabel.setTextAlignment(TextAlignment.RIGHT);

        idLabel.setAlignment(Pos.BASELINE_RIGHT);
        nameLabel.setAlignment(Pos.BASELINE_RIGHT);
        licensenumLabel.setAlignment(Pos.BASELINE_RIGHT);
        licenceExpiryLabel.setAlignment(Pos.BASELINE_RIGHT);
        telNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        permissionsLabel.setAlignment(Pos.BASELINE_RIGHT);
        commentLabel.setAlignment(Pos.BASELINE_RIGHT);

        idLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        nameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        licensenumLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        licenceExpiryLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        telNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        permissionsLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        commentLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        idField.setPrefWidth(250);
        nameFieldField.setPrefWidth(250);
        licenceNumField.setPrefWidth(250);
        LicenceExpiryField.setPrefWidth(250);
        telNumField.setPrefWidth(250);
        permissionsField.setPrefWidth(250);
        commentField.setPrefWidth(250);

        //restriction handling
        nameFieldField.setRestrict("[a-zA-Z ]");
        nameFieldField.setMaxLength(45);
        licenceNumField.setRestrict("[0-9]");
        licenceNumField.setMaxLength(14);
        telNumField.setRestrict("[0-9]");
        telNumField.setMaxLength(11);
        commentField.setRestrict("[a-zA-Z -_/]");
        commentField.setMaxLength(500);

        //disabling aditable datepickers
        LicenceExpiryField.setEditable(false);

        dataentery.add(idLabel, 5, 2);
        dataentery.add(nameLabel, 1, 2);
        dataentery.add(licensenumLabel, 3, 2);
        dataentery.add(licenceExpiryLabel, 1, 3);
        dataentery.add(telNumberLabel, 3, 3);
        dataentery.add(permissionsLabel, 1, 4);
        dataentery.add(commentLabel, 3, 4);

        dataentery.add(idField, 6, 2);
        dataentery.add(nameFieldField, 2, 2);
        dataentery.add(licenceNumField, 4, 2);
        dataentery.add(LicenceExpiryField, 2, 3);
        dataentery.add(telNumField, 4, 3);
        dataentery.add(permissionsField, 2, 4);
        dataentery.addRow(4, commentField);

        //table configuration
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        LicenceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("LicenceNumber"));
        LicenceExpirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("LicenceExpirationDate"));
        MobileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("MobileNumber"));
        PermissionsColumn.setCellValueFactory(new PropertyValueFactory<>("Permissions"));
        CommentColumn.setCellValueFactory(new PropertyValueFactory<>("Comment"));

        table.getColumns().addAll(idColumn,NameColumn, LicenceNumberColumn, LicenceExpirationDateColumn, MobileNumberColumn, PermissionsColumn, CommentColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.prefHeightProperty().bind(root.heightProperty().subtract(vbox.heightProperty()));
        table.setItems(list);

        hbox.getItems().addAll(Insert, Update, Delete, report);
        hbox.setPadding(new Insets(10, 10, 10, 10));

        vbox.getChildren().addAll(dataentery, hbox);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        root.getChildren().addAll(headerLabel, vbox, table);
        root.setPadding(new Insets(10));

    }
    private void actionHandling() {
        Insert.setOnMouseClicked(this::onCreate);
        Delete.setOnMouseClicked(this::onDelete);
        Update.setOnMouseClicked(this::onUpdate);

        table.getSelectionModel().getSelectedItems().addListener((ListChangeListener<DriverDTO>) c -> {
            try {
                if (!c.getList().isEmpty()) {
                    DriverDTO selected = c.getList().get(0);
                    idField.setText(String.valueOf(selected.getId()));
                    nameFieldField.setText(selected.getName());
                    licenceNumField.setText(selected.getLicenceNumber());
                    LicenceExpiryField.setValue(selected.getLicenceExpirationDate());
                    telNumField.setText(selected.getMobileNumber());
                    permissionsField.setValue(selected.getPermissions());
                    commentField.setText(selected.getComment());
                }
            } catch (Exception e) {
                showErrorWindowForException("Error reading selected driver", e);
            }
        });
    }

    @Async
    private void onUpdate(MouseEvent mouseEvent) {
        try {
            if ((nameFieldField.getText().length() > 0) && (licenceNumField.getText().length() > 0) && (telNumField.getText().length() > 0)) {
                if ((driverService.findByLicenceId(licenceNumField.getText()).isPresent())) {

                    DriverDTO driver = new DriverDTO(Long.parseLong(idField.getText()), licenceNumField.getText(), nameFieldField.getText(),
                            LicenceExpiryField.getValue(), telNumField.getText(),
                            permissionsField.getSelectionModel().getSelectedItem().toString(), commentField.getText());

                    driverService.save(driver);
                    update();
                } else {
                    showErrorWindow("Error inserting data", "Driver not exist , please check entered data ...");
                }
            } else {
                showErrorWindow("Error inserting data", "Please check entered data .. No possible empty fields");
            }
        } catch (Exception e) {
            showErrorWindowForException("Error updating driver", e);
        }
    }

    @Async
    private void onDelete(MouseEvent mouseEvent) {
        try {
                driverService.deleteById(Long.parseLong(idField.getText()));
                update();

        } catch (Exception e) {
            showErrorWindowForException("Error deleting client", e);
        }
    }

    @Async
    private void onCreate(MouseEvent mouseEvent) {
        try {
            if ((nameFieldField.getText().length() > 0) && (licenceNumField.getText().length() > 0) && (telNumField.getText().length() > 0)) {
                if (!driverService.findByLicenceId(licenceNumField.getText()).isPresent()) {
                    DriverDTO driver = new DriverDTO(licenceNumField.getText(), nameFieldField.getText(),
                        LicenceExpiryField.getValue(), telNumField.getText(),
                            permissionsField.getSelectionModel().getSelectedItem().toString(), commentField.getText());
                    driverService.save(driver);
                    update();
                } else {
                    showErrorWindow("Error inserting data", "Driver already exist , please check entered data ...");
                }
            } else {
                showErrorWindow("Error inserting data", "Please check entered data .. No possible empty fields");
            }
            update();
        } catch (Exception e) {
            showErrorWindowForException("Error inserting client", e);
        }
    }

    @Async
    public void update() {
        List<DriverDTO> dataBaseList = driverService.findAll();
        Platform.runLater(() -> {
            getDriversList().removeAll(
                    dataBaseList
                            .stream()
                            .filter(item -> !getDriversList().contains(item))
                            .collect(this::getDriversList, ObservableList::add, ObservableList::addAll)
                            .stream()
                            .filter(tableListItem -> dataBaseList.stream().noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
                            .collect(Collectors.toList()));
        });
    }

    public Node getRoot() {
        return root;
    }

    private ObservableList<DriverDTO> getDriversList() {
        return list;
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