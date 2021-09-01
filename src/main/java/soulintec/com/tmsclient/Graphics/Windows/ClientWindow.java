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
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Entities.ClientDTO;
import soulintec.com.tmsclient.Entities.ClientLocationsDTO;
import soulintec.com.tmsclient.Graphics.Controls.DataEntryPartitionTitled;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedButton;
import soulintec.com.tmsclient.Graphics.Controls.EnhancedTextField;
import soulintec.com.tmsclient.Services.ClientsService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientWindow implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private DataEntryPartitionTitled clientsDataentery = new DataEntryPartitionTitled("Client");;
    private DataEntryPartitionTitled locationsDataenetery = new DataEntryPartitionTitled("Location");;
    private DataEntryPartitionTitled selectedClientData = new DataEntryPartitionTitled("Selected client");

    private Tab clientstab = new Tab("Clients Management");
    private Tab locationstab = new Tab("Locations Mnagement");
    private VBox clientsPane = new VBox();
    private VBox locationspane = new VBox();

    private VBox clientsvbox = new VBox();
    private ToolBar clientshbox = new ToolBar();
    private VBox locationsvbox = new VBox();
    private ToolBar locationshbox = new ToolBar();
    private Label headerLabel = new Label("Clients");

    private BorderPane root = new BorderPane();
    private TabPane tabContainer = new TabPane();

    private Stage mainStage = null;
    private Stage contextstage;

    private TableFilter<ClientDTO> clientsTableFilter;
    private TableFilter<ClientLocationsDTO> locationsTableFilter;
    private TableFilter<ClientDTO> contextTableFilter;

    private ObservableList<ClientDTO> ClientsList = FXCollections.observableArrayList();
    private TableView<ClientDTO> ClientsTableView = new TableView<>();
    private TableColumn<ClientDTO, Long> ClientIDColumn = new TableColumn<>("ID");
    private TableColumn<ClientDTO, String> ClientNameColumn = new TableColumn<>("Name");
    private TableColumn<ClientDTO, String> ClientMainOfficeColumn = new TableColumn<>("Office Address");
    private TableColumn<ClientDTO, String> ClientContactNameColumn = new TableColumn<>("Contact name");
    private TableColumn<ClientDTO, String> ClientContactTelNumberColumn = new TableColumn<>("Tel Number");
    private TableColumn<ClientDTO, String> ClientContactEmailColumn = new TableColumn<>("Email");

    private ObservableList<ClientDTO> ContextClientsList = FXCollections.observableArrayList();
    private TableView<ClientDTO> ContextClientsTableView = new TableView<>();
    private TableColumn<ClientDTO, Long> ContextClientIDColumn = new TableColumn<>("ID");
    private TableColumn<ClientDTO, String> ContextClientNameColumn = new TableColumn<>("Name");
    private TableColumn<ClientDTO, String> ContextClientMainOfficeColumn = new TableColumn<>("Office Address");
    private TableColumn<ClientDTO, String> ContextClientContactNameColumn = new TableColumn<>("Contact name");
    private TableColumn<ClientDTO, String> ContextClientContactTelNumberColumn = new TableColumn<>("Tel Number");
    private TableColumn<ClientDTO, String> ContextClientContactEmailColumn = new TableColumn<>("Email");

    private ObservableList<ClientLocationsDTO> locationsList = FXCollections.observableArrayList();
    private TableView<ClientLocationsDTO> LocationsTableView = new TableView<>();
    private TableColumn<ClientLocationsDTO, Long> LocationIDColumn = new TableColumn<>("ID");
    private TableColumn<ClientLocationsDTO, String> LocationClientColumn = new TableColumn<>("Client");
    private TableColumn<ClientLocationsDTO, String> LocationNameColumn = new TableColumn<>("Location");
    private TableColumn<ClientLocationsDTO, String> LocationAddressColumn = new TableColumn<>("Address");
    private TableColumn<ClientLocationsDTO, String> LocationContactNameColumn = new TableColumn<>("Contact name");
    private TableColumn<ClientLocationsDTO, String> LocationContactTelNumberColumn = new TableColumn<>("Tel Number");
    private TableColumn<ClientLocationsDTO, String> LocationContactEmailColumn = new TableColumn<>("Email");

    private EnhancedButton InsertClient = new EnhancedButton("Create new client");
    private EnhancedButton DeleteClient = new EnhancedButton("Delete selected client");
    private EnhancedButton UpdateClient = new EnhancedButton("Update selected client");
    private EnhancedButton report = new EnhancedButton("Show Report");
    private EnhancedButton InsertLocation = new EnhancedButton("Create new location");
    private EnhancedButton DeleteLocation = new EnhancedButton("Delete selected location");
    private EnhancedButton UpdateLocation = new EnhancedButton("Update selected location");
    private EnhancedButton ReportLocation = new EnhancedButton("Show Report");
    private Label IDClientsLabel = new Label();
    private Label nameLabel = new Label("Name :");
    private Label MainOfficeAdressLabel = new Label("Office Address :");
    private Label ContactNameLabel = new Label("Contact name :");
    private Label ContactTelNumberLabel = new Label("Tel Number :");
    private Label ContactEmailLabel = new Label("Email :");

    private Label LocationIDLabel = new Label();
    private Label LocationClientIDLabel = new Label("Client ID");
    private Label LocationNameLabel = new Label("Location Name");
    private Label LocationAdressLabel = new Label("Location Address");
    private Label LocationContactNameLabel = new Label("Contact name");
    private Label LocationContactTelNumberLabel = new Label("Tel Number");
    private Label LocationContactEmailLabel = new Label("Email");

    private Label contextINameLabel = new Label();
    private Label contextOfficeAddressLabel = new Label("");
    private Label ContextContactNumLabel = new Label("");
    private Label ContextTelNumberLabel = new Label("");
    private Label ContextEmailLabel = new Label("");

    private Label contextLocationNameLabel = new Label("Name :");
    private Label contextLocationAdressLabel = new Label("Office Address :");
    private Label contextLocationContactNameLabel = new Label("Contact name :");
    private Label contextLocationContactTelNumberLabel = new Label("Tel Number :");
    private Label contextLocationContactEmailLabel = new Label("Email :");

    private EnhancedTextField nameField = new EnhancedTextField();
    private EnhancedTextField MainOfficeAdressField = new EnhancedTextField();
    private EnhancedTextField ContactNameField = new EnhancedTextField();
    private EnhancedTextField ContactTelNumberField = new EnhancedTextField();
    private EnhancedTextField ContactEmailField = new EnhancedTextField();

    private EnhancedTextField LocationclientIDField = new EnhancedTextField();
    private EnhancedTextField LocationnameField = new EnhancedTextField();
    private EnhancedTextField LocationAdressField = new EnhancedTextField();
    private EnhancedTextField LocationContactNameField = new EnhancedTextField();
    private EnhancedTextField LocationContactTelNumberField = new EnhancedTextField();
    private EnhancedTextField LocationContactEmailField = new EnhancedTextField();

    private final ObjectProperty<Cursor> CURSOR_DEFAULT = new SimpleObjectProperty<>(Cursor.DEFAULT);
    private final ObjectProperty<Cursor> CURSOR_WAIT = new SimpleObjectProperty<>(Cursor.WAIT);

    @Autowired
    ClientsService clientService;


    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        mainStage = event.getStage();
        contextstage = new Stage();

        userAuthorities();
        graphicsBuilder();
        actionHandling();
    }
    private void userAuthorities() {
        
    }

    private void graphicsBuilder() {

        clientsGraphicsBuilder();
        locationsGraphicBuilder();
        contextTableCreation();

        tabContainer.getTabs().addAll(clientstab, locationstab);

        root.setTop(headerLabel);
        root.setCenter(tabContainer);
        root.setPadding(new Insets(10));
    }
    private void clientsGraphicsBuilder() {
        headerLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:white;-fx-font-size:25;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setTextAlignment(TextAlignment.CENTER);
        headerLabel.prefWidthProperty().bind(tabContainer.widthProperty());
        headerLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        //control buttons configuration
        InsertClient.setPrefWidth(150);
        DeleteClient.setPrefWidth(150);
        UpdateClient.setPrefWidth(150);
        report.setPrefWidth(150);
        //dataentery region configuration
        IDClientsLabel.setAlignment(Pos.CENTER);

        nameLabel.setPrefWidth(150);
        MainOfficeAdressLabel.setPrefWidth(150);
        ContactNameLabel.setPrefWidth(150);
        ContactTelNumberLabel.setPrefWidth(150);
        ContactEmailLabel.setPrefWidth(150);

        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        MainOfficeAdressLabel.setTextAlignment(TextAlignment.RIGHT);
        ContactNameLabel.setTextAlignment(TextAlignment.RIGHT);
        ContactTelNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        ContactEmailLabel.setTextAlignment(TextAlignment.RIGHT);

        nameLabel.setAlignment(Pos.BASELINE_RIGHT);
        MainOfficeAdressLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContactNameLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContactTelNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        ContactEmailLabel.setAlignment(Pos.BASELINE_RIGHT);

        nameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        MainOfficeAdressLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContactNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContactTelNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        ContactEmailLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        IDClientsLabel.setPrefWidth(50);
        MainOfficeAdressField.setPrefWidth(150);
        ContactNameField.setPrefWidth(150);
        ContactTelNumberField.setPrefWidth(150);
        ContactEmailField.setPrefWidth(150);
        nameField.setPrefWidth(150);

        //restriction declaration
        MainOfficeAdressField.setPrefWidth(250);
        ContactNameField.setPrefWidth(250);
        ContactTelNumberField.setPrefWidth(250);
        ContactEmailField.setPrefWidth(250);
        nameField.setPrefWidth(250);

        //restriction handling
        nameField.setRestrict("[a-zA-Z-_ ]");
        nameField.setMaxLength(45);
        MainOfficeAdressField.setRestrict("[a-zA-Z-_ ]");
        MainOfficeAdressField.setMaxLength(45);
        ContactNameField.setRestrict("[a-zA-Z-_ ]");
        ContactNameField.setMaxLength(14);
        ContactTelNumberField.setRestrict("[0-9]");
        ContactTelNumberField.setMaxLength(11);
        ContactEmailField.setRestrict("[a-zA-Z0-9-_.@]");
        ContactEmailField.setMaxLength(30);

        clientsDataentery.add(nameLabel, 1, 2);
        clientsDataentery.add(MainOfficeAdressLabel, 3, 2);
        clientsDataentery.add(ContactNameLabel, 1, 3);
        clientsDataentery.add(ContactTelNumberLabel, 3, 3);
        clientsDataentery.add(ContactEmailLabel, 1, 4);

        clientsDataentery.add(IDClientsLabel, 1, 1);
        clientsDataentery.add(nameField, 2, 2);
        clientsDataentery.add(MainOfficeAdressField, 4, 2);
        clientsDataentery.add(ContactNameField, 2, 3);
        clientsDataentery.add(ContactTelNumberField, 4, 3);
        clientsDataentery.add(ContactEmailField, 2, 4);

        //table configuration
        ClientIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ClientNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ClientMainOfficeColumn.setCellValueFactory(new PropertyValueFactory<>("MainOfficeAdress"));
        ClientContactNameColumn.setCellValueFactory(new PropertyValueFactory<>("ContactName"));
        ClientContactTelNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ContactTelNumber"));
        ClientContactEmailColumn.setCellValueFactory(new PropertyValueFactory<>("ContactEmail"));

        ClientsTableView.getColumns().addAll(ClientIDColumn, ClientNameColumn, ClientMainOfficeColumn, ClientContactNameColumn, ClientContactTelNumberColumn, ClientContactEmailColumn);
        ClientsTableView.prefHeightProperty().bind(tabContainer.heightProperty().subtract(clientsvbox.heightProperty()));
        ClientsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ClientsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ClientsTableView.setItems(getClientsList());
        clientsTableFilter = TableFilter.forTableView(ClientsTableView).apply();

        clientsDataentery.setVgap(5);
        clientsDataentery.setHgap(5);

        clientshbox.getItems().addAll(InsertClient, UpdateClient, DeleteClient, report);

        clientsvbox.getChildren().addAll(clientsDataentery, clientshbox);
        clientsvbox.setPadding(new Insets(5));
        clientsvbox.setSpacing(5);

        clientsPane.getChildren().add(clientsvbox);
        clientsPane.getChildren().add(ClientsTableView);

        clientstab.setContent(clientsPane);
        clientstab.setClosable(false);

    }
    private void locationsGraphicBuilder() {

        //control buttons configuration
        InsertLocation.setPrefWidth(150);
        DeleteLocation.setPrefWidth(150);
        UpdateLocation.setPrefWidth(150);
        ReportLocation.setPrefWidth(150);
        //dataentery region configuration
        LocationIDLabel.setAlignment(Pos.CENTER);

        LocationClientIDLabel.setPrefWidth(150);
        LocationNameLabel.setPrefWidth(150);
        LocationAdressLabel.setPrefWidth(150);
        LocationContactNameLabel.setPrefWidth(150);
        LocationContactTelNumberLabel.setPrefWidth(150);
        LocationContactEmailLabel.setPrefWidth(150);

        LocationClientIDLabel.setTextAlignment(TextAlignment.RIGHT);
        LocationNameLabel.setTextAlignment(TextAlignment.RIGHT);
        LocationAdressLabel.setTextAlignment(TextAlignment.RIGHT);
        LocationContactNameLabel.setTextAlignment(TextAlignment.RIGHT);
        LocationContactTelNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        LocationContactEmailLabel.setTextAlignment(TextAlignment.RIGHT);

        LocationClientIDLabel.setAlignment(Pos.BASELINE_RIGHT);
        LocationNameLabel.setAlignment(Pos.BASELINE_RIGHT);
        LocationAdressLabel.setAlignment(Pos.BASELINE_RIGHT);
        LocationContactNameLabel.setAlignment(Pos.BASELINE_RIGHT);
        LocationContactTelNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        LocationContactEmailLabel.setAlignment(Pos.BASELINE_RIGHT);

        LocationClientIDLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        LocationNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        LocationAdressLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        LocationContactNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        LocationContactTelNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        LocationContactEmailLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        LocationclientIDField.setPrefWidth(150);
        LocationnameField.setPrefWidth(250);
        LocationAdressField.setPrefWidth(250);
        LocationContactNameField.setPrefWidth(250);
        LocationContactTelNumberField.setPrefWidth(250);
        LocationContactEmailField.setPrefWidth(250);

        //restriction handling
        LocationnameField.setRestrict("[a-zA-Z-_ ]");
        LocationnameField.setMaxLength(45);
        LocationAdressField.setRestrict("[a-zA-Z-_ ]");
        LocationAdressField.setMaxLength(45);
        LocationContactNameField.setRestrict("[a-zA-Z-_ ]");
        LocationContactNameField.setMaxLength(14);
        LocationContactTelNumberField.setRestrict("[0-9]");
        LocationContactTelNumberField.setMaxLength(11);
        LocationContactEmailField.setRestrict("[a-zA-Z0-9-_.@]");
        LocationContactEmailField.setMaxLength(30);

        locationsDataenetery.add(LocationClientIDLabel, 1, 2);
        locationsDataenetery.add(LocationNameLabel, 3, 2);
        locationsDataenetery.add(LocationAdressLabel, 1, 3);
        locationsDataenetery.add(LocationContactNameLabel, 3, 3);
        locationsDataenetery.add(LocationContactTelNumberLabel, 1, 4);
        locationsDataenetery.add(LocationContactEmailLabel, 3, 4);

//        locationsDataenetery.add(LocationIDLabel, 1, 1);
        locationsDataenetery.add(LocationclientIDField, 2, 2);
        locationsDataenetery.add(LocationnameField, 4, 2);
        locationsDataenetery.add(LocationAdressField, 2, 3);
        locationsDataenetery.add(LocationContactNameField, 4, 3);
        locationsDataenetery.add(LocationContactTelNumberField, 2, 4);
        locationsDataenetery.add(LocationContactEmailField, 4, 4);

        //selected client data
        contextLocationNameLabel.setPrefWidth(150);
        contextLocationAdressLabel.setPrefWidth(150);
        contextLocationContactNameLabel.setPrefWidth(150);
        contextLocationContactTelNumberLabel.setPrefWidth(150);
        contextLocationContactEmailLabel.setPrefWidth(150);

        contextLocationNameLabel.setTextAlignment(TextAlignment.RIGHT);
        contextLocationAdressLabel.setTextAlignment(TextAlignment.RIGHT);
        contextLocationContactNameLabel.setTextAlignment(TextAlignment.RIGHT);
        contextLocationContactTelNumberLabel.setTextAlignment(TextAlignment.RIGHT);
        contextLocationContactEmailLabel.setTextAlignment(TextAlignment.RIGHT);

        contextLocationNameLabel.setAlignment(Pos.BASELINE_RIGHT);
        contextLocationAdressLabel.setAlignment(Pos.BASELINE_RIGHT);
        contextLocationContactNameLabel.setAlignment(Pos.BASELINE_RIGHT);
        contextLocationContactTelNumberLabel.setAlignment(Pos.BASELINE_RIGHT);
        contextLocationContactEmailLabel.setAlignment(Pos.BASELINE_RIGHT);

        contextLocationNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextLocationAdressLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextLocationContactNameLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextLocationContactTelNumberLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");
        contextLocationContactEmailLabel.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:DARKCYAN;");

        contextINameLabel.setPrefWidth(250);
        contextOfficeAddressLabel.setPrefWidth(250);
        ContextContactNumLabel.setPrefWidth(250);
        ContextTelNumberLabel.setPrefWidth(250);
        ContextEmailLabel.setPrefWidth(250);

        contextLocationNameLabel.setTextAlignment(TextAlignment.LEFT);
        contextLocationAdressLabel.setTextAlignment(TextAlignment.LEFT);
        contextLocationContactNameLabel.setTextAlignment(TextAlignment.LEFT);
        contextLocationContactTelNumberLabel.setTextAlignment(TextAlignment.LEFT);
        contextLocationContactEmailLabel.setTextAlignment(TextAlignment.LEFT);

        contextINameLabel.setTextAlignment(TextAlignment.RIGHT);
        contextOfficeAddressLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextContactNumLabel.setTextAlignment(TextAlignment.RIGHT);
        ContextTelNumberLabel.setTextAlignment(TextAlignment.RIGHT);;
        ContextEmailLabel.setTextAlignment(TextAlignment.RIGHT);

        selectedClientData.add(contextLocationNameLabel, 1, 1);
        selectedClientData.add(contextLocationAdressLabel, 3, 1);
        selectedClientData.add(contextLocationContactNameLabel, 1, 2);
        selectedClientData.add(contextLocationContactTelNumberLabel, 3, 2);
        selectedClientData.add(contextLocationContactEmailLabel, 5, 1);

        selectedClientData.add(contextINameLabel, 2, 1);
        selectedClientData.add(contextOfficeAddressLabel, 4, 1);
        selectedClientData.add(ContextContactNumLabel, 2, 2);
        selectedClientData.add(ContextTelNumberLabel, 4, 2);
        selectedClientData.add(ContextEmailLabel, 6, 1);

        //table configuration
        LocationIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        LocationClientColumn.setCellValueFactory(new PropertyValueFactory<>("ClientID"));
        LocationNameColumn.setCellValueFactory(new PropertyValueFactory<>("LocationName"));
        LocationAddressColumn.setCellValueFactory(new PropertyValueFactory<>("LocationAdress"));
        LocationContactNameColumn.setCellValueFactory(new PropertyValueFactory<>("ContactName"));
        LocationContactTelNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ContactTelNumber"));
        LocationContactEmailColumn.setCellValueFactory(new PropertyValueFactory<>("ContactEmail"));

        LocationsTableView.getColumns().addAll(LocationIDColumn, LocationClientColumn, LocationNameColumn, LocationAddressColumn,
                LocationContactNameColumn, LocationContactTelNumberColumn, LocationContactEmailColumn);
        LocationsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        LocationsTableView.prefHeightProperty().bind(tabContainer.heightProperty().subtract(locationsvbox.heightProperty()));
        LocationsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        LocationsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        LocationsTableView.setItems(getLocationsList());
        locationsTableFilter = TableFilter.forTableView(LocationsTableView).apply();


        selectedClientData.setVgap(5);
        selectedClientData.setHgap(5);

        locationshbox.getItems().addAll(InsertLocation, UpdateLocation, DeleteLocation, ReportLocation);

        locationsvbox.getChildren().addAll(selectedClientData, locationsDataenetery, locationshbox);
        locationsvbox.setPadding(new Insets(5));
        locationsvbox.setSpacing(5);

        locationstab.setContent(locationspane);
        locationstab.setClosable(false);

        locationspane.getChildren().add(locationsvbox);
        locationspane.getChildren().add(LocationsTableView);

        locationstab.setContent(locationspane);
        locationstab.setClosable(false);
    }
    private void contextTableCreation() {
        //table configuration
        ContextClientIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ContextClientNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ContextClientMainOfficeColumn.setCellValueFactory(new PropertyValueFactory<>("MainOfficeAdress"));
        ContextClientContactNameColumn.setCellValueFactory(new PropertyValueFactory<>("ContactName"));
        ContextClientContactTelNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ContactTelNumber"));
        ContextClientContactEmailColumn.setCellValueFactory(new PropertyValueFactory<>("ContactEmail"));

        ContextClientsTableView.getColumns().clear();
        ContextClientsTableView.getColumns().addAll(ContextClientIDColumn, ContextClientMainOfficeColumn, ContextClientContactNameColumn,
                ContextClientContactTelNumberColumn, ContextClientContactEmailColumn, ContextClientNameColumn);
        ContextClientsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        contextTableFilter = TableFilter.forTableView(ContextClientsTableView).apply();

        Pane pane = new Pane(ContextClientsTableView);
        pane.setBorder(new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        pane.setPadding(new Insets(2, 2, 2, 2));
        contextstage.setScene(new Scene(pane));
        contextstage.initStyle(StageStyle.UNDECORATED);
        contextstage.initOwner(mainStage);

        ContextClientsTableView.prefWidthProperty().bind(contextstage.widthProperty());
        ContextClientsTableView.prefHeightProperty().bind(contextstage.heightProperty());
    }

    private void actionHandling() {
        clientsActionHandling();
        locationsActionHandling();
    }
    private void clientsActionHandling() {
        InsertClient.setOnMouseClicked(this::onCreateNewClient);
        DeleteClient.setOnMouseClicked(this::onDeleteClient);
        UpdateClient.setOnMouseClicked(this::onUpdateClient);

        ClientsTableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<ClientDTO>) c -> {
            try {
                if (!c.getList().isEmpty()) {
                    ClientDTO selected = c.getList().get(0);
                    IDClientsLabel.setText(String.valueOf(selected.getID()));
                    nameField.setText(selected.getName());
                    MainOfficeAdressField.setText(selected.getMainOfficeAdress());
                    ContactNameField.setText(selected.getContactName());
                    ContactTelNumberField.setText(selected.getContactTelNumber());
                    ContactEmailField.setText(selected.getContactEmail());
                }
            } catch (Exception e) {
                showErrorWindowForException("Error reading selected client", e);
            }
        });
    }
    private void locationsActionHandling() {
        InsertLocation.setOnMouseClicked(this::onCreateLocation);
        DeleteLocation.setOnMouseClicked(this::onDeleteLocation);
        UpdateLocation.setOnMouseClicked(this::onUpdateLocation);

        LocationsTableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<ClientLocationsDTO>) c -> {
            try {
                if (!c.getList().isEmpty()) {
                    ClientLocationsDTO selected = c.getList().get(0);

                    LocationIDLabel.setText(String.valueOf(selected.getID()));
                    LocationclientIDField.setText(String.valueOf(selected.getClientID()));
                    LocationnameField.setText(selected.getLocationName());
                    LocationAdressField.setText(selected.getLocationAdress());
                    LocationContactNameField.setText(selected.getContactName());
                    LocationContactTelNumberField.setText(selected.getContactTelNumber());
                    LocationContactEmailField.setText(selected.getContactEmail());

                    clientService.findById(selected.getClientID()).ifPresent(selectedClient -> {
                        LocationclientIDField.setText(String.valueOf(selectedClient.getID()));
                        contextINameLabel.setText(selectedClient.getName());
                        contextOfficeAddressLabel.setText(selectedClient.getMainOfficeAdress());
                        ContextContactNumLabel.setText(selectedClient.getContactName());
                        ContextTelNumberLabel.setText(selectedClient.getContactTelNumber());
                        ContextEmailLabel.setText(selectedClient.getContactEmail());
                    });

                }
            } catch (Exception e) {
                showErrorWindowForException("Error reading selected location", e);
            }
        });

        LocationclientIDField.setOnMouseClicked(action -> {
            ContextClientsList = FXCollections.observableArrayList(Lists.newArrayList(clientService.findAll()));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ContextClientsTableView.getItems().clear();
                    ContextClientsTableView.setItems(ContextClientsList);
                    contextstage.setWidth(900);
                    contextstage.setHeight(500);
                    contextstage.setX(action.getScreenX());
                    contextstage.setY(action.getScreenY());
                    contextstage.setResizable(true);
                    contextstage.show();
                }
            });
        });

        mainStage.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (contextstage.isShowing()) {
                contextstage.close();
            }
        });

        ContextClientsTableView.setOnMouseClicked(action -> {
            if (action.getClickCount() == 2) {
                ClientDTO selected = ContextClientsTableView.getSelectionModel().getSelectedItem();
                LocationclientIDField.setText(String.valueOf(selected.getID()));
                contextINameLabel.setText(selected.getName());
                contextOfficeAddressLabel.setText(selected.getMainOfficeAdress());
                ContextContactNumLabel.setText(selected.getContactName());
                ContextTelNumberLabel.setText(selected.getContactTelNumber());
                ContextEmailLabel.setText(selected.getContactEmail());

                contextstage.close();
            }
        });
    }



    @Async
    private void onUpdateLocation(MouseEvent mouseEvent) {
        try {
            if ((LocationclientIDField.getText().length() > 0) && (LocationnameField.getText().length() > 0) && (LocationAdressField.getText().length() > 0)
                    && (LocationContactNameField.getText().length() > 0) && (LocationContactTelNumberField.getText().length() > 0)
                    && (LocationContactEmailField.getText().length() > 0)) {
                ClientLocationsDTO location = new ClientLocationsDTO(Long.parseLong(LocationIDLabel.getText()), Long.parseLong(LocationclientIDField.getText()),
                        LocationnameField.getText(), LocationAdressField.getText(), LocationContactNameField.getText(), LocationContactTelNumberField.getText(),
                        LocationContactEmailField.getText());

                clientService.saveLocation(location);

                update();

            } else {
                showErrorWindow("Error updating data", "Please check entered data .. No possible empty fields");
            }

        } catch (Exception e) {
            showErrorWindowForException("Error updating location", e);
        }
    }

    @Async
    private void onDeleteLocation(MouseEvent mouseEvent) {
        try {
            clientService.deleteById(Long.parseLong(LocationIDLabel.getText()));
            update();
        } catch (Exception e){
            showErrorWindowForException("Error deleting location", e);
        }
    }

    @Async
    private void onCreateLocation(MouseEvent mouseEvent) {
        try {
            if ((LocationclientIDField.getText().length() > 0) && (LocationnameField.getText().length() > 0) && (LocationAdressField.getText().length() > 0)
                    && (LocationContactNameField.getText().length() > 0) && (LocationContactTelNumberField.getText().length() > 0)
                    && (LocationContactEmailField.getText().length() > 0)) {
                ClientLocationsDTO location = new ClientLocationsDTO(Long.parseLong(LocationclientIDField.getText()), LocationnameField.getText(),
                        LocationAdressField.getText(), LocationContactNameField.getText(), LocationContactTelNumberField.getText(),
                        LocationContactEmailField.getText());

                clientService.saveLocation(location);
                update();
            } else {
                showErrorWindow("Error inserting data", "Please check entered data .. No possible empty fields");
            }
        } catch (Exception e) {
            showErrorWindowForException("Error inserting location", e);
        }
    }


    @Async
    private void onUpdateClient(MouseEvent mouseEvent) {
        if ((nameField.getText().length() > 0) && (MainOfficeAdressField.getText().length() > 0) && (ContactNameField.getText().length() > 0)
                && (ContactTelNumberField.getText().length() > 0) && (ContactEmailField.getText().length() > 0)) {

            ClientDTO client = new ClientDTO(Long.parseLong(IDClientsLabel.getText()), nameField.getText(), MainOfficeAdressField.getText(),
                    ContactNameField.getText(), ContactTelNumberField.getText(), ContactEmailField.getText());
            clientService.save(client);
        } else {
            showErrorWindow("Error updating data", "Please check entered data .. No possible empty fields");
        }
    }

    @Async
    private void onDeleteClient(MouseEvent mouseEvent) {
        try {
            clientService.deleteById(Long.parseLong(IDClientsLabel.getText()));
            clientService.deleteByClientName(Long.parseLong(IDClientsLabel.getText()));

            update();
        }catch (Exception e){
            showErrorWindowForException("Error deleting client", e);
        }
    }

    @Async
    private void onCreateNewClient(MouseEvent mouseEvent) {
        try {
            if ((nameField.getText().length() > 0) && (MainOfficeAdressField.getText().length() > 0) && (ContactNameField.getText().length() > 0)
                    && (ContactTelNumberField.getText().length() > 0) && (ContactEmailField.getText().length() > 0)) {
                ClientDTO client = new ClientDTO(nameField.getText(), MainOfficeAdressField.getText(), ContactNameField.getText(),
                        ContactTelNumberField.getText(), ContactEmailField.getText());
                clientService.save(client);
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
        List<ClientDTO> dataBaseList = clientService.findAll();
        List<ClientLocationsDTO> locationsDataBaseList = clientService.findAllLocations();
        Platform.runLater(() -> {
            getClientsList().removeAll(
                    dataBaseList
                            .stream()
                            .filter(item -> !getClientsList().contains(item))
                            .collect(this::getClientsList, ObservableList::add, ObservableList::addAll)
                            .stream()
                            .filter(tableListItem -> dataBaseList.stream().noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
                            .collect(Collectors.toList()));

            getLocationsList().removeAll(
                    locationsDataBaseList
                            .stream()
                            .filter(item -> !getLocationsList().contains(item))
                            .collect(this::getLocationsList, ObservableList::add, ObservableList::addAll)
                            .stream()
                            .filter(tableListItem -> locationsDataBaseList.stream().noneMatch(dataBaseItem -> dataBaseItem.equals(tableListItem)))
                            .collect(Collectors.toList()));
        });
    }

    public Node getTabContainer() {
        return root;
    }

    private ObservableList<ClientDTO> getClientsList() {
        return ClientsList;
    }
    private ObservableList<ClientLocationsDTO> getLocationsList() {
        return locationsList;
    }

    protected void showErrorWindow(String header, String content){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.getDialogPane().setMaxWidth(500);
            alert.initOwner(mainStage);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.show();
        });
    }
    protected void showErrorWindowForException(String header, Throwable e) {
        Platform.runLater(() -> {
            ExceptionDialog exceptionDialog = new ExceptionDialog(e);
            exceptionDialog.setHeaderText(header);
            exceptionDialog.getDialogPane().setMaxWidth(500);
            exceptionDialog.initOwner(mainStage);
            exceptionDialog.initModality(Modality.WINDOW_MODAL);
            exceptionDialog.show();

        });
    }
}
