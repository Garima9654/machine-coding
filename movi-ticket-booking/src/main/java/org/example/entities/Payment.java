package org.example.entities;

import org.example.enums.PaymentStatus;

import java.util.List;
import java.util.UUID;

public class Payment {
    private final String id;
    private final double amount;
    private final PaymentStatus paymentStatus;
    private final String transactionId;

    public Payment(double amount, PaymentStatus paymentStatus, String transactionId) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
