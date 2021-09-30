package soulintec.com.tmsclient.Graphics.Windows.TanksWindow;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.log4j.Log4j2;
import org.controlsfx.dialog.ExceptionDialog;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Graphics.Controls.*;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow.MaterialsModel;

import java.time.LocalDateTime;

@Log4j2
@Component
public class TanksView implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private TanksController controller;
    private TanksModel model;
    private MaterialsModel materialsModel;

    protected static MainWindow initialStage;

    private DataEntryPartitionTitled tanksDataEntry;
    private DataEntryPartitionTitled contextProductsDataEntry;

    private Tab tanksTab;
    private VBox tanksPane;
    private Label headerLabel;

    private VBox tanksVbox;
    private ToolBar tanksHbox;

    private BorderPane root;
    private TabPane tabContainer;

    private Stage mainWindow;
    private Stage contextProductStage;

    private TableView<TanksModel.TableObject> tanksTableView;
    private TableColumn<TanksModel.TableObject, LongProperty> tankIdColumn;
    private TableColumn<TanksModel.TableObject, StringProperty> nameColumn;
    private TableColumn<TanksModel.TableObject, StringProperty> stationColumn;
    private TableColumn<TanksModel.TableObject, DoubleProperty> capacityColumn;
    private TableColumn<TanksModel.TableObject, LongProperty> productColumn;
    private TableColumn<TanksModel.TableObject, DoubleProperty> qtyColumn;
    private TableColumn<TanksModel.TableObject, ObjectProperty<LocalDateTime>> dateOfQtySetColumn;
    private TableColumn<TanksModel.TableObject, StringProperty> userOfQtySetColumn;
    private TableColumn<TanksModel.TableObject, DoubleProperty> calcQtyColumn;
    private TableColumn<TanksModel.TableObject, StringProperty> createdByColumn;
    private TableColumn<TanksModel.TableObject, StringProperty> onTerminalColumn;
    private TableColumn<TanksModel.TableObject, ObjectProperty<LocalDateTime>> creationDateColumn;
    private TableColumn<TanksModel.TableObject, ObjectProperty<LocalDateTime>> modifyDateColumn;

    private TableView<MaterialsModel.TableObject> contextProductTableView;
    private TableColumn<MaterialsModel.TableObject, StringProperty> contextProductNameColumn;
    private TableColumn<MaterialsModel.TableObject, StringProperty> contextProductDescriptionColumn;

    private EnhancedButton insertTank;
    private EnhancedButton deleteTank;
    private EnhancedButton updateTank;
    private EnhancedButton refreshTank;
    private EnhancedButton report;

    private Label idLabel;
    private Label nameLabel;
    private Label capacityLabel;
    private Label qtyLabel;
    private Label calcQtyLabel;
    private Label stationLabel;
    private Label tankProductIDLabel;
    private Label dateOfQtySetLabel;
    private Label userOfQtySetLabel;
    private Label creationDateLabel;
    private Label modificationLabel;
    private Label onTerminalLabel;
    private Label createdByLabel;

    private Label ContextProductNameLabel;
    private Label ContextProductDescriptionLabel;

    private EnhancedLongField tankIdField;
    private EnhancedTextField nameField;
    private EnhancedDoubleField capacityField;
    private EnhancedTextField stationField;
    private EnhancedLongField materialIdField;
    private EnhancedDoubleField qtyField;
    private EnhancedDoubleField calcQtyField;
    private EnhancedTextField dateOfQtySetField;
    private EnhancedTextField userOfQtySetField;
    private EnhancedTextField creationDateField;
    private EnhancedTextField modificationDateField;
    private EnhancedTextField createdByField;
    private EnhancedTextField onTerminalField;

    private Label ContextProductNameFieldField;
    private Label ContextProductDescriptionField;

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);


    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainWindow = event.getStage();
        contextProductStage = new Stage();
        userAuthorities();
        init();
        graphicsBuilder();
        actionHandling();
    }

    private void userAuthorities() {

    }

    private void init() {
        initialStage = ApplicationContext.applicationContext.getBean(MainWindow.class);
        controller = ApplicationContext.applicationContext.getBean(TanksController.class);
        model = controller.getModel();
        materialsModel = controller.getMaterialsModel();

        tanksDataEntry = new DataEntryPartitionTitled("Tank");
        contextProductsDataEntry = new DataEntryPartitionTitled("Selected product");

        tanksTab = new Tab("Tanks Management");
        tanksPane = new VBox();
        headerLabel = new Label("Tanks");

        tanksVbox = new VBox();
        tanksHbox = new ToolBar();

        root = new BorderPane();
        tabContainer = new TabPane();

        tanksTableView = new TableView<>();
        tankIdColumn = new TableColumn<>("Id");
        nameColumn = new TableColumn<>("Name");
        capacityColumn = new TableColumn<>("Capacity");
        productColumn = new TableColumn<>("Material");
        stationColumn = new TableColumn<>("Station");
        qtyColumn = new TableColumn<>("Quantity");
        dateOfQtySetColumn = new TableColumn<>("Date od quantity set");
        userOfQtySetColumn = new TableColumn<>("User od quantity set");
        calcQtyColumn = new TableColumn<>("Calculated quantity");
        createdByColumn = new TableColumn<>("Created By");
        onTerminalColumn = new TableColumn<>("On Terminal");
        creationDateColumn = new TableColumn<>("Creation Date");
        modifyDateColumn = new TableColumn<>("Modification Date");

        contextProductTableView = new TableView<>();

        contextProductNameColumn = new TableColumn<>("Name");
        contextProductDescriptionColumn = new TableColumn<>("Description");

        insertTank = new EnhancedButton("Insert new tank");
        deleteTank = new EnhancedButton("Delete selected tank");
        updateTank = new EnhancedButton("Update selected tank");
        refreshTank = new EnhancedButton("Refresh data");
        report = new EnhancedButton("Show Report");

        idLabel = new Label("Tank ID :");
        nameLabel = new Label("Name :");
        capacityLabel = new Label("Capacity :");
        qtyLabel = new Label("Quantity :");
        calcQtyLabel = new Label("Calculated quantity :");
        stationLabel = new Label("Station :");
        tankProductIDLabel = new Label("Product ID :");
        dateOfQtySetLabel = new Label("Date of quantity set :");
        userOfQtySetLabel = new Label("User of quantity set :");
        creationDateLabel = new Label("Creation Date :");
        modificationLabel = new Label("Modification Date :");
        onTerminalLabel = new Label("On Terminal :");
        createdByLabel = new Label("Created By :");

        ContextProductNameLabel = new Label("Name :");
        ContextProductDescriptionLabel = new Label("Description :");

        tankIdField = new EnhancedLongField();
        nameField = new EnhancedTextField();
        capacityField = new EnhancedDoubleField();
        stationField = new EnhancedTextField();
        materialIdField = new EnhancedLongField();
        qtyField = new EnhancedDoubleField();
        calcQtyField = new EnhancedDoubleField();
        dateOfQtySetField = new EnhancedTextField();
        userOfQtySetField = new EnhancedTextField();
        creationDateField = new EnhancedTextField();
        modificationDateField = new EnhancedTextField();
        createdByField = new EnhancedTextField();
        onTerminalField = new EnhancedTextField();

        ContextProductNameFieldField = new Label();
        ContextProductDescriptionField = new Label();
    }

    private void graphicsBuilder() {

        tanksGraphicsBuilder();
        productsContextGraphicsBuilder();

        tabContainer.getTabs().addAll(tanksTab);

        root.setTop(headerLabel);
        root.setCenter(tanksPane);
        root.setPadding(new Insets(10));
    }

    private void tanksGraphicsBuilder() {
        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(root.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        //control buttons configuration
        insertTank.setPrefWidth(150);
        deleteTank.setPrefWidth(150);
        updateTank.setPrefWidth(150);
        refreshTank.setPrefWidth(150);
        report.setPrefWidth(150);

        //dataentery region configuration
        idLabel.setPrefWidth(150);
        nameLabel.setPrefWidth(150);
        capacityLabel.setPrefWidth(150);
        qtyLabel.setPrefWidth(150);
        calcQtyLabel.setPrefWidth(150);
        stationLabel.setPrefWidth(150);
        tankProductIDLabel.setPrefWidth(150);
        dateOfQtySetLabel.setPrefWidth(150);
        userOfQtySetLabel.setPrefWidth(150);
        stationLabel.setPrefWidth(150);
        userOfQtySetLabel.setPrefWidth(150);
        creationDateLabel.setPrefWidth(150);
        modificationLabel.setPrefWidth(150);
        onTerminalLabel.setPrefWidth(150);
        createdByLabel.setPrefWidth(150);

        idLabel.setTextAlignment(TextAlignment.RIGHT);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        capacityLabel.setTextAlignment(TextAlignment.RIGHT);
        qtyLabel.setTextAlignment(TextAlignment.RIGHT);
        calcQtyLabel.setTextAlignment(TextAlignment.RIGHT);
        stationLabel.setTextAlignment(TextAlignment.RIGHT);
        tankProductIDLabel.setTextAlignment(TextAlignment.RIGHT);
        dateOfQtySetLabel.setTextAlignment(TextAlignment.RIGHT);
        userOfQtySetLabel.setTextAlignment(TextAlignment.RIGHT);
        stationLabel.setTextAlignment(TextAlignment.RIGHT);
        userOfQtySetLabel.setTextAlignment(TextAlignment.RIGHT);
        creationDateLabel.setTextAlignment(TextAlignment.RIGHT);
        modificationLabel.setTextAlignment(TextAlignment.RIGHT);
        onTerminalLabel.setTextAlignment(TextAlignment.RIGHT);
        createdByLabel.setTextAlignment(TextAlignment.RIGHT);

        idLabel.setAlignment(Pos.BASELINE_RIGHT);
        nameLabel.setAlignment(Pos.BASELINE_RIGHT);
        capacityLabel.setAlignment(Pos.BASELINE_RIGHT);
        qtyLabel.setAlignment(Pos.BASELINE_RIGHT);
        calcQtyLabel.setAlignment(Pos.BASELINE_RIGHT);
        stationLabel.setAlignment(Pos.BASELINE_RIGHT);
        tankProductIDLabel.setAlignment(Pos.BASELINE_RIGHT);
        dateOfQtySetLabel.setAlignment(Pos.BASELINE_RIGHT);
        userOfQtySetLabel.setAlignment(Pos.BASELINE_RIGHT);
        stationLabel.setAlignment(Pos.BASELINE_RIGHT);
        userOfQtySetLabel.setAlignment(Pos.BASELINE_RIGHT);
        creationDateLabel.setAlignment(Pos.BASELINE_RIGHT);
        modificationLabel.setAlignment(Pos.BASELINE_RIGHT);
        onTerminalLabel.setAlignment(Pos.BASELINE_RIGHT);
        createdByLabel.setAlignment(Pos.BASELINE_RIGHT);

        idLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        nameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        capacityLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        qtyLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        calcQtyLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        stationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        tankProductIDLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        dateOfQtySetLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        userOfQtySetLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        stationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        userOfQtySetLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        creationDateLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        modificationLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        onTerminalLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        createdByLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        tankIdField.setPrefWidth(250);
        nameField.setPrefWidth(250);
        capacityField.setPrefWidth(250);
        stationField.setPrefWidth(250);
        materialIdField.setPrefWidth(250);
        qtyField.setPrefWidth(250);
        calcQtyField.setPrefWidth(250);
        dateOfQtySetField.setPrefWidth(250);
        userOfQtySetField.setPrefWidth(250);
        creationDateField.setPrefWidth(250);
        modificationDateField.setPrefWidth(250);
        createdByField.setPrefWidth(250);
        onTerminalField.setPrefWidth(250);

        tankIdField.setEditable(false);
        dateOfQtySetField.setEditable(false);
        userOfQtySetField.setEditable(false);
        qtyField.setEditable(false);
        calcQtyField.setEditable(false);
        creationDateField.setEditable(false);
        modificationDateField.setEditable(false);
        createdByField.setEditable(false);
        onTerminalField.setEditable(false);

        //alocating in the window 
        tanksDataEntry.add(idLabel, 1, 1);
        tanksDataEntry.add(tankIdField, 2, 1);

        tanksDataEntry.add(nameLabel, 3, 1);
        tanksDataEntry.add(nameField, 4, 1);

        tanksDataEntry.add(stationLabel, 5, 1);
        tanksDataEntry.add(stationField, 6, 1);

        tanksDataEntry.add(capacityLabel, 7, 1);
        tanksDataEntry.add(capacityField, 8, 1);

        tanksDataEntry.add(tankProductIDLabel, 1, 2);
        tanksDataEntry.add(materialIdField, 2, 2);

        tanksDataEntry.add(qtyLabel, 3, 2);
        tanksDataEntry.add(qtyField, 4, 2);

        tanksDataEntry.add(calcQtyLabel, 5, 2);
        tanksDataEntry.add(calcQtyField, 6, 2);

        tanksDataEntry.add(dateOfQtySetLabel, 1, 3);
        tanksDataEntry.add(dateOfQtySetField, 2, 3);

        tanksDataEntry.add(userOfQtySetLabel, 3, 3);
        tanksDataEntry.add(userOfQtySetField, 4, 3);

        tanksDataEntry.add(creationDateLabel, 1, 4);
        tanksDataEntry.add(creationDateField, 2, 4);

        tanksDataEntry.add(modificationLabel, 3, 4);
        tanksDataEntry.add(modificationDateField, 4, 4);

        tanksDataEntry.add(createdByLabel, 5, 4);
        tanksDataEntry.add(createdByField, 6, 4);

        tanksDataEntry.add(onTerminalLabel, 7, 4);
        tanksDataEntry.add(onTerminalField, 8, 4);

        //table configuration
        tankIdColumn.setCellValueFactory(new PropertyValueFactory<>("tankIdColumn"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacityColumn"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("materialIDColumn"));
        stationColumn.setCellValueFactory(new PropertyValueFactory<>("stationColumn"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qtyColumn"));
        dateOfQtySetColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfQtySetColumn"));
        userOfQtySetColumn.setCellValueFactory(new PropertyValueFactory<>("userOfQtySetColumn"));
        calcQtyColumn.setCellValueFactory(new PropertyValueFactory<>("calculatedQtyColumn"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDateColumn"));
        modifyDateColumn.setCellValueFactory(new PropertyValueFactory<>("modifyDateColumn"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdByColumn"));
        onTerminalColumn.setCellValueFactory(new PropertyValueFactory<>("onTerminalColumn"));

        tanksTableView.getColumns().addAll(tankIdColumn, nameColumn,stationColumn,productColumn, capacityColumn,qtyColumn,calcQtyColumn,dateOfQtySetColumn,
                userOfQtySetColumn,creationDateColumn,modifyDateColumn,createdByColumn,onTerminalColumn);
        tanksTableView.prefHeightProperty().bind(root.heightProperty().subtract(tanksVbox.heightProperty()));
        tanksTableView.setItems(controller.getDataList());
//        TanksTableView.setRowFactory((TableView<TanksModel.TableObject> param) -> new EnhancedTableRow());

        tankIdColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(1));
        nameColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(3));
        capacityColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(1));
        productColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(1));
        stationColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(1));
        qtyColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(2));
        calcQtyColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(2));
        dateOfQtySetColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(3));
        userOfQtySetColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(3));
        creationDateColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(3));
        modifyDateColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(3));
        createdByColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(3));
        onTerminalColumn.prefWidthProperty().bind(tanksTableView.widthProperty().divide(29).multiply(3));

        //stage configuration
        tanksHbox.getItems().addAll(insertTank, updateTank, deleteTank/*, new Separator(), refreshTank*/);
        tanksHbox.setPadding(new Insets(10, 10, 10, 10));

        tanksVbox.getChildren().addAll(contextProductsDataEntry, tanksDataEntry, tanksHbox);
        tanksVbox.setSpacing(5);
        tanksVbox.setAlignment(Pos.CENTER);

        tanksPane.getChildren().addAll(tanksVbox, tanksTableView);
        tanksPane.setPadding(new Insets(0));

        tanksTab.setContent(tanksPane);
        tanksTab.setClosable(false);

        tankIdField.longValueProperty().bindBidirectional(model.tankIdProperty());
        nameField.textProperty().bindBidirectional(model.nameProperty());
        capacityField.doubleValueProperty().bindBidirectional(model.capacityProperty());
        stationField.textProperty().bindBidirectional(model.stationProperty());
        materialIdField.longValueProperty().bindBidirectional(model.materialIDProperty());
        qtyField.doubleValueProperty().bindBidirectional(model.qtyProperty());
        calcQtyField.doubleValueProperty().bindBidirectional(model.calculatedQtyProperty());
        dateOfQtySetField.textProperty().bindBidirectional(model.dateOfQtySetProperty());
        userOfQtySetField.textProperty().bindBidirectional(model.userOfQtySetProperty());
        creationDateField.textProperty().bindBidirectional(model.creationDateProperty());
        modificationDateField.textProperty().bindBidirectional(model.modifyDateProperty());
        createdByField.textProperty().bindBidirectional(model.createdByProperty());
        onTerminalField.textProperty().bindBidirectional(model.onTerminalProperty());

    }

    private void productsContextGraphicsBuilder() {
        //dataentery region configuration
        ContextProductNameLabel.setPrefWidth(150);
        ContextProductDescriptionLabel.setPrefWidth(150);

        ContextProductNameLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextProductDescriptionLabel.setTextAlignment(TextAlignment.RIGHT);

        ContextProductNameLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextProductDescriptionLabel.setAlignment(Pos.BASELINE_RIGHT);

        ContextProductNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextProductDescriptionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        ContextProductNameFieldField.setPrefWidth(250);
        ContextProductDescriptionField.setPrefWidth(250);

        contextProductsDataEntry.add(ContextProductNameLabel, 1, 2);
        contextProductsDataEntry.add(ContextProductDescriptionLabel, 3, 2);

//        dataentery.add(IDLabel, 1, 1);
        contextProductsDataEntry.add(ContextProductNameFieldField, 2, 2);
        contextProductsDataEntry.add(ContextProductDescriptionField, 4, 2);

        contextProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        contextProductDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionColumn"));

        contextProductTableView.getColumns().addAll(contextProductNameColumn, contextProductDescriptionColumn);
        contextProductTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //stage configuration

        Pane pane = new Pane(contextProductTableView);
        pane.setBorder(new Border(new BorderStroke(Color.valueOf("#0099cc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        pane.setPadding(new Insets(2, 2, 2, 2));
        contextProductStage.setScene(new Scene(pane));
        contextProductStage.initStyle(StageStyle.UNDECORATED);
        contextProductStage.initOwner(mainWindow);

        contextProductTableView.prefWidthProperty().bind(contextProductStage.widthProperty());
        contextProductTableView.prefHeightProperty().bind(contextProductStage.heightProperty());

        materialIdField.longValueProperty().bindBidirectional(materialsModel.materialIdProperty());
        ContextProductNameFieldField.textProperty().bindBidirectional(materialsModel.nameProperty());
        ContextProductDescriptionField.textProperty().bindBidirectional(materialsModel.descriptionProperty());
    }

    private void actionHandling() {
        tanksActionHandling();
        mainWindow.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (contextProductStage.isShowing()) {
                contextProductStage.close();
            }
        });
    }

    private void tanksActionHandling() {
        insertTank.setOnMouseClicked(controller::onInsert);
        deleteTank.setOnMouseClicked(controller::onDelete);
        updateTank.setOnMouseClicked(controller::onUpdate);
        refreshTank.setOnMouseClicked(action -> update());

        tanksTableView.setOnMouseClicked((a) -> {
            controller.onTableSelection(tanksTableView.getSelectionModel().getSelectedItems());
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



    }

    private void onRunProductContextWidow(MouseEvent action) {
        Platform.runLater(() -> {
            contextProductTableView.getItems().clear();
            contextProductTableView.setItems(controller.getProductContextList());
            contextProductStage.setWidth(600);
            contextProductStage.setHeight(500);
            contextProductStage.setResizable(false);
            contextProductStage.setX(action.getScreenX());
            contextProductStage.setY(action.getScreenY());
            contextProductStage.show();
        });
    }

    public synchronized void update() {
        ReadOnlyBooleanProperty update = controller.update();
        tanksTableView.cursorProperty().bind(Bindings.when(update).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));
        refreshTank.disableProperty().bind(update);
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
