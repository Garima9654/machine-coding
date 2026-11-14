package org.example.entities;

import org.example.enums.SeatStatus;

import java.awt.print.Book;
import java.util.List;

public class Booking {
    private final String id;
    private final User user;
    private final Show show;
    private final List<Seat> seatList;
    private final double paymentAmount;
    private final Payment payment;

    private Booking(String id, User user, Show show, List<Seat> seatList, double paymentAmount, Payment payment) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seatList = seatList;
        this.paymentAmount = paymentAmount;
        this.payment = payment;
    }

    public void confirmBooking(){
        for (Seat seat: seatList){
            seat.setSeatStatus(SeatStatus.BOOKED);
        }
    }

    public String getId() { return id; }
    public User getUser() { return user; }
    public Show getShow() { return show; }
    public List<Seat> getSeatList() { return seatList; }
    public double getPaymentAmount() { return paymentAmount; }
    public Payment getPayment() { return payment; }

    public static class BookingBuilder{
        private String id;
        private User user;
        private Show show;
        private List<Seat> seatList;
        private double paymentAmount;
        private Payment payment;

        public BookingBuilder setId(String id){
            this.id= id;
            return this;
        }

        public BookingBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public BookingBuilder setShow(Show show) {
            this.show = show;
            return this;
        }

        public BookingBuilder setSeatList(List<Seat> seatList) {
            this.seatList = seatList;
            return this;
        }

        public BookingBuilder setPaymentAmount(double paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        public BookingBuilder setPayment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Booking build(){
            return new Booking(id, user, show, seatList, paymentAmount, payment);
        }
    }
}
