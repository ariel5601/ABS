package body.assign.table;

import engine.Engine;
import fxml.mainViewController;
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

public class matchingLoansTableController {

    @FXML
    private TableView<Loan> matchingLoansTable;

    @FXML
    private TableColumn<Loan, String> idColumn;

    @FXML
    private TableColumn<Loan, Integer> amountColumn;

    @FXML
    private TableColumn<Loan, Boolean> selectColumn;
    private Engine engine;
    private fxml.mainViewController mainViewController;
    private List<Loan> loanList;

    @FXML
    private TableColumn<Loan,String> categoryColumn;

    @FXML
    private TableColumn<Loan, Integer> timeBetweenPaysColumn;

    @FXML
    private TableColumn<Loan, Integer> interestColumn;

    @FXML
    private TableColumn<Loan, Integer> returnAmountColumn;

    @FXML
    private TableColumn<Loan, Loan.Status> statusColumn;


    public matchingLoansTableController(){


    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setMainViewController(fxml.mainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @FXML
    private void initialize(){

        idColumn.setCellValueFactory(new PropertyValueFactory<Loan,String>("id"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanStartAmount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Loan,String>("category"));
        selectColumn.setCellValueFactory(new PropertyValueFactory<Loan,Boolean>("check"));
        selectColumn.setCellFactory(column -> new CheckBoxTableCell());
        timeBetweenPaysColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanTimeBetweenEachPay"));
        interestColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanInterest"));
        returnAmountColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanFinalAmount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Loan,Loan.Status>("loanStatus"));


    }
    public  void fillTable(String userName){
//                if(mainViewController.getFileLoadedStatus()) {
        if(loanList!=null) {
            ObservableList<Loan> loans = FXCollections.observableList(loanList);
//       List<Value> list = new ArrayList<Value>(map.values());
            // loansTable.getItems().addAll(loans);
            matchingLoansTable.setVisible(true);
            matchingLoansTable.setItems(loans);
        }
//        }
    }
    public void updateData(){
        matchingLoansTable.refresh();
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
    }
    public void cleanTable(){

        matchingLoansTable.getItems().clear();
        matchingLoansTable.refresh();
    }
}

