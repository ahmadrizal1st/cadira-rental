package main.java.com.carrental.controller;

import main.java.com.carrental.model.Car;
import main.java.com.carrental.view.CarPanel;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class CarController {
    private CarPanel carPanel;

    public CarController(CarPanel carPanel) {
        this.carPanel = carPanel;
        initController();
    }

    private void initController() {
        carPanel.getAddButton().addActionListener(e -> addCar());
        carPanel.getUpdateButton().addActionListener(e -> updateCar());
        carPanel.getDeleteButton().addActionListener(e -> deleteCar());
        carPanel.getCarTable().getSelectionModel().addListSelectionListener(e -> displaySelectedCar());
        loadCars();
    }

    private void loadCars() {
        try {
            List<Car> cars = Car.getAllCars();
            DefaultTableModel model = carPanel.getTableModel();
            model.setRowCount(0); // Clear existing data
            for (Car car : cars) {
                model.addRow(new Object[]{car.getCarId(), car.getMake(), car.getModel(), car.getYear(), car.getLicensePlate(), car.getDailyRate(), car.isAvailable()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(carPanel, "Error loading cars: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCar() {
        try {
            String make = carPanel.getMakeField().getText();
            String model = carPanel.getModelField().getText();
            int year = Integer.parseInt(carPanel.getYearField().getText());
            String licensePlate = carPanel.getLicensePlateField().getText();
            double dailyRate = Double.parseDouble(carPanel.getDailyRateField().getText());
            boolean available = carPanel.getAvailableCheckBox().isSelected();

            Car car = new Car(0, make, model, year, licensePlate, dailyRate, available);
            Car.addCar(car);
            JOptionPane.showMessageDialog(carPanel, "Car added successfully!");
            carPanel.clearForm();
            loadCars();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(carPanel, "Invalid input. Please enter valid numbers for Year and Daily Rate.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(carPanel, "Error adding car: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCar() {
        int selectedRow = carPanel.getCarTable().getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int carId = (int) carPanel.getTableModel().getValueAt(selectedRow, 0);
                String make = carPanel.getMakeField().getText();
                String model = carPanel.getModelField().getText();
                int year = Integer.parseInt(carPanel.getYearField().getText());
                String licensePlate = carPanel.getLicensePlateField().getText();
                double dailyRate = Double.parseDouble(carPanel.getDailyRateField().getText());
                boolean available = carPanel.getAvailableCheckBox().isSelected();

                Car car = new Car(carId, make, model, year, licensePlate, dailyRate, available);
                Car.updateCar(car);
                JOptionPane.showMessageDialog(carPanel, "Car updated successfully!");
                carPanel.clearForm();
                loadCars();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(carPanel, "Invalid input. Please enter valid numbers for Year and Daily Rate.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(carPanel, "Error updating car: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(carPanel, "Please select a car to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteCar() {
        int selectedRow = carPanel.getCarTable().getSelectedRow();
        if (selectedRow >= 0) {
            int carId = (int) carPanel.getTableModel().getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(carPanel, "Are you sure you want to delete this car?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Car.deleteCar(carId);
                    JOptionPane.showMessageDialog(carPanel, "Car deleted successfully!");
                    carPanel.clearForm();
                    loadCars();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(carPanel, "Error deleting car: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(carPanel, "Please select a car to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void displaySelectedCar() {
        int selectedRow = carPanel.getCarTable().getSelectedRow();
        if (selectedRow >= 0) {
            carPanel.getMakeField().setText(carPanel.getTableModel().getValueAt(selectedRow, 1).toString());
            carPanel.getModelField().setText(carPanel.getTableModel().getValueAt(selectedRow, 2).toString());
            carPanel.getYearField().setText(carPanel.getTableModel().getValueAt(selectedRow, 3).toString());
            carPanel.getLicensePlateField().setText(carPanel.getTableModel().getValueAt(selectedRow, 4).toString());
            carPanel.getDailyRateField().setText(carPanel.getTableModel().getValueAt(selectedRow, 5).toString());
            carPanel.getAvailableCheckBox().setSelected((boolean) carPanel.getTableModel().getValueAt(selectedRow, 6));
        }
    }
}


