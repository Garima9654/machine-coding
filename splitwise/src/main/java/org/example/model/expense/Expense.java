package org.example.model.expense;

import org.example.model.User;
import org.example.model.split.Split;

import java.util.List;
import java.util.UUID;

public abstract class Expense {
    public final String id;
    public User user;
    public Double amount;
    public List<Split> splits;
    public ExpenseData expenseData;

    public Expense(Double amount, User user, List<Split> splits, ExpenseData expenseData) {
        this.id = UUID.randomUUID().toString();
        this.amount= amount;
        this.user= user;
        this.splits= splits;
        this.expenseData= expenseData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ExpenseData getExpenseData() {
        return expenseData;
    }

    public void setExpenseData(ExpenseData expenseData) {
        this.expenseData = expenseData;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }

    public abstract boolean validate();

}
