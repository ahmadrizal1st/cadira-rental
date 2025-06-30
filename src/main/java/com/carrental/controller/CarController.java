package main.java.com.carrental.controller;

import main.java.com.carrental.model.Car;
import main.java.com.carrental.view.CarPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class for managing car-related operations.
 * Handles communication between the Car model and CarPanel view,
 * including CRUD operations and user interactions.
 */
public class CarController {
    private CarPanel carPanel;

    /**
     * Constructs a CarController with the specified CarPanel.
     * 
     * @param carPanel The view component for car operations
     */
    public CarController(CarPanel carPanel) {
        this.carPanel = carPanel;
        initController();
    }

    /**
     * Initializes the controller by setting up event listeners
     * and loading initial car data.
     */
    private void initController() {
        // Set up action listeners for buttons
        carPanel.getAddButton().addActionListener(e -> addCar());
        carPanel.getUpdateButton().addActionListener(e -> updateCar());
        carPanel.getDeleteButton().addActionListener(e -> deleteCar());
        
        // Add selection listener to display selected car details
        carPanel.getCarTable().getSelectionModel().addListSelectionListener(e -> displaySelectedCar());
        
        // Load initial car data
        loadCars();
    }

    /**
     * Loads all cars from the database and populates the table.
     * Displays error message if database operation fails.
     */
    private void loadCars() {
        try {
            List<Car> cars = Car.getAllCars();
            DefaultTableModel model = carPanel.getTableModel();
            model.setRowCount(0); // Clear existing data
            
            // Add each car to the table model
            for (Car car : cars) {
                model.addRow(new Object[]{
                    car.getCarId(), 
                    car.getMake(), 
                    car.getModel(), 
                    car.getYear(), 
                    car.getLicensePlate(), 
                    car.getHourlyRate(), 
                    car.isAvailable()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(carPanel, 
                "Error loading cars: " + ex.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a new car to the database using form data.
     * Validates input and shows appropriate error messages.
     */
    private void addCar() {
        try {
            // Get data from form fields
            String make = carPanel.getMakeField().getText();
            String model = carPanel.getModelField().getText();
            int year = Integer.parseInt(carPanel.getYearField().getText());
            String licensePlate = carPanel.getLicensePlateField().getText();
            double hourlyRate = Double.parseDouble(carPanel.getHourlyRateField().getText());
            boolean available = carPanel.getAvailableCheckBox().isSelected();

            // Create and save new car
            Car car = new Car(0, make, model, year, licensePlate, hourlyRate, available);
            Car.addCar(car);
            
            JOptionPane.showMessageDialog(carPanel, "Car added successfully!");
            carPanel.clearForm();
            loadCars(); // Refresh the table
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(carPanel, 
                "Invalid input. Please enter valid numbers for Year and Hourly Rate.", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(carPanel, 
                "Error adding car: " + ex.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates an existing car in the database.
     * Requires a car to be selected in the table.
     */
    private void updateCar() {
        int selectedRow = carPanel.getCarTable().getSelectedRow();
        if (selectedRow >= 0) {
            try {
                // Get data from form fields and selected row
                int carId = (int) carPanel.getTableModel().getValueAt(selectedRow, 0);
                String make = carPanel.getMakeField().getText();
                String model = carPanel.getModelField().getText();
                int year = Integer.parseInt(carPanel.getYearField().getText());
                String licensePlate = carPanel.getLicensePlateField().getText();
                double hourlyRate = Double.parseDouble(carPanel.getHourlyRateField().getText());
                boolean available = carPanel.getAvailableCheckBox().isSelected();

                // Update the car
                Car car = new Car(carId, make, model, year, licensePlate, hourlyRate, available);
                Car.updateCar(car);
                
                JOptionPane.showMessageDialog(carPanel, "Car updated successfully!");
                carPanel.clearForm();
                loadCars(); // Refresh the table
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(carPanel, 
                    "Invalid input. Please enter valid numbers for Year and Hourly Rate.", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(carPanel, 
                    "Error updating car: " + ex.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(carPanel, 
                "Please select a car to update.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Deletes the selected car from the database after confirmation.
     */
    private void deleteCar() {
        int selectedRow = carPanel.getCarTable().getSelectedRow();
        if (selectedRow >= 0) {
            int carId = (int) carPanel.getTableModel().getValueAt(selectedRow, 0);
            
            // Confirm deletion with user
            int confirm = JOptionPane.showConfirmDialog(carPanel, 
                "Are you sure you want to delete this car?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Car.deleteCar(carId);
                    JOptionPane.showMessageDialog(carPanel, "Car deleted successfully!");
                    carPanel.clearForm();
                    loadCars(); // Refresh the table
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(carPanel, 
                        "Error deleting car: " + ex.getMessage(), 
                        "Database Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(carPanel, 
                "Please select a car to delete.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Displays the details of the selected car in the form fields.
     */
    private void displaySelectedCar() {
        int selectedRow = carPanel.getCarTable().getSelectedRow();
        if (selectedRow >= 0) {
            // Populate form fields with selected car's data
            carPanel.getMakeField().setText(carPanel.getTableModel().getValueAt(selectedRow, 1).toString());
            carPanel.getModelField().setText(carPanel.getTableModel().getValueAt(selectedRow, 2).toString());
            carPanel.getYearField().setText(carPanel.getTableModel().getValueAt(selectedRow, 3).toString());
            carPanel.getLicensePlateField().setText(carPanel.getTableModel().getValueAt(selectedRow, 4).toString());
            carPanel.getHourlyRateField().setText(carPanel.getTableModel().getValueAt(selectedRow, 5).toString());
            carPanel.getAvailableCheckBox().setSelected((boolean) carPanel.getTableModel().getValueAt(selectedRow, 6));
        }
    }
}