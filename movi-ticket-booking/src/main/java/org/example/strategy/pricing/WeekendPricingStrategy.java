package org.example.strategy.pricing;

import org.example.entities.Seat;

import java.util.List;

public class WeekendPricingStrategy implements PricingStrategy{
    private static final double surgeCharges=1.2;

    @Override
    public double calculatePrice(List<Seat> seats) {
        double amount = seats.stream().
                mapToDouble(seat -> seat.getSeatType().getPrice()).
                sum();

        return amount*surgeCharges;
    }
}
