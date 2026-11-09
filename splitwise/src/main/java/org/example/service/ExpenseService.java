package org.example.service;

import org.example.Repository;
import org.example.model.ExpenseType;
import org.example.model.User;
import org.example.model.expense.*;
import org.example.model.split.EqualSplit;
import org.example.model.split.PercentageSplit;
import org.example.model.split.Split;

import java.util.List;

public class ExpenseService {
    Repository repository;
    public ExpenseService(Repository repository){
        this.repository= repository;
    }
    public static Expense createExpense(ExpenseType expenseType, double amount,
                                        User expensePaidBy, List<Split> splits, ExpenseData expenseData){
        switch (expenseType){
            case EXACT:
                return new ExactExpense(amount, expensePaidBy, splits, expenseData);
            case PERCENTAGE:
                for(Split split: splits){
                    PercentageSplit percentageSplit= (PercentageSplit) split;
                    split.setAmount((amount*percentageSplit.getPercent())/100.0);
                }
                return new PercentageExpense(amount, expensePaidBy, splits, expenseData);
            case EQUAL:
                int size= splits.size();
                Double amountToDist = ((double) Math.round(amount*100/size))/100.0;
                for(Split split: splits){
                    split.setAmount(amountToDist);
                }
                return new EqualExpense(amount, expensePaidBy, splits, expenseData);
            default:
                return null;
        }
    }

}
