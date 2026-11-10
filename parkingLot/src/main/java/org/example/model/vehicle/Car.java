package org.example.model.vehicle;

public class Car implements Vehicle {
    private final String color;
    private final String licensePlate;

    public Car(String licensePlate) {
        this("", licensePlate);
    }

    public Car(String color, String licensePlate) {
        this.color = color;
        this.licensePlate = licensePlate;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.MEDIUM;
    }

    @Override
    public String getColor() {
        return color;
    }
}
