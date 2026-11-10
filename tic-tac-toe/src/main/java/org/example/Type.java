package org.example;

public enum Type {
    X,O;

    public static Type of(String value){
        for(Type type: Type.values()){
            if(type.name().equalsIgnoreCase(value)){
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid type: " + value);
    }

    public static boolean isValid(String value){
        for(Type type: Type.values()){
            if(type.name().equalsIgnoreCase(value)){
                return true;
            }
        }
        return false;
    }
}
