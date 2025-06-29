package main.java.com.carrental.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Rental {
    private int rentalId;
    private int carId;
    private int customerId;
    private LocalDateTime rentalDatetime;
    private LocalDateTime returnDatetime;
    private double totalCost;

    public Rental(int rentalId, int carId, int customerId, LocalDateTime rentalDatetime, LocalDateTime returnDatetime, double totalCost) {
        this.rentalId = rentalId;
        this.carId = carId;
        this.customerId = customerId;
        this.rentalDatetime = rentalDatetime;
        this.returnDatetime = returnDatetime;
        this.totalCost = totalCost;
    }

    // Getters
    public int getRentalId() { return rentalId; }
    public int getCarId() { return carId; }
    public int getCustomerId() { return customerId; }
    public LocalDateTime getRentalDatetime() { return rentalDatetime; }
    public LocalDateTime getReturnDatetime() { return returnDatetime; }
    public double getTotalCost() { return totalCost; }

    // Setters
    public void setCarId(int carId) { this.carId = carId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setRentalDatetime(LocalDateTime rentalDatetime) { this.rentalDatetime = rentalDatetime; }
    public void setReturnDatetime(LocalDateTime returnDatetime) { this.returnDatetime = returnDatetime; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    // CRUD operations
    public static void addRental(Rental rental) throws SQLException {
        String sql = "INSERT INTO rentals (car_id, customer_id, rental_datetime, return_datetime, total_cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, rental.getCarId());
            stmt.setInt(2, rental.getCustomerId());
            stmt.setTimestamp(3, Timestamp.valueOf(rental.getRentalDatetime()));
            stmt.setTimestamp(4, rental.getReturnDatetime() != null ? Timestamp.valueOf(rental.getReturnDatetime()) : null);
            stmt.setDouble(5, rental.getTotalCost());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rental.rentalId = generatedKeys.getInt(1);
                }
            }
        }
    }

    public static List<Rental> getAllRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rentals";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rentals.add(new Rental(
                    rs.getInt("rental_id"),
                    rs.getInt("car_id"),
                    rs.getInt("customer_id"),
                    rs.getTimestamp("rental_datetime").toLocalDateTime(),
                    rs.getTimestamp("return_datetime") != null ? rs.getTimestamp("return_datetime").toLocalDateTime() : null,
                    rs.getDouble("total_cost")
                ));
            }
        }
        return rentals;
    }

    public static void updateRental(Rental rental) throws SQLException {
        String sql = "UPDATE rentals SET car_id = ?, customer_id = ?, rental_datetime = ?, return_datetime = ?, total_cost = ? WHERE rental_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rental.getCarId());
            stmt.setInt(2, rental.getCustomerId());
            stmt.setTimestamp(3, Timestamp.valueOf(rental.getRentalDatetime()));
            stmt.setTimestamp(4, rental.getReturnDatetime() != null ? Timestamp.valueOf(rental.getReturnDatetime()) : null);
            stmt.setDouble(5, rental.getTotalCost());
            stmt.setInt(6, rental.getRentalId());
            stmt.executeUpdate();
        }
    }

    public static void deleteRental(int rentalId) throws SQLException {
        String sql = "DELETE FROM rentals WHERE rental_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rentalId);
            stmt.executeUpdate();
        }
    }

    public static boolean isCarAvailable(int carId, LocalDateTime rentalStart, LocalDateTime rentalEnd) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rentals WHERE car_id = ? AND ((rental_datetime < ? AND return_datetime > ?) OR (rental_datetime < ? AND return_datetime IS NULL)) AND return_datetime IS NOT NULL";
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

    public static double calculateTotalCost(double hourlyRate, LocalDateTime rentalStart, LocalDateTime rentalEnd) {
        if (rentalStart == null || rentalEnd == null || rentalStart.isAfter(rentalEnd)) {
            return 0.0;
        }
        long hours = ChronoUnit.HOURS.between(rentalStart, rentalEnd);
        // Add 1 hour if there's any partial hour
        if (ChronoUnit.MINUTES.between(rentalStart, rentalEnd) % 60 != 0) {
            hours++;
        }
        return hourlyRate * hours;
    }
}