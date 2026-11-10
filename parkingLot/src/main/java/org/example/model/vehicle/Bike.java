package org.example.model.vehicle;

public class Bike implements Vehicle{
    private final String color;
    private final String licensePlate;

    public Bike(String licensePlate){
        this("", licensePlate);
    }

    public Bike(String color, String licensePlate) {
        this.color = color;
        this.licensePlate = licensePlate;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.SMALL;
    }

    @Override
    public String getColor() {
        return color;
    }
}
