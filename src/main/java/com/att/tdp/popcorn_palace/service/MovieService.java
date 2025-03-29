package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.exception.BadRequestException;
import com.att.tdp.popcorn_palace.exception.NotFoundException;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    
    public Movie createMovie(Movie movie) {
        if (movieRepository.existsByTitle(movie.getTitle())) {
            throw new BadRequestException("Movie with this title already exists");
        }
        return movieRepository.save(movie);
    }
    
    public Movie updateMovie(Long id, Movie movie) {
        Movie existingMovie = movieRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Movie not found"));
            
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setDuration(movie.getDuration());
        existingMovie.setRating(movie.getRating());
        existingMovie.setReleaseYear(movie.getReleaseYear());
        
        return movieRepository.save(existingMovie);
    }
    
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException("Movie not found");
        }
        movieRepository.deleteById(id);
    }
    
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}

