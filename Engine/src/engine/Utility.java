package engine;

import loan.Loan;

import java.util.List;

public class Utility {

    public Loan findMinBeAmount(List<Loan> loans){
        int min=loans.get(0).getLoanStartAmount()-loans.get(0).getLoanCurrentAmount();
        Loan minLoan=loans.get(0);
        for(int i=0;i<loans.size();i++){
            if((loans.get(i).getLoanStartAmount()-loans.get(i).getLoanCurrentAmount()) < min){
                minLoan=loans.get(i);
                min=minLoan.getLoanStartAmount()-loans.get(i).getLoanCurrentAmount();
            }
        }
        return minLoan;

    }
}
