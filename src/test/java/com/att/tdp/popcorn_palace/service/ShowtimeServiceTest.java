package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import com.att.tdp.popcorn_palace.exception.BadRequestException;
import com.att.tdp.popcorn_palace.exception.NotFoundException;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ShowtimeServiceTest {

    @Mock
    private ShowtimeRepository showtimeRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ShowtimeService showtimeService;

    private ShowtimeDTO showtimeDTO;
    private Movie movie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create test data
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");
        movie.setGenre("Drama");
        movie.setDuration(120);
        movie.setRating("PG-13");
        movie.setReleaseYear(2020);

        showtimeDTO = new ShowtimeDTO();
        showtimeDTO.setMovieId(1L);
        showtimeDTO.setStartTime(LocalDateTime.of(2025, 3, 30, 10, 0));
        showtimeDTO.setEndTime(LocalDateTime.of(2025, 3, 30, 12, 0));
        showtimeDTO.setPrice(10.0);
        showtimeDTO.setTheater("Cineplex Downtown");
    }

    @Test
    void createShowtime_ShouldReturnShowtime_WhenValidInput() {
        // Arrange
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("Test Movie");
        movie.setGenre("Drama");
        movie.setDuration(120);
        movie.setRating("PG");
        movie.setReleaseYear(2022);
        
        ShowtimeDTO showtimeDTO = new ShowtimeDTO();
        showtimeDTO.setMovieId(movieId);
        showtimeDTO.setStartTime(LocalDateTime.of(2025, 3, 30, 10, 0));
        showtimeDTO.setEndTime(LocalDateTime.of(2025, 3, 30, 12, 0));
        showtimeDTO.setPrice(15.00);
        showtimeDTO.setTheater("IMAX");
    
        // Mock the behavior of the movie repository
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));  // Mocking findById to return the movie with ID 1
        when(movieRepository.existsById(movieId)).thenReturn(true);  // Ensure that existsById returns true for the movie
        
        // Mock the showtime repository to return no overlapping showtimes
        when(showtimeRepository.findByTheaterAndStartTimeBetween(anyString(), any(), any())).thenReturn(List.of());
    
        // Mock the save operation to return the same Showtime object after saving
        when(showtimeRepository.save(any(Showtime.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
        // Act
        Showtime createdShowtime = showtimeService.createShowtime(showtimeDTO);
    
        // Assert
        assertNotNull(createdShowtime);  // Check that the result is not null
        assertEquals(showtimeDTO.getStartTime(), createdShowtime.getStartTime());
        assertEquals(movie, createdShowtime.getMovie());
        verify(showtimeRepository, times(1)).save(any(Showtime.class));  // Verify that save was called on the repository
    }
    


    @Test
    void createShowtime_ShouldThrowBadRequestException_WhenEndTimeIsBeforeStartTime() {
        // Arrange
        showtimeDTO.setEndTime(LocalDateTime.of(2025, 3, 30, 8, 0));  // Invalid end time

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            showtimeService.createShowtime(showtimeDTO);
        });

        assertEquals("End time cannot be before start time", exception.getMessage());
    }
    
    @Test
    void deleteShowtime_ShouldThrowNotFoundException_WhenShowtimeDoesNotExist() {
        // Arrange
        when(showtimeRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            showtimeService.deleteShowtime(1L);
        });

        assertEquals("Showtime not found", exception.getMessage());
    }

    @Test
    void deleteShowtime_ShouldDeleteShowtime_WhenShowtimeExists() {
        // Arrange
        when(showtimeRepository.existsById(1L)).thenReturn(true);

        // Act
        showtimeService.deleteShowtime(1L);

        // Assert
        verify(showtimeRepository, times(1)).deleteById(1L);
    }
}
