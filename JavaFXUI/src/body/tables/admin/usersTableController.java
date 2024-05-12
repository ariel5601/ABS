package body.tables.admin;

import body.bodyAdminController;
import body.bodyCustomerController;
import customer.Customer;
import engine.Engine;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import loan.Loan;

import java.util.ArrayList;
import java.util.List;

public class usersTableController {

    @FXML
    private TableView<Customer> usersTable;
    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, Integer>  balanceColumn;;
    @FXML
    private TableColumn<Customer, Integer> loansTakenNewColumn;

    @FXML
    private TableColumn<Customer, Integer> loansTakenPendingColumn;

    @FXML
    private TableColumn<Customer, Integer> loansTakenRiskColumn;

    @FXML
    private TableColumn<Customer, Integer> loansTakenFinishedColumn;

    @FXML
    private TableColumn<Customer, Integer> loansGivenNewColumn;

    @FXML
    private TableColumn<Customer, Integer> loansGivenPendingColumn;

    @FXML
    private TableColumn<Customer, Integer> loansGivenRiskColumn;

    @FXML
    private TableColumn<Customer, Integer> loansGivenFinishedColumn;



    private bodyAdminController parentBodyController;
    private Engine engine;
    private ObservableList<Customer> customers;
    public usersTableController(){

    }
    public void setParentController(bodyAdminController bodyController){
        this.parentBodyController=bodyController;
    }

    @FXML
    private void initialize(){

        //nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        nameColumn.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getName()));
//        loansTakenNumberColumn.setCellValueFactory((p) -> new SimpleIntegerProperty(p.getValue().loansTakenStatusNew());
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        loansTakenNewColumn.setCellValueFactory(new PropertyValueFactory<>("loansTakenStatusNew"));
        loansTakenPendingColumn.setCellValueFactory(new PropertyValueFactory<>("loansTakenStatusPending"));
        loansTakenRiskColumn.setCellValueFactory(new PropertyValueFactory<>("loansTakenStatusRisk"));
        loansTakenFinishedColumn.setCellValueFactory(new PropertyValueFactory<>("loansTakenStatusFinished"));
        loansGivenNewColumn.setCellValueFactory(new PropertyValueFactory<>("loansGivenStatusNew"));
        loansGivenPendingColumn.setCellValueFactory(new PropertyValueFactory<>("loansGivenStatusPending"));
        loansGivenRiskColumn.setCellValueFactory(new PropertyValueFactory<>("loansGivenStatusRisk"));
        loansGivenFinishedColumn.setCellValueFactory(new PropertyValueFactory<>("loansGivenStatusFinished"));


    }
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    public void fillTable(){

        List<Customer> customersList = new ArrayList<>(engine.getAllCustomers().values());
         customers = FXCollections.observableList(customersList);
//       List<Value> list = new ArrayList<Value>(map.values());
//        takenLoansTable.getItems().addAll(loans);
        usersTable.setVisible(true);
        usersTable.setItems(customers);

//
    }
    public void updateData(){
        usersTable.refresh();
    }
}

