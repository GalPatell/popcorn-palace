package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.exception.BadRequestException;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setTitle("Inception");
        movie.setGenre("Sci-Fi");
        movie.setDuration(148);
        movie.setRating("PG-13");
        movie.setReleaseYear(2010);
    }

    @Test
    void testCreateMovieThrowsBadRequestExceptionWhenTitleExists() {
        // Arrange
        when(movieRepository.existsByTitle(movie.getTitle())).thenReturn(true);

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> movieService.createMovie(movie));
        assertEquals("Movie with this title already exists", exception.getMessage());

        // Verify
        verify(movieRepository, times(1)).existsByTitle(movie.getTitle());
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void testCreateMovieSavesAndReturnsMovieWhenTitleDoesNotExist() {
        // Arrange
        when(movieRepository.existsByTitle(movie.getTitle())).thenReturn(false);
        when(movieRepository.save(movie)).thenReturn(movie);

        // Act
        Movie savedMovie = movieService.createMovie(movie);

        // Assert
        assertNotNull(savedMovie);
        assertEquals(movie.getTitle(), savedMovie.getTitle());
        assertEquals(movie.getGenre(), savedMovie.getGenre());
        assertEquals(movie.getDuration(), savedMovie.getDuration());
        assertEquals(movie.getRating(), savedMovie.getRating());
        assertEquals(movie.getReleaseYear(), savedMovie.getReleaseYear());

        // Verify
        verify(movieRepository, times(1)).existsByTitle(movie.getTitle());
        verify(movieRepository, times(1)).save(movie);
    }
}