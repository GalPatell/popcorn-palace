package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.model.Ticket;
import com.att.tdp.popcorn_palace.dto.BookTicketsDTO; // Ensure this import is correct and the class exists
import com.att.tdp.popcorn_palace.service.TicketService;    
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    @PostMapping
    public ResponseEntity<Ticket> bookTicket(
        @RequestBody BookTicketsDTO bookTicketsDTO) {
        return ResponseEntity.ok(ticketService.bookTicket(bookTicketsDTO));
    }
}

