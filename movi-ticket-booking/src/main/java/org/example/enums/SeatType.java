package org.example.enums;

public enum SeatType {
    NORMAL(80.0), EXECUTIVE(120.0), PREMIUM(240.0);

    private final double price;

    SeatType(double price) {
        this.price = price;
    }

    public double getPrice(){
        return price;
    }
}
