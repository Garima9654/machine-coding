package org.example.model.vehicle;

public class Truck implements Vehicle{
    private final String color;
    private final String licensePlate;

    public Truck(String licensePlate){
        this("", licensePlate);
    }

    public Truck(String color, String licensePlate) {
        this.color = color;
        this.licensePlate = licensePlate;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.LARGE;
    }

    @Override
    public String getColor() {
        return color;
    }
}
