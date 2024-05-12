package main;

import customer.CustomerDataHolder;
import engine.Engine;
import exceptions.WrongInputException;
import loan.Loan;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UiMain {

   public static  Engine engine = new Engine();
//   public static Scanner scanner = new Scanner(System.in);
   public static DecimalFormat numberFormat = new DecimalFormat("#.0");
    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         // Engine engine = new Engine();
        boolean exit=false;
        boolean start=false;
        boolean validInput=false;
        String xmlFilePath="";
        System.out.println("Welcome to A.B.S");
        System.out.println("1. load a file");
        System.out.println("2. exit");
        int input=0;
                do {
            try {
                 input = scanner.nextInt();
                 if((input !=1) && (input!=2))
                     throw new WrongInputException();
                start=true;
           }catch (WrongInputException |InputMismatchException e){
                System.out.println("wrong input");
               System.out.println("please choose\n1-load a file \n2 -exit program");
                start=false;
                scanner.nextLine();//Advance the scanner
            }
       }while (!start);
        if(input == 1)
        {
            System.out.println("enter file path(must be a xml file):");
            boolean isFileloaded=false;
            scanner.nextLine();

           // boolean isFileXml;
            do {
               // String xmlFilePath = scanner.nextLine();
                 xmlFilePath = scanner.nextLine();
                if(xmlFilePath.endsWith(".xml")) {
                    isFileloaded = engine.loadFromXml(xmlFilePath);

                }
                else {
                    isFileloaded = false;
                    System.out.println("file is not a xml file");
                    System.out.println("please try another file");
                }

            }while (!isFileloaded);
        }
        if(input == 2){
            exit=true;
        }

        while (!exit) {
            manu(engine);
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                input=0; // goes to the default case
            }
            switch (input){

                case 1:
                    engine.ShowAllLoans();
                    break;
                case 2:
                    engine.ShowAllCustomers();
                    break;
                case 3: // ADD
                    List<String> namesList=engine.getAllCustomersNames();
                    System.out.println("choose a user to add balance");
                   for(int i=0;i<namesList.size();i++)
                   {
                       System.out.println(i+1+")" + namesList.get(i) +"  balance: " +numberFormat.format(engine.getAllCustomers().get(namesList.get(i)).getBalance()));
                   }
                    do {
                        try {
                            input = scanner.nextInt();
                            if(input >namesList.size() || input <=0)
                                throw new WrongInputException();
                                  validInput=true;
                            }catch (WrongInputException |InputMismatchException e){
                              System.out.println("wrong input");
                              System.out.println("please enter user number");
                          validInput=false;
                          scanner.nextLine();//Advance the scanner
                             }
                            }while (!validInput);
                  String choosenUser= namesList.get(input-1);
                    System.out.println("adding to: "+ choosenUser);
                    System.out.println("choose amount to add:");

                   int amountToAdd=0;
                    do {
                        try {
                            amountToAdd = scanner.nextInt();
                            if(amountToAdd <0)
                                throw new WrongInputException();
                            validInput=true;
                        }catch (WrongInputException |InputMismatchException e){
                            System.out.println("wrong input");
                            System.out.println("please choose an positive integer number");
                            validInput=false;
                            scanner.nextLine();//Advance the scanner
                        }
                    }while (!validInput);
                  engine.findCustomer(choosenUser).addBalance(amountToAdd,engine.getCurrentTime());
                    break;
                case 4:  // TAKE
                    List<String> nameList=engine.getAllCustomersNames();
                    System.out.println("choose a user to take money from");
                    for(int i=0;i<nameList.size();i++)
                    {
                        System.out.println(i+1+")" + nameList.get(i)+" balance: " +numberFormat.format(engine.getAllCustomers().get(nameList.get(i)).getBalance()));
                    }
                    do {
                        try {
                            input = scanner.nextInt();
                            if(input >nameList.size() || input <=0)
                                throw new WrongInputException();
                            validInput=true;
                        }catch (WrongInputException |InputMismatchException e){
                            System.out.println("wrong input");
                            System.out.println("please enter user number");
                            validInput=false;
                            scanner.nextLine();//Advance the scanner
                        }
                    }while (!validInput);
                   // input = scanner.nextInt();
                    String chosenUser= nameList.get(input-1);
                    System.out.println("taking from: "+ chosenUser);
                    System.out.println("choose amount to take:");
                    boolean isActionPossible=false;
                    do {
                        try {
                            int amountToTake = scanner.nextInt();
                            if(amountToTake < 0){
                                throw  new WrongInputException();
                            }
                            isActionPossible = engine.findCustomer(chosenUser).TakeMoneyFromAccount(amountToTake, engine.getCurrentTime());
                            if (!isActionPossible) {
                                System.out.println("chosen amount is too high user has only " + engine.findCustomer(chosenUser).getBalance());
                                System.out.println("please choose a different amount");
                            }
                        } catch (WrongInputException | InputMismatchException e) {
                            System.out.println("wrong input");
                            System.out.println("please choose an positive integer number");
                            isActionPossible = false;
                            scanner.nextLine();
                        }
                    }while (!isActionPossible);
                    break;
                case 5:
                    List<CustomerDataHolder> customerDataHolderList = engine.getCustomersList();
                    List<String> categories = new ArrayList<>();
                    categories.addAll(engine.getCategories());
                    System.out.println("choose a user:");
                    validInput=false;
                        for (int i = 0; i < customerDataHolderList.size(); i++) {
                            System.out.println(i + 1 + ")" + customerDataHolderList.get(i).getCustomerName() +
                                    "   balance:" + numberFormat.format(customerDataHolderList.get(i).getBalance()));
                        }
                                 do {
                                     try {
                                         validInput = true;
                                         input = scanner.nextInt();
                                         if (input > customerDataHolderList.size() || input <= 0)
                                             throw new WrongInputException();
                                     }catch (WrongInputException | InputMismatchException e){
                                         System.out.println("wrong input");
                                         System.out.println("please choose a user number");
                                         scanner.nextLine();
                                         validInput=false;
                                     }
                                 }while (!validInput);


                        //  input = scanner.nextInt();
                        String user = customerDataHolderList.get(input - 1).getCustomerName();
                        System.out.println("user chosen: " + user);
                        System.out.println("choose amount: ");
                        boolean isPossible = false;
                        int amountToTake = 0;
                        do {
                            try {
                                amountToTake = scanner.nextInt();
                                if (engine.findCustomer(user).getBalance() - amountToTake >= 0) {
                                    isPossible = true;
                                    if (amountToTake < 0) {
                                        isPossible = false;
                                        throw new WrongInputException();
                                    }
                                } else {
                                    isPossible=false;
                                    throw new WrongInputException();

                                }
                            }catch (WrongInputException | InputMismatchException e)
                            {
                                System.out.println("wrong input");
                                System.out.println("please choose a positive integer number that is lower or equals to the user balance");
                                isPossible = false;
                                scanner.nextLine();
                            }

                        } while (!isPossible);
                        String chosenCategory = "";

                            int categoryChoice = 0;
                                System.out.println("would you like to choose a specific category:");
                                System.out.println("1)yes");
                                System.out.println("2)no");
                                boolean choiceValidation =false;
                                do{
                                    try {
                                        choiceValidation=true;
                                        categoryChoice = scanner.nextInt();
                                        if(categoryChoice!=1 && categoryChoice!=2)
                                            throw   new WrongInputException();
                                    }catch (WrongInputException| InputMismatchException e){
                                        System.out.println("wrong input please enter 1 or 2");
                                        choiceValidation=false;
                                        scanner.nextLine();
                                    }
                                }
                                while (!choiceValidation);
                                        if (categoryChoice == 1) {
                                            choiceValidation=false;
                                            // System.out.println("minimum interest chosen: " + categoryChoice + "%");
                                            System.out.println("choose a category:");
                                            for (int i = 0; i < categories.size(); i++) {
                                                System.out.println(i + 1 + ")" + categories.get(i));
                                            }
                                            do {
                                                try {
                                                    choiceValidation=true;
                                                    input = scanner.nextInt();
                                                    if (input > categories.size() || input <= 0)
                                                        throw new WrongInputException();
                                                }catch (WrongInputException | InputMismatchException e)
                                                {
                                                    System.out.println("wrong input");
                                                    System.out.println("please choose one of the categories number");
                                                    choiceValidation=false;
                                                    scanner.nextLine();
                                                }
                                            }while (!choiceValidation);
                                            chosenCategory = categories.get(input - 1);
                                            System.out.println("category chosen: " + chosenCategory);
                                            validInput = true;
                                        } else if (categoryChoice == 2) {
                                            System.out.println("no category chosen");
                                            categoryChoice = 0;
                                            chosenCategory = "no category";
                                            validInput = true;
                                        } else {
                                            throw new WrongInputException();

                                        }

                        System.out.println("would you like to choose a minimum interest:");
                        System.out.println("1)yes");
                        System.out.println("2)no");
                        double interestChoice=0;
                        choiceValidation=false;
                        do {
                            try {
                                choiceValidation=true;
                                interestChoice = scanner.nextDouble();
                                if(interestChoice!=1 && interestChoice!=2)
                                    throw   new WrongInputException();
                            }catch (WrongInputException | InputMismatchException e){
                                System.out.println("wrong input please enter 1 or 2");
                                choiceValidation=false;
                                scanner.nextLine();
                            }
                        }while (!choiceValidation);
                        choiceValidation=false;
                        if (interestChoice == 1) {
                            System.out.println("choose minimum interest:");
                            do {
                                try {
                                    choiceValidation=true;
                                    interestChoice = scanner.nextDouble();
                                    if(interestChoice <=0 || interestChoice > 99){
                                        throw new WrongInputException();
                                    }
                                }catch (WrongInputException | InputMismatchException e){
                                    System.out.println("wrong input");
                                    System.out.println("please enter a number between 1 to 99");
                                    choiceValidation=false;
                                    scanner.nextLine();
                                }
                            }while (!choiceValidation);
                            System.out.println("minimum interest chosen: " + interestChoice + "%");
                        } else {
                            System.out.println("no interest chosen");
                            interestChoice = 0;
                        }
                        System.out.println("would you like to choose a minimum time for the loan:");
                        System.out.println("1)yes");
                        System.out.println("2)no");
                        choiceValidation=false;
                        int timeChoice=0;
                        do {
                            try {
                                choiceValidation=true;
                                 timeChoice = scanner.nextInt();
                                 if(timeChoice!=1 && timeChoice!=2)
                                     throw new WrongInputException();
                            }catch (WrongInputException | InputMismatchException e)
                            {
                                System.out.println("wrong input");
                                System.out.println("please choose 1 or 2 ");
                                choiceValidation=false;
                                scanner.nextLine();
                            }
                        }while (!choiceValidation);
                        if (timeChoice == 1) {
                            System.out.println("choose minimum time:");
                            choiceValidation=false;
                            do {
                                try {
                                    choiceValidation = true;
                                    timeChoice = scanner.nextInt();
                                    if (timeChoice < 0)
                                        throw new WrongInputException();
                                }catch (WrongInputException | InputMismatchException e)
                                {
                                    System.out.println("wrong input");
                                    System.out.println("please choose an integer number");
                                    choiceValidation=false;
                                    scanner.nextLine();
                                }
                            }while (!choiceValidation);
                            System.out.println("minimum time chosen: " + timeChoice);
                        } else {
                            System.out.println("no minimum time chosen");
                            timeChoice = 0;
                        }
                        List<Loan> matchingLoans = engine.findMatchingLoans(chosenCategory, amountToTake, interestChoice, timeChoice,0, user);
                        List<Loan> chosenLoans = new ArrayList<>();
                        if (matchingLoans.size() == 0)
                            System.out.println("no matching loans");
                        else {
                            System.out.println("matching loans:");
                            boolean finishedChoosing = false;
                            do {
                                if (matchingLoans.size() == 0) {
                                    finishedChoosing = true;
                                    System.out.println("no more matching loans");
                                } else {
                                    for (int i = 0; i < matchingLoans.size(); i++) {
                                        System.out.println(i + 1 + ")");
                                        matchingLoans.get(i).showLoanFullInfo();
                                    }
                                    System.out.println("choose a loan:");
                                    choiceValidation=false;
                                    do{
                                        try {
                                            choiceValidation = true;
                                            input = scanner.nextInt();
                                            if (input > matchingLoans.size() || input <= 0)
                                                throw new WrongInputException();
                                        }catch (WrongInputException | InputMismatchException e){
                                            System.out.println("wrong input");
                                            System.out.println("please choose a loan number");
                                            choiceValidation=false;
                                            scanner.nextLine();
                                        }
                                    }while (!choiceValidation);
                                    System.out.println("loan chosen:" + matchingLoans.get(input - 1).getId());
                                    chosenLoans.add(matchingLoans.get(input - 1));
                                    matchingLoans.remove(input - 1);
                                    System.out.println("whould you like to choose more loans?");
                                    System.out.println("1)yes  2)no");
                                    choiceValidation=false;
                                    int choice=0;
                                    do {
                                        try {
                                            choiceValidation = true;
                                            choice = scanner.nextInt();
                                            if(choice!=1 && choice!=2)
                                                throw new WrongInputException();
                                        }catch (WrongInputException | InputMismatchException e)
                                        {
                                            System.out.println("wrong input");
                                            System.out.println("please choose 1 or 2");
                                            choiceValidation=false;
                                            scanner.nextLine();
                                        }
                                    }while (!choiceValidation);
                                    if (choice == 1)
                                        finishedChoosing = false;
                                    else
                                        finishedChoosing = true;
                                }

                            } while (!finishedChoosing);

                           // engine.divideMoneyBetweenLoans(chosenLoans, amountToTake, user, engine.getCurrentTime());
                        }
                    break;
                case 6:
                    engine.advanceTime();
                    break;
                case 7:
                    System.out.println("enter File Path:");
                    System.out.println("note that a new file will overwrite the old file:");
                    boolean isFileloaded;
                    do {
                         xmlFilePath = scanner.nextLine();
                        if(xmlFilePath.equals("")) {
                            xmlFilePath = scanner.nextLine();
                        }
                        isFileloaded= engine.loadFromXml(xmlFilePath);
                    }while (!isFileloaded);
                    break;
                case 8:
                    exit=true;
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.println("wrong input please try again");
                    break;
            }
        }
    }

    private static void manu(Engine engine){
        System.out.println("current time:"+ engine.getCurrentTime());
        System.out.println("p1lease choose an action:");
        System.out.println("1. show all loans");
        System.out.println("2. show all clients");
        System.out.println("3. add money to account");
        System.out.println("4. take money from account");
        System.out.println("5. assign user to a loan");
        System.out.println("6. advance 1 time");
        System.out.println("7. load xml file");
        System.out.println("8. exit");
    }

}
