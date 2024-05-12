package fxml;

import body.bodyAdminController;
import body.bodyCustomerController;
import engine.Engine;
import header.headerController;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class mainViewController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button loadFileButton;
    @FXML
    private Button increaseTimeButton;
    @FXML private VBox headerComponent;
    @FXML private headerController headerComponentController;
    @FXML private AnchorPane bodyAdminComponent;
    @FXML private bodyAdminController bodyAdminComponentController;
    @FXML private TabPane bodyCustomerComponent;
    @FXML private bodyCustomerController bodyCustomerComponentController;
    @FXML private StackPane stackPane;
  //  private TabPane bodyCustomerComponent;
    private IntegerProperty currentTime;
    private SimpleStringProperty selectedFileProperty;
    private SimpleStringProperty currentTimeStringProperty;
    private Boolean isFileLoaded;
    private Engine engine;
    private Stage primaryStage;

    public mainViewController(){

        isFileLoaded=false;

    }

    public void setEngine(Engine engine){
        this.engine=engine;
        // currentTime.set(this.engine.getCurrentTime());
        bodyAdminComponentController.setEngine(engine);
        bodyCustomerComponentController.setEngine(engine);
        headerComponentController.setEngine(engine);
    }
    public void setBodyAdminController(bodyAdminController loanscontroller){
        this.bodyAdminComponentController=loanscontroller;
        this.bodyAdminComponentController.setMainController(this);

    }

    public Boolean getFileLoadedStatus() {
        return isFileLoaded;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    private void initialize(){
        // CurrentTimeLabel.textProperty().bind(currentTime.asString());
//        filePathLabel.textProperty().bind(this.selectedFileProperty);
        headerComponentController.setMainController(this);
        bodyAdminComponentController.setMainController(this);
        bodyCustomerComponent.setVisible(false);
        bodyCustomerComponentController.setMainController(this);
        //bodyCustomerComponentController.setVisible(false);
//        try {
//            bodyAdminComponent = loadScene("/body/bodyAdmin.fxml");
//            bodyCustomerComponent =  loadScene("/body/bodyCustomer.fxml");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        };



    }
//    private Parent loadScene(String sc) throws IOException {
//        return FXMLLoader.load(getClass().getResource(sc));
//    }
//    @FXML
//    void loadFileButtonAction(ActionEvent event) {
//        if(headerComponentController.loadFile(primaryStage)) {
//            increaseTimeButton.setDisable(false);
//           // tableComponentController.fillTable(tableComponent);
////        FileChooser fileChooser=new FileChooser();
////        fileChooser.setTitle("choose a xml file");
////        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml file", "*.xml"));
////        File selectedFile = fileChooser.showOpenDialog(primaryStage);
////                if (selectedFile == null) {
////            return;
////        }
////
////        String absolutePath = selectedFile.getAbsolutePath();
////        selectedFileProperty.set(absolutePath);
////       if (engine.loadFromXml(absolutePath)) {
////           isFileLoaded = true;
////           currentTimeStringProperty.bind(Bindings.concat("CurrentTime: " ,Bindings.format("%,d",currentTime)));
////           CurrentTimeLabel.textProperty().bind(currentTimeStringProperty);
////        if(headerComponentController.loadFile(primaryStage)) {
////            increaseTimeButton.setDisable(false);
////            tableComponentController.fillTable(tableComponent);
//        }
//
//    }
   public void loadFile(){
        if(headerComponentController.loadFile(primaryStage)) {
       bodyAdminComponentController.turnOnIncreaseTimeButton();
       bodyAdminComponentController.fillTables();
        // tableComponentController.fillTable(tableComponent);
//        FileChooser fileChooser=new FileChooser();
//        fileChooser.setTitle("choose a xml file");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml file", "*.xml"));
//        File selectedFile = fileChooser.showOpenDialog(primaryStage);
//                if (selectedFile == null) {
//            return;
//        }
//
//        String absolutePath = selectedFile.getAbsolutePath();
//        selectedFileProperty.set(absolutePath);
//       if (engine.loadFromXml(absolutePath)) {
//           isFileLoaded = true;
//           currentTimeStringProperty.bind(Bindings.concat("CurrentTime: " ,Bindings.format("%,d",currentTime)));
//           CurrentTimeLabel.textProperty().bind(currentTimeStringProperty);
//        if(headerComponentController.loadFile(primaryStage)) {
//            increaseTimeButton.setDisable(false);
//            tableComponentController.fillTable(tableComponent);
    }

   }
    //  isFileSelected.set(true);


//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select words file");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));
//        File selectedFile = fileChooser.showOpenDialog(primaryStage);
//        if (selectedFile == null) {
//            return;
//        }
//
//        String absolutePath = selectedFile.getAbsolutePath();
//        selectedFileProperty.set(absolutePath);
//        isFileSelected.set(true);
//    }

//    @FXML
//    void IncreaseTimeButtonAction(ActionEvent event) {
//
//        headerComponentController.increaseTime();
//
//    }
    public void  increaseTime(){
        headerComponentController.increaseTime();
    }
    public void switchToAdmin(){
        bodyAdminComponent.toFront();
        bodyAdminComponent.setVisible(true);
        bodyAdminComponentController.updateData();
        bodyCustomerComponent.setVisible(false);
    }
    public void switchToCustomer(String userName){
        bodyCustomerComponent.toFront();
        bodyCustomerComponentController.setCustomer(userName);
        bodyCustomerComponent.setVisible(true);
        bodyAdminComponent.setVisible(false);
        bodyCustomerComponentController.cleanAll();
    }
    public  void fillCustomerTables(String name){
        bodyCustomerComponentController.fillTables(name);


    }
    public void changeStyle(String style){
     borderPane.getStylesheets().clear();
     borderPane.getStylesheets().add(getClass().getResource(style).toExternalForm());
    }


}
