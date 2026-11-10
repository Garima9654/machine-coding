package org.example.model.parkingSpot;

import org.example.model.vehicle.Vehicle;
import org.example.model.vehicle.VehicleSize;

public interface ParkingSpot {
    boolean isAvailable();

    void occupy(Vehicle vehicle);

    void vacate();

    int getSpotNumber();

    VehicleSize getSize();
    int getFloorNumber();
}
