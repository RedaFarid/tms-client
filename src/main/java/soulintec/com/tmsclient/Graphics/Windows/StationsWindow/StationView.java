package soulintec.com.tmsclient.Graphics.Windows.StationsWindow;

import javafx.application.Platform;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableFilter;
import org.controlsfx.dialog.ExceptionDialog;
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
import soulintec.com.tmsclient.Services.StationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class StationView implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private StationsController controller;
    private MainWindowController mainWindowController;
    private StationsModel model;

    protected static MainWindow initialStage;

    private DataEntryPartitionTitled dataEntryPartitionTitled;

    private VBox stationsTab;
    private VBox stationsPane;

    private VBox stationsVbox;
    private ToolBar stationsHbox;
    private Label headerLabel;

    private BorderPane root;
    private TabPane tabContainer;

    private Stage mainStage;

    private TableFilter<StationsModel.TableObject> stationsTableFilter;

    private TableView<StationsModel.TableObject> table;
    private TableColumn<StationsModel.TableObject, LongProperty> stationIDColumn;
    private TableColumn<StationsModel.TableObject, StringProperty> stationNameColumn;
    private TableColumn<StationsModel.TableObject, StringProperty> locationColumn;
    private TableColumn<StationsModel.TableObject, ObjectProperty<String>> computerNameColumn;
    private TableColumn<StationsModel.TableObject, StringProperty> commentColumn;
    private TableColumn<StationsModel.TableObject, StringProperty> createdByColumn;
    private TableColumn<StationsModel.TableObject, StringProperty> modifiedByColumn;
    private TableColumn<StationsModel.TableObject, StringProperty> onTerminalColumn;
    private TableColumn<StationsModel.TableObject, ObjectProperty<LocalDateTime>> creationDateColumn;
    private TableColumn<StationsModel.TableObject, ObjectProperty<LocalDateTime>> modifyDateColumn;

    private EnhancedButton insertStation;
    private EnhancedButton deleteStation;
    private EnhancedButton updateStation;
    private EnhancedButton report;

    private Label idLabel;
    private Label nameLabel;
    private Label locationLabel;
    private Label computerNameLabel;
    private Label commentLabel;
    private Label creationDateLabel;
    private Label modificationLabel;
    private Label onTerminalLabel;
    private Label createdByLabel;
    private Label modifiedByLabel;

    private EnhancedLongField idField;
    private EnhancedTextField nameField;
    private EnhancedTextField locationField;
    private ComboBox<String> computerNameField;
    private EnhancedTextField commentField;
    private EnhancedTextField creationDateField;
    private EnhancedTextField modificationDateField;
    private EnhancedTextField createdByField;
    private EnhancedTextField modifiedByField;
    private EnhancedTextField onTerminalField;

    @Autowired
    StationService stationService;

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
        controller = ApplicationContext.applicationContext.getBean(StationsController.class);
        mainWindowController = ApplicationContext.applicationContext.getBean(MainWindowController.class);

        model = controller.getModel();

        dataEntryPartitionTitled = new DataEntryPartitionTitled("Station");
        stationsTab = new VBox();
        stationsPane = new VBox();
        stationsVbox = new VBox();
        stationsHbox = new ToolBar();
        headerLabel = new Label("Stations");

        root = new BorderPane();
        tabContainer = new TabPane();

        table = new TableView<>();

        stationIDColumn = new TableColumn<>("ID");
        stationNameColumn = new TableColumn<>("Name");
        locationColumn = new TableColumn<>("Location");
        computerNameColumn = new TableColumn<>("Computer name");
        commentColumn = new TableColumn<>("Comment");
        createdByColumn = new TableColumn<>("Created By");
        modifiedByColumn = new TableColumn<>("Modified By");
        onTerminalColumn = new TableColumn<>("On Terminal");
        creationDateColumn = new TableColumn<>("Creation Date");
        modifyDateColumn = new TableColumn<>("Modification Date");

        insertStation = new EnhancedButton("Create new station");
        deleteStation = new EnhancedButton("Delete selected station");
        updateStation = new EnhancedButton("Update selected station");
        report = new EnhancedButton("Show Report");

        idLabel = new Label("Id :");
        nameLabel = new Label("Name :");
        locationLabel = new Label("Location :");
        computerNameLabel = new Label("Computer name :");
        commentLabel = new Label("Comment :");
        creationDateLabel = new Label("Creation Date :");
        modificationLabel = new Label("Modification Date :");
        onTerminalLabel = new Label("On Terminal :");
        createdByLabel = new Label("Created By :");
        modifiedByLabel = new Label("Modified By :");

        idField = new EnhancedLongField();
        nameField = new EnhancedTextField();
        locationField = new EnhancedTextField();
        computerNameField = new ComboBox<>();
        commentField = new EnhancedTextField();
        creationDateField = new EnhancedTextField();
        modificationDateField = new EnhancedTextField();
        createdByField = new EnhancedTextField();
        modifiedByField = new EnhancedTextField();
        onTerminalField = new EnhancedTextField();
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
            insertStation.setAuthority(authorityDTOSList.get(0));
            updateStation.setAuthority(authorityDTOSList.get(0));
            deleteStation.setAuthority(authorityDTOSList.get(1));
        }
    }

    private void graphicsBuilder() {
        root.setTop(headerLabel);
        root.setCenter(stationsTab);
        root.setPadding(new Insets(10));

        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(root.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        //control buttons configuration
        insertStation.setPrefWidth(150);
        deleteStation.setPrefWidth(150);
        updateStation.setPrefWidth(150);
        report.setPrefWidth(150);
        //dataentery region configuration

        idLabel.setPrefWidth(150);
        nameLabel.setPrefWidth(150);
        locationLabel.setPrefWidth(150);
        computerNameLabel.setPrefWidth(150);
        commentLabel.setPrefWidth(150);
        creationDateLabel.setPrefWidth(150);
        modificationLabel.setPrefWidth(150);
        createdByLabel.setPrefWidth(150);
        modifiedByLabel.setPrefWidth(150);
        onTerminalLabel.setPrefWidth(150);

        idField.setEditable(false);
        creationDateField.setEditable(false);
        modificationDateField.setEditable(false);
        createdByField.setEditable(false);
        onTerminalField.setEditable(false);
        modifiedByField.setEditable(false);

        idLabel.setTextAlignment(TextAlignment.RIGHT);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        locationLabel.setTextAlignment(TextAlignment.RIGHT);
        computerNameLabel.setTextAlignment(TextAlignment.RIGHT);
        commentLabel.setTextAlignment(TextAlignment.RIGHT);
        creationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        modificationLabel.setTextAlignment(TextAlignment.RIGHT);
        createdByLabel.setTextAlignment(TextAlignment.RIGHT);
        onTerminalLabel.setTextAlignment(TextAlignment.RIGHT);
        modifiedByLabel.setTextAlignment(TextAlignment.RIGHT);

        idLabel.setAlignment(Pos.BASELINE_RIGHT);
        nameLabel.setAlignment(Pos.BASELINE_RIGHT);
        locationLabel.setAlignment(Pos.BASELINE_RIGHT);
        computerNameLabel.setAlignment(Pos.BASELINE_RIGHT);
        commentLabel.setAlignment(Pos.BASELINE_RIGHT);
        creationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        modificationLabel.setAlignment(Pos.BASELINE_RIGHT);
        createdByLabel.setAlignment(Pos.BASELINE_RIGHT);
        onTerminalLabel.setAlignment(Pos.BASELINE_RIGHT);
        modifiedByLabel.setAlignment(Pos.BASELINE_RIGHT);

        idLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        nameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        locationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        computerNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        commentLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        creationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        modificationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        createdByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        onTerminalLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        modifiedByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        idField.setPrefWidth(250);
//        mainOfficeAdressField.setPrefWidth(650);
        computerNameField.setPrefWidth(250);
        commentField.setPrefWidth(250);
//        contactEmailField.setPrefWidth(650);
        nameField.setPrefWidth(250);
        creationDateField.setPrefWidth(250);
        modificationDateField.setPrefWidth(250);
        createdByField.setPrefWidth(250);
        onTerminalField.setPrefWidth(250);
        modifiedByField.setPrefWidth(250);

        dataEntryPartitionTitled.add(idLabel, 1, 1);
        dataEntryPartitionTitled.add(idField, 2, 1);

        dataEntryPartitionTitled.add(nameLabel, 3, 1);
        dataEntryPartitionTitled.add(nameField, 4, 1);

        dataEntryPartitionTitled.add(locationLabel, 5, 1);
        dataEntryPartitionTitled.add(locationField, 6, 1);

        dataEntryPartitionTitled.add(computerNameLabel, 7, 1);
        dataEntryPartitionTitled.add(computerNameField, 8, 1);

        dataEntryPartitionTitled.add(commentLabel, 5, 2);
        dataEntryPartitionTitled.add(commentField, 6, 2, 3, 1);

        dataEntryPartitionTitled.add(creationDateLabel, 1, 3);
        dataEntryPartitionTitled.add(creationDateField, 2, 3);

        dataEntryPartitionTitled.add(modificationLabel, 3, 3);
        dataEntryPartitionTitled.add(modificationDateField, 4, 3);

        dataEntryPartitionTitled.add(createdByLabel, 5, 3);
        dataEntryPartitionTitled.add(createdByField, 6, 3);

        dataEntryPartitionTitled.add(modifiedByLabel, 7, 3);
        dataEntryPartitionTitled.add(modifiedByField, 8, 3);

        dataEntryPartitionTitled.add(onTerminalLabel, 1, 4);
        dataEntryPartitionTitled.add(onTerminalField, 2, 4);

        //table configuration
        stationIDColumn.setCellValueFactory(new PropertyValueFactory<>("stationIdColumn"));
        stationNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("locationColumn"));
        computerNameColumn.setCellValueFactory(new PropertyValueFactory<>("computerNameColumn"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("commentColumn"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDateColumn"));
        modifyDateColumn.setCellValueFactory(new PropertyValueFactory<>("modifyDateColumn"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdByColumn"));
        onTerminalColumn.setCellValueFactory(new PropertyValueFactory<>("onTerminalColumn"));
        modifiedByColumn.setCellValueFactory(new PropertyValueFactory<>("modifiedByColumn"));

        table.getColumns().addAll(stationIDColumn, stationNameColumn, locationColumn, computerNameColumn, commentColumn, creationDateColumn, modifyDateColumn, createdByColumn, modifiedByColumn,onTerminalColumn);
        table.prefHeightProperty().bind(root.heightProperty().subtract(stationsVbox.heightProperty()));
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(controller.getDataList());
        stationsTableFilter = TableFilter.forTableView(table).apply();

        dataEntryPartitionTitled.setVgap(5);
        dataEntryPartitionTitled.setHgap(5);

        stationsHbox.getItems().addAll(insertStation, updateStation, deleteStation);

        stationsVbox.getChildren().addAll(dataEntryPartitionTitled, stationsHbox);
        stationsVbox.setPadding(new Insets(5));
        stationsVbox.setSpacing(5);

        stationsPane.getChildren().add(stationsVbox);
        stationsPane.getChildren().add(table);

        stationsTab.getChildren().add(stationsPane);

        idField.longValueProperty().bindBidirectional(model.stationIdProperty());
        nameField.textProperty().bindBidirectional(model.nameProperty());
        locationField.textProperty().bindBidirectional(model.locationProperty());
        computerNameField.valueProperty().bindBidirectional(model.computerNameProperty());
        commentField.textProperty().bindBidirectional(model.commentProperty());
        createdByField.textProperty().bindBidirectional(model.createdByProperty());
        onTerminalField.textProperty().bindBidirectional(model.onTerminalProperty());
        modificationDateField.textProperty().bindBidirectional(model.modifyDateProperty());
        creationDateField.textProperty().bindBidirectional(model.creationDateProperty());
        modifiedByField.textProperty().bindBidirectional(model.modifiedByProperty());
    }

    private void actionHandling() {
        insertStation.setOnMouseClicked(controller::onInsert);
        deleteStation.setOnMouseClicked(controller::onDelete);
        updateStation.setOnMouseClicked(controller::onUpdate);
        computerNameField.setOnMouseClicked(i -> {
            controller.updateComputers();
            computerNameField.setItems(controller.getComputers());
        });
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
        return "Stations";
    }
}
