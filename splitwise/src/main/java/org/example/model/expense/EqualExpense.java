package org.example.model.expense;

import org.example.model.User;
import org.example.model.split.EqualSplit;
import org.example.model.split.Split;

import java.util.List;

public class EqualExpense extends Expense{
    public EqualExpense(Double amount, User user, List<Split> splits, ExpenseData expenseData) {
        super(amount, user, splits, expenseData);
    }

    @Override
    public boolean validate() {
        for (Split split: splits){
            if(!(split instanceof EqualSplit)) return false;
        }
        return true;
    }
}
