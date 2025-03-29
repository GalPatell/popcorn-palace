-- Drop the constraint explicitly if it exists
-- ALTER TABLE IF EXISTS movie 
--     DROP CONSTRAINT IF EXISTS movie_release_year_check;
-- Drop tables if they exist (and automatically drop any dependent constraints)
DROP TABLE IF EXISTS tickets CASCADE;
DROP TABLE IF EXISTS showtimes CASCADE;
DROP TABLE IF EXISTS movie CASCADE;

-- Create the 'movie' table
CREATE TABLE IF NOT EXISTS movie (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,  -- IDENTITY to auto-generate id in H2
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    duration INT CHECK (duration > 0), -- Ensure duration is positive
    rating VARCHAR(255),
    release_year INT CHECK (release_year >= 1888 AND release_year <= EXTRACT(YEAR FROM CURRENT_DATE)) -- Restrict release year to valid range
);

-- Create the 'showtimes' table
CREATE TABLE IF NOT EXISTS showtimes (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    theater VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION CHECK (price > 0), -- Ensure positive price
    start_time TIMESTAMP(6) NOT NULL,
    end_time TIMESTAMP(6) NOT NULL,
    CONSTRAINT fk_showtime_movie FOREIGN KEY (movie_id) 
        REFERENCES movie (id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

-- Create the 'tickets' table with composite key
CREATE TABLE IF NOT EXISTS tickets (
    seat_number VARCHAR(255) NOT NULL,
    showtime_id BIGINT NOT NULL,
    booking_time TIMESTAMP NOT NULL,
    PRIMARY KEY (seat_number, showtime_id),  -- Composite primary key
    CONSTRAINT fk_showtime FOREIGN KEY (showtime_id) 
        REFERENCES showtimes(id) 
        ON DELETE CASCADE
);
