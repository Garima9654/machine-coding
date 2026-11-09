package org.example.model;

import java.util.HashMap;

public enum ExpenseType {
    EXACT("EXACT"),
    EQUAL("EQUAL"),
    PERCENTAGE("PERCENTAGE");

    private final String name;

    ExpenseType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private final static HashMap<String, ExpenseType> map = new HashMap<>(values().length, 1);
    static{
        for(ExpenseType t: values()) map.put(t.name, t);
    }

    public static ExpenseType of(String expense){
        return map.get(expense.trim());
    }
}
