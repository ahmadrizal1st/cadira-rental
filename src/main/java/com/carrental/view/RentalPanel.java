package main.java.com.carrental.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RentalPanel extends JPanel {
    private JTable rentalTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton;
    private JTextField carIdField, customerIdField, rentalDateField, returnDateField, totalCostField;

    public RentalPanel() {
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "Car ID", "Customer ID", "Rental Date", "Return Date", "Total Cost"}, 0);
        rentalTable = new JTable(tableModel);
        add(new JScrollPane(rentalTable), BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.setBorder(BorderFactory.createTitledBorder("Rental Details"));

        formPanel.add(new JLabel("Car ID:"));
        carIdField = new JTextField();
        formPanel.add(carIdField);

        formPanel.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        formPanel.add(customerIdField);

        formPanel.add(new JLabel("Rental Date (YYYY-MM-DD):"));
        rentalDateField = new JTextField();
        formPanel.add(rentalDateField);

        formPanel.add(new JLabel("Return Date (YYYY-MM-DD):"));
        returnDateField = new JTextField();
        formPanel.add(returnDateField);

        formPanel.add(new JLabel("Total Cost:"));
        totalCostField = new JTextField();
        formPanel.add(totalCostField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Rental");
        updateButton = new JButton("Update Rental");
        deleteButton = new JButton("Delete Rental");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTextField getCarIdField() {
        return carIdField;
    }

    public JTextField getCustomerIdField() {
        return customerIdField;
    }

    public JTextField getRentalDateField() {
        return rentalDateField;
    }

    public JTextField getReturnDateField() {
        return returnDateField;
    }

    public JTextField getTotalCostField() {
        return totalCostField;
    }

    public JTable getRentalTable() {
        return rentalTable;
    }

    public void clearForm() {
        carIdField.setText("");
        customerIdField.setText("");
        rentalDateField.setText("");
        returnDateField.setText("");
        totalCostField.setText("");
    }
}


