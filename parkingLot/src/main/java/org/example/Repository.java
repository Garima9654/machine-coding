package org.example;

import org.example.model.ParkingFloor;
import org.example.model.parkingSpot.ParkingSpot;
import org.example.model.vehicle.Vehicle;
import org.example.model.vehicle.VehicleSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
    private final List<ParkingFloor> floors = new ArrayList<>();

    public Repository(int floorCount, int slotsPerFloor) {
        if (floorCount < 1 || slotsPerFloor < 1) {
            throw new IllegalArgumentException("Floor and slot counts must be positive");
        }
        for (int floor = 1; floor <= floorCount; floor++) {
            floors.add(new ParkingFloor(floor, slotsPerFloor));
        }
    }

    public List<ParkingFloor> getFloors() { return List.copyOf(floors); }

    public ParkingSpot findSpotForVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findAvailableSpot(vehicle.getSize());
            if (spot != null) {
                return spot;
            }
        }
        return null;
    }

    public ParkingSpot parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = findSpotForVehicle(vehicle);
        if (spot != null) {
            spot.occupy(vehicle);
        }
        return spot;
    }

    public void unparkVehicle(ParkingSpot spot) { spot.vacate(); }
}
