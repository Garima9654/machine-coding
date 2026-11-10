package org.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Dependency-free tests: run main() in your IDE or use run-tests.bat/run-tests.sh. */
public class ParkingLotTest {
    private static int passed;
    private static int failed;

    public static void main(String[] args) throws Exception {
        Path project = args.length == 0 ? Path.of("") : Path.of(args[0]);
        if (!Files.exists(project.resolve("sample-input.txt"))
                && Files.exists(project.resolve("parking-lot/sample-input.txt"))) {
            project = project.resolve("parking-lot");
        }

        check("Full supplied sample", Files.readString(project.resolve("sample-input.txt")),
                Files.readString(project.resolve("expected-output.txt")));

        check("Allocate lowest floor and slot, then report full", """
                create_parking_lot P 2 4
                park_vehicle CAR C1 black
                park_vehicle CAR C2 white
                park_vehicle CAR C3 red
                exit
                """, """
                Created parking lot with 2 floors and 4 slots per floor
                Parked vehicle. Ticket ID: P_1_4
                Parked vehicle. Ticket ID: P_2_4
                Parking Lot Full
                """);

        check("Unpark once, reject invalid tickets, reuse freed slot", """
                create_parking_lot P 1 4
                park_vehicle CAR KA-01 black
                unpark_vehicle P_1_4
                unpark_vehicle P_1_4
                unpark_vehicle P_1_9
                unpark_vehicle OTHER_1_4
                unpark_vehicle malformed
                park_vehicle CAR KA-02 white
                unpark_vehicle P_1_4
                exit
                """, """
                Created parking lot with 1 floors and 4 slots per floor
                Parked vehicle. Ticket ID: P_1_4
                Unparked vehicle with Registration Number: KA-01 and Color: black
                Invalid Ticket
                Invalid Ticket
                Invalid Ticket
                Invalid Ticket
                Parked vehicle. Ticket ID: P_1_4
                Unparked vehicle with Registration Number: KA-02 and Color: white
                """);

        check("Bike and truck registration and color", """
                create_parking_lot P 1 3
                park_vehicle BIKE BIKE-01 blue
                park_vehicle TRUCK TRUCK-01 orange
                unpark_vehicle P_1_2
                unpark_vehicle P_1_1
                exit
                """, """
                Created parking lot with 1 floors and 3 slots per floor
                Parked vehicle. Ticket ID: P_1_2
                Parked vehicle. Ticket ID: P_1_1
                Unparked vehicle with Registration Number: BIKE-01 and Color: blue
                Unparked vehicle with Registration Number: TRUCK-01 and Color: orange
                """);

        check("One-slot floor and strict vehicle types", """
                create_parking_lot P 1 1
                park_vehicle CAR C black
                park_vehicle BIKE B black
                park_vehicle TRUCK T green
                park_vehicle TRUCK T2 white
                display free_count CAR
                display free_count BIKE
                display occupied_slots TRUCK
                exit
                """, """
                Created parking lot with 1 floors and 1 slots per floor
                Parking Lot Full
                Parking Lot Full
                Parked vehicle. Ticket ID: P_1_1
                Parking Lot Full
                No. of free slots for CAR on Floor 1: 0
                No. of free slots for BIKE on Floor 1: 0
                Occupied slots for TRUCK on Floor 1: 1
                """);
check("Reuse lowest freed slot before moving to next floor", """
                create_parking_lot P 2 6
                park_vehicle CAR C1 red
                park_vehicle CAR C2 red
                park_vehicle CAR C3 red
                park_vehicle CAR C4 red
                unpark_vehicle P_1_5
                unpark_vehicle P_1_4
                park_vehicle CAR C5 blue
                park_vehicle CAR C6 green
                display occupied_slots CAR
                exit
                """, """
                Created parking lot with 2 floors and 6 slots per floor
                Parked vehicle. Ticket ID: P_1_4
                Parked vehicle. Ticket ID: P_1_5
                Parked vehicle. Ticket ID: P_1_6
                Parked vehicle. Ticket ID: P_2_4
                Unparked vehicle with Registration Number: C2 and Color: red
                Unparked vehicle with Registration Number: C1 and Color: red
                Parked vehicle. Ticket ID: P_1_4
                Parked vehicle. Ticket ID: P_1_5
                Occupied slots for CAR on Floor 1: 4,5,6
                Occupied slots for CAR on Floor 2: 4
                """);

check("Invalid commands preserve parking state", """
                display free_count CAR
                create_parking_lot P 0 6
                create_parking_lot P 1 4
                park_vehicle PLANE X red
                park_vehicle CAR
                display invalid CAR
                display free_count PLANE
                unknown_command
                display free_count CAR
                exit
                """, """
                Create a parking lot first
                Floor and slot counts must be positive
                Created parking lot with 1 floors and 4 slots per floor
                Invalid vehicle type
                Invalid command
                Invalid display type
                Invalid vehicle type
                Invalid command
                No. of free slots for CAR on Floor 1: 1
                """);

check("Whitespace, blank lines and EOF without exit", "\n  create_parking_lot   P  1  4  \n\n"
              + "\tpark_vehicle\tCAR\tC1\tred\n", """
                Created parking lot with 1 floors and 4 slots per floor
                Parked vehicle. Ticket ID: P_1_4
                """);

check("Exit ignores later commands", "exit\ncreate_parking_lot P 1 4\n", "");

check("Creating a new lot resets tickets", """
                create_parking_lot OLD 1 4
                park_vehicle CAR C1 red
                create_parking_lot NEW 1 4
                unpark_vehicle OLD_1_4
                park_vehicle CAR C2 green
                exit
                """, """
                Created parking lot with 1 floors and 4 slots per floor
                Parked vehicle. Ticket ID: OLD_1_4
                Created parking lot with 1 floors and 4 slots per floor
                Invalid Ticket
                Parked vehicle. Ticket ID: NEW_1_4
                """);

        System.out.println("\nResults: " + passed + " passed, " + failed + " failed");
        if (failed > 0) {
        throw new AssertionError("Parking lot tests failed");
        }
                }

private static void check(String name, String input, String expected) {
    try {
        String actual = runCommands(input);
        // Normalize Windows newlines, retaining all other whitespace, including blank slot lists.
        if (!normalize(expected).equals(normalize(actual))) {
            throw new AssertionError("Expected:\n" + expected + "Actual:\n" + actual);
        }
        passed++;
        System.out.println("PASS: " + name);
    } catch (Throwable error) {
        failed++;
        System.out.println("FAIL: " + name);
        error.printStackTrace(System.out);
    }
}

private static String runCommands(String commands) throws Exception {
    InputStream originalInput = System.in;
    PrintStream originalOutput = System.out;
    ByteArrayOutputStream captured = new ByteArrayOutputStream();
    try (PrintStream output = new PrintStream(captured, true, StandardCharsets.UTF_8)) {
        System.setIn(new ByteArrayInputStream(commands.getBytes(StandardCharsets.UTF_8)));
        System.setOut(output);
        Main.main(new String[0]);
    } finally {
        System.setIn(originalInput);
        System.setOut(originalOutput);
    }
    return captured.toString(StandardCharsets.UTF_8);
}

private static String normalize(String text) {
    return text.replace("\r\n", "\n");
}
}
