package body.assign.table;

import engine.Engine;
import javafx.concurrent.Task;
import loan.Loan;

import java.util.ArrayList;
import java.util.List;

public class findLoansTask extends Task<List<Loan>> {
    private Engine engine;
    private String category;
    private String user;
    private double interest;
    private int time;
    private int minNumberOfLoans;

    public findLoansTask(Engine engine,String category,String user ,double interest ,int time ,int minNumberOfLoans){
        this.engine=engine;
        this.category=category;
        this.user=user;
        this.interest=interest;
        this.time=time;
        this.minNumberOfLoans=minNumberOfLoans;
    }
    @Override
    protected List<Loan> call() throws Exception {
       // matchingLoans = engine.findMatchingLoans(category, amount, interest, time,minNumberOfLoans, customerName);
         int max =engine.getAllLoans().size();
         int i=0;
        List<Loan> matchingLoans= new ArrayList<>();
        for(String id:engine.getAllLoans().keySet()) {
            updateProgress(i+1,max);
            i++;
            updateMessage("finding loans");
            Thread.sleep(500);
            Loan loan = engine.getAllLoans().get(id);
            if (!loan.getLoanTaker().equals(user)) {
                if (loan.getLoanStatus().equals("NEW") || loan.getLoanStatus().equals("PENDING")) {
                    if(category.equals("no category")){
                        if (loan.getLoanInterest() > interest) {
                            if (loan.getLoanTotalTime() > time) {
                                if(engine.findCustomer(loan.getLoanTaker()).getLoansTakenStatusNew()
                                        +engine.findCustomer(loan.getLoanTaker()).getLoansTakenStatusPending()
                                        +engine.findCustomer(loan.getLoanTaker()).getLoansTakenStatusRisk()
                                        +engine.findCustomer(loan.getLoanTaker()).getLoansTakenStatusActive()<=minNumberOfLoans)
                                    matchingLoans.add(loan);
                            }
                        }
                    }
                    else if (loan.getCategory().equals(category)) {
                        if (loan.getLoanInterest() > interest) {
                            if (loan.getLoanTotalTime() > time) {
                                matchingLoans.add(loan);
                            }
                        }
                    }
                }
            }
        }
        updateMessage("done");
        return matchingLoans;
         //List<Loan> matchingLoans;
//
//        for(int i=0;i<10;i++){
//            System.out.println(i);
//            updateProgress(i+1,10);
//            updateMessage("finding loans");
//            Thread.sleep(500);
//
//        }
//        updateMessage("done");
    }
}
