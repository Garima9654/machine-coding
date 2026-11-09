package org.example.model.expense;

import org.example.model.User;
import org.example.model.split.ExactSplit;
import org.example.model.split.Split;

import java.util.List;

public class ExactExpense extends Expense{
    public ExactExpense(Double amount, User user, List<Split> splits, ExpenseData expenseData) {
        super(amount, user, splits, expenseData);
    }
    @Override
    public boolean validate(){
        Double totalAmount = amount;
        Double splitAmount = 0.0;
        for(Split split: splits){
            if(!(split instanceof ExactSplit exactSplit)) return false;
            splitAmount+= exactSplit.getAmount();
        }
        return splitAmount.equals(totalAmount);
    }
}
