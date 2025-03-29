package com.att.tdp.popcorn_palace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.att.tdp.popcorn_palace.model.Showtime;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByTheaterAndStartTimeBetween(
        String theater, 
        LocalDateTime start, 
        LocalDateTime end
    );
}
