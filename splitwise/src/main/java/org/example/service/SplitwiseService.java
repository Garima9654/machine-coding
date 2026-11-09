package org.example.service;

import org.example.Repository;
import org.example.model.ExpenseType;
import org.example.model.User;
import org.example.model.expense.Expense;
import org.example.model.expense.ExpenseData;
import org.example.model.split.Split;

import java.util.List;

public class SplitwiseService {
    Repository repository;

    public SplitwiseService(Repository repository){
        this.repository= repository;
    }

    public void addExpense(ExpenseType expenseType, double amount, String paidBy, List<Split> splits, ExpenseData expenseData){
        User user = repository.getUser(paidBy);
        Expense expense = ExpenseService.createExpense(expenseType, amount,user, splits, expenseData);
        assert expense != null;
        System.out.println("expense data: "+ expense.getAmount());
        repository.addExpense(expense);
    }

    public void showBalance(String userName) {
        List<String> balances = repository.getBalance(userName);
        if (balances.isEmpty()) {
            System.out.println("No balances");
        } else {
            for(String balance: balances){
                System.out.println(balance);
            }
        }
    }

    public void showBalances(){
        List<String> balances = repository.getBalances();
        if (balances.isEmpty()) {
            System.out.println("No balances");
        } else {
            for(String balance: balances){
                System.out.println(balance);
            }
        }
    }
}
