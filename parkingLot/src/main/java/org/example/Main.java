package org.example;

import org.example.model.Ticket;
import org.example.model.vehicle.*;
import org.example.service.ParkingLot;
import org.example.service.fare.BaseFareStrategy;
import org.example.service.fare.FareCalculator;
import org.example.service.fare.PeakHourStrategy;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = null;
        try(Scanner scanner = new Scanner(System.in)){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                if(line.isEmpty()){
                    continue;
                }
                String[] parts = line.split("\\s+");
                if(parts[0].equals("exit")){
                    break;
                }
                try{
                    switch (parts[0]){
                        case "create_parking_lot"->{
                            requireLength(parts, 4);
                            int floors= Integer.parseInt(parts[2]);
                            int slots= Integer.parseInt(parts[3]);
                            Repository repository = new Repository(floors, slots);
                            FareCalculator fareCalculator =new FareCalculator(
                                    List.of(new BaseFareStrategy(), new PeakHourStrategy())
                            );
                            parkingLot = new ParkingLot(parts[1], repository, fareCalculator);
                            System.out.println("Created parking lot with " + floors
                                    + " floors and " + slots + " slots per floor");
                        }
                        case "park_vehicle" -> {
                            requireLength(parts, 4);
                            requireParkingLot(parkingLot);
                            Vehicle vehicle= createVehicle(parts[1], parts[2], parts[3]);
                            Ticket ticket = parkingLot.enterVehicle(vehicle);
                            System.out.println(ticket == null ? "Parking Lot Full"
                                    : "Parked vehicle. Ticket ID: " + ticket.getTicketId());
                        }
                        case "unpark_vehicle" -> {
                            requireLength(parts, 2);
                            requireParkingLot(parkingLot);
                            Ticket ticket = parkingLot.leaveVehicle(parts[1]);
                            if (ticket == null) {
                                System.out.println("Invalid Ticket");
                            } else {
                                System.out.println("Unparked vehicle with Registration Number: "
                                        + ticket.getVehicle().getLicensePlate() + " and Color: "
                                        + ticket.getVehicle().getColor());
                            }
                        }
                        case "display" -> {
                            requireLength(parts, 3);
                            requireParkingLot(parkingLot);
                            if (!List.of("free_count", "free_slots", "occupied_slots").contains(parts[1])) {
                                throw new IllegalArgumentException("Invalid display type");
                            }
                            parkingLot.display(parts[1], parts[2], sizeFor(parts[2]));
                        }
                        default -> System.out.println("Invalid command");
                    }
                } catch (IllegalArgumentException | IllegalStateException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }
    private static void requireLength(String[] parts, int expected) {
        if (parts.length != expected) {
            throw new IllegalArgumentException("Invalid command");
        }
    }

    private static void requireParkingLot(ParkingLot parkingLot) {
        if (parkingLot == null) {
            throw new IllegalStateException("Create a parking lot first");
        }
    }

    private static VehicleSize sizeFor(String type) {
        return switch (type) {
            case "CAR" -> VehicleSize.MEDIUM;
            case "BIKE" -> VehicleSize.SMALL;
            case "TRUCK" -> VehicleSize.LARGE;
            default -> throw new IllegalArgumentException("Invalid vehicle type");
        };
    }

    private static Vehicle createVehicle(String type, String registration, String color){
        return switch (type){
            case "CAR" -> new Car(color, registration);
            case "BIKE" -> new Bike(color, registration);
            case "TRUCK" -> new Truck(color, registration);
            default -> throw new IllegalArgumentException("Invalid vehicle type");
        };
    }
}