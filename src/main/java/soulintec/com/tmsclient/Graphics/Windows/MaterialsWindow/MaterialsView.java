package soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
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
import soulintec.com.tmsclient.Services.MaterialService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialsView implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private MaterialController controller;
    private MainWindowController mainWindowController;
    private MaterialsModel model;

    protected static MainWindow initialStage;

    private DataEntryPartitionTitled dataEntryPartitionTitled;
    private VBox root;
    private VBox vbox;
    private ToolBar hbox;
    private Label headerLabel;

    public Stage mainWindow;
    private TableFilter<MaterialsModel.TableObject> tableFilter;

    private TableView<MaterialsModel.TableObject> table;
    private TableColumn<MaterialsModel.TableObject, LongProperty> idColumn;
    private TableColumn<MaterialsModel.TableObject, StringProperty> nameColumn;
    private TableColumn<MaterialsModel.TableObject, StringProperty> descriptionColumn;
    private TableColumn<MaterialsModel.TableObject, StringProperty> createdByColumn;
    private TableColumn<MaterialsModel.TableObject, StringProperty> onTerminalColumn;
    private TableColumn<MaterialsModel.TableObject, StringProperty> modifiedByColumn;
    private TableColumn<MaterialsModel.TableObject, ObjectProperty<LocalDateTime>> creationDateColumn;
    private TableColumn<MaterialsModel.TableObject, ObjectProperty<LocalDateTime>> modifyDateColumn;

    private EnhancedButton insert;
    private EnhancedButton delete;
    private EnhancedButton update;
    private EnhancedButton report;

    private Label idLabel;
    private Label nameLabel;
    private Label descriptionLabel;
    private Label creationDateLabel;
    private Label modificationLabel;
    private Label onTerminalLabel;
    private Label createdByLabel;
    private Label modifiedByLabel;

    private EnhancedLongField idField;
    private EnhancedTextField nameField;
    private EnhancedTextField descriptionField;
    private EnhancedTextField creationDateField;
    private EnhancedTextField modificationDateField;
    private EnhancedTextField createdByField;
    private EnhancedTextField modifiedByField;
    private EnhancedTextField onTerminalField;

    @Autowired
    private MaterialService materialService;

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);

    public List<RoleDTO> authorityDTOSList = new ArrayList<>();

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainWindow = event.getStage();
        initialization();
        userAuthorities();
        initialization();
        graphicsBuilder();
        actionHandling();
    }

    private void initialization() {
        initialStage = ApplicationContext.applicationContext.getBean(MainWindow.class);
        controller = ApplicationContext.applicationContext.getBean(MaterialController.class);
        mainWindowController = ApplicationContext.applicationContext.getBean(MainWindowController.class);

        model = controller.getModel();

        root = new VBox();
        dataEntryPartitionTitled = new DataEntryPartitionTitled("Material");
        vbox = new VBox();
        hbox = new ToolBar();
        headerLabel = new Label("Materials");

        table = new TableView<>();

        idColumn = new TableColumn<>("Id");
        nameColumn = new TableColumn<>("Name");
        descriptionColumn = new TableColumn<>("Description");
        createdByColumn = new TableColumn<>("Created By");
        modifiedByColumn = new TableColumn<>("Modified By");
        onTerminalColumn = new TableColumn<>("On Terminal");
        creationDateColumn = new TableColumn<>("Creation Date");
        modifyDateColumn = new TableColumn<>("Modification Date");

        insert = new EnhancedButton("Create new material");
        delete = new EnhancedButton("Delete selected material");
        update = new EnhancedButton("Update selected material");
        report = new EnhancedButton("Show Report");

        idLabel = new Label("Id :");
        nameLabel = new Label("Name :");
        descriptionLabel = new Label("Description :");
        creationDateLabel = new Label("Creation Date :");
        modificationLabel = new Label("Modification Date :");
        onTerminalLabel = new Label("On Terminal :");
        createdByLabel = new Label("Created By :");
        modifiedByLabel = new Label("Modified By :");

        idField = new EnhancedLongField();
        nameField = new EnhancedTextField();
        descriptionField = new EnhancedTextField();
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
        RoleDTO reporting = new RoleDTO("Generate Reports for  " + this);

        authorityDTOSList.add(saving);
        authorityDTOSList.add(deleting);
        authorityDTOSList.add(reporting);

        mainWindowController.createWindowAuthorities(authorityDTOSList);
    }

    private void assignAuthoritiesTemplate() {
        if (authorityDTOSList.size() != 0) {
            insert.setAuthority(authorityDTOSList.get(0));
            update.setAuthority(authorityDTOSList.get(0));
            delete.setAuthority(authorityDTOSList.get(1));
            report.setAuthority(authorityDTOSList.get(2));
        }
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
        descriptionLabel.setPrefWidth(150);
        creationDateLabel.setPrefWidth(150);
        modificationLabel.setPrefWidth(150);
        createdByLabel.setPrefWidth(150);
        onTerminalLabel.setPrefWidth(150);
        modifiedByLabel.setPrefWidth(150);

        idLabel.setTextAlignment(TextAlignment.RIGHT);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        descriptionLabel.setTextAlignment(TextAlignment.RIGHT);
        creationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        modificationLabel.setTextAlignment(TextAlignment.RIGHT);
        createdByLabel.setTextAlignment(TextAlignment.RIGHT);
        onTerminalLabel.setTextAlignment(TextAlignment.RIGHT);
        modifiedByLabel.setTextAlignment(TextAlignment.RIGHT);

        idLabel.setAlignment(Pos.BASELINE_RIGHT);
        nameLabel.setAlignment(Pos.BASELINE_RIGHT);
        descriptionLabel.setAlignment(Pos.BASELINE_RIGHT);
        creationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        modificationLabel.setAlignment(Pos.BASELINE_RIGHT);
        createdByLabel.setAlignment(Pos.BASELINE_RIGHT);
        onTerminalLabel.setAlignment(Pos.BASELINE_RIGHT);
        modifiedByLabel.setAlignment(Pos.BASELINE_RIGHT);

        idLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        nameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        descriptionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        creationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        modificationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        createdByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        onTerminalLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        modifiedByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        idField.setPrefWidth(250);
        nameField.setPrefWidth(250);
//        descriptionField.setPrefWidth(650);
        creationDateField.setPrefWidth(250);
        modificationDateField.setPrefWidth(250);
        createdByField.setPrefWidth(250);
        onTerminalField.setPrefWidth(250);
        modifiedByField.setPrefWidth(250);

        idField.setEditable(false);
        creationDateField.setEditable(false);
        modificationDateField.setEditable(false);
        createdByField.setEditable(false);
        onTerminalField.setEditable(false);
        modifiedByField.setEditable(false);

        //restriction handling
        nameField.setRestrict("[a-zA-Z-_/. ]");
        nameField.setMaxLength(45);
        descriptionField.setRestrict("[a-zA-Z0-9 ]");
        descriptionField.setMaxLength(14);
        creationDateField.setRestrict("[0-9].");
        creationDateField.setMaxLength(11);
        modificationDateField.setRestrict("[a-zA-Z1-9-_/*]");
        modificationDateField.setMaxLength(500);

        dataEntryPartitionTitled.add(idLabel, 1, 2);
        dataEntryPartitionTitled.add(nameLabel, 3, 2);
        dataEntryPartitionTitled.add(descriptionLabel, 5, 2);

        dataEntryPartitionTitled.add(creationDateLabel, 1, 3);
        dataEntryPartitionTitled.add(modificationLabel, 3, 3);
        dataEntryPartitionTitled.add(createdByLabel, 5, 3);
        dataEntryPartitionTitled.add(modifiedByLabel, 7, 3);

        dataEntryPartitionTitled.add(idField, 2, 2);
        dataEntryPartitionTitled.add(nameField, 4, 2);
        dataEntryPartitionTitled.add(descriptionField, 6, 2, 3, 1);

        dataEntryPartitionTitled.add(creationDateField, 2, 3);
        dataEntryPartitionTitled.add(modificationDateField, 4, 3);
        dataEntryPartitionTitled.add(createdByField, 6, 3);
        dataEntryPartitionTitled.add(modifiedByField, 8, 3);

        dataEntryPartitionTitled.add(onTerminalLabel, 1, 4);
        dataEntryPartitionTitled.add(onTerminalField, 2, 4);

        //table configuration
        idColumn.setCellValueFactory(new PropertyValueFactory<>("materialIdColumn"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionColumn"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDateColumn"));
        modifyDateColumn.setCellValueFactory(new PropertyValueFactory<>("modifyDateColumn"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdByColumn"));
        onTerminalColumn.setCellValueFactory(new PropertyValueFactory<>("onTerminalColumn"));
        modifiedByColumn.setCellValueFactory(new PropertyValueFactory<>("modifiedByColumn"));

        idColumn.setReorderable(false);
        nameColumn.setReorderable(false);
        descriptionColumn.setReorderable(false);
        creationDateColumn.setReorderable(false);
        modifyDateColumn.setReorderable(false);
        createdByColumn.setReorderable(false);
        onTerminalColumn.setReorderable(false);
        modifiedByColumn.setReorderable(false);

        table.getColumns().addAll(idColumn, nameColumn, descriptionColumn, creationDateColumn, modifyDateColumn, createdByColumn, modifiedByColumn, onTerminalColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.prefHeightProperty().bind(root.heightProperty().subtract(dataEntryPartitionTitled.heightProperty().add(hbox.heightProperty())));
        table.setItems(controller.getDataList());
        tableFilter = TableFilter.forTableView(table).apply();

        hbox.getItems().addAll(insert, update, delete, new Separator()
                , report
        );
        hbox.setPadding(new Insets(10, 10, 10, 10));

        vbox.getChildren().addAll(dataEntryPartitionTitled, hbox);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        root.getChildren().addAll(headerLabel, vbox, table);
        root.setPadding(new Insets(10));

        idField.longValueProperty().bindBidirectional(model.materialIdProperty());
        nameField.textProperty().bindBidirectional(model.nameProperty());
        createdByField.textProperty().bindBidirectional(model.createdByProperty());
        onTerminalField.textProperty().bindBidirectional(model.onTerminalProperty());
        descriptionField.textProperty().bindBidirectional(model.descriptionProperty());
        modificationDateField.textProperty().bindBidirectional(model.modifyDateProperty());
        creationDateField.textProperty().bindBidirectional(model.creationDateProperty());
        modifiedByField.textProperty().bindBidirectional(model.modifiedByProperty());
    }

    private void actionHandling() {
        insert.setOnMouseClicked(controller::onInsert);
        update.setOnMouseClicked(controller::onUpdate);
        delete.setOnMouseClicked(controller::onDelete);

        report.setOnAction(e -> {
            controller.onReport(tableFilter.getFilteredList()).whenComplete((pane, throwable) -> {
                if (throwable != null) {
                    MainWindow.showErrorWindowForException(throwable.getMessage(), throwable);
                    return;
                }
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    stage.setScene(new Scene(pane));
                    stage.setTitle("Materials Report");
                    stage.initOwner(mainWindow);
                    stage.setWidth(1300);
                    stage.show();
                });
            });
        });

        table.setOnMouseClicked((a) -> {
            controller.onTableSelection(table.getSelectionModel().getSelectedItems());
        });
    }

    public void update() {
        ReadOnlyBooleanProperty update = controller.update();
        table.cursorProperty().bind(Bindings.when(update).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));
    }

    public Node getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return "Materials";
    }
}
