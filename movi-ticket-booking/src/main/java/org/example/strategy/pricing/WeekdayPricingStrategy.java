package org.example.strategy.pricing;

import org.example.entities.Seat;
import org.example.enums.SeatType;

import java.util.List;

public class WeekdayPricingStrategy implements PricingStrategy{
    @Override
    public double calculatePrice(List<Seat> seats) {
        return seats.stream().
                mapToDouble(seat-> seat.getSeatType().getPrice()).
                sum();
    }
}
