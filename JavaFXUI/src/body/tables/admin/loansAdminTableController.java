package body.tables.admin;

import body.tables.customer.addSceneController;
import engine.Engine;
import fxml.mainViewController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import loan.Loan;

import java.util.ArrayList;
import java.util.List;

public class loansAdminTableController {

    @FXML
    private TableView<Loan> loansTable;
    @FXML
    private TableColumn<Loan, String> loanID;

    @FXML
   private TableColumn<Loan, String> loanTaker;

    @FXML
    private TableColumn<Loan, String> loanCategoryColumn;

    @FXML
    private TableColumn<Loan, Integer> loanAmountColumn;

    @FXML
    private TableColumn<Loan, Integer> loanInterestColumn;

    @FXML
    private TableColumn<Loan, Loan.Status> loanStatusColumn;
    @FXML
    private TableColumn<Loan ,Loan> actionColumn;
    @FXML
    private infoSceneController infoSceneController;

    private Engine engine;
    private mainViewController mainViewController;
    public loansAdminTableController(){

    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setMainViewController(fxml.mainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @FXML
    private void initialize(){

        loanID.setCellValueFactory(new PropertyValueFactory<Loan,String>("id"));
        loanTaker.setCellValueFactory(new PropertyValueFactory<Loan,String>("loanTaker"));
       loanCategoryColumn.setCellValueFactory(new PropertyValueFactory<Loan,String>("category"));
      //  loanCategoryColumn.setCellValueFactory((p) -> new SimpleStringProperty(p.getValue().getCategory()));

                loanAmountColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanStartAmount"));
        loanInterestColumn.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanInterest"));
        loanStatusColumn.setCellValueFactory(new PropertyValueFactory<Loan,Loan.Status>("loanStatus"));
        actionColumn.setCellValueFactory(cellData ->new SimpleObjectProperty<>(cellData.getValue()));
        actionColumn.setCellFactory(column -> new TableCell<Loan, Loan>() {
            private final Button button = new Button("more info");

            {
                button.setOnAction(e -> {
                    Loan loan = getItem();
                    newInfoScene(loan);
                    // do whatever you need with loan..
                });
            }

            @Override
            protected void updateItem(Loan item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });

//        // just provide the entire row as the value for cells in the actionColumn:
//        actionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
//
//        // cell factory which provides cell which display a button:
//        actionColumn.setCellFactory(column -> new TableCell<Symptom, Symptom>() {
//            private final Button button = new Button("Select Symptom");
//
//            {
//                button.setOnAction(e -> {
//                    Symptom symptom = getItem();
//                    // do whatever you need with symptom..
//                });
//            }
//
//        aColumn.setCellValueFactory(new PropertyValueFactory<Appointment,LocalDate>("date"));
//        Write:
//
//        aColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
//        loanAmount.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanStartAmount"));
//        loanInterest.setCellValueFactory(new PropertyValueFactory<Loan,Integer>("loanInterest"));
//        loanStatus.setCellValueFactory(new PropertyValueFactory<Loan,Loan.Status>("loanStatus"));
//        if(mainViewController.getFileLoadedStatus()) {
//            List<Loan> loanList = new ArrayList<Loan>(engine.getAllLoans().values());
//            ObservableList<Loan> loans = FXCollections.observableList(loanList);
////        List<Value> list = new ArrayList<Value>(map.values());
//            loansTable.setItems(loans);
//        }
                ;
    }
    public  void fillTable(){
//                if(mainViewController.getFileLoadedStatus()) {
            List<Loan> loanList = new ArrayList<>(engine.getAllLoans().values());
            ObservableList<Loan> loans = FXCollections.observableList(loanList);
//       List<Value> list = new ArrayList<Value>(map.values());
                   // loansTable.getItems().addAll(loans);
                    loansTable.setVisible(true);
                    loansTable.setItems(loans);
//        }
    }
    public void updateData(){
        loansTable.refresh();
    }
    public void newInfoScene(Loan loan){

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("infoScene.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                infoSceneController=fxmlLoader.getController();
                infoSceneController.showInfo(loan);
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.showAndWait();

               // engine.findCustomer(customerName).addBalance(amount,engine.getCurrentTime());
               // balance.set(engine.findCustomer(customerName).getBalance());
               // this.fillTables(customerName);
            } catch (NumberFormatException e){

            }
            catch(Exception e) {
                e.printStackTrace();
            }


    }

}
