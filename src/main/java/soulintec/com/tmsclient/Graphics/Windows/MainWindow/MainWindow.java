package soulintec.com.tmsclient.Graphics.Windows.MainWindow;

import com.google.common.io.Resources;
import eu.hansolo.medusa.Clock;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.extern.log4j.Log4j2;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.StatusBar;
import org.controlsfx.dialog.ExceptionDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import soulintec.com.tmsclient.ApplicationContext;
import soulintec.com.tmsclient.Graphics.Windows.ClientsWindow.ClientView;
import soulintec.com.tmsclient.Graphics.Windows.DriversWindow.DriversView;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.mainWindowSubNodes.IconicButton;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.mainWindowSubNodes.WindowInterfaceMessages;
import soulintec.com.tmsclient.Graphics.Windows.MainWindow.mainWindowSubNodes.windowReferenceNode;
import soulintec.com.tmsclient.Graphics.Windows.MaterialsWindow.MaterialsView;
import soulintec.com.tmsclient.Graphics.Windows.TanksWindow.MainTanksWindow;
import soulintec.com.tmsclient.Graphics.Windows.TruckWindow.TruckView;

@Log4j2
@Component
public class MainWindow implements ApplicationListener<ApplicationContext.ApplicationListener> {

    private static Stage window;
    private BorderPane root = new BorderPane();
    private Scene scene;

    private ToolBar fastActionsBar = new ToolBar();
    private StatusBar statusbar = new StatusBar();

    private Image baseIcon = new Image(Resources.getResource("Icons/Soulintec.png").toString());
    private ImageView baseIconView = new ImageView(baseIcon);
    private VBox blankPane = new VBox();
    private Label mainLabel = new Label("Terminal management system\nSystem developed by SOULINTEC for integrated solutions");

    private StringProperty tempStringProperty = new SimpleStringProperty();

    private Clock clock = new Clock();

    private IconicButton Iconic = new IconicButton(Resources.getResource("Icons/maxminbuttons.png").toString());

    private windowReferenceNode logger = new windowReferenceNode(Resources.getResource("Icons/log.png").toString(), "Log", tempStringProperty);
    private windowReferenceNode products = new windowReferenceNode(Resources.getResource("Icons/stocks.png").toString(), "Products", tempStringProperty);
    private windowReferenceNode clients = new windowReferenceNode(Resources.getResource("Icons/clients.png").toString(), "Clients", tempStringProperty);
    private windowReferenceNode drivers = new windowReferenceNode(Resources.getResource("Icons/drivers.png").toString(), "Drivers", tempStringProperty);
    private windowReferenceNode tanks = new windowReferenceNode(Resources.getResource("Icons/tanks.png").toString(), "Tanks", tempStringProperty);
    private windowReferenceNode trucks = new windowReferenceNode(Resources.getResource("Icons/trucks.png").toString(), "Trucks", tempStringProperty);

    private Image loginImage = new Image(Resources.getResource("Icons/login.png").toString());
    private Image logoutimage = new Image(Resources.getResource("Icons/logout.png").toString());
    private Image dashboardimage = new Image(Resources.getResource("Icons/dashboard.png").toString());
    private Image exitimage = new Image(Resources.getResource("Icons/exit.png").toString());
    private ImageView loginview = new ImageView(loginImage);
    private ImageView logoutview = new ImageView(logoutimage);
    private ImageView dashboardview = new ImageView(dashboardimage);
    private ImageView exitview = new ImageView(exitimage);

    private Label currentUserLabel = new Label();

    private VBox buttonsview = new VBox();

    private Button logIn = new Button("Log in", loginview);
    private Button logOut = new Button("Log out", logoutview);
    private Button DashBoardButton = new Button("Dashboard", dashboardview);
    private Button exit = new Button("Exit", exitview);
    private boolean IconicStatus = true;

    private final double SIDE_BAR_COLLAPSED_HEIGHT = 60;
    private final double SIDE_BAR_EXPANDED_HEIGHT = 250;

    private final Boolean isEnableNotifications = false;
    private final int POPUP_DURATION = 30;
    private final int MAX_NUM_NOTIFICATIONS_LINES = 1;

    @Autowired
    MaterialsView materialsView;

    @Autowired
    ClientView clientView;



    @Autowired
    DriversView driversView;

    @Autowired
    MainTanksWindow mainTanksWindow;

    @Autowired
    TruckView truckView;

    private Notifications notifications;

    @Override
    public void onApplicationEvent(ApplicationContext.ApplicationListener event) {
        window = event.getStage();
        notifications = Notifications
                .create()
                .threshold(10, Notifications.create())
                .hideAfter(Duration.seconds(POPUP_DURATION))
                .position(Pos.CENTER)
                .owner(window);
        init();
        graphicsBuild();
        actionHandling();
    }

    private void graphicsBuild() {

        mainLabel.setStyle("-fx-font-weight:normal;-fx-font-style:italic;-fx-text-fill:Darkblue;-fx-font-size:20;");
        mainLabel.setAlignment(Pos.CENTER);
        mainLabel.setTextAlignment(TextAlignment.CENTER);

        blankPane.setAlignment(Pos.CENTER);
        blankPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        blankPane.getChildren().addAll(baseIconView, mainLabel);

        loginview.setFitWidth(20);
        logoutview.setFitWidth(20);
        dashboardview.setFitWidth(30);
        exitview.setFitWidth(20);
        loginview.setFitHeight(20);
        logoutview.setFitHeight(20);
        dashboardview.setFitHeight(25);
        exitview.setFitHeight(20);

        buttonsview.setMinWidth(SIDE_BAR_COLLAPSED_HEIGHT);
        buttonsview.setSpacing(0);
        buttonsview.setPadding(new Insets(20, 0, 20, 0));
        buttonsview.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonsview.getChildren().add(Iconic);
        buttonsview.getChildren().add(new Separator());
        buttonsview.getChildren().add(logger);
        buttonsview.getChildren().add(new Separator());
        buttonsview.getChildren().add(products);
        buttonsview.getChildren().add(clients);
        buttonsview.getChildren().add(drivers);
        buttonsview.getChildren().add(tanks);
        buttonsview.getChildren().add(trucks);

        buttonsview.setAlignment(Pos.BASELINE_LEFT);

        //buttons
        logIn.setPrefWidth(120);
        logOut.setPrefWidth(120);
        exit.setPrefWidth(120);
        DashBoardButton.setPrefWidth(200);

        logIn.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:black;-fx-font-size:18;");
        logOut.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:black;-fx-font-size:18;");
        exit.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:black;-fx-font-size:18;");
        DashBoardButton.setStyle("-fx-font-weight:bold;-fx-font-style:normal;-fx-text-fill:black;-fx-font-size:18;");

        currentUserLabel.setStyle("-fx-font-weight:normal;-fx-font-style:normal;-fx-text-fill:black;-fx-font-size:30;");
        currentUserLabel.setPrefWidth(1035);

        // add digital clock
        clock.setSkinType(Clock.ClockSkinType.TEXT);
        clock.setPrefHeight(40);
        clock.setRunning(true);

        fastActionsBar.getItems().addAll(logIn, logOut, new Separator(), DashBoardButton, new Separator(), exit, currentUserLabel, clock /*, clock */);
        fastActionsBar.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        fastActionsBar.setBorder(new Border(new BorderStroke(Color.CADETBLUE.darker(), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));

        root.setTop(fastActionsBar);
        root.setLeft(buttonsview);
        root.setBottom(statusbar);

        scene = new Scene(root);
//        scene.getStylesheets().add(Resources.getResource("Styles/MainWindowStyle.css").toString());

        window.setScene(scene);
        window.setWidth(1920);
        window.setHeight(1080);
        window.setMaximized(true);
        window.setTitle("Terminal management system");
        window.getIcons().add(new Image(Resources.getResource("Icons/splash.png").toString()));
        window.setMinWidth(1900);
        window.setX(1920);
        window.setAlwaysOnTop(true);

        try {
        window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void actionHandling() {
//        window.setOnCloseRequest(Event::consume);
        logIn.setOnMouseClicked(this::onLogIn);
        logOut.setOnMouseClicked(this::onLogOut);
        exit.setOnMouseClicked(action -> window.close());
        Iconic.setOnMouseClicked(action -> {
            IconicStatus = !IconicStatus;
            if (IconicStatus) {
                KeyValue widthValue = new KeyValue(buttonsview.prefWidthProperty(), SIDE_BAR_COLLAPSED_HEIGHT);
                KeyFrame frame = new KeyFrame(Duration.seconds(0.1), widthValue);
                Timeline timeline = new Timeline(frame);
                timeline.play();
                logger.setIconic(IconicStatus);
                products.setIconic(IconicStatus);
                clients.setIconic(IconicStatus);
                drivers.setIconic(IconicStatus);
                tanks.setIconic(IconicStatus);
                trucks.setIconic(IconicStatus);
            } else {
                KeyValue widthValue = new KeyValue(buttonsview.prefWidthProperty(), SIDE_BAR_EXPANDED_HEIGHT);
                KeyFrame frame = new KeyFrame(Duration.seconds(0.4), widthValue);
                Timeline timeline = new Timeline(frame);
                timeline.play();
                timeline.setOnFinished(a -> {
                    logger.setIconic(IconicStatus);
                    products.setIconic(IconicStatus);
                    clients.setIconic(IconicStatus);
                    drivers.setIconic(IconicStatus);
                    tanks.setIconic(IconicStatus);
                    trucks.setIconic(IconicStatus);
                });
            }
        });

        tanks.setOnIconClicked((String param) -> {
            root.setCenter(mainTanksWindow.getTabContainer());
            mainTanksWindow.update();
        });
        clients.setOnIconClicked((String param) -> {
            clientView.update();
            root.setCenter(clientView.getTabContainer());
        });
        drivers.setOnIconClicked((String param) -> {
            driversView.update();
            root.setCenter(driversView.getRoot());
        });
        products.setOnIconClicked((String param) -> {
            materialsView.update();
            root.setCenter(materialsView.getRoot());
        });
        trucks.setOnIconClicked((String param) -> {
            truckView.update();
            root.setCenter(truckView.getTabContainer());
        });
    }

    private void onLogIn(MouseEvent mouseEvent) {

    }
    private void onLogOut(MouseEvent mouseEvent) {

    }

    public Stage getInitialStage() {
        return window;
    }
    public StatusBar getStatusBar() {
        return statusbar;
    }

    public void init() {
        trucks.getWindowInterface().setValue(WindowInterfaceMessages.EnableMonitoring.name());
        tanks.getWindowInterface().setValue(WindowInterfaceMessages.EnableMonitoring.name());
        drivers.getWindowInterface().setValue(WindowInterfaceMessages.EnableMonitoring.name());
        clients.getWindowInterface().setValue(WindowInterfaceMessages.EnableMonitoring.name());
        products.getWindowInterface().setValue(WindowInterfaceMessages.EnableMonitoring.name());

    }



    public static void showErrorWindow(String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.getDialogPane().setMaxWidth(500);
            alert.initOwner(window);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initStyle(StageStyle.UTILITY);
            alert.show();
        });
    }
    public static void showErrorWindowForException(String header, Throwable e) {
        Platform.runLater(() -> {
            ExceptionDialog exceptionDialog = new ExceptionDialog(e);
            exceptionDialog.setHeaderText(header);
            exceptionDialog.getDialogPane().setMaxWidth(500);
            exceptionDialog.initOwner(window);
            exceptionDialog.initModality(Modality.APPLICATION_MODAL);
            exceptionDialog.initStyle(StageStyle.UTILITY);
            exceptionDialog.show();

        });
    }
}
