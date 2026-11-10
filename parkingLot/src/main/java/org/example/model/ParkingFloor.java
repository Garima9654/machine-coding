package org.example.model;

import org.example.model.parkingSpot.CompactSpot;
import org.example.model.parkingSpot.OversizedSpot;
import org.example.model.parkingSpot.ParkingSpot;
import org.example.model.parkingSpot.RegularSpot;
import org.example.model.vehicle.Vehicle;
import org.example.model.vehicle.VehicleSize;

import java.util.ArrayList;
import java.util.List;

public class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSpot> spots= new ArrayList<>();

    public ParkingFloor(int floorNumber, int slotsPerFloor) {
        if(floorNumber<1 || slotsPerFloor<1){
            throw new IllegalArgumentException("Floor and slot counts must be positive");
        }
        this.floorNumber= floorNumber;
        for(int slot=1; slot<= slotsPerFloor; slot++){
            if(slot == 1){
                spots.add(new OversizedSpot(floorNumber, slot));
            }
            else if(slot>=2 && slot<=3){
                spots.add(new CompactSpot(floorNumber, slot));
            }
            else{
                spots.add(new RegularSpot(floorNumber, slot));
            }
        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ParkingSpot findAvailableSpot(VehicleSize size){
        for(ParkingSpot parkingSpot: spots){
            if(parkingSpot.getSize() == size && parkingSpot.isAvailable()){
                return  parkingSpot;
            }
        }
        return null;
    }

    public List<Integer> getSlotNumbers(VehicleSize size, boolean available){
        List<Integer> numbers = new ArrayList<>();
        for (ParkingSpot spot: spots){
            if(spot.getSize() == size && spot.isAvailable() == available){
                numbers.add(spot.getSpotNumber());
            }
        }
        return numbers;
    }

}
