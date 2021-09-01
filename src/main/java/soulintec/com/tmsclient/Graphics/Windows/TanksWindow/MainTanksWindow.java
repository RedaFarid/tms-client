package soulintec.com.tmsclient.Graphics.Windows.TanksWindow;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.Permissions;
import soulintec.com.tmsclient.Entities.ProductDTO;
import soulintec.com.tmsclient.Entities.TankDTO;
import soulintec.com.tmsclient.Entities.TankStatus;
import soulintec.com.tmsclient.Graphics.Controls.DataEntryPartitionTitled;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedButton;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedTextField;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Graphics.Windows.TanksWindow.Utils.EnhancedTableRow;
import soulintec.com.tmsclient.Services.ProductService;
import soulintec.com.tmsclient.Services.TanksService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Component
public class MainTanksWindow implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private DataEntryPartitionTitled TanksDataEntry;
    private DataEntryPartitionTitled StrappingTableDataEntry;
    private DataEntryPartitionTitled ContextTanksDataEntry;
    private DataEntryPartitionTitled ContextProductsDataEntery;

    private Tab TanksTab;
    private Tab StrappingTableTab;
    private VBox TanksPane;
    private VBox StrappingTablePane;
    private Label headerLabel;

    private VBox TanksVbox;
    private ToolBar TanksHbox;
    private VBox StrappingTableVbox;
    private ToolBar StrappingTableHbox;

    private BorderPane root;
    private TabPane tabContainer;

    private Stage mainWindow = null;
    private Stage contextTanksٍtage;
    private Stage contextProductStage;

    private FileChooser fileChooser = new FileChooser();

    private String SelectedTankForStrapping = "";

    private ObservableList<TankTableObject> tankslist;
    private TableView<TankTableObject> TanksTableView;
    private TableColumn<TankTableObject, LongProperty> TankIdColumn;
    private TableColumn<TankTableObject, StringProperty> TankNameColumn;
    private TableColumn<TankTableObject, DoubleProperty> TankCapacityColumn;
    private TableColumn<TankTableObject, DoubleProperty> TankLevelColumn;
    private TableColumn<TankTableObject, DoubleProperty> TankVolumnColumn;
    private TableColumn<TankTableObject, DoubleProperty> TankTotaMassColumn;
    private TableColumn<TankTableObject, DoubleProperty> TankMassColumn;
    private TableColumn<TankTableObject, DoubleProperty> TankReservedMassColumn;
    private TableColumn<TankTableObject, DoubleProperty> TankLowLevelColumn;
    private TableColumn<TankTableObject, DoubleProperty> TankHighLevelColumn;
    private TableColumn<TankTableObject, DoubleProperty> TankHightColumn;
    private TableColumn<TankTableObject, DoubleProperty> TankRediusColumn;
    private TableColumn<TankTableObject, ObjectProperty<TankStatus>> TankPermissionColumn;
    private TableColumn<TankTableObject, ObjectProperty<Permissions>> TankStatusColumn;
    private TableColumn<TankTableObject, LongProperty> TankProductColumn;
    private TableColumn<TankTableObject, String> correctionFactorColumn;
    private TableColumn<TankTableObject, String> correctionTempColumn;
    private TableColumn<TankTableObject, String> currentTempColumn;
    private TableColumn<TankTableObject, String> correctedDensityColumn;
    private TableColumn<TankTableObject, String> calculatedDensityColumn;

    private ObservableList<TankDTO> ContextTanksList = FXCollections.observableArrayList();
    private TableView<TankDTO> ContextTanksTableView = new TableView<>();
    private TableColumn<TankDTO, String> ContextTankNameColumn = new TableColumn<>("Name");
    private TableColumn<TankDTO, String> ContextTankCapacityColumn = new TableColumn<>("Capacity");
    private TableColumn<TankDTO, String> ContextTankLevelColumn = new TableColumn<>("Level");
    private TableColumn<TankDTO, String> ContextTankVolumnColumn = new TableColumn<>("Volume");
    private TableColumn<TankDTO, String> ContextTankDensityColumn = new TableColumn<>("Denisty");
    private TableColumn<TankDTO, String> ContextTankCalculatedDensityColumn = new TableColumn<>("CalculatedDensity");
    private TableColumn<TankDTO, String> ContextTankTotalMassColumn = new TableColumn<>("Total mass");
    private TableColumn<TankDTO, String> ContextTankMassColumn = new TableColumn<>("Mass");
    private TableColumn<TankDTO, String> ContextTankReservedMassColumn = new TableColumn<>("Reserved Mass");
    private TableColumn<TankDTO, String> ContextTankLowLevelColumn = new TableColumn<>("Low Level");
    private TableColumn<TankDTO, String> ContextTankHighLevelColumn = new TableColumn<>("High Level");
    private TableColumn<TankDTO, String> ContextTankHightColumn = new TableColumn<>("Height");
    private TableColumn<TankDTO, String> ContextTankRediusColumn = new TableColumn<>("Redius");
    private TableColumn<TankDTO, String> ContextTankIsAutoDenistyCalculateColumn = new TableColumn<>("Auto Density calculate");
    private TableColumn<TankDTO, String> ContextTankPermissionColumn = new TableColumn<>("Permission");
    private TableColumn<TankDTO, String> ContextTankStatusColumn = new TableColumn<>("Status");
    private TableColumn<TankDTO, String> ContextTankProductColumn = new TableColumn<>("Product ID");



    private ObservableList<ProductDTO> ContextProductList = FXCollections.observableArrayList();
    private TableView<ProductDTO> ContextProductTableView = new TableView<>();
    private TableColumn<ProductDTO, String> ContextProductNameColumn = new TableColumn<>("Name");
    private TableColumn<ProductDTO, String> ContextProductStockIDColumn = new TableColumn<>("Stock ID");
    private TableColumn<ProductDTO, String> ContextProductSpecificGravityColumn = new TableColumn<>("Specific Gravity");
    private TableColumn<ProductDTO, String> ContextProductDescriptionColumn = new TableColumn<>("Description");

    private EnhancedButton InsertTank = new EnhancedButton("Insert new tank");
    private EnhancedButton DeleteTank = new EnhancedButton("Delete selected tank");
    private EnhancedButton UpdateTank = new EnhancedButton("Update selected tank");
    private EnhancedButton refreshTank = new EnhancedButton("Refresh data");
    private EnhancedButton report = new EnhancedButton("Show Report");

    private EnhancedButton DeleteStrappingData = new EnhancedButton("Delete Strapping Data of the selected Tank");
    private EnhancedButton ImportStrappingTable = new EnhancedButton("Import Strapping Table from .CSV file");
    private EnhancedButton exportStrappingTables = new EnhancedButton("Export data to csv");
    private EnhancedButton strappingReport = new EnhancedButton("Show Report");


    private Label TankNameLabel = new Label("Name :");
    private Label TankCapacityLabel = new Label("Capacity :");
    private Label TankLevelLabel = new Label("Level :");
    private Label TankVolumeLabel = new Label("Volume :");
    private Label TankTotalMasstLabel = new Label("Total mass :");
    private Label TankMasstLabel = new Label("Actual mass :");
    private Label TankReservedMasstLabel = new Label("Reserved mass :");
    private Label TankLowLevelLabel = new Label("Low Level :");
    private Label TankHighLevelLabel = new Label("HighLevel :");
    private Label TankHightLevelLabel = new Label("Height :");
    private Label TankRediusLevelLabel = new Label("Radius :");
    private Label TankPermissionLabel = new Label("Permission :");
    private Label TankStatusLabel = new Label("Status :");
    private Label TankProductIDLabel = new Label("Product ID :");
    private Label correctedDensityLabel = new Label("Corrected density :");
    private Label calculatedDensityLabel = new Label("Calculated Density :");
    private Label correctionFactorLabel = new Label("Correction factor :");
    private Label correctionTempLabel = new Label("Correction temperature :");
    private Label currentTemperatureLabel = new Label("Current temperature :");

    private Label ContextTankNameLabel = new Label("Name :");
    private Label ContextTankCapacityLabel = new Label("Capacity :");
    private Label ContextTankLevelLabel = new Label("Level :");
    private Label ContextTankVolumeLabel = new Label("Volume :");
    private Label ContextTankDenistyLabel = new Label("Density :");
    private Label ContextTankCalculatedDenistyLabel = new Label("Calculated Density :");
    private Label ContextTankTotalMasstLabel = new Label("Weight :");
    private Label ContextTankMasstLabel = new Label("Actual mass :");
    private Label ContextTankReservedMasstLabel = new Label("Reserved mass :");
    private Label ContextTankLowLevelLabel = new Label("Low Level :");
    private Label ContextTankHighLevelLabel = new Label("HighLevel :");
    private Label ContextTankHightLevelLabel = new Label("Height :");
    private Label ContextTankRediusLevelLabel = new Label("Radius :");
    private Label ContextTankAutoDenistyCalculationsLabel = new Label("Auto Density calculations :");
    private Label ContextTankPermissionLabel = new Label("Permission :");
    private Label ContextTankStatusLabel = new Label("Status :");
    private Label ContextTankProductIDLabel = new Label("Product ID :");
    private Label ContextProductnameLabel = new Label("Name :");
    private Label ContextProductStockIDLabel = new Label("Stock ID :");
    private Label ContextProductSpecificGravityLabel = new Label("Specific Gravity :");
    private Label ContextProductDescriptionLabel = new Label("Description :");

    private Label StrappingTableTankIDLabel = new Label("Tank ID :");


    private EnhancedTextField TankNameField = new EnhancedTextField();
    private EnhancedTextField TankCapacityField = new EnhancedTextField();
    private EnhancedTextField TankLevelField = new EnhancedTextField();
    private EnhancedTextField TankVolumeField = new EnhancedTextField();
    private EnhancedTextField TankTotalMassField = new EnhancedTextField();
    private EnhancedTextField TankMassField = new EnhancedTextField();
    private EnhancedTextField TankReservedMassField = new EnhancedTextField();
    private EnhancedTextField TankLowLevelField = new EnhancedTextField();
    private EnhancedTextField TankHighLevelField = new EnhancedTextField();
    private EnhancedTextField TankHeightField = new EnhancedTextField();
    private EnhancedTextField TankRadiusLevelField = new EnhancedTextField();
    private ComboBox<String> TankPermissionField = new ComboBox();
    private EnhancedTextField TankStatusField = new EnhancedTextField();
    private EnhancedTextField TankProductNameField = new EnhancedTextField();
    private EnhancedTextField correctedDensityField = new EnhancedTextField();
    private EnhancedTextField calculatedDensityField = new EnhancedTextField();
    private EnhancedTextField correctionTempField = new EnhancedTextField();
    private EnhancedTextField correctionFactorField = new EnhancedTextField();
    private EnhancedTextField currentTempField = new EnhancedTextField();

    private EnhancedTextField StrappingTableTankIDField = new EnhancedTextField();

    private Label ContextTankNameField = new Label();
    private Label ContextTankCapacityField = new Label();
    private Label ContextTankLevelField = new Label();
    private Label ContextTankVolumeField = new Label();
    private Label ContextTankDenistyField = new Label();
    private Label ContextTankCalculatedDenistyField = new Label();
    private Label ContextTankTotalMassField = new Label();
    private Label ContextTankMassField = new Label();
    private Label ContextTankReservedMassField = new Label();
    private Label ContextTankLowLevelField = new Label();
    private Label ContextTankHighLevelField = new Label();
    private Label ContextTankHeightField = new Label();
    private Label ContextTankRadiusField = new Label();
    private Label ContextTankAutoDensityCalculationsField = new Label();
    private Label ContextTankPermissionField = new Label();
    private Label ContextTankStatusField = new Label();
    private Label ContextTankProductIDField = new Label();

    private Label ContextProductnameFieldField = new Label();
    private Label ContextProductStockIDField = new Label();
    private Label ContextProductSpecficGravityField = new Label();
    private Label ContextProductDescriptionField = new Label();

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);

    @Autowired
    private TanksService tankService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TanksController controller;

    @Autowired(required = false)
    private MainWindow mainView;

    @Autowired(required = false)
    private Executor executor;

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainWindow = event.getStage();
        contextProductStage = new Stage();
        contextTanksٍtage = new Stage();
        userAuthorities();
        init();
        graphicsBuilder();
        actionHandling();
    }
    private void userAuthorities() {

    }

    private void init() {
        controller.setMainTanksWindow(this);

        TanksDataEntry = new DataEntryPartitionTitled("Tank");
        StrappingTableDataEntry = new DataEntryPartitionTitled("Strapping point");
        ContextTanksDataEntry = new DataEntryPartitionTitled("Selected tank");
        ContextProductsDataEntery = new DataEntryPartitionTitled("Selected product");

        TanksTab = new Tab("Tanks Management");
        StrappingTableTab = new Tab("Strapping Table Management");
        TanksPane = new VBox();
        StrappingTablePane = new VBox();
        headerLabel = new Label("Tanks");

        TanksVbox = new VBox();
        TanksHbox = new ToolBar();
        StrappingTableVbox = new VBox();
        StrappingTableHbox = new ToolBar();

        root = new BorderPane();
        tabContainer = new TabPane();

        tankslist = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
        TanksTableView = new TableView<>();
        TankIdColumn = new TableColumn<>("Id");
        TankNameColumn = new TableColumn<>("Name");
        TankCapacityColumn = new TableColumn<>("Capacity");
        TankLevelColumn = new TableColumn<>("Level(m)");
        TankVolumnColumn = new TableColumn<>("Volume");
        TankTotaMassColumn = new TableColumn<>("Total mass");
        TankMassColumn = new TableColumn<>("Mass");
        TankReservedMassColumn = new TableColumn<>("Reserved Mass");
        TankLowLevelColumn = new TableColumn<>("Low Level");
        TankHighLevelColumn = new TableColumn<>("High Level");
        TankHightColumn = new TableColumn<>("Height");
        TankRediusColumn = new TableColumn<>("Radius");
        TankPermissionColumn = new TableColumn<>("Permission");
        TankStatusColumn = new TableColumn<>("Status");
        TankProductColumn = new TableColumn<>("Product ID");

        correctionFactorColumn = new TableColumn<>("Correction factor");
        correctionTempColumn = new TableColumn<>("Correction temp.");
        currentTempColumn = new TableColumn<>("Current temp.");
        correctedDensityColumn = new TableColumn<>("Corrected Density");
        calculatedDensityColumn = new TableColumn<>("Calc. Density");
    }

    private void graphicsBuilder() {

        tanksGraphicsBuilder();
        StrappingTableGraphicsBuilder();
        tanksContextGraphicsBuilder();
        productsContextGraphicsBuilder();

        tabContainer.getTabs().addAll(TanksTab, StrappingTableTab);

        root.setTop(headerLabel);
        root.setCenter(tabContainer);
        root.setPadding(new Insets(10));
    }
    private void tanksGraphicsBuilder() {
        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(root.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        //control buttons configuration
        InsertTank.setPrefWidth(150);
        DeleteTank.setPrefWidth(150);
        UpdateTank.setPrefWidth(150);
        refreshTank.setPrefWidth(150);
        report.setPrefWidth(150);

        //dataentery region configuration
        correctionFactorLabel.setPrefWidth(150);
        TankNameLabel.setPrefWidth(150);
        TankCapacityLabel.setPrefWidth(150);
        TankLevelLabel.setPrefWidth(150);
        TankVolumeLabel.setPrefWidth(150);
        correctedDensityLabel.setPrefWidth(150);
        calculatedDensityLabel.setPrefWidth(150);
        TankTotalMasstLabel.setPrefWidth(150);
        TankMasstLabel.setPrefWidth(150);
        TankReservedMasstLabel.setPrefWidth(150);
        TankLowLevelLabel.setPrefWidth(150);
        TankHighLevelLabel.setPrefWidth(150);
        TankHightLevelLabel.setPrefWidth(150);
        TankRediusLevelLabel.setPrefWidth(150);
        correctionTempLabel.setPrefWidth(150);
        TankPermissionLabel.setPrefWidth(150);
        TankStatusLabel.setPrefWidth(150);
        TankProductIDLabel.setPrefWidth(150);
        currentTemperatureLabel.setPrefWidth(150);

        correctionFactorLabel.setTextAlignment(TextAlignment.RIGHT);
        TankNameLabel.setTextAlignment(TextAlignment.RIGHT);
        TankCapacityLabel.setTextAlignment(TextAlignment.RIGHT);
        TankLevelLabel.setTextAlignment(TextAlignment.RIGHT);
        TankVolumeLabel.setTextAlignment(TextAlignment.RIGHT);
        correctedDensityLabel.setTextAlignment(TextAlignment.RIGHT);
        calculatedDensityLabel.setTextAlignment(TextAlignment.RIGHT);
        TankTotalMasstLabel.setTextAlignment(TextAlignment.RIGHT);
        TankMasstLabel.setTextAlignment(TextAlignment.RIGHT);
        TankReservedMasstLabel.setTextAlignment(TextAlignment.RIGHT);
        TankLowLevelLabel.setTextAlignment(TextAlignment.RIGHT);
        TankHighLevelLabel.setTextAlignment(TextAlignment.RIGHT);
        TankHightLevelLabel.setTextAlignment(TextAlignment.RIGHT);
        TankRediusLevelLabel.setTextAlignment(TextAlignment.RIGHT);
        correctionTempLabel.setTextAlignment(TextAlignment.RIGHT);
        TankPermissionLabel.setTextAlignment(TextAlignment.RIGHT);
        TankStatusLabel.setTextAlignment(TextAlignment.RIGHT);
        TankProductIDLabel.setTextAlignment(TextAlignment.RIGHT);
        currentTemperatureLabel.setTextAlignment(TextAlignment.RIGHT);

        correctionFactorLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankNameLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankCapacityLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankLevelLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankVolumeLabel.setAlignment(Pos.BASELINE_RIGHT);
        correctedDensityLabel.setAlignment(Pos.BASELINE_RIGHT);
        calculatedDensityLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankTotalMasstLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankMasstLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankReservedMasstLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankLowLevelLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankHighLevelLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankHightLevelLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankRediusLevelLabel.setAlignment(Pos.BASELINE_RIGHT);
        correctionTempLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankPermissionLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankStatusLabel.setAlignment(Pos.BASELINE_RIGHT);
        TankProductIDLabel.setAlignment(Pos.BASELINE_RIGHT);
        currentTemperatureLabel.setAlignment(Pos.BASELINE_RIGHT);

        correctionFactorLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankCapacityLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankLevelLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankVolumeLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        correctedDensityLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        calculatedDensityLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankTotalMasstLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankMasstLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankReservedMasstLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankLowLevelLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankHighLevelLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankHightLevelLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankRediusLevelLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        correctionTempLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankPermissionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankStatusLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        TankProductIDLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        currentTemperatureLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");


        correctionFactorField.setPrefWidth(250);
        TankNameField.setPrefWidth(250);
        TankCapacityField.setPrefWidth(250);
        TankLevelField.setPrefWidth(250);
        TankVolumeField.setPrefWidth(250);
        correctedDensityField.setPrefWidth(250);
        calculatedDensityField.setPrefWidth(250);
        TankTotalMassField.setPrefWidth(250);
        TankMassField.setPrefWidth(250);
        TankReservedMassField.setPrefWidth(250);
        TankLowLevelField.setPrefWidth(250);
        TankHighLevelField.setPrefWidth(250);
        TankHeightField.setPrefWidth(250);
        TankRadiusLevelField.setPrefWidth(250);
        correctionTempField.setPrefWidth(250);
        TankPermissionField.setPrefWidth(250);
        TankStatusField.setPrefWidth(250);
        TankProductNameField.setPrefWidth(250);

        //restriction handling
        TankNameField.setRestrict("[a-zA-Z0-9-]");
        TankNameField.setMaxLength(14);
        TankVolumeField.setRestrict("[0-9.]");
        TankVolumeField.setMaxLength(14);
        TankCapacityField.setRestrict("[0-9.]");
        TankCapacityField.setMaxLength(14);
        TankLevelField.setRestrict("[0-9.]");
        TankLevelField.setMaxLength(14);
        correctedDensityField.setRestrict("[0-9.]");
        correctedDensityField.setMaxLength(14);
        TankTotalMassField.setRestrict("[0-9.]");
        TankTotalMassField.setMaxLength(14);
        TankLowLevelField.setRestrict("[0-9.]");
        TankLowLevelField.setMaxLength(14);
        TankHighLevelField.setRestrict("[0-9.]");
        TankHighLevelField.setMaxLength(14);


        TankLevelField.setEditable(false);
        TankVolumeField.setEditable(false);
        calculatedDensityField.setEditable(false);
        TankTotalMassField.setEditable(false);
        TankMassField.setEditable(false);
        TankReservedMassField.setEditable(false);
        TankStatusField.setEditable(false);

        //alocating in the window 
        TanksDataEntry.add(TankNameLabel, 1, 2);
        TanksDataEntry.add(TankNameField, 2, 2);

        TanksDataEntry.add(TankLevelLabel, 3, 1);
        TanksDataEntry.add(TankLevelField, 4, 1);

        TanksDataEntry.add(TankVolumeLabel, 3, 2);
        TanksDataEntry.add(TankVolumeField, 4, 2);



        TanksDataEntry.add(TankCapacityLabel, 3, 3);
        TanksDataEntry.add(TankCapacityField, 4, 3);

        TanksDataEntry.add(TankTotalMasstLabel, 3, 4);
        TanksDataEntry.add(TankTotalMassField, 4, 4);

        TanksDataEntry.add(TankLowLevelLabel, 1, 3);
        TanksDataEntry.add(TankLowLevelField, 2, 3);

        TanksDataEntry.add(TankHighLevelLabel, 1, 4);
        TanksDataEntry.add(TankHighLevelField, 2, 4);

        TanksDataEntry.add(TankPermissionLabel, 1, 7);
        TanksDataEntry.add(TankPermissionField, 2, 7);

        TanksDataEntry.add(TankStatusLabel, 3, 7);
        TanksDataEntry.add(TankStatusField, 4, 7);

        TanksDataEntry.add(TankProductIDLabel, 1, 1);
        TanksDataEntry.add(TankProductNameField, 2, 1);
        //
        TanksDataEntry.add(TankMasstLabel, 3, 5);
        TanksDataEntry.add(TankMassField, 4, 5);

        TanksDataEntry.add(TankReservedMasstLabel, 3, 6);
        TanksDataEntry.add(TankReservedMassField, 4, 6);

        TanksDataEntry.add(TankHightLevelLabel, 1, 5);
        TanksDataEntry.add(TankHeightField, 2, 5);

        TanksDataEntry.add(TankRediusLevelLabel, 1, 6);
        TanksDataEntry.add(TankRadiusLevelField, 2, 6);



        TanksDataEntry.add(correctionFactorLabel, 5, 1);
        TanksDataEntry.add(correctionFactorField, 6, 1);

        TanksDataEntry.add(currentTemperatureLabel, 5, 2);
        TanksDataEntry.add(currentTempField, 6, 2);

        TanksDataEntry.add(correctionTempLabel, 5, 3);
        TanksDataEntry.add(correctionTempField, 6, 3);

        TanksDataEntry.add(correctedDensityLabel, 5, 4);
        TanksDataEntry.add(correctedDensityField, 6, 4);

        TanksDataEntry.add(calculatedDensityLabel, 5, 5);
        TanksDataEntry.add(calculatedDensityField, 6, 5);


        //table configuration
        TankIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TankNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TankCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        TankLevelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        TankVolumnColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
        TankTotaMassColumn.setCellValueFactory(new PropertyValueFactory<>("totalmass"));
        TankMassColumn.setCellValueFactory(new PropertyValueFactory<>("mass"));
        TankReservedMassColumn.setCellValueFactory(new PropertyValueFactory<>("reservedMassForLot"));
        TankLowLevelColumn.setCellValueFactory(new PropertyValueFactory<>("lowLevel"));
        TankHighLevelColumn.setCellValueFactory(new PropertyValueFactory<>("hightLevel"));
        TankHightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        TankRediusColumn.setCellValueFactory(new PropertyValueFactory<>("redius"));
        TankPermissionColumn.setCellValueFactory(new PropertyValueFactory<>("permission"));
        TankStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        TankProductColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        correctionFactorColumn.setCellValueFactory(new PropertyValueFactory<>("correctionFactor"));
        correctionTempColumn.setCellValueFactory(new PropertyValueFactory<>("correctionTemp"));
        currentTempColumn.setCellValueFactory(new PropertyValueFactory<>("currentTemperature"));
        correctedDensityColumn.setCellValueFactory(new PropertyValueFactory<>("correctedDensity"));
        calculatedDensityColumn.setCellValueFactory(new PropertyValueFactory<>("calcuatedDensity"));


        TanksTableView.getColumns().addAll(TankIdColumn, TankNameColumn, TankCapacityColumn, TankLevelColumn, TankVolumnColumn, TankMassColumn, TankReservedMassColumn, TankTotaMassColumn,
                TankProductColumn, correctionFactorColumn, correctionTempColumn, currentTempColumn, correctedDensityColumn, calculatedDensityColumn, TankLowLevelColumn, TankHighLevelColumn,
                TankHightColumn, TankRediusColumn, TankPermissionColumn, TankStatusColumn);
        TanksTableView.prefHeightProperty().bind(tabContainer.heightProperty().subtract(TanksVbox.heightProperty().add(headerLabel.heightProperty())));
        TanksTableView.setItems(tankslist);
        TanksTableView.setRowFactory((TableView<TankTableObject> param) -> new EnhancedTableRow());

        TankIdColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(1));
        TankNameColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(3));
        TankCapacityColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(3));
        TankLevelColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(6));
        TankVolumnColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(4));
        TankTotaMassColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(3));
        TankMassColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(3));
        TankReservedMassColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(3));
        TankLowLevelColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(3));
        TankHighLevelColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(3));
        TankHightColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(2));
        TankRediusColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(2));
        TankPermissionColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(4));
        TankStatusColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(2));
        TankProductColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(4));
        correctionFactorColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(4));
        correctionTempColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(4));
        currentTempColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(4));
        correctedDensityColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(4));
        calculatedDensityColumn.prefWidthProperty().bind(TanksTableView.widthProperty().divide(60).multiply(4));

        //stage configuration

        TanksHbox.getItems().addAll(InsertTank, UpdateTank, DeleteTank, report, new Separator(), refreshTank);
        TanksHbox.setPadding(new Insets(10, 10, 10, 10));

        TanksVbox.getChildren().addAll(ContextProductsDataEntery, TanksDataEntry, TanksHbox);
        TanksVbox.setSpacing(5);
        TanksVbox.setAlignment(Pos.CENTER);

        TanksPane.getChildren().addAll(TanksVbox, TanksTableView);
        TanksPane.setPadding(new Insets(10));

        TanksTab.setContent(TanksPane);
        TanksTab.setClosable(false);

        TankPermissionField.getItems().clear();
        TankPermissionField.getItems().addAll(FXCollections.observableArrayList(Stream.of(Permissions.values()).map(Enum::toString).collect(Collectors.toList())));
    }
    private void StrappingTableGraphicsBuilder() {
        //control buttons configuration
        DeleteStrappingData.setPrefWidth(250);
        ImportStrappingTable.setPrefWidth(250);
        exportStrappingTables.setPrefWidth(250);
        strappingReport.setPrefWidth(150);
        //dataentery region configuration
        StrappingTableTankIDLabel.setPrefWidth(100);
        StrappingTableTankIDLabel.setTextAlignment(TextAlignment.RIGHT);
        StrappingTableTankIDLabel.setAlignment(Pos.BASELINE_RIGHT);
        StrappingTableTankIDLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        StrappingTableTankIDField.setPrefWidth(200);

        StrappingTableDataEntry.add(StrappingTableTankIDLabel, 1, 1);
        StrappingTableDataEntry.add(StrappingTableTankIDField, 2, 1);

        //stage configuration

        StrappingTableHbox.getItems().addAll(DeleteStrappingData, ImportStrappingTable, exportStrappingTables, strappingReport);
        StrappingTableHbox.setPadding(new Insets(10, 10, 10, 10));

        StrappingTableVbox.getChildren().addAll(ContextTanksDataEntry, StrappingTableDataEntry, StrappingTableHbox);
        StrappingTableVbox.setSpacing(5);

        StrappingTableTab.setContent(StrappingTablePane);
        StrappingTableTab.setClosable(false);

        StrappingTablePane.getChildren().add(StrappingTableVbox);
        StrappingTablePane.setPadding(new Insets(10));

        StrappingTableTab.setContent(StrappingTablePane);
        StrappingTableTab.setClosable(false);
    }
    private void tanksContextGraphicsBuilder() {

        //dataentery region configuration
        ContextTankNameLabel.setPrefWidth(150);
        ContextTankCapacityLabel.setPrefWidth(150);
        ContextTankLevelLabel.setPrefWidth(150);
        ContextTankVolumeLabel.setPrefWidth(150);
        ContextTankDenistyLabel.setPrefWidth(150);
        ContextTankCalculatedDenistyLabel.setPrefWidth(150);
        ContextTankTotalMasstLabel.setPrefWidth(150);
        ContextTankMasstLabel.setPrefWidth(150);
        ContextTankReservedMasstLabel.setPrefWidth(150);
        ContextTankLowLevelLabel.setPrefWidth(150);
        ContextTankHighLevelLabel.setPrefWidth(150);
        ContextTankHightLevelLabel.setPrefWidth(150);
        ContextTankRediusLevelLabel.setPrefWidth(150);
        ContextTankAutoDenistyCalculationsLabel.setPrefWidth(150);
        ContextTankPermissionLabel.setPrefWidth(150);
        ContextTankStatusLabel.setPrefWidth(150);
        ContextTankProductIDLabel.setPrefWidth(150);

        ContextTankNameLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankCapacityLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankLevelLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankVolumeLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankDenistyLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankCalculatedDenistyLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankTotalMasstLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankMasstLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankReservedMasstLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankLowLevelLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankHighLevelLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankHightLevelLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankRediusLevelLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankAutoDenistyCalculationsLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankPermissionLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankStatusLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTankProductIDLabel.setTextAlignment(TextAlignment.RIGHT);

        ContextTankNameLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankCapacityLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankLevelLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankVolumeLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankDenistyLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankCalculatedDenistyLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankTotalMasstLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankMasstLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankReservedMasstLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankLowLevelLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankHighLevelLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankHightLevelLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankRediusLevelLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankAutoDenistyCalculationsLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankPermissionLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankStatusLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextTankProductIDLabel.setAlignment(Pos.BASELINE_RIGHT);

        ContextTankNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankCapacityLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankLevelLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankVolumeLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankDenistyLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankCalculatedDenistyLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankTotalMasstLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankMasstLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankReservedMasstLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankLowLevelLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankHighLevelLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankHightLevelLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankRediusLevelLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankAutoDenistyCalculationsLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankPermissionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankStatusLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextTankProductIDLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        ContextTankNameField.setPrefWidth(250);
        ContextTankCapacityField.setPrefWidth(250);
        ContextTankLevelField.setPrefWidth(250);
        ContextTankVolumeField.setPrefWidth(250);
        ContextTankDenistyField.setPrefWidth(250);
        ContextTankCalculatedDenistyField.setPrefWidth(250);
        ContextTankTotalMassField.setPrefWidth(250);
        ContextTankMassField.setPrefWidth(250);
        ContextTankReservedMassField.setPrefWidth(250);
        ContextTankLowLevelField.setPrefWidth(250);
        ContextTankHighLevelField.setPrefWidth(250);
        ContextTankHeightField.setPrefWidth(250);
        ContextTankRadiusField.setPrefWidth(250);
        ContextTankAutoDensityCalculationsField.setPrefWidth(250);
        ContextTankPermissionField.setPrefWidth(250);
        ContextTankStatusField.setPrefWidth(250);
        ContextTankProductIDField.setPrefWidth(250);

        ContextTanksDataEntry.add(ContextTankNameLabel, 1, 2);
        ContextTanksDataEntry.add(ContextTankNameField, 2, 2);

        ContextTanksDataEntry.add(ContextTankLevelLabel, 3, 1);
        ContextTanksDataEntry.add(ContextTankLevelField, 4, 1);

        ContextTanksDataEntry.add(ContextTankVolumeLabel, 3, 2);
        ContextTanksDataEntry.add(ContextTankVolumeField, 4, 2);

        ContextTanksDataEntry.add(ContextTankDenistyLabel, 3, 3);
        ContextTanksDataEntry.add(ContextTankDenistyField, 4, 3);

        ContextTanksDataEntry.add(ContextTankCapacityLabel, 5, 3);
        ContextTanksDataEntry.add(ContextTankCapacityField, 6, 3);

        ContextTanksDataEntry.add(ContextTankTotalMasstLabel, 3, 4);
        ContextTanksDataEntry.add(ContextTankTotalMassField, 4, 4);

        ContextTanksDataEntry.add(ContextTankLowLevelLabel, 1, 3);
        ContextTanksDataEntry.add(ContextTankLowLevelField, 2, 3);

        ContextTanksDataEntry.add(ContextTankHighLevelLabel, 1, 4);
        ContextTanksDataEntry.add(ContextTankHighLevelField, 2, 4);

        ContextTanksDataEntry.add(ContextTankPermissionLabel, 5, 1);
        ContextTanksDataEntry.add(ContextTankPermissionField, 6, 1);

        ContextTanksDataEntry.add(ContextTankStatusLabel, 5, 2);
        ContextTanksDataEntry.add(ContextTankStatusField, 6, 2);

        ContextTanksDataEntry.add(ContextTankProductIDLabel, 1, 1);
        ContextTanksDataEntry.add(ContextTankProductIDField, 2, 1);

        ContextTanksDataEntry.add(ContextTankMasstLabel, 3, 5);
        ContextTanksDataEntry.add(ContextTankMassField, 4, 5);

        ContextTanksDataEntry.add(ContextTankReservedMasstLabel, 3, 6);
        ContextTanksDataEntry.add(ContextTankReservedMassField, 4, 6);

        ContextTanksDataEntry.add(ContextTankHightLevelLabel, 1, 5);
        ContextTanksDataEntry.add(ContextTankHeightField, 2, 5);

        ContextTanksDataEntry.add(ContextTankRediusLevelLabel, 1, 6);
        ContextTanksDataEntry.add(ContextTankRadiusField, 2, 6);

        //table configuration
        ContextTankNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ContextTankCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
        ContextTankLevelColumn.setCellValueFactory(new PropertyValueFactory<>("Level"));
        ContextTankVolumnColumn.setCellValueFactory(new PropertyValueFactory<>("Volume"));
        ContextTankDensityColumn.setCellValueFactory(new PropertyValueFactory<>("Denisty"));
        ContextTankCalculatedDensityColumn.setCellValueFactory(new PropertyValueFactory<>("CalculatedDenisty"));
        ContextTankTotalMassColumn.setCellValueFactory(new PropertyValueFactory<>("totalMass"));
        ContextTankMassColumn.setCellValueFactory(new PropertyValueFactory<>("Mass"));
        ContextTankReservedMassColumn.setCellValueFactory(new PropertyValueFactory<>("ReservedMassForLot"));
        ContextTankLowLevelColumn.setCellValueFactory(new PropertyValueFactory<>("LowLevel"));
        ContextTankHighLevelColumn.setCellValueFactory(new PropertyValueFactory<>("HightLevel"));
        ContextTankHightColumn.setCellValueFactory(new PropertyValueFactory<>("Height"));
        ContextTankRediusColumn.setCellValueFactory(new PropertyValueFactory<>("redius"));
        ContextTankIsAutoDenistyCalculateColumn.setCellValueFactory(new PropertyValueFactory<>("AutoDenistyCalculations"));
        ContextTankPermissionColumn.setCellValueFactory(new PropertyValueFactory<>("Permission"));
        ContextTankStatusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        ContextTankProductColumn.setCellValueFactory(new PropertyValueFactory<>("ProductID"));

        ContextTanksTableView.getColumns().addAll(ContextTankNameColumn, ContextTankCapacityColumn, ContextTankLevelColumn, ContextTankVolumnColumn, ContextTankDensityColumn,
                ContextTankCalculatedDensityColumn, ContextTankTotalMassColumn, ContextTankMassColumn, ContextTankReservedMassColumn, ContextTankLowLevelColumn,
                ContextTankHighLevelColumn, ContextTankHightColumn, ContextTankRediusColumn, ContextTankIsAutoDenistyCalculateColumn, ContextTankPermissionColumn,
                ContextTankStatusColumn, ContextTankProductColumn);

        ContextTanksTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        //stage configuration

        Pane pane = new Pane(ContextTanksTableView);
        pane.setBorder(new Border(new BorderStroke(Color.valueOf("#0099cc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        pane.setPadding(new Insets(2, 2, 2, 2));
        contextTanksٍtage.setScene(new Scene(pane));
        contextTanksٍtage.initStyle(StageStyle.UNDECORATED);
        contextTanksٍtage.initOwner(mainWindow);

        ContextTanksTableView.prefWidthProperty().bind(contextTanksٍtage.widthProperty());
        ContextTanksTableView.prefHeightProperty().bind(contextTanksٍtage.heightProperty());
    }
    private void productsContextGraphicsBuilder() {
        //dataentery region configuration
        ContextProductnameLabel.setPrefWidth(150);
        ContextProductStockIDLabel.setPrefWidth(150);
        ContextProductSpecificGravityLabel.setPrefWidth(150);
        ContextProductDescriptionLabel.setPrefWidth(150);

        ContextProductnameLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextProductStockIDLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextProductSpecificGravityLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextProductDescriptionLabel.setTextAlignment(TextAlignment.RIGHT);

        ContextProductnameLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextProductStockIDLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextProductSpecificGravityLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContextProductDescriptionLabel.setAlignment(Pos.BASELINE_RIGHT);

        ContextProductnameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextProductStockIDLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextProductSpecificGravityLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContextProductDescriptionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        ContextProductnameFieldField.setPrefWidth(250);
        ContextProductStockIDField.setPrefWidth(250);
        ContextProductSpecficGravityField.setPrefWidth(250);
        ContextProductDescriptionField.setPrefWidth(250);

        ContextProductsDataEntery.add(ContextProductnameLabel, 1, 2);
        ContextProductsDataEntery.add(ContextProductStockIDLabel, 3, 2);
        ContextProductsDataEntery.add(ContextProductSpecificGravityLabel, 1, 3);
        ContextProductsDataEntery.add(ContextProductDescriptionLabel, 3, 3);

//        dataentery.add(IDLabel, 1, 1);
        ContextProductsDataEntery.add(ContextProductnameFieldField, 2, 2);
        ContextProductsDataEntery.add(ContextProductStockIDField, 4, 2);
        ContextProductsDataEntery.add(ContextProductSpecficGravityField, 2, 3);
        ContextProductsDataEntery.add(ContextProductDescriptionField, 4, 3);

        //table configuration
        ContextProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ContextProductStockIDColumn.setCellValueFactory(new PropertyValueFactory<>("StockID"));
        ContextProductSpecificGravityColumn.setCellValueFactory(new PropertyValueFactory<>("SpecificGravity"));
        ContextProductDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));

        ContextProductTableView.getColumns().addAll(ContextProductNameColumn, ContextProductStockIDColumn, ContextProductSpecificGravityColumn, ContextProductDescriptionColumn);
        ContextProductTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //stage configuration

        Pane pane = new Pane(ContextProductTableView);
        pane.setBorder(new Border(new BorderStroke(Color.valueOf("#0099cc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        pane.setPadding(new Insets(2, 2, 2, 2));
        contextProductStage.setScene(new Scene(pane));
        contextProductStage.initStyle(StageStyle.UNDECORATED);
        contextProductStage.initOwner(mainWindow);

        ContextProductTableView.prefWidthProperty().bind(contextProductStage.widthProperty());
        ContextProductTableView.prefHeightProperty().bind(contextProductStage.heightProperty());
    }

    private void actionHandling() {
        tanksActionHandling();
        mainWindow.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (contextTanksٍtage.isShowing()) {
                contextTanksٍtage.close();
            }
            if (contextProductStage.isShowing()) {
                contextProductStage.close();
            }
        });
    }
    private void tanksActionHandling() {
        InsertTank.setOnMouseClicked(this::onCreateTank);
        DeleteTank.setOnMouseClicked(this::onDeleteTank);
        UpdateTank.setOnMouseClicked(this::onUpdateTank);
        refreshTank.setOnMouseClicked(action -> update());

        TanksTableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<TankTableObject>) action -> {
            try {
                if (TanksTableView.getSelectionModel().getSelectedItems().size() > 0) {
                    TankTableObject selected = TanksTableView.getSelectionModel().getSelectedItem();

                    correctionFactorField.setText(String.valueOf(selected.getId()));
                    TankNameField.setText(selected.getName());
                    TankCapacityField.setText(String.valueOf(selected.getCapacity()));
                    TankLevelField.setText(String.valueOf(selected.getLevel()));
                    TankVolumeField.setText(String.valueOf(selected.getVolume()));
                    TankTotalMassField.setText(String.valueOf(selected.getTotalmass()));
                    TankMassField.setText(BigDecimal.valueOf(selected.getMass()).toPlainString());
                    TankReservedMassField.setText(String.valueOf(selected.getReservedMassForLot()));
                    TankLowLevelField.setText(String.valueOf(selected.getLowLevel()));
                    TankHighLevelField.setText(String.valueOf(selected.getHightLevel()));
                    TankHeightField.setText(String.valueOf(selected.getHeight()));
                    TankRadiusLevelField.setText(String.valueOf(selected.getRedius()));
                    TankPermissionField.setValue(selected.getPermission().name());
                    TankStatusField.setText(selected.getStatus().name());
                    TankProductNameField.setText(String.valueOf(selected.getProductID()));

                    correctedDensityField.setText(String.valueOf(selected.getCorrectedDensity()));
                    calculatedDensityField.setText(String.valueOf(selected.getCalcuatedDensity()));
                    correctionTempField.setText(String.valueOf(selected.getCorrectionTemp()));
                    correctionFactorField.setText(String.valueOf(selected.getCorrectionFactor()));
                    currentTempField.setText(String.valueOf(selected.getCurrentTemperature()));


                    if (StringUtils.isNoneBlank(TankProductNameField.getText())) {
                        ProductDTO selectedProduct = productService.findByName(TankProductNameField.getText());
                        ContextProductnameFieldField.setText(selectedProduct.getName());
                        ContextProductStockIDField.setText(selectedProduct.getStockID());
                        ContextProductSpecficGravityField.setText(String.valueOf(selectedProduct.getSpecificGravity()));
                        ContextProductDescriptionField.setText(selectedProduct.getDescription());
                    }

                    tabContainer.setCursor(Cursor.DEFAULT);

                    TanksTableView.refresh();
                }
            } catch (Exception e) {
                e.printStackTrace();
                MainWindow.showErrorWindowForException("Error selecting tank", e);
            }
        });

        //managing context
        TankProductNameField.setOnMouseClicked(this::onRunProductContextWidow);
        ContextProductTableView.setOnMouseClicked(action -> {
            if (action.getClickCount() == 2) {
                ProductDTO selected = ContextProductTableView.getSelectionModel().getSelectedItem();

                TankProductNameField.setText(String.valueOf(selected.getName()));
                ContextProductnameFieldField.setText(selected.getName());
                ContextProductStockIDField.setText(selected.getStockID());
                ContextProductSpecficGravityField.setText(String.valueOf(selected.getSpecificGravity()));
                ContextProductDescriptionField.setText(selected.getDescription());

                contextProductStage.close();
            }
        });

    }

    @Async
    private void onRunTankContextWindow(MouseEvent action) {
        ContextTanksList = FXCollections.observableArrayList(Lists.newArrayList(tankService.findAll()));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ContextTanksTableView.getItems().clear();
                ContextTanksTableView.setItems(ContextTanksList);
                contextTanksٍtage.setWidth(1300);
                contextTanksٍtage.setHeight(500);
                contextTanksٍtage.setResizable(false);
                contextTanksٍtage.setX(action.getScreenX());
                contextTanksٍtage.setY(action.getScreenY());
                contextTanksٍtage.show();
            }
        });
    }

    @Async
    private void onUpdateTank(MouseEvent action) {
        try {
            if ((TankNameField.getText().length() > 0) && (TankProductNameField.getText().length() > 0)) {
                tankService.findByTankName(TankNameField.getText()).ifPresentOrElse(tank -> {

                    tank.setCapacity(Double.parseDouble(TankCapacityField.getText()));
                    tank.setLowLevel(Double.parseDouble(TankLowLevelField.getText()));
                    tank.setHeight(Double.parseDouble(TankHeightField.getText()));
                    tank.setRedius(Double.parseDouble(TankRadiusLevelField.getText()));
                    tank.setHightLevel(Double.parseDouble(TankHighLevelField.getText()));
                    tank.setPermission(Permissions.valueOf(TankPermissionField.getValue().toString()));
                    tank.setProductID(TankProductNameField.getText());

                    tank.setCorrectionFactor(Double.parseDouble(correctionFactorField.getText()));
                    tank.setCorrectionTemp(Double.parseDouble(correctionTempField.getText()));
                    tank.setCorrectedDensity(Double.parseDouble(correctedDensityField.getText()));

                    tankService.save(tank);
                }, () -> {
                    MainWindow.showErrorWindow("Error Inserting data", "Driver already exist , please check entered data ...");
                });
            } else {
                MainWindow.showErrorWindow("Error updating data", "Please check entered data .. No possible empty fields");
            }
        } catch (Exception e) {
            MainWindow.showErrorWindowForException("Error inserting data", e);
        }
    }

    @Async
    private void onCreateTank(MouseEvent action) {
        try {
            if ((TankNameField.getText().length() > 0) && (TankProductNameField.getText().length() > 0)) {
                if ((tankService.findByTankName(TankNameField.getText()).isEmpty())) {
                    TankDTO tank = new TankDTO(
                            TankNameField.getText(),
                            Double.parseDouble(TankCapacityField.getText()),
                            Double.parseDouble(TankLevelField.getText()),
                            Double.parseDouble(TankVolumeField.getText()),

                            Double.parseDouble(correctedDensityField.getText()),
                            Double.parseDouble(correctionFactorField.getText()),
                            Double.parseDouble(correctionTempField.getText()),
                            Double.parseDouble(currentTempField.getText()),

                            Double.parseDouble(TankMassField.getText()),

                            Double.parseDouble(TankLowLevelField.getText()),
                            Double.parseDouble(TankHighLevelField.getText()),
                            Double.parseDouble(TankHeightField.getText()),
                            Double.parseDouble(TankRadiusLevelField.getText()),
                            Permissions.valueOf(TankPermissionField.getValue().toString()),
                            TankProductNameField.getText()
                    );
                    tankService.save(tank);
                    update();
                } else {
                    MainWindow.showErrorWindow("Error Inserting data", "Tank already exist , please check entered data ...");
                }
            } else {
                MainWindow.showErrorWindow("Error inserting data", "Please check entered data .. No possible empty fields");
            }
        } catch (Exception e) {
            MainWindow.showErrorWindowForException("Error inserting data", e);
        }
    }

    @Async
    private void onDeleteTank(MouseEvent action) {
        try {
                tankService.deleteByTankName(TankNameField.getText());
                update();

        }catch (Exception e){
            MainWindow.showErrorWindowForException("Error deleting tank", e);
        }
    }

    @Async
    private void onRunProductContextWidow(MouseEvent action) {
        ContextProductList = FXCollections.observableArrayList(Lists.newArrayList(productService.findAll()));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ContextProductTableView.getItems().clear();
                ContextProductTableView.setItems(ContextProductList);
                contextProductStage.setWidth(900);
                contextProductStage.setHeight(500);
                contextProductStage.setResizable(false);
                contextProductStage.setX(action.getScreenX());
                contextProductStage.setY(action.getScreenY());
                contextProductStage.show();
            }
        });
    }

    @Async
    private void onDeleteStrappingData(MouseEvent mouseEvent) {
        try {
                update();

        }catch (Exception e){
            MainWindow.showErrorWindowForException("Error deleting strapping data", e);
        }
    }

    public synchronized void update() {
        Task<String> updateTask = updateTask();
        TanksTableView.cursorProperty().bind(Bindings.when(updateTask.runningProperty()).then(CURSOR_WAIT).otherwise(CURSOR_DEFAULT));
        refreshTank.disableProperty().bind(updateTask.runningProperty());
        executor.execute(updateTask);
    }
    private synchronized Task<String> updateTask(){
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                List<TankDTO> dataBaseList = Lists.newArrayList(tankService.findAll());

                Platform.runLater(() -> {
                    tankslist.removeAll(
                            dataBaseList
                                    .stream()
                                    .map(TankTableObject::createFromDTO)
                                    .filter(item -> !tankslist.contains(item))
                                    .collect(() -> tankslist, ObservableList::add, ObservableList::addAll)
                                    .stream()
                                    .filter(tableListItem -> dataBaseList.stream().map(TankTableObject::createFromDTO).noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
                                    .collect(Collectors.toList()));
                });
                return null;
            }
        };
    }

    @Scheduled(fixedDelay = 500)
    public void cyclicUpdate() {
//        if (tankslist != null) {
//            final Map<Long, TankDTO> tankDTOMap = Lists.newArrayList(tankService.findAll()).stream().collect(Collectors.toMap(TankDTO::getId, Function.identity()));
//            for (TankTableObject item : tankslist) {
//                try {
//                    final TankDTO tank = tankDTOMap.get(item.getId());
//
//                    item.setVolume(tank.getVolume());
//                    item.setLevel(tank.getLevel());
//                    item.setMass(tank.getMass());
//
//                    item.setCapacity(tank.getCapacity());
//
//                    item.setTotalmass(tank.getTotalMass());
//                    item.setProductID(tank.getProductID());
//                    item.setReservedMassForLot(tank.getReservedMassForLot());
//                    item.setLowLevel(tank.getLowLevel());
//                    item.setHightLevel(tank.getHightLevel());
//                    item.setHeight(tank.getHeight());
//                    item.setRedius(tank.getRedius());
//                    item.setPermission(tank.getPermission());
//                    item.setStatus(tank.getStatus());
//
//                    item.setCorrectionFactor(tank.getCorrectionFactor());
//                    item.setCorrectionTemp(tank.getCorrectionTemp());
//                    item.setCurrentTemperature(tank.getCurrentTemperature());
//                    item.setCorrectedDensity(tank.getCorrectedDensity());
//                    item.setCalcuatedDensity(tank.getCalcuatedDensity());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }


    public Node getTabContainer() {
        return root;
    }



    public static class TankTableObject{

        private LongProperty id = new SimpleLongProperty();
        private StringProperty name = new SimpleStringProperty();
        private DoubleProperty capacity = new SimpleDoubleProperty();
        private DoubleProperty level = new SimpleDoubleProperty();
        private DoubleProperty volume = new SimpleDoubleProperty();
        private DoubleProperty totalmass = new SimpleDoubleProperty();
        private DoubleProperty mass = new SimpleDoubleProperty();
        private DoubleProperty reservedMassForLot = new SimpleDoubleProperty();
        private DoubleProperty lowLevel = new SimpleDoubleProperty();
        private DoubleProperty hightLevel = new SimpleDoubleProperty();
        private DoubleProperty height = new SimpleDoubleProperty();
        private DoubleProperty redius = new SimpleDoubleProperty();
        private ObjectProperty<Permissions> permission = new SimpleObjectProperty<>();
        private ObjectProperty<TankStatus> status = new SimpleObjectProperty<>();
        private StringProperty productID = new SimpleStringProperty();

        private DoubleProperty correctedDensity = new SimpleDoubleProperty();
        private DoubleProperty correctionFactor = new SimpleDoubleProperty();
        private DoubleProperty correctionTemp = new SimpleDoubleProperty();
        private DoubleProperty currentTemperature = new SimpleDoubleProperty();
        private DoubleProperty calcuatedDensity = new SimpleDoubleProperty();

        public TankTableObject(long id, String name, double capacity, double level,
                               double volume, double totalmass, double mass, double reservedMassForLot,
                               double lowLevel, double hightLevel, double height, double redius, Permissions permission,
                               TankStatus status, String productID,
                               double correctionFactor, double correctionTemperature, double correctedDensity, double currentTemperature, double calculatedDensity) {
            this.setId(id);
            setName(name);
            setCapacity(capacity);
            setLevel(level);
            setVolume(volume);
            setTotalmass(totalmass);
            setMass(mass);
            setReservedMassForLot(reservedMassForLot);
            setLowLevel(lowLevel);
            setHightLevel(hightLevel);
            setHeight(height);
            this.setRedius(redius);
            setPermission(permission);
            setStatus(status);
            setProductID(productID);

            setCorrectionFactor(correctionFactor);
            setCorrectionTemp(correctionTemperature);
            setCorrectedDensity(correctedDensity);
            setCurrentTemperature(currentTemperature);
            setCalcuatedDensity(calculatedDensity);

        }

        public static TankTableObject createFromDTO(TankDTO tankDTO){
                return new TankTableObject(tankDTO.getId(), tankDTO.getName(), tankDTO.getCapacity(), tankDTO.getLevel(), tankDTO.getVolume(),
                         tankDTO.getTotalMass(), tankDTO.getMass(), tankDTO.getReservedMassForLot(), tankDTO.getLowLevel(), tankDTO.getHightLevel(),
                        tankDTO.getHeight(), tankDTO.getRedius(), tankDTO.getPermission(), tankDTO.getStatus(), tankDTO.getProductID(),
                        tankDTO.getCorrectionFactor(), tankDTO.getCorrectionTemp(), tankDTO.getCorrectedDensity(), tankDTO.getCurrentTemperature(), tankDTO.getCalcuatedDensity());
            }

        public long getId() {
            return id.get();
        }

        public LongProperty idProperty() {
            return id;
        }

        public void setId(long id) {
            this.id.set(id);
        }

        public String getName() {
            return name.get();
        }

        public StringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public double getCapacity() {
            return capacity.get();
        }

        public DoubleProperty capacityProperty() {
            return capacity;
        }

        public void setCapacity(double capacity) {
            this.capacity.set(capacity);
        }

        public double getLevel() {
            return level.get();
        }

        public DoubleProperty levelProperty() {
            return level;
        }

        public void setLevel(double level) {
            this.level.set(level);
        }

        public double getVolume() {
            return volume.get();
        }

        public DoubleProperty volumeProperty() {
            return volume;
        }

        public void setVolume(double volume) {
            this.volume.set(volume);
        }

        public double getTotalmass() {
            return totalmass.get();
        }

        public DoubleProperty totalmassProperty() {
            return totalmass;
        }

        public void setTotalmass(double totalmass) {
            this.totalmass.set(totalmass);
        }

        public double getMass() {
            return mass.get();
        }

        public DoubleProperty massProperty() {
            return mass;
        }

        public void setMass(double mass) {
            this.mass.set(mass);
        }

        public double getReservedMassForLot() {
            return reservedMassForLot.get();
        }

        public DoubleProperty reservedMassForLotProperty() {
            return reservedMassForLot;
        }

        public void setReservedMassForLot(double reservedMassForLot) {
            this.reservedMassForLot.set(reservedMassForLot);
        }

        public double getLowLevel() {
            return lowLevel.get();
        }

        public DoubleProperty lowLevelProperty() {
            return lowLevel;
        }

        public void setLowLevel(double lowLevel) {
            this.lowLevel.set(lowLevel);
        }

        public double getHightLevel() {
            return hightLevel.get();
        }

        public DoubleProperty hightLevelProperty() {
            return hightLevel;
        }

        public void setHightLevel(double hightLevel) {
            this.hightLevel.set(hightLevel);
        }

        public double getHeight() {
            return height.get();
        }

        public DoubleProperty heightProperty() {
            return height;
        }

        public void setHeight(double height) {
            this.height.set(height);
        }

        public double getRedius() {
            return redius.get();
        }

        public DoubleProperty rediusProperty() {
            return redius;
        }

        public void setRedius(double redius) {
            this.redius.set(redius);
        }

        public Permissions getPermission() {
            return permission.get();
        }

        public ObjectProperty<Permissions> permissionProperty() {
            return permission;
        }

        public void setPermission(Permissions permission) {
            this.permission.set(permission);
        }

        public TankStatus getStatus() {
            return status.get();
        }

        public ObjectProperty<TankStatus> statusProperty() {
            return status;
        }

        public void setStatus(TankStatus status) {
            this.status.set(status);
        }

        public String getProductID() {
            return productID.get();
        }

        public StringProperty productIDProperty() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID.set(productID);
        }

        public double getCorrectedDensity() {
            return correctedDensity.get();
        }

        public DoubleProperty correctedDensityProperty() {
            return correctedDensity;
        }

        public void setCorrectedDensity(double correctedDensity) {
            this.correctedDensity.set(correctedDensity);
        }

        public double getCorrectionFactor() {
            return correctionFactor.get();
        }

        public DoubleProperty correctionFactorProperty() {
            return correctionFactor;
        }

        public void setCorrectionFactor(double correctionFactor) {
            this.correctionFactor.set(correctionFactor);
        }

        public double getCorrectionTemp() {
            return correctionTemp.get();
        }

        public DoubleProperty correctionTempProperty() {
            return correctionTemp;
        }

        public void setCorrectionTemp(double correctionTemp) {
            this.correctionTemp.set(correctionTemp);
        }

        public double getCurrentTemperature() {
            return currentTemperature.get();
        }

        public DoubleProperty currentTemperatureProperty() {
            return currentTemperature;
        }

        public void setCurrentTemperature(double currentTemperature) {
            this.currentTemperature.set(currentTemperature);
        }

        public double getCalcuatedDensity() {
            return calcuatedDensity.get();
        }

        public DoubleProperty calcuatedDensityProperty() {
            return calcuatedDensity;
        }

        public void setCalcuatedDensity(double calcuatedDensity) {
            this.calcuatedDensity.set(calcuatedDensity);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TankTableObject that = (TankTableObject) o;
            return Objects.equal(id.getValue(), that.id.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id.getValue());
        }

        @Override
        public String toString() {
            return "Tank    id = %-10s, name = %-30s, capacity = %-10s".formatted(id, name, capacity);
        }
    }
}
