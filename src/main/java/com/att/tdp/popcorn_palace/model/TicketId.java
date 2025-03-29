package com.att.tdp.popcorn_palace.model;
import java.util.Objects;

public class TicketId  {

    private String seatNumber;
    private Long showtime;

    // Default constructor
    public TicketId() {}

    // Constructor
    public TicketId(String seatNumber, Long showtime) {
        this.seatNumber = seatNumber;
        this.showtime = showtime;
    }

    // Getters and Setters
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Long getShowtime() {
        return showtime;
    }

    public void setShowtime(Long showtime) {
        this.showtime = showtime;
    }

    // Override equals() and hashCode() for composite key consistency
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketId ticketId = (TicketId) o;
        return Objects.equals(seatNumber, ticketId.seatNumber) &&
               Objects.equals(showtime, ticketId.showtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatNumber, showtime);
    }
}