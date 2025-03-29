
# Popcorn Palace Project Setup Instructions

## Prerequisites
1. **Java Development Kit (JDK)**: Ensure JDK 21 or later is installed.
2. **Maven**: Ensure Apache Maven is installed and added to your system's PATH.
3. **PostgreSQL**: Install PostgreSQL and ensure it is running.
4. **Docker**: Install Docker if you plan to use the provided `docker-compose.yml` file for database setup.

---

## Setting Up the Project

### 1. Clone the Repository
```bash
git clone <repository-url>
cd popcorn-palace
```

### 2. Configure the Database
- **Option 1: Using Docker Compose**
  1. Navigate to the project root directory.
  2. Run the following command to start the PostgreSQL database:
     ```bash
     docker-compose up -d
     ```
  3. Verify that the database is running on `localhost:5432`.

- **Option 2: Manual Setup**
  1. Create a PostgreSQL database named `popcorn-palace`.
  2. Create a user with the following credentials:
     - Username: `popcorn-palace`
     - Password: `popcorn-palace`
  3. Grant all privileges on the database to the user.

---

### 3. Configure Application Properties
- The application is pre-configured to connect to the database using the credentials specified above.
- If you need to modify the database connection, update the `application.yaml` file located in:
  ```
  src/main/resources/application.yaml
  ```

---

### 4. Run the Application
1. Build the project using Maven:
   ```bash
   mvn clean install
   ```
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```
3. The application will start on `http://localhost:8080`.

---

## Running Tests
1. The project includes unit and integration tests.
2. To run all tests, execute:
   ```bash
   mvn test
   ```

---
## Postman collection is part of the repository

## API Endpoints
### Movies
- **Create Movie**: `POST /movies`
- **Update Movie**: `PUT /movies/{id}`
- **Delete Movie**: `DELETE /movies/{id}`
- **Get All Movies**: `GET /movies`

### Showtimes
- **Create Showtime**: `POST /showtimes`
- **Update Showtime**: `PUT /showtimes/{id}`
- **Delete Showtime**: `DELETE /showtimes/{id}`
- **Get All Showtimes**: `GET /showtimes`

### Tickets
- **Book Ticket**: `POST /tickets`

---

## Additional Notes
- The project uses an in-memory H2 database for testing. Configuration for this is located in:
  ```
  src/test/resources/application.yaml
  ```
- Ensure the `test` profile is active when running tests.

---

## Troubleshooting
- **Database Connection Issues**: Verify that the database is running and the credentials in `application.yaml` are correct.
- **Port Conflicts**: Ensure no other application is using port `8080` or `5432`.

---
