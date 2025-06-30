package main.java.com.carrental.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rental transaction in the car rental system.
 * This class models rental information including timing, costs,
 * and provides operations for rental management.
 */
public class Rental {
    private int rentalId;
    private int carId;
    private int customerId;
    private LocalDateTime rentalDatetime;
    private LocalDateTime returnDatetime;
    private double totalCost;

    /**
     * Constructs a new Rental instance.
     * 
     * @param rentalId The unique identifier for the rental
     * @param carId The ID of the rented car
     * @param customerId The ID of the renting customer
     * @param rentalDatetime The date and time when the rental starts
     * @param returnDatetime The date and time when the car is returned (nullable)
     * @param totalCost The total cost of the rental
     */
    public Rental(int rentalId, int carId, int customerId, 
                 LocalDateTime rentalDatetime, LocalDateTime returnDatetime, 
                 double totalCost) {
        this.rentalId = rentalId;
        this.carId = carId;
        this.customerId = customerId;
        this.rentalDatetime = rentalDatetime;
        this.returnDatetime = returnDatetime;
        this.totalCost = totalCost;
    }

    // Getters
    /** @return The rental's unique identifier */
    public int getRentalId() { return rentalId; }
    
    /** @return The ID of the rented car */
    public int getCarId() { return carId; }
    
    /** @return The ID of the renting customer */
    public int getCustomerId() { return customerId; }
    
    /** @return The start date and time of the rental */
    public LocalDateTime getRentalDatetime() { return rentalDatetime; }
    
    /** @return The return date and time of the rental (nullable) */
    public LocalDateTime getReturnDatetime() { return returnDatetime; }
    
    /** @return The total cost of the rental */
    public double getTotalCost() { return totalCost; }

    // Setters
    /** @param carId The car ID to set */
    public void setCarId(int carId) { this.carId = carId; }
    
    /** @param customerId The customer ID to set */
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    /** @param rentalDatetime The rental start datetime to set */
    public void setRentalDatetime(LocalDateTime rentalDatetime) { 
        this.rentalDatetime = rentalDatetime; 
    }
    
    /** @param returnDatetime The return datetime to set (nullable) */
    public void setReturnDatetime(LocalDateTime returnDatetime) { 
        this.returnDatetime = returnDatetime; 
    }
    
    /** @param totalCost The total cost to set */
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    // CRUD operations
    
    /**
     * Adds a new rental to the database.
     * 
     * @param rental The rental object to be added
     * @throws SQLException If a database access error occurs
     */
    public static void addRental(Rental rental) throws SQLException {
        String sql = "INSERT INTO rentals (car_id, customer_id, rental_datetime, return_datetime, total_cost) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Set parameters for the prepared statement
            stmt.setInt(1, rental.getCarId());
            stmt.setInt(2, rental.getCustomerId());
            stmt.setTimestamp(3, Timestamp.valueOf(rental.getRentalDatetime()));
            stmt.setTimestamp(4, rental.getReturnDatetime() != null ? 
                Timestamp.valueOf(rental.getReturnDatetime()) : null);
            stmt.setDouble(5, rental.getTotalCost());
            
            stmt.executeUpdate();

            // Retrieve the auto-generated rental ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rental.rentalId = generatedKeys.getInt(1);
                }
            }
        }
    }

    /**
     * Retrieves all rentals from the database.
     * 
     * @return A list of all rentals
     * @throws SQLException If a database access error occurs
     */
    public static List<Rental> getAllRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rentals";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Process each row in the result set
            while (rs.next()) {
                rentals.add(new Rental(
                    rs.getInt("rental_id"),
                    rs.getInt("car_id"),
                    rs.getInt("customer_id"),
                    rs.getTimestamp("rental_datetime").toLocalDateTime(),
                    rs.getTimestamp("return_datetime") != null ? 
                        rs.getTimestamp("return_datetime").toLocalDateTime() : null,
                    rs.getDouble("total_cost")
                ));
            }
        }
        return rentals;
    }

    /**
     * Updates an existing rental in the database.
     * 
     * @param rental The rental object with updated information
     * @throws SQLException If a database access error occurs
     */
    public static void updateRental(Rental rental) throws SQLException {
        String sql = "UPDATE rentals SET car_id = ?, customer_id = ?, rental_datetime = ?, " +
                     "return_datetime = ?, total_cost = ? WHERE rental_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set parameters for the prepared statement
            stmt.setInt(1, rental.getCarId());
            stmt.setInt(2, rental.getCustomerId());
            stmt.setTimestamp(3, Timestamp.valueOf(rental.getRentalDatetime()));
            stmt.setTimestamp(4, rental.getReturnDatetime() != null ? 
                Timestamp.valueOf(rental.getReturnDatetime()) : null);
            stmt.setDouble(5, rental.getTotalCost());
            stmt.setInt(6, rental.getRentalId());
            
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a rental from the database.
     * 
     * @param rentalId The ID of the rental to be deleted
     * @throws SQLException If a database access error occurs
     */
    public static void deleteRental(int rentalId) throws SQLException {
        String sql = "DELETE FROM rentals WHERE rental_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, rentalId);
            stmt.executeUpdate();
        }
    }

    /**
     * Checks if a car is available for rental during a specified time period.
     * 
     * @param carId The ID of the car to check
     * @param rentalStart The proposed start time of the rental
     * @param rentalEnd The proposed end time of the rental
     * @return true if the car is available, false otherwise
     * @throws SQLException If a database access error occurs
     */
    public static boolean isCarAvailable(int carId, LocalDateTime rentalStart, LocalDateTime rentalEnd) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rentals WHERE car_id = ? AND " +
                    "((rental_datetime < ? AND return_datetime > ?) OR " +
                    "(rental_datetime < ? AND return_datetime IS NULL)) " +
                    "AND return_datetime IS NOT NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, carId);
            stmt.setTimestamp(2, Timestamp.valueOf(rentalEnd));
            stmt.setTimestamp(3, Timestamp.valueOf(rentalStart));
            stmt.setTimestamp(4, Timestamp.valueOf(rentalEnd));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        }
        return false;
    }

    /**
     * Calculates the total cost of a rental based on the hourly rate and time period.
     * 
     * @param hourlyRate The hourly rate of the car
     * @param rentalStart The start time of the rental
     * @param rentalEnd The end time of the rental
     * @return The calculated total cost (0 if invalid parameters)
     */
    public static double calculateTotalCost(double hourlyRate, LocalDateTime rentalStart, LocalDateTime rentalEnd) {
        // Validate input parameters
        if (rentalStart == null || rentalEnd == null || rentalStart.isAfter(rentalEnd)) {
            return 0.0;
        }
        
        // Calculate full hours
        long hours = ChronoUnit.HOURS.between(rentalStart, rentalEnd);
        
        // Add 1 hour if there's any partial hour
        if (ChronoUnit.MINUTES.between(rentalStart, rentalEnd) % 60 != 0) {
            hours++;
        }
        
        return hourlyRate * hours;
    }
}