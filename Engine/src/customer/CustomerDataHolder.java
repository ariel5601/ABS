package customer;

import loan.Loan;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class CustomerDataHolder {
    private  String customerName;
    private int customerId;
    private double balance=0;
    private Map<String , Loan> loans = new HashMap<>();
    private LinkedHashSet<Action> actions = new LinkedHashSet<>();

    public CustomerDataHolder(String name){
        this.customerName=name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getCustomerName() {
        return customerName;
    }
}
