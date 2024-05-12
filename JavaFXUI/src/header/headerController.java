package header;

import engine.Engine;
import exceptions.CategoryErrorException;
import exceptions.LoanPaymentTimeException;
import exceptions.LoanUserNotFoundException;
import exceptions.UserAlreadyExistException;
import fxml.mainViewController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import loan.Loan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class headerController {

    @FXML
    private Label filePathLabel;

    @FXML
    private Label CurrentTimeLabel;
    @FXML
    private Label fileErrorLabel;
    @FXML
    private ComboBox<String> currentUserBox;
    @FXML
    private ComboBox<String> styleComboBox;
    @FXML
    private HBox headerHBox;

    private SimpleIntegerProperty currentTime;
    private SimpleStringProperty selectedFileProperty;
    private SimpleStringProperty currentTimeStringProperty;
    private Boolean isFileLoaded;
    private Engine engine;
    private mainViewController mainViewController;
    private ObservableList<String> customersList;
    private ObservableList<String> stylesList;



    public headerController(){
        currentTime=new SimpleIntegerProperty(1);
        customersList= FXCollections.observableArrayList("Admin");
        stylesList=FXCollections.observableArrayList("Style1" ,"Style2","Style3");
        selectedFileProperty=new SimpleStringProperty();
        currentTimeStringProperty=new SimpleStringProperty();
        isFileLoaded=false;

    }
    @FXML
    private void initialize(){
        // CurrentTimeLabel.textProperty().bind(currentTime.asString());
        filePathLabel.textProperty().bind(this.selectedFileProperty);
        currentUserBox.setItems(customersList);
        currentUserBox.getSelectionModel().select(0);
        CurrentTimeLabel.textProperty().bind(currentTimeStringProperty);
        styleComboBox.setItems(stylesList);
        styleComboBox.getSelectionModel().select(0);


    }

    public void increaseTime(){
        engine.advanceTime();
        currentTime.set(engine.getCurrentTime());

    }
    public boolean loadFile(Stage primaryStage){
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("choose a xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml file", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return false;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        selectedFileProperty.set(absolutePath);
        try {
            if (engine.loadFromXml(absolutePath)) {
                fileErrorLabel.setText("");
                isFileLoaded = true;
                if (customersList.size() > 1) {
                    customersList.remove(1, customersList.size());
                }
                currentTime.set(engine.getCurrentTime());
                currentTimeStringProperty.bind(Bindings.concat("CurrentTime: ", Bindings.format("%,d", currentTime)));
                CurrentTimeLabel.textProperty().bind(currentTimeStringProperty);
                List<String> tempList = new ArrayList<>(engine.getAllCustomers().keySet());
                customersList.addAll(tempList);
//            List<Loan> loanList = new ArrayList<>(engine.getAllLoans().values());
//            ObservableList<Loan> loans = FXCollections.observableList(loanList);
            }
        }catch (CategoryErrorException e){
            fileErrorLabel.setText("one of the loans is trying to assign to a category that doesn't exist\n" +
                    "please try loading another file");
            isFileLoaded=false;
        }
        catch (LoanPaymentTimeException e){
            fileErrorLabel.setText("one of the loans time between each pay doesn't fit with the loan total time \n" +
                    "please try loading another file");
            isFileLoaded=false;
        }
        catch (LoanUserNotFoundException e){
            fileErrorLabel.setText("one of the loans user doesn't exist\n" +
                    "please try loading another file");
            isFileLoaded=false;
        }
        catch (UserAlreadyExistException e){
            fileErrorLabel.setText("two different users have to same name\n" +
                    "please try loading another file");
            isFileLoaded=false;
        }

        return isFileLoaded;
    }
    public  void setMainController(mainViewController mainViewController){
        this.mainViewController=mainViewController;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    @FXML
    void comboAction(ActionEvent event) {
         if(currentUserBox.getValue().equals("Admin"))
         {
             mainViewController.switchToAdmin();
         }
         else
         {
             mainViewController.switchToCustomer(currentUserBox.getValue());

             mainViewController.fillCustomerTables(currentUserBox.getValue());
         }
    }
    @FXML
    void styleComboAction(ActionEvent event){
        if(styleComboBox.getValue().equals("Style1")){
             mainViewController.changeStyle("style1.css");
        }
        else if((styleComboBox.getValue().equals("Style2"))){
            mainViewController.changeStyle("style2.css");
        }
        else if((styleComboBox.getValue().equals("Style3"))){
            mainViewController.changeStyle("style3.css");

        }
    }
    public String getCurrentUser(){
        return currentUserBox.getValue();
    }
}


