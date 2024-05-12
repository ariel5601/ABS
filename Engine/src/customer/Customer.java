package customer;
import javafx.beans.property.SimpleIntegerProperty;
import loan.Loan;
import engine.Engine;

import java.text.DecimalFormat;
import java.util.*;

public class Customer {
    private String customerName;
    private int customerId;
    private double balance = 0;
    private Map<String, Loan> loansTaken = new HashMap<>();
    private LinkedHashSet<Action> actions = new LinkedHashSet<>();
    // private Set<Loan> loansTaken= new HashSet<>();
    private Map<String, Loan> loansInvested = new HashMap<>();
    DecimalFormat numberFormat = new DecimalFormat("#.0");

    private SimpleIntegerProperty loansTakenStatusNew = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty loansTakenStatusPending = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty loansTakenStatusRisk = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty loansTakenStatusFinished = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty loansGivenStatusNew = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty loansGivenStatusPending = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty loansGivenStatusRisk = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty loansGivenStatusFinished = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty loansTakenStatusActive=new SimpleIntegerProperty(0);


    public Customer(String name) {
        customerName = name;
        //customerId=id;
    }

    public String getName() {
        return this.customerName;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double balanceToAdd, int programTime) {
        String description = "added money to account +" + numberFormat.format(balanceToAdd);
        double before = this.balance;
        this.balance = balance + balanceToAdd;
        Action action = new Action(description, programTime, before, this.balance);
        this.actions.add(action);
    }

    public boolean TakeMoneyFromAccount(int amountToTake, int programTime) {
        if (amountToTake > this.balance) {
            return false;
        }
        String description = "taking money from account -" + amountToTake;
        double before = this.balance;
        this.balance = balance - amountToTake;
        Action action = new Action(description, programTime, before, this.balance);
        this.actions.add(action);
        return true;
    }

    public void addLoanToCustomer(Loan loan, int programTime) {
        String description = "asked for a loan - " + loan.getId() + " (" + loan.getLoanStartAmount() + ")";
        Action action = new Action(description, programTime, this.balance, this.balance);
        loansTaken.put(loan.getId(), loan);
        actions.add(action);
    }

    public void addNewInvestedLoan(Loan loan, int amount, int programTime) {
        String description = "invested in a loan  - " + loan.getId() + " (" + amount + ")";
        Action action = new Action(description, programTime, this.balance, this.balance - amount);
        loansInvested.put(loan.getId(), loan);
        actions.add(action);
    }


    public void printCustomerInfoGeneral() {
        System.out.println("_________________________________");
        System.out.println("User name:" + this.customerName);
        System.out.println("Balance:" + numberFormat.format(this.getBalance()));
        System.out.println("loans taken:");
        if (loansTaken.size() != 0) {
            for (String loanName : loansTaken.keySet()) {
                System.out.println(loansTaken.get(loanName).getId());
                System.out.println("category:" + loansTaken.get(loanName).getCategory());
                System.out.println("amount:" + loansTaken.get(loanName).getLoanStartAmount());
                System.out.println("time between payment:" + loansTaken.get(loanName).getLoanTimeBetweenEachPay());
                System.out.println("interest:" + loansTaken.get(loanName).getLoanInterest() + "%");
                System.out.println("amount to return:" + loansTaken.get(loanName).getLoanFinalAmount());
                System.out.println("status:" + loansTaken.get(loanName).getLoanStatus());
                if (loansTaken.get(loanName).getLoanStatus().equals("PENDING")) {
                    System.out.println("amount left until active:"
                            + (loansTaken.get(loanName).getLoanStartAmount()
                            - loansTaken.get(loanName).getLoanCurrentAmount()));
                } else if (loansTaken.get(loanName).getLoanStatus().equals("ACTIVE")) {
                    System.out.println("time to next payment:" + loansTaken.get(loanName).getTimeToNextPay());
                    System.out.println("next payment amount:" + (loansTaken.get(loanName).getLoanFinalAmount() /
                            (loansTaken.get(loanName).getLoanTotalTime() / loansTaken.get(loanName).getLoanTimeBetweenEachPay())));
                } else if (loansTaken.get(loanName).getLoanStatus().equals("RISK")) {
                    System.out.println("debt amount:" + loansTaken.get(loanName).getDebtAmount());
                } else if (loansTaken.get(loanName).getLoanStatus().equals("FINISHED")) {
                    System.out.println("loan start time:" + loansTaken.get(loanName).getLoanStartTime());
                    System.out.println("loan end time:" + loansTaken.get(loanName).getLoanEndTime());
                }
                System.out.println("--------");
            }
        } else {
            System.out.println("user has no loans");
        }
        System.out.println("loans invested:");
        if (loansInvested.size() != 0) {
            for (String loanName : loansInvested.keySet()) {
                System.out.println(loansInvested.get(loanName).getId());
                System.out.println("category:" + loansInvested.get(loanName).getCategory());
                System.out.println("amount:" + loansInvested.get(loanName).getLoanStartAmount());
                System.out.println("time between payment:" + loansInvested.get(loanName).getLoanTimeBetweenEachPay());
                System.out.println("interest:" + loansInvested.get(loanName).getLoanInterest() + "%");
                System.out.println("amount to return:" + loansInvested.get(loanName).getLoanFinalAmount());
                System.out.println("status:" + loansInvested.get(loanName).getLoanStatus());
                if (loansInvested.get(loanName).getLoanStatus().equals("PENDING")) {
                    System.out.println("amount left until active:"
                            + (loansInvested.get(loanName).getLoanStartAmount()
                            - loansInvested.get(loanName).getLoanCurrentAmount()));
                } else if (loansInvested.get(loanName).getLoanStatus().equals("ACTIVE")) {
                    System.out.println("time to next payment:" + loansInvested.get(loanName).getTimeToNextPay());
                    System.out.println("next payment amount:" + (loansInvested.get(loanName).getLoanFinalAmount() /
                            (loansInvested.get(loanName).getLoanTotalTime() / loansInvested.get(loanName).getLoanTimeBetweenEachPay())));
                } else if (loansInvested.get(loanName).getLoanStatus().equals("RISK")) {
                    System.out.println("debt amount:" + loansInvested.get(loanName).getDebtAmount());
                } else if (loansInvested.get(loanName).getLoanStatus().equals("FINISHED")) {
                    System.out.println("loan start time:" + loansInvested.get(loanName).getLoanStartTime());
                    System.out.println("loan end time:" + loansInvested.get(loanName).getLoanEndTime());
                }
                System.out.println("--------");
            }
        } else {
            System.out.println("user has no loans");
        }
        System.out.println("actions:");
        if (actions.size() != 0) {
            for (Action action : actions) {
                action.showActionDetails();
            }
        } else {
            System.out.println("user has no actions");
        }
    }


    public void printCustomerNamesAndBalances() {
        System.out.println("_________________________________");
        System.out.println("User name:" + this.customerName + "   Balance:" + this.getBalance());
    }

    public void addNewAction(String action, int amount, int time) {
        Action newAction = new Action(action, time, this.balance, this.balance + amount);
        actions.add(newAction);

    }

    public Map<String, Loan> getLoansTaken() {
        return loansTaken;
    }

    public Map<String, Loan> getLoansGiven() {
        return loansInvested;
    }

    public LinkedHashSet<Action> getActions() {
        return actions;
    }

    public ArrayList<String> getActionsDescription() {
        ArrayList<String> list = new ArrayList<>();
        for (Action action : actions) {
            list.add(action.getActionDescription());
        }
        return list;

    }

    //    public ArrayList<Loan> loansTakenStatusNew(){
//        ArrayList<Loan> list=new ArrayList<>();
//         for(Loan loan:this.getLoansTaken().values()){
//             if(this.getLoansTaken().get(loan).getLoanStatus().equals("NEW")){
//                 list.add(loan);
//             }
//         }
//         return list;
//    }



    public int getLoansTakenStatusNew() {
        loansTakenStatusNew.set(0);
        for (Loan loan : this.getLoansTaken().values()) {
            if (loan.getLoanStatus().equals("NEW")) {
                loansTakenStatusNew.set(loansTakenStatusNew.get() + 1);
            }
        }
        return loansTakenStatusNew.get();
    }
    public int getLoansTakenStatusPending() {
        loansTakenStatusPending.set(0);
        for (Loan loan : this.getLoansTaken().values()) {
            if (loan.getLoanStatus().equals("PENDING")) {
                loansTakenStatusPending.set(loansTakenStatusPending.get() + 1);
            }
        }
        return loansTakenStatusPending.get();
    }
    public int getLoansTakenStatusRisk() {
        loansTakenStatusRisk.set(0);
        for (Loan loan : this.getLoansTaken().values()) {
            if (loan.getLoanStatus().equals("RISK")) {
                loansTakenStatusRisk.set(loansTakenStatusRisk.get() + 1);
            }
        }
        return loansTakenStatusRisk.get();
    }
    public int getLoansTakenStatusFinished() {
        loansTakenStatusFinished.set(0);
        for (Loan loan : this.getLoansTaken().values()) {
            if (loan.getLoanStatus().equals("FINISHED")) {
                loansTakenStatusFinished.set(loansTakenStatusFinished.get() + 1);
            }
        }
        return loansTakenStatusFinished.get();
    }
    public int getLoansTakenStatusActive() {
        loansTakenStatusActive.set(0);
        for (Loan loan : this.getLoansTaken().values()) {
            if (loan.getLoanStatus().equals("ACTIVE")) {
                loansTakenStatusActive.set(loansTakenStatusActive.get() + 1);
            }
        }
        return loansTakenStatusPending.get();
    }
    public int getLoansGivenStatusNew() {
        loansGivenStatusNew.set(0);
        for (Loan loan : this.getLoansGiven().values()) {
            if (loan.getLoanStatus().equals("NEW")) {
                loansGivenStatusNew.set(loansGivenStatusNew.get() + 1);
            }
        }
        return loansGivenStatusNew.get();
    }
    public int getLoansGivenStatusPending() {
        loansGivenStatusPending.set(0);
        for (Loan loan : this.getLoansGiven().values()) {
            if (loan.getLoanStatus().equals("PENDING")) {
                loansGivenStatusPending.set(loansGivenStatusPending.get() + 1);
            }
        }
        return loansGivenStatusPending.get();
    }
    public int getLoansGivenStatusRisk() {
        loansGivenStatusRisk.set(0);
        for (Loan loan : this.getLoansGiven().values()) {
            if (loan.getLoanStatus().equals("RISK")) {
                loansGivenStatusRisk.set(loansGivenStatusRisk.get() + 1);
            }
        }
        return loansGivenStatusRisk.get();
    }
    public int getLoansGivenStatusFinished() {
        loansGivenStatusFinished.set(0);
        for (Loan loan : this.getLoansGiven().values()) {
            if (loan.getLoanStatus().equals("FINISHED")) {
                loansGivenStatusFinished.set(loansGivenStatusFinished.get() + 1);
            }
        }
        return loansGivenStatusFinished.get();
    }


}
