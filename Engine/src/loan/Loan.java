package loan;

import customer.Customer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loan {

    public enum Status{NEW, PENDING ,ACTIVE, RISK , FINISHED}
   // private static int newIdLoanNum=1000;
    private String id;
    private int loanID;
    //private Customer loanTaker;
    private String loanTaker;
    private int loanStartAmount;
    private double loanFinalAmount;
    private Map<Customer, Integer> loanGivers;
    private Map<Customer ,Payment> loanPayments;
    private String category;
    private double loanInterest;
   // private int loanStartTime;
    private int loanTotalTime;
    private int loanEndTime;
   // private int loanActiveStatusTime;
    private int loanTimeBetweenEachPay;
    private int timeToNextPay;
    private Status loanStatus;
    private int loanCurrentAmount;
    private int activeStartTime;
    private double loanPaidAmount;
    private int debtAmount;
    private int debtTimes;
    private int timeSinceActive;
    private boolean isLateToPay;
    private List<String> debtPaymnetsInfo=new ArrayList<>();
    private SimpleBooleanProperty check;
    private SimpleBooleanProperty payCurrentDebt;
    private SimpleBooleanProperty payAllAmount;



    public Loan(String taker,String category,int amount ,int interest , int loanTimeBetweenEachPay, int totalTime ,String id){
        this.loanTaker = taker;
        this.loanGivers=new HashMap<>();
        this.loanPayments=new HashMap<>();
        this.category=category;
       this.loanStartAmount=amount;
        this.loanInterest=interest;
        this.loanTotalTime=totalTime;
        //this.loanID=++newIdLoanNum;
        this.loanTimeBetweenEachPay=loanTimeBetweenEachPay;
        this.loanStatus = Status.NEW;
        this.id=id;
        this.loanCurrentAmount=0;
        this.loanPaidAmount=0;
        this.timeSinceActive=0;
        this.loanFinalAmount= (int) (amount+(amount*(loanInterest/100)));
        this.isLateToPay=false;
        this.check=new SimpleBooleanProperty(false);
        this.payAllAmount=new SimpleBooleanProperty(false);
        this.payCurrentDebt=new SimpleBooleanProperty(false);

    }
    public void changeStatus(){
        //this.status = risk/finished;
    }
    public String getId(){return this.id;}
  //  public StringProperty idProperty() { return id; }

    public void showLoanGeneral(){
        System.out.println("_________________");
        System.out.println(this.id);
        System.out.println("loan taker:" + this.loanTaker);
        System.out.println("category:" + this.category);
        System.out.println("loan amount:" + this.loanStartAmount);
        System.out.println("interest:" + this.loanInterest + "%");
        System.out.println("status:" + this.loanStatus);
     if(loanStatus.equals(Status.PENDING)){
         //showAllLoanGivers();
     }
     if(loanStatus.equals(Status.ACTIVE)){
         System.out.println("start time:" + this.activeStartTime);
         showAllLoanPayments();
     }
        //if() fending/active/risk/finished // diferent for each one
        if (loanStatus.equals(Status.FINISHED)){
            //showAllLoanGivers();
            System.out.println("start time:" + this.activeStartTime);
            System.out.println("finished time:" + this.loanEndTime);
            showAllLoanPayments();
        }
        if(loanStatus.equals(Status.RISK)){
            showAllLoanPayments();
            //showAllDebtInfo();
        }
    }
public void showLoanFullInfo(){
    System.out.println(this.id);
    System.out.println("category:"+this.category);
    System.out.println("amount:"+this.loanStartAmount);
    System.out.println("time between payment:"+this.loanTimeBetweenEachPay);
    System.out.println("interest:"+this.loanInterest+"%");
    System.out.println("amount to return:" +this.loanFinalAmount);
    System.out.println("status:" +this.loanStatus);
    if(this.loanStatus.equals(Status.PENDING)) {
        System.out.println("amount left until active:"
                + (this.loanStartAmount
                - this.loanCurrentAmount));
    }
    else if(this.loanStatus.equals(Status.ACTIVE)){
        System.out.println("time to next payment:"+this.getTimeToNextPay());
        System.out.println("next payment amount:"+this.getLoanFinalAmount()/
                this.getLoanTotalTime()/this.getLoanTimeBetweenEachPay());
    }
    else if(this.loanStatus.equals(Status.RISK)){
        System.out.println("debt amount:"+ this.getDebtAmount());
    }
    else if(this.loanStatus.equals(Status.FINISHED)){
        System.out.println("loan start time:"+this.getLoanStartTime());
        System.out.println("loan end time:"+ this.getLoanEndTime());
    }
    System.out.println("--------");
}
//    public void showLoanClient(){
//        System.out.println(this.id);
//        System.out.println(this.loanStartAmount);
//        System.out.println(this.loanInterest);
//        System.out.println(this.loanTimeBetweenEachPay);
//        System.out.println(this.loanInterest);
//        System.out.println(this.loanFinalAmount);
//        System.out.println(this.loanStatus);
//    }

    public int getLoanStartAmount() {
        return loanStartAmount;
    }
    public String getCategory() {
        return category;
    }
    public double getLoanInterest() {
        return loanInterest;
    }
    public int getLoanTotalTime() {
        return loanTotalTime;
    }
    public String getLoanStatus(){
        return loanStatus.toString();
    }
    public String getLoanTaker() {
        return loanTaker;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public int getDebtTimes(){return this.debtTimes;}

    public void setDebtTimes(int debtTimes) {
        this.debtTimes = debtTimes;
    }

    public double getLoanPaidAmount() {
        return loanPaidAmount;
    }

    public void setLoanPaidAmount(double loanPaidAmount) {
        this.loanPaidAmount = loanPaidAmount;
    }

    public void setDebtAmount(int debtAmount) {
        this.debtAmount = debtAmount;
    }
    //    public StringProperty categoryProperty() { return category; }

    public  void CheckStatus(int programTime){
        if(loanPaidAmount == loanFinalAmount){
            if((!loanStatus.equals(Status.FINISHED))) {
                this.loanStatus = Status.FINISHED;
                loanEndTime = programTime;
                System.out.println("loan - " + id + " Status changed to finished");
            }
        }
        else if((loanTotalTime < timeSinceActive) || isLateToPay){
            if((!loanStatus.equals(Status.RISK))) {
                this.loanStatus = Status.RISK;
                System.out.println("loan - " + id + " Status changed to at risk");
            }

        }
      else if(loanCurrentAmount == loanStartAmount){
          if((!loanStatus.equals(Status.ACTIVE))){
              this.loanStatus = Status.ACTIVE;
              activeStartTime = programTime;
              System.out.println("loan - " + id + " Status changed to active");
          }

            // add the money to the loan taker
        }
        else if(loanCurrentAmount !=0){
            this.loanStatus=Status.PENDING;
            System.out.println("loan - " + id + " Status changed to pending");
        }

    }
    public void addMoneyToLoan(int amount){
        this.loanCurrentAmount =loanCurrentAmount + amount;
    }
    public  void addLoanGiver(Customer customer , int amount , int payTime){
        if(!loanGivers.containsKey(customer)) {
            loanGivers.put(customer, amount);
        }
        else
            loanGivers.put(customer,loanGivers.get(customer)+amount);
        customer.addNewInvestedLoan(this,amount,payTime);
        Payment payment = new Payment(customer.getName() ,loanTaker ,amount ,loanInterest ,payTime);
        loanPayments.put(customer,payment);
    }
    public void showAllLoanPayments(){
        for(Customer customer : loanPayments.keySet()){
           // System.out.println("investor: " + customer.getName() + "\n  amount given:" + loanGivers.get(customer));
            loanPayments.get(customer).showPayment();
        }
    }
    public Map<Customer ,Integer> getAllLoanGivers(){

        for(Customer customer : loanGivers.keySet()){
             System.out.println("investor: " + customer.getName() + "\n  amount given:" + loanGivers.get(customer));

        }
        return loanGivers;
    }

    public int getTimeToNextPay() {
        return timeToNextPay;
    }

    public void setTimeToNextPay(int timeToNextPay) {
        this.timeToNextPay = timeToNextPay;
    }
    public int getLoanTimeBetweenEachPay(){
        return loanTimeBetweenEachPay;
    }
    public void setStatusToRisk(){
        if((!loanStatus.equals(Status.RISK))) {
            this.loanStatus = Status.RISK;
            System.out.println("loan - " + id + " Status changed to at risk");
        }
    }

    public void setLoanTotalTime(int loanTotalTime) {
        this.loanTotalTime = loanTotalTime;
    }

    public void makeNextPayment(Customer taker ,int programTime){
        int amountToPayToAllEveryTime =(int) (((getLoanStartAmount()+(getLoanStartAmount()*loanInterest/100))/(loanTotalTime/loanTimeBetweenEachPay)+ debtAmount));
        // the /loangivers.size() is because we get the amount * numbr of loaners (dont now why need to check)
        if(!taker.TakeMoneyFromAccount(amountToPayToAllEveryTime , programTime)){
//         for (Customer customer : loanGivers.keySet()) {
//                customer.addBalance(amountToPayToEachOne, programTime);
//                this.loanPaidAmount = this.loanPaidAmount + amountToPayToEachOne;
//            }
//            for (Customer customer : loanGivers.keySet()) {
//                customer.addBalance(amountToPayToEachOne, programTime);
//                this.loanPaidAmount = this.loanPaidAmount + amountToPayToEachOne;
//            }
            //   int amountToPayToEachOne =(int) ((getLoanStartAmount()+(getLoanStartAmount()*loanInterest/100))/(loanTotalTime/loanTimeBetweenEachPay)+ debtAmount);
            if(!(this.loanFinalAmount==this.loanPaidAmount+amountToPayToAllEveryTime)) {
                this.debtAmount = amountToPayToAllEveryTime;
                this.debtTimes++;
                this.debtPaymnetsInfo.add("late to return payment\n" +this.getId()+"\namount:" + this.debtAmount + "\ntime:"+ programTime);
                //
            }
            else
                this.debtPaymnetsInfo.add("late to return payment\n" +this.getId()+"\namount:" + amountToPayToAllEveryTime + "\ntime:"+ programTime);
          //  this.CheckStatus(programTime);
            //this.setStatusToRisk();
            this.isLateToPay=true;
        }
        else {
            isLateToPay=false;

            for (Customer customer : loanGivers.keySet()) {
                double amountToPayToCustomer =  ((((loanGivers.get(customer)+(loanGivers.get(customer)*(loanInterest/100)))/(loanTotalTime/loanTimeBetweenEachPay))));
                customer.addBalance(amountToPayToCustomer*(this.debtTimes+1), programTime);
                this.loanPaidAmount = this.loanPaidAmount + amountToPayToCustomer*(this.debtTimes+1);
                // before change comment // // // // // // // // // // //
                //
                //
                //

            }
            if(this.getLoanStatus().equals("RISK")){
                this.setStatusToActive();
                this.debtAmount=0;
                this.debtTimes=0;
            }
            this.CheckStatus(programTime);

        }

    }

    public void setStatusToActive() {
        this.loanStatus=Status.ACTIVE;
    }
    public int getLoanCurrentAmount(){
        // current amount is the amount invested before the loan is getting active
        // it is the amount that is still on hold until the loan will get active
        // and then will go to the user asking for the loan
        return loanCurrentAmount;
    }
    public  List<String> getDebtPaymnetsInfo(){
        List<String> list = new ArrayList<String>();
        for(String info : this.debtPaymnetsInfo){
            list.add(info);
        }
        return list;
    }

    public double getLoanFinalAmount() {
        return loanFinalAmount;
    }
    public int getDebtAmount(){
        return this.debtAmount;
    }
    public int getLoanStartTime(){
        return this.activeStartTime;
    }

    public int getLoanEndTime() {
        return loanEndTime;
    }
    public int getTimeSinceActive(){
        return this.timeSinceActive;
    }
    public void increaseLoanTime(){
        this.timeSinceActive++;
    }
    public BooleanProperty checkProperty() { return check; }
    public BooleanProperty payCurrentDebtProperty(){ return payCurrentDebt;}
    public BooleanProperty payAllAmountProperty(){ return payAllAmount;}
    public void setCheck(boolean val){
        this.check.set(val);
    }
    public void setPayCurrentDebt(boolean val){
        this.payCurrentDebt.set(val);
    }
    public void setPayAllAmount(boolean val){
        this.payAllAmount.set(val);
    }
    public void advanceTimeInLoan(Customer taker ,int programTime){
        int amountToPayToAllEveryTime =(int) (((getLoanStartAmount()+(getLoanStartAmount()*loanInterest/100))/(loanTotalTime/loanTimeBetweenEachPay)+ debtAmount));
        // the /loangivers.size() is because we get the amount * numbr of loaners (dont now why need to check)
//         for (Customer customer : loanGivers.keySet()) {
//                customer.addBalance(amountToPayToEachOne, programTime);
//                this.loanPaidAmount = this.loanPaidAmount + amountToPayToEachOne;
//            }
//            for (Customer customer : loanGivers.keySet()) {
//                customer.addBalance(amountToPayToEachOne, programTime);
//                this.loanPaidAmount = this.loanPaidAmount + amountToPayToEachOne;
//            }
            //   int amountToPayToEachOne =(int) ((getLoanStartAmount()+(getLoanStartAmount()*loanInterest/100))/(loanTotalTime/loanTimeBetweenEachPay)+ debtAmount);
            if(!(this.loanFinalAmount==this.loanPaidAmount+amountToPayToAllEveryTime)) {
                this.debtAmount = amountToPayToAllEveryTime;
                this.debtTimes++;
                this.debtPaymnetsInfo.add("late to return payment\n" +this.getId()+"\namount:" + this.debtAmount + "\ntime:"+ programTime);
                //
            }
            else
                this.debtPaymnetsInfo.add("late to return payment\n"+this.getId()+"\namount:" + amountToPayToAllEveryTime + "\ntime:"+ programTime);
            //  this.CheckStatus(programTime);
            //this.setStatusToRisk();
            this.isLateToPay=true;

//        else {
//            isLateToPay=false;
//
//            for (Customer customer : loanGivers.keySet()) {
//                double amountToPayToCustomer =  ((((loanGivers.get(customer)+(loanGivers.get(customer)*(loanInterest/100)))/(loanTotalTime/loanTimeBetweenEachPay))));
//                customer.addBalance(amountToPayToCustomer*(this.debtTimes+1), programTime);
//                this.loanPaidAmount = this.loanPaidAmount + amountToPayToCustomer*(this.debtTimes+1);
//                // before change comment // // // // // // // // // // //
//                //
//                //
//                //
//
//            }
//            if(this.getLoanStatus().equals("RISK")){
//                this.setStatusToActive();
//                this.debtAmount=0;
//                this.debtTimes=0;
//            }
//            this.CheckStatus(programTime);
//
//        }

    }
}



