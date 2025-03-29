package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import com.att.tdp.popcorn_palace.exception.BadRequestException;
import com.att.tdp.popcorn_palace.model.Movie;
import com.att.tdp.popcorn_palace.model.Showtime;   
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.att.tdp.popcorn_palace.exception.NotFoundException;


@Service
@Transactional
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    
    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }
    
    public Showtime createShowtime(ShowtimeDTO showtimeDTO) {
        validateShowtime(showtimeDTO);
        
        // Check for overlapping showtimes
        List<Showtime> overlappingShowtimes = showtimeRepository
            .findByTheaterAndStartTimeBetween(
                showtimeDTO.getTheater(),
                showtimeDTO.getStartTime(),
                showtimeDTO.getEndTime()
            );
            
        if (!overlappingShowtimes.isEmpty()) {
            throw new BadRequestException("There is already a showtime scheduled in this theater at this time");
        }
        
        Movie movie = movieRepository.findById(showtimeDTO.getMovieId()).orElseThrow(() -> new NotFoundException("Movie not found"));
        // Create the showtime and set the movie
        Showtime showtime = new Showtime();
        showtime.setMovie(movie);  // Set the movie
        showtime.setStartTime(showtimeDTO.getStartTime());
        showtime.setEndTime(showtimeDTO.getEndTime());
        showtime.setPrice(showtimeDTO.getPrice());
        showtime.setTheater(showtimeDTO.getTheater());
        return showtimeRepository.save(showtime);
    }
    
    private void validateShowtime(ShowtimeDTO showtime) {
        if (showtime.getEndTime().isBefore(showtime.getStartTime())) {
            throw new BadRequestException("End time cannot be before start time");
        }
        
        if (!movieRepository.existsById(showtime.getMovieId())) {
            throw new BadRequestException("Movie does not exist");
        }
    }

    private void validateShowtime(Showtime showtime) {
        if (showtime.getEndTime().isBefore(showtime.getStartTime())) {
            throw new BadRequestException("End time cannot be before start time");
        }
        
        if (!movieRepository.existsById(showtime.getMovie().getId())) {
            throw new BadRequestException("Movie does not exist");
        }
    }
    
    // Other methods for update, delete, and fetch
    public Showtime updateShowtime(Long id, ShowtimeDTO showtimeDTO) {
        Showtime existingShowtime = showtimeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Showtime not found"));
            
        validateShowtime(existingShowtime);
        
        existingShowtime.setMovie(existingShowtime.getMovie());
        existingShowtime.setTheater(showtimeDTO.getTheater());
        existingShowtime.setStartTime(showtimeDTO.getStartTime());
        existingShowtime.setEndTime(showtimeDTO.getEndTime());
        existingShowtime.setPrice(showtimeDTO.getPrice());
        
        return showtimeRepository.save(existingShowtime);
    }

    public void deleteShowtime(Long id) {
        if (!showtimeRepository.existsById(id)) {
            throw new NotFoundException("Showtime not found");
        }
        showtimeRepository.deleteById(id);
    }

    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }
    
}

