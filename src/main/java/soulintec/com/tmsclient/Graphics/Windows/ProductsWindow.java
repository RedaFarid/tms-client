package soulintec.com.tmsclient.Graphics.Windows;


import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
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
import org.controlsfx.control.table.TableFilter;
import org.controlsfx.dialog.ExceptionDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.ProductDTO;
import soulintec.com.tmsclient.Entities.TankDTO;
import soulintec.com.tmsclient.Graphics.Controls.DataEntryPartitionTitled;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedButton;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedTextField;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.MainWindow;
import soulintec.com.tmsclient.Services.ProductService;
import soulintec.com.tmsclient.Services.TanksService;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Component
public class ProductsWindow implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private DataEntryPartitionTitled dataentery = new DataEntryPartitionTitled("Product");
    private VBox root = new VBox();
    private VBox vbox = new VBox();
    private ToolBar hbox = new ToolBar();
    private Label headerLabel = new Label("Stocks");

    private Stage mainWindow = null;
    private TableFilter<ProductDTO> tableFilter;

    private ObservableList<ProductDTO> list = FXCollections.observableArrayList();
    private TableView<ProductDTO> table = new TableView<>();
    private TableColumn<ProductDTO, Long> idColumn = new TableColumn<>("Id");
    private TableColumn<ProductDTO, String> NameColumn = new TableColumn<>("Name");
    private TableColumn<ProductDTO, String> StockIDColumn = new TableColumn<>("Stock ID");
    private TableColumn<ProductDTO, String> SpecificGravityColumn = new TableColumn<>("Specific Gravity");
    private TableColumn<ProductDTO, String> stockQtyColumn = new TableColumn<>("Stock quantity");
    private TableColumn<ProductDTO, String> DescriptionColumn = new TableColumn<>("Description");

    private EnhancedButton Insert = new EnhancedButton("Create new product");
    private EnhancedButton Delete = new EnhancedButton("Delete selected product");
    private EnhancedButton Update = new EnhancedButton("Update selected product");
    private EnhancedButton report = new EnhancedButton("Show Report");

    private Label idLabel = new Label("Id :");
    private Label nameLabel = new Label("Name :");
    private Label StockIDLabel = new Label("Stock ID :");
    private Label SpecificGravityLabel = new Label("Specific Gravity :");
    private Label DescriptionLabel = new Label("Description :");

    private EnhancedTextField idField = new EnhancedTextField();
    private EnhancedTextField nameField = new EnhancedTextField();
    private EnhancedTextField StockIDField = new EnhancedTextField();
    private EnhancedTextField SpecficGravityField = new EnhancedTextField();
    private EnhancedTextField DescriptionField = new EnhancedTextField();

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);


    @Autowired
    private ProductService productService;

    @Autowired
    private TanksService tankService;


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
        StockIDLabel.setPrefWidth(150);
        SpecificGravityLabel.setPrefWidth(150);
        DescriptionLabel.setPrefWidth(150);

        idLabel.setTextAlignment(TextAlignment.RIGHT);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        StockIDLabel.setTextAlignment(TextAlignment.RIGHT);
        SpecificGravityLabel.setTextAlignment(TextAlignment.RIGHT);
        DescriptionLabel.setTextAlignment(TextAlignment.RIGHT);

        idLabel.setAlignment(Pos.BASELINE_RIGHT);
        nameLabel.setAlignment(Pos.BASELINE_RIGHT);
        StockIDLabel.setAlignment(Pos.BASELINE_RIGHT);
        SpecificGravityLabel.setAlignment(Pos.BASELINE_RIGHT);
        DescriptionLabel.setAlignment(Pos.BASELINE_RIGHT);

        idLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        nameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        StockIDLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        SpecificGravityLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        DescriptionLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        idField.setPrefWidth(250);
        nameField.setPrefWidth(250);
        StockIDField.setPrefWidth(250);
        SpecficGravityField.setPrefWidth(250);
        DescriptionField.setPrefWidth(250);

        //restriction handling
        nameField.setRestrict("[a-zA-Z-_/. ]");
        nameField.setMaxLength(45);
        StockIDField.setRestrict("[a-zA-Z0-9 ]");
        StockIDField.setMaxLength(14);
        SpecficGravityField.setRestrict("[0-9].");
        SpecficGravityField.setMaxLength(11);
        DescriptionField.setRestrict("[a-zA-Z1-9-_/*]");
        DescriptionField.setMaxLength(500);

        dataentery.add(idLabel, 5, 2);
        dataentery.add(nameLabel, 1, 2);
        dataentery.add(StockIDLabel, 3, 2);
        dataentery.add(SpecificGravityLabel, 1, 3);
        dataentery.add(DescriptionLabel, 3, 3);

        dataentery.add(idField, 6, 2);
        dataentery.add(nameField, 2, 2);
        dataentery.add(StockIDField, 4, 2);
        dataentery.add(SpecficGravityField, 2, 3);
        dataentery.add(DescriptionField, 4, 3);

        //table configuration
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        StockIDColumn.setCellValueFactory(new PropertyValueFactory<>("StockID"));
        SpecificGravityColumn.setCellValueFactory(new PropertyValueFactory<>("SpecificGravity"));
        stockQtyColumn.setCellValueFactory(new PropertyValueFactory<>("stockQty"));
        DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));

        table.getColumns().addAll(idColumn, NameColumn, StockIDColumn, SpecificGravityColumn, stockQtyColumn, DescriptionColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.prefHeightProperty().bind(root.heightProperty().subtract(dataentery.heightProperty().add(hbox.heightProperty())));
        table.setItems(list);
        tableFilter = TableFilter.forTableView(table).apply();

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

        table.getSelectionModel().getSelectedItems().addListener((ListChangeListener<ProductDTO>) c -> {
            try {
                if (!c.getList().isEmpty()) {
                    ProductDTO selected = c.getList().get(0);
                    idField.setText(String.valueOf(selected.getId()));
                    nameField.setText(selected.getName());
                    StockIDField.setText(selected.getStockID());
                    SpecficGravityField.setText(String.valueOf(selected.getSpecificGravity()));
                    DescriptionField.setText(selected.getDescription());
                }
            } catch (Exception e) {
                showErrorWindowForException("Error reading selected driver", e);
            }
        });
    }

    @Async
    private void onUpdate(MouseEvent mouseEvent) {
        try {
            if ((nameField.getText().length() > 0) && (SpecficGravityField.getText().length() > 0) && (DescriptionField.getText().length() > 0)) {

                productService.findById(Long.parseLong(idField.getText())).ifPresentOrElse(productDTO -> {
                    productDTO.setDescription(DescriptionField.getText());
                    productDTO.setSpecificGravity(Double.parseDouble(SpecficGravityField.getText()));
                    productDTO.setStockID(StockIDField.getText());
                    productDTO.setName(nameField.getText());

                    productService.save(productDTO);
                }, () -> {});
                update();
            } else {
                showErrorWindow("Error inserting data", "Driver not exist , please check entered data ...");
            }
        } catch (Exception e) {
            showErrorWindowForException("Error updating driver", e);
        }
    }

    @Async
    private void onDelete(MouseEvent mouseEvent) {
        try {
            if (tankService.existsByProductId(nameField.getText()) == 0L) {
                productService.deleteById(Long.parseLong(idField.getText()));
                update();
            }else {
                final String reduce = tankService.findByProductId(nameField.getText()).stream().map(TankDTO::getName).map(String::valueOf).map(String::toUpperCase).reduce("", (a, b) -> a + "Tank : " + b + "\n");
                MainWindow.showErrorWindow("Can not delete ", "Stock is referenced by the following tanks \n" + reduce);
            }
        } catch (Exception e) {
            showErrorWindowForException("Error deleting client", e);
        }
    }

    @Async
    private void onCreate(MouseEvent mouseEvent) {
        try {
            if ((nameField.getText().length() > 0) && (SpecficGravityField.getText().length() > 0) && (DescriptionField.getText().length() > 0)) {
                ProductDTO product = new ProductDTO(nameField.getText(), StockIDField.getText(),
                        Double.parseDouble(SpecficGravityField.getText()), DescriptionField.getText());
                productService.save(product);
            } else {
                showErrorWindow("Error inserting data", "Driver already exist , please check entered data ...");
            }
            update();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorWindowForException("Error inserting client", e);
        }
    }

    @Async
    public void update() {
        List<ProductDTO> dataBaseList = productService.findAll();
        Platform.runLater(() -> {
            getProductsList().removeAll(
                    dataBaseList
                            .stream()
                            .filter(item -> !getProductsList().contains(item))
                            .collect(this::getProductsList, ObservableList::add, ObservableList::addAll)
                            .stream()
                            .filter(tableListItem -> dataBaseList.stream().noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
                            .collect(Collectors.toList()));
        });
    }

    public Node getRoot() {
        return root;
    }

    private ObservableList<ProductDTO> getProductsList() {
        return list;
    }

    protected void showErrorWindow(String header, String content) {
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
