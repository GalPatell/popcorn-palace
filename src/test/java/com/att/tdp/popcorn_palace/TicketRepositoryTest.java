package com.att.tdp.popcorn_palace;

import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.model.Ticket;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    private Movie movie;
    private Showtime showtime;

    @BeforeEach
    void setUp() {
        // Set up Movie
        movie = new Movie();
        movie.setTitle("The Dark Knight");
        movie.setGenre("Action");
        movie.setDuration(152);
        movie.setRating("PG-13");
        movie.setReleaseYear(2008);
        movie = movieRepository.save(movie);

        // Set up Showtime
        showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setTheater("IMAX Theater");
        showtime.setPrice(15.0);
        showtime.setStartTime(LocalDateTime.of(2025, 3, 30, 12, 0, 0, 0));
        showtime.setEndTime(LocalDateTime.of(2025, 3, 30, 14, 30, 0, 0));
        showtime = showtimeRepository.save(showtime);
    }

    @Test
    void testSaveTicket() {
        // Create and save ticket
        Ticket ticket = new Ticket();
        ticket.setSeatNumber("A1");
        ticket.setShowtime(showtime);
        ticket.setBookingTime(LocalDateTime.of(2025, 3, 29, 14, 0, 0, 0));

        Ticket savedTicket = ticketRepository.save(ticket);

        // Assert that ticket is saved successfully
        assertThat(savedTicket).isNotNull();
        assertThat(savedTicket.getSeatNumber()).isEqualTo("A1");
        assertThat(savedTicket.getShowtime()).isEqualTo(showtime);
    }

    @Test
    void testFindTicketBySeatNumberAndShowtimeId() {
        // Create and save ticket
        Ticket ticket = new Ticket();
        ticket.setSeatNumber("A2");
        ticket.setShowtime(showtime);
        ticket.setBookingTime(LocalDateTime.of(2025, 3, 29, 15, 0, 0, 0));
        ticketRepository.save(ticket);

        // Retrieve ticket
        boolean foundTicket = ticketRepository.existsByShowtimeAndSeatNumber(showtime, "A2");

        // Assert ticket is found
        assertThat(foundTicket).isTrue();
    }

    @Test
    void testTicketNotFound() {
        // Try to find a non-existing ticket
        // Retrieve ticket
        boolean foundTicket = ticketRepository.existsByShowtimeAndSeatNumber(showtime, "A44");

        // Assert ticket is found
        assertThat(foundTicket).isTrue();
    }
}
