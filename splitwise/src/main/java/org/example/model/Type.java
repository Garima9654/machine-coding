package org.example.model;

import java.util.HashMap;
import java.util.Map;

public enum Type {
    EXPENSE ("EXPENSE"),
    SHOW("SHOW"),
    QUIT("QUIT");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final HashMap<String, Type> map= new HashMap<>(values().length, 1);

    static{
        for(Type t: values()) map.put(t.name, t);
    }

    public static Type of(String type){
        return map.get(type.trim());
    }
}
