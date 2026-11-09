package org.example;

import org.example.model.ExpenseType;
import org.example.model.Type;
import org.example.model.User;
import org.example.model.expense.Expense;
import org.example.model.expense.ExpenseData;
import org.example.model.split.EqualSplit;
import org.example.model.split.ExactSplit;
import org.example.model.split.PercentageSplit;
import org.example.model.split.Split;
import org.example.service.SplitwiseService;
import org.example.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Repository repository= new Repository();
        UserService userService= new UserService(repository);
        User user1 = new User(1, "u1","u1@gmail.com","9890098900");
        User user2 = new User(2, "u2","u2@gmail.com","9999999999");
        User user3 = new User(3, "u3","u3@gmail.com","9898989899");
        User user4 = new User(4, "u4","u4@gmail.com","8976478292");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);
        SplitwiseService splitwiseService= new SplitwiseService(repository);

        while(true){
            Scanner scan = new Scanner(System.in);
            String[] commnds= scan.nextLine().split(" ");
            Type type = Type.of(commnds[0]);

            switch (type){
                case EXPENSE:
                    String username = commnds[1];
                    Double amountSpend = Double.parseDouble(commnds[2]);
                    Integer totalMembers= Integer.parseInt(commnds[3]);
                    List<Split> splitList = new ArrayList<>();
                    int expenseIndex= 3 + totalMembers + 1;
                    ExpenseType expense= ExpenseType.of(commnds[expenseIndex]);
                    switch (expense){
                        case EQUAL:
                            for(int i=0; i< totalMembers; i++){
                                splitList.add(new EqualSplit(userService.getUser(commnds[4+i])));
                            }
                            splitwiseService.addExpense(ExpenseType.EQUAL, amountSpend, username, splitList, new ExpenseData("GoaTrip"));
                            break;
                        case EXACT:
                            for(int i=0; i<totalMembers; i++){
                                System.out.println("user: "+ commnds[4+i] + " amount: ");
                                splitList.add(new ExactSplit(userService.getUser(commnds[4+i]), Double.parseDouble(commnds[expenseIndex+i+1])));
                            }
                            splitwiseService.addExpense(ExpenseType.EXACT, amountSpend, username, splitList, new ExpenseData("Grocery"));
                            break;
                        case PERCENTAGE:
                            for(int i=0; i<totalMembers; i++){
                                splitList.add(new PercentageSplit(userService.getUser(commnds[4+i]), Double.parseDouble(commnds[expenseIndex+i+1])));
                            }
                            splitwiseService.addExpense(ExpenseType.PERCENTAGE, amountSpend, username, splitList, new ExpenseData("Lunch"));
                            break;
                    }
                    break;
                case SHOW:
                    if(commnds.length == 1){
                        splitwiseService.showBalances();
                    }
                    else
                        splitwiseService.showBalance(commnds[1]);
                    break;
                case QUIT:
                    System.out.println("Quiting...");
                    return;
                default:
                    System.out.println("No Expected Argument Found");
                    break;
            }
        }
    }
}