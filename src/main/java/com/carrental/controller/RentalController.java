package main.java.com.carrental.controller;

import main.java.com.carrental.model.Car;
import main.java.com.carrental.model.Rental;
import main.java.com.carrental.view.RentalPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Date;

/**
 * Controller class for managing rental operations.
 * Handles the interaction between the Rental model and RentalPanel view,
 * including CRUD operations, cost calculations, and date/time validations.
 */
public class RentalController {
    private RentalPanel rentalPanel;

    /**
     * Constructs a RentalController with the specified RentalPanel.
     * 
     * @param rentalPanel The view component for rental operations
     */
    public RentalController(RentalPanel rentalPanel) {
        this.rentalPanel = rentalPanel;
        initController();
    }

    /**
     * Initializes the controller by setting up event listeners
     * and loading initial rental data.
     */
    private void initController() {
        // Set up action listeners for all buttons
        rentalPanel.getAddButton().addActionListener(e -> addRental());
        rentalPanel.getUpdateButton().addActionListener(e -> updateRental());
        rentalPanel.getDeleteButton().addActionListener(e -> deleteRental());
        rentalPanel.getCalculateCostButton().addActionListener(e -> calculateAndDisplayCost());
        
        // Add selection listener to display selected rental details
        rentalPanel.getRentalTable().getSelectionModel().addListSelectionListener(
            e -> displaySelectedRental());
        
        // Load initial rental data
        loadRentals();
    }

    /**
     * Loads all rentals from the database and populates the table.
     * Displays an error message if the database operation fails.
     */
    private void loadRentals() {
        try {
            List<Rental> rentals = Rental.getAllRentals();
            DefaultTableModel model = rentalPanel.getTableModel();
            model.setRowCount(0); // Clear existing data
            
            // Add each rental to the table model
            for (Rental rental : rentals) {
                model.addRow(new Object[]{
                    rental.getRentalId(),
                    rental.getCarId(),
                    rental.getCustomerId(),
                    rental.getRentalDatetime(),
                    rental.getReturnDatetime(),
                    rental.getTotalCost()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rentalPanel,
                "Error loading rentals: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a new rental to the database after validating inputs and car availability.
     * Calculates total cost based on rental duration and car's hourly rate.
     */
    private void addRental() {
        try {
            // Get basic rental information from form
            int carId = Integer.parseInt(rentalPanel.getCarIdField().getText());
            int customerId = Integer.parseInt(rentalPanel.getCustomerIdField().getText());

            // Get rental and return datetimes
            LocalDateTime rentalDatetime = rentalPanel.getRentalDateTime();
            LocalDateTime returnDatetime = rentalPanel.getReturnDateTime();

            // Validate datetime sequence
            if (returnDatetime != null && rentalDatetime.isAfter(returnDatetime)) {
                JOptionPane.showMessageDialog(rentalPanel,
                    "Return datetime cannot be before rental datetime.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check car availability for the selected period
            if (!Rental.isCarAvailable(carId, rentalDatetime, returnDatetime)) {
                JOptionPane.showMessageDialog(rentalPanel,
                    "Car is not available for the selected time period.",
                    "Availability Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get car details for rate calculation
            Car car = Car.getAllCars().stream()
                .filter(c -> c.getCarId() == carId)
                .findFirst()
                .orElse(null);
                
            if (car == null) {
                JOptionPane.showMessageDialog(rentalPanel,
                    "Car not found.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate and display total cost
            double totalCost = Rental.calculateTotalCost(
                car.getHourlyRate(),
                rentalDatetime,
                returnDatetime);

            // Create and save new rental
            Rental rental = new Rental(0, carId, customerId,
                rentalDatetime, returnDatetime, totalCost);
            Rental.addRental(rental);
            
            JOptionPane.showMessageDialog(rentalPanel,
                "Rental added successfully! Total Cost: " + 
                String.format("%.2f", totalCost));
            rentalPanel.clearForm();
            loadRentals(); // Refresh the table
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(rentalPanel,
                "Invalid input. Please enter valid numbers for Car ID and Customer ID.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(rentalPanel,
                "Invalid datetime format. Please use YYYY-MM-DD HH:MM:SS.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rentalPanel,
                "Error adding rental: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates an existing rental in the database.
     * Performs validation checks similar to addRental().
     */
    private void updateRental() {
        int selectedRow = rentalPanel.getRentalTable().getSelectedRow();
        if (selectedRow >= 0) {
            try {
                // Get rental ID from selected row
                int rentalId = (int) rentalPanel.getTableModel().getValueAt(selectedRow, 0);
                
                // Get updated information from form
                int carId = Integer.parseInt(rentalPanel.getCarIdField().getText());
                int customerId = Integer.parseInt(rentalPanel.getCustomerIdField().getText());
                LocalDateTime rentalDatetime = rentalPanel.getRentalDateTime();
                LocalDateTime returnDatetime = rentalPanel.getReturnDateTime();
        
                // Validate datetime sequence
                if (returnDatetime != null && rentalDatetime.isAfter(returnDatetime)) {
                    JOptionPane.showMessageDialog(rentalPanel,
                        "Return datetime cannot be before rental datetime.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check car availability (see note in documentation)
                if (!Rental.isCarAvailable(carId, rentalDatetime, returnDatetime)) {
                    JOptionPane.showMessageDialog(rentalPanel,
                        "Car is not available for the selected time period.",
                        "Availability Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get car details for rate calculation
                Car car = Car.getAllCars().stream()
                    .filter(c -> c.getCarId() == carId)
                    .findFirst()
                    .orElse(null);
                    
                if (car == null) {
                    JOptionPane.showMessageDialog(rentalPanel,
                        "Car not found.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Calculate new total cost
                double totalCost = Rental.calculateTotalCost(
                    car.getHourlyRate(),
                    rentalDatetime,
                    returnDatetime);

                // Update the rental
                Rental rental = new Rental(rentalId, carId, customerId,
                    rentalDatetime, returnDatetime, totalCost);
                Rental.updateRental(rental);
                
                JOptionPane.showMessageDialog(rentalPanel,
                    "Rental updated successfully! Total Cost: " + 
                    String.format("%.2f", totalCost));
                rentalPanel.clearForm();
                loadRentals(); // Refresh the table
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(rentalPanel,
                    "Invalid input. Please enter valid numbers for Car ID and Customer ID.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(rentalPanel,
                    "Invalid datetime format. Please use YYYY-MM-DD HH:MM:SS.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rentalPanel,
                    "Error updating rental: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(rentalPanel,
                "Please select a rental to update.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Deletes the selected rental from the database after confirmation.
     */
    private void deleteRental() {
        int selectedRow = rentalPanel.getRentalTable().getSelectedRow();
        if (selectedRow >= 0) {
            int rentalId = (int) rentalPanel.getTableModel().getValueAt(selectedRow, 0);
            
            // Confirm deletion with user
            int confirm = JOptionPane.showConfirmDialog(rentalPanel,
                "Are you sure you want to delete this rental?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Rental.deleteRental(rentalId);
                    JOptionPane.showMessageDialog(rentalPanel,
                        "Rental deleted successfully!");
                    rentalPanel.clearForm();
                    loadRentals(); // Refresh the table
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rentalPanel,
                        "Error deleting rental: " + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(rentalPanel,
                "Please select a rental to delete.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Displays the details of the selected rental in the form fields.
     * Handles date/time parsing and formatting for proper display.
     */
    private void displaySelectedRental() {
        int selectedRow = rentalPanel.getRentalTable().getSelectedRow();
        if (selectedRow >= 0) {
            try {
                // Set basic rental information
                rentalPanel.getCarIdField().setText(
                    rentalPanel.getTableModel().getValueAt(selectedRow, 1).toString());
                rentalPanel.getCustomerIdField().setText(
                    rentalPanel.getTableModel().getValueAt(selectedRow, 2).toString());

                // Handle Rental Date and Time parsing
                Object rentalDateObj = rentalPanel.getTableModel().getValueAt(selectedRow, 3);
                if (rentalDateObj instanceof String) {
                    String rentalDateString = (String) rentalDateObj;
                    String[] dateTimeParts = rentalDateString.split("T");
                    
                    if (dateTimeParts.length >= 2) {
                        // Parse and set Rental Date
                        Date rentalDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateTimeParts[0]);
                        if (rentalPanel.getRentalDatePicker() != null) {
                            rentalPanel.setRentalDatePicker(rentalDate);
                        }
                        
                        // Parse and set Rental Time
                        java.sql.Time rentalTime = java.sql.Time.valueOf(dateTimeParts[1]);
                        if (rentalPanel.getRentalTimeSpinner() != null) {
                            rentalPanel.setRentalTimeSpinner(rentalTime);
                        }
                    }
                }

                // Handle Return Date and Time parsing
                Object returnDateObj = rentalPanel.getTableModel().getValueAt(selectedRow, 4);
                if (returnDateObj instanceof String) {
                    String returnDateString = (String) returnDateObj;
                    String[] dateTimeParts = returnDateString.split("T");
                    
                    if (dateTimeParts.length >= 2) {
                        // Parse and set Return Date
                        Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateTimeParts[0]);
                        if (rentalPanel.getReturnDatePicker() != null) {
                            rentalPanel.setReturnDatePicker(returnDate);
                        }
                        
                        // Parse and set Return Time
                        java.sql.Time returnTime = java.sql.Time.valueOf(dateTimeParts[1]);
                        if (rentalPanel.getReturnTimeSpinner() != null) {
                            rentalPanel.setReturnTimeSpinner(returnTime);
                        }
                    }
                }

                // Set Total Cost
                rentalPanel.getTotalCostLabel().setText(
                    rentalPanel.getTableModel().getValueAt(selectedRow, 5).toString());
                    
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(rentalPanel, 
                    "Error parsing date: " + e.getMessage(), 
                    "Date Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(rentalPanel, 
                    "Invalid time format: " + e.getMessage(), 
                    "Time Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rentalPanel, 
                    "Error displaying rental data: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Calculates and displays the rental cost based on current form inputs.
     * Validates inputs and shows appropriate error messages.
     */
    private void calculateAndDisplayCost() {
        try {
            int carId = Integer.parseInt(rentalPanel.getCarIdField().getText());
            
            // Get rental and return datetimes
            LocalDateTime rentalDatetime = rentalPanel.getRentalDateTime();
            LocalDateTime returnDatetime = rentalPanel.getReturnDateTime();

            // Validate datetime sequence
            if (returnDatetime != null && rentalDatetime.isAfter(returnDatetime)) {
                JOptionPane.showMessageDialog(rentalPanel,
                    "Return datetime cannot be before rental datetime.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                rentalPanel.getTotalCostLabel().setText("0.00");
                return;
            }

            // Get car details for rate calculation
            Car car = Car.getAllCars().stream()
                .filter(c -> c.getCarId() == carId)
                .findFirst()
                .orElse(null);
                
            if (car == null) {
                JOptionPane.showMessageDialog(rentalPanel,
                    "Car not found.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                rentalPanel.getTotalCostLabel().setText("0.00");
                return;
            }

            // Calculate and display total cost
            double totalCost = Rental.calculateTotalCost(
                car.getHourlyRate(),
                rentalDatetime,
                returnDatetime);
                
            rentalPanel.getTotalCostLabel().setText(String.format("%.2f", totalCost));
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(rentalPanel,
                "Invalid input. Please enter valid numbers for Car ID.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            rentalPanel.getTotalCostLabel().setText("0.00");
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(rentalPanel,
                "Invalid datetime format. Please use YYYY-MM-DD HH:MM:SS.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
            rentalPanel.getTotalCostLabel().setText("0.00");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rentalPanel,
                "Error calculating cost: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            rentalPanel.getTotalCostLabel().setText("0.00");
        }
    }
}