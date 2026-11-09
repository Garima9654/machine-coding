package org.example;

import org.example.model.ExpenseType;
import org.example.model.User;
import org.example.model.expense.Expense;
import org.example.model.expense.ExpenseData;
import org.example.model.split.Split;
import org.example.service.ExpenseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
    List<Expense> expenses;
    Map<String, User> users;
    Map<String, Map<String, Double>> balanceSheet;

    public Repository(){
        users= new HashMap<>();
        balanceSheet= new HashMap<>();
        expenses= new ArrayList<>();
    }

    public void addUser(User user){
        users.put(user.getUsername(), user);
        balanceSheet.put(user.getUsername(), new HashMap<>());
    }

    public User getUser(String username){
        return users.get(username);
    }

//    public void addExpense(Expense expense){
//        expenses.add(expense);
//    }
//not able to understand
    public void addExpense(Expense expense){
        String paidBy= expense.getUser().getUsername();
        System.out.println("paidByy: "+ paidBy);
        for(Split split: expense.splits){
            String paidTo= split.getUser().getUsername();
            Map<String, Double> balances=balanceSheet.get(paidBy);

            if(!balances.containsKey(paidTo)){
                balances.put(paidTo, 0.0);
            }
            balances.put(paidTo, balances.get(paidTo) + split.getAmount());

            balances = balanceSheet.get(paidTo);
            if(!balances.containsKey(paidBy)){
                balances.put(paidBy, 0.0);
            }

            balances.put(paidBy, balances.get(paidBy) - split.getAmount());
        }
    }

    public List<String> getBalance(String username){
        List<String> balances = new ArrayList<>();
        for(Map.Entry<String, Double> map: balanceSheet.get(username).entrySet()){
            if(map.getValue()!=0){
                balances.add(checkSign(username, map.getKey(), map.getValue()));
            }
        }
        return balances;
    }

    public List<String> getBalances(){
        List<String> balances = new ArrayList<>();
        for(Map.Entry<String, Map<String, Double>> mp: balanceSheet.entrySet()){
            for(Map.Entry<String, Double> map: mp.getValue().entrySet()){
                if(map.getValue()>0){
                    balances.add(checkSign(mp.getKey(), map.getKey(), map.getValue()));
                }
            }
        }
        return balances;
    }

    private String checkSign(String user1, String user2, double amount){
//        user2 owes user1
        String user1Name = users.get(user1).getUsername();
        String user2Name = users.get(user2).getUsername();
        if(amount>0){
            return user2Name + " owes " + user1Name + ": " + Math.abs(amount);
        }
        else if(amount<0){
            return user1Name + " owes " + user2Name + ": " + Math.abs(amount);
        }
        return "";
    }
}
