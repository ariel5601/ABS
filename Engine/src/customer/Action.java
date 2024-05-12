package customer;

import java.text.DecimalFormat;

public class Action {
    private  String actionDescription;
    private  int actionExecutionTime;
    private double balanceBeforeAction;
    private double balanceAfterAction;

    DecimalFormat numberFormat = new DecimalFormat("#.0");

    Action(String actionDescription , int actionExecutionTime , double balanceBeforeAction , double balanceAfterAction){
        this.actionDescription=actionDescription;
        this.actionExecutionTime =actionExecutionTime;
        this.balanceBeforeAction=balanceBeforeAction;
        this.balanceAfterAction=balanceAfterAction;
    }
    public double getBalanceBeforeAction(){ return this.balanceBeforeAction;}
    public double getBalanceAfterAction(){return  this.balanceAfterAction;}
    public int getActionExecutionTime(){return this.actionExecutionTime;}
    public String getActionDescription() {return actionDescription;}
    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public void setActionExecutionTime(int actionExecutionTime) {
        this.actionExecutionTime = actionExecutionTime;
    }

    public void setBalanceBeforeAction(int balanceBeforeAction) {
        this.balanceBeforeAction = balanceBeforeAction;
    }

    public void setBalanceAfterAction(int balanceAfterAction) {
        this.balanceAfterAction = balanceAfterAction;
    }
    public  void showActionDetails(){
        System.out.println("  "+this.actionDescription);
        System.out.println("   time:" + this.actionExecutionTime);
        System.out.println("   balance before:" + numberFormat.format(this.balanceBeforeAction));
        System.out.println("   balance after:" + numberFormat.format(this.balanceAfterAction));
    }
}

