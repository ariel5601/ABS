package main;

import engine.Engine;
import fxml.mainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class Main extends Application {

    private Engine engine;

    @Override
    public void start(Stage primaryStage) throws Exception {
        engine = new Engine();
        //if you just want to load the FXML
//        Parent root = FXMLLoader.load(WelcomeFXML.class.getResource("welcome.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/body/tables/admin/loansAdminTableComponent.fxml");
       // fxmlLoader.setLocation(url);
        //TableView<Loan> tableView = fxmlLoader.load(url.openStream());
       // bodyAdminController adminController=fxmlLoader.getController();
        //if you want to load the FXML and get access to its controller
         fxmlLoader = new FXMLLoader();
         url = getClass().getResource("/fxml/mainView.fxml");
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load(url.openStream());
        mainViewController mainController = fxmlLoader.getController();
        //mainController.setbodyAdminController(loansTableController);
       // loansTableController.setMainController(mainController);
        mainController.setEngine(engine);
        primaryStage.setTitle("A.B.S");
        mainController.setPrimaryStage(primaryStage);

       // welcomeController.setModel(model);

        Scene scene = new Scene(root, 1400, 940);

        /*Button singInButton = (Button) scene.lookup("#signInButton");
        final Text actionTarget = (Text)scene.lookup("#actiontarget");
        singInButton.setOnAction(e -> {
            actionTarget.setText("Sign in button pressed!");
        });*/

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
