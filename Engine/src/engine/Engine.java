package engine;

import customer.Customer;
import customer.CustomerDataHolder;
import exceptions.CategoryErrorException;
import exceptions.LoanPaymentTimeException;
import exceptions.LoanUserNotFoundException;
import exceptions.UserAlreadyExistException;
import generated.AbsCustomer;
import generated.AbsDescriptor;
import generated.AbsLoan;
import loan.Loan;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

public class Engine {
    private static int programTime=1;
    private Map<String, Loan> allLoans;
    private final static String JAXB_XML_PACKAGE_NAME="generated";
   // private Set<Customer> allClients=new HashSet<>();
    private Map<String , Customer>allCustomers;
    private Set<String> categories;
    DecimalFormat numberFormat = new DecimalFormat("#.0"); // to show to numbers with only one digit after the point

    public boolean loadFromXml(String filePath)
    {
        allLoans=new HashMap<>();
        allCustomers=new HashMap<>();
        categories=new HashSet<>();
        try{
            programTime=1;
            InputStream inputStream = new FileInputStream(new File(filePath));
            AbsDescriptor absDescriptor = deserializeFrom(inputStream);
         //  Categories = (Set<String>) absDescriptor.getAbsCategories().getAbsCategory();
            categories.addAll( absDescriptor.getAbsCategories().getAbsCategory());
            for(AbsCustomer abscustomer : absDescriptor.getAbsCustomers().getAbsCustomer()){
                Customer customer =new Customer(abscustomer.getName());
                customer.addBalance(abscustomer.getAbsBalance() , programTime);
                if(allCustomers.containsKey(abscustomer.getName()))
                    throw new UserAlreadyExistException();
                allCustomers.put(abscustomer.getName(),customer);
            }
            for(AbsLoan absLoan : absDescriptor.getAbsLoans().getAbsLoan()) {
                Loan newLoan = new Loan(absLoan.getAbsOwner() ,absLoan.getAbsCategory() ,absLoan.getAbsCapital()
                        ,absLoan.getAbsIntristPerPayment(),absLoan.getAbsPaysEveryYaz(),absLoan.getAbsTotalYazTime(),absLoan.getId());
              if(!allCustomers.keySet().contains(newLoan.getLoanTaker())){
                  throw new LoanUserNotFoundException();
              }
              if(absLoan.getAbsTotalYazTime()%absLoan.getAbsPaysEveryYaz() !=0){
                  throw new LoanPaymentTimeException();
              }
                allLoans.put(newLoan.getId(), newLoan);
               Customer customer =  (Customer) allCustomers.get(absLoan.getAbsOwner());
              // Categories.add(absLoan.getAbsCategory());

                    if (!categories.contains(absLoan.getAbsCategory()))
                    {
                        throw new CategoryErrorException();
                    }
                 //   if(allCustomers.keySet().contains())

               customer.addLoanToCustomer(newLoan , programTime);

            }
            System.out.println("File Loaded successfully");
            programTime=1;
            return true;
        }catch (JAXBException | FileNotFoundException e){
            System.out.println("failed to load file");
            System.out.println("file doesn't exist"); // to fix
            System.out.println("please try loading another file");
            return false;
        }catch (CategoryErrorException e) {
            System.out.println("failed to load file");
            System.out.println("one of the loans is trying to assign to a category that doesn't exist");
            System.out.println("please try loading another file");
            throw new CategoryErrorException();
            //return false;
        }catch (LoanUserNotFoundException e){
            System.out.println("failed to load file");
            System.out.println("one of the loans user doesn't exist");
            System.out.println("please try loading another file");
            throw new LoanUserNotFoundException();
           // return false;
        }catch (LoanPaymentTimeException e){
            System.out.println("failed to load file");
            System.out.println("one of the loans time between each pay doesn't fit with the loan total time ");
            System.out.println("please try loading another file");
            throw new LoanPaymentTimeException();
            //return false;
        }catch (UserAlreadyExistException e){
            System.out.println("failed to load file");
            System.out.println("two different users have to same name");
            System.out.println("please try loading another file");
            throw new UserAlreadyExistException();
           // return false;
        }

    }
    public static AbsDescriptor deserializeFrom(InputStream in)throws JAXBException{
        JAXBContext jc =JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u =jc.createUnmarshaller();
        return (AbsDescriptor) u.unmarshal(in);
    }

    public Customer createCustomer(String name,int id){
        // should be changed to DTO later
        Customer customer = new Customer(name);
        allCustomers.put(name,customer);
        return customer;

    }
    public void createLoan(Customer taker,String category,int amount ,int intrest , int totalTime){
       // Loan loan = new Loan(taker,category,amount,intrest,totalTime);
       // allLoans.add(loan);

    }

    public Map<String ,Customer> getAllCustomers() {
        return allCustomers;
    }

    public Map<String ,Loan> getAllLoans() {
        return allLoans;
    }
    public int getCurrentTime(){
        return programTime;
    }
    public Customer findCustomer(String name){
       return  allCustomers.get(name);
    }
    public void ShowAllCustomers()
    {
        for(String name : allCustomers.keySet()){
            allCustomers.get(name).printCustomerInfoGeneral();
        }
    }
    public List<String>getAllCustomersNames(){
     List<String> customersNamesList = new ArrayList<>(allCustomers.size());
        customersNamesList.addAll(allCustomers.keySet());
        return customersNamesList;
    }
    public Customer findCustomerBeName(String name){
        return allCustomers.get(name);
    }
    public void ShowAllLoans()
    {
        for(String name : allLoans.keySet()){
            allLoans.get(name).showLoanGeneral();
        }
    }
    public List<Loan> findMatchingLoans(String category , int amount , double interest , int time , int minNumberOfLoans ,String user){
        List<Loan> matchingLoans= new ArrayList<>();
        for(String id:allLoans.keySet()) {
            Loan loan = allLoans.get(id);
            if (!loan.getLoanTaker().equals(user)) {
                if (loan.getLoanStatus().equals("NEW") || loan.getLoanStatus().equals("PENDING")) {
                    if(category.equals("no category")){
                        if (loan.getLoanInterest() > interest) {
                            if (loan.getLoanTotalTime() > time) {
                                if(this.findCustomer(loan.getLoanTaker()).getLoansTakenStatusNew()
                                        +this.findCustomer(loan.getLoanTaker()).getLoansTakenStatusPending()
                                        +this.findCustomer(loan.getLoanTaker()).getLoansTakenStatusRisk()
                                        +this.findCustomer(loan.getLoanTaker()).getLoansTakenStatusActive()<=minNumberOfLoans)
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
          return matchingLoans;
    }
    public List<CustomerDataHolder> getCustomersList() {
        List<CustomerDataHolder> customersList=new ArrayList<>(allCustomers.size());
        for(String customer : allCustomers.keySet()){
            CustomerDataHolder customerData = new CustomerDataHolder(customer);
            customerData.setBalance(allCustomers.get(customer).getBalance());
            customersList.add(customerData);
        }
        return customersList;
    }
    public Set<String> getCategories(){
        return categories;
    }
     public void divideMoneyBetweenLoans(List<Loan> chosenLoans , int amount ,int percent, String costumerName , int time){
        Utility utility = new Utility();
        while(chosenLoans.size()!=0 && amount!=0){
        Loan loan =utility.findMinBeAmount(chosenLoans);
        int min =loan.getLoanStartAmount()-loan.getLoanCurrentAmount();
        if(min>loan.getLoanStartAmount()*percent/100)
            min=loan.getLoanStartAmount()*percent/100;
        if(min <= (amount/chosenLoans.size()))
        {
            loan.addMoneyToLoan(min);
            amount=amount-min;
            System.out.println(min +" added to loan - "  + loan.getId());
            //allCustomers.get(costumerName).addNewAction(("gave loan to " + loan.getLoanTaker()) , min , programTime);
            allCustomers.get(costumerName).TakeMoneyFromAccount(min , programTime);
            loan.addLoanGiver(allCustomers.get(costumerName),min , time);
            loan.CheckStatus(programTime);
            if(loan.getLoanStatus().equals(Loan.Status.ACTIVE.toString())){
                allCustomers.get(loan.getLoanTaker()).addNewAction(("got money from loan - "+loan.getId())  , loan.getLoanStartAmount() , programTime);
                allCustomers.get(loan.getLoanTaker()).addBalance(loan.getLoanStartAmount(),programTime);
                // getLoanStartAmount can be changedy to loan current amount
            }
            chosenLoans.remove(loan);
        }
        else{
            int amountToEach= amount/chosenLoans.size();
            for(int i=0;i<chosenLoans.size();i++){
                chosenLoans.get(i).addMoneyToLoan(amountToEach);
                amount=amount-amountToEach;
                System.out.println(amountToEach +" added to loan - "  + chosenLoans.get(i).getId());
               // allCustomers.get(costumerName).addNewAction(("gave loan to " + chosenLoans.get(i).getLoanTaker()) , amountToEach , programTime);
                allCustomers.get(costumerName).TakeMoneyFromAccount(amountToEach , programTime);
                chosenLoans.get(i).addLoanGiver(allCustomers.get(costumerName),amountToEach ,time);
                chosenLoans.get(i).CheckStatus(programTime);
                if(loan.getLoanStatus().equals(Loan.Status.ACTIVE.toString())){
                    allCustomers.get(chosenLoans.get(i).getLoanTaker()).addNewAction("got money from loan - "+chosenLoans.get(i).getId() , amountToEach, programTime);
                    allCustomers.get(chosenLoans.get(i).getLoanTaker()).addBalance(amountToEach,programTime);
                    // getLoanStartAmount can be changedy to loan current amount

                }
                chosenLoans.remove(i);
            }
        }
        }
     }
     public  void advanceTime(){
        programTime++;
        for(Loan loan:allLoans.values()){
//            loan.setTimeToNextPay(loan.getTimeToNextPay()+1);
            if(loan.getLoanStatus().equals("ACTIVE") || loan.getLoanStatus().equals("RISK")) {
                loan.setTimeToNextPay(loan.getTimeToNextPay()+1);
                loan.increaseLoanTime();
                if (loan.getTimeToNextPay() == loan.getLoanTimeBetweenEachPay()) {
                   //loan.makeNextPayment(allCustomers.get(loan.getLoanTaker()), programTime);
                    loan.advanceTimeInLoan(allCustomers.get(loan.getLoanTaker()), programTime);
                    loan.setTimeToNextPay(0);
                }
              //  loan.CheckStatus(programTime);
                // needs to be checked
                loan.CheckStatus(programTime);
            }
        }

     }

}

