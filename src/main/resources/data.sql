-- 1. Insert movie data
INSERT INTO movie (title, genre, duration, rating, release_year)
VALUES
('The Shawshank Redemption', 'Drama', 142, 'R', 1994),
('The Godfather', 'Crime', 175, 'R', 1972),
('The Dark Knight', 'Action', 152, 'PG-13', 2008),
('Pulp Fiction', 'Crime', 154, 'R', 1994),
('Inception', 'Sci-Fi', 148, 'PG-13', 2010);

-- 2. Insert showtimes data, which will auto-generate 'id' values for showtime records
-- Assuming the IDs generated will be 1, 2, 3, 4, 5
INSERT INTO showtimes (movie_id, theater, price, start_time, end_time)
VALUES
(1, 'Cineplex Downtown', 12.50, '2025-03-30 10:00:00', '2025-03-30 12:30:00'),
(2, 'IMAX Theater', 15.00, '2025-03-30 12:00:00', '2025-03-30 14:30:00'),
(3, 'Galaxy Cinema', 13.50, '2025-03-30 14:00:00', '2025-03-30 16:30:00'),
(4, 'Cineplex Downtown', 11.00, '2025-03-30 16:30:00', '2025-03-30 19:00:00'),
(5, 'IMAX Theater', 18.00, '2025-03-30 19:30:00', '2025-03-30 22:00:00');

-- After this, you can verify that the showtimes are inserted successfully.
-- Run a select query to check the inserted showtimes:
-- SELECT * FROM showtimes;

-- 3. Insert ticket data, using the 'id' values generated in the showtimes table
-- Assuming the auto-generated 'showtime_id' are 1, 2, 3, 4, 5 as inserted above
INSERT INTO tickets (seat_number, showtime_id, booking_time)
VALUES 
('A1', 1, '2025-03-29 14:00:00'),  -- Ticket for The Shawshank Redemption (showtime_id = 1)
('B2', 2, '2025-03-29 15:00:00'),  -- Ticket for The Godfather (showtime_id = 2)
('C3', 3, '2025-03-29 16:00:00'),  -- Ticket for The Dark Knight (showtime_id = 3)
('D4', 4, '2025-03-29 17:00:00'),  -- Ticket for Pulp Fiction (showtime_id = 4)
('E5', 5, '2025-03-29 18:00:00'),  -- Ticket for Inception (showtime_id = 5)
('F6', 1, '2025-03-29 14:30:00'),  -- Another ticket for The Shawshank Redemption (showtime_id = 1)
('G7', 2, '2025-03-29 15:30:00'),  -- Another ticket for The Godfather (showtime_id = 2)
('H8', 3, '2025-03-29 16:30:00'),  -- Another ticket for The Dark Knight (showtime_id = 3)
('I9', 4, '2025-03-29 17:30:00'),  -- Another ticket for Pulp Fiction (showtime_id = 4)
('J10', 5, '2025-03-29 18:30:00'); -- Another ticket for Inception (showtime_id = 5)
