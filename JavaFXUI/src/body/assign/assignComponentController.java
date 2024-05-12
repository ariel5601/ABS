package body.assign;

import body.assign.table.findLoansTask;
import body.assign.table.matchingLoansTableController;
import engine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import loan.Loan;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class assignComponentController {

    @FXML
    private TableView<Loan> matchingLoansTable;
    @FXML
    private matchingLoansTableController matchingLoansTableController;
    @FXML
    private Button searchLoansButton;
    @FXML
    private Button chooseLoansButton;
    @FXML
    private TextField amountTF;
    @FXML
    private TextField interestTF;
    @FXML
    private TextField timeTF;
    @FXML
    private TextField minNumberOfLoansTF;
    @FXML
    private TextField minPercentTF;
    @FXML
    private ComboBox<String> categoryCB;
    @FXML
    private Label amountErrorLabel;
    @FXML
    private Label interestErrorLabel;
    @FXML
    private Label timeErrorLabel;
    @FXML
    private Label loansNumberErrorLabel;
    @FXML
    private Label minPercentErrorLabel;
    @FXML
    private Label noAmountEnteredError;
    @FXML
    private Label progressMassageLabel;
    @FXML
    private ProgressBar findLoansProgressBar;
    private Engine engine;
    private fxml.mainViewController mainViewController;
    private ObservableSet<String> categoryList;
    private String customerName;
    private List<Loan> matchingLoans;
     private int amount;
     private int interest;
     private int time;
     private int minNumberOfLoans;
     private int minPercent;
    private boolean isAmountEntered;
    public assignComponentController(){
         amount=0;
         interest=0;
         time=0;
         minNumberOfLoans=10;
         minPercent=0;
         isAmountEntered=true;

    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        matchingLoansTableController.setEngine(engine);

    }

    public void setMainViewController(fxml.mainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }
    public void fillTable(String name){
        customerName=name;
        //(FXCollections.observableList(m.getList()));
        ArrayList<String> list=  new ArrayList<>( engine.getCategories());
        list.add("no category");
        categoryCB.setItems((FXCollections.observableList(list)));
        categoryCB.getSelectionModel().select("no category");


        matchingLoansTableController.fillTable(name);

        //categoryCB.setItems(categoryList);
       // customersList= FXCollections.observableArrayList("Admin");
    }
    @FXML
    private void initialize(){
        chooseLoansButton.setDisable(true);
    }
    @FXML
    void searchLoansAction(ActionEvent event){
        amountErrorLabel.setVisible(false);
        interestErrorLabel.setVisible(false);
        loansNumberErrorLabel.setVisible(false);
        minPercentErrorLabel.setVisible(false);
        timeErrorLabel.setVisible(false);
        noAmountEnteredError.setVisible(false);


        //= Integer.parseInt(amountToAddField.getText());
        //amountToAdd = Integer.parseInt(amountToAddField.getText());
        try {
             amount = Integer.parseInt(amountTF.getText());
            if(engine.findCustomer(customerName).getBalance()<amount)
            {

                amountErrorLabel.setText("amount is higher than user's balance");
                isAmountEntered=false;
                this.cleanAll();
                amountErrorLabel.setVisible(true);
            }
            else {
                chooseLoansButton.setDisable(false);
                isAmountEntered=true;

            }

        }
        catch (NumberFormatException e){
             amountErrorLabel.setVisible(true);
             amountErrorLabel.setText("amount needs to be a number");
            isAmountEntered=false;
            this.cleanAll();

        }
        try {
             interest = Integer.parseInt(interestTF.getText());

        }
        catch (NumberFormatException e){
            interestErrorLabel.setVisible(true);
            interestErrorLabel.setText("interest needs to be a between 1-100, set to 0");
            interestErrorLabel.setTextFill(Color.RED);
            if(interestTF.getText().equals("")){
                interestErrorLabel.setText("no interest chosen set to 0");
                interestErrorLabel.setTextFill(Color.BLUE);
            }

        }
        try {
             time = Integer.parseInt(timeTF.getText());

        }
        catch (NumberFormatException e){
            timeErrorLabel.setVisible(true);
            timeErrorLabel.setText("time needs to be an integer number, set to 0");
            timeErrorLabel.setTextFill(Color.RED);
            if(timeTF.getText().equals("")){
                timeErrorLabel.setText("no time chosen set to 0");
                timeErrorLabel.setTextFill(Color.BLUE);
            }
        }
        try {
             minNumberOfLoans = Integer.parseInt(minNumberOfLoansTF.getText());

        }
        catch (NumberFormatException e){
            loansNumberErrorLabel.setVisible(true);
            loansNumberErrorLabel.setText("field needs to be a number");
            loansNumberErrorLabel.setTextFill(Color.RED);
            if(minNumberOfLoansTF.getText().equals("")){
                loansNumberErrorLabel.setText("no number of loans chosen");
                loansNumberErrorLabel.setTextFill(Color.BLUE);
            }
        }
        try {
             minPercent = Integer.parseInt(minPercentTF.getText());

        }
        catch (NumberFormatException e){
            minPercentErrorLabel.setVisible(true);
            minPercentErrorLabel.setText("percent needs to be a between 1-100, set to 100");
            minPercent=100;
            minPercentErrorLabel.setTextFill(Color.RED);
            if(minPercentTF.getText().equals("")){
                minPercentErrorLabel.setText("no min percent of loan chosen, set to 100");
                minPercentErrorLabel.setTextFill(Color.BLUE);
            }

        }
         String category = categoryCB.getValue();
        if(category==null)
        {
            category="no category";
        }
        if(isAmountEntered) {
            // matchingLoans = engine.findMatchingLoans(category, amount, interest, time,minNumberOfLoans, customerName);
            findLoansTask task=new findLoansTask(engine,category,customerName,interest,time,minNumberOfLoans);
            findLoansProgressBar.setVisible(true);
             findLoansProgressBar.progressProperty().bind(task.progressProperty());
             progressMassageLabel.textProperty().bind(task.messageProperty());
             new Thread(task).start();
            task.setOnSucceeded(e -> {
                matchingLoans=task.getValue();
                matchingLoansTableController.setLoanList(matchingLoans);
                matchingLoansTableController.fillTable(customerName);
                matchingLoansTableController.updateData();
                // this code executed when task is successfully completed
                // this is executed on the FX Application Thread, so you can
                // safely modify the UI
            });
//            matchingLoansTableController.setLoanList(matchingLoans);
//            matchingLoansTableController.fillTable(customerName);
//            matchingLoansTableController.updateData();
        }
        else {
            noAmountEnteredError.setText("please choose amount to invest");
            noAmountEnteredError.setVisible(true);
        }

    }
    public void cleanAll() {
        if(matchingLoans!=null) {
            for (Loan loan : matchingLoans) {
                loan.setCheck(false);
            }
        int amount = 0;
        int interest = 0;
        int time = 0;
        int minNumberOfLoans = 10;
        int minPercent = 0;
        boolean isAmountEntered = true;
        amountErrorLabel.setVisible(false);
        interestErrorLabel.setVisible(false);
        loansNumberErrorLabel.setVisible(false);
        minPercentErrorLabel.setVisible(false);
        timeErrorLabel.setVisible(false);
        noAmountEnteredError.setVisible(false);
            chooseLoansButton.setDisable(true);
        matchingLoansTableController.cleanTable();

        }
    }
    @FXML
    void chooseSelectedLoansAction(ActionEvent event){
        List<Loan> chosenLoans = new ArrayList<>();
        for(Loan loan:matchingLoans){
            if(loan.checkProperty().get()){
                chosenLoans.add(loan);
            }
        }
        engine.divideMoneyBetweenLoans(chosenLoans,amount,minPercent,customerName,engine.getCurrentTime());
        this.cleanAll();

    }

}
