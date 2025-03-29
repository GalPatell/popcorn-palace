package com.att.tdp.popcorn_palace.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "movie")  // 'customer' is the field in Order that holds the reference
    private List<Showtime> showtimes;

    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Genre is required")
    private String genre;
    
    @Positive(message = "Duration must be positive")
    private Integer duration;
    
    @NotBlank(message = "Rating is required")
    private String rating;
    
    @Min(value = 1888, message = "Release year must be after 1888")
    private Integer releaseYear;
    
    // Default constructor
    public Movie() {
    }

    // Constructor with fields
    public Movie(String title, String genre, Integer duration, String rating, Integer releaseYear) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getRating() {
        return rating;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
}
