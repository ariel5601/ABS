package body;

import customer.Customer;
import engine.Engine;
import fxml.mainViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import body.tables.admin.usersTableController;
import loan.Loan;
import body.tables.admin.loansAdminTableController;

public class bodyAdminController {
    @FXML
    private Button increaseTimeButton;

    @FXML
    private Button loadFileButton;

    @FXML private TableView<Loan> loansTableComponent;
    @FXML private loansAdminTableController loansTableComponentController;
    @FXML private TableView<Customer> customerTableView;
    @FXML private usersTableController customerTableViewController;
    private Engine engine;
    private mainViewController mainController;


    @FXML
    public void IncreaseTimeButtonAction(ActionEvent event) {

        mainController.increaseTime();
    }
    @FXML
    private void initialize(){

       // increaseTimeButton.disableProperty().bind(mainController.loadFile(););
    }

    @FXML
    void loadFileButtonAction(ActionEvent event) {
        mainController.loadFile();
    }
    public void setEngine(Engine engine){
        this.engine=engine;
        loansTableComponentController.setEngine(engine);
        customerTableViewController.setEngine(engine);
    }

    public void setMainController(mainViewController mainController) {
        this.mainController = mainController;
    }

    public void fillTables(){
        loansTableComponentController.fillTable();
        customerTableViewController.fillTable();
    }
    public void turnOnIncreaseTimeButton(){
        increaseTimeButton.setDisable(false);
    }
    public void updateData(){
        customerTableViewController.updateData();
        loansTableComponentController.updateData();

    }

}

