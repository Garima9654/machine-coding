package org.example.model.expense;

import org.example.model.User;
import org.example.model.split.PercentageSplit;
import org.example.model.split.Split;

import java.util.List;

public class PercentageExpense extends Expense{
    public PercentageExpense(Double amount, User user, List<Split> splits, ExpenseData expenseData) {
        super(amount, user, splits, expenseData);
    }

    @Override
    public boolean validate() {
        Double finalPercent= 100.00;
        Double calc = 0.00;
        for (Split split: splits){
            if(!(split instanceof PercentageSplit percentageSplit)) return false;
            calc += percentageSplit.getPercent();
        }
        return finalPercent.equals(calc);
    }
}
