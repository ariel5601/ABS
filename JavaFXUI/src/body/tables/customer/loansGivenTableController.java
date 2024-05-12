package body.tables.customer;

import body.bodyCustomerController;
import engine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import loan.Loan;
import customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class loansGivenTableController {

    @FXML
    private TableView<Loan> givenLoansTable;

    @FXML
    private TableColumn<Loan, String> idColumn;
    @FXML
    private TableColumn<Loan, String> categoryColumn;
    @FXML
    private TableColumn<Loan, Integer> amountColumn;

    @FXML
    private TableColumn<Loan, Integer> interestColumn;

    @FXML
    private TableColumn<Loan, Integer> timeBetweenEachPayColumn;

    @FXML
    private TableColumn<Loan, Integer> returnAmountColumn;

    @FXML
    private TableColumn<Loan, Loan.Status> statusColumn;

    private bodyCustomerController parentBodyController;
    private Engine engine;
    public loansGivenTableController(){

    }
    public void setParentController(bodyCustomerController bodyController){
        this.parentBodyController=bodyController;
    }
    @FXML
    private void initialize(){

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        //  loanCategoryColumn.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getCategory()));

        amountColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanStartAmount"));
        interestColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanInterest"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Loan,Loan.Status>("loanStatus"));
        timeBetweenEachPayColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanTimeBetweenEachPay"));
        returnAmountColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanFinalAmount"));
        // statusColumn.setCellValueFactory(new PropertyValueFactory<Loan,Loan.Status>("loanStatus"));


    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    public void fillTable(String userName){

        List<Loan> loanList = new ArrayList<>(engine.getAllCustomers().get(userName).getLoansGiven().values());
        ObservableList<Loan> loans = FXCollections.observableList(loanList);
//       List<Value> list = new ArrayList<Value>(map.values());
//        takenLoansTable.getItems().addAll(loans);
        givenLoansTable.setVisible(true);
        givenLoansTable.setItems(loans);
//
    }
}
