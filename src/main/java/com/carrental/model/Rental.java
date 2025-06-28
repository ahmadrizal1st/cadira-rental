package main.java.com.carrental.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rental {
    private int rentalId;
    private int carId;
    private int customerId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private double totalCost;

    public Rental(int rentalId, int carId, int customerId, LocalDate rentalDate, LocalDate returnDate, double totalCost) {
        this.rentalId = rentalId;
        this.carId = carId;
        this.customerId = customerId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalCost = totalCost;
    }

    // Getters
    public int getRentalId() { return rentalId; }
    public int getCarId() { return carId; }
    public int getCustomerId() { return customerId; }
    public LocalDate getRentalDate() { return rentalDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public double getTotalCost() { return totalCost; }

    // Setters
    public void setCarId(int carId) { this.carId = carId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setRentalDate(LocalDate rentalDate) { this.rentalDate = rentalDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    // CRUD operations
    public static void addRental(Rental rental) throws SQLException {
        String sql = "INSERT INTO rentals (car_id, customer_id, rental_date, return_date, total_cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, rental.getCarId());
            stmt.setInt(2, rental.getCustomerId());
            stmt.setDate(3, Date.valueOf(rental.getRentalDate()));
            stmt.setDate(4, rental.getReturnDate() != null ? Date.valueOf(rental.getReturnDate()) : null);
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
                    rs.getDate("rental_date").toLocalDate(),
                    rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null,
                    rs.getDouble("total_cost")
                ));
            }
        }
        return rentals;
    }

    public static void updateRental(Rental rental) throws SQLException {
        String sql = "UPDATE rentals SET car_id = ?, customer_id = ?, rental_date = ?, return_date = ?, total_cost = ? WHERE rental_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rental.getCarId());
            stmt.setInt(2, rental.getCustomerId());
            stmt.setDate(3, Date.valueOf(rental.getRentalDate()));
            stmt.setDate(4, rental.getReturnDate() != null ? Date.valueOf(rental.getReturnDate()) : null);
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
}


