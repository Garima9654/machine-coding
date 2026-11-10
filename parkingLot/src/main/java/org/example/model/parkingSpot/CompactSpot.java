package org.example.model.parkingSpot;

import org.example.model.vehicle.Vehicle;
import org.example.model.vehicle.VehicleSize;

public class CompactSpot implements ParkingSpot{
    private final int floorNumber;
    private int spotId;
    private Vehicle vehicle;

    public CompactSpot(int spotId){
        this(1, spotId);
    }

    public CompactSpot(int floorNumber, int spotId) {
        this.floorNumber = floorNumber;
        this.spotId = spotId;
    }

    @Override
    public boolean isAvailable() {
        return vehicle==null;
    }

    @Override
    public void occupy(Vehicle vehicle) {
        if (!isAvailable()) {
            throw new IllegalStateException("Spot is already occupied");
        }
        if (vehicle == null || vehicle.getSize() != getSize()) {
            throw new IllegalArgumentException("Vehicle does not match spot type");
        }
        this.vehicle = vehicle;
    }

    @Override
    public void vacate() {
        this.vehicle=null;
    }

    @Override
    public int getSpotNumber() {
        return spotId;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.SMALL;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
