package body;

import body.assign.assignComponentController;
import body.assign.table.matchingLoansTableController;
import body.tables.customer.addSceneController;
import body.tables.customer.loansTakenTableController;
import body.tables.customer.loansGivenTableController;
import body.tables.customer.paymentTableController;
import customer.Customer;
import engine.Engine;
import fxml.mainViewController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import loan.Loan;

import java.util.List;

public class bodyCustomerController {

    @FXML
    private TabPane customerTabPane;
    @FXML
    private Tab infoTab;
    @FXML
    private TableView<Loan> loansTakenTable;
    @FXML
    private TableView<Loan> loansTakenTableInPayTab;
    @FXML
    private loansTakenTableController loansTakenTableController;
    @FXML
    private loansTakenTableController loansTakenTableInPayTabController;
    @FXML
    private TableView<Loan> loansGivenTable;
    @FXML
    private loansGivenTableController loansGivenTableController;
    @FXML
    private AnchorPane assignComponent;
    @FXML
    private assignComponentController assignComponentController;
    @FXML
    private TableView<Loan> paymentsTable;
    @FXML
    private paymentTableController  paymentsTableController;
    @FXML
    private Label balanceLabel;
    private mainViewController mainController;
    private Engine engine;
    private String  customerName;
    private SimpleDoubleProperty balance;
    @FXML
    private Button addButton;
    @FXML
    private addSceneController sceneController;
    @FXML
    private ListView<String> actionListView;
    @FXML
    private ListView<String> paymentsInfoList;

    @FXML
    private Button removeBalanceButton;
    @FXML
    private Button returnButton;
    public bodyCustomerController(){
        balance=new SimpleDoubleProperty();
    }
    public void setMainController(mainViewController controller){
        this.mainController=controller;

    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        loansTakenTableController.setEngine(engine);
        loansGivenTableController.setEngine(engine);
        assignComponentController.setEngine(engine);
        loansTakenTableInPayTabController.setEngine(engine);
        paymentsTableController.setEngine(engine);
    }
    @FXML
    private void initialize(){
        balanceLabel.textProperty().bind(balance.asString());
        infoTab.setOnSelectionChanged(e -> {
            this.fillTables(customerName);
        });


    }


    public void fillTables(String userName){
        loansTakenTableController.fillTable(userName);
        loansGivenTableController.fillTable(userName);
        assignComponentController.fillTable(userName);
        loansTakenTableInPayTabController.fillTable(userName);
        paymentsTableController.fillTable(userName);
        balance.set(engine.findCustomer(customerName).getBalance());
        ObservableList<String> actionsDescriptionList = FXCollections.observableArrayList(engine.findCustomer(customerName).getActionsDescription());
        actionListView.setItems(actionsDescriptionList);
        //balanceLabel.textProperty().bind(balance.asString());
        ObservableList<String> paymentsDebtInfo= FXCollections.observableArrayList();
        for(Loan loan :engine.findCustomer(userName).getLoansTaken().values()){
            paymentsDebtInfo.addAll(loan.getDebtPaymnetsInfo());
        }
        paymentsInfoList.setItems(paymentsDebtInfo);

    }
    public void setCustomer(String customer){
        this.customerName=customer;
    }
    @FXML
    void addBalanceAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tables/customer/addScene.fxml"));

            Parent root1 = (Parent) fxmlLoader.load();
            sceneController=fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.showAndWait();
             int amount=sceneController.getAmountToAdd();
             engine.findCustomer(customerName).addBalance(amount,engine.getCurrentTime());
            balance.set(engine.findCustomer(customerName).getBalance());
            this.fillTables(customerName);
        } catch (NumberFormatException e){

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    void removeBalanceAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tables/customer/addScene.fxml"));

            Parent root1 = (Parent) fxmlLoader.load();
            sceneController=fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.showAndWait();
            int amount=sceneController.getAmountToAdd();
            engine.findCustomer(customerName).TakeMoneyFromAccount(amount,engine.getCurrentTime());
            balance.set(engine.findCustomer(customerName).getBalance());
            this.fillTables(customerName);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    public void cleanAll(){
        assignComponentController.cleanAll();
    }
    @FXML
    void infoTabSelect(ActionEvent event) {
        fillTables(customerName);
    }
    @FXML
    void returnAction(ActionEvent event) {
        double totalAmountToPay = 0;
        ObservableList<Loan> list = paymentsTable.getItems();
        for (Loan loan : list) {
            if (loan.payAllAmountProperty().get()) {
                totalAmountToPay = totalAmountToPay + loan.getLoanFinalAmount();
            } else if (loan.payCurrentDebtProperty().get()) {
                totalAmountToPay = totalAmountToPay + loan.getDebtAmount();
            } else {

            }
        }
        if (totalAmountToPay <= engine.findCustomer(customerName).getBalance()) {
            engine.findCustomer(customerName).TakeMoneyFromAccount((int)totalAmountToPay,engine.getCurrentTime());

            ///// if(!taker.TakeMoneyFromAccount(amountToPayToAllEveryTime , programTime)
            //
            for (Loan loan : list) {
                if(loan.payAllAmountProperty().get())
                {
                    for (Customer customer : loan.getAllLoanGivers().keySet()) {
                        double amountToPayToCustomer = ((((loan.getAllLoanGivers().get(customer) + (loan.getAllLoanGivers().get(customer) * (loan.getLoanInterest() / 100))))));
                        customer.addBalance(amountToPayToCustomer, engine.getCurrentTime());
                        loan.setLoanPaidAmount(loan.getLoanPaidAmount() + amountToPayToCustomer);
                        // before change comment // // // // // // // // // // //
                        //
                        //
                        //

                    }
                }
                else if(loan.payCurrentDebtProperty().get()) {
                    for (Customer customer : loan.getAllLoanGivers().keySet()) {
                        double amountToPayToCustomer = ((((loan.getAllLoanGivers().get(customer) + (loan.getAllLoanGivers().get(customer) * (loan.getLoanInterest() / 100))) / (loan.getLoanTotalTime() / loan.getLoanTimeBetweenEachPay()))));
                        customer.addBalance(amountToPayToCustomer * (loan.getDebtTimes() ), engine.getCurrentTime());
                        loan.setLoanPaidAmount(loan.getLoanPaidAmount() + amountToPayToCustomer * (loan.getDebtTimes() ));   // still needs to be checked
                        // before change comment // // // // // // // // // // //
                        //
                        //
                        //

                    }
                }
                if (loan.getLoanStatus().equals("RISK")) {
                    loan.setStatusToActive();
                    loan.setDebtAmount(0);
                    loan.setDebtTimes(0);
                }
                loan.CheckStatus(engine.getCurrentTime());
                //
            }
        }
    }




}
