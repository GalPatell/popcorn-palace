package com.att.tdp.popcorn_palace.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.model.Ticket;
import com.att.tdp.popcorn_palace.dto.BookTicketsDTO;
import com.att.tdp.popcorn_palace.repository.TicketRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.exception.BadRequestException;
import com.att.tdp.popcorn_palace.exception.NotFoundException;

@Service
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ShowtimeRepository showtimeRepository;
    
    public TicketService(TicketRepository ticketRepository, ShowtimeRepository showtimeRepository) {
        this.ticketRepository = ticketRepository;
        this.showtimeRepository = showtimeRepository;
    }
    
    public Ticket bookTicket(BookTicketsDTO bookTicketsDTO) {
        Showtime showtime = showtimeRepository.findById(bookTicketsDTO.getShowtimeId())
            .orElseThrow(() -> new NotFoundException("Showtime not found"));
            
        if (ticketRepository.existsByShowtimeAndSeatNumber(showtime, bookTicketsDTO.getSeatNumber())) {
            throw new BadRequestException("This seat is already booked");
        }
        
        Ticket ticket = new Ticket();
        ticket.setShowtime(showtime);
        ticket.setSeatNumber(bookTicketsDTO.getSeatNumber());
        ticket.setBookingTime(LocalDateTime.now());
        
        return ticketRepository.save(ticket);
    }
}

