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

//package main.java.com.carrental.controller;
//
//import main.java.com.carrental.model.Car;
//import main.java.com.carrental.model.Rental;
//import main.java.com.carrental.view.RentalPanel;
//
//import javax.swing.JOptionPane;
//import javax.swing.table.DefaultTableModel;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//public class RentalController {
//    private RentalPanel rentalPanel;
//
//    public RentalController(RentalPanel rentalPanel) {
//        this.rentalPanel = rentalPanel;
//        initController();
//    }
//
//    private void initController() {
//        rentalPanel.getAddButton().addActionListener(e -> addRental());
//        rentalPanel.getUpdateButton().addActionListener(e -> updateRental());
//        rentalPanel.getDeleteButton().addActionListener(e -> deleteRental());
//        rentalPanel.getCalculateCostButton().addActionListener(e -> calculateAndDisplayCost());
//        rentalPanel.getRentalTable().getSelectionModel().addListSelectionListener(e -> displaySelectedRental());
//        loadRentals();
//    }
//
//    private void loadRentals() {
//        try {
//            List<Rental> rentals = Rental.getAllRentals();
//            DefaultTableModel model = rentalPanel.getTableModel();
//            model.setRowCount(0); // Clear existing data
//            for (Rental rental : rentals) {
//                model.addRow(new Object[]{
//                        rental.getRentalId(),
//                        rental.getCarId(),
//                        rental.getCustomerId(),
//                        rental.getRentalDatetime(),
//                        rental.getReturnDatetime(),
//                        rental.getTotalCost()
//                });
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rentalPanel, "Error loading rentals: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void addRental() {
//        try {
//            int carId = Integer.parseInt(rentalPanel.getCarIdField().getText());
//            int customerId = Integer.parseInt(rentalPanel.getCustomerIdField().getText());
//
//            // Combine date and time for rental and return datetime
//            LocalDateTime rentalDatetime = getDateTime(rentalPanel.getRentalDateChooser(), rentalPanel.getRentalTimeSpinner());
//            LocalDateTime returnDatetime = getDateTime(rentalPanel.getReturnDateChooser(), rentalPanel.getReturnTimeSpinner());
//
//            if (returnDatetime != null && rentalDatetime.isAfter(returnDatetime)) {
//                JOptionPane.showMessageDialog(rentalPanel, "Return datetime cannot be before rental datetime.", "Input Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // Check car availability
//            if (!Rental.isCarAvailable(carId, rentalDatetime, returnDatetime)) {
//                JOptionPane.showMessageDialog(rentalPanel, "Car is not available for the selected time period.", "Availability Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            Car car = Car.getAllCars().stream().filter(c -> c.getCarId() == carId).findFirst().orElse(null);
//            if (car == null) {
//                JOptionPane.showMessageDialog(rentalPanel, "Car not found.", "Input Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            double totalCost = Rental.calculateTotalCost(car.getHourlyRate(), rentalDatetime, returnDatetime);
//
//            Rental rental = new Rental(0, carId, customerId, rentalDatetime, returnDatetime, totalCost);
//            Rental.addRental(rental);
//            JOptionPane.showMessageDialog(rentalPanel, "Rental added successfully! Total Cost: " + String.format("%.2f", totalCost));
//            rentalPanel.clearForm();
//            loadRentals();
//        } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(rentalPanel, "Invalid input. Please enter valid numbers for Car ID and Customer ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rentalPanel, "Error adding rental: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    // Utility function to combine date and time into LocalDateTime
//    private LocalDateTime getDateTime(JDateChooser dateChooser, JSpinner timeSpinner) {
//        if (dateChooser.getDate() == null) return null;
//
//        Date date = dateChooser.getDate();
//        String time = new SimpleDateFormat("HH:mm").format(timeSpinner.getValue());
//
//        String dateTimeStr = new SimpleDateFormat("yyyy-MM-dd").format(date) + " " + time;
//        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//    }
//
//    // Other methods (updateRental, deleteRental, etc.) remain unchanged
//    public static void updateRental(Rental rental) throws SQLException {
//        String sql = "UPDATE rentals SET car_id = ?, customer_id = ?, rental_datetime = ?, return_datetime = ?, total_cost = ? WHERE rental_id = ?";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, rental.getCarId());
//            stmt.setInt(2, rental.getCustomerId());
//            stmt.setTimestamp(3, Timestamp.valueOf(rental.getRentalDatetime()));
//            stmt.setTimestamp(4, rental.getReturnDatetime() != null ? Timestamp.valueOf(rental.getReturnDatetime()) : null);
//            stmt.setDouble(5, rental.getTotalCost());
//            stmt.setInt(6, rental.getRentalId());
//            stmt.executeUpdate();
//        }
//    }
//
//    public static void deleteRental(int rentalId) throws SQLException {
//        String sql = "DELETE FROM rentals WHERE rental_id = ?";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, rentalId);
//            stmt.executeUpdate();
//        }
//    }
//
//    public static boolean isCarAvailable(int carId, LocalDateTime rentalStart, LocalDateTime rentalEnd) throws SQLException {
//        String sql = "SELECT COUNT(*) FROM rentals WHERE car_id = ? AND ((rental_datetime < ? AND return_datetime > ?) OR (rental_datetime < ? AND return_datetime IS NULL)) AND return_datetime IS NOT NULL";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, carId);
//            stmt.setTimestamp(2, Timestamp.valueOf(rentalEnd));
//            stmt.setTimestamp(3, Timestamp.valueOf(rentalStart));
//            stmt.setTimestamp(4, Timestamp.valueOf(rentalEnd));
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getInt(1) == 0;
//                }
//            }
//        }
//        return false;
//    }
//
//    public static double calculateTotalCost(double hourlyRate, LocalDateTime rentalStart, LocalDateTime rentalEnd) {
//        if (rentalStart == null || rentalEnd == null || rentalStart.isAfter(rentalEnd)) {
//            return 0.0;
//        }
//        long hours = ChronoUnit.HOURS.between(rentalStart, rentalEnd);
//        // Add 1 hour if there's any partial hour
//        if (ChronoUnit.MINUTES.between(rentalStart, rentalEnd) % 60 != 0) {
//            hours++;
//        }
//        return hourlyRate * hours;
//    }
//}



