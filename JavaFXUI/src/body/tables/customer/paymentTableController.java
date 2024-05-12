package body.tables.customer;

import body.bodyCustomerController;
import engine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import loan.Loan;

import java.util.ArrayList;
import java.util.List;

public class paymentTableController {

    @FXML
    private TableView<Loan> paymentsTable;

    @FXML
    private TableColumn<Loan, String> idColumn;

    @FXML
    private TableColumn<Loan, Integer> totalAmountToReturnColumn;

    @FXML
    private TableColumn<Loan, Integer> CurrentAmountToReturnColumn;

    @FXML
    private TableColumn<Loan, Loan.Status> statusColumn;

    @FXML
    private TableColumn<Loan, Boolean> returnCurrentColumn;

    @FXML
    private TableColumn<Loan, Boolean> returnAllColumn;
    private bodyCustomerController parentBodyController;
    private Engine engine;
    public paymentTableController(){

    }

    @FXML
    private void initialize(){

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        //  loanCategoryColumn.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getCategory()));

        totalAmountToReturnColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanFinalAmount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Loan,Loan.Status>("loanStatus"));
        CurrentAmountToReturnColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("debtAmount"));
        returnCurrentColumn.setCellValueFactory(new PropertyValueFactory<Loan,Boolean>("payCurrentDebt"));
        returnCurrentColumn.setCellFactory(column -> new CheckBoxTableCell());
        returnAllColumn.setCellValueFactory(new PropertyValueFactory<Loan,Boolean>("payAllAmount"));
        returnAllColumn.setCellFactory(column -> new CheckBoxTableCell());
//        selectColumn.setCellValueFactory(new PropertyValueFactory<Loan,Boolean>("check"));
//        selectColumn.setCellFactory(column -> new CheckBoxTableCell());



    }
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    public void fillTable(String userName){
        paymentsTable.getItems().clear();
        List<Loan> loanList = new ArrayList<>(engine.getAllCustomers().get(userName).getLoansTaken().values());
        ObservableList<Loan> loans = FXCollections.observableList(loanList);
//       List<Value> list = new ArrayList<Value>(map.values());
//        takenLoansTable.getItems().addAll(loans);
        for(Loan loan:loans){
            if(loan.getLoanStatus().equals("ACTIVE") || loan.getLoanStatus().equals("RISK")){
                paymentsTable.getItems().add(loan);
            }
        }

//
    }

}