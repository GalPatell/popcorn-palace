package com.att.tdp.popcorn_palace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.IdClass;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@IdClass(TicketId.class)  // This is where you define your composite key
public class Ticket {

    @Id
    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @Id
    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;

    // Getters and setters
    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
}
