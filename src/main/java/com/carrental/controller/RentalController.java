package main.java.com.carrental.controller;

import main.java.com.carrental.model.Rental;
import main.java.com.carrental.view.RentalPanel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class RentalController {
    private RentalPanel rentalPanel;

    public RentalController(RentalPanel rentalPanel) {
        this.rentalPanel = rentalPanel;
        initController();
    }

    private void initController() {
        rentalPanel.getAddButton().addActionListener(e -> addRental());
        rentalPanel.getUpdateButton().addActionListener(e -> updateRental());
        rentalPanel.getDeleteButton().addActionListener(e -> deleteRental());
        rentalPanel.getRentalTable().getSelectionModel().addListSelectionListener(e -> displaySelectedRental());
        loadRentals();
    }

    private void loadRentals() {
        try {
            List<Rental> rentals = Rental.getAllRentals();
            DefaultTableModel model = rentalPanel.getTableModel();
            model.setRowCount(0); // Clear existing data
            for (Rental rental : rentals) {
                model.addRow(new Object[]{rental.getRentalId(), rental.getCarId(), rental.getCustomerId(), rental.getRentalDate(), rental.getReturnDate(), rental.getTotalCost()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rentalPanel, "Error loading rentals: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRental() {
        try {
            int carId = Integer.parseInt(rentalPanel.getCarIdField().getText());
            int customerId = Integer.parseInt(rentalPanel.getCustomerIdField().getText());
            LocalDate rentalDate = LocalDate.parse(rentalPanel.getRentalDateField().getText());
            LocalDate returnDate = rentalPanel.getReturnDateField().getText().isEmpty() ? null : LocalDate.parse(rentalPanel.getReturnDateField().getText());
            double totalCost = Double.parseDouble(rentalPanel.getTotalCostField().getText());

            Rental rental = new Rental(0, carId, customerId, rentalDate, returnDate, totalCost);
            Rental.addRental(rental);
            JOptionPane.showMessageDialog(rentalPanel, "Rental added successfully!");
            rentalPanel.clearForm();
            loadRentals();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(rentalPanel, "Invalid input. Please enter valid numbers for Car ID, Customer ID, and Total Cost.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(rentalPanel, "Invalid date format. Please use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rentalPanel, "Error adding rental: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRental() {
        int selectedRow = rentalPanel.getRentalTable().getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int rentalId = (int) rentalPanel.getTableModel().getValueAt(selectedRow, 0);
                int carId = Integer.parseInt(rentalPanel.getCarIdField().getText());
                int customerId = Integer.parseInt(rentalPanel.getCustomerIdField().getText());
                LocalDate rentalDate = LocalDate.parse(rentalPanel.getRentalDateField().getText());
                LocalDate returnDate = rentalPanel.getReturnDateField().getText().isEmpty() ? null : LocalDate.parse(rentalPanel.getReturnDateField().getText());
                double totalCost = Double.parseDouble(rentalPanel.getTotalCostField().getText());

                Rental rental = new Rental(rentalId, carId, customerId, rentalDate, returnDate, totalCost);
                Rental.updateRental(rental);
                JOptionPane.showMessageDialog(rentalPanel, "Rental updated successfully!");
                rentalPanel.clearForm();
                loadRentals();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(rentalPanel, "Invalid input. Please enter valid numbers for Car ID, Customer ID, and Total Cost.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(rentalPanel, "Invalid date format. Please use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rentalPanel, "Error updating rental: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(rentalPanel, "Please select a rental to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteRental() {
        int selectedRow = rentalPanel.getRentalTable().getSelectedRow();
        if (selectedRow >= 0) {
            int rentalId = (int) rentalPanel.getTableModel().getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(rentalPanel, "Are you sure you want to delete this rental?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Rental.deleteRental(rentalId);
                    JOptionPane.showMessageDialog(rentalPanel, "Rental deleted successfully!");
                    rentalPanel.clearForm();
                    loadRentals();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rentalPanel, "Error deleting rental: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(rentalPanel, "Please select a rental to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void displaySelectedRental() {
        int selectedRow = rentalPanel.getRentalTable().getSelectedRow();
        if (selectedRow >= 0) {
            rentalPanel.getCarIdField().setText(rentalPanel.getTableModel().getValueAt(selectedRow, 1).toString());
            rentalPanel.getCustomerIdField().setText(rentalPanel.getTableModel().getValueAt(selectedRow, 2).toString());
            rentalPanel.getRentalDateField().setText(rentalPanel.getTableModel().getValueAt(selectedRow, 3).toString());
            Object returnDateObj = rentalPanel.getTableModel().getValueAt(selectedRow, 4);
            rentalPanel.getReturnDateField().setText(returnDateObj != null ? returnDateObj.toString() : "");
            rentalPanel.getTotalCostField().setText(rentalPanel.getTableModel().getValueAt(selectedRow, 5).toString());
        }
    }
}


