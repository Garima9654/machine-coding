package org.example.model.split;

import org.example.model.User;

public class ExactSplit extends Split{
    public ExactSplit(User user, Double amount) {
        super(user);
        this.amount= amount;
    }
}
