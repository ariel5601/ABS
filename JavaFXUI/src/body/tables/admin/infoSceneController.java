package body.tables.admin;

import customer.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import loan.Loan;

public class infoSceneController {

    @FXML
    private TextFlow sceneTextFlow;

    @FXML
    private Label statusLabel;
    public infoSceneController(){

    }
    @FXML
    private void initialize(){

    }
    public  void showInfo(Loan loan){
        statusLabel.setText(loan.getLoanStatus());
        if(loan.getLoanStatus().equals("PENDING")){
            for(Customer customer:loan.getAllLoanGivers().keySet()){
                Text str = new Text("\ninvestor:"+ customer.getName() + "        amount given:" +loan.getAllLoanGivers().get(customer));
                sceneTextFlow.getChildren().add(str);
            }
            Text str1=new Text("\n\namount left until active:" + (loan.getLoanStartAmount() - loan.getLoanCurrentAmount()));
            sceneTextFlow.getChildren().add(str1);
        }
        else if(loan.getLoanStatus().equals("ACTIVE")){
            for(Customer customer:loan.getAllLoanGivers().keySet()){
                Text str = new Text("\ninvestor:"+ customer.getName() + "        amount given:" +loan.getAllLoanGivers().get(customer));
                sceneTextFlow.getChildren().add(str);
            }
            Text str1=new Text("\nloan start time:"+loan.getLoanStartTime());
            sceneTextFlow.getChildren().add(str1);
            Text str2=new Text("\ntime to next payment:"+loan.getTimeToNextPay());
            System.out.println("\nnext payment amount:"+loan.getLoanFinalAmount()/
                    loan.getLoanTotalTime()/loan.getLoanTimeBetweenEachPay());
            sceneTextFlow.getChildren().add(str2);
            Text str3=new Text("\ninterest:" + loan.getLoanInterest()+ "\nfinal amount to return"+ loan.getLoanFinalAmount());
            sceneTextFlow.getChildren().add(str3);

        }
        else if(loan.getLoanStatus().equals("RISK")){
            for(Customer customer:loan.getAllLoanGivers().keySet()){
                Text str = new Text("\ninvestor:"+ customer.getName() + "        amount given:" +loan.getAllLoanGivers().get(customer));
                sceneTextFlow.getChildren().add(str);
            }
            Text str1=new Text("\nloan start time:"+loan.getLoanStartTime());
            sceneTextFlow.getChildren().add(str1);
            Text str2=new Text("\ntime to next payment:"+loan.getTimeToNextPay());
            System.out.println("\nnext payment amount:"+loan.getLoanFinalAmount()/
                    loan.getLoanTotalTime()/loan.getLoanTimeBetweenEachPay());
            sceneTextFlow.getChildren().add(str2);
            Text str3=new Text("\ninterest:" + loan.getLoanInterest()+ "\nfinal amount to return"+ loan.getLoanFinalAmount());
            sceneTextFlow.getChildren().add(str3);
            Text str4=new Text("\ndebt amount:"+ loan.getDebtAmount());
            sceneTextFlow.getChildren().add(str4);

        }
        else if(loan.getLoanStatus().equals("FINISHED")){
            for(Customer customer:loan.getAllLoanGivers().keySet()){
                Text str = new Text("\ninvestor:"+ customer.getName() + "        amount given:" +loan.getAllLoanGivers().get(customer));
                sceneTextFlow.getChildren().add(str);
            }
            Text str1=new Text("\n\nloan start time:"+loan.getLoanStartTime()+"\nloan end time:"+ loan.getLoanEndTime());
            sceneTextFlow.getChildren().add(str1);

        }
    }
}
