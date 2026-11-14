package org.example.strategy.payment;

import org.example.entities.Payment;

public interface PaymentStrategy {
     Payment pay(double amount);
}
