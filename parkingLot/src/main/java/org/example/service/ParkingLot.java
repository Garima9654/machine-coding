package org.example.service;

import org.example.Repository;
import org.example.model.ParkingFloor;
import org.example.model.Ticket;
import org.example.model.parkingSpot.ParkingSpot;
import org.example.model.vehicle.Vehicle;
import org.example.model.vehicle.VehicleSize;
import org.example.service.fare.FareCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private final String parkingLotId;
    Repository repository;
    FareCalculator fareCalculator;
    private final Map<String, Ticket> activeTickets = new HashMap<>();

    public ParkingLot(String parkingLotId, Repository repository, FareCalculator fareCalculator){
        this.parkingLotId = parkingLotId;
        this.repository= repository;
        this.fareCalculator= fareCalculator;
    }

    public Ticket enterVehicle(Vehicle vehicle){
        ParkingSpot parkingSpot= repository.parkVehicle(vehicle);
        if(parkingSpot!=null){
            String ticketId = parkingLotId +"_"+ parkingSpot.getFloorNumber() + "_" + parkingSpot.getSpotNumber();
            Ticket ticket = new Ticket(ticketId, vehicle, parkingSpot, LocalDateTime.now());
            activeTickets.put(ticketId, ticket);
            return ticket;
        } else{
            return null;
        }
    }

    public Ticket leaveVehicle(String ticketId){
        Ticket ticket = activeTickets.get(ticketId);
        if(ticket!=null){
            ticket.setExitTime(LocalDateTime.now());
            BigDecimal fare= fareCalculator.calculateFare(ticket);
            repository.unparkVehicle(ticket.getParkingSpot());
            ticket.setTicketFare(fare);
            activeTickets.remove(ticketId);
            return ticket;
        }
        else{
//            System.out.println("invalid Ticket");
            return null;
        }
    }

    public void display(String displayType, String vehicleType, VehicleSize size) {
        for (ParkingFloor floor : repository.getFloors()) {
            boolean available = !displayType.equals("occupied_slots");
            List<Integer> slots = floor.getSlotNumbers(size, available);
            String suffix = " for " + vehicleType + " on Floor " + floor.getFloorNumber() + ": ";
            if (displayType.equals("free_count")) {
                System.out.println("No. of free slots" + suffix + slots.size());
            } else {
                String label = available ? "Free slots" : "Occupied slots";
                String slotNumbers = slots.stream().map(String::valueOf)
                        .collect(java.util.stream.Collectors.joining(","));
                System.out.println(label + suffix + slotNumbers);
            }
        }
    }


}
