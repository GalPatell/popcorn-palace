package com.att.tdp.popcorn_palace.dto;

public class BookTicketsDTO {

    private Long showtimeId;
    private String seatNumber;

    // Getters and Setters
    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setCustomerName(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}