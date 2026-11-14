package org.example.entities;

import org.example.enums.SeatStatus;
import org.example.enums.SeatType;

import java.util.UUID;

public class Seat {
    private final String id;
    private final int row;
    private final int column;
    private final SeatType seatType;
    private SeatStatus seatStatus;

    public Seat(int row, int column, SeatType seatType, SeatStatus seatStatus) {
        this.id = UUID.randomUUID().toString();
        this.row = row;
        this.column = column;
        this.seatType = seatType;
        this.seatStatus = seatStatus;
    }

    public String getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public SeatType getSeatType() {
        return seatType;
    }
}
