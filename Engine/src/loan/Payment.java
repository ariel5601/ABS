package loan;

import customer.Customer;

import java.text.DecimalFormat;

public class Payment {
    private int paymentTime;
    private int paymentAmountBeforeInterest;
    private int paymentAmountAfterInterest;
    private double interest;
    private String paymentTaker;
    private String paymentGiver;
    private int paidAmount;


    public Payment(String giver , String taker , int paymentAmountBeforeInterest , double interest , int payTime){
        paymentGiver = giver;
        paymentTaker = taker;
        this.paymentAmountBeforeInterest =paymentAmountBeforeInterest;
        this.paymentAmountAfterInterest = (int) (paymentAmountBeforeInterest + ((paymentAmountBeforeInterest*interest)/100));
        this.interest=interest;
        paymentTime=payTime;
    }
    public double getInterest() {
        return interest;
    }

    public int getGetPaymentAmountAfterInterest() {
        return paymentAmountAfterInterest;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public int getPaymentAmountBeforeInterest() {
        return paymentAmountBeforeInterest;
    }

    public int getPaymentTime() {
        return paymentTime;
    }

    public String getPaymentGiver() {
        return paymentGiver;
    }

    public String getPaymentTaker() {
        return paymentTaker;
    }

    public void setGetPaymentAmountAfterInterest() {
        this.paymentAmountAfterInterest = (int) (paymentAmountBeforeInterest + ((paymentAmountBeforeInterest*interest)/100));
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setPaymentAmountBeforeInterest(int paymentAmountBeforeInterest) {
        this.paymentAmountBeforeInterest = paymentAmountBeforeInterest;
    }

    public void setPaymentGiver(String paymentGiver) {
        this.paymentGiver = paymentGiver;
    }

    public void setPaymentTaker(String paymentTaker) {
        this.paymentTaker = paymentTaker;
    }

    public void setPaymentTime(int paymentTime) {
        this.paymentTime = paymentTime;
    }
    public void showPayment(){
        System.out.println("investor:" + paymentGiver + " \n amount given:" + paymentAmountBeforeInterest + " interest:" + interest
        + "\n amount after interest: " + paymentAmountAfterInterest + " gave at:" + paymentTime);
        //System.out.println("investor: " + customer.getName() + "\n  amount given:" + loanGivers.get(customer));
    }

}